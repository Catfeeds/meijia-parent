$(".serviceType-confirm-btn").on("click", function() {
	var str = "";
	$("[type='checkbox'][checked]").each(function() {
		str = str + $(this).val() + ",";
	});
	var s = str.substring(0, str.lastIndexOf(","));
	$("#partnerTypeIds").val(s);
	$('#myModal').modal('hide');
});

$(".region-confirm-btn").on("click", function() {
	var cityStr = "";
	var cityName = "";
	$("input[name='cityId']:checkbox:checked").each(function() {
		cityStr = cityStr + $(this).val() + ",";
		var name = $(this).parent().find("input:hidden").val();
		cityName = cityName +name+",";
	});
	//所选城市Id数组
	var s1 = cityStr.substring(0, cityStr.lastIndexOf(","));
	//所选城市名字数组
	var cityNames = cityName.substring(0, cityName.lastIndexOf(",")).split(",");

	var regionStr = "";
	var regionName = ""
	var regionCityId =""
	$("input[name='regionId']:checkbox:checked").each(function() {
		regionStr = regionStr + $(this).val() + ",";
			var name = $(this).prev().val();;
			var cityId = $(this).next().val();;
		regionName = regionName +name+",";
		regionCityId = regionCityId +cityId+",";
	});
	var s2 = regionStr.substring(0, regionStr.lastIndexOf(","));
	var regionNames = regionName.substring(0,regionName.lastIndexOf(",")).split(",");
	var regionCityIds = regionCityId.substring(0,regionCityId.lastIndexOf(",")).split(",");
	$("#cityIdStr").val(s1);
	$("#regionIdStr").val(s2);
	
	$("#cityAndRegion").empty();
	var cityHtml ="";
	var a = s1.split(",");
	var b = s2.split(",");
	for(var i=0;i<a.length;i++){
		cityHtml = cityHtml+"<div class='col-md-2' align='right' >"+cityNames[i]+"</div>"+"<div class='col-md-10'> "
		var reg ="";	
		for(var j=0;j<b.length;j++){
				if(a[i]==regionCityIds[j]){
					reg =reg+"  "+regionNames[j]+"  ";
				}
		}
		cityHtml=cityHtml+reg+"</div>";
	}
	$("#cityAndRegion").append(cityHtml);
	$("#region").modal('hide');
});
$('#regionId').trigger('change');