

//根据城市动态加载 城市 下辖的 区县
$("#cityId").on('change',function(){
	
	var cityId = $(this).val();
	
	if(cityId == ""){
		
		var  $optionList = "<option value=''>请选择区县</option>";
		$("#regionId").html($optionList);
		return false;
	}
	
	$.ajax({
		type:"get",
		url: "/simi-oa/interface-dict/get-region-by-cityid.json",
		dataType:'json',
		data: {
			"cityId" : cityId
		},
		success: function(data){
			
			var regionList = data.data;
			
			$("#regionId").html("");
			var optionHtml = "<option value=''>请选择区县</option>";
			
			var selectRegionId = "";
			
			if($("#returnRegionId").length > 0){
				selectRegionId = $("#returnRegionId").val();
			}
			
			
			for(var i= 0, j= regionList.length ; i <j ; i++){
				
				if(regionList[i].region_id == selectRegionId){
					optionHtml += "<option value="+regionList[i].region_id + " selected	>" +regionList[i].name+"</option>";
				}else{
					optionHtml += "<option value="+regionList[i].region_id + ">" +regionList[i].name+"</option>";
				}
			}
			
			$("#regionId").append(optionHtml);
		},
		error : function(){
			
			console.log('选择城市得到区县,请求error');
		}
	});
	
});

//页面加载事件

$(document).ready(function(){
	
	$("#cityId").trigger("change");
	
});

