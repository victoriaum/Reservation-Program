$(function(){

  // 로그아웃 클릭시
  $("img#logoutBtn").click(function(){
    location.href="/login";
  });

  // 로그인한 아이디 가져오기
  $(".student_id").val("${session.loginUser.student_id}");

  // 메뉴 아이콘 클릭시
  $(".titleIconMenu").click(function(){

  });

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
