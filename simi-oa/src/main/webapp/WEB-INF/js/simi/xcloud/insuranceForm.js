
$('#insuranceForm').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		cityId : {
			cityId   : "cityId"
		},
		regionId:{
			regionId : "regionId"
		},
		pension : {
			required : true,
			number : true
		},
		medical : {
			required : true,
			number : true
		},
		unemployment: {
			required : true,
			number : true
		},
		injury: {
			required : true,
			number : true
		},
		birth: {
			required : true,
			number : true
		},
		fund: {
			required : true,
			number : true
		}
	},

	messages : {
		pension : {
			required : "请输入养老保险基数",
			number : "请输入合法的数字"	
		},
		medical : {
			required : "请输入医疗保险基数",
			number:"请输入合法的数字"
		},
		unemployment: {
			required : "请输入失业险基数",
			number : "请输入合法的数字"
		},
		injury : {
			required : "请输入工伤险基数",
			number : "请输入合法的数字"
		},
		birth : {
			required : "请输入生育险基数",
			number : "请输入合法的数字"
		},
		fund : {
			required : "请输入公积金基数",
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

$.validator.addMethod("cityId",function(value,elements){
	
	// value 值是  option 选项 在 所有 option 中的 下标，从 0开始
	if(value != 0){
		return true;
	}
},"请选择城市");


$.validator.addMethod("regionId",function(value,elements){
	
	// value 值是  option 选项 在 所有 option 中的 下标，从 0开始
	if(value != 0){
		return true;
	}
},"请选择区县");



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




