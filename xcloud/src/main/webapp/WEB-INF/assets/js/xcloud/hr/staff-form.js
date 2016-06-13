
//$('input[type=file]').on('change', function() {
//		var fileNames = '';
//		$.each(this.files, function() {
//			fileNames += '<span class="am-badge">' + this.name + '</span> ';
//		});
//	$(this).parent().parent().find('#file-list').html(fileNames);
//});

$("#jobNumber").on('blur', function(e) {
	var v = $("#jobNumber").val();
	
	if (v != '') {
		v = pad(v, 4);
		$("#jobNumber").val(v);
	}
});

//校验手机号是否重复
$("#mobile").on('blur', function(e) {
	
	var mobile = $("#mobile").val();
	
	if (!verifyMobile(mobile)) {
		return false;
	}
	
	var id = $("#id").val();
	if (id != "" && id > 0) {
		return false;
	}
	var params = {};
	params.mobile = mobile;
	// 发送验证码
	$.ajax({
		type : "GET",
		url : "/xcloud/staff/get-by-mobile.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			var vo = data.data;
			if (vo) {
				
				$("#name").val(vo.name);
				if (vo.id > 0) {
					alert("手机号不能重复,该用户已经为贵司员工,不需要重复添加.");
					$('#btn-staff-submit').attr("disabled","disabled"); // 禁用
					return false;
				}
			}
			$('#btn-staff-submit').removeAttr("disabled");
		}
	})
	
});

// 校验身份证号 是否重复
$("#idCard").on('blur', function(e) {
	
	var idCard = $("#idCard").val();
	
	if (!isIdCardNo(idCard)) {
		return false;
	}
	
	var id = $("#id").val();
	if (id != "" && id > 0) {
		return false;
	}
	var params = {};
	params.idCard = idCard;
	
	$.ajax({
		type : "GET",
		url : "/xcloud/staff/get-by-idCard.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			var vo = data.data;
			if (vo) {
				
				$("#name").val(vo.name);
				if (vo.id > 0) {
					alert("身份证号不能重复,该用户已经为贵司员工,不需要重复添加.");
					$('#btn-staff-submit').attr("disabled","disabled"); // 禁用
					return false;
				}
			}
			$('#btn-staff-submit').removeAttr("disabled");
		}
	})
	
});




// 提交表单
$("#btn-staff-submit").on('click', function(e) {
	var form = $('#staff-form');
	
	var formValidity = $('#staff-form').validator().data('amui.validator').validateForm().valid;
	
	if (formValidity) {
		// done, submit form
		console.log("ok");
		form.submit();
	} else  {
		// fail
		console.log("fail");
	};
});




$("#headImgDiv").dropzone({
    url:".json",
    method:"POST",
    dictDefaultMessage:"上传 ",
    dictFileTooBig:"超过文件上限",
    addRemoveLinks: true,
    dictRemoveLinks: "x",
    dictCancelUpload: "x",
    dictRemoveFile:"删除",
    
    maxFiles: 1,
    maxFilesize: 1,
    dictMaxFilesExceeded:"超出个数限制",
    
    acceptedFiles: ".html,.htm,.xhtml,.mht,.doc,.docx,.jpg",
    
    previewsContainer:"#headImgDisplay",
    previewTemplate:"<div class='dz-preview dz-file-preview'>"+
						  "<div class='dz-details'>"
						 +"<div class='dz-filename'><span data-dz-name></span></div>"
						 +"<div class='dz-size' data-dz-size></div>"
						 +   "<img data-dz-thumbnail />"
						 + "</div>"
					+"</div>",
    init: function() {
    	 this.on("sending", function(file, xhr, data) {
             data.append("ruleType", "ruleFrom");
         });
    	
        this.on("success", function(file) {
            console.log("File " + file.name + "uploaded");
        });
        this.on("removedfile", function(file) {
            console.log("File " + file.name + "removed");
        });
    }
});




