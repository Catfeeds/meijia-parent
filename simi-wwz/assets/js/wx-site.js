//var siteAPIPath = "http://localhost:8080/onecare/app/"; //本地
//var localUrl = "http://localhost:8080";
var localUrl = "http://www.yougeguanjia.com";
var siteAPIPath = "http://www.yougeguanjia.com/onecare/app/"; //正式
//var siteAPIPath = "http://182.92.237.135/onecare/app/";  //测试

var cities = {1:'',2:'北京',3:'天津'};
var weekDay = {0:'周日',1:'周一',2:'周二',3:'周三',4:'周四',5:'周五',6:'周六'};
var holidays = ['2015-2-17','2015-2-18','2015-2-19','2015-2-20','2015-2-21','2015-2-22','2015-2-23','2015-2-24',
                '2015-2-25','2015-2-26','2015-2-27','2015-2-28','2015-4-5','2015-5-1'];
var serviceDict = {1:{'name':'做饭','image':'images/index-zuofan.png','key':'zuofan'},
                   2:{'name':'洗衣','image':'images/index-xiyi.png','key':'xiyi'},
                   3:{'name':'家电清洗','image':'images/index-jiadian.png','key':'jiadian'},
                   4:{'name':'保洁','image':'images/index-baojie.png','key':'baojie'},
                   5:{'name':'擦玻璃','image':'images/index-caboli.png','key':'caboli'},
                   6:{'name':'管道疏通','image':'images/index-guandao.png','key':'guandao'},
                   7:{'name':'新居开荒','image':'images/index-xinju.png','key':'xinju'}};

var infoTypeDict = {1:{'name':'INFO_NAME_1','image':''},
                    2:{'name':'INFO_NAME_3','image':''},
                    3:{'name':'INFO_NAME_3','image':''}};

var baseData={'baojie':{'2':'401', '3':'402', '4':'403'},
              'zuofan':{'2':'101', '3':'102',' 4':'103', '5':'104'},
              'caboli':{'8':'501'},//算
              'xinju':{},//算
              'guandao':{},//算
              'jiadian':[['301','空调','空调描述',150,150,'台'],
                         ['302','油烟机(中式)','油烟机(中式)',160,160,'台'],
                         ['303','油烟机(欧式)','油烟机(欧式)',200,200,'台'],
                         ['304','微波炉','微波炉',100,100,'台'],
                         ['305','洗衣机','洗衣机',120,120,'台'],
                         ['306','电冰箱(双门)','电冰箱(双门)',120,120,'台'],
                         ['307','电冰箱(三门)','电冰箱(三门)',160,160,'台'],
                         ['308','电冰箱(对开门)','电冰箱(对开门)',180,180,'台'],
                         ['309','烤箱','烤箱',100,100,'台']
                        ]
             };

// $(function(){
    // if(supports_html5_storage()){}
    // /*window.location.reload();*/
// }());

function supports_html5_storage() {
    try {
        return 'localStorage' in window && window['localStorage'] !== null;
    } catch (e) {
        alert("您的浏览器不支持H5本地存储，无法完成相关功能。");
        return false;
    }
}
function isLogin(){
    return typeof localStorage['user_phone']!='undefined' && localStorage['user_phone']!='';
}
function changeAddr(f,g){
    if(isLogin){
        var go = '';
        if (g!='' || typeof g !=='undefined')
            go='?go='+g;
        f=='false'?window.location.href="wx-add-address.html"+go:window.location.href="wx-address.html"+go;
    }
    return;
}

//载入用户地址
function loadAddr(){
    if(localStorage['user_address']=="undefined" || typeof localStorage['user_address']==="undefined" || localStorage['user_address']==''){
        //请求用户地址
        $.ajax({
            type : "GET",
            url  : siteAPIPath+"user/get_addrs.json",
            dataType: "json",
            cache : false,
            data : {"mobile":localStorage['user_phone']},
            success : onUserAddrSuccess,
            error : onUserAddrError
        });
    }
    else{
        var pageName = getPageName();
        var go = pageName[0].split('-')[1];
        var o={"userAddr":cities[localStorage['user_city_id']]+' '+localStorage['user_address'],
               "funCall":"changeAddr('true','"+go+"')",
               "buttonText":"更换",
               "disabled":""
              };
        displayAddr(o);
    }
}

function displayAddr(o)
{
    var userCell='<label for="user-address">服务地址:</label>\
                    <div class="am-input-group">\
                        <input type="text" class="am-form-field" value=\"'+o.userAddr+'\" '+o.disabled+'>\
                        <span class="am-input-group-btn">\
                            <button type="button" class="am-btn am-btn-warning" onclick="'+o.funCall+';">'+o.buttonText+'</button>\
                        </span>\
                    </div>';
    $('#user_cell').html(userCell);
}

//用户地址处理
function onUserAddrSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("未能获取用户地址，请稍后再试。");
    return;
  }

  //记录用户常用地址
  if(data.data[0]){
      localStorage['user_address'] = data.data[0].cell_name + data.data[0].addr;
      localStorage['user_addr_id'] = data.data[0].addr_id;//小区id
      localStorage['user_city_id'] = data.data[0].city_id;
      var pageName = getPageName();
      var go = pageName[0].split('-')[1];
      var o={"userAddr":cities[localStorage['user_city_id']]+' '+localStorage['user_address'],
             "funCall":"changeAddr('true','"+go+"')",
             "buttonText":"更换",
             "disabled":""};
     displayAddr(o);
  }
  else{//没有地址
    var pageName = getPageName();
    var go = pageName[0].split('-')[1];
    var o={"userAddr":"您需要先添加地址",
           "funCall" :"changeAddr('false','"+go+"')",
           "buttonText":"添加地址",
           "disabled":"disabled"
           };
    displayAddr(o);
  }
}
function onUserAddrError(data, status){
    //console.log(data.msg);
    alert("未能获取用户地址，请稍后再试。(5)");
}
function getPageName()
{
    var a = location.href;
    var b = a.split("/");
    var c = b.slice(b.length-1, b.length).toString().split(".");
    return c.slice(0, 1);
}
function getPageName2()
 {
     var strUrl=location.href;
     var arrUrl=strUrl.split("/");
     var strPage=arrUrl[arrUrl.length-1];
     return strPage;
 }
function createDateList(){
  var rangeFlag = true;
  var rangeDays = 9;
  var today = new Date;
  var thisYear = today.getFullYear();
  var thisMonth = today.getMonth() + 1;
  var thisDay = today.getDate();
  var thisHour = today.getHours();

  var minYear = thisYear;
  var maxYear = thisYear;

  var startMonth = thisMonth;
  var startDay = thisDay;
  var endMonth = 12;
  var endDay = 31;

  var sa = [];
  var count = 0;
  for (var y = minYear; y <= maxYear; y++){
    for(var m = startMonth; m <= endMonth; m++){
      // if(m < 10)
        // var sm = y + "0" + m;
      // else
        // var sm = y + "" + m;
      var sm = y + '-' + m;

      if (m>startMonth)
        startDay = 1;

      var maxDays=31;
      if (m==4 || m==6 || m==9 || m==11){
        maxDays=30
      }
      if (m==2){
        if ((thisYear/4)!=parseInt(thisYear/4)){
           maxDays=28
        }
        else{
          maxDays=29
        }
      }
      for(var d=startDay; d<=maxDays; d++){
        // if(d < 10)
          // var v = sm + "0" + d;
        // else
          // var v = sm + "" + d;
        var v = sm + '-' + d;
        if($.inArray(v,holidays)!=-1) continue;

        var cd = new Date(y,m-1,d);
        var wd = weekDay[cd.getDay()];
        if (thisHour>=16 && d==thisDay)//18点之后
            continue;

        var s = y+"/"+m+"/" + d + ' ('+wd+')';
        h = "<option value='" + v + "'>" + s + "</option>";
        sa.push(h);
        if(rangeFlag){
          if (count++ == rangeDays){
            //console.log(sa.join(''));
            return sa.join('');
          }
        }
        if(m == endMonth && d == endDay){
          //console.log(sa.join(''));
          return sa.join('');
        }
      }
    }
  }
}

function createTimeList(){
    var minArr = [':00',':30'];
    var dtNow = new Date();
    var fy = dtNow.getFullYear();
    var m = dtNow.getMonth() + 1;
    if(m < 10)
        m = "0" + m;

    var d = dtNow.getDate();
    if(d < 10)
      d = "0" + d;

    var dtomorrow = dtNow.getDate()+1;
    if(dtomorrow < 10)
      dtomorrow = "0" + dtomorrow;

    var dateT = fy + '' + m + '' + d;     //今天
    var dateS = fy + '' + m + '' + dtomorrow; //明天
    var dateChoiced = $('#service_date_list').val();
    var tmpDt = dateChoiced.split('-');
    tmpDt[1] = parseInt(tmpDt[1])<10 ? '0'+tmpDt[1] : tmpDt[1];
    tmpDt[2] = parseInt(tmpDt[2])<10 ? '0'+tmpDt[2] : tmpDt[2];
    dateChoiced = tmpDt.join('');

    var dt = dateAdd('h',5);//4小时后
    var startHour = dt.getHours();//整数
    //alert(startHour);
    var currentHour = dtNow.getHours();
    var startIndex = 0;

    //console.log('当前小时='+currentHour+',当前分钟='+dtNow.getMinutes());
    //console.log('选择='+dateChoiced+',今天='+dateT+',明天='+dateS);

    if(dateChoiced == dateT){//所选日期为今天
        if(currentHour >= 0 && currentHour<8){
            startHour = 12;
        }
        else if(currentHour>=8 && currentHour < 20){
            if(dtNow.getMinutes() >= 30){
               currentHour = currentHour+1;
            }
            else{
                startIndex = 1;
                currentHour = currentHour+0.5;
            }
            startHour = parseInt(currentHour+4);
            startHour = startHour > 20 ? 20 : startHour;
        }
        else{
            //
        }
    }
    else if(dateChoiced == dateS){//所选日期为明天
        startIndex = 0;
        if(currentHour >= 0 && currentHour<18){
            startHour = 8;
        }
        else if(currentHour>=18 && currentHour<24){
            startHour = 12;
        }
    }
    else if(dateChoiced > dateS){//所选日期是明天以后的
        startIndex = 0;
        startHour = 8;
    }
    else{
        startIndex = 0;
        startHour = 8;
    }
    endHour = 20;//最晚显示到20点
    timeList = [];
    for (var i=startHour; i<=endHour; i++){
        //alert('new='+minArr.length+',start= '+startIndex);
        for(var k=startIndex; k<minArr.length; k++){
            //alert(k);
            var t = i + minArr[k];
            var h = '<option value="' + t + '">'+ t +'</option>'
            timeList.push(h);
            if(i == 20) break;
        }
        startIndex = 0;
    }
    var timeH = timeList.join('');
    return timeH;
}
function dateAdd(strInterval, Number) {
    //var dtTmp = this;
    switch (strInterval) {
        case 's': return new Date(Date.now() + (1000 * Number));
        case 'n': return new Date(Date.now() + (60000 * Number));
        case 'h': return new Date(Date.now() + (3600000 * Number));
        case 'd': return new Date(Date.now() + (86400000 * Number));
        case 'w': return new Date(Date.now() + ((86400000 * 7) * Number));
        //case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        //case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        //case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
    }
}

function changeServiceTimeList()
{
    var timeListHTML = createTimeList();
    $("#service_time_list").html(timeListHTML);
}
function queryVal(q){
    if(window.location.href.match(new RegExp("(&|\\u003F)" + q + "=([^&]*)(&|$)")))
      return RegExp.$2;
    else
      return null;
}
function logout(){
    localStorage.clear();
    window.location.href = 'index.html';
    return;
}
function getUrlArgStr(){
    var q=location.search.substr(1);
    var qs=q.split('&');
    var argStr='';
    if(qs){
        for(var i=0;i<qs.length;i++){
            argStr+=qs[i].substring(0,qs[i].indexOf('='))+'='+qs[i].substring(qs[i].indexOf('=')+1)+'&';
        }
    }
    return argStr;
}