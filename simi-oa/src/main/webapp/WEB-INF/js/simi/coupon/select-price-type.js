
//根据省份ID获取城市列表
$("#serviceTypeId").on('change', function(){

	if ( $("#servicePriceId").length <= 0 ) { 
		return false;
	}	

	
	$serviceTypeId = $(this).val();
	//comsole.log($serviceTypeId);
//	if(0 == $provinceId){
//		$optionList = '<option value="0">全部</option>';
//		$("#cityId").html($optionList);
//		return;
//	}
	
	//发送ajax请求根据省ID获取城市ID
	$.ajax({
		type: 'GET',
		url: appRootUrl + '/interface-dict/get-price-by-type.json',
		dataType: 'json',
		cache: false,
		data:{serviceTypeId:$serviceTypeId},
		success:function($result){
			if(0 == $result.status){
				var servicePriceIdSelectedId = 0;
				if ( $("#servicePriceIdSelectedId").length >0 ) { 
					servicePriceIdSelectedId = $('#servicePriceIdSelectedId').val();
				}		
				console.log(servicePriceIdSelectedId);
				//针对所在城市的下拉联动

				$cityOptions = '<option value="0">全部</option>';
				//$optionList = "";
				$.each($result.data, function(i, obj) {
					if (obj.id == servicePriceIdSelectedId) {
						$cityOptions += '<option value="'+obj.id+'" selected>' + obj.name + "</option>";
					} else {
						$cityOptions += '<option value="'+obj.id+'">' + obj.name + "</option>";
					}

				});
				
				$("#servicePriceId").html($cityOptions);
			}
		},
		error:function(){
			
		}
	});
});


//根据省份ID获取城市列表
/*$("#cityId").on('change', function(){

//	if ( $("#regionId").length <= 0 ) { 
//		return false;
//	}	
	
	var cityId = $(this).val();
//	if(0 == cityId){
//		$optionList = '<option value="0">全部</option>';
//		$("#cityId").html($optionList);
//		return;
//	}
	
	//发送ajax请求根据省ID获取城市ID
	$.ajax({
		type: 'GET',
		url: appRootUrl + '/interface-dict/get-region-by-cityid.json',
		dataType: 'json',
		cache: false,
		data:{cityId:cityId},
		success:function($result){
			if(0 == $result.status){
				var citySelectedId = 0;
				if ( $("#regionSelectedId").length >0 ) { 
					regionSelectedId = $('#regionSelectedId').val();
				}		
				
				//针对所在城市的下拉联动

				$regionOptions = '<option value="0">全部</option>';
				//$optionList = "";
				$.each($result.data, function(i, obj) {
					if (obj.region_id == regionSelectedId) {
						$regionOptions += '<option value="'+obj.region_id+'" selected>' + obj.name + "</option>";
					} else {
						$regionOptions += '<option value="'+obj.region_id+'">' + obj.name + "</option>";
					}

				});
				
				$("#regionId").html($regionOptions);
			}
		},
		error:function(){
			
		}
	});
});*/
