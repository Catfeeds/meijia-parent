$(function(){
	var userId = $.urlParam('user_id');
    //获取用户消息列表
    getUserAddrList(userId);

}());

//获取用户消息列表
function getUserAddrList(userId){
    $.ajax({
        type:"GET",
        url:siteAPIPath+"user/get_addrs.json?user_id="+userId,
        dataType:"json",
        cache:false,
        success : onListSuccess,
    });
}
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法获取用户地址信息列表，请稍后再试。");
    return;
  }
  var userAddr = data.data;
  var userAddrList = [];
  var addrH = '';
  $.each(userAddr,function(i,item){
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
    $('#address_list').html(userAddrList.join(''));
  }
}
$("#add_address").bind("click",function(){
	var userId = $.urlParam('user_id');
	location.href="wx-add-address.html?user_id="+userId;
});
