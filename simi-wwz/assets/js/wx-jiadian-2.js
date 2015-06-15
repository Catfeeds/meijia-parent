$(function(){

    if(localStorage['jiadianTotalPrice']){
        $('#jiadian_total_price').text(localStorage['jiadianTotalPrice']+'元');
    }
    if(localStorage['jiadianList']){
        var jiadianArr = [];
        var jiadianList = localStorage['jiadianList'].split(',');
        $.each(jiadianList,function(i,item){
            var a = item.split('_');
            var h = '<li class="am-g am-list-item-desced">\
                <div style="float:left;width:50%">'+a[3]+'</div>\
                <div style="float:left;width:30%;text-align:center;">1</div>\
                <div style="float:right;width:20%;text-align:right;">'+a[1]+'元</div>\
              </li>';
            jiadianArr.push(h);
        });
        var jiadianH = jiadianArr.join('');
        $('#jiadian_selected').html(jiadianH);
    }

    $('#jiadian_next2').bind('click', function(){
        if(typeof localStorage['jiadianTotalPrice'] === 'undefined' ||
           typeof localStorage['jiadianTotalPrice'] === 'null' ||
                  localStorage['jiadianTotalPrice'] === '' || 
                  localStorage['jiadianTotalPrice'] === '0' ||
                  parseInt(localStorage['jiadianTotalPrice'])<=0){
                  return;
        }
        window.location.href='wx-jiadian-3.html';
    });
}());
