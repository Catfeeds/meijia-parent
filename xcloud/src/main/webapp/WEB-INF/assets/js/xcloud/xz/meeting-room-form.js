//提交验证
$("#btn-room-submit").on('click', function(e) {
	console.log("click");
	var form = $('#room-form');
	var formValidity = form.validator().data('amui.validator').validateForm().valid;
	if (formValidity) {
		// done, submit form
		console.log("ok");

		var id = form.find('input[name="id"]').val();
		var name = form.find('input[name="name"]').val();
		// 判断是否有重名的情况
		console.log(id);
		console.log(name);

		var params = {};
		params.id = id;
		params.name = name;
		// 发送验证码
		$.ajax({
			type : "GET",
			url : "/xcloud/xz/meeting/check-room-name.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				var status = data.status;
				console.log(status);
				if (status == "999") {
					alert("会议室名称已存在");
					return false;
				}
				form.submit();
			}
		})

	} else {
		// fail
		console.log("fail");
	}
	;
});
