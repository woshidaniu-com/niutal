$(function(){
var ops = {
	 url: 'getCsszList.zf',  
	 uniqueId: "id", 
	 pagination:false,
	 toolbar:  '#but_ancd',
	 showColumns:true,
	 showToggle:true,
	 showRefresh:true,
	 mobileResponsive:true,
	 columns: [
        {field: 'ssmk', title: '所属模块',width:'10%'}, 
        {field: 'zs', title: '设置项',width:'20%'}, 
        {field: 'zdz',title: '设置值',width:'30%',formatter:function(cellvalue,rowObject,index){
        	 var html = [];
        	 var zdm = jQuery.defined(rowObject.zdm)?rowObject.zdm:"";
        	 var zdz = jQuery.defined(rowObject.zdz)?rowObject.zdz:""; 
    		//字段类型：
    		//1 ：表示 下拉框
    		//2：表示输入框
    		//3：表示单选框
    		//4：表示多选框
    		//5:表示日期选择宽 
        	 var zdlx = parseInt(rowObject.zdlx||2); 
        	 if (zdlx == 1){
        	 	html.push('<select name="'+zdm+'" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" class="form-control"  validate="'+(rowObject.zdzyq||"{required:true}")+'" val="'+zdz+'">');
				html.push('<option value="">--请选择--</option>');
				//循环数据
				$.each(rowObject["zdsjList"],function(i,item){
					html.push('<option '+(zdz ==  item["key"] ? ' selected="selected" ' : "")+' value="'+item["key"]+'">'+item["value"]+'</option>');
				});
        		html.push('</select>');
        	 } else if (zdlx == 2){
				 html.push('<input name="'+zdm+'" type="text" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+zdz+'"  class="form-control"  validate="'+(rowObject.zdzyq||"{required:true}")+'"/>');
        	 } else if (zdlx == 3){
				//循环数据
				$.each(rowObject["zdsjList"],function(i,item){
					html.push(' <label class="radio-inline">');
					html.push('<input name="'+zdm+'" type="radio" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+item["key"]+'" '+(zdz ==  item["key"] ? ' checked="checked" ' : "")+' validate="'+(rowObject.zdzyq||"{required:true}")+'">'+item["value"]);
					html.push(' </label>');
				});
        	 } else if (zdlx == 4){
        	 	var zdzs = zdz.split(",");
				//循环数据
				$.each(rowObject["zdsjList"],function(i,item){
					html.push(' <label class="checkbox-inline">');
					html.push('<input name="'+zdm+'" type="checkbox" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+item["key"]+'" '+( zdzs.indexOf(item["key"]) >= 0 ? ' checked="checked" ' : "")+' validate="'+(rowObject.zdzyq||"{required:true}")+'">'+item["value"]);
					html.push(' </label>');
				});
        	 } else if (zdlx == 5){
        		 var format = format = 'YYYY-MM-DD';
				 html.push('<input name="'+zdm+'" value="'+zdz+'" type="text" data-zddm="'+zdm+'" data-old="'+zdz+'" placeholder="" class="form-control laydate-icon-zfcolor"   onfocus="laydate({format:\'' + format + '\'})" style="display:inline-block" validate="'+(rowObject.zdzyq||"{required:true}")+'"></input>');
        	 }else{
        		 //nothing todo
        	 }
        	 return html.join("");
        } },
        {field: 'bz',title: '备注',width:'40%'}
     ],
     searchParams:function(){
     	return {ssmk:$("#ssmk").val()};
     }
};
$('#dataTable').loadGrid(ops);
$("#btn_bc").bind("click",function(){
	submitForm("ajaxForm",function(responseData,statusText){
		if(responseData["status"] == "success"){
			$.success(responseData["message"]);
		}else{
			$.error(responseData["message"]);
			}
		});
	});
});