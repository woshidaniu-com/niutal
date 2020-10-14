<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini" %>
    <%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
    <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
  <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xsxx/jcsj.js?_rv_=<%=resourceVersion%>"></script>
  <script type="text/javascript">
 		
		jQuery(function(){
			var zydmGrid = new ZydmGrid();
			loadJqGrid("#tabGrid","#pager",zydmGrid);
		});
		jQuery(function(){
			ancdBd_zydm();
		});
  		
  </script>
  </head>
  <body>
			<div class="tab_cur">
				<p class="location">
					<em>您的当前位置:</em>${currentMenu}
		      <ul>
		        <li id="ha_bmdm"><a href="javascript:" onclick="contentSwitch('bmdm_id')"><span>部门</span></a></li>
		        <li id="ha_zydm" class="ha"><a href="javascript:" onclick="contentSwitch('zydm')"><span>专业</span></a></li>
				<li id="ha_bjdm"><a href="javascript:" onclick="contentSwitch('bjdm')"><span>班级</span></a></li>
		      </ul>
		    </div>
		    <!-- 部门数据维护 -->
		    <div id="xsxx_bm" class="xsxx_sjwh" style="display: block;" >
				<div class="toolbox">
					<!-- 加载当前菜单栏目下操作     -->
						<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
					<!-- 加载当前菜单栏目下操作 -->
	            </div> 
	            <div class="searchtab">
	            <s:form  action="/xsxx/bmdm_csJcsj.html?doType=query&yqbh=zydm" theme="simple">
	            		<table width="100%" border="0">
							<tbody>
								<tr>
									<th>
										专业代码
									</th>
									<td>
										<s:textfield name="zydm" id="search_zydm" maxlength="15" cssStyle="width:120px"></s:textfield>
									</td>
									<th>
										专业名称
									</th>
									<td>
										<s:textfield name="zymc" id="search_zymc" maxlength="15" cssStyle="width:120px"></s:textfield>
									</td>
									<th>
										部门
									</th>
									<td>
										 <s:select name="bmdm_id" id="search_bmdm_id" list="bmdmModelList" listKey="bmdm_id" listValue="bmmc" cssStyle="width:154px"  headerKey="" headerValue=""></s:select>
									</td>
									 <td>
						                <div class="btn">
						                   <button type="button" id="search_go" onclick="searchResult_zydm();return false;">
												查询
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
		
		
		<script type="text/javascript">
			if('${result}'!=null && '${result}'!=''){
				alert('${result}');
			}
		</script>
 </body>
</html>
