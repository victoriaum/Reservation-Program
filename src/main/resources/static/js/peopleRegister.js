$(function(){
  func_studentBtn();

})



// student 클릭시
function func_studentBtn(){
  $("#teacherCheck").removeClass("clickedCheck");
  $("#studentCheck").addClass("clickedCheck");
  func_studentField();
}


// teacher 클릭시
function func_teacherBtn(){
  $("#studentCheck").removeClass("clickedCheck");
  $("#teacherCheck").addClass("clickedCheck");
  func_teacherField();
}


// student 필드 생성
function func_studentField(){
  $(".helpArea").html("<span>※ 학번은 아이디와 초기 비밀번호로 입력됩니다.</span>");
  $(".inputArea").html("<div><img class='space1 minusBtn' src='image/admin/minus.png' onclick='func_minusStudent(this)'/>"
              + " <input type='number' class='space2 id' placeholder='학번' />"
              + " <input type='text' class='space3 name' placeholder='이름' /></div>");
}


// teacher 필드 생성
function func_teacherField(){

}


// 학생 필드 입력줄 한 개 삭제
function func_minusStudent(obj){
  $(obj).parent().remove();
}


// 학생 필드 입력줄 한 개 추가
function func_addStudent(obj){
  $(".inputArea").append("<div><img class='space1 minusBtn' src='image/admin/minus.png' onclick='func_minusStudent(this)'/>"
      + " <input type='number' class='space2 id' placeholder='학번' />"
      + " <input type='text' class='space3 name' placeholder='이름' /></div>");
}


// RegisterForm 유효성검사
function func_peopleRegister(){
  var form = document.peopleResiterForm;
  var id = $(".id").val();
  var name = $(".name").val();

  if(id==null || id=="" || id=="학번" || name==null || name=="" || name=="이름"){
    $("#error").html("빈칸이 없어야 저장이 가능합니다!");
    return false;
  }

  var count = $(".id").length;

  $("form").append("<>");

  return true;
  form.submit();
}