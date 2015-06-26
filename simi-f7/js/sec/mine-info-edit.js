myApp.onPageInit('mine-info-edit', function(page) {

	// 获取城市信息列表
	getCityList();

});
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
	getSecInfo(secId, mobile);
}
function getCityList() {
	
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "city/get_list.json?",
		dataType : "json",
		cache : false,
		statusCode : {
				200 : cityListSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
	});
}

function getSecInfo(secId, mobile) {
	var secInfoSuccess = secInfoSuccess(data, status) {
		var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(result.msg);
			return;
		}
		var sec = result.data;
		$$("#name").val(sec.name);
		$$("#nickName").val(sec.nick_name);
		if(sec.sex !=' ' && sec.sex !=null){
			$("#sex option[value=" + sec.sex + "]").attr("selected", true);
		}
		$$("#birthDay").val(sec.birth_day);
		$$("#city_name option[value="+sec.city_id+"]").attr("selected", true);
		$$("#secId").val(sec.id);
	}
	
	var postdata = {};
	postdata.sec_id = secId;
	postdata.mobile = secMobile;
	$.ajax({
		type : "POST",
		url : siteAPIPath + "sec/get_secinfo.json",
		dataType : "json",
		cache : false,
		data : postdata,
		statusCode : {
			200 : secInfoSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
	});
}
//保存秘书信息

$("#mind_info_submit").bind("click", function() {
	var saveSecSuccess = saveSecSuccess(data, status) {
		var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(result.msg);
			return;
		}
		window.location.href = "mine-info.html";
	} 
	
	/*var $form = $('#order-form');
	var isValid = $('.am-form').data('amui.validator').isFormValid();
	if (!isValid) {
		alert("is false");
		return ;
	}*/
	var secId = localStorage['sec_id'];
	var name = $$("#name").val();
	var nickName = $$("#nickName").val();
	var sex = $$("#sex").val();
	var birthDay = $$("#birthDay").val();
	var cityId = $$("#city_name").val();

	$$.ajax({
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
		statusCode : {
			200 : saveSecSuccess,
			400 : ajaxError,
			500 : ajaxError
		}
	});
})


