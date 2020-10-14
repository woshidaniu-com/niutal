<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>		
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/cxTjbbXq.js?_rv_=<%=resourceVersion%>"></script>
		

		
	</head>
	<body>
    <form action="/zfxg/tjcx/tjcx_export.html" method="post" id="form1">

     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
	
     	<input type="hidden" id="gltj" name="gltj" value=""/>
        <input type="hidden" id="curBbl" name="curBbl" value=""/>
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<div class="buttonbox">
			<ul id="but_ancd">			
					<li>
						<a href="javascript:void(0);" id="btn_dc" class=btn_dc>
							导出</a>
					</li>
			</ul>
		</div>
		</div>
		
		<div class="clearall"></div>
		<div class="tjxm">
		    <div class="tjxm_2">
		        <div class="gltj">
			        <div class="gltj1">
			        	<div class="clearall"></div>
				        <div class="gltj1_2" style="display:none;">
					        <p><span>已选条件</span><a href="javascript:;" title="展开更多"></a></p>
					        <ul id="yxtj">
					     	   <li>
					     	     	<span class="title"><font color='#155fbe'>未选择任何条件&nbsp;</font></span>
					        	</li>
					        	<div class="clearall"></div>
					        </ul>
					        <div class="clearall"></div>
					    </div>
					    <div class="gltj1_2">
					        <ul id="dqxx">
					     	   <li>
					     	     	<span class="title"><font color='#155fbe'>所有记录&nbsp;</font></span>
					        	</li>
					        	<div class="clearall"></div>
					        </ul>
					        <div class="clearall"></div>
					    </div>
					 </div>
		        </div>
		    </div>
		</div>		

		<div class="clearall"></div>		
		<div class="formbox"></div>
	</form>
	</body>
	
</html>
