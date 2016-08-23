
//提交验证
$("#btn-company-asset-form-submit").on('click', function(e) {

    var form = $('#company-asset-form');
	
	var formValidity = $('#company-asset-form').validator().data('amui.validator').validateForm().valid;
	
	if (formValidity) {
		
		var params = {}
		
		params.user_id = $("#userId").val();
		params.company_id = $("#companyId").val();
		
		params.asset_type_id = $('select').find('option:selected').val();
		
		params.name = $("#name").val();
		params.total = $("#total").val();4
		params.price = $("#price").val();
		params.unit = $("#unit").val();
		params.seq = $("#seq").val();
		params.place = $("#place").val();
		
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "/app/record/post_asset", 	
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/assets/company_asset_list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
