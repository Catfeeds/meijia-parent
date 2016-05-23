

//提交验证
$("#btn-team-submit").on('click', function(e) {
	
    var form = $('#job_hunter_form');
	
	var formValidity = $('#job_hunter_form').validator().data('amui.validator').validateForm().valid;
	
	if (formValidity) {
		
		var params = $("#job_hunter_form").serializeArray();
		
		$.ajax({
			type : "POST",
			url : xCloudRootUrl + "/hr/resume/resume_exchange_form.json", 	// 提交到 xcloud 接口, app接口没有 修改功能
			data : params,
			dataType : "json",
			success : function(data) {
				
				if (data.appResultData.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.appResultData.status == 0) {
					location.href = "/xcloud/hr/resume/resume_exchange_list";
				}
			}
		})
	} else  {
		console.log("fail");
	};
});
