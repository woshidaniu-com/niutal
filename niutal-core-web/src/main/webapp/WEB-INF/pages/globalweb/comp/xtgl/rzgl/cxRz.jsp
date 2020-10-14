<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/rzgl.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var logGrid = new LogGrid();
				loadJqGrid("#tabGrid","#pager",logGrid);
				bdan();
			})
		</script>
	</head>


	<body>
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
				<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->
			<div class="searchtab">
				<s:form  method="post" theme="simple" id="form1">
					<table width="100%" border="0" id="searchTab">
						<tbody>
							<tr>
								<th>业务模块</th>
								<td>
									<input type="text" name="czmk" id="czmk"/>
								</td>
								<th>操作人</th>
								<td>
									<input type="text" name="czr" id="czr"/>
								</td>
								<th>
									操作类型
								</th>
								<td>
									<select name="czlx" id="czlx">
										<option value=''></option>
										<option value="insert">
											新增
										</option>
										<option value="update">
											更改
										</option>
										<option value="delete">
											删除
										</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th>操作日期</th>
								<td>
									<input class="Wdate" id="czkssj" type="text" name="czkssj" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/> 至
									<input class="Wdate" id="czjssj" type="text" name="czjssj" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
								</td>
								<td colspan="4">
										<button class="btn_cx" id="search_go"
											onclick="searchResult();return false;">
											查 询
										</button>
										<button type="button" class="btn_cz" id="btn_cz" onclick="searchReset();return false;">
				              				重 置 
				             			</button>
								</td>
							</tr>						
						</tbody>
					</table>
			</s:form>
			</div>
		</div>
		
		
		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
		</body>
</html>
