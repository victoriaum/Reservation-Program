$(function(){
  // 메뉴 버튼 클릭시 설정
  $("#requestMenu").click(function(){
    location.href="/request";
  });
  $("#scheduleMenu").click(function(){
    location.href="/schedule";
  });
  $("#noticeMenu").click(function(){
    location.href="/notice";
  });
  $("#mypageMenu").click(function(){
    location.href="/mypage";
  });
});

