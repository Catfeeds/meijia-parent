$(function(){
    //订单列表
    var userId = $.urlParam('user_id');
    $.ajax({
        type:"GET",
        url:siteAPIPath+"order/get_list.json",
        dataType:"json",
        cache:false,
        data:{"user_id":userId,"page":1},
        success : onListSuccess,
        error : onListError
    });
}());

//获取订单列表后的处理
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法获取订单列表，请稍后再试。");
    return;
  }
  var orderArr = data.data;
  var orderList = [];
  $.each(orderArr,function(i,item) {
      
      //var serviceImg = serviceDict[item.service_type].image;
      var orderNo = item.order_no;
      var orderId = item.id;
      
      //用户称呼
      var name = item.name;
      
      //用户手机号
      var mobile = item.mobile;
      
      //服务类型
      var serviceName = item.service_type_name;
      var serivceType = item.service_type;
      
      //服务内容
      var serviceContent = item.service_content;
      
      //支付方式
      var orderPayType = item.order_pay_type;
      var orderPayTypeName = '';
      if (orderPayType == "0") orderPayTypeName = '无需支付';
      if (orderPayType == "1") orderPayTypeName = '在线支付';
      
      //支付金额
      var orderMoney = item.orderMoney;
      
      //下单时间
      var dO = new Date(item.add_time*1000);
      var hh = dO.getHours()>9 ?dO.getHours():'0'+dO.getHours();
      var mm = dO.getMinutes()>9 ?dO.getMinutes():'0'+dO.getMinutes();
      var orderTime = dO.getFullYear()+'年'+(dO.getMonth()+1)+'月'+dO.getDate()+'日 '+hh+':'+mm;
      
      //订单状态
      var orderStatus = getOrderStatus(item.order_status);
      
      //服务时间
      var dS = 0;
      var serviceTime = '';
	  if (item.start_time != '' && item.start_time > 0) {
	      dS = new Date(item.start_time*1000);
	      hh = dS.getHours()>9?dS.getHours():'0'+dS.getHours();
	      mm = dS.getMinutes()>9?dS.getMinutes():'0'+dS.getMinutes();
	      serviceTime = dS.getFullYear()+'年'+(dS.getMonth()+1)+'月'+dS.getDate()+'日 ('+weekDay[dS.getDay()]+') '+hh+':'+mm;
      }
      
      //服务地址
	  var serviceAddress = ''
      if (item.service_addr != '') {
    	  serviceAddress = item.city_name + ' ' + item.service_addr;
  	  }
	  
	  //备注
	  var remarks = item.remarks;
     
      var ha = [];
      ha.push('<div class="am-panel am-panel-default">');
      	ha.push('<div class="am-panel-hd">');
      	ha.push('<h4 class="am-panel-title" data-am-collapse="{parent:\'#accordion\', target:\'#do-not-say-');ha.push(i);ha.push('\'}">');
      	//ha.push('<img src="');ha.push(serviceImg);ha.push('" width="50" height="50" align="middle" />  ');
      	ha.push(serviceName);ha.push('-订单号：');ha.push(orderNo);
      	ha.push('</h4>');
      ha.push('</div>');
      ha.push('<div id="do-not-say-');ha.push(i);ha.push('" class="am-panel-collapse am-collapse');
      if(i==0)
        ha.push(' am-in">');
      else
        ha.push('">');
      	ha.push('<div class="am-panel-bd">');
      		ha.push('<small>下单时间：');ha.push(orderTime);ha.push('</small>');
      		if (orderStatus[0] == 1 || orderStatus[0] == 3){
      	        ha.push('<button class="am-btn am-btn-default am-btn-xs am-text-danger am-align-right" id=""');
      	        ha.push(' onclick="javascript:pushOrder(');ha.push(orderId);
      	        ha.push(')"><span class="am-icon-money"></span>推送给用户</button>');
      	    }
      		ha.push('<hr>');
      		//订单状态
      		ha.push('<div class="am-progress am-progress-striped am-progress am-active ">');
      		ha.push('<div id="');ha.push('p_'+orderNo);ha.push('" class="am-progress-bar am-progress-bar-warning" style="width:');
      		ha.push(orderStatus[1]);ha.push('">');ha.push(orderStatus[2]);ha.push('</div>');
      	ha.push('</div>');
      		
      	ha.push('<small>客户昵称：');ha.push(name);ha.push('</small><br\>');
      		
      	ha.push('<small>联系方式：');ha.push(mobile);ha.push('</small><br\>');
      		      		
      	ha.push('<small>服务内容：');ha.push(serviceContent);ha.push('</small><br\>');
      		
      	ha.push('<small>支付方式：');ha.push(orderPayTypeName);ha.push('</small><br\>');
      		
      	ha.push('<small>支付金额：');ha.push(orderMoney);ha.push('</small><br\>');
      		
      	if (serviceTime != '') {
      		ha.push('<small>服务时间：');ha.push(serviceTime);ha.push('</small><br\>');
      	}
      		
      		if (serviceAddress != '') {
      			ha.push('<small>服务地址：');ha.push(serviceAddress);ha.push('</small><br\>');
      		}     	
      		
      		if (serviceAddress != '') {
      			ha.push('<small>备注：');ha.push(remarks);ha.push('</small><br\>');
      		}           		
      		
      ha.push('</div></div></div>');
      orderList.push(ha.join(''));
  });
  
  if(orderList.length){
    $('#accordion').html(orderList.join(''));
  }
}

function onListError(data, status){
    //console.log(data.msg);
    alert("目前无法获取订单列表，请稍后再试。(5)");
}

function getOrderStatus(status){
    var ret = [0,'10%',''];
    if (status == 0)  //用户主动取消订单，显示“已取消”
        ret = [0,'100%','已关闭']; 
    else if (status == 1)   
        ret = [1,'20%','待确认'];
    else if (status == 2)  
        ret = [2,'100%','已确认'];  
    else if (status == 3)   
        ret = [3,'20%', '待支付'];
    else if (status == 4)   
        ret = [4,'100%','已支付'];     
    else if (status == 9)   
        ret = [9,'100%','已完成'];
    return ret;
}


function pushOrder(orderId){
	var userId = $.urlParam('user_id');
    $.ajax({
        type:"GET",
        url:siteAPIPath+"order/push_order.json",
        dataType:"json",
        cache:false,
        data:{"user_id":userId,"order_id":orderId},
        success : onPushOrderSuccess,
        error : onError
    });    
}

function onPushOrderSuccess(data, status){

	if (data.status =="999") {
		alert(data.msg);
		return ;
	}
	        
	if (data.status == "0") {
		alert("推送订单给用户成功!");
		return;
	} 
}
