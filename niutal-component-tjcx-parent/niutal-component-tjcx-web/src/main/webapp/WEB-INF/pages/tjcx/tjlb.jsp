<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>统计报表</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjlb.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxPublic.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjlbForTb.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjlbForGltj.js?_rv_=<%=resourceVersion%>"></script>
		<style>
			#tjlbDiv table{border:1px solid #999;}
			#tjlbDiv table tr td,th{white-space:nowrap;}
			#tjlbDiv table tr th{padding:5px;border:1px solid #999;}
			#tjlbDiv table tr td{padding:5px;border:1px solid #999;font: normal 15px/15px "宋体";text-align:right}
			#tjlbDiv table tr td.tjlbleft{text-align:left}
			#tjlbDiv table tr td.tjlbtotal{font:bold 15px/15px "宋体";}
			#tip{display: none;
				text-align:center;
				position: absolute;
				z-index: 100;
				width: 100%;
				height: 100%;
				background: transparent;
				background-color: #eee;
				opacity: 0.8;
				filter:alpha(opacity=80);
				top:0;
				padding-top:250px}
			#tjlbDiv table tr td a{text-decoration: underline;}
			#tjlbDiv table tr td a:link{ color:#0f5dc2}
			#tjlbDiv table tr td a:hover{ color:#000}
			
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

			.tjxm_4 label{
				color: #155fbe;
			    float: left;
			    font-weight: bold;
			    margin-top: 4px;
			    margin-bottom: 4px;
			    margin-right:20px;
			}
		</style>
  </head>
  <OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT> 
  <body>
 	<s:form namespace="/zfxg/tjcx" action="tjbb_export" method="post" id="form1" >
     	<input type="hidden" id="bbhxl" name="bbhxl" value="${bbhxl}"/>
     	<input type="hidden" id="bbzxl" name="bbzxl" value="${bbzxl}"/>
     	<input type="hidden" id="tsx" name="tsx" value="${tsx}"/>
     	<input type="hidden" id="oldGltj" name="oldGltj" value="${gltj}">
       	<input type="hidden" id="newGltj" name="newGltj" value="">
    	<input type="hidden" id="tableHtml" name="tableHtml" value="">
     	<input type="hidden" id="zdxsms" name="zdxsms" value="2"/>
        <input type="hidden" id="czy" name="czy" value="${czy}"/>
    </s:form>
    
    <s:form action="/zfxg/tjcx/tjtb_tjtb.html" method="post" id="form2" target="_blank" >
    	<input type="hidden" id="hxlHead" name="hxlHead" value="">
    	<input type="hidden" id="hxlContent" name="hxlContent" value="">
    	<input type="hidden" id="hxlContentCode" name="hxlContentCode" value="">
    	<input type="hidden" id="zxlContent" name="zxlContent" value="">
    	<input type="hidden" id="zxlContentCode" name="zxlContentCode" value="">
    	<input type="hidden" id="hxlhjContentJson" name="hxlhjContentJson" value="">
     	<input type="hidden" id="zxlhjContentJson" name="zxlhjContentJson" value="">
     	<input type="hidden" id="contentJson" name="contentJson" value="">
      	<input type="hidden" id="tjxJson" name="tjxJson" value="">
     	<input type="hidden" id="zxlHead" name="zxlHead" value="">
      	<input type="hidden" id="zxlHeadCode" name="zxlHeadCode" value="">
     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
      	<input type="hidden" id="gltjmc" name="gltjmc" value="${gltjmc}"/>
        <input type="hidden" id="curBbl" name="curBbl" value=""/>
       	<input type="hidden" id="curBblmc" name="curBblmc" value=""/>
       </s:form>

    
    <table cellspacing="0" cellpadding="2" width="800px" align="center" border="0" id="tjlbGnszq">
    	<tr><td>
     	<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<div class="buttonbox"  style="display: none;">
				<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" id="btn_dc" class=btn_dc>
							导出</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_dy" class=btn_dy>
							打印</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_tj" class=btn_tj>
							生成图表</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_cx" class=btn_cx>
							使用说明</a>
					</li>
				</ul>
			</div> 
		</div>
    
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
	        <div class="gltj" id="cxzdDiv" style="display:none;">
	        </div>
	  	  </div>

			<div class="tjxm_4" style="display:none;">
				<label>固定总数合计<input type="checkbox" value="1" name="lockInitial"></label>
				<a href="javascript:;" class="tj" id="tjBtn" onclick="tjlb();">统  计</a>
			</div>
		</div>    
		</td></tr>
	</table>
   
    <br/>
		<% if(request.getAttribute("kzms") != null){ %>
    	 <!-- 快照描述 -->
	    <table cellspacing="0" cellpadding="2" width="800px" align="center" border="0" id="tjbb_kzms">
	    	<tbody>
	    		<tr>
	    			<td>
	    				<div style="  border: solid 1px; border-color: #dbe6f0; padding: 10px 10px 10px 10px;">
	    					${kzms}
	    				</div>
	    			</td>
	    		</tr>
	    	</tbody>
	    </table>
   		<% } %>
    
    <br/>
    
 	<div id="tip">
 		<img src="<%=stylePath %>images/ico_loading.gif" width="140px" height="140px" />
 	</div>
		
  	<div id="tjlbDiv" style="display:block;"></div>
  	
  </body>
</html>
