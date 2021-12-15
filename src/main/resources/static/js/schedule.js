$(function(){
  // 현재 날짜 구하기
  var today = new Date();
  var year = today.getFullYear();
  var month = today.getMonth()+1;
  var date = today.getDate();

  func_dateSetting(year,month,date);

  /* Url Hash Navigation */
  $('.owl-carousel').owlCarousel({
    items:4,
    loop:false,
    center:true,
    margin:10,
    URLhashListener:true,
    autoplayHoverPause:false,
    startPosition: 'URLHash'
  })

  $(".owl-dots").hide();

  location.hash = $("#todayDate").val();





});


// 초기 dateArea 값 지정
function func_dateSetting(year,month,date){

  // 현재 년도 기준으로 작년, 올해 보여줌.
  for(var i=year-1; i<year+2; i++){
    $("#year").append("<option value='"+i+"'>"+i+"</option>");
  }
  $("#year").val(year).prop("selected",true);

  const monthArray = ['January','February','March','April','May','June','July','August','September','October','November','December'];

  for(var i=0; i<monthArray.length; i++){
    $("#month").append("<option value='"+(i+1)+"'>"+monthArray[i]+"</option>");
  }
  $("#month").val(month).prop("selected",true);

}


/*// owlCarousel 값 넣기
function func_dateDetail(year,month){
  var dayCnt = new Date(year, month, 0).getDate();

  for (var i=1; i<dayCnt+1; i++){
    var day = new Date(year, month-1, i);
    const dayArray = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
    var week = dayArray[day.getDay()];

    var month = ('0' + month).slice(-2);

    if(i<10){
      $(".owl-item").append("<div class='item' data-hash='"+year+"-"+month+"-0"+i+"'>"
          + " <div class='dateDetail'>"
          + " <span class='day'>"+week+"</span>"
          + " <span>"+i+"</span>"
          + " </div></div>");
    } else {
      $(".owl-item").append("<div class='item' data-hash='"+year+"-"+month+"-"+i+"'>"
          + " <div class='dateDetail'>"
          + " <span class='day'>"+week+"</span>"
          + " <span>"+i+"</span>"
          + " </div></div>");
    }

  }
}*/


/*  // Listen to owl events:
  owl.on('changed.owl.carousel', function(event) {
    location.hash = 'slide' + event.property.value;
  })*/