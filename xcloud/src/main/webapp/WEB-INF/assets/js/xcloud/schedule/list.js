
var currentLangCode = 'zh-cn';

function renderCalendar() {
	$('#calendar').fullCalendar({
		header : {
			left : 'prev,next today',
			center : 'title',
			right : 'month,agendaWeek,agendaDay'
		},
//		defaultDate : '2015-12-12',
		lang : currentLangCode,
		height: 450,
		buttonIcons : true, // show the prev/next text
		weekNumbers : true,
		editable : false,
		eventLimit : true, // allow "more" link when too many
		axisFormat:'H:mm',
		eventSources: [

       // your event source
       {
           url: '/myfeed.php', // use the `url` property
           color: 'yellow',    // an option!
           textColor: 'black'  // an option!
       }],
		
		// events
//		events : [ {
//			title : '差旅规划',
//			url : 'card-swtx.html',
//			start : '2015-12-01T16:00:00'
//		}, {
//			title : '年会',
//			start : '2015-12-07',
//			end : '2015-12-10'
//		}, {
//			id : 999,
//			title : '例会',
//			start : '2015-12-09T16:00:00'
//		}, {
//			id : 999,
//			title : '写周报',
//			start : '2015-12-16T16:00:00'
//		}, {
//			title : '面试邀约',
//			start : '2015-12-11',
//			end : '2015-12-13'
//		}, {
//			title : '部门会议',
//			start : '2015-12-12T10:30:00',
//			end : '2015-12-12T12:30:00'
//		}, {
//			title : '通知公告',
//			start : '2015-12-12T12:00:00'
//		}, {
//			title : '例会',
//			start : '2015-12-12T14:30:00'
//		}, {
//			title : '访问官网',
//			url : 'http://51xingzheng.cn/',
//			start : '2015-12-28'
//		} ],
	});
}

renderCalendar();
