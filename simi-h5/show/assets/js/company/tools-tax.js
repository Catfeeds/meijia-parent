
// 点击计算
$("#btn-do").on("click",function() {
			
	// 数据校验
	var formValidity = $('#taxForm').validator().data('amui.validator').validateForm().valid;
	
	if (!formValidity) {
		return false;
	}
	
	
	
	// 发送请求，得到 配置的 基数比例
	
	var params = {};
	
	params.settingType = $("#settingType").find("option:selected").val();
	params.salary = $("#salary").val();
	params.insurance = $("#insurance").val();
	params.beginTax = $("#beginTax").find("option:selected").val();
	$.ajax({
		type : "POST",
		url : appRootUrl + "insurance/math_tax.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			
			var vo = data.data;
			
			//应缴税款
			$("#taxNeed").val(vo.taxNeed);
			$("#taxedSalary").val(vo.taxedSalary);
			
		},
		error : function() {
			return false;
		}
	});
	
});
