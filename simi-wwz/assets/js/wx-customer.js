$(function(){
	
    var secId = 1;
    var mobile = 13810002890;
    //获取用户消息列表
    getUserList(secId,mobile);

}());

//获取用户消息列表
function getUserList(secId,mobile){
    $.ajax({
        type:"POST",
        url:siteAPIPath+"/sec/get_users.json",
        dataType:"json",
        cache:false,
        data:"sec_id="+secId+"&mobile="+mobile,
        success : onListSuccess,
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
  var users = data.data;
  if(users==''){
	$("#moreInfo").css("display","none");
  }
  var usersList = [];
  var addrH = '';
  $.each(users,function(i,item){
	  
    var tmpA = [];
    
    tmpA.push(" <li><a href='wx-customer-info.html'>");
    tmpA.pusht("<img src="+item.headImg+"alt='...' width='50' height='50' class='am-radius'>"+item.name+"</a></li>");
    
   /* tmpA.push("<li class='am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left'> ");
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
    tmpA.push('</li>');*/
    
    usersList.push(tmpA.join(''));
  });
  if(userMsgList.length){
    $('#customer_list').html(usersList.join(''));
  }
}


function onListError(data, status){
}
