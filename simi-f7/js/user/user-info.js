myApp.onPageBeforeInit('user-info', function (page) {
		
	var userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
	getUserInfo(userId);
	
	$$(".user-info-edit-link").on("click",function(){
			mainView.router.loadPage("user/user-info-edit.html?user_id="+userId);
		});
	
});


function formSetInfoSuccess(result) {
	var data = result.data;
	$$("#head_img").attr("src",data.head_img);
console.log(data);
	$$("#name").text(data.name);
	var sex = data.sex;
	if(sex == 0){
		$$("#sex").text("先生");
	}else{
		$$("#sex").text("女士");
	}
	$$("#mobile").text(data.mobile);
	$$("#province_name").text(data.province_name);
	$$("#rest_money").text(data.rest_money);
	$$("#score").text(data.score);
	var userType,userFrom;
	var user_type = data.user_type;
	var user_from = data.user_from;
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
	$$("#user_type").text(userType);
	$$("#user_from").text(userFrom);
}

//获取用户信息接口
function getUserInfo(userId) {

	var onUserInfoSuccess =function(data, textStatus, jqXHR) {
	 	myApp.hideIndicator();
	   	var result = JSON.parse(data.response);
		if (result.status == "999") {
			myApp.alert(result.msg);
			return;
		}
		formSetInfoSuccess(result);
	}
	var postdata = {};
    postdata.user_id = userId;    
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json",
		dataType : "json",
		cache : false,
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

