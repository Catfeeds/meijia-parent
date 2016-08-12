
var currentLangCode = 'zh-cn';

function renderCalendar() {
	$('#calendar').fullCalendar({
		header : {
			left : 'prev,next today',
			center : 'title',
			right : 'month,basicWeek,agendaDay'
		},
		// theme: true,
		// defaultDate : '2015-12-12',
		firstHour : 8,
		lang : currentLangCode,
		height : 550,
		buttonIcons : true, // show the prev/next text
		weekNumbers : false,
		weekends : true,
		weekMode : 'liquid',
		editable : false,
		eventLimit : true, // allow "more" link when too many
		// axisFormat:'HH:mm',
		formatDate : 'HH:mm',
		
		// timeFormat: {
		// agenda: (settings.clock) ? 'HH:mm{ - HH:mm}' :
		// settings.agenda,
		// ' ': (settings.clock) ? 'HH:mm' : 'h(:mm)t'
		// },
		eventSources : [

		// your event source
		{
			url : '/xcloud/schedule/get-card-list.json', // use the
			// `url`
			// property
			color : 'white', // an option!
			textColor : 'black' // an option!
		} ],
		
		eventMouseover: function(calEvent, jsEvent) {
		    var tooltip = '<div class="tooltipevent" style="width:200px;height:50px;background:#fff;position:absolute;z-index:10001;">' + calEvent.description + '</div>';
		    $("body").append(tooltip);
		    $(this).mouseover(function(e) {
		        $(this).css('z-index', 10000);
		        $('.tooltipevent').fadeIn('500');
		        $('.tooltipevent').fadeTo('10', 1.9);
		    }).mousemove(function(e) {
		        $('.tooltipevent').css('top', e.pageY + 10);
		        $('.tooltipevent').css('left', e.pageX + 20);
		    });
		},

		eventMouseout: function(calEvent, jsEvent) {
		     $(this).css('z-index', 8);
		     $('.tooltipevent').remove();
		},
	
	});
}

renderCalendar();
