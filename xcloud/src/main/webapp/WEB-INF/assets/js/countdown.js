!function ($) {

  $.fn.countDown = function( callback ){

  		var s = parseInt( $(this).text() ),
  		$this = $(this);
  		if( s == NaN ) return false;

  		var c = function(){
	  		var t = setTimeout(function( ){
	  			$this.text( --s );
	  			if( s > 0 ){
	  				c( );
	  			}else{
            if(typeof callback == 'function')
	  				callback();
	  			}
	  		}, 1000);

  		}
  		c( );
  };

}(window.jQuery);