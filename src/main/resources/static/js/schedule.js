$(function(){
  var todayDate = new Date();
  var year = todayDate.getFullYear();
  var month = todayDate.getMonth()+1;
  var date = todayDate.getDate();

  // 초기값 설정
  func_inputDate(year,month);
  var weekcntArray = func_weekNow(year, month, date).split(" ");
  func_weekBtn(Number(weekcntArray[0]),Number(weekcntArray[1]));
  var dateArray = func_calculatePeriodDate(year, month, Number(weekcntArray[0]), Number(weekcntArray[1])).split(" ");
  func_getSchedule(dateArray[0], dateArray[1]);

  var selectedYear = $("#year").val(year).prop("selected",true);
  var selectedMonth = $("#month").val(year).prop("selected",true);
  var selectedWeek = $(".checkedWeekNo").val();

  // dateAreaInput을 변경하는 경우
  $(".dateAreaInput").change(function(){
    $(".scheduleCnt").html("");
    $(".scheduleArea").html("");

    var checkedWeekNo = Number($(".checkedWeekNo").text());
    func_inputDate(year,month);
    var weekcntArray = func_weekNow(year, month, date).split(" ");
    func_weekBtn(checkedWeekNo,Number(weekcntArray[1]));
    var dateArray = func_calculatePeriodDate(year, month, checkedWeekNo, Number(weekcntArray[1])).split(" ");
    func_getSchedule(dateArray[0], dateArray[1]);
  });

  // week을 변경하는 경우
  $(".weekNo").click(function(){
    $(".scheduleCnt").html("");
    $(".scheduleArea").html("");
    $(".weekNo").removeClass("checkedWeekNo");
    $(this).addClass("checkedWeekNo");

    var checkedWeekNo = Number($(".checkedWeekNo").text());
    func_inputDate(year,month);
    var weekcntArray = func_weekNow(year, month, date).split(" ");
    func_weekBtn(checkedWeekNo,Number(weekcntArray[1]));
    var dateArray = func_calculatePeriodDate(year, month, checkedWeekNo, Number(weekcntArray[1])).split(" ");
    func_getSchedule(dateArray[0], dateArray[1]);
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
  $("#year").html("");
  $("#month").html("");

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


// 일자로 현재 주차 계산하기
function func_weekNow(year,month,date) {
  var lastdate = new Date(year, month - 1, 0).getDate();
  var firstdateDay = new Date(year, month - 1, 0).getDay();
  var lastdateDay = new Date(year, month - 1, lastdate).getDay();
  var today = new Date(year, month - 1, date).getDay();

  var weekcnt = Math.floor(lastdate / 7);
  if (lastdate % 7 != 0) {
    if (7 - firstdateDay < lastdate % 7) {
      weekcnt = weekcnt + 2;
    } else if (7 - firstdateDay == lastdate % 7) {
      weekcnt = weekcnt + 1;
    }
  }

  var weekcntToday;
  if (7 - firstdateDay < date % 7) {
    weekcntToday = Math.floor(date / 7 + 2);
  } else if (7 - firstdateDay == date % 7) {
    weekcntToday = Math.floor(date / 7 + 1);
  }

  return weekcntToday + " " + weekcnt;
}


// weekBtn 만들기
function func_weekBtn(checkedWeekNo, weekcnt){
  $("#week").html("");
  for(var i=1; i<weekcnt+1; i++){
    if(i==checkedWeekNo){
      $("#week").append("<span class='col weekNo checkedWeekNo' value='"+i+"'>"+i+"</span>");
    } else {
      $("#week").append("<span class='col weekNo' value='"+i+"'>"+i+"</span>");
    }
  }
}


// 주차 시작일, 마지막날 계산하기
function func_calculatePeriodDate(year,month,checkedWeekNo, weekcnt){
  console.log("func_calculatePeriodDate:"+checkedWeekNo+weekcnt);
  var date = 1+7*(checkedWeekNo-1);
  if(date>lastdate){
    date=lastdate;
  }

  var lastdate = new Date(year, month - 1, 0).getDate();
  var firstdateDay = new Date(year, month - 1, 0).getDay();
  var lastdateDay = new Date(year, month - 1, lastdate).getDay();
  var today = new Date(year, month - 1, date).getDay();

  if(month<10){
    month="0"+month;
  }

  var weekStartDate, weekEndDate;

  if(checkedWeekNo==1){
    weekStartDate = year+"-"+month+"-01";
    weekEndDate = year+"-"+month+"-0"+(7-firstdateDay);

  } else if(weekcnt==checkedWeekNo){
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
function func_getSchedule(startDate, endDate){
  console.log("func_getSchedule:"+startDate+endDate);
  $.ajax({
    url:"/getSchedule",
    type: "post",
    dataType: "json",
    data:{startDate:startDate, endDate:endDate},
    success: function(data){
      var cnt = data.scheduleList.length;
      $("#scheduleCnt").html("총 "+cnt+"개의 일정");

      if(cnt>0) {    // 해당하는 일정이 있는 경우
        $.each(data.scheduleList, function (idx,val) {
          var scheduleArray = val.split(",");
          var dateArray = scheduleArray[1].split("-");
          var date = Number(dateArray[1])+"/"+dateArray[2];

          if($("#loginType").val()=="1"){
            var attenderCnt = scheduleArray[4].split(',').length;
            if(attenderCnt==1 && scheduleArray[4].split(',')[0]==""){
              attenderCnt=0;
            }

            $(".scheduleArea").append("<div class='scheduleDetail' id='"+scheduleArray[0]+"' "
                                    + " onclick='func_detail(this.id)'>"
                                    + "<span class='date'>"+date+"&nbsp;&nbsp;&nbsp;&nbsp;</span>"
                                    + "<span class='time'>"+scheduleArray[2]+"&nbsp;-&nbsp;"+scheduleArray[3]+"</span>"
                                    + "<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>"
                                    + "<img class='smallImg' src='image/report/attender.png'/>"
                                    + "<span> "+attenderCnt+"&nbsp;/&nbsp;</span>"
                                    + "<span class=''>"+scheduleArray[5]+"</span></div>");
          }
          else {
            $(".scheduleArea").append("<div class='scheduleDetail' id='"+scheduleArray[0]+"' "
                                    + " onclick='func_detail(id)'>"
                                    + "<span class='date'>"+date+"&nbsp;&nbsp;&nbsp;&nbsp;</span>"
                                    + "<span class='time'>"+scheduleArray[2]+"&nbsp;-&nbsp;"+scheduleArray[3]+"</span>"
                                    + "<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>"
                                    + "<span>"+scheduleArray[4]+" </span></div>"
                                    + "<span>"+scheduleArray[5]+"&nbsp;"+scheduleArray[6]+"</span>");
          }
        });
      }
      else{    // 해당하는 일정이 없는 경우
        $("#scheduleCnt").html("일정이 없습니다.");
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