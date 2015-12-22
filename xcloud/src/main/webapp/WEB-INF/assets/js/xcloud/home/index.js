$('#slider1').flexslider({
	start : function() {
		$('#slider1').find('.tr-slider-title li:first').fadeIn(200);
	},
	before : function() {
		$('#slider1').find('.tr-slider-title li').fadeOut(100);
	},
	after : function() {
		var n = $('#slider1').find('.am-active-slide').index();
		$('#slider1').find('.tr-slider-title li').eq(n - 1).stop().fadeIn(500);
	}
});