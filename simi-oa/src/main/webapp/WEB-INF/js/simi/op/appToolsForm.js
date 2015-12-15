

var formVal = $('#app-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		name : {
			required: true
		},
		
		No: {
			required: true,
     		digits:true,
			range:[0,255]
		},
		logo: {
			required: true
		},
		openType: {
			required: true
		},
		url: {
			required: true
		}
	},

	messages: {
		
		name : {
			required: "名称.名称.名称.重要的事情说三遍!"
		},
		No: {
			required: "请输入编号。",
			digits:"请输入整数",
			range:"输入值必须介于 0 和 255之间"

		},
		logo: {
			required: "请选择上传路径。"
		},
		openType: {
			required: "请选择跳转类型。"
		},
		url: {
			required: "请输入跳转地址。"
		}

	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#app-form')).show();
	},

	highlight: function (element) { // hightlight error inputs
		$(element)
			.closest('.form-group').addClass('has-error'); // set error class to the control group
	},

	success: function (label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement: function (error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

$('.app-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#adForm_btn").click();
		return false;
	}
});

$("#logoFile").fileinput({
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

$('#logo').change(function(){
	 $("#img_url_new").text($("#logo").val());
});

$("#adForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#app-form').validate().form()) {
			var openType = $('#openType').val();

			var errors = {};
			if (openType.indexOf("h5") >= 0 ) {
				if ($("#url").val() == null || $("#url").val() == "" ) {
					alert("请输入跳转地址");
					errors.url = "请输入跳转地址";
					formVal.showErrors(errors);
					return false;
				}
			}
			
			var isPartner = $('input:radio[name=isPartner]:checked').val();
				if (isPartner== 1) {
					if($("#authUrl").val() == null || $("#authUrl").val() == ""){
					alert("请填写不满足条件时跳转页面名称或路径");
					errors.serviceTypeIds = "请填写不满足条件时跳转页面名称或路径";
					formVal.showErrors(errors);
					return false;
					}
				}
				
			$('#app-form').submit();
		}
	}
});

