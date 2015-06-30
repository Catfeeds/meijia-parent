myApp.onPageInit('chat', function (page) {

	var im_username = localStorage['im_username'];
	var im_password = localStorage['im_password'];
	var userId = page.query.user_id;
	
	console.log("im_username = " + im_username);
	console.log("im_passowrd = " + im_password);
	console.log("userId = " + userId);
	
	var clientWidth = document.body.scrollWidth ;
	var clientHeight = document.body.scrollHeight ;
	console.log(document.body.clientWidth);
	console.log(document.body.clientHeight);
	
	var html = '<iframe src="js/lib/webim/index.html" id="iframe" frameborder="0" scrolling="auto" class="mainiframe" width="100%" height="'+clientHeight+'"></iframe>';
	$$('.page-content-inner').append(html);
});