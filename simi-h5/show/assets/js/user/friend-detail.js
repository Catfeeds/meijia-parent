var userId = getUrlParam("user_id");

function getUserInfo() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	$.ajax({
		type : "GET",
		url : appRootUrl + "user/get_user_index.json?user_id=" + userId + "&view_user_id=" + userId,
		dataType : "json",
		cache : true,
		async : false,
		success : function(data) {
			var user = data.data;
			$("#headImg").attr("src", user.head_img);
			$("#name").html(user.name);
			$("#level").html(user.level);
			var mobile = user.mobile;
			if (mobile != undefined && mobile != "") {
				$("#mobile").html(user.mobile);
				$("#mobileHref").attr("href","tel:"+mobile);
				$("#smsHref").attr("href","sms:"+mobile);
			}
			$("#cityName").html(user.province_name);
			$("#companyName").html(user.company_name);
			$("#deptName").html(user.dept_name);
			$("#email").html(user.email);
		}
	});
}


getUserInfo();