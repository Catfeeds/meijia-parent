
//选择一个 选项时，触发事件
//$("#asset-maxchecked").on("change",function(){
//	
//		
//		var thisVal = $(this).val();
//		
//		
//		var cloneNode = "<div class='am-form-group'> " 
//							+"<label  class='am-u-sm-3 am-form-label'>数量:</label>"
//							+"<div class='am-u-sm-9'>"
//							+	"<input type='number' class='am-form-field am-radius' id="+thisVal+" placeholder="+$(this).text()+">"
//							+	"<small>*必填项</small>"
//							+"</div>"
//						+"</div>";
//
//		
//		$("#assetSelect").after(cloneNode);
//});



//提交验证
$("#btn-use-asset-form-submit").on('click', function(e) {
	
	
    var form = $('#use-asset-form');
	
	var formValidity = $('#use-asset-form').validator().data('amui.validator').validateForm().valid;
	
	//校验 select
//	var selectOp = 0;
//	
//	$("#asset-maxchecked").find("option").each(function(k,v){
//		
//		if($(this).selected){
//			selectOp += 1;
//		}
//	});
//	
//	if(selectOp === 0){
//		alert("请您至少选择一种物品")
//		return false;
//	}
	
	
	if (formValidity) {
		
		var params = {}
		
		params.user_id = $("#userId").val();
		params.company_id = $("#companyId").val();
		
		params.name = $("#name").val();
		params.mobile = $("#mobile").val();
		params.purpose = $("#purpose").val();
		
		var assetArray = [];
		
		var assetId =  $("#asset-maxchecked").find("option:selected").val();
		
		var total = $("#assetNum").val();
		
		assetArray.push({"asset_id":assetId,"total":total});
		
		params.asset_json = JSON.stringify(assetArray);
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "/app/record/post_asset_use", 	
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/assets/use_asset_list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
