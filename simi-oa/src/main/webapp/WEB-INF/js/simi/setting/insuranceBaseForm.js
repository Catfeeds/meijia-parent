
$('#insuranceForm').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		cityId : {
			cityId   : "cityId"
		},
	
		shebaoMin : {
			required : true,
			number : true
		},
		shebaoMax : {
			required : true,
			number : true
		},
		gjjMin : {
			required : true,
			number : true
		},
		gjjMax : {
			required : true,
			number : true
		},
		
	},

	messages : {
		shebaoMin : {
			required : "请输入最低社保缴纳基数",
			number : "请输入合法的数字"	
		},
		shebaoMan : {
			required : "请输入最高社保缴纳基数",
			number : "请输入合法的数字"	
		},
		gjjMin : {
			required : "请输入最低公积金缴纳基数",
			number:"请输入合法的数字"
		},
		gjjMax : {
			required : "请输入最高公积金缴纳基数",
			number:"请输入合法的数字"
		}
	},

	invalidHandler : function(event, validator) { // display error alert on
		// form submit
		$('.alert-error', $('#insuranceForm')).show();
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

$.validator.addMethod("cityId",function(value,elements){
	
	// value 值是  option 选项 在 所有 option 中的 下标，从 0开始
	if(value != 0){
		return true;
	}
},"请选择城市");

//提交
$("#insuranceFormBtn").on("click",function(form){
	
	if (confirm("确认要保存吗?")) {
		
		if($('#insuranceForm').validate().form()){
			$('#insuranceForm').submit();
		}else{
			return false;
		}
	}
	
});




