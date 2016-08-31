$('#partner-service-type-form').validate({ 
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			rules: {
				name: {
					required: true
				},
				parentId :{
					required:true
				},
			},

			messages: {
				name: {
					required: "请输入提供的服务名称"
				},
				parentId:{
					required:"请选择服务的位置"
				},
			},

			invalidHandler: function (event, validator) { //display error alert on form submit
				$('.alert-error', $('#partner-service-type-form')).show();
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

		$('.partner-service-type-form input').keypress(function (e) {
			if (e.which == 13) {
				$("#btn_submit").click();
				return false;
			}
		});

		$("#btn_submit").click(function(){
			if (confirm("确认要保存吗?")){
				if ($('#partner-service-type-form').validate().form()) {
					console.log("parent_id = " + $("#parentId").val());
					$('#partner-service-type-form').submit();
				}
		    }
		});