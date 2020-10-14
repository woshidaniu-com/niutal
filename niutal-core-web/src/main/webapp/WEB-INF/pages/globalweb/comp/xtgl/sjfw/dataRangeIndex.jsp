<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/dataRangeIndex.js?_rv_=<%=resourceVersion%>"></script>
	<style>
	.ui-jqgrid tr.jqgrow td {
	  	vertical-align:middle;
	  	text-align: center;
	}
	.sjfw_table td{
	  	height:auto;
	  	border: 0px;
	}
	
	.lisnavBox{
		clear: both;
	}
	
	.lisnavBox li{
		min-height: 23px;
		line-height: 23px;
		list-style: none;
		list-style-type: none;
		overflow: hidden;
		-moz-background-clip: padding;
		-webkit-background-clip: padding-box;
		background-clip: padding-box;
	}
	
	.lisnavBox li p{
		background: none;
		padding-bottom: 0;
		margin: 0;
	}
	</style>
	<script type="text/javascript">
	jQuery(function() {
		var sjfwGrid = new SjfwGrid();
		loadJqGrid("#tabGrid", "#pager", sjfwGrid);
	})
</script>
</head>
<body>
	<div class="searchtab">
		<s:form name="form" method="post" action="/xtgl/yhgl_xxx.html" theme="simple">
			<table width="100%" border="0" id="searchTab">
				<thead>
					<tr>
			         <div class="toolbox">
						<div class="buttonbox">	
							<ul>
							<li><a href="#" class="btn_zj" id="btn_zj" onclick="sjfwsz();return false;">数据授权</a></li>
				          	<li><a href="#" class="btn_fh" id="btn_fh" onclick="refRightContent('yhgl_cxYhxx.html');return false;">返回</a></li>
							</ul>
						</div> 
					</div>	
			      </tr>
				</thead>
				<tbody>
					<tr>
						<th>
							角&nbsp;色
						</th>
						<td>
							<s:select list="jsxxList" listKey="jsdm" listValue="jsmc"
								headerKey="" headerValue="全部" id="jsdm" name="jsdm"
								onchange="searchResult();return false;" cssStyle="width:150px;"></s:select>
						</td>
						<th>
							所属机构
						</th>
						<td>
							<s:select list="jgdmsList" listKey="bmdm_id" listValue="bmmc"
								headerKey="" headerValue="全部" id="jgdm" name="jgdm"
								onchange="searchResult();return false;" cssStyle="width:150px;"></s:select>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
	<div class="formbox"  style="">
		<table id="tabGrid"></table>
		<div id="pager"></div>
	</div>
	
	
</body>
</html>
