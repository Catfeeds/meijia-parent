// 身份证号码的验证规则
function isIdCardNo(num) {

	var len = num.length, re;
	if (len == 15)
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/);
	else if (len == 18)
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/);
	else {
		// alert("输入的数字位数不对。");
		return false;
	}
	var a = num.match(re);
	if (a != null) {
		if (len == 15) {
			var D = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getYear() == a[3] && (D.getMonth() + 1) == a[4]
					&& D.getDate() == a[5];
		} else {
			var D = new Date(a[3] + "/" + a[4] + "/" + a[5]);
			var B = D.getFullYear() == a[3] && (D.getMonth() + 1) == a[4]
					&& D.getDate() == a[5];
		}
		if (!B) {
			// alert("输入的身份证号 "+ a[0] +" 里出生日期不对。");
			return false;
		}
	}
	if (!re.test(num)) {
		// alert("身份证最后一位只能是数字和字母。");
		return false;
	}
	return true;
}

//验证是否为正整数
function verifyNumber($str){
	$pattern = /^[0-9]*[1-9][0-9]*$/;
	if($str.match($pattern)){
		return true;
	}else{
		return false;
	}
}	

//验证手机号的方法
function verifyMobile(mobile){
	$pattern = /^1[3,4,5,6,7,8，9][0-9]{9}$/;
	if(mobile.match($pattern)){
		return true;
	}else{
		return false;
	}
}
//验证价钱的方法
function verifyPrice(price){
//	$pattern =/^\d{4}\.\d{2}$/;
	$pattern =/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	if(price.match($pattern)){
		return true;
	}else{
		return false;
	}
}
//验证邮箱的方法
function isEmail(str){ 
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(str); 
} 	