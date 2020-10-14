jQuery(function($) {
	$(document).off("focus",".ke-input-text").on("focus",".ke-input-text",function(){
		return false;
	});
	var ke_container = $("#ke_control").find(".ke-container");
	KindEditor.remove('#czms');
	var editor = KindEditor.create('#czms', $.extend(true,{},newsOption,{
		afterCreate: function () {
		
			this.sync();
			this.readonly(false);
			this.focus();
			
			ke_container = $("#ke_control").find(".ke-container");
			$('#ajaxForm').validateForm( {
				//提交前的回调函数
				beforeSubmit : function(formData, jqForm, options) {
					editor.sync();

					var czms = editor.text();
					if (!$.founded(czms)) {
						$(ke_container).errorClass("操作描述内容必须填写!");
						return false;
					} else {
						$(ke_container).successClass();
						return true;
					}
					
				}
			});
	    },
	    afterBlur: function () {
	    	this.sync();

	    	if (!$.founded(this.text())) {
				$(ke_container).errorClass("操作描述内容必须填写!");
			} else {
				$(ke_container).successClass();
			}
	    } 
	}));

	window.setTimeout(function(){
		editor.readonly(false);
	}, 1000);

});