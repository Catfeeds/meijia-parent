$(function(){
    var secId = localStorage['sec_id'];
    var mobile = localStorage['sec_mobile'];
    //获取用户消息列表
    getSecInfo(secId,mobile);

  /*  $('.form_datetime-1').datetimepicker({
	format : 'yyyy-mm-dd hh:ii',
	todayBtn : true,
	todayHighlight : true,
	language : 'zh-CN',
	pickerPosition : 'top-left',
	minuteStep : 30,
	autoclose : true*/
})
}());

//获取用户消息列表
function getSecInfo(secId,mobile){
    $.ajax({
        type:"POST",
        url:siteAPIPath+"sec/get_secinfo.json",
        dataType:"json",
        cache:false,
        data:"sec_id="+secId+"&mobile="+mobile,
        success : onListSuccess,
        error : onListError
    });
}
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
    	/*alert(data);
        alert("目前无法获取用户消息列表，请稍后再试。");*/
    return;
  }
  var sec = data.data;
  if(sec==''){
	$("#moreInfo").css("display","none");
  }
  $("#name").val(sec.name);
  $("#nickName").val(sec.nick_name);
  $("#sex option[value="+sec.sex+"]").attr("selected",true);
  $("#birthDay").val(sec.birth_day);
  $("#cityName").val(sec.city_name);
  $("#secId").val(sec.id);
}
function onListError(data, status){
}
$("#mind_info_submit").bind("click",function(){
	  var secId = localStorage['sec_id'];
	  var name = $("#name").val();
	  var nickName = $("#nickName").val();
	  var sex = $("#sex").val();
	  var birthDay = $("#birthDay").val();
	  var cityName = $("#cityName").val();
	  
	  $.ajax({
      type : "POST",
      url  : siteAPIPath+"sec/post_secinfo.json",
      dataType: "json",
      cache : false,
      data : {"sec_id":secId,
              "name":name,
              "nick_name":nickName,
              "sex":sex,
              "birth_day":birthDay,
              "city_id":3,
              "head_img":"",
             },
      success : onSuccess,
      error : onError
  });
})
function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("亲，信息修改出现一些小问题，请稍后再试。");
    return;
  }
  var sec = data.data;
  /*//记录秘书的相关信息
  localStorage['sec_id'] = sec.id;
  localStorage['sec_mobile'] = sec.mobile;
  localStorage['sec_name'] = sec.name;
  localStorage['sec_sex'] = sec.sex;
  localStorage['sec_head_img'] = sec.head_img;
  localStorage['sec_birth_day'] = sec.birth_day;
  localStorage['sec_city_id'] = sec_city_id;
  localStorage['sec_city_name'] = sec_city_name;*/
  window.location.href="wx-mine-info.html";
}
function onError(data, status){
    console.log(data.msg);
    alert("修改个人信息失败");
}


