$(function(){
  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();


  // 과 선택했을 때
  $(".dept").click(function(){
    var checkedDept = $(this).text();

    $(".choosenArea").append("<span class='col-4 choice checkedChoice' id='checkedDept'>"+checkedDept+"</span>")

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
          $(".secondArea").append("<span class='choice teacher' id='"+valArray[0]+","+valArray[2]+"' onclick='func_getSchedule(this.id)'>"
                                  +valArray[0]+" "+valArray[1]+"</span>")
        });
      },
      error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
  });

});


// 선생님 선택했을 때
function func_getSchedule(id){
  var idArray = id.split(",");
  $(".choosenArea").append("<span class='col-6 choice checkedChoice'>"+idArray[0]+"</span>")

  $(".secondArea").hide();
  $(".thirdArea").show();

  $.ajax({
    url:"/getTeacherSchedule",
    type: "post",
    dataType:"json",
    data:{checkedTeacher:idArray[1]},
    success: function(json){
      $.each(json.scheduleList, function(idx, val) {
        var scheduleArray = val.split(",");
        var subAreaTitleArray = scheduleArray[1].substring(0,4)+"년 "+scheduleArray[1].substring(4,6)+"월 "+scheduleArray[1].substring(6)+"일";
        var attenderCnt = scheduleArray[4].split(",").length;
        console.log(attenderCnt);
        $(".thirdArea").append("<div class='subArea' id='"+scheduleArray[0]+"' onclick='func_doResiger(this.id)'>"
                              +"<span class='subAreaTitle'>"+subAreaTitleArray+"</span><br>"
                              +"<span>"+scheduleArray[2]+" - "+scheduleArray[3]+"</span><br>"
                              +"<img class='attenderImg' src='image/request/attender.png'/>"
                              +"<span>"+attenderCnt+" / "+scheduleArray[5]+"</span>"
                              +"</div>")
      });
    },
    error: function(request, status, error){
      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });
}



