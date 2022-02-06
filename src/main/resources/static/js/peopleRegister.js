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
              + " <select class='grade'><option selected>본1</option>"
              + " <option>본2</option>"
              + " <option>본3</option>"
              + " <option>본4</option></select>"
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
      + " <select class='grade'><option selected>본1</option>"
      + " <option>본2</option>"
      + " <option>본3</option>"
      + " <option>본4</option></select>"
      + " <input type='number' class='space2 id' placeholder='학번' />"
      + " <input type='text' class='space3 name' placeholder='이름' /></div>");
}


// RegisterForm 유효성검사
function func_peopleRegister(){
  var idAll = "";
  var nameAll = "";
  var gradeAll = "";
  var flag = true;

  $(".id").each(function() {
    var id = $(this).val();
    if(id==null || id==""){
      $("#error").html("빈칸이 없어야 저장이 가능합니다!");
      flag = false;
    } else {
      idAll += id+" ";
    }
  });

  $(".name").each(function() {
    var name = $(this).val();
    if(name==null || name==""){
      $("#error").html("빈칸이 없어야 저장이 가능합니다!");
      flag = false;
    } else {
      nameAll += name+" ";
    }
  });

  $(".grade").each(function() {
    var grade = $(this).val();
    gradeAll += grade+" ";
  });

  console.log(idAll);
  console.log(nameAll);
  console.log(gradeAll);

  if(!flag){
    return false;
  } else {
    $.ajax({
      url:"/admin/studentRegister",
      type: "post",
      dataType:"json",
      data:{idAll:idAll,nameAll:nameAll,gradeAll:gradeAll},
      success: function(json) {
        if (json.result == 1) {    // 학생 모두 등록 성공
          Swal.fire({
            title: 'Success!',
            icon: 'success',
            showConfirmButton: false,
            timer: 1200
          })
          setTimeout(function() {}, 1300);
        } else {    // 학생 일부/전체 등록 실패
          Swal.fire({
            title: '모두 등록이 실패했습니다!',
            icon: 'fail',
            showConfirmButton: true
          })
          setTimeout(function() {
            $("#error").html(json.result+"<br>위 학번을 가진 학생은 이미 등록되어 있습니다. 그 외의 학생은 모두 등록되었습니다.");
          }, 1300);
        }
      },
      error: function(report, status, error){
        alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
      }
    });
  }

}