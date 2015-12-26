

var partnerId = getUrlParam("partner_id");
$("#partnerId").val(partnerId);
var $partnerListPage = 1;

function partnerGetList (page) {
	var ajaxUrl = appRootUrl + "partner/get_partnerUser_list.json?partner_id="+partnerId;
	ajaxUrl = ajaxUrl + "&page="+page;
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999") return false;
			var partnerUserVo = data.data.list;
			console.log(partnerUserVo);
			var html = $('#partner-list-part').html();
			//var resultHtml = '';

			var partnerServiceTypeHtml = "";
			for(var i=0 ; i < partnerUserVo.length; i++){
				
				var htmlPart = html;
				var headImg = '<img alt="" src="'+partnerUserVo[i].head_img+'">';
				htmlPart = htmlPart.replace('{headImg}', headImg);
				//参数
				htmlPart = htmlPart.replace('{id}',partnerUserVo[i].id);
				htmlPart = htmlPart.replace('{partnerId}',partnerUserVo[i].partner_id);
			//	htmlPart = htmlPart.replace('{userId}',partnerUserVo[i].user_id);
				
				htmlPart = htmlPart.replace('{name}',partnerUserVo[i].name);
				htmlPart = htmlPart.replace('{mobile}',partnerUserVo[i].mobile);
				htmlPart = htmlPart.replace('{companyName}',partnerUserVo[i].company_name);
				htmlPart = htmlPart.replace('{cityAndRegion}',partnerUserVo[i].city_and_region);
				htmlPart = htmlPart.replace('{serviceTypeName}',partnerUserVo[i].service_type_name);
				htmlPart = htmlPart.replace('{responseTimeName}',partnerUserVo[i].response_time_name);
				htmlPart = htmlPart.replace('{introduction}',partnerUserVo[i].introduction);
				//$('a').attr('href','http://www.google.com') 
				htmlPart = "<a href=\"store-partnerUser-form.html?id=0&partner_id="
					+partnerUserVo[i].partner_id+"&user_id="+partnerUserVo[i].user_id+"\"> " + htmlPart + "</a>";
				
				/*<a href="store-partnerUser-form.html?id=0&&partner_id={partnerId}&&user_id={userId}"><button
				type="button" class="am-btn am-btn-danger am-btn-block"> <i class="am-icon-plus-circle"></i> 新增人员
			</button></a>	*/
				var tags = partnerUserVo[i].user_tags;
				console.log(tags);
				var tagHtml = "";
				for (var j = 0; j < tags.length; j++) {
					var tagId = tags[j].tag_id;
					var tagName = tags[j].tag_name;
					tagHtml += "<button class=\"am-btn am-btn-warning am-round\" id=\""+tagId+"\" type=\"button\" >" + tagName + "</button>&nbsp;";
				}
				htmlPart = htmlPart.replace('{tagNames}',tagHtml);
				partnerServiceTypeHtml += htmlPart;
			}	
				$("#scroller").append(partnerServiceTypeHtml);
			
			//如果第一页并且返回数据等于10条，则可以显示加载更多按钮
			if (page == 1 && partnerUserVo.length >= 10) {1
				$("#btn-get-more").css('display','block'); 
			}
			
			//如果为第二页以上，并且返回数据小于10条，则不显示加载更多按钮
			if (page > 1 && partnerUserVo.length < 10) {
				$("#btn-get-more").css('display','none'); 
			}
		}
	});
}

//默认加载第一页
partnerGetList(1);


$("#btn-get-more").on('click', function(e) {
	$partnerListPage = $partnerListPage + 1;
	partnerGetList($partnerListPage);
});

function clickJieDanList (partnerId) {
	alert(partnerId.partnerId);
	console.log(partnerId.partnerId);
};



