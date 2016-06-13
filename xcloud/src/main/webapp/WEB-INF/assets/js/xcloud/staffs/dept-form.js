

//提交验证
$("#btn-team-submit").on('click', function(e) {
	
    var form = $('#dept_form');
	
	var formValidity = $('#dept_form').validator().data('amui.validator').validateForm().valid;
	
	if (formValidity) {
		
		var params = $("#dept_form").serializeArray();
		
		$.ajax({
			type : "POST",
			url : xCloudRootUrl + "/staff/dept/dept_form.json", 	// 提交到 xcloud 接口, app接口没有 修改功能
			data : params,
			dataType : "json",
			success : function(data) {
				
				location.href = "/xcloud/staff/dept/dept_list";
			}
		})
	} else  {
		console.log("fail");
	};
});

$('.chosen-select').chosen({
	  no_results_text: '没有找到匹配的项！'
});