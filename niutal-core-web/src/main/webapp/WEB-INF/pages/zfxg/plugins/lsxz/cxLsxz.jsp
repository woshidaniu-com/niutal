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
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
			//列表数据  单选
			var LsxxGridOne = Class.create(BaseJqGrid,{  
						caption : "老师信息列表",
						pager: "pager", //分页工具栏  
					    url: _path+'/zfxg!plugins/lsxz_cxLsxz.html?doType=query',  
					    colModel:[
							 {label:'ID',name:'zgh', index:'zgh', key:true, hidden:true},
						     {label:'职工号',name:'zgh', index:'zgh'},
						     {label:'姓名',name:'xm', index:'xm'},
						     {label:'联系电话',name:'lxdh', index: 'lxdh'},
						     {label:'电子邮箱',name:'dzyx', index: 'dzyx'}
						],
						ondblClickRow: function(rowid,iRow,iCol,e){
							getLsxx(rowid);
				   		} 
			});


			//列表数据  多选
			var LsxxGridTwo = Class.create(BaseJqGrid,{  
						caption : "老师信息列表",
						pager: "pager", //分页工具栏  
					    url: _path+'/zfxg!plugins/lsxz_cxLsxz.html?doType=query',  
					    colModel:[
							 {label:'ID',name:'zgh', index:'zgh', key:true, hidden:true},
						     {label:'职工号',name:'zgh', index:'zgh'},
						     {label:'姓名',name:'xm', index:'xm'},
						     {label:'联系电话',name:'lxdh', index: 'lxdh'},
						     {label:'电子邮箱',name:'dzyx', index: 'dzyx'}
						],
						ondblClickRow: function(rowid,iRow,iCol,e){
							//getLsxx(rowid);
				   		} 
			});

			//查询
			function searchResult(){
				var map = {};
				      
				map["zgh"] = jQuery('#zgh').val();
				map["xm"] = jQuery('#xm').val();
				map["doSearch"] = "search";
				search('tabGrid',map);
			}

			//获取老师单个信息   单选
			function getLsxx(id){
				if(id==null || id==""){
					alert("请先选定一条记录!");
					return ;
				}
				var url=_path+'/zfxg!plugins/lsxz_ckLsxzAjax.html'; 
				url+='?zgh='+id;
				jQuery.post(url, "", function(data){
					if(data != null){
						getParentDialogWin().setChooseData(data,"lsxx");
						iFClose();
					}else{
						alert("查询数据异常!");
					}
				}, "json");
				return false;
			}

			//获取老师单个信息    单选
			function getLsxxOne(){
				var id=getChecked();
				if(id.length!=1){
					alert("请先选定一条记录!");
					return ;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				getLsxx(row.zgh);
				return false;
			}
			//获取老师列表信息
			function getLsxxList(){
				var ids = getChecked();
				var url = _path+'/zfxg!plugins/lsxz_cxLsxzAjax.html';
				if (ids.length == 0){
					alert('请勾选复选框选择记录！');
				}else{
					jQuery.post(url,{ids:ids.toString()},function(data){
						if(data != null){
							getParentDialogWin().setChooseData(data,"lsxx");
							iFClose();
						}else{
							alert("查询数据异常!");
						}
					},'json');
				}
			}

			//获取学生信息
			function xzLsxx(){
				var sfdx = jQuery("#sfdx").val();
				if(sfdx == "1"){
					getLsxxList();//多数据选择
				}else{
					getLsxxOne();//单数据选择
				}
			}

			jQuery(function(){
				setTimeout(function(){
					var sfdx=jQuery("#sfdx").val();
					if(sfdx == "0"){
						var lsxxGridOne = new LsxxGridOne();
						loadJqGrid("#tabGrid","#pager",lsxxGridOne);
					}else if(sfdx == "1"){
						var lsxxGridTwo = new LsxxGridTwo();
						loadJqGrid("#tabGrid","#pager",lsxxGridTwo);
					}
				},90);
				setTimeout(function(){
					searchResult();
				},500);
			})
		</script>
	</head>


	<body>
		<div class="tab_cur">
			<p class="location">
				<em></em><a>老师信息列表</a>
				<input type="hidden" name="sfdx" id="sfdx" value="${sfdx }"/>
			</p>
		</div>
		<s:if test="sfdx == 0">
		<!--提示-->
		<div class="prompt">
			<h3>
				<span>系统提示：</span>
			</h3>
			<p>
				1、双击列表中一条记录，可以选择该单位信息<br/>
				2、请点击'查询'按钮查询数据，建议带查询条件
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
			<s:form  action="/zfxg!plugins/lsxz_cxLsxz.html?doType=query" theme="simple">
			<div class="searchtab">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>职工号：</th>
							<td>
								<input type="text" name="zgh" id="zgh" style="width:145px;" />
							</td>
							<th>姓名：</th>
							<td>
								<input type="text" name="xm" id="xm" style="width:145px;" />
							</td>
							<th></th>
							<td>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
							</td>
							<th></th>
							<td>
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
			</s:form>
		</div>

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
		<div class="tab">
			<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
		    <tfoot>
		      <tr>
		        <td colspan="4">
		          <div class="btn">
		            <button name="btn_tj" onclick="xzLsxx();return false;" type="button">确 定</button>
		            <button name="btn_tj" onclick="iFClose();" type="button">关 闭</button>
		           </div>
		         </td>
		      </tr>
		    </tfoot>
		    </table>
		</div>
	</body>
</html>
