$(function(){
  var todayDate = new Date();
  var year = todayDate.getFullYear();
  var month = todayDate.getMonth()+1;
  var date = todayDate.getDate();

  if(month<10){
    month="0"+month;
  }
  if(date<10){
    date="0"+month;
  }

  func_getTodaySchedule(year+"-"+month+"-"+date);

  $(".moreNotice").click(function(){
    location.href="/notice";
  });
  $(".moreSchedule").click(function(){
    location.href="/schedule";
  });
});


// 오늘 일정 가져오기
function func_getTodaySchedule(todayDate){
  console.log(todayDate)
  $.ajax({
    url:"/getTodaySchedule",
    type: "post",
    dataType: "json",
    data:{todayDate:todayDate},
    success: function(data){
      var cnt = data.scheduleList.length;

      if(cnt>0) {    // 해당하는 일정이 있는 경우
        $.each(data.scheduleList, function (idx,val) {
          var scheduleArray = val.split(",");

          if($("#loginType").val()=="1"){
            var attenderCnt = scheduleArray[3].split(',').length;
            if(attenderCnt==1 && scheduleArray[3].split(',')[0]==""){
              attenderCnt=0;
            }

            $(".indexSchedule").append("<p class='indexContentsDetail' value='"+scheduleArray[0]+"'> "
                + "<span class='indexContentsDetailInfo2'>"+scheduleArray[1]+"</span>"
                + "<span class='noticeSubject'>검사요청 인원: "
                + "<span class='import'>"+attenderCnt+"</span>명</span></p>");
          }
          else {
            $(".indexSchedule").append("<p class='indexContentsDetail' value='"+scheduleArray[0]+"'> "
                + "<span class='indexContentsDetailInfo2'>"+scheduleArray[1]+"</span>"
                + "<span class='noticeSubject'>"
                + "<span>"+scheduleArray[2]+" "+scheduleArray[3]+" "+scheduleArray[4]+"</span></span></p>");
          }
        });
      }
      else{    // 해당하는 일정이 없는 경우
        $(".indexSchedule").html("<p class='indexContentsDetail'> "
            + "<span class='noticeSubject'>오늘 일정은 없습니다.</span></p>");
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}