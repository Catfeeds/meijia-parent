//添加员工
$("#btn-express-add").click(function() {
	location.href = "/xcloud/xz/express/express-form?id=0";
});

// 立即结算，根据所选择的checkbox
// 分配部门
$("#btn-express-close-all").click(function() {
	console.log("btn-express-close-all click");

	var total = $("#page-total").html();
	total = total.replace("共", "");
	total = total.replace("条记录", "");

	if (total == "0") {
		alert("当前列表数为0, 没有可用结算的快递单.");
		return false;
	}

	if (confirm("确定要将当前搜索结果进行一键结算吗?")) {
		var params = {};
		
		var userId = $("#userId").val();

		var isClose = $("#isClose").val();
		if (isClose == "") isClose = "-1";
		var expressType = $("#expressType").val();
		if (expressType == "") expressType = "-1";
		var expressNo = $("#expressNo").val();

		var expressId = $("#expressId").val();
		if (expressId == "") expressId = 0;
		
		params.userId = userId;
		params.startDate = $("#startDate").val();
		params.endDate = $("#endDate").val();
		params.isClose = isClose;
		params.expressType = expressType;
		params.expressNo = expressNo;
		params.expressId = expressId;

		console.log(params);

		$.ajax({
			type : "POST",
			url : appRootUrl + "/record/express_close_by_search.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				console.log(data);
				var status = data.status;
				if (data.status == 999) {
					alert(data.msg);
					return false;
				}
				
				alert("结算成功");
				window.location.reload();//刷新当前页面.
					

			}
		})
	}
});
