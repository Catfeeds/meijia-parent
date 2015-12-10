

var formVal = $('#dict-form').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		title : {
			required: true
		},
		
		No: {
			required: true,
     		digits:true,
			range:[0,255]

		},
		imgUrl: {
			required: true
		},
		gotoType: {
			required: true
		},

		adType: {
			required: true
		}


	},

	messages: {
		
		title : {
			required: "标题.标题.标题，重要的事情说三遍!"
		},
		No: {
			required: "请输入编号。",
			digits:"请输入整数",
			range:"输入值必须介于 0 和 255之间"

		},
		imgUrl: {
			required: "请选择上传路径。"
		},
		gotoType: {
			required: "请选择跳转类型。"
		},


	},

	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-error', $('#dict-form')).show();
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

$('.dict-form input').keypress(function (e) {
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
	msgSizeTooLarge: "上传文件大小超过8mb"
});

$('#imgUrl').change(function(){
	 $("#img_url_new").text($("#imgUrl").val());
});

$("#adForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#dict-form').validate().form()) {
			console.log($("#serviceTypeIds").val());
			var gotoType = $('#gotoType').val();

			var errors = {};
			if (gotoType.indexOf("h5") >= 0 ) {
				if ($("#gotoUrl").val() == null || $("#gotoUrl").val() == "" ) {
					alert("请输入跳转地址");
					errors.gotoUrl = "请输入跳转地址";
					formVal.showErrors(errors);
					return false;
				}
				
			}
			
			if (gotoType == "app" || gotoType == "h5+list" ) {
				if ($("#serviceTypeIds").val() == null || $("#serviceTypeIds").val() == "") {
					alert("请选择显示服务大类");
					errors.serviceTypeIds = "请选择显示服务大类";
					formVal.showErrors(errors);
					return false;
				}
				
			}
			
//			$('#dict-form').submit();
		}
	}
});

$('#adType option').each(function(){
	var selectedAdType = "," +  $("#adTypeSelected").val() + ",";
	var v = "," +  $(this).val() + ",";
	if (selectedAdType.indexOf(v) >= 0) {
		$(this).attr('selected', true);
     }
});

$("#adType").multiSelect({   
	keepOrder: true,
	selectableHeader: "<div class='custom-header'>可选</div>",
	selectionHeader: "<div class='custom-header'>已选</div>",
});



$('#serviceTypeIds option').each(function(){
	
	var serviceTypeIdsSelected = "," + $("#serviceTypeIdsSelected").val() + ",";
	var v =  "," + $(this).val()  + ",";
	console.log(serviceTypeIdsSelected + "----" + v);
	if (serviceTypeIdsSelected.indexOf(v) >= 0) {
		console.log($(this).val());
		$(this).attr('selected', true);
     }
});

$("#serviceTypeIds").multiSelect({   
	keepOrder: true,
	selectableHeader: "<div class='custom-header'>可选</div>",
	selectionHeader: "<div class='custom-header'>已选</div>",
});





////查看有选择的checkbox
//$("input[name='adType']:checkbox").each(function(){
//	var selectedAdType = $("#adTypeSelected").val();
//	var v = $(this).attr('value');
//	if (selectedAdType.indexOf(v + ",") >= 0) {
//		$(this).attr('checked', true);
//	}
//	
//});

