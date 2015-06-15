$(function(){
    //用户未登录
    if(!isLogin()){
        window.location.href="wx-login.html#go=wx-add-address";
        return;
    };
    var userPhone = localStorage['user_phone'];

    var cellID=0;
    //取城市id，小区id
    $('#user_cell').typeahead({
        ajax:{
            url: siteAPIPath+'dict/get_cell_by_key.json',
            timeout: 300,                   // 延时
            method: 'get',
            triggerLength: 1,               // 输入几个字符之后，开始请求
        },
        display: "name",     // 默认的对象属性名称为 name 属性
        val: "id",           // 默认的标识属性名称为 id 属性
        items: 8,            // 最多显示项目数量
        itemSelected: function (item, val, text) {// 当选中一个项目的时候，回调函数
            console.log(val);
            cellID=val;
        }
    });
    var cityID = $('#city_list').val();//城市ID
    
    //添加新地址
    $('#address_add').bind('click',function(){
        var cellName = $("#user_cell").val();//提交时不需要小区名，而用小区id
        var addressNo = $("#user_address_no").val();//门牌号
        if(cellName=='' || addressNo == ''){
            alert("亲，别闹，信息要填写完整哦。");
            return;
        }
        if(cellID==0){
            alert("亲，需要选择列表中选择小区，或填写其他小区");
            return;
        }
        $.ajax({
            type:"POST",
            url:siteAPIPath+"user/post_addrs.json",
            dataType:"json",
            cache:false,
            data:{"mobile":userPhone,
                  "addr_id":0,//新增地址
                  "city_id":cityID,//暂时写死，将来要改
                  "cell_id":cellID,//
                  "addr":addressNo,
                  "is_default":0},
            success : onSuccess,
            error : onError
        });
        return false;
    });
}());


function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法添加地址，请稍后再试。");
    return;
  }
  
  //跳 原页面或地址列表页
  //if (queryVal('go')!==null && queryVal('go')!==''){
    // console.log('更换+添加');
    // localStorage['user_address'] = data.addr;
    // localStorage['user_addr_id'] = data.addr_id;
    // localStorage['user_city_id'] = data.city_id;
    //var go = 'wx-'+queryVal('go')+'.html';
    //window.location.href = go;
  //}
  //else{
    if (queryVal('go')!==null && queryVal('go')!==''){
        window.location.href = 'wx-address.html?go='+queryVal('go');
    }
    else{
        window.location.href = 'wx-address.html';
    }
  //}
}

function onError(data, status){
    console.log(data.msg);
    alert("亲，可能由于网络原因，目前无法添加地址，请稍后再试吧。(5)");
}
