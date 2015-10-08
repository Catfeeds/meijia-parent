
//step wizard
$('#register').stepy({
  backLabel: '上一步',
  block: true,
  nextLabel: '下一步',
  titleClick: true,
  titleTarget: '.stepy-tab'
});

//公司行业

var tradeJson = getDictTrade();
if (tradeJson != undefined) {
	
	var output = "";
	$.each(tradeJson, function(i,pitem){
		
		if (pitem.parent_id > 0) return;
		console.log(pitem.trade_id + "----" + pitem.name + "----" + pitem.parent_id);
		var optGroup = "";
		optGroup = "<optgroup label=\""+pitem.name+"\">";
		var tradeId = pitem.trade_id;
		$.each(tradeJson, function(j,item) {
			if (item.parent_id == tradeId && item.parent_id > 0) {
				optGroup+= "<option value=\""+item.trade_id+"\">"+ item.name +"</option>";
			}
		});
		optGroup += "</optgroup>";
		output += optGroup;
		
	});	
	
	$("#company_trade").html(output);
//	console.log(output);
}

      