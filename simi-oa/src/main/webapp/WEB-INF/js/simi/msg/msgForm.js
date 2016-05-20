$('#msg-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		title : {
			required : true
		},
		summary : {
			required : true
		},
		content:{
			required: true
		},
		category:{
			category:"category"
		},
		userType:{
			userType:"userType"
		},
		appType:{
			appType:"appType"
		}
		
	},

	messages : {
		title : {
			required : "请输入消息标题。"
		},
		summary : {
			required : "请输入消息摘要。"
		},
		content : {
			required : "请输入消息内容。"
		},
	},

	invalidHandler : function(event, validator) { // display error alert on
													// form submit
		$('.alert-error', $('#msg-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

//下拉框 校验
$.validator.addMethod("category",function(value,elements){
	
	var index = elements.selectedIndex;
	
	if(index != 0){
		return true;
	}
},"请选择跳转类型");

$.validator.addMethod("userType",function(value,elements){
	
	var index = elements.selectedIndex;
	
	if(index != 0){
		return true;
	}
	
},"请选择用户类型");

$.validator.addMethod("appType",function(value,elements){
	
	var index = elements.selectedIndex;
	
	if(index != 0){
		return true;
	}
	
},"请选择应用类型");




$('.msg-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#addMsg_btn").click();
		return false;
	}
});




$('.form_datetime').datetimepicker({
	format : "yyyy-mm-dd hh:ii",
	language : "zh-CN",
	autoclose : true,
	startView : 2,
	todayBtn:true,
	todayHighlight:true,
	minuteStep:1,
	startDate:new Date()
});

$("#previewMsg_btn").click(function() {
	
	var title = $("#title").val();
	var content = $("#some-textarea").val();
	var summary = $("#summary").val();
	$.ajax({
        type:"post",
        url:"/"+appName+"/interface-promotion/preview-msg.json",
        dataType:"json",
        cache:false,
        data:"title="+title+"&summary="+summary+"&content="+content,
        success :function(data){
        	var data = data.data;
        	window.open(appRootUrl+data);
        },
	    error : function(){
	    }
    });
});



/*
 *  选择 用户类型--> 全部用户，动态添加 注释。
 */
$("#userType").on("change",function(){
	
	if($(this).val() == 3){
		$("#useTypeTip").show();
	}else{
		$("#useTypeTip").hide();
	}
	 
});

/*
 * 选择 是否可用
 */
$("input[name='isEnable']").on("change",function(){
	
	var thisVal = $(this).val();
	
	if(thisVal == 0){
		$("#sendWayDiv").hide();
	}
	
	if(thisVal == 1){
		$("#sendWayDiv").show();
	}
})


/*
 *  选择 发送方式--测试发送
 */
$("#sendWay").on("change",function(){
	
	if($(this).val() == 1){
		$("#sendWayTip").hide();
	}else{
		$("#sendWayTip").show();
	}
});

/*
 * 提交表单
 * 
 */

$("#editMsg_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#msg-form').validate().form()) {
			
			var msg =  $("#msg-form").serializeArray();
			
			var isEnable = $("input[name='isEnable']:checked").val();
			
			$("#sendStatusTip").show();
			if(isEnable == 1){
				$("#sendStatusTip").find("font").text("发送中...");
			}else{
				$("#sendStatusTip").find("font").text("消息不可用,保存但不发送");
			}
			
			$.ajax({
				type:"post",
				url: "saveMsgForm.json",
				data: msg,
				dataType:"json",
				success:function(datas,sta,xhr){
					
					if(sta == "100"){
						//选择的不可用
						$("#sendStatusTip").find("font").text("已保存");
					}else{
						$("#sendStatusTip").find("font").text("已发送");
					}
					
					$("#sendStatusTip").hide(2000);
					$("#editMsg_btn").text("再次发送");
					
					//状态改为已发送
					$("#isSend").val(1);
					
				}
				
			})
		}else{
			return false;
		}
	}
});

