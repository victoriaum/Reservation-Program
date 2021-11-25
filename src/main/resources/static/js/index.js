$(function(){
  $("div#result").hide();

  // 로그인 클릭시
  $("div#result").click(function(){
    loginCheck();
  });

  // 로그인 실패문구 출력
  if("${loginuser}"==null){
    $("div#result").html("일치하는 회원이 없습니다. 다시 로그인해주세요!");
    $("div#result").show();
  }
});


// 로그인 유효성 검사
function loginCheck() {

  let frm = document.loginForm;

  // 아이디가 입력이 안된 경우
  if($("input#idInput").val().trim()==null || $("input#idInput").val().trim()=="") {
    $("div#result").show();
    $("div#result").html("아이디를 입력해주세요!");
    return false;
  }

  // 비밀번호가 입력이 안된 경우
  if($("input#pwdInput").val().trim()==null || $("input#pwdInput").val().trim()=="") {
    $("div#result").show();
    $("div#result").html("비밀번호를 입력해주세요!");
    return false;
  }

  frm.action="<%=ctxPath%>/login";
  frm.method="POST";
  frm.submit();

}