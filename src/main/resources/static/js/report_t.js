$(function(){
  $(".datepicker").datepicker({
    language: 'ko'
  });

  $("#makeSchedule").click(function(){
    location.href="/makeSchedule";
  });
});

// 일정을 클릭했을 때
function func_edit(obj){
  if($(obj).next().hasClass("editImg")){
    $(obj).next().remove();
    $(obj).removeClass("smallWidth");
  }
  else {
    $(obj).after("<img class='editImg' id='"+$(obj.id)+"' src='image/report/edit.png' "
        + "onclick='func_editSchedule(this.id)'/>");
    $(obj).addClass("smallWidth");
  }
}

// 일정 수정하기 이동
function func_editSchedule(id){
  location.href="/makeSchedule?no"+id;
}