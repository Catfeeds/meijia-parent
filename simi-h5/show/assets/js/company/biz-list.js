var userId = getUrlParam("user_id");

$("#gotoPartner").attr("href","store-my-index.html?user_id="+userId);
$("#gotoInvite").attr("href","invite-index.html?user_id="+userId);