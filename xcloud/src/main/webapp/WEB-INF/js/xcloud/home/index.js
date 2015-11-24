$(".h5a-sidebar").affix({
	offset : {
		top : -10,
		bottom : function() {
			return this.bottom = $(".bs-footer").outerHeight(!0)
		}
	}
}), $("#external-events div.external-event").each(function() {
	var a = {
		title : $.trim($(this).text())
	};
	$(this).data("eventObject", a), $(this).draggable({
		zIndex : 999,
		revert : !0,
		revertDuration : 0
	})
});
var a = new Date, b = a.getDate(), c = a.getMonth(), d = a.getFullYear();
$("#calendar").fullCalendar(
				{
					theme : false,
					lang : 'zh-cn',
					height : 480,
					header : {
						left : "prev,next today",
						center : "title",
						right : "month,agendaWeek,agendaDay"
					},
					events : [ {
						title : "All Day Event",
						start : new Date(d, c, 1)
					}, {
						title : "Long Event",
						start : new Date(d, c, b - 5),
						end : new Date(d, c, b - 2),
						className : "orange"
					}, {
						id : 999,
						title : "Repeating Event",
						start : new Date(d, c, b - 3, 16, 0),
						allDay : !1
					}, {
						id : 999,
						title : "Repeating Event",
						start : new Date(d, c, b + 4, 16, 0),
						allDay : !1
					}, {
						title : "Meeting",
						start : new Date(d, c, b, 10, 30),
						allDay : !1
					}, {
						title : "Lunch",
						start : new Date(d, c, b, 12, 0),
						end : new Date(d, c, b, 14, 0),
						allDay : !1
					}, {
						title : "Birthday Party",
						start : new Date(d, c, b + 1, 19, 0),
						end : new Date(d, c, b + 1, 22, 30),
						allDay : !1,
						className : "blue"
					}, {
						title : "Click for Google",
						start : new Date(d, c, 28),
						end : new Date(d, c, 29),
						url : "http://google.com/",
						className : "red"
					} ],
					editable : !0,
					droppable : !0,
					drop : function(a, b) {
						var c = $(this).data("eventObject"), d = $(this).data(
								"class"), e = $.extend({}, c);
						e.className = d, e.start = a, e.allDay = b,
								$("#calendar").fullCalendar("renderEvent", e,
										!0), $("#drop-remove").is(":checked")
										&& $(this).remove()
					}
				})
