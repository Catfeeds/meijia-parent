
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
		pensionP : {
			required : true,
			number : true
		},
		pensionC : {
			required : true,
			number : true
		},
		medicalP : {
			required : true,
			number : true
		},
		medicalC : {
			required : true,
			number : true
		},
		unemploymentP: {
			required : true,
			number : true
		},
		unemploymentC: {
			required : true,
			number : true
		},
		injuryP: {
			required : true,
			number : true
		},
		injuryC: {
			required : true,
			number : true
		},
		birthP: {
			required : true,
			number : true
		},
		birthC: {
			required : true,
			number : true
		},
		fundP: {
			required : true,
			number : true
		},
		fundC: {
			required : true,
			number : true
		},
	},

	messages : {
		pensionP : {
			required : "请输入养老保险基数",
			number : "请输入合法的数字"	
		},
		pensionC : {
			required : "请输入养老保险基数",
			number : "请输入合法的数字"	
		},
		medicalP : {
			required : "请输入医疗保险基数",
			number:"请输入合法的数字"
		},
		medicalC : {
			required : "请输入医疗保险基数",
			number:"请输入合法的数字"
		},
		unemploymentP: {
			required : "请输入失业险基数",
			number : "请输入合法的数字"
		},
		unemploymentC: {
			required : "请输入失业险基数",
			number : "请输入合法的数字"
		},
		
		injuryP : {
			required : "请输入工伤险基数",
			number : "请输入合法的数字"
		},
		injuryC : {
			required : "请输入工伤险基数",
			number : "请输入合法的数字"
		},
		birthP : {
			required : "请输入生育险基数",
			number : "请输入合法的数字"
		},
		birthC : {
			required : "请输入生育险基数",
			number : "请输入合法的数字"
		},
		fundP : {
			required : "请输入公积金基数",
			number : "请输入合法的数字"
		},
		fundC : {
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




