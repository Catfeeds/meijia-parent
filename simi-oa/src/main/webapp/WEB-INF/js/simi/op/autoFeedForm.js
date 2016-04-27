

var formVal = $('#autoFeedform').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		title : {
			required: true
		},
		
		content : {
			required: true
		},


	},

	messages: {
		
		title : {
			required: "标题.标题.标题，重要的事情说三遍!"
		},
		
		content: {
			required: "请输入内容!"
		},
	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#autoFeedform')).show();
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

$('.autoFeedform input').keypress(function (e) {
	if (e.which == 13) {
		$("#adForm_btn").click();
		return false;
	}
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
	msgSizeTooLarge: "上传文件大小超过8mb",
	maxFileCount: 10,
	msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
});

$("#adForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#autoFeedform').validate().form()) {
			$('#autoFeedform').submit();
		}
	}
});