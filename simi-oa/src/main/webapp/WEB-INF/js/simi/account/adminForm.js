$('#admin-form').validate({ 
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			rules: {
				name: {
					required: true
				},
				nickname: {
					required: true
				},
				mobile: {
					required: true,
					isMobile : true
				},
				email: {
					required: true,
					email: true 
				},
				username: {
					required: true
				},
				password: {
					required: true
				},
				confirmPassword :{
					required:true
				},
				roleId:{
					required:true
				}
				
			},

			messages: {
				name: {
					required: "请输入姓名"
				},
				nickname: {
					required: "请输入昵称"
				},
				mobile: {
					required: "请输入手机号",
					isMobile : "请输入正确的手机号"
				},
				email: {
					required: "请输入邮箱"
				},
				username: {
					required: "请输入用户名"
				},
				password:{
					required:"请输入密码"
				},
				confirmPassword:{
					required:"请再次输入密码"
				},
				roleId:{
					required:"请选择角色"
				}
			},

			invalidHandler: function (event, validator) { //display error alert on form submit
				$('.alert-error', $('#admin-form')).show();
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
				error.insertAfter(element.parents(".col-sm-5"));
			}

		});

		$('.admin-form input').keypress(function (e) {
			if (e.which == 13) {
				$("#btn_submit").click();
				return false;
			}
		});

		$("#btn_submit").click(function(){
		
			if (confirm("确认要保存吗?")){

				if ($('#admin-form').validate().form()) {
					
					$('#admin-form').submit();
				}
			}
});


		
		