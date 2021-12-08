$(function(){

  // 로그아웃 클릭시
  $("img#logoutBtn").click(function(){
    location.href="/login";
  });

  // 로그인한 아이디 가져오기
  $(".student_id").val("${session.loginUser.student_id}");

  /* 현재 날짜와 시각 구하기
  var today = new Date();

  var year = today.getFullYear();
  var month = ('0' + (today.getMonth() + 1)).slice(-2);
  var day = ('0' + today.getDate()).slice(-2);
  var todayDate = year.substring(2)+month+day;

  var hours = ('0' + today.getHours()).slice(-2);
  var minutes = ('0' + today.getMinutes()).slice(-2);
  var presentTime = hours + ':' + minutes; */

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
