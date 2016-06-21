//样本方式选择触发事件
$("#sampleType").on('change', function() {
	var type = $(this).val();

	var fileType = $("#fileType").val();

	console.log("fileType = " + fileType);
	console.log("type = " + type);

	if (fileType == "word" && type == "textarea") {
		alert("word格式只能选择上传样本文件.");
		return false;
	}
	
	if (fileType == "mht" && type == "textarea") {
		alert("mht格式只能选择上传样本文件.");
		return false;
	}

	if (type == "upload") {
		$("#div-sample-file").css("display", "block");
		$("#div-sample-src").css("display", "none");
	}

	if (type == "textarea") {
		$("#div-sample-file").css("display", "none");
		$("#div-sample-src").css("display", "block");
	}
});

//匹配类型选择触发事件
$("#matchType").on('change', function() {
	var v = $(this).val();
	
	if (v == "reg") {
		$("#section-jsoup-match-reg").css("display", "none");
		$("#section-jsoup-result-match-reg").css("display", "none");
		$("#section-reg-match").css("display", "block");
	}
	
	if (v == "jsoup") {
		$("#section-jsoup-match-reg").css("display", "block");
		$("#section-jsoup-result-match-reg").css("display", "block");
		$("#section-reg-match").css("display", "none");
	}
	
});	

var uploadRuleFileUrl = resumeAppRootUrl + "/uploadRuleFile.json";
//上传文件的事件
Dropzone.autoDiscover = false;
$(".dropzone").dropzone({
    url: uploadRuleFileUrl,
    method:"POST",
    dictDefaultMessage:"拖拽文件上传或者点击",
    dictFileTooBig:"超过文件上限",

    addRemoveLinks: true,
//    dictRemoveLinks: "x",
//    dictCancelUpload: "x",
    maxFiles: 1,
    maxFilesize: 1,
    acceptedFiles: ".html,.htm,.xhtml,.mht,.doc,.docx",
    init: function() {
    	 this.on("sending", function(file, xhr, data) {
             data.append("ruleType", "ruleFrom");
         });
    	
        this.on("success", function(file, response) {
//            console.log("File " + response + "uploaded");
        	var data = response;
        	var filename = response.data;
        	$("#samplePath").val(filename);
        	console.log($("#samplePath").val());
        });
        this.on("removedfile", function(file) {
            console.log("File " + file.name + "removed");
        });
    }
});


//测试jsoup匹配表达式结果展现
$("#reg-match-test-btn").click(function() {
	console.log("reg-match-test-btn click");
	// 1. 先校验是否数据正常
	var c1 = checkSampleSrc();
	
	if (c1 == false) return false;
	
	var fileType = $("#fileType").val();

	var sampleType = $("#sampleType").val();

	var samplePath = $("#samplePath").val();

	var sampleSrc = $("#sampleSrc").val();
	
	var blockRegex = $("#blockRegex").val();
	
	if (blockRegex == "") {
		alert("需要填写匹配表达式.");
		$("#blockRegex").focus();
		return false;
	}
	
	var blockMatchIndex = $("#blockMatchIndex").val();

	var fieldRegex = $("#fieldRegex").val();
	
	var fieldMatchIndex = $("#fieldMatchIndex").val();
	
	
	var params = {};
	params.fileType = fileType;
	params.sampleType = sampleType;
	params.samplePath = samplePath;
	params.sampleSrc =  sampleSrc;
	params.blockRegex = encodeURIComponent(blockRegex);
	params.blockMatchIndex = blockMatchIndex;
	params.fieldRegex = encodeURIComponent(fieldRegex);
	params.fieldMatchIndex = fieldMatchIndex;
	
//	console.log(params);
	
//	console.log($.base64('decode',params.blockRegex));
//	return false;
	$.ajax({
		type : "POST",
		url : resumeAppRootUrl + "/resumeTestRegexParse.json",
		data : params,
		dataType : "json",
		async : false,
		success : function(rdata, textStatus) {
			if (rdata.status == "999") {
				$("#testSuccess").val(0);
				$("#testMatchResult").html("错误信息:" + rdata.data);
				return true;
			}

			if (rdata.status == "0") {
				$("#testMatchResult").html(rdata.data);
				$("#testSuccess").val(1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		},

	});
});

// 测试jsoup匹配表达式结果展现
$("#jsoup-match-test-btn").click(function() {
	console.log("jsoup-match-test-btn click");

	// 1. 先校验是否数据正常
	var c1 = checkSampleSrc();
	
	if (c1 == false) return false;
	
	
	var fileType = $("#fileType").val();

	var sampleType = $("#sampleType").val();

	var samplePath = $("#samplePath").val();

	var sampleSrc = $("#sampleSrc").val();
	
	var findPatten = $("#findPatten").val();
	
	var textOrHtml = $("#textOrHtml").val();

	var attrName = $("#attrName").val();
	
	var removeRegex = $("#removeRegex").val();
	
	var resultRegex = $("#resultRegex").val();
	
	var resultIndex = $("#resultIndex").val();
	
	
	
	var params = {};
	params.fileType = fileType;
	params.sampleType = sampleType;
	params.samplePath = samplePath;
	params.sampleSrc = sampleSrc;
	params.findPatten = findPatten;
	params.textOrHtml = textOrHtml;
	params.attrName = attrName;
	params.removeRegex = removeRegex;
	
	params.resultRegex = "";
	if (resultRegex != "") {
		params.resultRegex = encodeURIComponent(resultRegex);
	}
	
	params.resultIndex = resultIndex;
	
//	console.log(params);
//	return false;
	$.ajax({
		type : "POST",
		url : resumeAppRootUrl + "/resumeTestJsoupParse.json",
		data : params,
		dataType : "json",
		async : false,
		success : function(rdata, textStatus) {
			if (rdata.status == "999") {
				$("#testSuccess").val(0);
				alert(rdata.msg);
				return true;
			}

			if (rdata.status == "0") {
				$("#testMatchResult").html(rdata.data);
				$("#testSuccess").val(1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		},

	});
});

$('.contentForm input').keypress(function(e) {
	if (e.which == 13) {
		$("#btn-save").click();
		return false;
	}
});

$("#btn-save").click(function() {
	if (confirm("确认要保存吗?")) {
		if (!$('#contentForm').validate().form()) return false;
		// 1. 先校验是否数据正常
		var c1 = checkSampleSrc();
		
		if (c1 == false) return false;
		
		var s = $("#testSuccess").val();
		if (s == "0") {
			alert("测试未通过，请填写好正确的解析规则.");
			return false;
		}
		
		var matchCorrect = $("#matchCorrect").val();
		
		if (matchCorrect == "") {
			alert("需要填写正确的匹配结果");
			$("#matchCorrect").focus();
			return false;
		}
		
		$('#contentForm').submit();
	}
});



function checkSampleSrc() {
	// 1. 先校验是否数据正常
	var fileType = $("#fileType").val();

	var sampleType = $("#sampleType").val();

	var samplePath = $("#samplePath").val();

	var sampleSrc = $("#sampleSrc").val();

//	console.log("fileType = " + fileType);
//	console.log("sampleType = " + sampleType);
//	console.log("samplePath = " + samplePath);
//	console.log("sampleSrc = " + sampleSrc);

	if (sampleType == "upload" && samplePath == "") {
		alert("选上传样本文件");
		return false;
	}

	if (sampleType == "textarea" && sampleSrc == "") {
		alert("需要样本源码.");
		$("#sampleSrc").focus();
		return false;
	}
	
	return true;
}


$('#contentForm').validate({
	errorElement: 'span', //default input error message container
	errorClass: 'help-block', // default input error message class
	focusInvalid: false, // do not focus the last invalid input
	rules: {
		
		fromId: {
			required: true
		},
		
	},

	messages: {
		
		fromId: {
			required: "必选项"
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
