

function feedComment() {
	var userId = $("#userId").val();
	
	if (userId == "") {
		alert("选择回答的用户.");
		$("#userId").focus();
		return false;
	}
	
	var comment = $("#comment").val();
	
	if (comment == "") {
		alert("需要输入回答内容");
		$("#comment").focus();
		return false;
	}
	
	var fid = $("#fid").val();
	
	
	var params = {};
	
	params.user_id = userId;
	params.fid = fid;
	params.comment = comment;
	params.feed_type = 2;
	
	$.ajax({
		type : "POST",
		url : simiAppRootUrl + "/app/feed/post_comment.json",
		data : params,
		dataType : "json",
		async : false,
		success : function(rdata, textStatus) {
			if (rdata.status == "0") {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		},

	});
	
	
}