$(function(){
    if(!isLogin()){
        $('#user_address').bind('focus',function(){
            window.location.href="wx-login.html?go=zuofan";
            return;
        });
    }
    else{
        loadAddr();
        if(localStorage['user_type']=='1'){
            $('#shiji').css('display','block');
        }
    }
    localStorage.removeItem('coupon_value');
    localStorage.removeItem('card_passwd');
    dateListHTML = createDateList();
    $("#service_date_list").html(dateListHTML);
    changeServiceTimeList();
    
    //做饭订单提交
    $('#zuofan_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.html?go=zuofan";
            return;
        }
        if(typeof localStorage['user_city_id'] == "undefined" || localStorage['user_city_id']=='' ||
           typeof localStorage['user_addr_id'] == "undefined" || localStorage['user_addr_id']==''){
           alert("您需要先选择或添加一个地址。");
           return;
        }
        
        if($("#user_address")=='')
        {
          alert("请填写地址信息");
          return false;
        }
        var userPhone = localStorage['user_phone'];
        var userCityID = localStorage['user_city_id'];
        var userAddressID = localStorage['user_addr_id'];

        var agentMobile = 0;
        var customName = '';
        if(localStorage['user_type']=='1'){
            if($('#true_phone').val()=='' || $('#true_user').val()==''){
                alert('代理商预约必须填写实际联系人电话和姓名');
                return false;
            }
            if($('#true_phone').val().length!=11){
                alert('请填写正确的联系人手机号');
                return false;
            }
            userPhone = $('#true_phone').val();
            agentMobile = localStorage['user_phone'];
            customName = $('#true_user').val();
        }

        var userRemarks = encodeURIComponent($("#user_remark").val());
        var serviceDate = $("#service_date_list").val();
        var startTime = $("#service_time_list").val();
        //var shArr = $('input:radio[name="service_hours"]:checked').val().split('_');
        var shArr = $('label.am-active').attr('id').split('_');
        var serviceHour = shArr[1];//服务时长
        
        var dtArr = serviceDate.split('-');//用户所选服务日期
        var tArr = startTime.split(':');//用户所选服务开始时间HH:mm
        
        //算服务结束时间是否合理<22:00
        // console.log(parseInt(tArr[0])+parseInt(tArr[1])/60);
        // console.log(serviceHour);
        // console.log(parseInt(tArr[0])+parseInt(tArr[1])/60+parseInt(serviceHour));
        // return false;
        if((parseInt(tArr[0])+parseInt(tArr[1])/60)+parseInt(serviceHour)>22){
            alert("您所选的服务时段或时长无法完成预约，请重新选择！");
            return false;
        }

        //////
        localStorage['zuofan_service_date'] = serviceDate;
        localStorage['zuofan_start_time'] = startTime;
        //////

        var selectType = baseData['zuofan'][serviceHour];
        var sendDatas = ['[{"type":"',selectType,'","value":"',serviceHour,'"}]'].join('');
        var serviceType = $("#service_type").val();
        localStorage['service_type'] = serviceType;
        var serviceTool = 0;//$("#service_tool").val();
        
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
                    "service_hour":serviceHour,
                    "addr_id":userAddressID,
                    "remarks":userRemarks,
                    "clean_tools":serviceTool,
                    "order_from":1,
                    "agent_mobile":agentMobile,
                    "custom_name":customName},
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
  localStorage['zuofan_order_id'] = data.data.order_id;
  localStorage['zuofan_order_no'] = data.data.order_no;
  localStorage['zuofan_order_money'] = data.data.order_money;
  localStorage['zuofan_order_pay'] = data.data.order_pay;
  localStorage['zuofan_price_hour'] = data.data.price_hour;
  localStorage['zuofan_hour_discount'] = data.data.hour_discount;
  window.location.href="wx-pay.html?t=zuofan";
}
function onError(data, status){
    console.log(data.msg);
    alert("亲，订单处理出现一些小问题，请稍后再试。(5)");
}
