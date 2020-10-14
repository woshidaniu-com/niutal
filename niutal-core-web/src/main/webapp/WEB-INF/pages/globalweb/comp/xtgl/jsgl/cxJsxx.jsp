<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">

		var JsglGrid = Class.create(BaseJqGrid, {
			caption : "角色列表",
			pager : "pager", // 分页工具栏
			height : "auto",
			rowNum : 15, // 每页显示记录数
			rowList : [15, 30, 50, 100], // 可调整每页显示的记录数			
			url : _path + '/xtgl/jsgl_cxJsxx.html?doType=query', // 这是Action的请求地址
			colModel : [ {
				label : '角色代码',
				name : 'jsdm',
				index : 'jsdm',
				key : true,
				hidden : true
			},{
				label : '角色名称',
				name : 'jsmc',
				index : 'jsmc',
				align : 'center'

			},{
				label : '用户数',
				name : 'yhnum',
				index : 'yhnum',
				align : 'center'

			}, {
				label : '系统默认',
				name : 'sfmrjs',
				index : 'sfmrjs',
				align : 'center'

			}, {
				label : '是否可删除',
				name : 'sfksc',
				index : 'sfksc',
				hidden : true
			},{
				label : '角色说明',
				name : 'jssm',
				index : 'jssm'
			}],
			sortname : 'jsdm', // 首次加载要进行排序的字段
			sortorder : "desc"
		});
				
		jQuery(function(){
			var jsglGrid = new JsglGrid();
			loadJqGrid("#tabGrid","#pager",jsglGrid);
			bdan();
		})
		
		//为select option 增加title属性提示
		jQuery(function(){
			addOptionTitle();
		});

		</script>
</head>

<s:form action="/xtgl/jsgl_cxJsxx.html"  theme="simple">
	<body>
		<!-- 功能操作 -->
		<input type="hidden" name="gnczStr" id="gnczStr" value="${gnczStr }"/>
			<div class="toolbox">
				<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
				<!-- 加载当前菜单栏目下操作 -->
	      </div> 
			    
            	<div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								
								<th>
									是否已分配用户
								</th>
								<td>
								<s:select name="sffpyh" list="sfList" listKey="key"	listValue="value" id="sffpyh" headerKey="" headerValue="全部" cssStyle="width:80px">
								</s:select>
								</td>
								<th>
									角色名称
								</th>
								<td>
									<s:textfield id="jsmc" name="jsmc" maxlength="20" cssStyle="width:135px"></s:textfield> 
								</td>
								
								 <td>
					                <div class="btn">
					                   <button type="button" id="search_go" onclick="searchResult();return false;">
											查询
										</button>
										<!--  <button type="button" class="btn_cz" id="btn_cz" onclick="searchReset();return false;">
				              				重 置-->
				             			</button>
					                 </div>
					              </td>
							</tr>
						</tbody>
					</table>
            	</div>
           <div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
			
</body>
</s:form>
<script type="text/javascript">
			if('${result}'!=null && '${result}'!=''){
				alert('${result}');
			}
			if('${message}'!=null && '${message}'!=''){
				alert('${message}');
			}
		</script>
</html>