

var userId = getUrlParam("user_id");
//商品管理
$("#storePrice").attr("href","store-price-list.html?user_id="+userId);
//订单处理
$("#orderControll").attr("href","store-order-list.html?user_id="+userId);
//人员管理
$("#userControll").attr("href","invite-index.html");