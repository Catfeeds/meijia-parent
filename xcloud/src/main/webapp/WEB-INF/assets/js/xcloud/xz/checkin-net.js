$("#btn-checkin-add").click(function() {
	location.href = "/xcloud/xz/checkin/netForm";
});

//删除会议室
function checkInNetDel(id) {
	if(confirm("确定要删除出勤地点吗")) {
		var params = {};
		params.id = id;
		// 发送验证码
		$.ajax({
			type : "POST",
			url : "/xcloud/xz/checkin/net-del.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				var status = data.status;
				console.log(status);
				if (status == "999") {
					return false;
				}
				location.reload();
			}
		})
	}
}
