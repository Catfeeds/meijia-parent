/*

var formVal = $('#appHelp-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		title : {
			required: true
		},
		
		serialNo: {
			required: true,
     		digits:true,
			range:[0,255]
		},
		iconUrl: {
			required: true
		},
		
	},

	messages: {
		
		title : {
			required: "标题.标题.标题.重要的事情说三遍!"
		},
		serialNo: {
			required: "请输入编号。",
			digits:"请输入整数",
			range:"输入值必须介于 0 和 255之间"

		},
		iconUrl: {
			required: "请选择上传路径。"
		}
	

	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#appHelp-form')).show();
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

});*/

$('.appHelp-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#adForm_btn").click();
		return false;
	}
});

$("#cardIconFile").fileinput({
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

$('#cardIcon').change(function(){
	 $("#img_url_new").text($("#imgUrl").val());
});

$("#adForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		//if ($('#appHelp-form').validate().form()) {
			
			$('#appHelp-form').submit();
		//}
	}
});

