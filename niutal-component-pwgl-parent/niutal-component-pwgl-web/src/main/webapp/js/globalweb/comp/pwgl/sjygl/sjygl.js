$(function($){
	$(document).off("click", "#btn_zj").on("click", "#btn_zj", function(e){
		//增加
		$.showDialog(_path + "/pwgl/sjygl/zj.zf", $.pw.getTitle(this), $.extend({}, addConfig, {"width":"700px"}));
	}).off("click", "#btn_xg").on("click", "#btn_xg", function(e){
		//修改
		var id = $("#tabGrid").getKeys();
		if(id.length != 1){
			$.alert("请选择一条进行修改！");
		}
		$.showDialog(_path + "/pwgl/sjygl/xg.zf?id=" + id, $.pw.getTitle(this), $.extend({}, modifyConfig, {"width":"700px"}));
	}).off("click", "#btn_sc").on("click", "#btn_sc", function(e){
		//删除
		var pks = $("#tabGrid").getKeys();
		if(pks.length == 0){
			$.alert("请选择至少一条记录！");
		}else{
			$.confirm("确认删除已选的" + pks.length + "条记录吗？", function(res){
				if(res){
					$.post(_path + "/pwgl/sjygl/sc.zf", {pks:pks.join(",")}, function(data){
						if(data.status == "success" || data.status == "SUCCESS"){
							$.success(data.message, searchResult);
						}else if(data.status == "fail" || data.status == "FAIL"){
							$.alert(data.message);
						}else{
							$.error(data.message);
						}
					});
				}
			});
		}
		//$('#tabGrid').plcz(_path + "/pwgl/sjygl/sc.zf", '删除');
	});
});