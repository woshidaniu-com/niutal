

// 查询
function searchResult() {
	var map = {};
	map["sffpyh"] = jQuery('#sffpyh').val();
	map["jsmc"]   = jQuery('#jsmc').val();
	map["gnmkdm"] = jQuery('#gnmkdm').val();
	search('tabGrid', map);
}

function ckYh(obj) {
	var url = "yhgl_ckJsyh.html?jsdm=" + obj;
	showWindow('查看用户', 650, 360, url);
}

/*
 * 绑定操作按钮
 */
function bdan() {
	var btnzj = jQuery("#btn_zj");
	var btnsc = jQuery("#btn_sc");
	var btnxg = jQuery("#btn_xg");
	var btngnsq = jQuery("#btn_gnsq");
	var btnfpyh = jQuery("#btn_fpyh");
	var btnck = jQuery("#btn_ck");
	// 绑定增加点击事件
	if (btnzj != null) {
		
		btnzj.click(function() {
			showWindow('增加角色', 400, 350, 'jsgl_zjJsxx.html');
                        return false;
		});
	}

	// 绑定删除事件
	if (btnsc != null) {
		btnsc.click(function() {
					var ids = getChecked();
					var url = 'jsgl_scJsxx.html';
					if (ids.length == 0) {
						alert('请选择您要删除的记录！');
						return false;
					}
					ids = ids + "";
					var tmpValue=true; 
					var pkVs = ids.split(",");
					for ( var i = 0; i < pkVs.length; i++) {
						var row = jQuery("#tabGrid").jqGrid('getRowData', pkVs[i]);
						if(row["sfksc"]=='0'){
							tmpValue=false;
						}
					}
					if(!tmpValue){
						alert("您选择的记录集合中含有默认[不可删除]角色，无法删除，请重新选择！");
						return false;
					}
										
				/*	else {
						ids = ids + "";
						var jsdms = ids.split(",");
						for ( var i = 0; i < jsdms.length; i++) {
							var row = jQuery("#tabGrid").jqGrid('getRowData',
									jsdms[i]);
							if (row.sfksc == '1') {
								bkscjsmc += row.jsmc + "，";
							}
							
							if (row.yhnum != "0") {
								yhjsmc += row.jsmc + "，";
							}
						}
						if (yhjsmc != "") {
							yhjsmc =  yhjsmc.substring(0, yhjsmc.length-1)  + "已分配用户不能删除  ";
							alert(yhjsmc);
							return false;
						}else if (bkscjsmc != "") {
							bkscjsmc = bkscjsmc.substring(0, bkscjsmc.length-1) + "为系统默认角色不能删除  ";
							alert(bkscjsmc);
							return false;
						} else {*/
							var _do = function() {
								jQuery.ajaxSetup( {
									async : false
								});
								jQuery.post(url, {
									ids : ids.toString()
								}, function(data) {
									alert(data.toString());
									refershGrid('tabGrid');
								}, 'json');
								jQuery.ajaxSetup( {
									async : true
								});
							};
							showConfirmDivLayer('您确定要删除选择的记录吗？', {
								'okFun' : _do
							});

				});
	}

	// 绑定修改事件
	if (btnxg != null) {
		btnxg.click(function() {
			var id = getChecked();
			if (id.length != 1) {
				alert('请选定一条记录!');
				return;
			}
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			showWindow('修改角色', 400, 350, 'jsgl_xgJsxx.html?jsdm=' + id);
			return false;
		});
	}
	
	// 查看事件
	if (btnck != null) {
		btnck.click(function() {
			var id = getChecked();
			if (id.length != 1) {
				alert('请选定一条记录!');
				return;
			}
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			var url = "yhgl_ckJsyh.html?jsdm=" + id;
			showWindow('查看用户', 650, 360, url);
			return false;
		});
	}

	// 功能授权
	if (btngnsq != null) {
		btngnsq.click(function() {
			gnsq();
			return false;
		});
	}

	// 角色分配用户
	if (btnfpyh != null) {
		btnfpyh.click(function() {
			jsfpyh();
			return false;
		});
	}
}


/**
 * 删除角色信息
 * @param cbvname 复选框名
 * @param url 执行的路径
 * @param mes 弹出的提示语
 * @return
 */
function deleteJsxx(cbvname,url,mes) {
	if (jQuery("input[name='"+cbvname+"']")) {
		var array = jQuery("input[name='"+cbvname+"']");
		var count = 0;
		var pkValue="";
		jQuery.each(array,function(i,n) {
			if (jQuery(array[i]).attr("checked")) {
				count++;
				pkValue += jQuery(array[i]).val()+",";
			}
		}); 
		if(""!=pkValue){
			pkValue = trim(pkValue);
			pkValue = pkValue.substring(0, pkValue.length-1);
		}
		if (count <= 0) {
			alert("请选择要删除的记录！");
			return;
		} else {
			jQuery.ajax({
				url:"jsgl_jcSfksc.html",
				type:"post",
				dataType:"json",
				data:{pkValue:pkValue},
				success:function(data){
				if(data==null){
					if(confirm('确定要删除吗？')){
						subForm(url+pkValue);
					}
				}else{
					alert(data);
					return false;
				}
				}
			});
	        }
	}
}


/**
* 建立子菜单的方法，先生成二级，再递归生成三级
* 
*@param array
*            父菜单代码数组
* @param gnlist
*            所有功能列表
* @param flag
*            回调该函数的标志，区别功能代码是从页面取还是对象取
 * lt 2012.3.28
*/
function createChildMenu(array, gnlist, flag) {
	var ejarray = new Array();
	var index = 0;
	var src = jQuery('#img_open').attr("src");
	var gnczStr = jQuery('#gnczStr').val();
	jQuery.each(
					array,
					function(i, n) {
						if (n != undefined && n != null) {
							var id = flag == 0 ? n.id : n.gnmkdm;
							var child = jQuery('#' + id + '_div');
							var html = '';
							jQuery.each(
											gnlist,
											function(j, obj) {
												if (id == obj.fjgndm
														&& obj.fjgndm != null) {
													if (obj.gnmkdm.length == 7) {// 无子节点
														html += viewBlank(6, 3);
														html += "<a href='#' name='gnid' id='";
														html += obj.gnmkdm;
														html += "' onclick='' style='text-decoration:none!important;'>";
														html += "<img src='";
														html += src;
														html += "' alt='点击展现叶子结点' id='";
														html += obj.gnmkdm;
														html += "_img' onclick='dispChildMenu(this)'/>";
														html += "<input type='checkbox' class='cbvclass' id='";
														html += obj.gnmkdm;
														html += "_cbv' name='sjgndmcbv' value='";
														html += obj.gnmkdm;
														html += "' onclick='selectChild(this);selectParent(this)' style='cursor: pointer;'/>";
														html += obj.gnmkmc;
														html += "</a><br/>";
														html += "<div style='display: none;' id='";
														html += obj.gnmkdm;
														html += "_div' >";
														// 拼接操作按钮
														html += appendCzanHtml(
																obj.gnmkdm,
																getGnmkczStr(
																		obj.gnmkdm,
																		gnczStr));

														html += "</div>";
													} else if (obj.gnmkdm.length == 5) {// 有子节点
														if (obj != undefined
																&& obj != null) {
															ejarray[index] = obj;// 标志的是二级菜单，三级菜单要从二级对象里面取
															index++;
														}
														html += viewBlank(6, 2);
														html += "<a href='#' name='gnid' id='";
														html += obj.gnmkdm;
														html += "' onclick='' style='text-decoration:none!important;'>";
														html += "<img src='";
														html += src;
														html += "' alt='点击展现叶子结点' id='";
														html += obj.gnmkdm;
														html += "_img' onclick='dispChildMenu(this)'/>";
														html += "<input type='checkbox' id='";
														html += obj.gnmkdm;
														html += "_cbv' style='cursor: pointer;' class='cbvclass' name='topcbv' value='";
														html += obj.gnmkdm;
														html += "' onclick='selectChild(this);selectParent(this);'/>";
														html += obj.gnmkmc;
														html += "</a><br/><div style='display: none;' id='";
														html += obj.gnmkdm;
														html += "_div' ></div>";
													}
												}
											});
							jQuery(child).html(html);
						}

						// 这里要生成三级菜单

						if (ejarray.length > 0) {
							// 递归处理
							createChildMenu(ejarray, gnlist, 1);
							ejarray = new Array();
						}

					});
}

/**
 * 拼空格数
 * cent:单位空格数，centnum：单位数
 * lt 2012.4.17
 */
function viewBlank(cent, centnum) {
	var centstr = '';
	var unionstr = '';
	if (cent > 0) {
		for ( var i = 0; i < cent; i++) {
			centstr += '&nbsp;';
		}
	}
	if (centnum > 0) {
		for ( var j = 0; j < centnum; j++) {
			unionstr += centstr;
		}
	}
	return unionstr;
}
			
			
/**
 * 显示子结点菜单
 * @param obj
 * lt 2012.4.18
 */
function dispChildMenu(obj){
	var id = obj.id;
	var divid = document.getElementById(id.replace('_img','_div'));
	var imgid = document.getElementById(id);
	if(divid.style.display == 'block'){
		divid.style.display = 'none';
	
		obj.src=jQuery('#img_open').attr("src");
	}else{
		divid.style.display = 'block';
		obj.src=jQuery('#img_close').attr("src");
	}
}



//选择父菜单选项
function selectParent(obj){
	//选择上级菜单
	var num = 0;
	var parentDiv = obj.parentNode.parentNode;
	var inputName = parentDiv.id;
	var array = parentDiv.getElementsByTagName("input");
	var count = 0;
					return false;
	for (var i=0;i<array.length;i++) {
		count = array[i].checked ? count + 1 : count + 0;
	}
	if (count < 1) {
		document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
	} else {
		if (obj.checked) {
			document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
		}
	}
	
	//选择上上级菜单
	selectParentParent(obj);
}

//选择三级菜单选项
function selectSjmenu(obj){
	//选择上级菜单
	var num = 0;
	var parentDiv = obj.parentNode.parentNode;
	var inputName = parentDiv.id;
	var array = parentDiv.getElementsByTagName("input");
	var count = 0;

	for (var i=0;i<array.length;i++) {
		count = array[i].checked ? count + 1 : count + 0;
	}
	
	if (count < 1) {
		//document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
	} else {
		if (obj.checked) {
			document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
		}
	}
	
	//选择上上级菜单
	selectParentParent(obj);
}

//选择最上层的菜单
function selectParentParent(obj){
	var num = 0;
	var parentDiv = obj.parentNode.parentNode.parentNode;
	var inputName = parentDiv.id;

	var array = document.getElementById(inputName).getElementsByTagName("input");
	var count = 0;
	for (var i=0;i<array.length;i++) {
		count = array[i].checked ? count + 1 : count + 0;
	}
	if (count < 1 ) {
		//最外层的IDV不用选中
		if (inputName != 'topDiv') {
			document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
		}
	} else {
		if (inputName != 'topDiv' && obj.checked) {
			document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
		}
	}
	
	//
	selectParentParentParent(obj);
}

//选择最上层的菜单
function selectParentParentParent(obj){
	var num = 0;
	var parentDiv = obj.parentNode.parentNode.parentNode.parentNode;
	
	var inputName = parentDiv.id;
	if (inputName != null && inputName != "" && document.getElementById(inputName)) {
		var array = document.getElementById(inputName).getElementsByTagName("input");
		var count = 0;
		for (var i=0;i<array.length;i++) {
			count = array[i].checked ? count + 1 : count + 0;
		}
		if (count < 1 ) {
			//最外层的IDV不用选中
			if (inputName != 'topDiv') {
				document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
			}
		} else {
			if (inputName != 'topDiv' && obj.checked) {
				document.getElementById(inputName.replace('_div','_cbv')).checked = obj.checked;
			}
		}
	}
}

/**
 * 获取某三级功能菜单下面的所有操作按钮字符串(注意分隔字符串)
 *    格式如：N010101:zj:增加-N010101:xg:修改
 * @param gnmkdm
 * @return 拼接好字符串，失败则返回""
 */
function getGnmkczStr(gnmkdm,gnczList) {
	var result = "";
	if (gnmkdm != "" && gnczList != "") {
		var array = gnczList.split("-");
		for (var i=0;i<array.length;i++) {
			if (gnmkdm == array[i].substr(0,7)) {
				result += array[i];
				result += "-";
			}
		}
	}
	return result != "" ? result.substr(0,result.length-1) : result;
}

/**
 * 拼接某三级功能菜单下面的操作按钮HTML
 * @param gnczStr 某功能模块的操作按钮字符串
 * @param gnmkdm 某功能模块代码
 * @return 拼接好的字符串
 */
function appendCzanHtml(gnmkdm,gnczStr) {
	var html = "";
	if (gnczStr != "") {
		var array = gnczStr.split("-");
		for (var i=0;i<array.length;i++) {
			var childArray = array[i].split(":");
			if (i==0) {
				html += viewBlank(6,4);//先加空格进行排版
			}
			html +="<a href='#' name='czid' id='";
			html += gnmkdm;
			html += "_";
			html += childArray[1];
			html += "' onclick='' style='text-decoration:none!important;'>";
			
			html += "<input type='checkbox' class='czcbvclass' id='";
			html += childArray[0];
			html += ":";
			html += childArray[1];
			html += "_cbv' name='gnczcbv' value='";
			html += gnmkdm;
			html += ":";
			html += childArray[1];
			html += "' onclick='selectSjmenu(this)' style='cursor: pointer;'/>";
			html += childArray[2];
			html += "</a>&nbsp;&nbsp;&nbsp;";
			
			//每行显示四个按钮，超过换行
			if (i>0 && (((i+1)%4)==0) && i!=(array.length-1)) {
				html += "<br/>";
				html += viewBlank(6,4);
			}
		}
	}
	return html;
}

/**
 * 保存角色功能数据
 * @return
 */
function saveData() {
		jQuery("#gnsqForm").ajaxSubmit({
			target:"#rightContent",
			url:"jsgl_cxJsxx.html"
		});
}

/**
 * 显示角色功能权限
 * @param jsdm
 * @return
 */
function displayJsgnqx(jsdm) {
	var array = jQuery('#topDiv').find("input[type='checkbox']");
	jQuery.each(array,function(i,n) {
		if (jQuery(n)) {
			jQuery(n).attr("checked", false);
		}
	});
	
	if (jsdm != null && jsdm != '') {
		//显示角色功能权限
		jQuery.ajax({
			url:"jsgl_cxJsdmGnqx.html",
			type:"post",
			dataType:"json",
			data:{jsdm:jsdm},
			success:function(data){	
				viewJsgnqx(data);
			}
		});
	}
}

/**
 * 显示角色功能权限
 * @param str
 * @return
 */
function viewJsgnqx(str) {
	if (str != null && str != '') {
		var array = str.split("-");
		jQuery.each(array, function(i,n) {
			
			if (document.getElementById(n+'_cbv')) {
				
				document.getElementById(n+'_cbv').checked = true;
			} 
			var sjid = n.substr(0,n.indexOf(":"));
			var ejid = sjid.substr(0,sjid.length-2);
			var yjid = sjid.substr(0,sjid.length-4);

			if (document.getElementById(sjid+'_cbv')) {
				document.getElementById(sjid+'_cbv').checked = true;
			}
			if (document.getElementById(ejid+'_cbv')) {
				document.getElementById(ejid+'_cbv').checked = true;
			}
			if (document.getElementById(yjid+'_cbv')) {
				document.getElementById(yjid+'_cbv').checked = true;
			}
			return false;
		});
	}
}
/**
 * 角色信息操作方法
 * @param type 修改或删除二个标记
 * @return
 */
function jsfpyh() {
	
	var id = getChecked();
	if (id.length != 1) {
		alert('请选定一条记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	refRightContent('jsgl_fpyhJs.html?jsdm='+id);
}

/**
 * 功能授权
 * @param type 
 * @return
 */
function gnsq() {
	
	var id = getChecked();
	if (id.length != 1) {
		alert('请选定一条记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	refRightContent('jsgl_gnsqJs.html?jsdm='+id);
	//document.location.href='jsgl_gnsqJs.html?jsdm='+id;
}

/**
 * 保存功能权限操作
 * @return
 */
function saveGnqx() {
	if (curr_row == null) {
		alert("请选择要操作的数据，单击一行即可！");
		return false;
	}
	var array = jQuery("input[type='checkbox']");
	var result = false;
	jQuery.each(array,function(i,n) {
		if (jQuery(n)) {
			result = jQuery(n).attr("checked");
			if(result) {
				return false;
			}
		}
	});
	if (!result) {
		alert("请选择要赋予的功能模块权限！");
		return false;
	}
	if (confirm('确认要保存所选择的权限吗？')){
		saveData();
	}
}

//保存角色分配用户信息
function saveJsfpyhxx() {
	var array = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
	var param="";
	if (array != null && array.length > 0) {
		jQuery.each(array,function(i,n) {
			param = param + jQuery("#yfpTabGrid").jqGrid("getCell", n,"zgh")+",";
		});
		param = param.substring(0, param.length-1);
	} 

	//ajaxSubForm("fpyhForm","jsgl_fpyhSaveJs.html?yhCbv="+param);
	ajaxSubFormWithMsg("fpyhForm","jsgl_fpyhSaveJs.html?yhCbv="+param);
}
//显示角色所分配用户信息
function displayjsPyYhxx() {
	var jsyhStr = jQuery('#jsyhStr').val();
	if (jsyhStr != null && jsyhStr != '') {
		var array = jsyhStr.split("!!");
		jQuery.each(array,function (i,n) {
			if (document.getElementById(n+"_cbv")) {
				document.getElementById(n+"_cbv").checked = true;
			}
		});
	}
}

//保存学生数据权限
function saveJsXsSjQx(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	map["xy_id"] = jQuery('#xy_id_xx').val();
	map["zy_id"] = jQuery('#zy_id_xx').val();
	map["nj_id"] = jQuery('#nj_id_xx').val();
	map["bj_id"] = jQuery('#bj_id_xx').val();
	jQuery.ajax({
		url:"jsgl_sjsqSaveXsSj.html",
		type:"post",
		dataType:"json",
		data:map,
		success:function(data){	
		refershGrid('tabGrid');
		}
	});	
}

//保存课程数据权限
function saveJsKcSjQx(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	map["bmdm_id"] = jQuery('#xy_id_kc').val();
	jQuery.ajax({
		url:"jsgl_sjsqSaveKcSj.html",
		type:"post",
		dataType:"json",
		data:map,
		success:function(data){	
		getJsKc();
		}
	});
}

function getJsKc(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	jQuery.ajax({
			url:"jsgl_cxJsKc.html",
			type: "post",
			dataType:"json",
			data:map,
			success : function(data) {
				var htmls = '<thead><tr><th field="f1" width="160">学院名称</th>	</tr></thead>';
				if(data.length>0){
				    htmls = htmls + "<tbody>";
				 for(var i = 0 ;i<data.length; i++){
				    htmls = htmls +"<tr><td>"+data[i]["bmmc"]+"</td></tr>";
				 }
				    htmls = htmls + "</tbody>";
				} 
				 jQuery("#kcdata").children().remove();
				 jQuery("#kcdata").append(htmls);
			}, 
			error : function() { 
				alert('查询失败'); 
			} 
			
		});
}


function czBtn(){
	var btns = document.getElementById('cz_btn').value;
	var all = document.getElementsByTagName('input');
	var checs = new Array(); 
	var count = 0;
	
	for(var i=0; i<all.length; i++){
		if(all[i].type == 'checkbox' && all[i].disabled == false){
			checs[count++] = all[i]
		}
	}
	
	for (var i=0; i<checs.length; i++){
		checs[i].checked = false;
	}
	if(btns!=null&&btns!=""){
		var btn = btns.split(",");
		for(var i=0; i<btn.length; i++){
			var obj = document.getElementById(btn[i]+'_lv4');
			if(obj!=null){
				obj.checked = true;
				}
			parentCsh(btn[i]+'_lv4');
		}
	}
}

function init(){
	var btns = jQuery("#cz_btn").val();
	if(btns!=null && btns!=""){
		var btn = btns.split(",");
	
	for(var i=0; i<btn.length; i++){
		var obj = document.getElementById(btn[i]+'_lv4');
		if(obj!=null){
			obj.checked = true;
			}
		parentCsh(btn[i]+'_lv4');
	}
	}
}

function parentCsh(id){
	
	var ids = id.split('_');

	// 上级代码
	var sjdm = ids[0];
	var lv = ids[2];
	var sjid = '';
	
	if(lv == 'lv1'){
		return;
	}else if(lv == 'lv2'){
		sjid = 'chec_'+sjdm+'_lv1';	
	}else if(lv == 'lv3'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv2';
	}else if(lv == 'lv4'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv3';
	}
	try{
		var sjobj = document.getElementById(sjid);
		sjobj.checked = true;
		parentCsh(sjid);
	}catch(e){}
}

// 全选
function selectAll(){
	 var _checkbox = jQuery("#gnsqForm input:checkbox");
	 if(_checkbox.filter(":not(:checked)").length == 0){
		 _checkbox.attr("checked",false);
	 }else{
		 _checkbox.attr("checked","checked");
	 }	 
}

// 调换类名
function changeClass(obj){
	var as = getElementsByClass('current',document,'li');
	for(var i=0;i<as.length;i++){
		as[i].className = "";
	}
	
	obj.parentNode.className = "current";
}

// 更换模块
function changeMk(obj){
	var gnmkId = obj.id;
	var trs = getElementsByClass('tr_02',document,'tr');
	
	for(var i=0; i<trs.length; i++){
		var trId = trs[i].id.split("_");
		if(gnmkId == trId[0]){
			trs[i].style.display = "";
		}else{
			trs[i].style.display = "none";
		}
	}	
	changeClass(obj);
}

/**
 * 获取指定class的对象数组
 * @param searchClass 查询用class   
 * @param node 对象
 * @param tag  想要的标签
 * @return class数组
 * @author sjf
 */
function getElementsByClass(searchClass,node,tag) {
    var classElements = new Array();
    if ( node == null )
            node = document;
    if ( tag == null )
            tag = '*';
    var els = node.getElementsByTagName(tag);
    var elsLen = els.length;
    var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
    for (i = 0, j = 0; i < elsLen; i++) {
            if ( pattern.test(els[i].className) ) {
                    classElements[j] = els[i];
                    j++;
            }
    }
    return classElements;
}

// 点击checkbox
function checkboxClick(obj){
	childrenChoice(obj);
	parentsChoice(obj);
}


function childrenChoice(obj){
	var id = obj.id;
	var ids = id.split('_');
	
	if(ids[2] == 'lv4'){
		return;
	}
	
	// 自身代码
	var dm = ids[1];
	var chec = getElementsByJb(dm,'input',id);
	var checked = obj.checked;
	
	for(var i=0; i<chec.length; i++){
		if(!chec[i].disabled){
			chec[i].checked = checked;
		}
		if(chec[i].name != 'btns'){
			childrenChoice(chec[i]);
		}
	}
}

function parentsChoice(obj){
	var ids = obj.id.split('_');
	// 上级代码
	var sjdm = ids[0];
	var lv = ids[2];
	var sjid = '';
	
	if(lv == 'lv1'){
		return;
	}else if(lv == 'lv2'){
		sjid = 'chec_'+sjdm+'_lv1';	
	}else if(lv == 'lv3'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv2';
	}else if(lv == 'lv4'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv3';
	}
	
	var checked = obj.checked;
	
	var sjobj = document.getElementById(sjid);
	
	var broArr = getElementsByJb(sjdm,'input', sjid);
	
	for(var i=0; i<broArr.length; i++){
		if(broArr[i].checked){
			checked = true;
			break;
		}
	}
	if(checked==true){
		sjobj.checked = checked;
	}
	
	parentsChoice(sjobj);
}

function getElementsByJb(searchClass,tag,id) {
	 var ids = id.split('_');
	    var classElements = new Array();
	    var els;
	    if(ids[2] == 'lv1'){	// 一级
	    	els =  document.getElementById('tbody_'+ids[1]).getElementsByTagName(tag);
	    }else if(ids[2] == 'lv2'){ // 二级
	    	els = document.getElementById('tbody_'+ids[0]).getElementsByTagName(tag);
	    }else{
	    	//els = document.getElementById(id).parentNode.parentNode.nextSibling.getElementsByTagName(tag);
	    	els=jQuery(document.getElementById(id).parentNode.parentNode).next().find(tag);
	    }
	   // var elsLen = els.length;
	   var elsLen = els.length;
	    var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
	    for (i = 0, j = 0; i < elsLen; i++) {
	    	if ( pattern.test(els[i].className) ) {
	        	classElements[j] = els[i];
	            j++;
	    	}
	    }
	    return classElements;
}

