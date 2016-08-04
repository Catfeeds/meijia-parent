$("#cityId").on('change',function(){

	
	var cityId = $("#cityId").find("option:selected").val();
	
	if (cityId == "") return false;
	
	var params = {};
	params.city_id = cityId;
	$.ajax({
		type : "get",
		url : appRootUrl + "insurance/get_insurance_base.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			var result = data.data;
			
			$("#shebaoMin").html(result.shebao_min);
			$("#shebaoMax").html(result.shebao_max);
			$("#gjjMin").html(result.gjj_min);
			$("#gjjMax").html(result.gjj_max);
			
			$("#shebao").val(result.shebao_min);
			$("#gjj").val(result.gjj_min);
		},
		error : function() {
			return false;
		}
	});
});

// 点击计算
$("#calculate").on("click",function() {
			
	// 数据校验
	var formValidity = $('#toolsInsuForm').validator().data('amui.validator').validateForm().valid;
	
	if (!formValidity) {
		return false;
	}
		
	// 发送请求，得到 配置的 基数比例
	var shebao = $("#shebao").val();
	var gjj = $("#gjj").val();

	var params = {};
	
	params.city_id = $("#cityId").find("option:selected").val();
//	params.region_id = $("#regionId").find("option:selected").val();
	params.shebao = shebao;
	params.gjj = gjj;
	$.ajax({
		type : "POST",
		url : appRootUrl + "insurance/math_insurance.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			console.log(data);
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			var vo = data.data;

			$("#pensionP").html(vo.pensionP);
			$("#lpensionP").html(vo.pensionPio);
			
			$("#pensionC").html(vo.pensionC);
			$("#lpensionC").html(vo.pensionCio);
			
			$("#medicalP").html(vo.medicalP);
			$("#lmedicalC").html(vo.medicalPio);
			
			$("#medicalC").html(vo.medicalC);
			$("#lpensionC").html(vo.medicalCio);
			
			
			$("#unemploymentP").html(vo.unemploymentP);
			$("#lunemploymentP").html(vo.unemploymentPio);
			
			$("#unemploymentC").html(vo.unemploymentC);
			$("#lunemploymentC").html(vo.unemploymentCip);
			
			
			$("#injuryP").html(vo.injuryP);
			$("#linjuryP").html(vo.injuryPio);
			
			$("#injuryC").html(vo.injuryC);
			$("#linjuryC").html(vo.injuryCio);
			
			$("#birthP").html(vo.birthP);
			$("#lbirthP").html(vo.birthPio);
			
			$("#birthC").html(vo.birthC);
			$("#lbirthC").html(vo.birthCio);
			
			$("#fundP").html(vo.fundP);
			$("#lfundP").html(vo.fundPio);
			
			$("#fundC").html(vo.fundC);
			$("#lfundC").html(vo.fundCio);
			
			$("#totalP").html(vo.totalP);
			$("#totalC").html(vo.totalC);
			

			
		},
		error : function() {
			return false;
		}
	});
	
});
