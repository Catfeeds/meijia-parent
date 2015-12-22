
var $form = $('#login-form');
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

$("#login-btn").on('click', function(e) {
	var $form = $('#login-form');
	var validator = $form.data('amui.validator');
	$form.validator();
	if (!validator.isFormValid()) {
		return false;
	}

	$('#login-form').submit();
});



