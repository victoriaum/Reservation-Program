$(function(){

})


// 계정 수정하기
function func_editAccount(){
  for(var i=0; i<$(".subInput").length; i++){
    if($(".subInput").val()==null && $(".subInput").val().trim()==""){
      $(".error").html("빈칸이 있으면 계정삭제가 불가합니다.");
      return false;
    }
  }

  var type = $("#type").val();
  if (type == '1') {
    teacher_id = $("#teacher_id").val();
    teacher_password = $("#teacher_password").val();
    teacher_name = $("#teacher_name").val();
    teacher_dept = $("#teacher_dept").val();
    teacher_position = $("#teacher_position").val();

    infoArray = [teacher_id, teacher_password, teacher_name, teacher_dept,
      teacher_position];
    func_delAjax(type, infoArray);
  }
  else if (type == '2') {
    student_id = $("#student_id").val();
    student_password = $("#student_password").val();
    student_name = $("#student_name").val();
    student_grade = $("#student_grade").val();

    infoArray = [student_id, student_password, student_name, student_grade];
    func_delAjax(type, infoArray);
  }
}


// 계정 삭제하기
function func_delAccount() {
  var type = $("#type").val();
  var infoArray, id;

  if (type == '1') {
    id = $("#teacher_id").val();
  }
  else if (type == '2') {
    id = $("#student_id").val();
  }

  $.ajax({
    url:"/admin/delAccount",
    type: "post",
    dataType:"json",
    data:{type:type,id:id},
    success: function(json) {
      if (json.result == 1) {    // 계정삭제 성공
        Swal.fire({
          title: 'Success!',
          icon: 'success',
          html: '계정이 성공적으로 삭제되었습니다.',
          showConfirmButton: false,
          timer: 1200
        })
        setTimeout(function() {}, 1300);
      } else {    // 계정삭제 실패
        Swal.fire({
          icon: 'error',
          title: 'Failed',
          showConfirmButton: false,
          timer: 1200
        })
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}



