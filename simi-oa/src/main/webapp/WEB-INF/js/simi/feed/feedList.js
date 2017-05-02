function btn_star(fid, featured) {
	
	if (featured == 0) {
		if (confirm("确认要置为精选吗?")) {
			$.ajax({
				type: 'POST',
				url: appRootUrl + '/feed/set_star.json',
				dataType: 'json',
				async:false,
				cache: false,
				data:{fid:fid},
				success:function(result){
					location.replace(location.href);
				},
				
			});
		}
	} else {
		if (confirm("确认要去掉精选吗?")) {
			$.ajax({
				type: 'POST',
				url: appRootUrl + '/feed/set_star.json',
				dataType: 'json',
				async:false,
				cache: false,
				data:{fid:fid},
				success:function(result){
					location.replace(location.href);
				},
				
			});
		}
	}
	
}

function btn_feed_del(fid) {

	if (confirm("确认要删除此问答吗，此操作不可恢复?")) {
		$.ajax({
			type: 'POST',
			url: appRootUrl + '/feed/del.json',
			dataType: 'json',
			async:false,
			cache: false,
			data:{fid:fid},
			success:function(result){
				location.replace(location.href);
			},
		});
	}
}
