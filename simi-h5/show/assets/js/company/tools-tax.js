
// 点击计算
$("#btn-do").on("click",function() {
			
	// 数据校验
	var formValidity = $('#toolsInsuForm').validator().data('amui.validator').validateForm().valid;
	
	if (!formValidity) {
		return false;
	}
	
	
	
	// 发送请求，得到 配置的 基数比例
	
	var params = {};
	
	params.setting_type = $("#settingType").find("option:selected").val();
	
	$.ajax({
		type : "get",
		url : appRootUrl + "insurance/get_tax.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			
			var vo = data.data;
			
			//税前工资
			var salary = $("#salary").val();
			
			//五险一金
			var insurance = $("#insurance").val();
			
			//起征点
			var beginTax = $("#beginTax").find("option:selected").val();
			
		},
		error : function() {
			return false;
		}
	});
	
});
