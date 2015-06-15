$(function(){
    // if(!isLogin()){
        // $('#user_address').bind('focus',function(){
            // window.location.href="wx-login.htm?go=zuofan";
            // return;
        // });
    // }
    // else{
        // loadAddr();
    // }
    
    //
    var jiadianArr = [];
    var jiadianList = baseData['jiadian'];
    
    if (typeof localStorage['selectType'] !== 'undefined' && localStorage['selectType'] !== ''){
        var typeA = localStorage['selectType'].split(',');
        console.log(typeA);
    }
    $.each(jiadianList,function(i,item){
        if($.inArray('jiadian_'+item[0], typeA)!=-1){
            var check = ' checked="checked" ';
        }
        else{
            check = '';
        }
        var h = '<li class="am-g am-list-item-desced" style="padding:5px 10px 5px 10px;">\
            <div style="float:left;width:80%">\
              <div>'+item[1]+'</div>\
              <div class="am-list-item-text">清洗价：'+item[3]+'元/台</div>\
            </div>\
            <div style="float:right;width:5%;height:40px;padding-top:10px">\
              <input id="jiadian_'+item[0]+'" type="checkbox" name="jiadian" value="'+item[0]+'_'+item[3]+'_'+item[4]+'_'+item[1]+'"'+check+'>\
            </div>\
          </li>';
        jiadianArr.push(h);
    });
    var jiadianH = jiadianArr.join('');
    $('#jiadian_list').html(jiadianH);

    var listArr=[];
    var typeArr = [];
    var totalPrice = 0;

    // $("[name=jiadian]:checkbox").bind("click", function () {
       // if(this.checked){
           //计算总价,显示,记录本地存储,返回时使用
           // listArr.push(this.value);
           // var v = this.value.split('_');
           
           // totalPrice += parseInt(v[1]);
       // }
       // else{
        // totalPrice -= parseInt(v[1]);
        // listArr.pop()
       // }
    // });
    
    //下一步
    $('#jiadian_next').bind('click', function(){
        var ck = $("[name=jiadian]:checkbox");
        $.each(ck,function(i,item){
          if(ck[i].checked){
            var v = ck[i].value.split('_');
            totalPrice += parseInt(v[1]);
            listArr.push(ck[i].value);
            typeArr.push(ck[i].id);
          }
        });
        if (totalPrice == 0){
            alert("请选择需要清洗的家电项目");
            return false;
        }
        //存储用户所选的家电项目列表
        localStorage['jiadianList'] = listArr;
        localStorage['selectType'] = typeArr;
        localStorage['jiadianTotalPrice'] = totalPrice;
        window.location.href='wx-jiadian-2.html';
    });
}());
