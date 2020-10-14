<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/bjdm.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var BjdmGrid = Class.create(BaseJqGrid,{  
					caption : "班级列表",
					pager: "pager", //分页工具栏  
				    url: _path+'/zfxg/bjdm/cxBjdmList.html', //这是Action的请求地址  
				    colModel:[
				    	 {label:'bjdm_id',name:'bjdm_id', index: 'bjdm_id',key:true,hidden:true},
					     {label:'班级代码',name:'bjdm', index: 'bjdm',align:'center'},
					     {label:'班级名称',name:'bjmc', index: 'bjmc',align:'center'},
					     {label:'年级',name:'njdm_id', index: 'njdm_id',align:'center'},
					     {label:'学院',name:'bmmc', index: 'bmdm_id',align:'center'},
					     {label:'专业',name:'zymc', index: 'zydm_id',align:'center'},
					     {label:'操作',name:'ROW_ID', index: 'ROW_ID',align:'center',formatter:function(v,o,r){
					    	 
					    	 return "<label class='btn_01' onclick='callBackResult(\""+r["bjdm_id"]+"\");'>选择</label>";
					     }}
					],
					sortname: 'bjdm', //首次加载要进行排序的字段 
				 	sortorder: "asc",
				 	multiselect:false
				});
				
				var bjdmGrid = new BjdmGrid();
				setTimeout(function(){
					loadJqGrid("#tabGrid","#pager",bjdmGrid);
				},50);
				jQuery("#bmdm_id_lsbm").bind("change",function(){
					var xydm = jQuery(this).val();
					jQuery.post(_path+"/zfxg/zydm/cxZydmByXydm.html",{bmdm_id_lsbm:xydm},function(data){
						jQuery("#zydm_id option:not(:first)").remove();
						
						jQuery.each(data,function(i,n){
							jQuery("#zydm_id").append("<option value='"+data[i]["zydm_id"]+"'>"+data[i]["zymc"]+"</option>");
						});
					},'json');
				});
			});
			
			function callBackResult(bjid){
				var row = jQuery("#tabGrid").jqGrid('getRowData', bjid);
				var api = frameElement.api;
				row["bjdm_id"] = bjid;
				api.get('parentDialog').showResult(row);
				closeDialog();
			}
			
		</script>
	</head>

	<body>

		<div id="divBody">
			<div class="searchtab">
				<s:form name="form" method="post" action="/zfxg/xydm/cxXydm.html" theme="simple" id="cxyhForm">
					<table width="100%" border="0" id="searchTab">
						<tbody>
							<tr>
								<th>班级代码</th>
								<td>
									<input type="text" name="bjdm" id="bjdm"/>
								</td>
								<th>班级名称</th>
								<td>
									<input type="text" name="bjmc" id="bjmc"/>
								</td>
								<th>学院</th>
								<td>
									<s:select list="xydmList" id="bmdm_id_lsbm" 
											  name="bmdm_id_lsbm" listKey="bmdm_id" 
									  		  listValue="bmmc" headerKey="" headerValue="---请选择---"
									  		  cssStyle="width:180px;"
								    ></s:select>
								</td>
							</tr>
							<tr>
								<th>年级</th>
								<td>
									<input type="text" name="njdm_id" id="njdm_id"/>
								</td>
								<th>专业</th>
								<td>
									<s:select list="zydmList" id="zydm_id" 
											  name="zydm_id" listKey="zydm_id" 
									  		  listValue="zymc" headerKey="" headerValue="---请选择---"
									  		  cssStyle="width:180px;"
								  	></s:select>
								</td>
								<td colspan="2">
									<div class="btn">
										<button type="button" class="btn_cx" id="search_go"
											onclick="searchResult();return false;">
											查 询
										</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</s:form>
			</div>
				
			<div class="formbox">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
		</div>
	</body>
</html>
