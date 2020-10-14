<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>打印</title>
[#include "/globalweb/head/head_v5.ftl" /]
<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/api/jquery.media.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<div>
	<a class="media" id="pdf-shower" data-href="${key}"></a>
</div>
<script type="text/javascript">
	$(function(){
		var $obj = $("#pdf-shower");
		$obj.attr("href", _path + "/pwgl/api/showPdf.zf?key=" + encodeURIComponent($obj.data("href")));
		$obj.addClass("pdf-ready");
		$obj.media({
			width:"100%",
			height:document.body.clientHeight + "px"
		});
	});
</script>