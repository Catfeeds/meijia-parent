
myApp.onPageBeforeInit('mine-info-edit', function(page) {

	var secId = localStorage['sec_id'];
	var secMobile = localStorage['sec_mobile'];
	
	var secInfoSuccess = function (data, textStatus, jqXHR) {
		var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(result.msg);
			return;
		}
		var sec = result.data;
		
		var formData = {
			'name' : sec.name,
			'nick_name' : sec.nick_name,
			'sex' : sec.sex,
			'birth_day' : sec.birth_day,
			'city_id' : sec.city_id,
			'sec_id'	: sec.id
		}

		myApp.formFromJSON('#mine-form', formData);
		
		var cityList = JSON.parse(localStorage.getItem("city_list"));

		var citySelected = $$("#city_id");
		var optionHtml = "";
		$$.each(cityList,function(i,item){

			if (item.city_id == sec.city_id) {
				optionHtml+= '<option value="'+item.city_id+'" selected="true" >'+item.name+'</option>';
			} else {
				optionHtml+= '<option value="'+item.city_id+'">'+item.name+'</option>';
			}
			
		});

		myApp.smartSelectAddOption('.smart-select select', optionHtml, 0);

		
	}	
	
	
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
	
	$$('#head_img').on('change', gotPic);
	
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
		
		
		
//		var mine_form = $$('#mine-form');
//		var formData = new FormData();
//		formData.append('name', "小茶1");
//		console.log(formData.toString());
//		return false;
		
//		formData.append('name', "小茶1");
//		formdata.append("nick_name", $$('nick_name').val());
//		formdata.append("sex", $$('sex').val());
//		formdata.append("birth_day", $$('birth_day').val());
//		formdata.append("city_id", $$('city_id').val());
//		console.log(formData);
//		return false;
//		if ($$('#head_img')[0].files[0]) {
//			formData.append('head_img', $$('#head_img')[0].files[0]);
//		}		
		
		
		
		
	});
});

//保存秘书信息
function saveSecSuccess(data, textStatus, jqXHR) {
		myApp.hideIndicator();
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

function gotPic(event) {
    if (event.target.files.length === 1 && event.target.files[0].type.indexOf('image/') === 0) {
        $$('#avatar').attr('src', URL.createObjectURL(event.target.files[0]));
    }
}

