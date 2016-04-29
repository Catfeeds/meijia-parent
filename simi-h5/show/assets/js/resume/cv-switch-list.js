var partnerUserId = getUrlParam("user_id");

if (partnerUserId == undefined || partnerUserId == '') {
	partnerUserId = 0;
	
}

if (partnerUserId == 0) {
	$("#btn-change").css("display", "none");
}

var $partnerListPage = 1;


function resumeList (page) {
	var ajaxUrl = appRootUrl + "resume/hr_resume_change_list.json?partner_user_id="+partnerUserId;
	ajaxUrl = ajaxUrl + "&page="+page;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {

			if (data.status == "999"){
				alert(data.msg);
				return;
			}
			
			var resumeListVo = data.data;
			var html = $('#resumeListModel').html();
            
			var partnerServiceTypeHtml = "";
			
			for(var i=0 ; i < resumeListVo.length; i++){
				
				var htmlPart = html; 
				
				htmlPart = htmlPart.replace('{title}',resumeListVo[i].title);
				htmlPart = htmlPart.replace('{cityName}',resumeListVo[i].city_name);
				htmlPart = htmlPart.replace('{endTimeStr}',resumeListVo[i].end_time_str);
				
				
				htmlPart = htmlPart.replace('{userId}',resumeListVo[i].user_id);
				htmlPart = htmlPart.replace('{resumeId}',resumeListVo[i].id);
				
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



//点击加载更多
$("#btn-get-more").on('click', function(e) {
	$partnerListPage = $partnerListPage + 1;
	resumeList($partnerListPage);
});


//点击跳转详情
function resumeDetailForm(userId, resumeId) {
	var url = "cv-req-detail.html?user_id=" + userId;
		url+= "&resume_id=" + resumeId;
		
	window.location.href = url;
}

//发布新需求
$("#publishMyNeed").on("click",function(){
	
	var url = "cv-req-publish.html?user_id=" + partnerUserId;
	
	window.location.href = url;
	
});


