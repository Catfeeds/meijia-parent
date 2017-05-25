
var partnerUserId = getUrlParam("user_id");
$("#publishUserId").val(partnerUserId);
var resumeId = getUrlParam("resume_id");
if (resumeId == undefined || resumeId == '') resumeId = 0;
$("#resumeId").val(resumeId);



//页面进入。加载 发布者、城市
function initFormPage(){
	var params = {};
	params.partner_user_id = partnerUserId;
	params.resume_id = resumeId;
	var ajaxUrl = appRootUrl + "resume/hr_job_hunter_form.json";
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		data: params,
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
				
				//如果是编辑，则默认选中
				var resumeId = result.id;
				if (resumeId > 0) {
					$("#publishTitle").val(result.title);
					$("#publishUserId").val(result.user_id);
					$("#publishUserName").val(result.name);
					$("#publishCityName").val(result.city_name);
					$("#jobId").val(result.job_id);
					$("#salaryId").val(result.salary_id);
					console.log(result.limit_day);
					$("#publishTimeSelect").val(result.limit_day);
					$("#reward").val(result.reward);
					$("#jobRes").val(result.job_res);
					$("#jobReq").val(result.job_req);
					$("#remarks").val(result.remarks);
				}
			}
		}
	});
	
	
}

function getJobs() {
	var ajaxUrl = resumeAppUrl + "/hrDict/getByOption.json";
	var pids = "5002000,3010000";
	var params = {};
	params.pids = pids;
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		data : params,
		dataType : "json",
		async : false,	
		cache : true,
		success : function(data) {
			
			if (data.status == 0) {
				
				var result = data.data;
				var options = "";
				$.each(result, function(i, item) {
					options+= "<option value='"+item.id+"'>"+ item.name +"</option>";
				});
				$("#jobId").append(options);
			}
		}
	});
}

function getSalary() {
	var ajaxUrl = resumeAppUrl + "/hrDict/getByOption.json";
	
	var params = {};
	params.type = "salary";
	params.from_id = 1;
	params.not_code = "0000000000";
	params.order_by_str ="id desc";
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		data : params,
		dataType : "json",
		async : false,	
		cache : true,
		success : function(data) {
			
			if (data.status == 0) {
				
				var result = data.data;
				var options = "";
				$.each(result, function(i, item) {
					options+= "<option value='"+item.id+"'>"+ item.name +"</option>";
				});
				$("#salaryId").append(options);
			}
		}
	});
}

//1. 页面加载执行
getJobs();
getSalary();
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
	params.resume_id = $("#resumeId").val();
	params.user_id = $("#publishUserId").val();
	
	params.user_name = $("#publishUserName").val();
	params.city_name = $("#publishCityName").val();
	
	params.publish_title = $("#publishTitle").val();
	params.job_id = $("#jobId").val();
	params.salary_id = $("#salaryId").val();
	params.publish_limit_day = $("#publishTimeSelect").find("option:selected").val();
	
	params.reward = $("#reward").val();
	params.publish_job_res = $("#jobRes").val();
	params.publish_job_req = $("#jobReq").val();
	params.remarks = $("#remarks").val();
	
	console.log(params);
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




