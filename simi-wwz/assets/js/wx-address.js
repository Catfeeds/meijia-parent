$(function(){
    //用户未登录
    if(!isLogin()){
        window.location.href="wx-login.html#go=wx-address";
        return;
    };
    var userPhone = localStorage['user_phone'];

    //获取用户地址列表
    getAddress(userPhone);
    $('#add_address').bind('click',function(){
        if (queryVal('go')!==null && queryVal('go')!==''){
            window.location.href='wx-add-address.html?go='+queryVal('go');
        }
        else{
            window.location.href='wx-add-address.html';
        }
    });

    //更换常用地址
    $('#address_list').delegate('li','click',function(){
        var lid = this.id;
        var idArr = lid.split('_');
        var addr = $("#"+idArr[2]).val();
        $.ajax({
            type:"POST",
            url:siteAPIPath+"user/post_addrs.json",
            dataType:"json",
            cache:false,
            data:{"mobile":userPhone,
                  "addr_id":idArr[2],
                  "city_id":idArr[0],
                  "cell_id":idArr[1],
                  "addr":addr,
                  "is_default":"1"},
            success : onChangeSuccess,
            error : onChangeError
        });
        return false;
    });
}());

//获取用户地址列表
function getAddress(userPhone){
    $.ajax({
        type:"GET",
        url:siteAPIPath+"user/get_addrs.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone},
        success : onListSuccess,
        error : onListError
    });
}
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法获取地址列表，请稍后再试。");
    return;
  }
  var addrArr = data.data;
  var addrList = [];
  var addrH = '';
  $.each(addrArr,function(i,item){
    var lid =item.city_id+"_"+item.cell_id+"_"+item.addr_id;
    var addrA = [cities[item.city_id], ' ', item.cell_name, item.addr, '<input type="hidden" value="',item.addr,'" id="', item.addr_id ,'">'];
    //var addrs = cities[item.city_id]+" "+item.cell_name+item.addr;//小区名+门牌号
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
    addrList.push(tmpA.join(''));
  });
  if(addrList.length){
    $('#address_list').html(addrList.join(''));
  }
}

function onListError(data, status){
    console.log(data.msg);
    alert("目前无法获取地址列表，请稍后再试。(5)");
}

//更换常用地址后
function onChangeSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("暂时无法更新常用地址，请稍后再试。");
    return;
  }

  localStorage['user_address'] = data.addr;
  localStorage['user_addr_id'] = data.addr_id;
  localStorage['user_city_id'] = data.city_id;

  getAddress(localStorage['user_phone']);

  //跳 原页面或地址列表页
  if (queryVal('go')!==null && queryVal('go')!==''){
    var go = 'wx-'+queryVal('go')+'.html';
    window.location.href = go;
  }
  // else{
    // window.location.href = 'wx-address.html';
  // }
}

function onChangeError(data, status){
    console.log(data.msg);
    alert("暂时无法更新常用地址，请稍后再试。(5)");
}