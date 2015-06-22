$(function(){
	
	 if(!isLogin()){
	        window.location.href="wx-login.html?go=mine";
	        return;
	    }
	
   // var secId = 1;
    var secId = localStorage['sec_id'];
    var mobile =  localStorage['sec_mobile'];
    //获取用户消息列表
    getUserList(secId,mobile);

}());

//获取用户消息列表
function getUserList(secId,mobile){
    $.ajax({
        type:"POST",
        url:siteAPIPath+"sec/get_users.json",
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
    tmpA.push("<img src="+item.headImg+"alt='...' width='50' height='50' class='am-radius'>"+item.name+"</a></li>");
    
    usersList.push(tmpA.join(''));
  });
  if(usersList.length){
    $('#customer_list').html(usersList.join(''));
  }
}


function onListError(data, status){
}
