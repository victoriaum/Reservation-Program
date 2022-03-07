$(function(){

})


function func_editAccount(){
  var type = $("#type").val();

}

function func_delAccount(){
  if($("input").val()==null && $("input").val().trim()==""){
    $(".error").innerHTML("빈칸이 있으면 계정삭제가 불가합니다.");
    return false;
  }

  var type = $("#type").val();
  var teacherDto = new teacherDto;
  var studentDto = new studentDto;

  if(type=='1'){
    teacher_id = $("#teacher_id").val();
    teacher_password = $("#teacher_password").val();
    teacher_name = $("#teacher_name").val();
    teacher_dept = $("#teacher_dept").val();
    teacher_position = $("#teacher_position").val();

    var teacherArray = [teacher_id,teacher_password,teacher_name,teacher_dept,teacher_position];
  }
  else if(type=='2'){
    student_id = $("#student_id").val();
    student_password = $("#student_password").val();
    student_name = $("#student_name").val();
    student_grade = $("#student_grade").val();

    var studentArray = [student_id,student_password,student_name,student_grade];
  }

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
          icon: 'error',
          title: 'Failed',
          html: json.result+'<br>위 학번을 가진 학생은 이미 있습니다. <br>수정후 다시 시도해주세요!'
        })
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}