//检测必备的数据是否正确。

//检测城市列表

//localStorage.removeItem("city_list");
//console.log(localStorage.getItem('city_list'));
if (localStorage.getItem('city_list') == null) {
	AjaxCityList();
}

//检测
if (localStorage.getItem('service_type_list') == null) {
	serviceTypeList();
}


function AjaxCityList() {
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "city/get_list.json?",
		dataType : "json",
		cache : true,
		statusCode : {
				200 : ajaxCityListSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
	});
}

function ajaxCityListSuccess(data, textStatus, jqXHR) {
//	console.log("cityListSuccess");
	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	var cityList = JSON.stringify(result.data);
	localStorage.setItem('city_list', cityList);
//	console.log(localStorage.getItem("city_list"));
//	var cityList = JSON.parse(localStorage.getItem("city_list"));
//	
//	$$.each(cityList, function(i,item){
//		console.log(item.city_id + "---" + item.name);
//	});
}


function serviceTypeList() {
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "app/dict/get_serviceType.json?",
		dataType : "json",
		cache : true,
		statusCode : {
				200 : ajaxServiceTypeListSuccess,
				400 : ajaxError,
				500 : ajaxError
			}
	});
}

function ajaxServiceTypeListSuccess(data, textStatus, jqXHR) {
//	console.log("cityListSuccess");
	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	var serviceTypeList = JSON.stringify(result.data);
	localStorage.setItem('service_type_list', serviceTypeList);
//	console.log(localStorage.getItem("city_list"));
//	var cityList = JSON.parse(localStorage.getItem("city_list"));
//	
//	$$.each(cityList, function(i,item){
//		console.log(item.city_id + "---" + item.name);
//	});
}
