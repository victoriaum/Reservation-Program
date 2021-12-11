$(function(){

  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();

  // 과 선택했을 때
  $(".dept").click(function(){
    var checkedDept = $(this).text();

    $(".choosenArea").append("<span class='col-4 choice checkedChoice' id='checkedDept'>"
                              +checkedDept
                              +"<img class='closeCheckedChose' id='deptOut' "
                                  + "onclick='func_deptOut()'src='image/close_white.png'/>"
                              +"</span>")

    $(".firstArea").hide();
    $(".secondArea").show();

    $.ajax({
      url:"/getTeacher",
      type: "post",
      dataType:"json",
      data:{checkedDept:checkedDept},
      success: function(json){
        $.each(json.teacherList, function(idx, val) {
          var valArray = val.split(",");
          val = val.replace(","," ");
          $(".secondArea").append("<span class='choice teacher' id='"+valArray[0]+","+valArray[2]+"' "
                                  + "onclick='func_getSchedule(this.id)'>"
                                  + valArray[0]+" "+valArray[1]+"</span>")
        });
      },
      error: function(report, status, error){
        alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
      }
    });
  });

});


// 선생님 선택했을 때
function func_getSchedule(id){
  var idArray = id.split(",");
  $(".choosenArea").append("<span class='col-6 choice checkedChoice' id='checkedTeacher'>"
                            +idArray[0]
                            +"<img class='closeCheckedChose' id='teacherOut' "
      + "                           onclick='func_teacherOut()' src='image/close_white.png'/>"
                            +"</span>")

  $(".secondArea").hide();
  $(".thirdArea").show();

  $.ajax({
    url:"/getTeacherSchedule",
    type: "post",
    dataType:"json",
    data:{checkedTeacher:idArray[1]},
    success: function(json){
      if(json.scheduleList==""){    // 저장된 일정이 없는 경우
        $(".thirdArea").append("<div>정해진 일정이 없습니다.</div>");
      }
      else {    // 저장된 일정이 있는 경우
        $.each(json.scheduleList, function(idx, val) {
          var scheduleArray = val.split(",");
          var lastIndex = scheduleArray.length - 1;
          var subAreaDateArray = scheduleArray[1].substring(0, 4) + "년 "
              + scheduleArray[1].substring(4, 6) + "월 "
              + scheduleArray[1].substring(6) + "일";
          var attenderCnt = lastIndex - 4;

          $(".thirdArea").append(
              "<div class='subArea' id='" + scheduleArray[0] + "' onclick='func_report(this)'>"
              + "<span class='date'>" + subAreaDateArray + "</span><br>"
              + "<span class='time'>" + scheduleArray[2] + " - "
              + scheduleArray[3] + "</span><br>"
              + "<span class='space'><span id='attenderCnt'>" + attenderCnt + "</span> / "
              + scheduleArray[lastIndex] + "</span>"
              + "<img class='attenderImg' src='image/report/attender.png'/>"
              + "</div>");
        });
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


// 선택한 진료과 지우기
function func_deptOut(){
  $("#checkedDept").remove();
  $("#checkedTeacher").remove();

  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();

  $(".secondArea").html("");
  $(".thirdArea").html("");
}


// 선택한 선생님 지우기
function func_teacherOut(){
  $("#checkedTeacher").remove();

  $(".secondArea").show();
  $(".firstArea").hide();
  $(".thirdArea").hide();

  $(".thirdArea").html("");

}


// 검사요청하기
function func_report(subArea) {
  var id = subArea.id;

  if($(subArea).hasClass("smallWidth")){
    $(subArea).removeClass("smallWidth");
    $(subArea).next().remove();
  } else {
    $(subArea).addClass("smallWidth");
    $(subArea).after("<div class='reportBtnSpace'>"
                  + "<span type='button' class='reportBtn reportOkay' id='"+id+"' onclick='func_reportOkay(this)'>"
                  + "<img class='reportBtnImg' src='image/check_white.png'/>"
                  + "</span>"
                  + "<span type='button' class='reportBtn reportNo' id='"+id+"' onclick='func_reportNo(this)'>"
                  + "<img class='reportBtnImg' src='image/close_white.png'/>"
                  + "</span></div>");
  }
}


// 검사요청 okay 버튼 클릭시, 등록요청
function func_reportOkay(obj) {
  var login_id = $("#loginId").val();
  var schedule_no = Number(obj.id);

  $.ajax({
    url:"/sendReport",
    type: "post",
    dataType:"json",
    data:{schedule_no:schedule_no, login_id:login_id},
    success: function(json){
      if(json.result==1){    // 일정 저장성공
        alert("일정이 등록됐습니다.");
        var attenderCnt = $(obj).parent().prev().children('span.space').children('span#attenderCnt');
        var cnt = Number(attenderCnt.text());
        attenderCnt.html(cnt+1);
      }
      else if(json.result==0){    // 일정 저장실패패
        alert("이미 등록된 예약입니다.");
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


// 검사요청 close 버튼 클릭시, 등록취소
function func_reportNo(obj) {
  var login_id = $("#loginId").val();
  var schedule_no = Number(obj.id);

  $.ajax({
    url:"/cancelReport",
    type: "post",
    dataType:"json",
    data:{schedule_no:schedule_no, login_id:login_id},
    success: function(json){
      if(json.result==1){    // 일정 취소성공
        alert("일정이 취소됐습니다.");
        var attenderCnt = $(obj).parent().prev().children('span.space').children('span#attenderCnt');
        var cnt = Number(attenderCnt.text());
        attenderCnt.html(cnt-1);
      }
      else if(json.result==0){    // 일정 취소실패
        alert("등록되지 않은 예약으로 취소할 수 없습니다.");
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}



