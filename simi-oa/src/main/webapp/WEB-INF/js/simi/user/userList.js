function doReset(){
		$("#mobile1").attr("value","");
	}

 function download(){
    var url="/"+appName+"/user/download_project";
    window.open(url);
}
 
 function orderByScore() {
	 $("#orderByStr").val("order by score desc");
	 $("#searchForm").submit();
 }