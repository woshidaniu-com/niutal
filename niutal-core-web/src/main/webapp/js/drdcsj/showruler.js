function loadRuler() {
	var url = _path + '/niutal/dr/import_getRulers.html';
	$.ajax( {
		type : "POST",
		url : url,
		data : {
			drmkdm : $("#drmkdm").val(),
		},
		success : function(data) {
			var lhzjhtml="";
			var lhzjmes="";
			for ( var i = 0; i < data.length; i++) {
				//验证信息
				var tdmes="";
				var drlpz=data[i];
				var lyzgzlist=data[i].lyzgzdzList;
				if(lyzgzlist.length>0){
					for ( var j = 0; j < lyzgzlist.length; j++) {
						var lyzgzdz=lyzgzlist[j];
						var gzts=lyzgzdz.gzts;
						var yzgzModel=lyzgzlist[j].yzgzModel;
						//如果对照表配置没有验证规则提示，则使用验证规则自带提示
						if(!gzts){
							gzts=yzgzModel.gzts;
						}
						//如果是联合验证住字段或不是联合验证
						if(lyzgzdz.lhyz=="0"){
							tdmes+=j+1+"."+gzts;
							tdmes+="</br>";
						}else if(lyzgzdz.lhyz=="1"){
							lhzjhtml+="<tr><th>";
							lhzjhtml+=drlpz.drlmc;
							lhzjmes+=j+1+".&nbsp;&nbsp;"+gzts;
							lhzjmes+="</br>";
						}else{
							lhzjhtml+=","+drlpz.drlmc;
						}
					}
					//如果为空这是联合主键，不做处理
					if(tdmes!=""){
						var trhtml="<tr><th>";
						trhtml+=drlpz.drlmc;
						trhtml+="</th><td align='center'>";
						trhtml+=getcolor(tdmes);
						trhtml+="</td></tr>";
						$("tbody").append($(trhtml));
					}
				}
			}
			//处理联合主键显示
			if(lhzjhtml!=""){
				lhzjhtml+="</th><td align='center'>";
				lhzjhtml+=getcolor(lhzjmes);
				lhzjhtml+="</td></tr>";
				$("tbody").append($(lhzjhtml));
			}
		}
	});
}
function getcolor(mes){
	return "<font color='red'>"+mes+"</font>"
}