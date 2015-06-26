myApp.onPageBeforeInit('order-form-page', function(page) {

	var userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}

	var secId = localStorage['sec_id'];

	var userInfoSuccess = function(data, textStatus, jqXHR) {
		// We have received response and can hide activity indicator
		myApp.hideIndicator();
		var result = JSON.parse(data.response);
		var user = result.data;
		var formData = {
			'sec_id' : secId,
			'user_id' : userId,
			'name' : user.name,
			'mobile' : user.mobile
		}

		myApp.formFromJSON('#order-form', formData);
		console.log($$("#sec_id").val());

	};

	var postdata = {};

	postdata.user_id = userId;
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "user/get_userinfo.json",
		dataType : "json",
		cache : true,
		data : postdata,

		statusCode : {
			200 : userInfoSuccess,
			400 : ajaxError,
			500 : ajaxError
		},
		success : function() {

		}
	});

	$$('#order-submit').on('click', function() {

		var formData = myApp.formToJSON('#order-form');
		$$.ajax({
			cache : true,
			type : "POST",
			url : siteAPIPath + "order/post_add.json",
			data : formData,
			async : false,
			error : function(request) {
				alert("订单提交失败，请查看你的输入是否正确!");
			},
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return;
				}

				if (data.status == "0") {
					alert("订单已经推送给用户,下一步会跳转到订单详情页面");
				}
			}
		});
	});

});