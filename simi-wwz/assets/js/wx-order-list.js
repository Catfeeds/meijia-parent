$(function(){
    //订单列表

    localStorage.removeItem('coupon_value');
    localStorage.removeItem('card_passwd');
    var userPhone = localStorage['user_phone'];
    $.ajax({
        type:"GET",
        url:siteAPIPath+"order/get_list.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone,"page":1},
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
  $.each(orderArr,function(i,item){
      var serviceName = serviceDict[item.service_type].name;
      var serviceImg = serviceDict[item.service_type].image;
      var orderNO = item.order_no;
      var ordreID = item.order_id;
      var serivceType = item.service_type;
      
      //下单时间
      var dO = new Date(item.add_time*1000);
      var hh = dO.getHours()>9?dO.getHours():'0'+dO.getHours();
      var mm = dO.getMinutes()>9?dO.getMinutes():'0'+dO.getMinutes();
      var orderTime = dO.getFullYear()+'年'+(dO.getMonth()+1)+'月'+dO.getDate()+'日 '+hh+':'+mm;
      
      //服务时间
      var dS = new Date(item.start_time*1000);
      hh = dS.getHours()>9?dS.getHours():'0'+dS.getHours();
      mm = dS.getMinutes()>9?dS.getMinutes():'0'+dS.getMinutes();
      var serviceTime = dS.getFullYear()+'年'+(dS.getMonth()+1)+'月'+dS.getDate()+'日 ('+weekDay[dS.getDay()]+') '+hh+':'+mm;
      
      var address = cities[item.city_id] + ' ' + item.cell_name + item.addr;
      var orderStatus = getOrderStatus(item.order_status,item.start_time,item.service_hours,orderNO, item.service_type);
      
      var ha = [];
      ha.push('<div class="am-panel am-panel-default">');
      ha.push('<div class="am-panel-hd">');
      ha.push('<h4 class="am-panel-title" data-am-collapse="{parent:\'#accordion\', target:\'#do-not-say-');ha.push(i);ha.push('\'}">');
      ha.push('<img src="');ha.push(serviceImg);ha.push('" width="50" height="50" align="middle" />  ');
      ha.push(serviceName);ha.push('-订单号：');ha.push(orderNO);
      ha.push('</h4>');
      ha.push('</div>');
      ha.push('<div id="do-not-say-');ha.push(i);ha.push('" class="am-panel-collapse am-collapse');
      if(i==0)
        ha.push(' am-in">');
      else
        ha.push('">');
      ha.push('<div class="am-panel-bd">');
      ha.push('<small>下单时间：');ha.push(orderTime);ha.push('</small>');
      if (orderStatus[0] == 1){
        ha.push('<button class="am-btn am-btn-default am-btn-xs am-text-danger am-align-right" id=""');
        ha.push(' onclick="javascript:pay(\'');ha.push(orderNO);
        ha.push('\',');ha.push(serivceType+',');
        ha.push(ordreID);ha.push(',');
        ha.push(item.start_time);ha.push(',');
        ha.push(item.order_money);
        ha.push(')"><span class="am-icon-money"></span>付款</button>');
      }
      else if(orderStatus[0] == 2){
        ha.push('<button class="am-btn am-btn-default am-btn-xs am-text-danger am-align-right" id="');
        ha.push('b_'+orderNO);ha.push('" onclick="javascript:co(\'');ha.push(orderNO);
        ha.push('\')"><span class="am-icon-money"></span>取消</button>');
      }
      else if(orderStatus[0] == 3){
        ha.push('<button class="am-btn am-btn-default am-btn-xs am-text-danger am-align-right" id="');
        ha.push('b_'+orderNO);ha.push('" onclick="javascript:ap(\'');ha.push(orderNO);
        ha.push('\')"><span class="am-icon-money"></span>评价</button>');
      }
      ha.push('<hr>');
      ha.push('<div class="am-progress am-progress-striped am-progress am-active ">');
      ha.push('<div id="');ha.push('p_'+orderNO);ha.push('" class="am-progress-bar am-progress-bar-warning" style="width:');
      ha.push(orderStatus[1]);ha.push('">');ha.push(orderStatus[2]);ha.push('</div>');
      ha.push('</div>');
      ha.push('<small>服务时间：');ha.push(serviceTime);ha.push('<br/>');
      ha.push('服务地址：');ha.push(address);ha.push('<br/>');
      ha.push('订单号：');
      ha.push(orderNO);
      ha.push('</small></div></div></div>');
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

function getOrderStatus(status, serviceTime, serviceHours, orderNO){
    var ret = [0,'10%',''];
    if (status == 0)  //用户主动取消订单，显示“已取消”
        ret = [0,'100%','已取消'];//0:其他，1:支付，2:取消，预留第4位服务类型
    else if (status == 1)  //等待用户支付费用，显示“待支付”
        ret = [1,'20%','待支付'];
    else if (status == 7)  //未支付的订单在一定时间后，后台系统会自动关闭，显示“已关闭”
        ret = [0,'100%','已关闭'];
    else if (status == 4)  //该状态值接口目前并不会给出，以后可能会给出，前端保留判断，显示“即将服务”
        ret = [0,'80%','即将服务'];
    else if (status == 5)  //该状态值接口目前并不会给出，以后可能会给出，前端保留判断，显示“待评价”
         ret = [0,'90%','待评价'];
    else if (status == 2 || status == 3){   //2表示已支付但未派工，3表示已派工，等待服务
        var dN = new Date();
        var dNUT = parseInt(dN.getTime()/1000);//当前时间
        var serviceOverTime = serviceTime + 3600 * serviceHours;//new Date(serviceTime*1000 + (3600000 * serviceHours));

        // if(orderNO == '558220951041343488'){
            // console.log('---- '+serviceTime);
            // console.log('hours:'+serviceHours);
            // console.log(serviceTime + 3600 * serviceHours);
            // console.log('>>>> '+serviceOverTime+' <<<<'+dNUT);
        // }

        //订单服务结束时间需要根据服务开始时间和服务时长计算
        if (dNUT >= serviceTime && dNUT < serviceOverTime)  //显示“即将服务”
            ret = [2,'80%','即将服务'];
        else if (dNUT > serviceOverTime)  //显示“待评价”
            ret = [3,'90%','待评价'];
        else //显示“待服务”
            ret = [2,'40%','待服务'];
    }
    else if (status == 6)    //订单被点评过，该订单才算已完成，显示“已完成”
        ret = [0,'100%','已完成'];
    return ret;
}
function pay(orderNO, serviceType, orderID, startTime, orderMoney){
    localStorage['service_type'] = serviceType;
    var t = serviceDict[serviceType]['key'];
    //console.log(t);
    //return;
    var dS = new Date(startTime*1000);
    //var hh = dS.getHours()>9?dS.getHours():'0'+dS.getHours();
    var mm = dS.getMinutes()>9?dS.getMinutes():'0'+dS.getMinutes();
    localStorage[t+'_service_date'] = dS.getFullYear()+'-'+(dS.getMonth()+1)+'-'+dS.getDate();
    localStorage[t+'_start_time'] = dS.getHours()+':'+mm;
    localStorage[t+'_order_money'] = orderMoney;
    localStorage[t+'_order_pay'] = orderMoney;
    localStorage[t+'_order_id'] = orderID;
    localStorage[t+'_order_no'] = orderNO;
    window.location.href="wx-pay.html?t="+t;
}

//取消订单
function co(orderNO){
    var userPhone = localStorage['user_phone'];
    localStorage['lorderno'] = orderNO;
    $.ajax({
        type:"POST",
        url:siteAPIPath+"order/post_order_cancel.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone,"order_no":orderNO},
        success : onCancelSuccess,
        error : onCancelError
    });
}

//评价订单
function ap(orderNO){
    //alert('即将推出');
    //return;
    localStorage['ap_orderno'] = orderNO;//备评价页用
    window.location.href="wx-appraise.html";
}
function onCancelSuccess(data, status){
    if(data.status != "0"){
        if (data.status =="999")
            alert(data.msg);
        else
            alert("目前无法取消该订单，请稍后再试，或拨打客服电话400-169-1615。");
        return;
    }
    //更改页面上的一些信息
    //不显示取消按钮
    var orderno = localStorage['lorderno'];
    $('#b_'+orderno).remove();
    //状态条更改
    var p = $('#p_'+orderno);
    p.style="width:100%";
    p.text('已取消');
    localStorage.removeItem('lorderno');
    alert("亲，该订单已为您取消。期待下次为您服务！");
}

function onCancelError(data, status){
    alert("目前无法取消该订单，请稍后再试，或拨打客服电话400-169-1615。(5)");
}