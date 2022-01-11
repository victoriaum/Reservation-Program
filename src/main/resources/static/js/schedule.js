$(function(){
  var todayDate = new Date();
  var year = todayDate.getFullYear();
  var month = todayDate.getMonth()+1;
  var date = todayDate.getDate();

  // 초기값 설정
  func_inputDate(year,month);
  var calculateDate = func_calculateWeek(year, month, date);
  var dateArray = calculateDate.split(" ");
  func_getSchedule(year, month, dateArray[0], dateArray[1]);

  // dateArea 변경하는 경우
  $(".dateAreaInput").change(function(){
    var year = $("#year option:selected").val();
    var monthArray = ['January','February','March','April','May','June','July','August','September','October','November','December'];
    var month;
    for(var i=0; i<monthArray.length; i++){
      if(monthArray[i]==$("#month option:selected").val()){
        month=i;
      }
    }
    func_inputDate(year,month);

    var week = $(".week").hasClass("checkedWeekNo").val();
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


// 일정 편집페이지로 이동
function func_detail(id) {
  location.href="/report_t/makeSchedule?no="+id;
}


// dateArea Input 설정
function func_inputDate(year,month){
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

}


// 주차 계산하기
function func_calculateWeek(year,month,date){
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
      weekEndDate = year+"-"+month+"-0"+(date+6-today);
    }
    else {
      weekEndDate = year+"-"+month+"-"+(date+6-today);
    }

    return weekStartDate+" "+weekEndDate;
  }
}


// 일정 가져오기
function func_getSchedule(year, month, weekStartDate, weekEndDate){
  if(month<10){
    month="0"+month;
  }

  $.ajax({
    url:"/getSchedule",
    type: "post",
    dataType: "json",
    data:{weekStartDate:weekStartDate, weekEndDate:weekEndDate},
    success: function(data){
      if(data.length>0) {    // 해당하는 일정이 있는 경우
        $.each(data, function (idx,val) {
          var scheduleArray = val.split(",");
          if($("loginType")=="1"){
            var attenderCnt = scheduleArray[3].split(',').length;
            $(".scheduleArea").append("<div class='scheduleAreaDetail' id='"+scheduleArray[0]+"' "
                                    + " onclick='func_detail(this.id)'>"
                                    + "<div class='timeArea'>"
                                    + "<span class='time'>"+scheduleArray[1]+"</span>"
                                    + "<span class='time'> - </span>"
                                    + "<span class='time'>"+scheduleArray[2]+"</span></div>"
                                    + "<img class='smallImg' src='image/report/attender.png'/>"
                                    + "<span>"+attenderCnt+"</span>"
                                    + "<span> / </span>"
                                    + "<span class='import'>"+scheduleArray[4]+"</span></div>");
          } else {
            $(".scheduleArea").append("<div class='scheduleAreaDetail' id='"+scheduleArray[0]+"' "
                                    + " onclick='func_detail(id)'>"
                                    + "<div class='timeArea'>"
                                    + "<span class='time'>"+scheduleArray[1]+"</span>"
                                    + "<span class='time'> - </span>"
                                    + "<span class='time'>"+scheduleArray[2]+"</span></div>"
                                    + "<span class='dept'>"+scheduleArray[3]+" </span></div>"
                                    + "<span class='teacher'>"+scheduleArray[4]+" "+scheduleArray[5]+"</span>");
          }
        });
      }
      else{    // 해당하는 일정이 없는 경우
        $(".scheduleArea").append("<span>해당하는 일정이 없습니다.<span>");
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