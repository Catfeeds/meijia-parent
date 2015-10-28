myApp.onPageInit('register2', function (page) {
	var mobile =  page.query.mobile;
	 var name =  page.query.name;
	//进入页面加载列表	
	
	//页面加载时获得标签列表
	 $$.ajax({
         type : "GET",
         url  : siteAPIPath+"user/get_tag_list.json?mobile="+mobile +"&name="+name,
         dataType: "json",
         cache : true,
         async : false,
 		statusCode : {
 			200 : tagsListSuccess,
 			400 : ajaxError,
 			500 : ajaxError
 		},
 		
 });
	 
//提交按鈕
	
	 $$('#information_btn').on('click', function(e){

				$$("#mine-addr-save").attr("disabled", true);
				
				if (orderFormValidation() == false) {
					return false;
				}
				
				var formData = myApp.formToJSON('#user-form-register');
				formData.mobile = mobile;
				formData.name = name;
				
				$$.ajax({
					type : "POST",
					url : siteAPIPath+ "user/post_user_register.json?",
					dataType : "json",
					cache : false,
					data : formData,
					statusCode : {
						200 : saveOrderSuccess,
						400 : ajaxError,
						500 : ajaxError
					}
				});
});
});
var tagsListSuccess= function(data, textStatus, jqXHR) {
	myApp.hideIndicator();
	var result = JSON.parse(data.response);
	var tags = result.data;
	
	if (result.status == "999") {
		myApp.alert(result.msg);
		return;
	}
	//所有标签的集合
	var aaa = tags.list;
	//已经选中标签的集合
	var bbb = tags.tag_list;
	
	var selectedTagIds = "";
	if(bbb !=null) {
		for(var j = 0; j< bbb.length;j++){
			selectedTagIds+= bbb[j] + ",";
		}	
		
		$$("#tag_ids").val(selectedTagIds);
	}
	var hasSelected = false;
	var tagHtml = "";
	if(aaa !=null){
	for(var i = 0; i<aaa.length;i++){
		hasSelected = false;
		if(bbb !=null){
		for(var j = 0; j< bbb.length;j++){
			
			if(aaa[i].tag_id == bbb[j]){
				
				hasSelected = true;
				
				break;
			}
		}
		}
		if(hasSelected){
			if (i % 2 == 0 ) {
				tagHtml +=  "<a href='#' id='tag_id_'"+tags.list[i].tag_id+" onclick='tagClick("+tags.list[i].tag_id+", $$(this))' class='button button-cancel button-round kehuxq-biaoqian'  style='margin-left: 2%;'>"+tags.list[i].tag_name+"</a>";
			} else {
				tagHtml +=  "<a href='#' id='tag_id_'"+tags.list[i].tag_id+" onclick='tagClick("+tags.list[i].tag_id+", $$(this))' class='button button-cancel button-round kehuxq-biaoqian'>"+tags.list[i].tag_name+"</a>";
			}
		}else{
			
			if (i % 2 == 0 ) { 
				tagHtml +=  "<a href='#' id='tag_id_'"+tags.list[i].tag_id+" onclick='tagClick("+tags.list[i].tag_id+", $$(this))' class='button button-cancel button-round kehuxg-biaoqian'  style='margin-left: 2%;'>"+tags.list[i].tag_name+"</a>";
			} else {
				tagHtml +=  "<a href='#' id='tag_id_'"+tags.list[i].tag_id+" onclick='tagClick("+tags.list[i].tag_id+", $$(this))' class='button button-cancel button-round kehuxg-biaoqian'>"+tags.list[i].tag_name+"</a>";
			}
			
		}		
		
		
	}}
	$$("#tagNames").append(tagHtml);
	 //$$("#degreeId").text(tags.degree_type_list);
	
	console.log(tags);
};
function tagClick(tagId, obj) {
	//如果未选中，则换class为选中   

	var tagIds = $$("#tag_ids").val();
	
	//kehuxq-biaoqian = 选中
	if (obj.is(".kehuxg-biaoqian")) {
		obj.addClass("kehuxq-biaoqian");
		obj.removeClass("kehuxg-biaoqian");
		
		if (tagIds.indexOf(tagId + ",") < 0) {
			tagIds += tagId + ",";
		}
	//kehuxg-biaoqian = 未选中	
	} else {
		obj.removeClass("kehuxq-biaoqian");
		obj.addClass("kehuxg-biaoqian");	
		if (tagIds.indexOf(tagId + ",") >= 0) {
			tagIds = tagIds.replace(tagId + ",", "");
		}		
	}
	
	 $$("#tag_ids").val(tagIds);
	 
	 console.log( $$("#tag_ids").val());
	
	
	
}
	 function orderFormValidation() {
			var formData = myApp.formToJSON('#user-form-register');

			console.log(formData);
			if (formData.realName == "") {
				myApp.alert("请输入真实姓名");
				return false;
			}
			// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
			 var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		       if(reg.test(formData.idCard) === false || formData.idCard == ""){
				myApp.alert("请输入正确的身份证号码");
				return false;
			}
			/*if (formData.realName == "") {
				myApp.alert("年龄");
				return false;
			}*/
			if (formData.major == "") {
				myApp.alert("请输入所学专业");
				return false;
			}
			
			return true;
		}
	 function saveOrderSuccess(data, textStatus, jqXHR) {
	 	 $$("#mine-addr-save").removeAttr('disabled');
	 	myApp.hideIndicator();
	 	var results = JSON.parse(data.response);
	 	
	 	if (results.status == "999") {
	 		myApp.alert(results.msg);
	 		return;
	 	}
	 	if (results.status == "0") {
	 		myApp.alert("信息提交成功，请耐心等待系统审批！");
	 		mainView.router.loadPage("register3.html");
	 	}
	 }
