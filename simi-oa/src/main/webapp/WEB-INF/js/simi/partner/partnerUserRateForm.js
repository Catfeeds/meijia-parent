$.fn.raty.defaults.starOn = '../../assets/jquery.raty-2.4.5/doc/img/star-on-big.png';
$.fn.raty.defaults.starOff = '../../assets/jquery.raty-2.4.5/doc/img/star-off-big.png';
$.fn.raty.defaults.starHalf = '../../assets/jquery.raty-2.4.5/doc/img/star-half-big.png';


function partnerUserRate() {
	var userId = $("#userId").val();
	
	var name = $("#rate_name").val();
	if (name == "") {
		alert("需要输入评价用户名");
		$("#rate_name").focus();
		return false;
	}
	
	var rate = $('#rate').raty('score');
	

	if (rate == undefined) {
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