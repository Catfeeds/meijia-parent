
//修改按钮事件
function btn_update(id) {
	location.href = appRootUrl + "account/register?id=" + id;
}

//删除按钮事件
function btn_del(id) {
	var statu = confirm("确定要删除吗?");
    if(!statu){
        return false;
    }
    location.href = appRootUrl + "account/delete/" + id;
}
