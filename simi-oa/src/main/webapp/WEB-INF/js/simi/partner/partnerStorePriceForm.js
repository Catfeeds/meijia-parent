$('#partner-service-price-form').validate({
	errorElement : 'span', //default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		no : {
			required: true,
			digits : true
		},
		name : {
			required : true
		},
		
		imgUrl: {
			required: true
		},
		
		/*serviceTitle: {
			required: true
		},
		*/
		price: {
			required: true,
			number:true,
		},
		
		disPrice: {
			required: true,
			number:true,
		},		
		
		contentStandard: {
			required: true
		},
		
		contentDesc: {
			required: true
		},
		
		contentFlow: {
			required: true
		},

		parentId : {
			required : true
		},
	},

	messages : {
		no : {
			required: "请输入序号",
			digits : "请输入数字"
		},		
		name : {
			required : "请输入提供的服务名称"
		},
		
		/*serviceTitle: {
			required: "请输入提供副标题"
		},
		*/
		price: {
			required: "请输入原价",
			number: "请输入正确的价格数字",
			
		},
		
		disPrice: {
			required: "请输入原价",
			number: "请输入正确的价格数字",
		},		
		
		contentStandard: {
			required: "请输入服务标准"
		},
		
		contentDesc: {
			required: "请输入服务说明"
		},
		
		contentFlow: {
			required: "请输入服务流程"
		},		
		
		parentId : {
			required : "请选择所属的服务类别"
		},
	},

	invalidHandler : function(event, validator) { //display error alert on form submit
		$('.alert-error', $('#partner-service-price-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

$('.partner-service-price-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#btn_submit").click();
		return false;
	}
});

$("#btn_submit").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#partner-service-price-form').validate().form()) {
			$('#partner-service-price-form').submit();
		}
	}
});

$("#btn_preview").click(function() {
	var serviceTypeId = $("#servicePriceId").val()
	//http://app.bolohr.com/simi-h5/discover/service-detail.html?service_type_id=1
	var url = "http://" + host + "/simi-h5/discover/service-detail.html?service_type_id="+ serviceTypeId;
	window.open(url,"_blank");
});

$("#imgUrlFile").fileinput({
	previewFileType: "image",
	browseClass: "btn btn-success",
	browseLabel: "上传图片",
	browseIcon: '<i class="glyphicon glyphicon-picture"></i>',
	removeClass: "btn btn-danger",
	removeLabel: "删除",
	removeIcon: '<i class="glyphicon glyphicon-trash"></i>',
	allowedFileExtensions: ["jpg", "gif", "jpeg","png",],
	maxFileSize: 8192,
	msgSizeTooLarge: "上传文件大小超过8mb"
});

$("#orderType").change(function(){ 
	var sel = $(this).children('option:selected').val();
	if (sel == "0") {
		$("#orderDurationSelectBox").css('display', 'none');
	}
	
	if (sel == "1") {
		console.log("adfsadfsadf")
		$("#orderDurationSelectBox").css('display', 'block');
	}
});