myApp.onPageBeforeInit('user-addr-list', function (page) {
		
	var userId = page.query.user_id;

	if (userId == undefined || userId == '' || userId == 0) {
		return;
	}
	  //获取用户地址列表
     getUserAddrList(userId);
     
     $$(".user-add-addr-link").on("click",function(){
 		mainView.router.loadPage("user/user-add-addr.html?user_id="+userId);
 	});
});
var onListSuccess = function(data, textStatus, jqXHR){
	myApp.hideIndicator();
   	var result = JSON.parse(data.response);
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	  var userAddr = result.data;
	  var userAddrList = [];
	  var addrH = '';
	  $$.each(userAddr,function(i,item){
		  	var lid =item.city_id+"_"+item.cell_id+"_"+item.addr_id;
		  	var addrA = [item.cell_name, item.addr, '<input type="hidden" value="',item.addr,'" id="', item.addr_id ,'">'];
		    var addrs = addrA.join('');//小区名+门牌号
		    var fava = item.is_default;
		    var tmpA = [];
		    tmpA.push('<li id="');
		    tmpA.push(lid);
		    if (fava==1)
		        tmpA.push('"><span class="am-badge am-badge-success">默认</span>');
		    else
		        tmpA.push('">');
		    tmpA.push(addrs);
		    tmpA.push('</li>');
		    userAddrList.push(tmpA.join(''));
	  });
	  if(userAddrList.length){
	    $$('#address_list').html(userAddrList.join(''));
	  }
	}
//获取用户消息列表
function getUserAddrList(userId){
    $$.ajax({
        type:"GET",
        url:siteAPIPath+"user/get_addrs.json?user_id="+userId,
        dataType:"json",
        cache:false,
        statusCode: {
         	200: onListSuccess,
 	    	400: ajaxError,
 	    	500: ajaxError
 	    },
    });
}
$$("#add_address").on("click",function(){
	mainView.router.loadPage("wx-add-address.html?user_id="+userId);
});