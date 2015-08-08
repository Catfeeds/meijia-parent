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
	$("input[name='cityId']:checkbox:checked").each(function() {
		cityStr = cityStr + $(this).val() + ",";
	});
	var s1 = cityStr.substring(0, cityStr.lastIndexOf(","));

	var regionStr = "";
	$("input[name='regionId']:checkbox:checked").each(function() {
		regionStr = regionStr + $(this).val() + ",";
	});
	var s2 = regionStr.substring(0, regionStr.lastIndexOf(","));
	$("#cityIdStr").val(s1);
	$("#regionIdStr").val(s2);
	$("#region").modal('hide');
});
$('#regionId').trigger('change');