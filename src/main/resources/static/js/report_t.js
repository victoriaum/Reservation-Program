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