//校验身份证号是否重复
function validateUserMobile(mobile, userId, userType){
	
	var isExist = false;
	var option ={
		url: appRootUrl + "/interface-user/validate-user-mobile.json",	
		type:"POST",
		async :false,
		dataType:"json",
		data:{
			mobile:mobile,
			userId:userId,
			userType:userType
		},
		success:function(data){
			console.log(data);
			
			if (data.status == 0) {
				isExist = true;
			}
			console.log("isExist = " + isExist);

		},
		error:function(){
			return false;
		}
	} ;
	$.ajax(option);
	return isExist;
}


//查询公司用户是否重复
function checkCompanyUserNameExist(userName, companyId) {
	console.log("checkCompanyUserNameExist");
	var isExist = true;
	var params = {};
	params.user_name = userName;
	params.company_id = companyId;
	$.ajax({
		type : "GET",
		url: appRootUrl + "/company/check-duplicate.json", //发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == 0) {
				isExist = false;
			}
		},
		error:function(){
			return false;
		}
	});
	return isExist;
}

//查询图片验证码是否正确
function checkCaptcha(token) {
	
	var isSuccess = false;
	var params = {};
	params.token = token;

	$.ajax({
		type : "GET",
		url :  "/xcloud/check_captcha.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == 0) {
				isSuccess = true;
			}
		},
		error:function(){
			return false;
		}
	});
	return isSuccess;
}
