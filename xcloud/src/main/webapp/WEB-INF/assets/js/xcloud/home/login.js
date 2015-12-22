$(function() {
	var $form = $('#login-form');
	var $tooltip = $('<div id="vld-tooltip">提示信息！</div>');
	$tooltip.appendTo(document.body);
	$form.validator();
	var validator = $form.data('amui.validator');
	$form.on('focusin focusout', '.am-form-error input', function(e) {
		if (e.type === 'focusin') {
			var $this = $(this);
			var offset = $this.offset();
			var width = $this.width();
			var msg = $this.data('validationMessage')
					|| validator.getValidationMessage($this.data('validity'));
			$tooltip.text(msg).show().css({
				left : offset.left + width + 30,
				top : offset.top
			});
		} else {
			$tooltip.hide();
		}
	});
});