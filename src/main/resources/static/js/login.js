$(function(){
  func_inputId();
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

  if (password==null || password=="") {
    $("#password").focus();
    $("#error").html("비밀번호를를 입력해 주세요.");
    return false;
  }

  return true;
  func_saveId(id);
  form.submit();
}

function func_inputId(){
  var saveid = func_getCookie("saveid");
  console.log(saveid);
  if(saveid !=""){
    $("input:checkbox[id='saveId']").prop("checked", true);
    $('#id').val(saveid);
  }
}

function func_saveId(id) {
  var date = new Date();
  var name = "saveId"
  if ($("#saveId").is(":checked")){
    date.setDate(date.getDate() + 7);
    document.cookie = name + "=" + escape(id) +  '; expires=' + date.toUTCString();
  }else{
    date.setDate(date.getDate() - 7);
    document.cookie = name + "=" + escape(id) +  '; expires=' + date.toUTCString();
  }
}

function func_getCookie(name) {
  var search = name + "=";
  if (document.cookie.length > 0) {
    offset = document.cookie.indexOf(search);
    console.log(offset);
    if (offset != -1) {
      offset += search.length;
      end = document.cookie.indexOf(";", offset);
      console.log(end);
      if (end == -1)
        end = document.cookie.length;
      return unescape(document.cookie.substring(offset, end));
    }
  }
  return "";
}

