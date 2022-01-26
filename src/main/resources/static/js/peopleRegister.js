$(function(){

  $("#studentCheck").click();

  // type에 따른 필드 변경
  $("#studentCheck").click(function(){
    $("#teacherCheck").removeClass("clickedCheck");
    $("#studentCheck").addClass("clickedCheck");
    func_studentField();
  });
  $("#teacherCheck").click(function(){
    $("#studentCheck").removeClass("clickedCheck");
    $("#teacherCheck").addClass("clickedCheck");
    func_teacherField();
  });

})

function func_studentField(){
  $(".helpArea").html("<span>※ 학번은 아이디와 초기 비밀번호로 입력됩니다.</span>");
  $(".inputArea").html("<div><img class='space1 minusBtn' th:src='@{image/admin/minus.png}'/>"
              + " <input type='text' class='space2' id='id' name='id' placeholder='학번' />"
              + " <input type='text' class='space3' id='name' name='name' placeholder='이름' /></div>"
              + " <div class='addBtn'><img class='/' th:src='@{image/admin/addPerson.png}'/></div>");
}

function func_teacherField(){
  $(".helpArea").html("<span>※ 학번은 아이디와 초기 비밀번호로 입력됩니다.</span>");
  $(".inputArea").html("<div><img class='space1 minusBtn' th:src='@{image/admin/minus.png}'/>"
      + " <input type='text' class='space2' id='id' name='id' placeholder='학번' />"
      + " <input type='text' class='space3' id='name' name='name' placeholder='이름' /></div>"
      + " <div class='addBtn'><img class='/' th:src='@{image/admin/addPerson.png}'/></div>");
}