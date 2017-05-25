var partnerUserId = getUrlParam("user_id");

if(partnerUserId == undefined || partnerUserId == null || partnerUserId == ""){
	partnerUserId = 0;
	
	//隐藏 发布信息按钮
	$("#publishJobDiv").hide();
	
}

var $partnerListPage = 1;


function resumeList (page) {
	var ajaxUrl = appRootUrl + "resume/hr_job_hunter_list.json";
	
	var jobId = $("#jobId").val();
	var cityName = $("#cityName").val();
	var params = {};
	params.partner_user_id = partnerUserId;
	params.page = page;
	console.log("jobId = " + jobId);
	if (jobId != undefined && jobId != "") {
		params.job_id = jobId;
	}
	
	if (cityName != undefined && cityName != "") {
		params.city_name = cityName;
	}
	console.log(params);
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		data : params,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {

			if (data.status == "999"){
				alert(result.msg);
				return;
			}
			
			var resumeListVo = data.data;
			var html = $('#resumeListModel').html();
            
			var partnerServiceTypeHtml = "";
			
			for(var i=0 ; i < resumeListVo.length; i++){
				
				var htmlPart = html; 
				
				htmlPart = htmlPart.replace('{title}',resumeListVo[i].title);
				htmlPart = htmlPart.replace('{cityName}',resumeListVo[i].city_name);
				htmlPart = htmlPart.replace('{salaryName}',resumeListVo[i].salary_name);
				htmlPart = htmlPart.replace('{endTimeStr}',resumeListVo[i].end_time_str);
				
//				htmlPart = htmlPart.replace('{userId}',resumeListVo[i].user_id);
				htmlPart = htmlPart.replace('{userId}', partnerUserId);
				htmlPart = htmlPart.replace('{resumeId}',resumeListVo[i].id);
				
				
				if(resumeListVo[i].end_time_flag == "disable"){
					//已 截止
					htmlPart = htmlPart.replace('{rewardCss}',"<span class='hd_tag_js'>悬赏已截止</span>");
				}else{
					htmlPart = htmlPart.replace('{rewardCss}',"<span>推荐奖金："+resumeListVo[i].reward+"元</span>");
				}
				
				partnerServiceTypeHtml += htmlPart;
			}
			
			$("#displayContainer").append(partnerServiceTypeHtml);
			
			//如果第一页并且返回数据等于10条，则可以显示加载更多按钮
			if (page == 1 && resumeListVo.length >= 10) {
				$("#btn-get-more").css('display','block'); 
			}
			
			//如果为第二页以上，并且返回数据小于10条，则不显示加载更多按钮
			if (page > 1 && resumeListVo.length < 10) {
				$("#btn-get-more").css('display','none'); 
			}
			
		}
	});
	
}

//页面加载，分页结果 
resumeList(1);


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
		cache : true,
		success : function(data) {
			
			if (data.status == 0) {
				
				var result = data.data;
				var liHtml = "";
				$.each(result, function(i, item) {
					liHtml+="<li class=\"am-divider\"></li>";
					liHtml+=" <li><a href=\"#\" onclick=\"changeJob("+item.id+", '"+ item.name +"')\">"+item.name+"</a></li>";
				});
				$("#jobListUl").append(liHtml);
			}
		}
	});
}

getJobs();
//点击加载更多
$("#btn-get-more").on('click', function(e) {
	$partnerListPage = $partnerListPage + 1;
	resumeList($partnerListPage);
});


//点击跳转详情
function resumeDetailForm(userId, resumeId) {
	var url = "job-req-detail.html?user_id=" + userId;
		url+= "&resume_id=" + resumeId;
		
	window.location.href = url;
}

//发布新需求
$("#publishJobHunter").on("click",function(){
	
	var url = "job-req-publish.html?user_id=" + partnerUserId;
	
	window.location.href = url;
	
});

function changeJob(jobId, jobName) {
	console.log("changeJob == " + jobId);
	if (jobId == undefined ) return false;
	$("#jobId").val(jobId);
	$("#selectJobTitle").html(jobName);
	$("#displayContainer").html("");
	$("#job-dropdown").dropdown('close');
	resumeList(1);
}

function changeArea(cityName) {
	if (cityName == undefined ) return false;
	$("#cityName").val(cityName);
	if (cityName == "") {
		$("#seletAreaTitle").html("地区");
	} else {
		$("#seletAreaTitle").html(cityName);
	}
	
	$("#displayContainer").html("");
	$("#city-dropdown").dropdown('close');
	resumeList(1);
}


