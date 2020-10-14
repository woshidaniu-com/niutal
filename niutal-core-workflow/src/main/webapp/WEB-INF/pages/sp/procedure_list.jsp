<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/sp/spSetting.js"></script>
		<script type="text/javascript">

		var urlJson = "<%=systemPath %>/sp/spSetting_cxSpList.html?doType=query"; //这是Action的请求地址  
		
		var DictionaryGrid = Class.create(BaseJqGrid,{  
			caption : "流程列表",
			pager: "pager1", //分页工具栏  
	        url: urlJson,
	        colNames: ['pid','流程名称','流程描述','步骤个数','启用状态','是否绑定业务'],
			colModel:[
			     {name:'PID',index:'PID', width:'0%',key:true, hidden: true},
			     {name:'PNAME', index: 'PNAME', width: '29%' },
			     {name:'PDESC', index: 'PDESC', width: '39%' },
			     {name:'NODECOUNTS', index: 'NODECOUNTS', width: '10%' ,align:'center'},
			     {name:'PSTATUS', index: 'PSTATUS', width: '10%' ,align:'center' ,formatter:'select',editoptions:{value:"0:停用;1:启用"}},
			     {name:'BUSINESSTYPE', index: 'BUSINESSTYPE', width: '12%' ,align:'center' ,formatter:'select',editoptions:{value:"0:否;1:是"}},
			],
			height : 300,
         	sortname: 'PNAME', //首次加载要进行排序的字段 
         	sortorder: "asc" //排序方式
    	});
			
		jQuery(function(){  
			var dictionaryGrid = new DictionaryGrid();	
			loadJqGrid("#tabGrid","#pager1",dictionaryGrid);
			boundSpButton();
		});	
		</script>
	</head>
<body>
	
	<div class="toolbox">
		<!-- 加载当前菜单栏目下操作     -->
		<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
	</div>
	
	<div class="searchtab">
         		<table width="100%" border="0">
			<tbody>
				<tr>
					<th>
						流程名称
					</th>
					<td>
						<input type="text" id="pname" name="pname"/>
					</td>
					<td>
		                <div class="btn">
		                   <button type="button" id="search_go" onclick="searchResult();return false;">
								查询
							</button>
							<button type="button" class="btn_cz" id="btn_cz" onclick="searchReset();return false;">
	              				重 置 
	             			</button>
		                 </div>
		            </td>
				</tr>
			</tbody>
		</table>
         	</div>

<table id="tabGrid"></table>
<div id="pager1"></div>
</body>
</html>