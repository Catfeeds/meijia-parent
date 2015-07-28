var userId;
myApp.onPageInit('user-add-addr', function (page) {
	
	 userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
/*	$$("#suggestId1").on('click',function(){

		var city = new BMap.LocalSearch(map, {
			renderOptions : {
				map : map,
				autoViewport : true
			}
		});
		var searchTxt = $("#suggestId").val();
		city.search(searchTxt); // 查找城市
	});*/
	//添加新地址
	$$('#user_add_addr_submit').on('click',function(){
		var city = new BMap.LocalSearch(map, {
			renderOptions : {
				map : map,
				autoViewport : true
			}
		});
		var searchTxt = $$("#suggestId").val();
		city.search(searchTxt); // 查找城市
		
	  /*  $$.ajax({
	        type:"POST",
	        url:siteAPIPath+"user/post_addrs.json",
	        dataType:"json",
	        cache:false,
	        data:{"user_id":userId,
	              "addr_id":0,//新增地址
	              "poi_type":0,//地理位置poi类型
	              "name":"宇飞大厦",//
	              "address":"东直门612",
	              "latitude":"101.11",//经度
	              "longitude":"103.22",//维度
	              "city":"北京",
	              "uid":"ssss",
	              "phone":"1313",//地理位置电话
	              "post_code":"1313131",//地理位置邮编
	              "addr":"621",//门牌号
	              "is_default":0,//是否默认小区
	              "city_id":"0",//保留字段
	              "cell_id":"0"//保留字段
	        },
	        statusCode: {
	         	200: onSuccess,
	 	    	400: ajaxError,
	 	    	500: ajaxError
	 	    },
	    });*/
	    return false;
	});

});
function onSuccess(data, textStatus, jqXHR){
	myApp.hideIndicator();
   	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
  mainView.router.loadPage("user/user-addr-list.html?user_id="+userId);
}
