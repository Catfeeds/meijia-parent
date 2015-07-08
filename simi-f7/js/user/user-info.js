myApp.onPageBeforeInit('user-info', function (page) {
		
	var userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
	getUserInfos(userId);
	
	$$(".user-info-edit-link").on("click",function(){
			mainView.router.loadPage("user/user-info-edit.html?user_id="+userId);
		});
	
	$$(".user-addr-list-link").on("click",function(){
		mainView.router.loadPage("user/user-addr-list.html?user_id="+userId);
	});
	
});


var onUserInfoSuccess =function(data, textStatus, jqXHR) {
   	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	
	var user = result.data;
   
	var userType,userFrom;
	var user_type = user.user_type;
	var user_from = user.user_from;
	if(user_type==0){
		userType = "普通用户";
	}else if(user_type==1){
		userType = "代理商";
	}else{
		userType ="";
	}
	if(user_from==0){
		userFrom = "APP";
	}else if(user_from == 1){
		userFrom = "微网站";
	}else if(user_from = 2){
		userFrom = "管理后台";
	}else{
		userFrom = "";
	}
	 $$("#head_img-span").attr("src",user.head_img);
	 $$("#name-span").text(user.name);
	 $$("#mobile-span").text(user.mobile);
	 $$("#sex-span").text(user.sex);
	 $$("#user-id").text(user.id);
	 $$("#province_name").text(user.province_name);
	 $$("#rest_money").text(user.rest_money);
	 $$("#score").text(user.score);
	 $$("#user_from").text(userFrom);	 
	 $$("#user_type").text(userType);	 
}

//获取用户信息接口
function getUserInfos(userId) {
	var postdata = {};
    postdata.user_id = userId;    
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json",
		dataType : "json",
		cache : true,
		data :postdata,
		statusCode: {
         	200: onUserInfoSuccess,
 	    	400: ajaxError,
 	    	500: ajaxError
 	    },
	});
}
/*function goToOrderForm() {
	var userId = $$.urlParam('user_id');
	location.href = "wx-order-form.html?user_id=" + userId;
}

function goToOrderList() {
	var userId = $$.urlParam('user_id');
	location.href = "wx-order-list.html?user_id=" + userId;
}*/

