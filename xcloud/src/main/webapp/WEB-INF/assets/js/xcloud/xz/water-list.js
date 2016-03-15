function waterSign(userId, orderId) {
	  var params = {}
		params.user_id = userId;
		params.order_id = orderId;
		$.ajax({
			type : "POST",
			url : appRootUrl + "/order/post_done_water.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}
				if (data.status == 0) {
					location.href = "/xcloud/xz/water/list";
				}
			}
		});

}
//添加员工
$("#btn-express-add").click(function() {
	location.href = "/xcloud/xz/water/water-form";
});
