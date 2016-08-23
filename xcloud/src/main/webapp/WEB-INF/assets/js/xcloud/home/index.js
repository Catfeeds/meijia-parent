
//首页精选
$.ajax({
	type : "get",
	url : "http://bolohr.com/api/tags/get_tag_posts/?slug=%E9%A6%96%E9%A1%B5%E7%B2%BE%E9%80%89,%E6%8B%9B%E8%81%98,%E5%9F%B9%E8%AE%AD,%E7%BB%A9%E6%95%88,%E5%91%98%E5%B7%A5,%E8%A7%84%E5%88%92,%E8%96%AA%E8%B5%84,%E8%81%8C%E5%9C%BA,%E8%A1%8C%E6%94%BF,%E5%B2%97%E4%BD%8D,%E8%A1%8C%E4%B8%9A,%E5%90%88%E5%90%8C,%E7%BB%8F%E8%90%A5,%E6%A1%88%E4%BE%8B,%E7%BA%A0%E7%BA%B7,%E6%AE%B5%E5%AD%90,%E7%A6%8F%E5%88%A9,%E5%85%A5%E8%81%8C,%E9%9D%A2%E8%AF%95,%E8%80%83%E8%AF%81,%E5%90%90%E6%A7%BD&count=5&order=DESC&include=id,title,url,custom_fields&page=1",
	dataType : "JSONP",
	async : true,
	success : function(rdata, textStatus) {
		var datas = rdata.posts;
		
		var h = "";
		$.each(datas, function(i, c) {
			var total = c.custom_fields.views;
			if (total == undefined) total = 0;
			h+="<tr>";
			h+="<td class=\"am-text-center\">" + (i + 1) + "</td>";
			h+="<td>" + c.title + "</td>";
			h+="<td>" + total + "看过</td>";
			h+="</tr>";
		});
		
		$("#news-index > tbody").append(h);
	},

});


//薪资资讯
$.ajax({
	type : "get",
	url : "http://bolohr.com/?json=get_category_posts&count=5&order=DESC&id=64&include=id,title,url,custom_fields&page=1",
	dataType : "JSONP",
	async : true,
	success : function(rdata, textStatus) {
		var datas = rdata.posts;
		
		var h = "";
		$.each(datas, function(i, c) {
			h+="<tr>";
			h+="<td class=\"am-text-center\">" + (i + 1) + "</td>";
			h+="<td>" + c.title + "</td>";
			h+="<td>" + c.custom_fields.views + "看过</td>";
			h+="</tr>";
		});
		
		$("#news-64 > tbody").append(h);
	},

});


//招聘咨询
$.ajax({
	type : "get",
	url : "http://bolohr.com/?json=get_category_posts&count=5&order=DESC&id=62&include=id,title,url,custom_fields&page=1",
	dataType : "JSONP",
	async : true,
	success : function(rdata, textStatus) {
		var datas = rdata.posts;
		
		var h = "";
		$.each(datas, function(i, c) {
			h+="<tr>";
			h+="<td class=\"am-text-center\">" + (i + 1) + "</td>";
			h+="<td>" + c.title + "</td>";
			h+="<td>" + c.custom_fields.views + "看过</td>";
			h+="</tr>";
		});
		
		$("#news-62 > tbody").append(h);
	},

});

//绩效资讯
$.ajax({
	type : "get",
	url : "http://bolohr.com/?json=get_category_posts&count=5&order=DESC&id=65&include=id,title,url,custom_fields&page=1",
	dataType : "JSONP",
	async : true,
	success : function(rdata, textStatus) {
		var datas = rdata.posts;
		
		var h = "";
		$.each(datas, function(i, c) {
			h+="<tr>";
			h+="<td class=\"am-text-center\">" + (i + 1) + "</td>";
			h+="<td>" + c.title + "</td>";
			h+="<td>" + c.custom_fields.views + "看过</td>";
			h+="</tr>";
		});
		
		$("#news-65 > tbody").append(h);
	},

});