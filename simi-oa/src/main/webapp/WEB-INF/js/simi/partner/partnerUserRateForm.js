function partnerUserRate() {
	var userId = $("#userId").val();
	
	var name = $("#rate_name").val();
	if (name == "") {
		alert("需要输入评价用户名");
		$("#rate_name").focus();
		return false;
	}
	
	var rate = $('#rate').rating('rate');
	
	var ex = /^\d+$/;
	if (!ex.test(rate)) {
		alert("需要选择评价星级");
		return false;
	}
	
	var comment = $("#comment").val();
	
	if (comment == "") {
		alert("需要输入评价内容");
		$("#comment").focus();
		return false;
	}
	
	
	
	
	var params = {};
	params.rate_type = 1;
	params.link_id = userId;
	params.rate = rate;
	params.name = name;
	params.rate_content = comment;
	
	$.ajax({
		type : "POST",
		url : simiAppRootUrl + "/app/record/post_rate.json",
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