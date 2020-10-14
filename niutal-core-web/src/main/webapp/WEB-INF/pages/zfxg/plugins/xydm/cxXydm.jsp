<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/drdcsj/import.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/dc/export.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/xydm.js?_rv_=<%=resourceVersion%>"></script>
		
		<script type="text/javascript">
			jQuery(function(){
				var xydmGrid = new XydmGrid();
				loadJqGrid("#tabGrid","#pager",xydmGrid);
				bdan();
			});
			
			function clearBindedEvents(){
				jQuery("#btn_zj").unbind();
				jQuery("#btn_sc").unbind();
				jQuery("#btn_xg").unbind();
				jQuery("#btn_dc").unbind();
				jQuery("#btn_dr").unbind();
			}
		</script>
	</head>

	<body>
<!-- 		<div class="tab_cur"> -->
<!-- 			<p class="location"> -->
<%-- 				<em>您的当前位置:</em>${currentMenu} --%>
<!-- 			</p> -->
<!-- 		</div> -->
		<div id="mainBody">
		  <div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->
	      </div> 

		<div id="divBody">
			<div class="searchtab">
				<s:form  method="post" action="/zsxt/xtgl/xydm/cxXydm.html" theme="simple" id="form1">
					<table width="100%" border="0" id="searchTab">
						<tbody>
							<tr>
								<th>学院代码</th>
								<td>
									<input type="text" name="bmdm_id" id="bmdm_id"/>
								</td>
								<th>学院名称</th>
								<td>
									<input type="text" name="bmmc" id="bmmc"/>
								</td>
								<th>部门类型</th>
								<td>
									<s:select list="bmlxList" name="bmlx" listKey="dm" listValue="mc" id="bmlx"></s:select>
								</td>
								<td>
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
				
			<div class="compTab">
				<div class="comp_title" id="comp_title">
			      <ul>
			        <li class="ha"><a href="javascript:void(0);"><span>学院</span></a></li>
			        <li><a href="javascript:void(0);" onclick="clearBindedEvents();jQuery('#divBody').load('<%=request.getContextPath() %>/zfxg/zydm/cxZydm.html');"><span>专业</span></a></li>
					<li><a href="javascript:void(0);" onclick="clearBindedEvents();jQuery('#divBody').load('<%=request.getContextPath() %>/zfxg/bjdm/cxBjdm.html');"><span>班级</span></a></li>
			      </ul>
			    </div>
			</div>	
				
			<div class="formbox">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
		</div>
		</div>
	</body>
</html>
