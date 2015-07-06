var secMobile,secId;
//获取列表页用户列表
myApp.template7Data['page:mine-info-edit'] = function(){
	
	console.log('page data for user-list-page');
	var result =  {};
	
    var secId = localStorage['sec_id'];
    var secMobile = localStorage['sec_mobile'];
	var postdata = {};
	postdata.sec_id = secId;
	postdata.mobile = secMobile;
	$$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/get_secinfo.json",
		dataType : "json",
		cache : true,
		data : postdata,
		async : false,
		success: function(data){
			console.log(data.data);
			result = data.data;
		}
	});	
	
	
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "city/get_list.json?",
		dataType : "json",
		cache : true,
		async : false,
		success: function(data){
			console.log(data.data);
			result.citylist = data.data;
		}
	});
	
	
	
	console.log(result);
	return result;
	
	/*$$.ajax({
		
		type : "POST",
		url  : siteAPIPath+"sec/get_users.json",
		//url  : "data/users.json",
		dataType: "json",
		cache : true,
		async : false,
		data : postdata,
		success: function(data){
			result = data;
		}
	})
	return result;*/
}


/*
myApp.onPageBeforeInit('mine-info-edit', function(page) {
	secMobile = localStorage['sec_mobile'];
	secId = localStorage['sec_id'];
	//重新载入页面（不起作用）
	mainView.router.reloadPage("sec/mine-info.html");
	// 获取城市信息列表
	getCityList();

	$$("#mine_info_submit").on("click", function() {
		var formData = myApp.formToJSON('#mine-form');
		$$.ajax({
			type : "POST",
			url : siteAPIPath + "sec/post_secinfo.json",
			dataType : "json",
			cache : false,
			data : formData,
			statusCode : {
				200 : saveSecSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
		});
	});
});*/
function cityListSuccess(data, textStatus, jqXHR) {
	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	var cityList = result.data;
	var citySelected = $$("#city_name");
	$$.each(cityList,function(i,item){
		citySelected.append('<option value="'+item.city_id+'">'+item.name+'</option>');
	});
	getSecInfo(secId, secMobile);
}
function getCityList() {
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "city/get_list.json?",
		dataType : "json",
		cache : true,
		statusCode : {
				200 : cityListSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
	});
}
function secInfoSuccess(data, textStatus, jqXHR) {
	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	var sec = result.data;
	$$("#name").val(sec.name);
	$$("#nickName").val(sec.nick_name);
	if(sec.sex !=' ' && sec.sex !=null){
	$$("#sex").find("option[value='"+sec.sex+"']").attr('selected',true);
	}
	$$("#birthDay").val(sec.birth_day);
	$$("#city_name").find("option[value='"+sec.city_id+"']").attr('selected',true);
	$$("#sec_id").val(sec.id);
}
function getSecInfo(secId, mobile) {
	var postdata = {};
	postdata.sec_id = secId;
	postdata.mobile = secMobile;
	$$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/get_secinfo.json",
		dataType : "json",
		cache : true,
		data : postdata,
		statusCode : {
			200 : secInfoSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
	});
}
//保存秘书信息
function saveSecSuccess(data, textStatus, jqXHR) {
		myApp.hideIndicator();
		console.log("submit success");
		var result = JSON.parse(data.response);

		if (result.status == "999") {
			myApp.alert(result.msg);
			return;
		}
		if (result.status == "0") {
			myApp.alert("个人信息修改完成");
			mainView.router.loadPage("sec/mine-info.html");
		}
} 