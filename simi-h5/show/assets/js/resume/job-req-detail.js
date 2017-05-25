
var partnerUserId = getUrlParam("user_id");

if(partnerUserId == undefined || partnerUserId == null || partnerUserId == ""){
	partnerUserId = 0;	
}

var resumeId = getUrlParam("resume_id");

//页面进入。加载 发布者、城市
function initDetailPage(){
	
	var ajaxUrl = appRootUrl + "resume/hr_job_hunter_detail.json?partner_user_id="
								+partnerUserId+"&resume_id="+resumeId;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == 0) {
				
				var result = data.data;
				
				$("#title").text(result.title);
				$("#userName").append("发布人:	"+result.user_name);
				$("#limitDay").append("截止日:	"+result.limit_day_str);
				$("#cityName").append("所在城市:	"+result.city_name);
				$("#jobName").append("职位:	"+result.job_name);
				$("#reward").text("推荐奖金: " + result.reward + " 元");
				$("#salaryName").text(result.salary_name);
				
				$("#jobRes").text(result.job_res);
				$("#jobReq").text(result.job_req);
				$("#remarks").text(result.remarks);
				
				var userId = result.user_id;
				if (userId != partnerUserId) {
					//隐藏 编辑信息按钮
					$("#publishJobDiv").hide();
				}
				$("#resumeId").val(result.id);
			}
		}
	});
}

initDetailPage();

//编辑新需求
$("#publishJobHunter").on("click",function(){
	var resumeId = $("#resumeId").val();
	var url = "job-req-publish.html?user_id=" + partnerUserId + "&resume_id=" + resumeId;
	
	window.location.href = url;
	
});

