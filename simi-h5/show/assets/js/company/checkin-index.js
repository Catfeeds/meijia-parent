
var userId = getUrlParam("user_id");

var params = {};
params.user_id = userId;
params.date_range = "week";
$.ajax({
	type : "POST",
	url : appRootUrl + "company/get_checkins.json?",
	dataType : "json",
	data : params,
	cache : true,
	async : false,
	success : function(data) {
		console.log(data);
		if (data.status == "999") return false;
		var list = data.data.list;

		var trHtml = "";
		for (var i = 0; i < list.length; i++) {
			var addTime = list[i].addTime;
			
			var checkinDate = moment.unix(addTime).format("MM月DD日");
			var checkinTime = moment.unix(addTime).format("HH:mm");
			var poiName = list[i].poiName;
			var remarks = list[i].remarks;
			
			var htmlPart = '<tr><td>{checkinDate}</td><td>{checkinInfo}</td><td class="am-success">{remarks}</td></tr>';
			
			htmlPart = htmlPart.replace('{checkinDate}', checkinDate);
			htmlPart = htmlPart.replace('{checkinInfo}', checkinTime + " " + poiName);
			htmlPart = htmlPart.replace('{remarks}', remarks);
			
			trHtml += htmlPart;
			console.log(htmlPart);
			
		}
		console.log(trHtml);
		$("#checkin-table>tbody").append(trHtml);
	}
});