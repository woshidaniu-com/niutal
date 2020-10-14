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
			    url: _path+'/zfxg/wjdc/wjgl_cxWjxx.html?doType=query',  
			    colModel:[
			         {label:'ID',name:'wjid', index:'wjid', key:true, hidden:true},
				     {label:'问卷名称 ',name:'wjmc', index:'wjmc'},
			//	     {label:'问卷类型',name:'wjlxmc', index: 'wjlxmc',width:50},
			      	 {label:'问卷状态',name:'wjzt', index: 'wjzt',width:50},
			      	 {label:'发布类型',name:'fblxmc', index: 'fblxmc',width:50},
			      	 {name:'fblx', index: 'fblx', hidden:true},
			      	 {label:'创建人',name:'cjrmc', index: 'cjrmc',width:50},
			      	 {label:'创建时间',name:'cjsj', index: 'cjsj',width:50},
				     {label:'过期时间',name:'gqsj', index: 'gqsj',width:50}
				],
				sortname: 'wjmc' //首次加载要进行排序的字段 
			});

			jQuery(function(){
				var wjxxGrid = new WjxxGrid();
				loadJqGrid("#tabGrid","#pager",wjxxGrid);
				bdan();
				//initWjlx();
			});
			
		</script>
	</head>


	<body>
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷维护</a>
			</p>	
		</div>
	<s:form namespace="/zfxg/wjdc" action="wjgl_cxWjxx" method="post" theme="simple">

		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
		          <table width="100%">
		            <tfoot>
					<tr>
                <td colspan="6">
                 </td>
              </tr>
		            </tfoot>
		            <tbody>
		              <tr>
		                <th>问卷名称</th>
							<td>
								<s:textfield name="wjmc" id="wjmc"   cssStyle="width:150px" maxlength="20"></s:textfield>
							</td>
							<th>问卷状态</th>
							<td>
								<s:select list="#{'':'','草稿':'草稿','发布':'发布','运行':'运行','停止':'停止' }" cssStyle="width:150px" name="wjzt" id="wjzt" ></s:select>
							</td>
							<th></th>
							<td></td> 
		              </tr>
		              <tr>
		               <th>创建人</th>
							<td>
								<s:textfield name="cjrmc" id="cjrmc"   cssStyle="width:150px" maxlength="10"></s:textfield>
							</td>
							<th>创建时间</th>
							<td colspan="2">
								<input type="text" name="cjkssj" value="" readonly="readonly" id="cjkssj" style="width:80px" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/> 至 
								<input type="text" name="cjjssj" value="" readonly="readonly" id="cjjssj"  style="width:80px" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td colspan="">
								 <div class="btn">
                   <button type="button" id="search_go"
										onclick="searchResult();" >
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
	</s:form>
	</body>
	
</html>
