$(function(){
  /*$("#datepicker").datepicker({
    language: 'ko'
  });

  $('input.timepicker').timepicker({});
  $('.timepicker').timepicker({
    timeFormat: 'p h:mm',
    interval: 30,
    minTime: '9:00am',
    maxTime: '9:00pm',
    defaultTime: '11',
    startTime: '9:00',
    dynamic: false,
    dropdown: true,
    scrollbar: true
  });*/
});

// 일정을 클릭했을 때
function func_edit(obj, id){
  if($(obj).next().hasClass("editImg")){
    $(obj).next().remove();
    $(obj).removeClass("smallWidth");
  }
  else {
    $(obj).after("<img class='editImg' id='"+id+"' src='image/report/edit.png' "
        + "onclick='func_editSchedule("+id+")'/>");
    $(obj).addClass("smallWidth");
  }
}

// 일정 수정하기 이동
function func_editSchedule(id){
  location.href="/report_t/makeSchedule?no="+id;
}


// 일정 저장하기
function func_saveSchedule(){

  var form = document.saveScheduleForm;
  var date = $("#date").val();
  var startTime = $("#startTime").val();
  var endTime = $("#endTime").val();
  var space = $("#space").val();

  if(date==null || date==""){
    $("#date").focus();
    $(".error").html("날짜를 입력해 주세요.");
    return false;
  }

  if(startTime==null || startTime==""){
    $("#startTime").focus();
    $(".error").html("시작시간을 입력해 주세요.");
    return false;
  }

  if(endTime==null || endTime==""){
    $("#endTime").focus();
    $(".error").html("마감시간을 입력해 주세요.");
    return false;
  }

  if(space==null || space==""){
    $("#space").focus();
    $(".error").html("가능인원을 입력해 주세요.");
    return false;
  }

  if(startTime>=endTime){
    $("#startTime").focus();
    $(".error").html("시작시간이 마감시간보다 빠를 수 없습니다.");
    return false;
  }

  return true;
  form.submit();
}