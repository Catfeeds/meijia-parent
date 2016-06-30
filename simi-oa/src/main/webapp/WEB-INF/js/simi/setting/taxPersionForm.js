
$('#insuranceForm').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		
		level : {
			required : true,
			number : true
		},
		
		taxRio : {
			required : true,
			number : true,
			range:[0,255]
		},
		taxSs : {
			required : true,
			number : true
		},
		
		
	},

	messages : {
		
		level : {
			required : "请输入级别",
			number : "请输入合法的数字",
			
		},
		
		taxRio : {
			required : "请输入税率",
			number : "请输入合法的数字",
			range:"0-100以内"
		},
		taxSs : {
			required : "请输入速算扣除数",
			number : "请输入合法的数字"	
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