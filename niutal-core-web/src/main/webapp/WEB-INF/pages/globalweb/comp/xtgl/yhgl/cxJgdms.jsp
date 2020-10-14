<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/yhgl.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var jgdmGrid = new JgdmGrid();
				setTimeout(function(){
					loadJqGrid("#tabGridJgdms","#pager",jgdmGrid);
				},100);
			})
			
			function ok(){
				var id = jQuery("#tabGridJgdms").jqGrid('getGridParam', 'selarrrow');
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGridJgdms").jqGrid('getRowData', id);
				var api = frameElement.api; 
				var parm = api.get("parentDialog",1).content;
				parm.jQuery("#jgdm").val(row.bmdm_id);
				parm.jQuery("#jgmc").val(row.bmmc);
				parm.jQuery("#cydm_id_bmlb").val(row.cydm_id_bmlb);
			    
			    iFClose();//关闭子窗体
			}
			function fClose(){
				iFClose();//关闭子窗体
			}
		</script>
	</head>

	<body>
			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>部门名称</th>
							<td>
								<input type="text" name="bmmc" id="bmmc"  onkeydown="if(pressEnter(event)){return false};"/>
							</td>
							<td>
								<div class="btn">
									<button class="btn_cx" id="search_go"
										onclick="searchResultJgdms();return false;">
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
			<table id="tabGridJgdms"></table>
			<div id="pager"></div>
		</div>
		
  		<div style="text-align: right;padding-top: 10px;">
	         <button onclick="ok();return false;" name="btn_tj" >确定</button>
	         <button onclick="fClose();return false;" name="btn_gb" >关 闭</button>
	    </div>
	       
	</body>
</html>
