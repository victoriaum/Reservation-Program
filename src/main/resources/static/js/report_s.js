$(function(){
  // 파라미터 값에 따른 화면구성 변경하기
  var url = document.location.href;
  var para = url.substr(url.indexOf("?")+1);
  var details = para.split("&");
  var dept = details[0].substr(details[0].indexOf("=")+1);
  var teacher_id = details[1].substr(details[1].indexOf("=")+1);

  // 아무것도 선택하지 않은 경우
  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();

  // 과만 선택된 경우
  if(dept!="0" && teacher_id=="0"){
    func_checkedDept(dept);
  }

  // 선생님도 선택된 경우
  if(dept!="0" && teacher_id!="0"){
    func_checkedTeacher(dept,teacher_id);
  }

  // 과 선택했을 때
  $(".dept").click(function() {
    var dept = $(this).text();
    location.href = '/report_s?dept='+dept+'&teacher_id=0';
  });

  // 선생님 선택했을 때
  $(".teacher").click(function() {
    var teacher = $(this).text();
    var teacher_id =  val.split(",").get(1);
    location.href = '/report_s?dept='+dept+'&teacher_id='+teacher_id;
  });
});


// dept 파라미터 값이 있는 경우
function func_checkedDept(dept) {
  $(".choosenArea").append("<span class='choice checkedChoice' id='checkedDept'>"
      +dept
      +"<img class='closeCheckedChose' id='deptOut' "
      + "onclick='func_deptOut()' src='image/close_white.png'/>"
      +"</span>")

  $(".firstArea").hide();
  $(".secondArea").show();

  var teacherList = $("#teacherList").val();
  $.each(teacherList, function(idx, val) {
    var valArray = val.split(",");
    val = val.replace(","," ");
    console.log(valArray[0]+" "+valArray[1]+","+valArray[2]+","+valArray[3]);
    // id값 ex) "teacher_name teacher_position,teacher_id,request_students"
    $(".secondArea").append("<span class='choice teacher' id='"+valArray[0]+" "+valArray[1]+","+valArray[2]+","+valArray[3]+"'>"
        + valArray[0]+" "+valArray[1]+"</span>");
  });
}


// dept, teacher 파라미터 값이 있는 경우
function func_checkedTeacher(dept, teacher_id){
  var student_id = $("#loginId").val();
  var scheduleList = $("#scheduleList").val();

  $(".choosenArea").append("<span class='choice checkedChoice' id='checkedTeacher'>"
      +teacher_id
      +"<img class='closeCheckedChose' id='teacherOut' onclick='func_teacherOut()' src='image/close_white.png'/>"
      +"</span>")

  $(".secondArea").hide();
  $(".thirdArea").show();


  $.each(scheduleList, function(idx, val) {
    var attenderCnt;
    if(val.schedule_attender==""){
      attenderCnt = 0;
    } else {
      attenderCnt = val.schedule_attender.split(",").length;
    }

    var checked = "";

/*    var studentsArray;
    for(var i=2; i<idArray.length; i++){
      if(i==2){
        studentsArray=idArray[i];
      } else {
        studentsArray+=","+idArray[i];
      }

      if(student_id==idArray[i]){
        checked = "checked";
      }
    }*/

    $(".thirdArea").append(
        "<div class='subArea' id='" +val.schedule_no+ "' onclick='func_report(this)'>"
        + "<span class='date'>" + val.schedule_date + "</span><br>"
        + "<span class='time'>" + val.schedule_start + " - "
        + val.schedule_end + "</span><br>"
        + "<span class='space'><span id='attenderCnt'>" + attenderCnt
        + "</span> / " + val.schedule_space + "</span>"
        + "<img class='attenderImg' src='image/report/attender.png'/>"
        + "</div>");
  });
}



// 선택한 진료과 지우기
function func_deptOut(){
  $("#checkedDept").remove();
  $("#checkedTeacher").remove();

  $(".firstArea").show();
  $(".secondArea").hide();
  $(".thirdArea").hide();

  $(".secondArea").html("");
  $(".thirdArea").html("");
}


// 선택한 선생님 지우기
function func_teacherOut(){
  $("#checkedTeacher").remove();

  $(".secondArea").show();
  $(".firstArea").hide();
  $(".thirdArea").hide();

  $(".thirdArea").html("");
}


// 검사요청하기
function func_report(subArea) {
  var id = subArea.id;

  if($(subArea).hasClass("smallWidth")){
    $(subArea).removeClass("smallWidth");
    $(subArea).next().remove();
  } else {
    $(subArea).addClass("smallWidth");
    $(subArea).after("<div class='reportBtnSpace'>"
                  + "<span type='button' class='reportBtn reportOkay' id='"+id+"' onclick='func_reportOkay(this)'>"
                  + "<img class='reportBtnImg' src='image/check_white.png'/>"
                  + "</span>"
                  + "<span type='button' class='reportBtn reportNo' id='"+id+"' onclick='func_reportNo(this)'>"
                  + "<img class='reportBtnImg' src='image/close_white.png'/>"
                  + "</span></div>");
  }
}


// 검사요청 okay 버튼 클릭시, 등록요청
function func_reportOkay(obj) {
  var login_id = $("#loginId").val();
  var schedule_no = Number(obj.id);

  $.ajax({
    url:"/report_s/requestReport",
    type: "post",
    dataType:"json",
    data:{schedule_no:schedule_no, login_id:login_id},
    success: function(json){
      if(json.result==1){    // 일정 저장성공
        Swal.fire({
          title: 'Success!',
          icon: 'success',
          showConfirmButton: false,
          timer: 1200
        })
        setTimeout(function() {
          location.href="/report_s";
        }, 1300);
        var attenderCnt = $(obj).parent().prev().children('span.space').children('span#attenderCnt');
        var cnt = Number(attenderCnt.text());
        attenderCnt.html(cnt+1);
      }
      else if(json.result==0){    // 일정 저장실패
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          showConfirmButton: false,
          timer: 1200
        })
        setTimeout(function() {
          location.href="/report_s";
        }, 1300);
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


// 검사요청 close 버튼 클릭시, 등록취소
function func_reportNo(obj) {
  var login_id = $("#loginId").val();
  var schedule_no = Number(obj.id);

  $.ajax({
    url:"/report_s/cancelReport",
    type: "post",
    dataType:"json",
    data:{schedule_no:schedule_no, login_id:login_id},
    success: function(json){
      if(json.result==1){    // 일정 취소성공
        Swal.fire({
          title: 'Success!',
          icon: 'success',
          showConfirmButton: false,
          timer: 1200
        })
        var attenderCnt = $(obj).parent().prev().children('span.space').children('span#attenderCnt');
        var cnt = Number(attenderCnt.text());
        attenderCnt.html(cnt-1);
      }
      else if(json.result==0){    // 일정 취소실패
        Swal.fire({
          icon: 'error',
          title: 'Failed',
          html: '등록되지 않은 예약으로 취소할 수 없습니다!'
        })
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}



// 일정 개설 요청하기
function func_openRequest(id){
  $.ajax({
    url:"/report_s/openRequest",
    type: "post",
    data: {"teacher_id":id},
    dataType:"json",
    success: function(json){
      if(json.result==1){    // 요청성공
        Swal.fire({
          title: 'Success!',
          icon: 'success',
          showConfirmButton: false,
          timer: 1200
        })
        setTimeout(function() {
          $(".thirdArea").html("<div class='noSchedule'>일정 개설이 요청됐습니다.<br>요청 취소를 원하시면 아래 취소 버튼을 눌러주세요."
              + "<button class='btn noScheduleBtn revokeOpenRequest' id='"+id+"' onclick='func_revokeOpenRequest(this.id)'>일정 개설 취소하기</button></div>");
        }, 1300);
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


// 일정 개설 요청취소하기
function func_revokeOpenRequest(id){
  $.ajax({
    url:"/report_s/revokeOpenRequest",
    type: "post",
    data: {"teacher_id":id},
    dataType:"json",
    success: function(json){
      if(json.result==1){    // 요청 취소성공
        Swal.fire({
          title: 'Success!',
          icon: 'success',
          showConfirmButton: false,
          timer: 1200
        })
        setTimeout(function() {
          $(".thirdArea").html("<div class='noSchedule'>정해진 일정이 없습니다.<br>일정 개설을 요청하시겠습니까?"
              + "<button class='btn noScheduleBtn openRequest' id='"+id+"' onclick='func_openRequest(this.id)'>일정 개설 요청하기</button></div>");
        }, 1300);
      }
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


