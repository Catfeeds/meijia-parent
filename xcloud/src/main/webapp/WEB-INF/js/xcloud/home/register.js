
//step wizard
$('#register-form').stepy({
  backLabel: '上一步',
  block: true,
  nextLabel: '下一步',
  titleClick: true,
  titleTarget: '.stepy-tab'
});

//公司行业

var tradeJson = getDictTrade();
if (tradeJson != undefined) {
	
	var output = "";
	$.each(tradeJson, function(i,pitem){
		
		if (pitem.parent_id > 0) return;
		console.log(pitem.trade_id + "----" + pitem.name + "----" + pitem.parent_id);
		var optGroup = "";
		optGroup = "<optgroup label=\""+pitem.name+"\">";
		var tradeId = pitem.trade_id;
		$.each(tradeJson, function(j,item) {
			if (item.parent_id == tradeId && item.parent_id > 0) {
				optGroup+= "<option value=\""+item.trade_id+"\">"+ item.name +"</option>";
			}
		});
		optGroup += "</optgroup>";
		output += optGroup;
		
	});	
	
	$("#company_trade").html(output);
//	console.log(output);
}

//表单验证

var formValStep1 = $('#register-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {

		mobile : {
			required : true,
			isMobile : true
		},
		
		img_token: {
			required : true,
		},
	},

	messages : {
		mobile : {
			required : "请输入手机号码。"
		},
		img_token : {
			required : "请输入图形验证码。",
		},
	},

	invalidHandler : function(event, validator) { // display error alert on
													// form submit
		$('.alert-error', $('#channel-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error
																	// class to
																	// the
																	// control
																	// group
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});


//获取验证码功能

$("#btn_sms_token").click(function() {
	
});

      