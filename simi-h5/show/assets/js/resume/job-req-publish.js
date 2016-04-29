
var partnerUserId = getUrlParam("user_id");


//隐藏域
$("#publishUserId").val(partnerUserId);

//页面进入。加载 发布者、城市
function initFormPage(){
	
	var ajaxUrl = appRootUrl + "resume/hr_job_hunter_form.json?partner_user_id="+partnerUserId;
	
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

$("#jobHunterSubmit").on('click',function(){
	
	var formValidity = $('#jobHunterReqPublishForm').validator().data('amui.validator').validateForm().valid;
	
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
	
	params.reward = $("#reward").val();
	params.publish_job_res = $("#jobRes").val();
	params.publish_job_req = $("#jobReq").val();
	params.remarks = $("#remarks").val();
	
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "/resume/hr_job_hunter_form.json",
		data : params,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			var userId = $("#publishUserId").val()
			var url = "job-reward-list.html?user_id="+userId;
			window.location.href = url;
		},
		error : function() {
			return false;
		}
	});
	
	
}




