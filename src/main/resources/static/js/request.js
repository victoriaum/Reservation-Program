$(function(){
  func_moveTo('/getDept');  // 등록된 진료과 가져오기


  $(".dept").click(function(e){
    $(".dept").addClass("checkedDept");
    e.addClass("checkedDept");
  });

});







