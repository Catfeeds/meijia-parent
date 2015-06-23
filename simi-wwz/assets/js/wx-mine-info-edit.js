$(function() {
	var secId = localStorage['sec_id'];
	var mobile = localStorage['sec_mobile'];
	// 获取用户消息列表
	getSecInfo(secId, mobile);
	
	getCityList();

	$('.form_datetime-1').datetimepicker({
		format : 'yyyy-mm-dd ',
		todayBtn : true,
		todayHighlight : true,
		language : 'zh-CN',
		pickerPosition : 'top-left',
		minuteStep : 30,
		autoclose : true
	});
}());

// 获取秘书信息
function getSecInfo(secId, mobile) {
	$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/get_secinfo.json",
		dataType : "json",
		cache : false,
		data : "sec_id=" + secId + "&mobile=" + mobile,
		success : onListSuccess,
		error : onListError
	});
}
function onListSuccess(data, status) {
	if (data.status != "0") {
		if (data.status == "999")
			alert(data.msg);
		else
			 alert(data); alert("目前无法获取用户消息列表，请稍后再试。");
		return;
	}
	var sec = data.data;
	if (sec == '') {
		$("#moreInfo").css("display", "none");
	}
	$("#name").val(sec.name);
	$("#nickName").val(sec.nick_name);
	$("#sex option[value=" + sec.sex + "]").attr("selected", true);
	$("#birthDay").val(sec.birth_day);
	$("#cityName").val(sec.city_name);
	$("#secId").val(sec.id);
}
function onListError(data, status) {
}
$("#mind_info_submit").bind("click", function() {
	var secId = localStorage['sec_id'];
	var name = $("#name").val();
	var nickName = $("#nickName").val();
	var sex = $("#sex").val();
	var birthDay = $("#birthDay").val();
	var cityId = $("#city_name").val();

	$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/post_secinfo.json",
		dataType : "json",
		cache : false,
		data : {
			"sec_id" : secId,
			"name" : name,
			"nick_name" : nickName,
			"sex" : sex,
			"birth_day" : birthDay,
			"city_id" : cityId,
			"head_img" : "",
		},
		success : onSuccess,
		error : onError
	});
})
function onSuccess(data, status) {
	if (data.status != "0") {
		if (data.status == "999")
			alert(data.msg);
		else
			alert("亲，信息修改出现一些小问题，请稍后再试。");
		return;
	}
	window.location.href = "wx-mine-info.html";
}
function onError(data, status) {
	console.log(data.msg);
	alert("修改个人信息失败");
}
//获取用户消息列表
function getCityList() {
	$.ajax({
		type : "GET",
		url : siteAPIPath + "city/get_list.json?",
		dataType : "json",
		cache : false,
		success : onCityListSuccess,
		error : onCityListError
	});
}
function onCityListSuccess(data, status) {
	if (data.status != "0") {
		if (data.status == "999")
			alert(data.msg);
		else
			 alert(data); alert("目前无法获取城市列表，请稍后再试。");
		return;
	}
	var cityList = data.data;
	var citySelected = $("#city_name");
	$.each(cityList,function(i,item){
		citySelected.append('<option value="'+item.city_id+'">'+item.name+'</option>');
		
	});
	
}
function onCityListError(data, status) {
	alert("获取城市信息失败");
}

