
var settingType = getUrlParam("setting_type");
if (settingType != undefined) {
	if (settingType == "meeting-type")
		$('#href-tab2').click();
}


function addSetting(formName) {
	var form = $("#" + formName);
	var formValidity = form.validator().data('amui.validator').validateForm().valid

	if (formValidity) {
	// done, submit form
		console.log("ok");

		var settingType = form.find('input[name="settingType"]').val();
		var name = form.find('input[name="name"]').val();
		//判断是否有重名的情况
		console.log(settingType);
		console.log(name);
		
		var params = {};
		params.setting_type = settingType;
		params.name = name;
		// 发送验证码
		$.ajax({
			type : "GET",
			url : "/xcloud/xz/meeting/check-setting-name.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				var status = data.status;
				console.log(status);
				if (status == "999") {
					alert("名称已存在");
					return false;
				}
				form.submit();
			}
		})
		
	} else  {
		// fail
		console.log("fail");
	};
}