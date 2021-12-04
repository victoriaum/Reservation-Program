$(function(){
 // 로그아웃 클릭시
  $("img#logoutBtn").click(function(){
    location.href="/login";
  });
});


// 이동할 action 기록시 이동
function func_moveTo(action) {
  var form = document.moveForm;
  $("#moveForm").attr("action", action);
  $("#moveForm").attr("method", "POST");
  form.submit();
}
// 클릭시 이동
function func_move(e) {
  var form = document.moveForm;
  $("#moveForm").attr("action", "/"+e.id);
  $("#moveForm").attr("method", "POST");
  form.submit();
}