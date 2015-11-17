
//根据服务大类获取服务小类
$("#serviceTypeId").on('change', function(){

	if ( $("#servicePriceId").length <= 0 ) { 
		return false;
	}	
	
	$serviceTypeId = $(this).val();

	//发送ajax请求根据服务大类ID获取服务小类ID
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
				//针对所在大类的下拉联动

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

