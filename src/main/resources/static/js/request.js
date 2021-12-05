$(function(){
  func_getDept(); // 등록된 진료과 가져오기

  $(".dept").click(function(e){
    $(".dept").addClass("checkedDept");
    e.addClass("checkedDept");
  });

});


function func_getDept(){
  $.ajax({
    url:'/getDept',
    type:'POST',
    dataType:'json',
    success:function(data){
      alert(data.deptList);
      $.each(data.deptList, function(index){
        $("#requestDepartment").append("<span class='choice dept'>data.deptList</span>")
      });
    },error:function(){
      alert("func_getDept() error 발생");
    }
  });

}






