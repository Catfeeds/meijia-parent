
var partnerUserId = getUrlParam("user_id");

var resumeId = getUrlParam("resume_id");


//页面进入。加载 发布者、城市
function initDetailPage(){
	
	var ajaxUrl = appRootUrl + "resume/hr_resume_detail.json?partner_user_id="
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
				
				$("#resumeTitle").text(result.title);
				$("#userName").append("发布人:	"+result.user_name);
				$("#limitDay").append("有效时长:	"+result.limit_day_str);
				$("#cityName").append("所在城市:	"+result.city_name);
				$("#needContent").text(result.content);
				$("#resumeLink").text(result.link);
				
			}
		}
	});
}

initDetailPage();



