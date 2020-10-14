$(function($){
	$("#ajaxForm select").trigger("chosen");

	$('.filestyle').each(function() {
		var $this = $(this),
			options = {
				'input': $this.attr('data-input') === 'false' ? false : true,
				'icon': $this.attr('data-icon') === 'false' ? false : true,
				'buttonBefore': $this.attr('data-buttonBefore') === 'true' ? true : false,
				'disabled': $this.attr('data-disabled') === 'true' ? true : false,
				'size': $this.attr('data-size'),
				'buttonText': $this.attr('data-buttonText'),
				'buttonName': $this.attr('data-buttonName'),
				'iconName': $this.attr('data-iconName'),
				'badge': $this.attr('data-badge') === 'false' ? false : true,
				'placeholder': $this.attr('data-placeholder')
			};
		$this.filestyle(options);
	});

	//var supportType = '.PDF.DOC.DOCX';
	var supportType = '.PDF.DOCX';

	$(document).off("change", "input:file.filestyle").on("change", "input:file.filestyle", function(e){
		//选择文件
		var filePath = $(this).val();
		var fileType = filePath.substr(filePath.lastIndexOf("."), filePath.length).toUpperCase();
		if(supportType.indexOf(fileType) == -1){
			$.alert("上传文件格式错误，当前仅支持" + supportType + "格式，请重新选择文件！");
			$(this).filestyle('clear');
			return false;
		}
	});
});