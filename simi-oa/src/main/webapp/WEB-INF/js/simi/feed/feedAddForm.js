$("#saveBtn").click(function() {
	if (confirm("确认要保存吗?")) {
		var userId = $("#userId").val();
		
		if (userId == "") {
			alert("选择提问用户.");
			$("#userId").focus();
			return false;
		}
		
		var title = $("#title").val();
		
		if (title == "") {
			alert("需要输入问题内容");
			$("#title").focus();
			return false;
		}
		
		var feedExtra = $("#feedExtra").val();
		
		var params = {};
		
		params.user_id = userId;
		params.title = title;
		params.feed_type = 2;
		params.feed_extra = feedExtra;
		
		$.ajax({
			type : "POST",
			url : simiAppRootUrl + "/app/feed/post_feed.json",
			data : params,
			dataType : "json",
			async : false,
			success : function(rdata, textStatus) {
				if (rdata.status == "999") {
					alert(rdata.msg);
					return false;
				}
				
				if (rdata.status == "0") {
					location.href = "/simi-oa/feed/list";
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {

			},

		});
		
		
	}
});