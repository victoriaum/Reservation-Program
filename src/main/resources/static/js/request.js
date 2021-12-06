$(function(){
  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();

  // 과 선택했을 때
  $(".dept").click(function(){
    var checkedDept = $(this).text();

    $(".choosenArea").append("<span class='choice checkedChoice'  id='checkedDept'>"+checkedDept+"</span>")

    $(".firstArea").hide();
    $(".secondArea").show();

    $.ajax({
      url:"/getTeacher",
      type: "post",
      dataType:"json",
      data:{checkedDept:checkedDept},
      success: function(json){
        $.each(json.teacherList, function(idx, val) {
          val.replace(","," ");
          $(".secondArea").append("<span class='choice teacher'>"+val+"</span>")
        });
      },
      error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
  });


  // 선생님 선택했을 때
  $(".teacher").click(function(){
    var checkedTeacher = $(this).text();

    $(".choosenArea").append("<span class='choice checkedChoice'  id='checkedTeacher'>"+checkedTeacher+"</span>")

    $(".secondArea").hide();
    $(".thirdArea").show();

    $.ajax({
      url:"/getTeacherSchedule",
      type: "post",
      dataType:"json",
      data:{checkedDept:checkedDept, checkedTeacher:checkedTeacher},
      success: function(json){
        $.each(json.scheduleList, function(idx, val) {
          val.replace(","," ");
        });
      },
      error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
  });

});





