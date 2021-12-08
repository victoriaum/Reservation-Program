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
      error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
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
              "<div class='subArea' id='" + scheduleArray[0] + "' onclick='func_request(this, this.id)'>"
              + "<span class='date'>" + subAreaDateArray + "</span><br>"
              + "<span class='time'>" + scheduleArray[2] + " - "
              + scheduleArray[3] + "</span><br>"
              + "<span class='space'>" + attenderCnt + " / "
              + scheduleArray[lastIndex] + "</span>"
              + "<img class='attenderImg' src='image/request/attender.png'/>"
              + "</div>");
        });
      }
    },
    error: function(request, status, error){
      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
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
function func_request(subArea, id) {
  if($(subArea).hasClass("smallWidth")){
    $(subArea).removeClass("smallWidth");
    $(subArea).next().remove();
  } else {
    $(subArea).addClass("smallWidth");
    $(subArea).after("<div class='requestBtnSpace'>"
                  + "<span type='button' class='requestBtn requestOkay' id='"+id+"' onclick='func_requestOkay(this.id})'>"
                  + "<img class='requestBtnImg' src='image/check_white.png'/>"
                  + "</span>"
                  + "<span type='button' class='requestBtn requestNo' onclick='func_requestNo(this)'>"
                  + "<img class='requestBtnImg' src='image/close_white.png'/>"
                  + "</span></div>");
  }
}


// 검사요청 close 버튼 클릭시
function func_requestNo(requestNo) {
  $(requestNo).parent().prev().click();
}


// 검사요청 okay 버튼 클릭시
function func_requestOkay(id) {
  var schedule_no = Number(id);


}