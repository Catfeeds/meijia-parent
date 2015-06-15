$(function(){
    var  url = location.href;
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
    var userMsgPage = 1;
    var userMsgPageTemp = userMsgPage;
    var mobile = paraString[0];
    var userPhone = mobile.substring(mobile.indexOf('=')+1,mobile.lenght);
    //获取用户消息列表
    getUserMsg(userPhone,userMsgPage);
    $('#userMsgMore').bind('click',function(){
    	 userMsgPageTemp = parseInt(userMsgPageTemp)+1;
    	 getUserMsgs(userPhone,userMsgPageTemp);
    });

}());

//获取用户消息列表
function getUserMsg(userPhone,userMsgPage){
    $.ajax({
        type:"GET",
        url:"/onecare/app/user/get_msg.json",
        dataType:"json",
        cache:false,
        data:"mobile="+userPhone+"&page="+userMsgPage,
        success : onListSuccess,
        error : onListError
    });
}
function getUserMsgs(userPhone,userMsgPage){
	$.ajax({
		type:"GET",
		url:"/onecare/app/user/get_msg.json",
		dataType:"json",
		cache:false,
		data:"mobile="+userPhone+"&page="+userMsgPage,
		success : onListS,
		error : onListError
	});
}
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
    	/*alert(data);
        alert("目前无法获取用户消息列表，请稍后再试。");*/
    return;
  }
  var userMsg = data.data;
  if(userMsg==''){
	$("#moreInfo").css("display","none");
  }
  var userMsgList = [];
  var addrH = '';
  $.each(userMsg,function(i,item){
    var tmpA = [];
    tmpA.push("<li class='am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left'> ");
    tmpA.push("<div class='am-u-sm-4 am-list-thumb'> ");
    var url = localUrl+item.html_url+"?mobile="+item.mobile+"&msgId="+item.msg_id;
    tmpA.push("<a href='"+url+"'");
    tmpA.push(" class=''>");
    if(item.is_readed==0){
    	tmpA.push("<img src='http://ico.ooopic.com/iconset02/3/gif/51110.gif'  alt='有个管家最新2.0版本升级有奖啦！' />");
    }else{
    	tmpA.push("<input type='hidden' />");
    }
    tmpA.push("</a></div>");
    tmpA.push("<div class='am-u-sm-8 am-list-main'>  <h3 class='am-list-item-hd'> ");
    tmpA.push("<a href='"+url+"'");
    tmpA.push("class=''>");
    tmpA.push(item.title);
    tmpA.push("</a></h3>");
    tmpA.push("<div class='am-list-item-text'>");
    tmpA.push(item.summary);
    tmpA.push("</div></div>");
    tmpA.push('</li>');
    userMsgList.push(tmpA.join(''));
  });
  if(userMsgList.length){
    $('#news_list').html(userMsgList.join(''));
  }
}
function onListS(data, status){
	if(data.status != "0"){
		if (data.status =="999")
			alert(data.msg);
		else
			/*alert(data);
		alert("目前无法获取用户消息列表，请稍后再试。");*/
		return;
	}
	var userMsg = data.data;
	var userMsgList = [];
	var addrH = '';
	$.each(userMsg,function(i,item){
		var tmpA = [];
		tmpA.push("<li  class='am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left'> ");
		tmpA.push("<div class='am-u-sm-4 am-list-thumb'> ");
	    var url = localUrl+item.html_url+"?mobile="+item.mobile+"&msgId="+item.msg_id;
	    tmpA.push("<a href='"+url+"'");
	    tmpA.push(" class=''>");
	    if(item.is_readed==0){
	    	tmpA.push("<img src='http://ico.ooopic.com/iconset02/3/gif/51110.gif'  alt='有个管家最新2.0版本升级有奖啦！' />");
	    }else{
	    	tmpA.push("<input type='hidden' />");
	    }
	    tmpA.push("</a></div>");
		tmpA.push("<div class='am-u-sm-8 am-list-main'>  <h3 class='am-list-item-hd'> ")
	    tmpA.push("<a href='"+url+"'");
	    tmpA.push("class=''>");
	    tmpA.push(item.title);
	    tmpA.push("</a></h3>");
	    tmpA.push("<div class='am-list-item-text'>");
		tmpA.push(item.summary);
		tmpA.push("</div></div>");
		tmpA.push('</li>');
		userMsgList.push(tmpA.join(''));
	});
	if(userMsgList.length){
		$("ul li:last-child").after(userMsgList.join(''));
	}
}

function onListError(data, status){
}
