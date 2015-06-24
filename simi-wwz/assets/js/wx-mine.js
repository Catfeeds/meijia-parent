$(function(){
   
    var secMobile = localStorage['sec_mobile'];
    var secId = localStorage['sec_id'];

    //获取秘书的相关信息
    $.ajax({
        type : "POST",
        url  : siteAPIPath+"sec/get_secinfo.json",
        dataType: "json",
        cache : false,
        data : {"mobile":secMobile,"sec_id":secId},
        success : onSecInfoSuccess,
        error : onSecInfoError
    });
   
}());

function onSecInfoSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("获取账户信息出现一些问题，请稍后再试。");
    return;
  }
  var sec  = data.data;
  if(sec.nick_name !=' ' && sec.nick_name !=null){
	  $("#user_phone").text(sec.nick_name);
  }else{
	  $("#user_phone").text(sec.mobile);
  }
  var yuan = data.data.rest_money+'元';
  $("#user_money").text("0元");
}

function onSecInfoError(data, status){
    //console.log(data.msg);
    alert("获取账户信息出现一些问题，请稍后再试。(5)");
}