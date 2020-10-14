function searchResult() {
	var map = {};
	map["pname"] = jQuery('#pname').val();
	search('tabGrid', map);
}

function boundSpButton(){
	var btnzj = jQuery("#btn_zj");
	var btnxg = jQuery("#btn_xg");
	var btnsc = jQuery("#btn_sc");
	var btnck = jQuery("#btn_ck");
	
	//add
	if (btnzj != null) {
		btnzj.click(function() {
			showWindow('新增流程配置', 750, 550, _path+'/sp/spSetting_zjSp.html');
            return false;
		});
	}
	
	// update
	if (btnxg != null) {
		btnxg.click(function() {
			var id = getChecked();
			if (id.length != 1) {
				alert('请选定一条记录!');
				return;
			}
			//var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			showWindow('修改流程配置', 750, 550, _path+'/sp/spSetting_zjSp.html?pid=' + id);
			return false;
		});
	}
	
	// 绑定删除事件
	if (btnsc != null) {
		btnsc.click(function() {
			var ids = getChecked();
			var url = _path+'/sp/spSetting_scSp.html';
			if (ids.length == 0) {
				alert('请选择您要删除的记录！');
				return false;
			} 
			ids = ids + "";
			
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
	
	// view
	if (btnck != null) {
		btnck.click(function() {
			var id = getChecked();
			if (id.length != 1) {
				alert('请选定一条记录!');
				return;
			}
			showWindow('查看流程配置', 750, 550, _path+'/sp/spSetting_ckSp.html?pid=' + id);
			return false;
		});
	}
	
}

function resetRy(){
	jQuery("[name='personIds']").val("");
	jQuery("[name='persons']").val("请点击选择...");
}

function addRow(){
	var arow = "<tr name='aaaa'><td align='center'><input type='checkbox' name='ckr' /></td>" +
			"<td><input type='text' name='bzname' size='26' /><font color='red'>*</font></td>" +
			"<td><input type='hidden' name='zhuangtai' value='0' /><input type='hidden' name='personIds' value='' /><input type='text' name='persons' value='请点击选择...' size='65' onclick='showPerson(this);' readonly='true'/><font color='red'>*</font></td>" +
			"</tr>";
	
	jQuery("#tbbbb").append(arow);
}

function delRow(){
	if(jQuery("#tbbbb input:checkbox:checked").length===0){
		alert('请选择您要删除的记录！');
		return false;
	}
	jQuery("#tbbbb input:checkbox:checked").parents("tr[name='aaaa']").remove();
}

function upRow(){
	if(jQuery("#tbbbb input:checkbox:checked").length===0){
		alert('请选择您要上移的记录！');
		return false;
	}
	if(jQuery("#tbbbb input:checkbox:checked").length>1){
		alert('请选择一条需要上移的记录！');
		return false;
	}
	var _cur = jQuery("#tbbbb input:checkbox:checked").parents("tr[name='aaaa']");
	var _pre = _cur.prev();
	var sTemp = _cur.html();
	
	if(_pre.html()=='null'||_pre.html()==null){
		alert('已是顶行，无需上移！');
	}else{
		_cur.html(_pre.html());
		_pre.html(sTemp);
	}
}

function downRow(){
	if(jQuery("#tbbbb input:checkbox:checked").length===0){
		alert('请选择您要下移的记录！');
		return false;
	}
	if(jQuery("#tbbbb input:checkbox:checked").length>1){
		alert('请选择一条需要下移的记录！');
		return false;
	}
	
	var _cur = jQuery("#tbbbb input:checkbox:checked").parents("tr[name='aaaa']");
	var _next = _cur.next();
	var sTemp = _cur.html();
	
	if(_next.html()=='null'||_next.html()==null){
		alert('已是底行，无需下移！');
	}else{
		_cur.html(_next.html());
		_next.html(sTemp);
	}
}

function showPerson(obj){
	var bzlx = jQuery("input:radio[name='bzlx']:checked").val();
	jQuery("[name='zhuangtai']").val("0");
	jQuery(obj).parent().find("[name='zhuangtai']").val("1");
	
	
	var pers = jQuery(obj).parent().find("[name='persons']").val();
	var perIds = jQuery(obj).parent().find("[name='personIds']").val();
	
	
	if(bzlx=='jbry'){
		showWindow('选择经办人员', 700, 480, _path+'/sp/spSetting_toSelectPer.html?perIds='+perIds+'&pers='+encodeURI(encodeURI(pers)));
		return false;
	}else if(bzlx=='jbjs'){
		showWindow('选择经办角色', 700, 480, _path+'/sp/spSetting_toSelectRole.html?perIds='+perIds+'&pers='+encodeURI(encodeURI(pers)));
		return false;
	}

	
}


function toSaveSp(){
	if(jQuery("#tbbbb input:checkbox").length==0){
		alert('请至少填写一个步骤信息！');
		return false;
	}
	var url =_path+'/sp/spSetting_zjBcSp.html';
	var bzlx = jQuery("input:radio[name='bzlx']:checked").val();
	var bzs = jQuery("input[name='bzname']");
	var pers = jQuery("input[name='persons']");
	
	var tmp1 = true;
	var tmp2 = true;
	var tmp3 = true;
	for(var i=0;i<bzs.length;i++){
		if(jQuery(bzs[i]).val()==''){
			tmp1 = false;
		}
	}
	if(!tmp1){
		alert('请填写步骤名称！');
		return false;
	}
	
	for(var i=0;i<pers.length;i++){
		if(jQuery(pers[i]).val()=='请点击选择...'){
			tmp2 = false;
		}
	}
	if(!tmp2){
		alert('请选择经办人员/角色！');
		return false;
	}
	
	
	for(var i=0;i<pers.length;i++){
		if(jQuery(pers[i]).val().split(',').length>10){
			tmp3 = false;
		}
	}
	if(!tmp3){
		alert('选择的经办人员/角色不能超过10个！');
		return false;
	}
	
	if (inputResult() &&checkInputNotNull('pname!!pdesc')){
		ajaxSubFormWithFun("myForm",url,{},function(data){
			alert(data,{},{"clkFun":function(){
				if(data=='保存失败！'){
					return false;
				}else{
					refershParent();
				}
			}});
		});
	}
}