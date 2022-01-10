$(function(){
  // 현재 날짜 구하기
  var today = new Date();
  func_dateSetting(today.getFullYear(),today.getMonth()+1,today.getDate());

  $(".dateAreaInput").change(function(){

  });



  /*/!* Url Hash Navigation *!/
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

  $('.owl-item').click(function() {
    $('.owl-item').removeClass("center");
    $(this).addClass("center");
    var date = $(".center").children().attr("data-hash");
    location.hash = date;
    $(".scheduleArea").empty();
    func_getScheduleData(date);
  });*/
});


// dateArea 값 지정
function func_dateSetting(year,month,date){

  // Year Input, 현재 년도 기준으로 작년, 올해 보여줌.
  for(var i=year-1; i<year+2; i++){
    $("#year").append("<option value='"+i+"'>"+i+"</option>");
  }
  $("#year").val(year).prop("selected",true);

  // Month Input
  var monthArray = ['January','February','March','April','May','June','July','August','September','October','November','December'];

  for(var i=0; i<monthArray.length; i++){
    $("#month").append("<option value='"+(i+1)+"'>"+monthArray[i]+"</option>");
  }
  $("#month").val(month).prop("selected",true);

  // Week
  var lastdate = new Date(year, month-1, 0).getDate();
  var firstdateDay = new Date(year, month-1, 0).getDay();
  var lastdateDay = new Date(year, month-1, lastdate).getDay();
  var today = new Date(year, month-1, date).getDay();

  var weekcnt = Math.floor(lastdate/7);
  if(lastdate%7!=0) {
    if(7-firstdateDay < lastdate%7){
      weekcnt=weekcnt+2;
    } else if(7-firstdateDay == lastdate%7) {
      weekcnt=weekcnt+1;
    }
  }

  var weekcntToday;
  if(7-firstdateDay < date%7){
    weekcntToday=Math.floor(date/7+2);
  } else if(7-firstdateDay == date%7) {
    weekcntToday=Math.floor(date/7+1);
  }

  for(var i=1; i<weekcnt+1; i++){
    if(i==weekcntToday){
      $("#week").append("<span class='col weekNo checkedWeekNo' value='"+i+"'>"+i+"</span>");
    } else {
      $("#week").append("<span class='col weekNo' value='"+i+"'>"+i+"</span>");
    }
  }

  var weekStartDate, weekEndDate;

  if(weekcntToday==1){
    weekStartDate = year+"-"+month+"-01";
    weekEndDate = year+"-"+month+"-0"+(7-firstdateDay);

  } else if(weekcnt==weekcntToday){
    weekStartDate = year+"-"+month+"-"+(lastdate-lastdateDay);
    weekEndDate = year+"-"+month+"-"+lastdate;

  } else {
    if((date-today)<10){
      weekStartDate = year+"-"+month+"-0"+(date-today);
    }
    else {
      weekStartDate = year+"-"+month+"-"+(date-today);
    }

    if((date+7-today)<10){
      weekEndDate = year+"-"+month+"-0"+(date+7-today);
    }
    else {
      weekEndDate = year+"-"+month+"-"+(date+7-today);
    }
  }

  func_getScheduleData(weekStartDate, weekEndDate)
}


// schduleList 가져오기
function func_getScheduleData(startDate, endDate){
  $.ajax({
    url:"/getSchedule",
    data:{startDate:startDate, endDate:endDate},
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


// 일정 편집페이지로 이동
function func_detail(id) {
  location.href="/report_t/makeSchedule?no="+id;
}


// 주차 구하기
function func_getWeek(year, month, date){

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