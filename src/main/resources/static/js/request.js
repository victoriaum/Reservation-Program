$(function(){
  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();
  $(".hiddenTeacherId").hide();

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
          var valArray = val.split(",");
          val = val.replace(","," ");
          $(".secondArea").append("<span class='choice teacher'>"+valArray[0]+" "+valArray[1]+""
                                + "<span class='hiddenTeacherId'>"+valArray[2]+"</span></span>")
        });
      },
      error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
  });


  // 선생님 선택했을 때
  $(".hiddenTeacherId").click(function(){
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





