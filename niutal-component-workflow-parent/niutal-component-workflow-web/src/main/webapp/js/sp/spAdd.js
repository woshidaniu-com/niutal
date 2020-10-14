
var txywGrid = null;
var bzGrid = null;
jQuery(function($) {
	
	txywGrid = $.extend(true,{},BaseJqGrid,{ 
		pager: null, //分页工具栏  
		resizeHandle:"#bzGridResizeHandle",
		datatype:"local",
		data:[],
		width: parseInt($("#bzGridResizeHandle").actual('innerWidth')),
		//width: $("#modifyModal").find("div.bootbox-body").innerWidth(),
		height: "180px",
		shrinkToFit	: false,
		multiselect : false,
		colModel:[				    	
			{label:'',name:'bzlx', index: 'bzlx',hidden:true},
			{label:'',name:'roleId', index: 'roleId',hidden:true},
			{label:'',name:'roleName', index: 'roleName',hidden:true},
			{label:'',name:'userId', index: 'userId',hidden:true},
			{label:'',name:'userName', index: 'userName',hidden:true},
			{label:'操作',name:'', index: '',align:'center',width:'80px',formatter:setCz},						
			{label:'步骤名称',name:'nodeName', index: 'nodeName',align:'center',width:'180px',formatter:setBzName},
			{label:'经办人选',name:'', index: '',align:'center',width:'350px',sortable:false,formatter:setJbrx},
			{label:'步骤标记',name:'node_bj', index: 'node_bj',align:'center',width:'100px',
				formatter:function(cellvalue, options, rowObject){
					$("#nodeBjHtml").find("select option").removeProp("selected").removeAttr("selected");
					$("#nodeBjHtml").find("select option[value='" + cellvalue + "']").prop("selected",true).attr("selected","selected");
					return $("#nodeBjHtml").html();
				}
			},
			{label:'数据范围值条件',name:'', index: '',align:'center',width:'180px',sortable:false,formatter:setSjfwztj},
			{label:'数据范围值条件取值',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setSjfwztjqz},
			{label:'发送邮件',name:'', index: '',align:'center',width:'80px',sortable:false,formatter:setSendMail,hidden:sendMail},
			{label:'发送短信',name:'', index: '',align:'center',width:'80px',sortable:false,formatter:setSendSms,hidden:sendSms},
			{label:'站内信内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMes},
			{label:'邮件内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMail,hidden:sendMail},
			{label:'短信内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentSms,hidden:sendSms},
			{label:'站内信内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMesEnd},
			{label:'邮件内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMailEnd,hidden:sendMail},
			{label:'短信内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentSmsEnd,hidden:sendSms}
		]
	});

	bzGrid = $.extend(true,{},BaseJqGrid,{ 
		pager: null, //分页工具栏  
		resizeHandle:"#bzGridResizeHandle",
		datatype:"local",
		data:[],
		width: parseInt($("#bzGridResizeHandle").actual('innerWidth')),
		//width: $("#bzGridResizeHandle").find("div.modal-dialog").actual("innerWidth"),
		height: "180px",
		shrinkToFit	: false,
		multiselect: false,
		colModel:[				    	
			{label:'',name:'bzlx', index: 'bzlx',hidden:true},
			{label:'',name:'roleId', index: 'roleId',hidden:true},
			{label:'',name:'roleName', index: 'roleName',hidden:true},
			{label:'',name:'userId', index: 'userId',hidden:true},
			{label:'',name:'userName', index: 'userName',hidden:true},
			{label:'操作',name:'', index: '',align:'center',width:'120px',formatter:setCz},						
			{label:'步骤名称',name:'nodeName', index: 'nodeName',align:'center',width:'200px',formatter:setBzName},
			{label:'经办人选',name:'', index: '',align:'center',width:'350px',sortable:false,formatter:setJbrx},
			{label:'数据范围值条件',name:'', index: '',align:'center',width:'180px',sortable:false,formatter:setSjfwztj},
			{label:'数据范围值条件取值',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setSjfwztjqz},
			{label:'发送邮件',name:'', index: '',align:'center',width:'80px',sortable:false,formatter:setSendMail,hidden:sendMail},
			{label:'发送短信',name:'', index: '',align:'center',width:'80px',sortable:false,formatter:setSendSms,hidden:sendSms},
			{label:'站内信内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMes},
			{label:'邮件内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMail,hidden:sendMail},
			{label:'短信内容',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentSms,hidden:sendSms},
			{label:'站内信内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMesEnd},
			{label:'邮件内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentMailEnd,hidden:sendMail},
			{label:'短信内容(结束)',name:'', index: '',align:'center',width:'280px',sortable:false,formatter:setContentSmsEnd,hidden:sendSms}
		]
	});
	
	$("#bzGrid").loadJqGrid(bzGrid);
	
	if($("#businessType").val()!=""){
		//修改 加载grid
		if(txyw.indexOf(","+$("#businessType").val()+",")!=-1){
			$("#bzGrid").reloadJqGrid(txywGrid);
		}					
	}							
	
	$(document).off("change","select[name='bzlx']").on("change","select[name='bzlx']",function(){
		var par = $(this).parent();
		par.find("[name='persons']").val("请点击选择...");
		par.find("[name='personIds']").val("");
	});
	
	$("#ajaxForm").validateForm();
	
	//*********消息设置
	$("#bzGrid").off("click","a[name='setMessage']").on("click","a[name='setMessage']",function(){
		$.showDialog(_path + "/xsbysjgl/xsxt_cxKtxxView.html?sbxx_id=" + $(this).attr("data"),"消息设置",{
			width:"1000px",
			modalName:"setMessageModal",
			buttons:{
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		}
		);
	});
	
	
	
	
	
	
	
	
	
});

/*操作*/
function setCz(cellvalue, options, rowObject){
	var res = "";
	res += '<a href="#" onclick="delRow(this);"><img src="' + _path + '/images/delete-icons-2.gif" alt="删除" /></a>&nbsp;';
	res += '<a href="#" onclick="upRow(this);"><img src="' + _path + '/images/up-icons-3.gif" alt="上移" /></a>&nbsp;';
	res += '<a href="#" onclick="downRow(this);"><img src="' + _path + '/images/down-icons-4.gif" alt="下移" /></a>';
	return res;
}

/*步骤名称*/
function setBzName(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["nodeName"] != undefined){
		nodeName = rowObject["nodeName"];
	}
	var res = '<font color="red">*</font><input type="text" name="bzname" size="20" value="' + nodeName + '"/>';
	return res;
}

/*经办人员或经办角色*/
function setJbrx(cellvalue, options, rowObject){
	var bzlx = "jbry";
	if(rowObject["bzlx"] != undefined){
		bzlx = rowObject["bzlx"];
	}
	var roleId = "";
	if(rowObject["roleId"] != undefined){
		roleId = rowObject["roleId"];
	}
	var roleName = "请点击选择...";
	if(rowObject["roleName"] != undefined){
		roleName = rowObject["roleName"];
	}
	var userId = "";
	if(rowObject["userId"] != undefined){
		userId = rowObject["userId"];
	}
	var userName = "请点击选择...";
	if(rowObject["userName"] != undefined){
		userName = rowObject["userName"];
	}
	
	var res = '<font color="red">*</font>';
	if(bzlx=='jbry'){
		res += '<select name="bzlx"><option value="jbry">经办人员</option><option value="jbjs">经办角色</option></select>：';
		res += '<input type="hidden" name="personIds" value="' + userId + '" />';
		res += '<input type="text" name="persons" value="' + userName + '" style="width:160px" onclick="showPerson(this);" readonly="true"/>';
	}else{
		res += '<select name="bzlx"><option value="jbry">经办人员</option><option value="jbjs" selected>经办角色</option></select>：';
		res += '<input type="hidden" name="personIds" value="' + roleId + '" />';
		res += '<input type="text" name="persons" value="' + roleName + '" style="width:160px" onclick="showPerson(this);" readonly="true"/>';
	}						
	return res;
}

//添加一行
function addRow(){
	if($("#businessType").val()==""){
		$.alert("请先选择业务类型，再添加流程步骤！");
		return false;
	}
	$("#bzGrid").addRowData("", {});
}

//删除一行
function delRow(ele){
	$(ele).parent().parent().remove();
}

//上移
function upRow(ele){
	var curTr = $(ele).parent().parent();//当前行
	var preTr = curTr.prev();//前一行
	if(preTr.html()=='null'||preTr.html()==null||preTr.html()==""){
		$.alert('已是顶行，无需上移！');
	}else{
		preTr.before(curTr);					
	}
}

//下移
function downRow(ele){
	var curTr = $(ele).parent().parent();//当前行
	var nextTr = curTr.next();//后一行
	if(nextTr.html()=='null'||nextTr.html()==null){
		$.alert('已是底行，无需下移！');
	}else{
		nextTr.after(curTr);
	}
}

//选择经办人员 选择经办角色
function showPerson(obj){
	var bzlx = jQuery(obj).parent().find("[name='bzlx']").val();	
	var pers = jQuery(obj).parent().find("[name='persons']").val();
	var perIds = jQuery(obj).parent().find("[name='personIds']").val();
	
	//已有的值组成数组
	var arrat = [];
	if(perIds.length > 0 && pers.length > 0){
		var keys = perIds.split(",");
		var texts = pers.split(",");
		$.each(keys,function(i,key){
	 		arrat.push({"key":key,"text":texts[i]});
	 	});
	}
	
	if(bzlx=='jbry'){
		//查询用户
		var settings = {
			"title":"查询用户",
			"multiselect":true,
			"checked":arrat
		};		
		jQuery.showSelectDialog("14",settings,function(data){
			var ids  = jQuery("#selectTabGrid").jqGrid('getGridParam', 'selarrrow');
			if(ids.length>1){
				$.alert("只能选择一条记录");
				return false;
			}
			if(data.length >1){
				$.alert("只能选择一条记录");
				return false;
			}
			var yhid = [];
			var xm = [];
			for(var i=0;i<data.length;i++){			
				yhid.push(data[i].key);
				xm.push(data[i].text);
			}
			$(obj).parent().find("[name='persons']").val(xm.join(","));
			$(obj).parent().find("[name='personIds']").val(yhid.join(","));
	  	});
	}else if(bzlx=='jbjs'){		
		//查询角色
		var settings = {
			"title":"查询角色",
			"multiselect":true,
			"checked":arrat
		};		
		jQuery.showSelectDialog("10",settings,function(data){
			var ids  = jQuery("#selectTabGrid").jqGrid('getGridParam', 'selarrrow');
			if(ids.length>1){
				$.alert("只能选择一条记录");
				return false;
			}
			if(data.length >1){
				$.alert("只能选择一条记录");
				return false;
			}
			var jsdm = [];
			var jsmc = [];
			for(var i=0;i<data.length;i++){			
				jsdm.push(data[i].key);
				jsmc.push(data[i].text);
			}
			$(obj).parent().find("[name='persons']").val(jsmc.join(","));
			$(obj).parent().find("[name='personIds']").val(jsdm.join(","));
	  	});	
	}	
}

//保存
function toSaveSp(funcCallback){
//	if($("#businessType").val()==""){
//		$.alert('请选择业务类型！');
//		return false;
//	}
	if(jQuery("input[name=bzname]").length===0){
		$.alert('请至少填写一个步骤信息！');
		return false;
	}
	var url =_path+'/sp/spSetting_zjBcSp.html';
	var bzlxs = jQuery("select[name='bzlx']");
	var bzs = jQuery("input[name='bzname']");
	var pers = jQuery("input[name='persons']");
	
	var tmp1 = true;
	var tmp2 = true;
	var tmp3 = true;
	//校验步骤名称
	for(var i=0;i<bzs.length;i++){
		if(jQuery(bzs[i]).val()==''){
			$.alert('请填写步骤名称！');
			tmp1 = false;
			break;
		}
		for(var j=i+1;j<bzs.length;j++){
			if($(bzs[i]).val() == $(bzs[j]).val()){
				$.alert('步骤名称不能有重复！');
				tmp1 = false;
				break;
			}
		}
		if(!tmp1){
			break;
		}
	}
	if(!tmp1){
		return false;
	}
	
	//校验经办人选
	for(var i=0;i<pers.length;i++){
		if(jQuery(pers[i]).val()=='请点击选择...'){
			$.alert('请选择经办人选！');
			tmp2 = false;
			break;
		}
		if($(bzlxs[i]).val()=="jbry"){
			if(i<pers.length-1 && $(bzlxs[i]).val() == $(bzlxs[i+1]).val() && $(pers[i]).val() == $(pers[i+1]).val()){
				$.alert("相邻步骤的经办人员不能相同！");
				tmp2 = false;
				break;
			}
		}		
	}
	if(!tmp2){		
		return false;
	}
	
	
	for(var i=0;i<pers.length;i++){
		if(jQuery(pers[i]).val().split(',').length>3){
			tmp3 = false;
		}
	}
	if(!tmp3){
		$.alert('选择的经办人员/角色不能超过100个！');
		return false;
	}
	
	submitForm("ajaxForm",function(data){
		funcCallback.call(this,data);
	});	
}

//选择业务类型
function chooseBusiness(){	
	$.showDialog(_path + '/sp/spSetting_cxSelectBusiness.html','选择业务类型',$.extend(true,{},addConfig,{
		width:"700px",
		data:{"cxAll":"all"},
		buttons:{
			success : {
				callback : function() {
					var data = getResult();
					if(data){
						jQuery("#businessType").val(data["YWDM"]);
						jQuery("#businessName").val(data["YWMC"]);
						
						//查询环节标记
						$("#node_bj").find("option:gt(0)").remove();
						jQuery.ajaxSetup({async:false});
						var bjUrl = _path + '/sp/spSetting_cxNodeBjList.html';
						$.post(bjUrl, {"businessType":data["YWDM"]} ,function(json){
							if($.founded(json)){
								$.each(json,function(index,item){					
									$("#node_bj").append('<option value="'+ item.BJ + '">' + item.MC + '</option>');
								});
							}
						},"json");
						jQuery.ajaxSetup({async:true});
						//重加载 grid
						if(txyw.indexOf(data["YWDM"])!=-1){
							$("#bzGrid").reloadJqGrid(txywGrid);
						}else{
							$("#bzGrid").reloadJqGrid(bzGrid);
						}
					}else{
						return false;//不正确 不关窗口
					}
				}
			}
		}
	}));
}






/*消息设置*/
function setMessage(cellvalue, options, rowObject){
	return "<a href='#' class='setMessage'>消息设置 </a>";
}


/*数据范围值条件*/
function setSjfwztj(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["sjfwztj"] != undefined){
		nodeName = rowObject["sjfwztj"];
	}
	var res = '<input type="text" name="sjfwztj" size="40" value="' + nodeName + '"/>';
	return res;
}

/*数据范围值条件取值*/
function setSjfwztjqz(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["sjfwztj_qz"] != undefined){
		nodeName = rowObject["sjfwztj_qz"];
	}
	var res = '<input type="text" name="sjfwztj_qz" size="40" value="' + nodeName + '"/>';
	return res;
}

/*发送邮件*/
function setSendMail(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["send_mail"] != undefined){
		nodeName = rowObject["send_mail"];
	}
	var res = '<select name="send_mail"><option value="0">否</option><option value="1">是</option></select>';
	if(nodeName=="1"){
		res = '<select name="send_mail"><option value="1">是</option><option value="0">否</option></select>：';
	}
	return res;
}

/*发送短信*/
function setSendSms(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["send_sms"] != undefined){
		nodeName = rowObject["send_sms"];
	}
	var res = '<select name="send_sms"><option value="0">否</option><option value="1">是</option></select>';
	if(nodeName=="1"){
		res = '<select name="send_sms"><option value="1">是</option><option value="0">否</option></select>：';
	}
	return res;
}

/*站内信内容*/
function setContentMes(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_mes"] != undefined){
		nodeName = rowObject["content_mes"];
	}
	var res = '<input type="text" name="content_mes" size="38" value="' + nodeName + '"/>';
	return res;
}

/*邮件内容*/
function setContentMail(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_mail"] != undefined){
		nodeName = rowObject["content_mail"];
	}
	var res = '<input type="text" name="content_mail" size="40" value="' + nodeName + '"/>';
	return res;
}

/*短信内容*/
function setContentSms(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_sms"] != undefined){
		nodeName = rowObject["content_sms"];
	}
	var res = '<input type="text" name="content_sms" size="40" value="' + nodeName + '"/>';
	return res;
}

/*站内信内容(结束)*/
function setContentMesEnd(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_mes_end"] != undefined){
		nodeName = rowObject["content_mes_end"];
	}
	var res = '<input type="text" name="content_mes_end" size="40" value="' + nodeName + '"/>';
	return res;
}

/*邮件内容(结束)*/
function setContentMailEnd(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_mail_end"] != undefined){
		nodeName = rowObject["content_mail_end"];
	}
	var res = '<input type="text" name="content_mail_end" size="40" value="' + nodeName + '"/>';
	return res;
}

/*短信内容(结束)*/
function setContentSmsEnd(cellvalue, options, rowObject){
	var nodeName = "";
	if(rowObject["content_sms_end"] != undefined){
		nodeName = rowObject["content_sms_end"];
	}
	var res = '<input type="text" name="content_sms_end" size="40" value="' + nodeName + '"/>';
	return res;
}
