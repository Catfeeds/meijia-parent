myApp.onPageBeforeInit('order-form-page', function(page) {

	var userId = page.query.user_id;
	
	var orderNo = page.query.order_no;
	
	var orderId = 0;
	
	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
	
	var secId = localStorage['sec_id'];
	
	//如果为新增订单
	if (orderNo == undefined || orderNo == '' || orderNo == 0) {
	
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
			$$("#order-submit").css('display','block'); 
//			console.log($$("#sec_id").val());
	
		};
	
		var postdata = {};
	
		postdata.user_id = userId;
		$$.ajax({
			type : "GET",
			url : siteAPIPath + "user/get_userinfo.json",
			dataType : "json",
			cache : true,
			data : postdata,
			timeout : 100000,
			statusCode : {
				200 : userInfoSuccess,
				400 : ajaxError,
				500 : ajaxError
			},
			success : function() {
	
			}
		});
				
		
	} else {  	
		//如果为修改订单
		
		var orderInfoSuccess = function(data, textStatus, jqXHR) {
			
			// We have received response and can hide activity indicator
			myApp.hideIndicator();
			var result = JSON.parse(data.response);
			var order = result.data;
			var formData = {
				'order_id'			: order.id,
				'sec_id' 			: secId,
				'user_id' 			: userId,
				'name' 				: order.name,
				'mobile' 			: order.mobile,
				'service_type'		: order.service_type,
				'service_content' 	: order.service_content,
				'order_pay_type'	: order.order_pay_type,
				'order_money'		: order.order_money
			}
	
			myApp.formFromJSON('#order-form', formData);
			
			orderId = order.id;
			
			if (order.order_status == 1 || order.order_status == 3) {
				$$("#order-submit").css('display','block'); 
			}			
	
		};
		
		var postdata = {};
	
		postdata.user_id = userId;
		postdata.order_no = orderNo;
		$$.ajax({
			type : "GET",
			url : siteAPIPath + "order/get_detail.json",
			dataType : "json",
			cache : true,
			data : postdata,
			statusCode : {
				200 : orderInfoSuccess,
				400 : ajaxError,
				500 : ajaxError
			},
			success : function() {
	
			}
		});		
		
		
	}

	
	
	$$('#order-submit').on('click', function() {
		
		
		if (orderFormValidation() == false) {
			return false;
		}
		
		
		var orderSubmitSuccess = function(data, textStatus, jqXHR) {
			// We have received response and can hide activity indicator
			myApp.hideIndicator();
//			console.log("submit success");
			var result = JSON.parse(data.response);

//			console.log(result);
			if (result.status == "999") {
				myApp.alert(result.msg);
				return;
			}
			
			if (result.status == "0") {
				myApp.alert("订单推送已完成");
				mainView.router.loadPage("order/order-list.html?user_id="+userId);
			}

		};		
		
		//如果为新增订单
		if (orderNo == undefined || orderNo == '' || orderNo == 0) {
			var formData = myApp.formToJSON('#order-form');
			$$.ajax({
				cache : true,
				type : "POST",
				url : siteAPIPath + "order/post_add.json",
				data : formData,
				statusCode : {
					200 : orderSubmitSuccess,
					400 : ajaxError,
					500 : ajaxError
				},
			});
		} else {
			//修改订单
			
			var postdata = {};
			
			postdata.user_id = userId;
			postdata.order_id = orderId;
			
			$$.ajax({
				cache : true,
				type : "POST",
				url : siteAPIPath + "order/push_order.json",
				data : postdata,
				statusCode : {
					200 : orderSubmitSuccess,
					400 : ajaxError,
					500 : ajaxError
				},
			});
		}
	});

});



function orderFormValidation() {
	var formData = myApp.formToJSON('#order-form');

	if (formData.name == "") {
		myApp.alert("请输入客户昵称");
		return false;
	}
	
	if (formData.mobile == "") {
		myApp.alert("请输入手机号");
		return false;
	}	
	
	if (isPhone(formData.mobile) == false) {
		myApp.alert("请输入正确的手机号");
		return false;
	}	
	
	if (formData.service_content == "") {
		myApp.alert("请输入服务内容");
		return false;
	}
	
	var orderPayType = $$('input[name="order_pay_type"]:selected').val();

	if (orderPayType == 1) {
		if (formData.order_money == "" || formData.order_money == 0) {
			myApp.alert("支付金额必须大于0");
			return false;
		}
	}

	return true;
}