$('#contentForm').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		
		fromId: {
			required: true
		},
		
		type: {
			required: true
		},
		
		name: {
			required: true
		},
	},

	messages: {
		
		fromId: {
			required: "必填项"
		},
		
		type: {
			required: "必填项"
		},
		
		name: {
			required: "必填项"
		},
	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#contentForm')).show();
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

$("#type").on('change', function() {
	var type = $(this).val();
	
	if (type == "") return false;
	
	var params = {};
	params.type = type;
	$.ajax({
		type : "GET",
		url : resumeAppRootUrl + "/hrDict/getParents.json",
		data : params,
		dataType : "json",
		async : false,
		success : function(rdata, textStatus) {
			if (rdata.status == "0") {
				var pid = $("#pid_value").val();
				var $options = '<option value="0">无上级</option>';
				//$optionList = "";
				$.each(rdata.data, function(i, obj) {
					if (obj.id == pid) {
						$options += '<option value="'+obj.id+'" selected>' + obj.name + "</option>";
					} else {
						$options += '<option value="'+obj.id+'">' + obj.name + "</option>";
					}

				});
				
				$("#pid").html($options);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		},

	});
	
	
});


$('.contentForm input').keypress(function (e) {
	if (e.which == 13) {
		$("#btn-save").click();
		return false;
	}
});

$("#btn-save").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#contentForm').validate().form()) {
			$('#contentForm').submit();
		}
	}
});

