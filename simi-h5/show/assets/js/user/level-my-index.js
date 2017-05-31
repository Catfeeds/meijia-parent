var userId = getUrlParam("user_id");

function getUserScore() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	$.ajax({
		type : "GET",
		url : appRootUrl + "user/get_user_index.json?user_id=" + userId + "&view_user_id=" + userId,
		dataType : "json",
		cache : true,
		async : false,
		success : function(data) {
			var user = data.data;
			
	
			var exp = user.exp;
			var levelMax = user.level_max;
			$("#level-img").attr("src", user.level_banner);
			$("#level-name").html(user.level);
			$("#exp").html(exp + "/" + levelMax);
			
			
			
			var per = Number(exp)  /  Number(levelMax)  ;
			per = (per * 100).toFixed(2);
			$("#level-progress").css("width", per + "%")
		}
	});
}


function getTopRank() {
	if (userId == undefined || userId == "" || userId == 0) return false;
	$.ajax({
		type : "GET",
		url : appRootUrl + "user/get_score_ranking.json?user_id=" + userId,
		dataType : "json",
		cache : true,
		async : false,
		success : function(data) {
			var datas = data.data;
			var curRank = datas.cur_rank;
			
			if (curRank == 0) {
				$("#curRankDiv").html("你本月暂时未获得积分.");
			} else {
				$("#curRankDiv").html("您的排名：第"+curRank+"位，亲，继续努力哦~");
			}
			
			
			var topRanks = datas.top_ranks;
			if (topRanks == undefined || topRanks == "") return false;
			
			var trHtml = "";
			$.each(topRanks, function(i, item) {
				trHtml+="<tr>";
				trHtml+="<td>" + item.rownum  + "</td>";
				trHtml+="<td>" + item.name + "</td>";
				trHtml+="<td>" + item.score + "</td>";
				trHtml+="</tr>";
				
			});
			
			$("#topRankDiv").html(trHtml);
			
		}
	});
}

getUserScore();
getTopRank();