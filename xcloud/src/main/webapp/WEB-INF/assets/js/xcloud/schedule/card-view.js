var $commentListPage = 1;

function getCommentList (page) {
	var cardId = $("#cardId").val();
	var userId = $("#userId").val();
	var params = {};
	params.user_id = userId;
	params.card_id = cardId;
	params.page = page;
	
	var ajaxUrl = appRootUrl + "card/get_comment_list.json";
	
	$.ajax({
		type : "GET",
		url : ajaxUrl,
		dataType : "json",
		data : params,
		cache : true,
		async : false,	
		success : function(data) {
			
			if (data.status == "999") {
				return false;
			}
			var commentList = data.data;
			console.log(commentList);
			var html = $('#comment-list-part').html();
			//var resultHtml = '';
            
			var liHtml = "";
			for(var i=0 ; i < commentList.length; i++){
				var htmlPart = html;
				var item = commentList[i];
				
				var imgTag = '<img width="48" height="48" class="am-comment-avatar" alt="" src="' + item.head_img +  '">';
				htmlPart = htmlPart.replace('{imgTag}',imgTag);
				htmlPart = htmlPart.replace('{name}',item.name);
				
				var addTimeStr = moment.unix(item.add_time).format("YYYY-MM-DD HH:mm");
				htmlPart = htmlPart.replace('{addTimeStr}', addTimeStr);
				
				
				htmlPart = htmlPart.replace('{commentContent}', item.comment);

				liHtml += htmlPart;

			
			}	
			$("#comment-list").append(liHtml);
			
			//如果第一页并且返回数据等于10条，则可以显示加载更多按钮
			if (page == 1 && commentList.length >= 10) {
				$("#btn-get-more").css('display','block'); 
			}
			
			//如果为第二页以上，并且返回数据小于10条，则不显示加载更多按钮
			if (page > 1 && commentList.length < 10) {
				$("#btn-get-more").css('display','none'); 
			}
		}
	});
}


//默认加载第一页
getCommentList(1);


$("#btn-get-more").on('click', function(e) {
	$commentListPage = $commentListPage + 1;
	getCommentList($commentListPage);
});

//提交验证
$("#btn-comment-submit").on('click', function(e) {

	var formValidity = $('#comment-form').validator().data('amui.validator').validateForm().valid;
	console.log(formValidity);

	if (formValidity) {
		// done, submit form
		console.log("ok");
		var cardId = $("#cardId").val();
		var userId = $("#userId").val();	
		var headImg = $("#headImg").val();
		var name = $("#name").val();
		var addTimeStr = moment().format("YYYY-MM-DD HH:mm");
		var comment = $("#comment").val();
		var params = {};
		params.user_id = userId;
		params.card_id = cardId;
		params.comment = comment;
		console.log(params);
		var ajaxUrl = appRootUrl + "card/post_comment.json";
		
		$.ajax({
			type : "POST",
			url : ajaxUrl,
			dataType : "json",
			data : params,
			cache : true,
			async : false,	
			success : function(data) {
				
				if (data.status == "999") {
					return false;
				}
				
				var htmlPart = $('#comment-list-part').html();
					
				var imgTag = '<img width="48" height="48" class="am-comment-avatar" alt="" src="' + headImg +  '">';
				htmlPart = htmlPart.replace('{imgTag}',imgTag);
				htmlPart = htmlPart.replace('{name}',name);
					
				
				htmlPart = htmlPart.replace('{addTimeStr}', addTimeStr);
					
					
				htmlPart = htmlPart.replace('{commentContent}', comment);
	
				$("#comment-list").prepend(htmlPart);
				
			}
		});		
		
	} else {
		// fail
		console.log("fail");

	};	
	
});