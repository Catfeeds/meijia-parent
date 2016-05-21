
//提交验证
$("#btn-team-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#team-form');
	
	var formValidity = $('#team-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.city_id = $("#cityId").val();
		params.link_tel = $("#linkTel").val();
		params.link_man = $("#linkMan").val();
		params.remarks = $("#remarks").val();
		params.team_type = $("#teamType").val();
		$.ajax({
			type : "POST",
			url : appRootUrl + "/order/post_add_team.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/teamwork/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
