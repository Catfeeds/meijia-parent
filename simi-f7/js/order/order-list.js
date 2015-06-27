myApp.onPageInit('order-list-page', function(page) {

	var userId = page.query.user_id;
	console.log("userId = " + userId);
	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}

	var secId = localStorage['sec_id'];
	
//	var orderListPartTemplate = $$('script#order-list-part').html();
//	var compiledOrderListPartTemplate = Template7.compile(orderListPartTemplate);
//	console.log(compiledOrderListPartTemplate);
	
	var orderListSuccess = function(data, textStatus, jqXHR) {
		// We have received response and can hide activity indicator
		myApp.hideIndicator();
		var result = JSON.parse(data.response);
		var orders = result.data;

//		var html = compiledOrderListPartTemplate(orders);
		
		var html = $$('#order-list-part').html();
//		console.log(html);
		var resultHtml = '';
		for(var i = 0; i< orders.length; i++) {
//			console.log(orders[i]);
			var order = orders[i];
//			var addTime = new Date(order.add_time * 1000);
			var timestamp = moment.unix(order.add_time);
			var addTimeStr = timestamp.format('YYYY-MM-DD HH:mm:ss');
			console.log(order.add_time + "----" + addTimeStr);
			var htmlPart = html;
			htmlPart = htmlPart.replace(new RegExp('{id}',"gm"), order.id);
			htmlPart = htmlPart.replace(new RegExp('{user_id}',"gm"), order.user_id);
			htmlPart = htmlPart.replace(new RegExp('{order_no}',"gm"), order.order_no);
			htmlPart = htmlPart.replace(new RegExp('{service_type_name}',"gm"), order.service_type_name);
			htmlPart = htmlPart.replace(new RegExp('{add_time}',"gm"), addTimeStr);
//			console.log(htmlPart);
			resultHtml += htmlPart;
		}
		$$('.list-block ul').append(resultHtml);
	};

	var postdata = {};

	postdata.user_id = userId;
	postdata.page = 1;
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "order/get_list.json",
		dataType : "json",
		cache : true,
		data : postdata,

		statusCode : {
			200 : orderListSuccess,
			400 : ajaxError,
			500 : ajaxError
		},
		success : function() {

		}
	});

});