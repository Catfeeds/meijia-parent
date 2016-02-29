if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
}
var userId = getUrlParam("user_id");
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_user_addr_list.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		/*if (data.data == null){
			alert("您还没有添加地址，请添加地址后再进行此操作！");
		}*/
		
		var userAddr = data.data;
		
		
		var userAddrHtml = "";
		for(var i=0 ; i < userAddr.length; i++){
			var addrId = userAddr[i].addr_id;
			var addrName = userAddr[i].addr_name;
			userAddrHtml +="<option value =\""+addrId+"\" >"+addrName+"</option>"
		}	
		$("#addrName").append(userAddrHtml);

	}
});
$('#order-green-form').validator({
	validate : function(validity) {
		
		//钱数校验
		if ($(validity.field).is('.js-ajax-validate')) {
			var  totalBudget = $('#totalBudget').val();  
			validity.valid = verifyPrice(totalBudget);

			return validity;    
		} 
		

	},
	// 域验证通过时添加的操作，通过该接口可定义各种验证提示
	markValid : function(validity) {
		// this is Validator instance
		var options = this.options;
		var $field = $(validity.field);
		var $parent = $field.closest('.am-form-group');
		$field.addClass(options.validClass).removeClass(options.inValidClass);

		$parent.addClass('am-form-success').removeClass('am-form-error');

		options.onValid.call(this, validity);
	},
});
$("#add_btn").on('click', function(e) {
	console.log("reg-submit click");
	var $form = $('#order-green-form');
	//var validator = $form.data('amui.validator');
//	var formValidity = validator.isFormValid()
	//$.when(formValidity).then(function() {
		// done, submit form
		companyRegSubmit()
	//}, function() {
		// fail
	//});
});
//提交
function companyRegSubmit() {
	var userId = getUrlParam("user_id");
	console.log(userId+"1111111111111111111111");
	var params = {};
	//params.addr_id = 3;
	//params.mobile = "18249516801";
	params.total_num = $('#totalNum').val();
	params.total_budget = $('#totalBudget').val();
	params.remarks = $('#remarks').val();
	params.user_id = userId;
//	params.addr = $('#addr').val();
	params.addr_id = $('#addrName').val();
	
	// 提交数据，完成注册流程
	$.ajax({
		type : "POST",
		url : appRootUrl + "/order/post_add_green.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			if (data.status == 0) {
				location.href = "store-reg-ok.html";
			}
		},
		error : function() {
			return false;
		}
	});
}