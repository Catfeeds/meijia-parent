
//添加 新的 领用登记 记录
$("#btn-use-asset-add").click(function() {
	
	location.href = "/xcloud/xz/assets/use_asset_form?id=0";
});


//领用记录详情
function getUseAssetDetail(id){
	
	location.href = "/xcloud/xz/assets/use_asset_form?id="+id;
}