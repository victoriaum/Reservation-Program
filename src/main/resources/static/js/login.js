$(function(){
  func_inputId("saveId");
});

// 로그인 유효성 검사
function func_login(){
  var form = document.loginForm;
  var id = $("#id").val();
  var password = $("#password").val();

  if(id==null || id=="" || id=="아이디"){
    $("#id").focus();
    $("#error").html("아이디를 입력해 주세요.");
    return false;
  }
  else if (password==null || password=="") {
    $("#password").focus();
    $("#error").html("비밀번호를를 입력해 주세요.");
    return false;
  }
  else if($("input:checkbox[id='saveId']").prop("checked")){
    func_setCookie("saveId",id,7);
  } else {
    func_deleteCookie("saveId");
  }

  return true;
  form.submit();
}

// 저장된 아이디 넣기
function func_inputId(name){
  var saveid = func_getCookie(name);
  if(saveid !=""){
    $("input:checkbox[id='saveId']").prop("checked", true);
    $('#id').val(saveid);
  }
}

// 쿠키 생성
function func_setCookie(name, value, exdays){
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + exdays);
  var value = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
  document.cookie = name + "=" + value;
}

// 쿠키 삭제
function func_deleteCookie(name){
  var expireDate = new Date();
  expireDate.setDate(expireDate.getDate() - 1);
  document.cookie = name + "= " + "; expires=" + expireDate.toGMTString();
}

// 쿠키 가져오기
function func_getCookie(name) {
  cookieName = name + "=";
  var cookieData = document.cookie;
  var start = cookieData.indexOf(name);
  var value = "";
  if(start != -1){
    start += name.length;
    var end = cookieData.indexOf(";", start);
    if(end == -1)end = cookieData.length;
    value = cookieData.substring(start+1, end);
  }
  return unescape(value);
}

