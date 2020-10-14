$(function($){
	$.extend({
		pw:{
			getTitle: function(){
				var $this = $(arguments[0]);
				if(null != $this.text() && $this.text() != ""){
					return $this.text();
				}
				return "";
			}
		}
	})
});