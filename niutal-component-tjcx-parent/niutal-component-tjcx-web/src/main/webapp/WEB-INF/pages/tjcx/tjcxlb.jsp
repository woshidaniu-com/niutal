<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxPublic.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxlb.js?_rv_=<%=resourceVersion%>"></script>
	<style>
			.tjxm .tjxm_2 .tjxm_2_title a.more {
				float: right;
				margin-top: 8px;
				margin-right: 10px;
				display: block;
				width: 10px;
				height: 10px;
				background: url(<%=stylePath %>/images/fwgl_cld_3_more.gif) center center no-repeat;
			}
			
			.tjxm .tjxm_2 .tjxm_2_title a.more1 {
				float: right;
				margin-top: 8px;
				margin-right: 10px;
				display: block;
				width: 10px;
				height: 10px;
				background: url(<%=stylePath %>/images/fwgl_cld_3_morex.gif) center center no-repeat;
			}
			
			#tjxmAddTjk {
				float: right;
				color: #116eb6;
				font: normal 12px/31px "宋体";
				margin-right: 45px;
			}

		</style>
	</head>
	<body>
   	 <input type="hidden" id="currentMenu" name="currentMenu" value="${TJCX_CURRENT_MENU}"/>
	
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>统计分析-统计查询</a>
			</p>	
		</div>
		<div class="prompt">
				<h3>
					<span>系统提示：</span>
				</h3>
				<p>
					当前展示列由[导出]功能中自定义字段进行配置生成
				</p>
				<a class="close" title="隐藏" onclick="this.parentNode.style.display='none';"></a>
		</div>				
    <form action="/zfxg/tjcx/tjcx_export.html" method="post" id="form1">

     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
     	<input type="hidden" id="gltjmc" name="gltjmc" value="${gltjmc}"/>
     	<input type="hidden" id="allGltj" name="allGltj" value="${gltj}">
       	<input type="hidden" id="newGltj" name="newGltj" value="">
       	<input type="hidden" id="tjlbFhbs" name="tjlbFhbs" value="1"/>
     	<input type="hidden" id="zdxsms" name="zdxsms" value="2"/>
        <input type="hidden" id="czy" name="czy" value="${czy}"/>
        <input type="hidden" id="tjcxlbxq" name="tjcxlbxq" value="${tjcxlbxq}"/>
       	
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<div class="buttonbox">
				<ul id="but_ancd">			
					<li>
						<a href="javascript:void(0);" id="btn_fh" class=btn_fh>返回</a>
					</li>		
					<li>
						<a href="javascript:void(0);" id="btn_dc" class=btn_dc>导出</a>
					</li>	
					<li>
						<a href="javascript:void(0);" id="btn_sx" class=btn_sx>刷新</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="clearall"></div>		
		    
  	 	<div class="tjxm">
	    	 <div class="clearall"></div>
	 		 <div class="tjxm_2" style="display:none;" id="oldYxtjDiv">
			        <div class="gltj">
				        <div class="gltj1">
				        	<div class="clearall"></div>
					        <div class="gltj1_2">
						        <p><span>已选条件</span><a href="javascript:;" title="展开更多"></a></p>
						        <ul id="yxtj">
							        <div class="clearall"></div>
						        </ul>
						        <div class="clearall"></div>
						    </div>
						 </div>
			        </div>
			    </div>  	 	
	    		<div class="clearall"></div>
		   	 <div class="tjxm_2" id="curTjxmDiv">
		    	<h4 class="tjxm_2_title" >
			    	<span>过滤条件</span>
			    	<span class="text1">(条件块内的各字段为“并且”关系，条件块间为“或者”关系)</span>
			    	<a href="javascript:;" id="tjlbZkgd" title="展开更多" class="more"></a>
			    	<a href="javascript:;" onclick="addCxzdTjk();" id="tjxmAddTjk" style="display:none;">增加条件块</a>
		    	</h4>
		        <div class="gltj" id="cxzdDiv" style="display:none;"></div>
		  	  </div>
			<div class="tjxm_4" style="display:none;"><a href="javascript:;" class="tj" id="cxBtn" onclick="tjcx();">查  询</a></div>
		</div>    
		<div class="clearall"></div>		
		<div class="formbox"></div>
	</form>
	</body>
	
</html>
