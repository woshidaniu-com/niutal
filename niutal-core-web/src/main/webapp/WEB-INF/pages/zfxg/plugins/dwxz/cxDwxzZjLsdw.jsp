<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/dwxz.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
			var DwxxGridTwo = Class.create(BaseJqGrid,{  
				caption : "单位信息列表",
				pager: "pager", //分页工具栏  
			    url: _path+'/zfxg!plugins/dwxz_cxDwxzZjLsdw.html',
			    colModel:[
					 {label:'ID',name:'zzjgdm', index:'zzjgdm', key:true, hidden:true},
				     {label:'单位名称 ',name:'dwmc', index:'dwmc'},
				     {label:'组织机构代码',name:'zzjgdm', index: 'zzjgdm'},
			      	 {label:'单位所在区域',name:'shi', index: 'shi'},
			      	 {label:'单位隶属部门',name:'dwlsbmmc', index: 'dwlsbm'}
				],
				postData:{"doType":"query","doSearch":"<s:property value='doSearch'/>"}
				
			});


			var DwxxGridOne = Class.create(BaseJqGrid,{  
				caption : "单位信息列表",
				pager: "pager", //分页工具栏  
			    url: _path+'/zfxg!plugins/dwxz_cxDwxzZjLsdw.html', 
			    multiselect : false, 
			    colModel:[
					 {label:'ID',name:'zzjgdm', index:'zzjgdm', key:true, hidden:true},
				     {label:'单位名称 ',name:'dwmc', index:'dwmc'},
				     {label:'组织机构代码',name:'zzjgdm', index: 'zzjgdm'},
			      	 {label:'单位所在区域',name:'shi', index: 'shi'},
			      	 {label:'单位隶属部门',name:'dwlsbmmc', index: 'dwlsbm'}
				],
				postData:{"doType":"query","doSearch":"<s:property value='doSearch'/>"},
				ondblClickRow: function(rowid,iRow,iCol,e){
					getDwxxOne(rowid);
	   			}
			});
			jQuery(function(){
				//初始化页面默认提示
				initDefaultClue();
				
				var sfdx=jQuery("#sfdx").val();
				if(sfdx == "0"){
					var dwxxGridOne = new DwxxGridOne();
					loadJqGrid("#tabGrid","#pager",dwxxGridOne);
				}else if(sfdx == "1"){
					var dwxxGridTwo = new DwxxGridTwo();
					loadJqGrid("#tabGrid","#pager",dwxxGridTwo);
				}
				
				initShengShi();
				initLsbm();
			});
			
			
			//获取单位单个信息    单选
			function getDwxxOne(id){
				if(id==null || id==""){
					alert("请先选定一条记录!");
					return ;
				}
				var url=_path+'/zfxg!plugins/dwxz_ckDwxzAjax.html'; 
				url+='?zzjgdm='+id;
				jQuery.post(url, "", function(data){
					
					if(data != null){
						getParentDialogWin().setChooseData(data[0],"dwxx");
						iFClose();
					}else{
						alert("查询数据异常!");
					}
				}, "json");
				return false;
			}
			
			//获取单位单个信息    多选
			function getDwxxTwo(){
				var id = getChecked();
				if(id==null || id=="" || id.length != "1"){
					alert("请先选定一条记录!");
					return ;
				}
				var url=_path+'/zfxg!plugins/dwxz_ckDwxzAjax.html'; 
				url+='?zzjgdm='+id;
				jQuery.post(url, "", function(data){
					if(data != null){
						getParentDialogWin().setChooseData(data,"dwxx");
						iFClose();
					}else{
						alert("查询数据异常!");
					}
				}, "json");
			}

			//获取单位列表信息
			function getDwxxList(){
				var ids = getChecked();
				var url = _path+'/zfxg!plugins/dwxz_cxDwxzAjax.html';
				if (ids.length == 0){
					alert('请勾选复选框选择记录！');
				}else{
					jQuery.post(url,{ids:ids.toString()},function(data){
						if(data != null){
							getParentDialogWin().setChooseData(data,"dwxx");
							iFClose();
						}else{
							alert("查询数据异常!");
						}
					},'json');
				}
			}

			//获取学生信息
			function xzDwxx(){
				var sfdx = jQuery("#sfdx").val();
				if(sfdx == 1){
					getDwxxList();//多数据选择
				}else{
					getDwxxTwo();//单数据选择
				}
			}
			//查询
			function searchResult() {
				var map = {};
				var dwmc=jQuery('#dwmc').val();
				if(dwmc == "请输入单位名称关键字"){
					dwmc="";
				}
				map["dwmc"] = dwmc;
				map["zzjgdm"] = jQuery('#zzjgdm').val();
				map["sheng"] = jQuery('#sheng').val();
				map["shi"] = jQuery('#shi').val();
				map["dwlsbm"] = jQuery('#dwlsbm').val();
				map["doSearch"] = "search";
				search('tabGrid',map);
			}

			//增加临时单位信息
			function zjLsdwxx(){
				var url=_path+"/zfjy!jygz/lsdwxx_zjLsdwxx.html";
				url=url+"?sjcjr="+jQuery("#xh").val();
				window.location.href=url;
			}

			//input默认值 隐藏
			function inputDefaultHide(obj,msg){
				var jqObj=jQuery(obj);
				if(jqObj.val() == msg){
					jqObj.val("");
					jqObj.css("color","#000000");
				}
			}
			//input默认值显示
			function inputDefaultShow(obj,msg){
				var jqObj=jQuery(obj);
				if(jqObj.val() == ""){
					jqObj.val(msg);
					jqObj.css("color","#CCCCCC");
				}
			}

			//初始化页面提示
			function initDefaultClue(){
				var jqDwmc=jQuery("#dwmc");
				var dwmcMess="请输入单位名称关键字";
				if(jqDwmc.val()=="" || jqDwmc.val()==dwmcMess ){
					jqDwmc.val(dwmcMess);
					jqDwmc.css("color","#CCC");
				}
			}

			//重置查询条件
			function searchReset() {
				var input = document.getElementsByTagName('input');
				var select = document.getElementsByTagName('select');
							
				for (var i = 0;i<input.length;i++) {
					if (input[i].disabled != true 
						&& input[i].type != 'checkbox')
						input[i].value="";
				}
				for (var i = 0;i<select.length;i++) {
					if (select[i].disabled != true && select[i].options.length > 0 && select[i].options(0).value == "")
					select[i].value="";
				}
			}
		</script>
	</head>


	<body>
		<input type="hidden" name="sfdx" id="sfdx" value="${sfdx }"/>
		<input type="hidden" name="xh" id="xh" value="${xh }"/>
		<s:if test="sfdx == 0">
		<!--提示-->
		<div class="prompt">
			<h3>
				<span>系统提示：</span>
			</h3>
			<p>
				1、双击列表中一条记录，可以选择该单位信息<br/>
				2、如果找不到您的签约单位，请点击'<a href="javascript:void(0);" style="color: blue;text-decoration:underline;" class="name" onclick="zjLsdwxx();return false;"  class="btn_zj" >增加签约单位</a>'。
			</p>
			<a class="close" title="隐藏" onClick="hidParent(this);"></a>
		</div>
		<script type="text/javascript"> 
			function hidParent(obj){
				obj.parentNode.style.display="none";
			}
		</script>
		<!--提示-->
		</s:if>
		<s:elseif  test="sfdx == 1">
			<div class="prompt">
			<h3>
				<span>系统提示：</span>
			</h3>
			<p>
				注:请点击'查询'按钮查询数据，建议带查询条件
			</p>
			<a class="close" title="隐藏" onClick="hidParent(this);"></a>
		</div>
		<script type="text/javascript"> 
			function hidParent(obj){
				obj.parentNode.style.display="none";
			}
		</script>
		<!--提示-->
		</s:elseif>
		<div class="toolbox">
			<%--<div class="buttonbox">
				<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" onclick="zjLsdwxx();return false;"  class="btn_zj" >
							增加签约单位</a>
					</li>
				</ul>
			</div>
			--%><div class="searchtab">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>单位名称</th>
							<td>
								<input type="text" name="dwmc" id="dwmc" onblur="inputDefaultShow(this,'请输入单位名称关键字');"
	        	onfocus="inputDefaultHide(this,'请输入单位名称关键字');"/>
							</td>
							<th>组织机构代码</th>
							<td>
								<input type="text" name="zzjgdm" id="zzjgdm"/>
							</td>
							<th>单位隶属部门</th>
							<td>
								<s:hidden name="dwlsbm" id="dwlsbm"></s:hidden>
	      						<s:textfield maxlength="50" name="lsbm" id="lsbm" theme="simple"></s:textfield>
							</td>
						</tr>
						<tr>
							<th>单位所在省</th>
							<td>
								<input type="text" name="sheng" id="sheng"/>
							</td>
							<th>单位所在市</th>
							<td>
								<input type="text" name="shi" id="shi"/>
							</td>
							<th></th>
							<td>
								<div class="btn">
									<button type="button" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
		<s:if test="sfdx == 1">
		<div class="tab">
			<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
		    <tfoot>
		      <tr>
		        <td colspan="4">
		          <div class="btn">
		            <button name="btn_tj" onclick="xzDwxx();return false;" type="button">确 定</button>
		            <button name="btn_tj" onclick="iFClose();" type="button">关 闭</button>
		           </div>
		         </td>
		      </tr>
		    </tfoot>
		    </table>
		</div>
		</s:if>
	</body>
</html>
