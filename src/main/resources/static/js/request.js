$(function(){

  $(".secondArea").hide();
  $(".thirdArea").hide();

  $(".dept").click(function(){
    if($(this).hasClass("checkedDept")){
      $(this).removeClass("checkedDDept");
      $(".secondArea").hide();
    }
    else {
      $("#checkedDept").html($(this).val());
      var form = document.selected;
      form.submit();
      $(".secondArea").show();
      $(".dept").removeClass("checkedDDept");
      $(this).addClass("checkedDDept");
    }
  });

});





