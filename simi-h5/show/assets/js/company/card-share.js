
var params = {};
params.card_id = $('#card_id').val();

//params.card_id = 2;
$.ajax({
	type : "GET",
	url : appRootUrl + "/card/get_detail.json",
	dataType : "json",
	cache : true,
	async : false,	
	data : params,
	success : function(data) {

		if (data.status == "0") {
			var cardView = data.data;
			console.log(cardView);
		     	$("#userName").html(cardView.user_name);
				$("#userHeadImg").attr("src",cardView.user_head_img);
				$("#cardTypeName").html(cardView.card_type_name);
				$("#serviceContent").html(cardView.service_content);
				$("#addTimeStr").html(cardView.add_time_str);
				$("#serviceAddr").html(cardView.service_addr);
				$("#degreeId").val(cardView.degree_id);
				$("#cardLogo").attr("src",cardView.card_logo)
				
			return false;
		}
	},
		
});

