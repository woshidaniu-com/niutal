jQuery(function($) {
	
	// 关闭过滤模式，保留所有标签
	KindEditor.options.filterMode = false;
	var ke_container = $("#ke_control").find(".ke-container");
	KindEditor.remove('#fbnr');
	var editor = KindEditor.create('#fbnr', $.extend(true,{},newsOption,{
		showMask:false,
		afterCreate: function () {
			var _self = this; 
			this.sync();
			this.readonly(false);
			this.focus();
			ke_container = $("#ke_control").find(".ke-container");
			$('#ajaxForm').validateForm( {
				//提交前的回调函数
				beforeSubmit : function(formData, jqForm, options) {

					_self.sync();
				
					var fbnr = editor.text();
					if (!$.founded(fbnr)) {
						$(ke_container).errorClass("通知内容必须填写!");
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
				$(ke_container).errorClass("通知内容必须填写!");
			} else {
				$(ke_container).successClass();
			}
	       
	    } 
	}));

	window.setTimeout(function(){
		editor.readonly(false);
	}, 1000);

	$("#selectID").click(function() {
		var mxdxlb = $("#mxdxlb").val();
		switch (mxdxlb) {
		case '1'://学生
				$.showDialog(_path + '/xtgl/xwgl_cxMxStudentIndex.html',
						'选择面向对象', mxdxConfig);
				break;
			case '2'://教师
				$.showDialog(_path + '/xtgl/xwgl_cxMxteacherIndex.html',
						'选择面向对象', mxdxConfig2);
				break;
			case '3'://岗位
				$.showDialog(_path + '/xtgl/xwgl_cxMxGwIndex.html',
						'选择面向对象', mxdxConfig3);
				break;
			}
		});

});