
var partnerUserId = getUrlParam("user_id");

//隐藏域
$("#publishUserId").val(partnerUserId);

//页面进入。加载 发布者、城市
function initFormPage(){
	
	var ajaxUrl = appRootUrl + "resume/hr_resume_change_form.json?partner_user_id="+partnerUserId;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == 0) {
				
				var result = data.data;
				
				$("#publishUser").text(result.user_name);
				$("#publishCity").text(result.city_name);
				
				//隐藏域
				$("#publishUserName").val(result.user_name);
				$("#publishCityName").val(result.city_name);
				
				
				for(var map in result.time_map){
					
					var html = "";
					
					if(map == 0){
						html = "<option value='"+map+"' selected>"+result.time_map[map] +"</option>";
					}else{
						html = "<option value='"+map+"'>"+result.time_map[map] +"</option>";
					}
					
					
					$("#publishTimeSelect").append(html);
				}
			}
		}
	});
}

//1. 页面加载执行
initFormPage();


//2. 点击提交。校验并提交

$("#submitPublish").on('click',function(){
	
//	var $form = $('#cvReqPublishForm');
//	var validator = $form.data('amui.validator');
	
//	var formValidity = validator.validateForm().valid;
//	$.when(formValidity).then(function() {
//		
//		submitPublish();
//	}, function() {
//		//fail
//	});
	var formValidity = $('#cvReqPublishForm').validator().data('amui.validator').validateForm().valid;
	
	if(formValidity){
		submitPublish();
	}
	
});

function submitPublish(){
	
	var params = {};
	
	params.user_id = $("#publishUserId").val();
	
	params.user_name = $("#publishUserName").val();
	params.city_name = $("#publishCityName").val();
	
	params.publish_title = $("#publishTitle").val();
	params.publish_limit_day = $("#publishTimeSelect").find("option:selected").val();
	params.publish_need_content = $("#publishNeedContent").val();
	params.publish_link = $("#publishLink").val();
	
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "/resume/hr_resume_change_form.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			var userId = $("#publishUserId").val()
			var url = "cv-switch-list.html?user_id="+userId;
			console.log(url);
			
			window.location.href = url;
		},
		error : function() {
			return false;
		}
	});
	
}




