<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	 <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>		
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script> 
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl.js?_rv_=<%=resourceVersion%>"></script>
	</head>
	
	<body >
		<html>
			<div class="toolbox">
				<div class="buttonbox">	
					<ul>
						<li><a href="#" class="btn_zj" id="btn_zj" onclick="saveData();return false;">保存</a></li>
						<li><a href="#" class="btn_qx" id="btn_qx" onclick="selectAll();return false;">全选</a></li>
						<li><a href="#" class="btn_sx" id="btn_sx" onclick="czBtn();return false;">重置</a></li>
						<li><a href="#" class="btn_fh" id="btn_fh" onclick="refRightContent('jsgl_cxJsxx.html');">返回</a></li>
					</ul>
				</div>
			</div>		
		    
		 	<table style="width: 795px" border="0" class="formlist">
				
				<tbody>
					<tr>
						<th width="15%">角色名称</th>
						<td width="30%">
							${model.jsmc}
						</td>
						<th width="15%">已分配用户数</th>
						<td>
							${model.yhnum}
						</td>
					</tr>
				</tbody>		
		</table>
 
		<s:include value="/WEB-INF/pages/globalweb/comp/xtgl/jsgl/gnCd.jsp"></s:include>
		<div id="tmpdiv1"></div>
		
		</html>
		
	</body>
</html>
