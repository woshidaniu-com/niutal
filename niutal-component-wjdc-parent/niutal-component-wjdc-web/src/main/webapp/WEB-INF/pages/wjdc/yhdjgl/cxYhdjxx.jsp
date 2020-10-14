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
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wjgl.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
			var WjxxGrid = Class.create(BaseJqGrid,{  
				caption : "问卷信息列表",
				pager: "pager", //分页工具栏  
			    url: _path+'/zfxg/wjdc/yhdjgl_cxYhdjxx.html?doType=query',  
			    colModel:[
			         {label:'ID',name:'wjid', index:'wjid', key:true, hidden:true},
				     {label:'问卷名称 ',name:'wjmc', index:'wjmc'},
				//     {label:'问卷类型',name:'wjlxmc', index: 'wjlxmc'},
			      	 {label:'问卷状态',name:'wjzt', index: 'wjzt'}
				//     {label:'过期时间',name:'gqsj', index: 'gqsj'}
				],
				sortname: 'cjsj', //首次加载要进行排序的字段 
			 	sortorder: "desc"
			});
			
			//用户答卷
			function yhdj(wjid){
				var url= _path+"/zfxg/wjdc/stgl_yhdj.html?wjModel.wjid="+wjid;
				window.open(url);
			}
			
			function searchResult(){
				var url= _path+"/zfxg/wjdc/yhdjgl_cxYhdjxx.html";
				ajaxSubForm("form_cxyhdjxx",url);
			}
			
			jQuery(function(){
				//var wjxxGrid = new WjxxGrid();
				//loadJqGrid("#tabGrid","#pager",wjxxGrid);
				//bdan();
				//initWjlx();
			});
			
		</script>
	</head>


	<body>
		<s:form namespace="/zfxg/wjdc" action="yhdjgl_cxYhdjxx" id="form_cxyhdjxx" method="post" theme="simple">
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 我的问卷</a>
			</p>	
		</div>
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab" style="display: none;">
				<table width="100%" border="0" id="searchTab">
					<tbody>
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
										onclick="searchResult();">
										查 询
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="formbox">
			<table width="100%" class="dateline">
              <thead>
				<tr>
					<td style="display: none;">
						<input type="checkbox" name="ck" id="ck" onclick="selectPk(this,'pkValue')"/>
					</td>
					<td>
						问卷名称
					</td>
					<!-- 
					<td>
						问卷类型
					</td>
					 -->
					<td>
						问卷状态
					</td>
					<td>
						过期时间
					</td>
					<td>
						答卷状态
					</td>
					<td>
						操作
					</td>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="wjList" id="wj">
					<tr>
						<td style="display: none;">
							<input type="checkbox" name="pkValue" id="ck"/>
						</td>
						<td>
							${wj.wjmc}
							<s:if test="djzt=='未答卷' && wjzt=='运行'">
								<font color="red">【NEW】</font>
							</s:if>
						</td>
						<!-- 
						<td>
							${wj.wjlxmc}
						</td>
						 -->
						<td>
							${wj.wjzt}
						</td>
						<td>
							${wj.gqsj}
						</td>
						<td>
							${wj.djzt}
						</td>
						<td>
							<s:if test="djzt=='未答卷' && wjzt=='运行'">
								<button type="button" onclick="yhdj('${wj.wjid}')">答卷</button>
							</s:if>
							<s:else>
								<button type="button" onclick="yhdj('${wj.wjid}')">查看</button>
							</s:else>
						</td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
			<jsp:include page="/WEB-INF/pages/comm/pageFootMenu.jsp"></jsp:include>
		</div>
		</s:form>
	</body>
</html>
