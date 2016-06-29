
//点击计算
$("#calculate").on("click",function(){
	
	//数据校验
	var formValidity = $('#toolsInsuForm').validator().data('amui.validator').validateForm().valid;
	
	if(!formValidity){
		return false;
	}
	
	//社保缴纳基数
	var socialInsurance =  $("#socialInsurance").val();
	
	//公积金缴纳基数
	var fundPrice = $("#fundPrice").val();
	
	if(Number(socialInsurance) < 1890 || Number(socialInsurance) > 19000){
		
		alert("社保公积金基数,最低1890,最高 19000");
		
		return false;
	}
	
	
	if(Number(fundPrice) < 1290 || Number(fundPrice) > 14000){
		
		alert("社保公积金基数,最低1890,最高 19000");
		return false;
	}
	
	
	//发送请求，得到 配置的 基数比例
	getInsurance();
})


function getInsurance(){
	
	var params = {};
	
	params.city_id = $("#cityId").find("option:selected").val();
	params.region_id = $("#regionId").find("option:selected").val();
	
	$.ajax({
		type : "get",
		url : appRootUrl + "company/get_insurance_setting.json",
		data : params,
		dataType : "json",
		cache : true,
		success : function(data) {
			
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}
			
			
			//社保缴纳基数
			var socialIns =  $("#socialInsurance").val();
			
			//公积金缴纳基数
			var fundPrice = $("#fundPrice").val();
			
			
			var result =  data.data;
			
			//个人部分， 所有数字保留2位小数,比例
			var pensionP 	  = (socialIns*result.pension_p/100).toFixed(2);
			var medicalP      = (socialIns*result.medical_p/100).toFixed(2);
			var unemploymentP = (socialIns*result.unemployment_p/100).toFixed(2);
			var injuryP		  = (socialIns*result.injury_p/100).toFixed(2);
			var birthP 		  = (socialIns*result.birth_p/100).toFixed(2);
			
			var fundP 		  = (fundPrice*result.fund_p/100).toFixed(2);
			
			//公司部分，均保留2位小数，比例
			var pensionC 	  = (socialIns*result.pension_c/100).toFixed(2);
			var medicalC 	  = (socialIns*result.medical_c/100).toFixed(2);
			var unemploymentC = (socialIns*result.unemployment_c/100).toFixed(2);
			var injuryC 	  = (socialIns*result.injury_c/100).toFixed(2);
			var birthC 		  = (socialIns*result.birth_c/100).toFixed(2);
			
			var fundC 		  = (fundPrice*result.fund_c/100).toFixed(2);
			
			
			//个人合计，钱
			var sumP = Number(pensionP) + Number(medicalP) + Number(unemploymentP)
					 +Number(injuryP) + Number(birthP) + Number(fundP);	
			
			//公司合计，钱
			var sumC = Number(pensionC) + Number(medicalC) + Number(unemploymentC)
			 		 +Number(injuryC) + Number(birthC) + Number(fundC);	
			
			
			//养老
			var pensionHtml = "<tr >"
						+ "<td>养老</td>"
						+ "<td>"+ pensionP +"(<font color='red'>"+result.pension_p+"%</font>)</td>"
						+ "<td>"+ pensionC +"(<font color='red'>"+result.pension_c+"%</font>)</td>"
						+"</tr>";
			//医疗
			var medicalHtml = "<tr >"
						+ "<td>医疗</td>"
						+ "<td>"+ medicalP +"(<font color='red'>"+result.medical_p+"%</font>)</td>"
						+ "<td>"+ medicalC +"(<font color='red'>"+result.medical_c+"%</font>)</td>"
						+"</tr>";
			//失业
			var unemploymentHtml = "<tr >"
						+ "<td>失业</td>"
						+ "<td>"+ unemploymentP +"(<font color='red'>"+result.unemployment_p+"%</font>)</td>"
						+ "<td>"+ unemploymentC +"(<font color='red'>"+result.unemployment_c+"%</font>)</td>"
						+"</tr>";
			
			//工伤
			var injuryHtml = "<tr >"
						+ "<td>工伤</td>"
						+ "<td>"+ injuryP +"(<font color='red'>"+result.injury_p+"%</font>)</td>"
						+ "<td>"+ injuryC +"(<font color='red'>"+result.injury_c+"%</font>)</td>"
						+"</tr>";
			//生育
			var birthHtml = "<tr >"
						+ "<td>生育</td>"
						+ "<td>"+birthP +"(<font color='red'>"+result.birth_p+"%</font>)</td>"
						+ "<td>"+birthC +"(<font color='red'>"+result.birth_c+"%</font>)</td>"
						+"</tr>";
			//公积金
			var fundHtml = "<tr >"
						+ "<td>公积金</td>"
						+ "<td>"+ fundP +"(<font color='red'>"+result.fund_p+"%</font>)</td>"
						+ "<td>"+ fundC +"(<font color='red'>"+result.fund_c+"%</font>)</td>"
						+"</tr>";
			
			//合计
			var sumHtml = "<tr class='am-active'>"
				+ "<td>合计</td>"
				+ "<td>"+ sumP.toFixed(2) +"</td>"
				+ "<td>"+ sumC.toFixed(2) +"</td>"
				+"</tr>";
			
			
			var bodyHtml = pensionHtml + medicalHtml + unemploymentHtml + injuryHtml
						+ birthHtml + fundHtml + sumHtml;
			
			
			$("#displayTBody").html("");
			$("#displayTBody").append(bodyHtml);
			
		},
		error : function() {
			return false;
		}
	});
	
}

//点击重置的处理
$("#resetForm").on("click",function(){
	
	// 重置时， 浏览器会把 select 重置到第一个option，  区县需要根据第一个 城市 ，联动获得
	var cityId = $("#cityId option:first").val();
	
	getRegionByCity(cityId);
	
});


