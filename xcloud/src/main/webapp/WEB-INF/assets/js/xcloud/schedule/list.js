
var currentLangCode = 'zh-cn';

function renderCalendar() {
	$('#calendar').fullCalendar({
		header : {
			left : 'prev,next today',
			center : 'title',
			right : 'month,basicWeek,agendaDay'
		},
		theme: true,
//		defaultDate : '2015-12-12',
		firstHour: 8,
		lang : currentLangCode,
		height: 550,
		buttonIcons : true, // show the prev/next text
		weekNumbers : false,
		weekends : true,
		weekMode:'liquid',
		editable : false,
		eventLimit : true, // allow "more" link when too many
		axisFormat:'H:mm',

//		timeFormat: {
//			  agenda: (settings.clock) ? 'HH:mm{ - HH:mm}' : settings.agenda,
//			  ' ': (settings.clock) ? 'HH:mm' : 'h(:mm)t'
//			},
		eventSources: [

       // your event source
       {
           url: '/xcloud/schedule/get-card-list.json', // use the `url` property
           color: 'white',    // an option!
           textColor: 'black'  // an option!
       }],
		
		// events
//		events : [ {
//			title : '差旅规划',
//			url : 'card-swtx.html',
//			start : '2015-12-01 16:00:00'
//		}, {
//			title : '年会',
//			start : '2015-12-07',
//			end : '2015-12-10'
//		}, {
//			id : 999,
//			title : '例会',
//			start : '2015-12-09 16:00:00'
//		}, {
//			id : 999,
//			title : '写周报',
//			start : '2015-12-16 16:00:00'
//		}, {
//			title : '面试邀约',
//			start : '2015-12-11',
//			end : '2015-12-13'
//		}, {
//			title : '部门会议',
//			start : '2015-12-12 10:30:00',
//			end : '2015-12-12 12:30:00'
//		}, {
//			title : '通知公告',
//			start : '2015-12-12 12:00:00'
//		}, {
//			title : '例会',
//			start : '2015-12-12 14:30:00'
//		}, {
//			title : '访问官网',
//			url : 'http://51xingzheng.cn/',
//			start : '2015-12-28'
//		} ]
	});
}

renderCalendar();
