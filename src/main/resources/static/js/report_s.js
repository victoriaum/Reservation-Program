$(function(){
  var url = document.location.href;
  var para = url.substr(url.indexOf("?")+1);
  var details = para.split("&");
  var dept_no = details[0].substr(details[0].indexOf("=")+1);
  var teacher_id = details[1].substr(details[1].indexOf("=")+1);

  var dept = "";
  if(dept_no==0){dept="0";}
  else if(dept_no==1){dept="외과";}
  else if(dept_no==2){dept="보철과";}
  else if(dept_no==3){dept="치주과";}
  else if(dept_no==4){dept="보존과";}
  else if(dept_no==5){dept="교정과";}

  // 뷰단 구성변경
  if(dept_no==0 && teacher_id==0){
    $(".firstArea").show();
    $(".secondArea").hide();
    $(".thirdArea").hide();
  }
  else if(dept_no!=0 && teacher_id==0){
    func_checkedDept(dept);
  }
  else if(dept_no!=0 && teacher_id!=0){
    func_checkedTeacher(dept,teacher_id);
    func_teacherInfo(teacher_id);
  }

  // 과 선택했을 때
  $(".dept").click(function() {
    location.href = '/report_s?dept_no='+$(this).attr("id")+'&teacher_id=0';
  });

  // 선생님 선택했을 때
  $(".teacher").click(function() {
    location.href = '/report_s?dept_no='+dept_no+'&teacher_id='+$(this).attr("id");
  });

  // 개설요청하기
  $(".openRequest").click(function(){
    func_openRequest(teacher_id);
  });
});


// dept 파라미터 값이 있는 경우
function func_checkedDept(dept) {
  $(".choosenArea").append("<span class='choice checkedChoice' id='checkedDept'>"
      + dept+"<img class='closeCheckedChose' id='deptOut' onclick='func_deptOut()' "
      + "src='image/close_white.png'/></span>");

  $(".firstArea").hide();
  $(".secondArea").show();
}

// dept, teacher 파라미터 값이 있는 경우
function func_checkedTeacher(dept, teacher_id){
  func_reportOkay(teacher_id);
  var student_id = $("#loginId").val();
  var teacherInfo = $("#teacherInfo").val();

  $(".choosenArea").append("<span class='choice checkedChoice' id='checkedDept'>"
      + dept+"<img class='closeCheckedChose' id='deptOut' onclick='func_deptOut()' "
      + "src='image/close_white.png'/></span>"
      + "<span class='choice checkedChoice' id='checkedTeacher'>"
      + teacherInfo+"<img class='closeCheckedChose' id='teacherOut' onclick='func_teacherOut("+dept+")' "
      + "src='image/close_white.png'/></span>");

  $(".secondArea").hide();
  $(".thirdArea").show();
}


// 선생님 정보가져오기
function func_teacherInfo(teacher_id) {
  $.ajax({
    url:"/report_s/teacherInfo",
    type: "post",
    dataType:"json",
    data:{teacher_id:teacher_id},
    success: function(json){
      var teacherInfo = json.teacherInfo.split(",")[0]+" "+json.teacherInfo.split(",")[1];
      $("#checkedTeacher").append(teacherInfo);
    },
    error: function(report, status, error){
      alert("code: "+report.status+"\n"+"message: "+report.responseText+"\n"+"error: "+error);
    }
  });
}


// 선택한 진료과 지우기
function func_deptOut(){
  location.href='/report_s?dept_no=0&teacher_id=0';
}

// 선택한 선생님 지우기
function func_teacherOut(dept){
  location.href='/report_s?dept_no='+dept+'&teacher_id=0';
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


