$(function(){
  // 로그아웃 클릭시
  $("img#logoutBtn").click(function(){
    location.href="/login";
  });
});


// 보류
function func_move(e) {
  var form = document.moveForm;
  $("#moveForm").attr("action", "/"+e.id);
  $("#moveForm").attr("method", "POST");

  form.submit();
}