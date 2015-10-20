
initHomePage();


//首页轮播图的加载
function initHomePage() {
//	console.log("initHomePage");
	var result;
//	console.log("111111111");
	$$.ajax({
		type : "GET",
		url : siteAPIPath + "indexAd/get_ad_list.json",
		dataType : "json",
		cache : true,
		async : false,
		success : function(data) {

			result = data;
			console.log(result);
			var dictAdList = result.data;
//			var html = $$('#index-imgUrl-list').html();
			var resultHtmlNow = '';
			for (var i = 0; i < dictAdList.length; i++) {
				var imgUrl = dictAdList[i];
				if (imgUrl.img_url == "") {
					//resultHtmlNow += "<div class=\"swiper-slide\"style=\"height:150px;\"><img src=\"img/tmp/13.png\"></div>";
					continue;
				} else {
					imgUrl = localUrl + imgUrl.img_url;
//					console.log(imgUrl);
					
					//TODO 轮播图 点击 图片 进入 指定 页面，暂时 写死
					
					if(i == 0){
						resultHtmlNow += "<div onclick='imgGoToHtml("+i+")' class=\"swiper-slide\"style=\"height:150px;\">" +
						"<img src=\""+ imgUrl + "\">" +"</div>";
					}else if(i == 1){
						resultHtmlNow +="<div onclick='imgGoToHtml("+i+")' class=\"swiper-slide\"style=\"height:150px;\">"+
						"<img src=\""+ imgUrl + "\">" +"</div>";
					}else{
						resultHtmlNow +="<div onclick='imgGoToHtml("+i+")' class=\"swiper-slide\"style=\"height:150px;\">"+
						"<img src=\""+ imgUrl + "\">" +"</div>";
					}
//					resultHtmlNow += "<div class=\"swiper-slide\"style=\"height:150px;\"><img src=\""
//							+ imgUrl + "\"></div>";
				}
			}
			
			
			if (resultHtmlNow == "") {

				resultHtmlNow = "<div class=\"swiper-slide\"style=\"height:150px;\"><img src=\"img/tmp/13.png\"></div>";
			}
			
			var html = "<div class=\"swiper-wrapper\" id=\"swiper-list\">";
			
			html += resultHtmlNow;
			html += "</div><div class=\"swiper-pagination\"></div>"
			
			$$(".swiper-container").html(html);
//			console.log($$(".swiper-container").html());
			
			var mySwiper = myApp.swiper('.swiper-container', {
				pagination:'.swiper-pagination',
				autoplay: 2000
			});				
	
		}

	})
	return result;
	
	
}

//暂时是个死方法，从console确定 图片具体是哪张
function imgGoToHtml(i){
	
	if(i == 0){
		mainView.router.loadPage("order/order-am.html");
	}else if(i == 1){
		mainView.router.loadPage("user/charge/mine-charge-list.html");
	}else{
		mainView.router.loadPage("order/order-am.html");
	}
}



//提醒类已预约 订单的 数量，“小红点”
//function myRemindOrderCount(){
//	
//	var userId = localStorage['user_id'];
//	
//	var postdata = {};
//	postdata.user_id = userId;
//	
//	
//	 $$.ajax({
//         type : "GET",
//         url  : siteAPIPath + "remind/get_remind_count_to_do.json",
//         dataType: "json",
//         cache : true,
//         data : postdata,
//         success:function(datas,status,xhr){
//        	 
//        	 var result = JSON.parse(datas.data);
//        	 
////        	 var  result = 0
//        	 if(result == 0){
//        		 $$("#remindCount").hide();
//        	 }
//        	 
//        	 $$("#remindCount").text(result);
//         },
//         error:function(xhr, status){
//        	 $$("#remindCount").hide();
//         }
//	 });
//}

//抽奖活动,点击事件
$$("#turnplate").click(function(){
	
	var userId =  localStorage['user_id'];
	
	var userMobile = localStorage['user_mobile'];
	
//	var postData = {};
//	
//	postData.userId = userId;
//	postData.userMobile = userMobile;
	
//	$$.ajax({
//		type: "get",
//		url: siteAPIPath + "activity/turnplate.json",
//		data: postData,
//	 success: function(datas,status,xhr){
//		 
//		 	var result = JSON.parse(datas);
//			if (result.status == "999") {
//				myApp.alert(result.msg);
//				return;
//			}
//			
	$$(this).attr("href","http://localhost:8080/jhj-app/turnplate.jsp?userId="+userId+"&userMobile="+userMobile);
		 
//	 },
//	 error:ajaxError
//	});
});




$$(document).on('pageBeforeInit', function(e) {
	var page = e.detail.page;

	if (page.name === 'index') {
		initHomePage();
		
		
		//首页滚动广告
	}
})

