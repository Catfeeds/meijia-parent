//添加会议室
$("#btn-meeting-add").click(function() {
	location.href = "/xcloud/xz/meeting/room-form?id=0";
});

//删除会议室
function roomDel(id) {
	if(confirm("确定要删除会议室吗")) {
		var params = {};
		params.id = id;
		// 发送验证码
		$.ajax({
			type : "POST",
			url : "/xcloud/xz/meeting/room-del.json", // 发送给服务器的url
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

