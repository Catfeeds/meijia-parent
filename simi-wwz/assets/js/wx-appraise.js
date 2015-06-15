$(function(){
    if(!isLogin()){
        window.location.href="wx-login.html?go=appraise";
        return;
    }
    //alert('xxxxx');
    if(typeof localStorage['ap_orderno'] == 'undefined' || localStorage['ap_orderno']==''){
        alert('无效的订单号或已评价的订单');
        window.location.href="wx-order.html";
        return;
    }
    var userPhone = localStorage['user_phone'];
    var orderNO = localStorage['ap_orderno'];
    $("#apimg").delegate('label','click',function(){
        //console.log(this.id);
        var lid = this.id;
        localStorage['ap_rate'] = lid.slice(2);
        this.style='width:86px;height:91px;background-image:url(images/'+ lid +'x.png);margin-right:30px;';
        $.each($(this).siblings(),function(i,item){
            item.style='width:86px;height:91px;background-image:url(images/'+item.id+'.png);margin-right:30px;';
        });
        $.each($('#ap_w label'),function(i,item){
            if ('w_'+lid == item.id){
                item.style.color = '#ff753e';
            }
            else{
                item.style.color = '';
            }
        });
    });
    //取订单信息
    $.ajax({
        type:"GET",
        url:siteAPIPath+"order/get_detail.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone,"order_no":orderNO},
        success : onInfoSuccess,
        error : onInfoError
    });
}());

function onInfoSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
      alert(data.msg);
    else
      alert("获取订单信息出现一些问题，请稍后再试。");
    return;
  }

  //显示订单信息
  var serviceImge = serviceDict[data.data.service_type].image;
  var serviceName = serviceDict[data.data.service_type].name;
  $('#service_type').html('<img id="service_img" height="50" align="middle" width="50" src="'+serviceImge+'"> <span id="service_name">'+serviceName+'</span>');

  var dS = new Date(data.data.start_time*1000);
  hh = dS.getHours()>9?dS.getHours():'0'+dS.getHours();
  mm = dS.getMinutes()>9?dS.getMinutes():'0'+dS.getMinutes();
  var serviceTime = dS.getFullYear()+'年'+(dS.getMonth()+1)+'月'+dS.getDate()+'日 ('+weekDay[dS.getDay()]+') '+hh+':'+mm;
  var serviceAddr = cities[data.data.city_id] + ' ' + data.data.cell_name + data.data.addr;
  $('#service_time').text(serviceTime);
  $('#service_addr').text(serviceAddr);
  
  $('#ap_submit').bind('click',function(){
    var userPhone = localStorage['user_phone'];
    var orderNO = localStorage['ap_orderno'];
    var rate = localStorage['ap_rate'] || '0';
    var words = $('ap_content').val();
    $.ajax({
        type:"POST",
        url:siteAPIPath+"order/post_rate.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone,
              "order_no":orderNO,
              "order_rate":rate,
              "order_rate_content":encodeURIComponent(words)},
        success : onApSuccess,
        error : onApError
    });
  });
}

function onInfoError(data, status){
    alert("获取订单信息出现一些问题，请稍后再试。(5)");
}

//订单评价处理
function onApSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
      alert(data.msg);
    else
      alert("提交评价出现一些问题，请稍后再试。");
    return;
  }
  localStorage.removeItem('ap_orderno');
  localStorage.removeItem('ap_rate');
  alert("感谢您对我们工作的评价！");
  window.location.href="wx-order.html";
}

function onApError(data, status){
    alert("提交评价出现一些问题，请稍后再试。(5)");
}

