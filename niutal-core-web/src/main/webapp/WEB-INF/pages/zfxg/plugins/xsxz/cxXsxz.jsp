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
			//列表数据
			var XsxxGridTwo = Class.create(BaseJqGrid,{  
						caption : "学生信息列表",
						pager: "pager", //分页工具栏  
					    url: _path+'/zfxg!plugins/xsxz_cxXsxz.html?doType=query',  
					    colModel:[
							 {label:'ID',name:'xh', index:'xh', key:true, hidden:true},
						     {label:'学号',name:'xh', index:'xh'},
						     {label:'姓名',name:'xm', index: 'xm'},
						     {label:'年级',name:'njmc', index: 'njdm'},
 					      	 {label:'学院',name:'xymc', index: 'xydm'},
					      	 {label:'专业',name:'zymc', index: 'zydm'},
					      	{label:'班级',name:'bjmc', index: 'bjdm'}
						],
						ondblClickRow: function(rowid,iRow,iCol,e){
							//单击信息选择
				   		} 
			});


			//列表数据
			var XsxxGridOne = Class.create(BaseJqGrid,{  
						caption : "学生信息列表",
						pager: "pager", //分页工具栏  
					    url: _path+'/zfxg!plugins/xsxz_cxXsxz.html?doType=query', 
					    colModel:[
							 {label:'ID',name:'xh', index:'xh', key:true, hidden:true},
						     {label:'学号',name:'xh', index:'xh'},
						     {label:'姓名',name:'xm', index: 'xm'},
						     {label:'年级',name:'njmc', index: 'njdm'},
 					      	 {label:'学院',name:'xymc', index: 'xydm'},
					      	 {label:'专业',name:'zymc', index: 'zydm'},
					      	{label:'班级',name:'bjmc', index: 'bjdm'}
						],
						ondblClickRow: function(rowid,iRow,iCol,e){
							//单击信息选择
							getXsxx(rowid);
				   		} 
			});
			


			//查询
			function searchResult(){
				var map = {};
				      
				map["xh"] = jQuery('#xh').val();
				map["xm"] = jQuery('#xm').val();
				map["njdm"] = jQuery('#njdm').val();
				map["xydm"] = jQuery('#bmdm').val();
				map["zydm"] = jQuery('#zydmID').val();
				map["bjdm"] = jQuery('#bjdmID').val();
				map["doSearch"] = "search";
				search('tabGrid',map);
			}

			//获取学生单个信息  单选
			function getXsxxOne(){
				var id=getChecked();
				if(id.length!=1){
					alert("请先选定一条记录!");
					return ;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				getXsxx(row.xh);
				return false;
			}
			
			//获取学生单个信息  单选
			function getXsxx(id){
				if(id==null || id==""){
					alert("请先选定一条记录!");
					return ;
				}
				var url=_path+'/zfxg!plugins/xsxz_ckXsxzAjax.html'; 
				url+='?xh='+id;
				jQuery.post(url, "", function(data){
					if(data != null){
						getParentDialogWin().setChooseData(data[0],"xsxx");
						iFClose();
					}else{
						alert("查询数据异常!");
					}
				}, "json");
				return false;
			}

			//获取学生列表信息
			function getXsxxList(){
				var ids = getChecked();
				var url = _path+'/zfxg!plugins/xsxz_cxXsxzAjax.html';
				if (ids.length == 0){
					alert('请勾选复选框选择记录！');
				}else{
					jQuery.post(url,{ids:ids.toString()},function(data){
						if(data != null){
							getParentDialogWin().setChooseData(data,"xsxx");
							iFClose();
						}else{
							alert("查询数据异常!");
						}
					},'json');
				}
			}

			//获取学生信息
			function xzXsxx(){
				var sfdx = jQuery("#sfdx").val();
				if(sfdx == "1"){
					getXsxxList();//多数据选择
				}else{
					getXsxxOne();//单数据选择
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

			jQuery(function(){
				setTimeout(function(){
					var sfdx=jQuery("#sfdx").val();
					if(sfdx == "0"){
						var xsxxGridOne = new XsxxGridOne();
						loadJqGrid("#tabGrid","#pager",xsxxGridOne);
					}else if(sfdx == "1"){
						var xsxxGridTwo = new XsxxGridTwo();
						loadJqGrid("#tabGrid","#pager",xsxxGridTwo);
						
					}
					initXsbm(false);
				},90);
				setTimeout(function(){
					searchResult();
				},650);
			})
		</script>
	</head>


	<body>
		<div class="tab_cur">
			<p class="location">
				<em></em><a>学生信息列表</a>
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
					注：双击列表中一条记录，可以选择该学生信息
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
		<div class="toolbox">
			<s:form  action="/zfxg!plugins/xsxz_cxXsxz.html?doType=query" theme="simple">
			<div class="searchtab">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>学号</th>
							<td>
								<input type="text" name="xh" id="xh" style="width:145px;" />
							</td>
							<th>姓名</th>
							<td>
								<input type="text" name="xm" id="xm" style="width:145px;" />
							</td>
							<th>年级</th>
							<td>
							<input type="text" name="njdm" id="njdm" style="width:145px;"/>
							<!-- <input type="hidden" name="njdm" id="njdm"/> -->
							</td>
						</tr>
						<tr>
							<th>学院</th>
							<td>
								<input type="text" name="bmmc" id="bmmc" style="width:145px;"/>
								<input type="hidden" name="xydm" id="bmdm"/>
							</td>
							<th>专业</th>
							<td>
								<input type="text" name="zymc" id="zymc" style="width:145px;"/>
								<input type="hidden" name="zydm" id="zydmID"/>
							</td>
							<th>班级</th>
							<td>
								<input type="text" name="bjmc" id="bjmc" style="width:145px;"/>
								<input type="hidden" name="bjdm" id="bjdmID"/>
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
		            <button name="btn_tj" onclick="xzXsxx();return false;" type="button">确 定</button>
		            <button name="btn_tj" onclick="iFClose();" type="button">关 闭</button>
		           </div>
		         </td>
		      </tr>
		    </tfoot>
		    </table>
		</div>
	</body>
</html>
