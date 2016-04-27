
//添加 新的 入库记录
$("#btn-comm-asset-add").click(function() {
	
	location.href = "/xcloud/xz/assets/commpany_asset_form?id=0&name=''";
});

function getAssetDetail(id,name){
	
	location.href = "/xcloud/xz/assets/commpany_asset_form?id="+id+"&name="+name;
}