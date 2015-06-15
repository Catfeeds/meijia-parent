$.validator.addMethod("uniqueName", function(value, element) {
  var response;
  $.ajax({
      type: "POST",
      url:"/simi-oa/interface-sec/check-name-dumplicate.json", //发送给服务器的url
      data: "username="+value + "&sec="+$('#id').val(),
      dataType:"json",
      async: false,
	  success: function(msg) {
		  response = msg.data;
	  }

  });

  //不存在，则返回true
  if(response == false) return true;

  //如果存在，则返回false;
  return false;

}, "");
$('#sec-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules : {
		username : {
			required : true,
			uniqueName : true
		},
		password : {
			required : true		
		},
		name : {
			required : true			
		},
		mobile : {
			required : true
		},
		birthDay:{
			required:true
		},
		headImg : {
			required : true

		},
		status : {

			required : true
		
		},

	},

	messages : {
		username : {
			required : "请输入登录名。",
			uniqueName : "名称已经存在"
		},
		password : {
			required : "密码不可以为空"
		
		},
		name : {
			required : "请输入名称。"
			
		},
		mobile : {
			required : "请输入手机号。"
		},
		birthDay : {
			required : "请输入出生日期。"
		},
		headImg:{
			required: "请选择上传路径"
		
		},
		status : {
			required : "请选择状态"
			

		},

	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#sec-form')).show();
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

$('.sec-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#addstaff_btn").click();
		return false;
	}
});

$("#headImg").fileinput({
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


/*$("#adForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#dict-form').validate().form()) {
			$('#dict-form').submit();
		}
	}
});*/

