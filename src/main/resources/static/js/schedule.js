$(function(){
  // 현재 날짜 구하기
  var today = new Date();
  var year = today.getFullYear();
  var month = today.getMonth()+1;
  var date = today.getDate();

  func_dateSetting(year,month,date);

  /* Url Hash Navigation */
  $('.owl-carousel').owlCarousel({
    items:6,
    loop:false,
    center:true,
    margin:10,
    URLhashListener:true,
    autoplayHoverPause:false,
    startPosition: 'URLHash'
  })

  $(".owl-dots").hide();

  var todayDate = $("#todayDate").val();
  location.hash = todayDate;
  /*func_getScheduleData(todayDate);*/

 /* $('.owl-carousel').on('changed.owl.carousel', function(e) {
    var date = $(".center").children().attr("data-hash");
    func_getScheduleData(date);
  });*/


});


// 초기 dateArea 값 지정
function func_dateSetting(year,month,date){

  // 현재 년도 기준으로 작년, 올해 보여줌.
  for(var i=year-1; i<year+2; i++){
    $("#year").append("<option value='"+i+"'>"+i+"</option>");
  }
  $("#year").val(year).prop("selected",true);

  const monthArray = ['January','February','March','April','May','June','July','August','September','October','November','December'];

  for(var i=0; i<monthArray.length; i++){
    $("#month").append("<option value='"+(i+1)+"'>"+monthArray[i]+"</option>");
  }
  $("#month").val(month).prop("selected",true);

}


// schduleList 가져오기
function func_getScheduleData(date){
  $.ajax({
    url:"/getScheduleData",
    data:{date:date},
    success: function(data){

      if($("#loginType")=="1"){
        $.each(data.scheduleList, function(idx, val) {
          var attenderCnt = val.schedule_attender.split(",").length;

          $(".scheduleArea").append("<div class='subArea' id='" +val.schedule_no+ "' onclick='func_report(this)'>"
              + "<span class='date'>" + val.schedule_date + "</span><br>"
              + "<span class='time'>" + val.schedule_start + " - "
              + val.schedule_end + "</span><br>"
              + "<span class='space'><span id='attenderCnt'>" + attenderCnt
              + "</span> / " + val.schedule_space + "</span>"
              + "<img class='attenderImg' src='image/report/attender.png'/>"
              + "</div>");
        });
      } else {
        $.each(data.scheduleList, function(idx, val) {
          var valArray = val.split(",");
          val = val.replace(","," ");
          $(".scheduleArea").append("<span class='choice teacher' id='"+valArray[0]+" "+valArray[1]+","+valArray[2]+"' "
              + "onclick='func_getSchedule(this.id)'>"
              + valArray[0]+" "+valArray[1]+"</span>")
        });
      }

    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });

}



/*// owlCarousel 값 넣기
function func_dateDetail(year,month){
  var dayCnt = new Date(year, month, 0).getDate();

  for (var i=1; i<dayCnt+1; i++){
    var day = new Date(year, month-1, i);
    const dayArray = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
    var week = dayArray[day.getDay()];

    var month = ('0' + month).slice(-2);

    if(i<10){
      $(".owl-item").append("<div class='item' data-hash='"+year+"-"+month+"-0"+i+"'>"
          + " <div class='dateDetail'>"
          + " <span class='day'>"+week+"</span>"
          + " <span>"+i+"</span>"
          + " </div></div>");
    } else {
      $(".owl-item").append("<div class='item' data-hash='"+year+"-"+month+"-"+i+"'>"
          + " <div class='dateDetail'>"
          + " <span class='day'>"+week+"</span>"
          + " <span>"+i+"</span>"
          + " </div></div>");
    }

  }
}*/


/*  // Listen to owl events:
  owl.on('changed.owl.carousel', function(event) {
    location.hash = 'slide' + event.property.value;
  })*/