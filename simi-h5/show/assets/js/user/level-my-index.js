var userId = getUrlParam("user_id");

$.ajax({
	type : "GET",
	url : appRootUrl + "user/get_user_index.json?user_id=" + userId + "&view_user_id=" + userId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {
		var user = data.data;
		
		console.log(user);
		
		$("#level-img").attr("src", user.level_banner);
		$("#level-name").html(user.level);
		$("#exp").html(user.exp);
		
		var exp = user.exp;
		var levelMax = user.level_max;
		
		var per = (Number(exp)/Number(levelMax)).toFix(2);
		
		$("#level-progress").css("width", per + "%")
	}
});
