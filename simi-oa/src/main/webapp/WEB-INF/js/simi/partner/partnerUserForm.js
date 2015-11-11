var formVal = $('#partner-user-form').validate({
	errorElement : 'span', //default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore: [],
	rules : {
		name : {
			required : true
		},
		
		mobile: {
			required: true,
			isMobile : true
		},
						
		provinceId : {
			required: true,
			isIntGtZero : true,
		}, 

		cityId : {
			required: true,
			isIntGtZero : true,
		}, 
		
		regionId : {
			required: true,
			isIntGtZero : true,
		}, 
		serviceTypeId: {
			required: true,
		},
		
		responseTime: {
			required: true,
		},
		
		introduction: {
			required: true,
		},
		
		tagIds: {
			required: true,
		},
	},

	messages : {
		name : {
			required : "请输入姓名."
		},
		
		mobile: {
			required: "请输入手机号.",
			isMobile : "请输入正确的手机号"
		},
				
		
		provinceId : {
			required: "请选择所在省份",
			isIntGtZero : "请选择所在省份"
		}, 

		cityId : {
			required: "请选择所在城市",
			isIntGtZero : "请选择所在城市"
		}, 
		
		regionId : {
			required: "请选择所在区县",
			isIntGtZero : "请选择所在区县"
		}, 

		serviceTypeId: {
			required: "请选择服务类别.",
		},
		
		responseTime: {
			required: "请选择服务响应时间.",
		},
		
		introduction: {
			required: "请输入个人简介",
		},
		
		tagIds: {
			required: "请选择标签",
		},
	},

	invalidHandler : function(event, validator) { //display error alert on form submit
		$('.alert-error', $('#partner-user-form')).show();
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

$('.partner-user-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#btn_submit").click();
		return false;
	}
});

$("#btn_submit").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#partner-user-form').validate().form()) {
			
			var mobile = $("#mobile").val();
			var userId = $("#userId").val();
			console.log(validateUserMobile(mobile));
			if (!validateUserMobile(mobile, userId)) {
				var errors = {};
				errors.mobile = "手机号码已经存在";
		    	formVal.showErrors(errors);
				return false;
			}
			
			$('#partner-user-form').submit();
		}
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

$("#introduction").keyup(function(){
	   var len = $(this).val().length;

	   if(len > 200){
		   $(this).val($(this).val().substring(0,200));
	   }
	   var num = 200 - len;
	   $("#introduction").text(num);
	   
//	   $("#introduction_limit").text(len + "/200");
});

//处理标签选中的样式.
$("input[name='tagName']").click(function() {
	
	var tagId  = $(this).attr("id");

	if($(this).attr("class")=="btn btn-default"){
		$(this).attr("class","btn btn-success");
		
	}else{
		$(this).attr("class","btn btn-success");
		$(this).attr("class","btn btn-default");
	}
	
	setTagIds();

});


function setTagIds() {
	var tagIds = $("#tagIds");
	var tagSelected = "";
	//处理选择按钮的情况
	$("input[name=tagName]").each(function(key, index) {
		console.log($(this));
		if ($(this).attr("class") == "btn btn-success") {
			tagSelected = tagSelected + $(this).attr("id") + ",";
		}
	});	

	if (tagSelected != "") {
		tagSelected = tagSelected.substring(0, tagSelected.length - 1);
	}

	tagIds.val(tagSelected);
}


function setTagButton() {
	var tagIds = $("#tagIds");
	if (tagIds.val() == "") return false;
	//处理选择按钮的情况
	$("input[name=tagName]").each(function(key, index) {
		var tagId = $(this).attr("id");
		if (tagIds.val().indexOf(tagId + ",") >= 0) {
			$(this).attr("class","btn btn-success");
		}
	});	
}


