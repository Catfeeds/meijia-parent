myApp.onPageInit('webim-page', function (page) {

	var im_username = localStorage['im_username'];
	var im_password = localStorage['im_password'];
	var userId = page.query.user_id;
	var to_im_name = page.query.to_im_name;
	var to_im_user = page.query.to_im_user;
	
//	console.log("im_username = " + im_username);
//	console.log("im_passowrd = " + im_password);
//	console.log("userId = " + userId);
	
	var clientWidth = document.body.scrollWidth ;
	var clientHeight = document.body.scrollHeight ;
//	console.log(document.body.clientWidth);
//	console.log(document.body.clientHeight);
	var url = "easymob-webim1.0/index.html";
	url += "?im_username=" + im_username;
	url += "&im_password=" + im_password;
	url += "&to_im_user=" + to_im_user;
	url += "&to_im_name=" + to_im_name;
	var html = '<iframe src="'+url+'" id="iframe-webim" frameborder="0" scrolling="auto" class="mainiframe" ></iframe>';
	
	$$('.page-content-inner').append(html);
});