
//提交验证
$("#btn-commpany-asset-form-submit").on('click', function(e) {

    var form = $('#commpany-asset-form');
	
	var formValidity = $('#commpany-asset-form').validator().data('amui.validator').validateForm().valid;
	
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
		
		alert(xCloudRootUrl + "/xz/assets/company_post_asset.json");
		
		$.ajax({
			type : "POST",
			url : xCloudRootUrl + "/xz/assets/company_post_asset.json", 	//TODO 此处是 云平台的 url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/assets/commpany_asset_list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
