$(function($){
	$("#ajaxForm select").trigger("chosen");
	
	$("#ajaxForm").off("change", "#mkdm").on("change", "#mkdm", function(e){
		//模块变更，改变字段
		$("#col").find("option:not(:first)").remove();
		var v = $(this).val();
		if(v != ""){
			$.get(_path + "/pwgl/sjygl/getCols.zf", {mkdm:v}, function(data){
				if(data.length == 0){
					$.alert("无可增加字段！");
				}else{
					var _html = new Array();
					for(var i = 0; i < data.length; i++){
						_html.push('<option value="');
						_html.push(data[i].col);
						_html.push('" data-text="');
						_html.push(data[i].com);
						_html.push('">');
						_html.push(data[i].col);
						_html.push('（');
						_html.push(data[i].com);
						_html.push('</span>）');
						_html.push('</option>');
					}
					$("#col").append(_html.join(""));
					$("#col").trigger("chosen:updated");
				}
				return false;
			});
		}else{
			$("#col").trigger("chosen:updated");
		}
	}).off("change", "#col").on("change", "#col", function(e){
		//字段改变，自动填充注释
		var text = $(this).find("option:selected").data("text");
		$("#com").val(text);
	});
});