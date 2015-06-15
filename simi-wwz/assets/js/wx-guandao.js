$(function(){
    if(!isLogin()){
        $('#user_address').bind('focus',function(){
            window.location.href="wx-login.html?go=guandao";
            return;
        });
    }
    else{
        loadAddr();
    }
    localStorage.removeItem('coupon_value');
    localStorage.removeItem('card_passwd');
    dateListHTML = createDateList();
    $("#service_date_list").html(dateListHTML);
    changeServiceTimeList();

    //管道订单提交
    $('#guandao_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.html?go=guandao";
            return;
        }
        if(typeof localStorage['user_city_id'] == "undefined" || localStorage['user_city_id']=='' ||
           typeof localStorage['user_addr_id'] == "undefined" || localStorage['user_addr_id']==''){
           alert("您需要先选择或添加一个地址。");
           return;
        }
        
        if($("#user_address").val()==''){
          alert("请填写地址信息");
          return false;
        }

        var userPhone = localStorage['user_phone'];
        var userCityID = localStorage['user_city_id'];
        var userAddressID = localStorage['user_addr_id'];

        var userRemarks = encodeURIComponent($("#user_remark").val());
        var serviceDate = $("#service_date_list").val();
        var startTime = $("#service_time_list").val();

        var serviceHour = 4;//服务时长
        var dtArr = serviceDate.split('-');
        var tArr = startTime.split(':');
        if((parseInt(tArr[0])+parseInt(tArr[1])/60)+parseInt(serviceHour)>22){
            alert("您所选的服务时段或时长无法完成预约，请重新选择！");
            return false;
        }

        //////
        localStorage['guandao_service_date'] = serviceDate;
        localStorage['guandao_start_time'] = startTime;
        //////

        var selectType = 601;//管道疏通
        var serviceSqr = 1;//1眼
        var sendDatas = ['[{"type":"',selectType,'","value":"',serviceSqr,'"}]'].join('');
        var serviceType = $("#service_type").val();
        localStorage['service_type'] = serviceType;
        var serviceTool = 0;
        
        var sd = new Date(dtArr[0],dtArr[1]-1,dtArr[2]);
        var st= new Date(dtArr[0],dtArr[1]-1,dtArr[2],parseInt(tArr[0]),parseInt(tArr[1],0));
        serviceDate = sd.getTime()/1000;
        startTime = st.getTime()/1000;

        
        $.ajax({
            type : "POST",
            url  : siteAPIPath+"order/post_add.json",
            dataType: "json",
            cache : false,
            data : {"mobile":userPhone,
                    "city_id":userCityID,
                    "service_type":serviceType,
                    "send_datas":sendDatas,
                    "service_date":serviceDate,
                    "start_time":startTime,
                    "service_hour":0,
                    "addr_id":userAddressID,
                    "remarks":userRemarks,
                    "clean_tools":serviceTool,
                    "order_from":1},
            success : onSuccess,
            error : onError
        });
        return false;
    });
}());

//订单提交处理
function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("亲，订单处理出现一些小问题，请稍后再试。");
    return;
  }
  //记录订单有关信息
  localStorage['guandao_order_id'] = data.data.order_id;
  localStorage['guandao_order_no'] = data.data.order_no;
  localStorage['guandao_order_money'] = data.data.order_money;
  localStorage['guandao_order_pay'] = data.data.order_pay;
  localStorage['guandao_price_hour'] = data.data.price_hour;
  localStorage['guandao_hour_discount'] = data.data.hour_discount;
  window.location.href="wx-pay.html?t=guandao";
}
function onError(data, status){
    console.log(data.msg);
    alert("亲，订单处理出现一些小问题，请稍后再试。(5)");
}
