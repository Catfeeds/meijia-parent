//校验身份证号是否重复
function validateUserMobile(mobile){
	
	var isExist = false;
	var option ={
		url: appRootUrl + "/interface-user/validate-user-mobile.json",	
		type:"POST",
		async :false,
		dataType:"text",
		data:{
			mobile:mobile
		},
		success:function(data){
			console.log(data);
			
			var dataObj = eval("("+data+")");
			console.log(dataObj.status);
			console.log(dataObj.msg);
			if (dataObj.status == 0) {
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