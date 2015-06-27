myApp.onPageInit('chat', function (page) {

	var im_username = localStorage['im_username'];
	var im_password = localStorage['im_password'];
	var userId = page.query.user_id;
	
	console.log("im_username = " + im_username);
	console.log("im_passowrd = " + im_password);
	console.log("userId = " + userId);
	
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	console.log(document.body.clientWidth);
	console.log(document.body.clientHeight);
	
//	var html = '<iframe src="js/lib/webim/index.html" id="iframe" frameborder="0" scrolling="auto" class="mainiframe" width="'+clientWidth+'"  height="'+clientHeight+'"></iframe>';
//	$$('.page-content').append(html);
});

function iFrameHeight() {
	var ifm = document.getElementById("iframe-chat");
	var subWeb = document.frames ? document.frames["iframe-chat"].document
			: ifm.contentDocument;
	console.log(subWeb);
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}
}