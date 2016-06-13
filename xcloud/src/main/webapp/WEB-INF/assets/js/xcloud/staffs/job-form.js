

//提交验证
$("#btn-team-submit").on('click', function(e) {
	
    var form = $('#job_form');
	
	var formValidity = $('#job_form').validator().data('amui.validator').validateForm().valid;
	
	if (formValidity) {
		
		var params = $("#job_form").serializeArray();
		
		$.ajax({
			type : "POST",
			url : xCloudRootUrl + "/job/job_form.json", 	// 提交到 xcloud 接口, app接口没有 修改功能
			data : params,
			dataType : "json",
			success : function(data) {
				
				location.href = "/xcloud/job/job_list";
			}
		})
	} else  {
		console.log("fail");
	};
});

$('.chosen-select').chosen({
	  no_results_text: '没有找到匹配的项！'
});