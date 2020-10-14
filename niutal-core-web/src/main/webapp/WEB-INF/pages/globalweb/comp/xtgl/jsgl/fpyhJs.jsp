<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl_fpyh.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			//为select option 增加title属性提示
			jQuery(function(){
				addOptionTitle();
				yfpGrid=new YfpGrid();
				loadJqGrid("#yfpTabGrid","#pagerYfp",yfpGrid);
			});
		</script>
	</head>
	<body >
	 <div class="toolbox">
	 	<div class="buttonbox">	
			<ul>
			<li><a href="#" class="btn_fh" id="btn_fh" onclick="refRightContent('jsgl_cxJsxx.html');return false;">返回</a></li>
			<li><a href="#" class="btn_sq" id="btn_zj" onclick="jsyhSq();return false;">授权</a></li>
			<li><a href="#" class="btn_hsz" id="btn_sc" onclick="cancelJsyhSq();return false;">撤销</a></li>
			</ul>
		</div> 
		
		<table width="100%" class=" formlist">
		    <tbody>
		      <tr>
		        <th width="20%">角色名称</th>
		        <td width="80%">
		        	<s:select list="jsModelList" id="jsdm" name="jsdm" listKey="jsdm" listValue="jsmc" theme="simple" onchange="loadJsyhxx(jQuery(this).val());"/>
		        </td>
		      </tr>
		      <tr>
		        <th>角色说明</th>
		        <td style="word-wrap:break-word"><e:forHtmlContent value="${model.jssm}"/></td>
		      </tr>
		    </tbody>
	 	</table>
	 
	   <div class="searchtab"  style="margin-top: 2px">
				<s:form id="fpyhForm" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
				<input type="hidden" name="jsdm" value="<e:forHtmlAttribute value="${model.jsdm }"/>"/>   
				<table width="100%" border="0" id="searchTab">
					<tbody>
		              <tr>
		                <th width="10%">用户名</th>
		                <td width="18%">
		                	<input type="text" name="zgh" id="zgh" style="width:120px"/>
		                </td>
		                <th width="10%">姓   名</th>
		                <td width="18%">
		                	<input type="text" name="xm" id="xm" style="width:120px"/>
		                </td>
		                <th width="10%">所属机构</th>
		                <td width="18%">
		                	<s:select list="jgdmsList" id="jgdm" name="jgdm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="全部" cssStyle="width:180px"></s:select>
		                </td>
		                <td width="16%">
		                  <div class="btn">
		                    <button class="btn_cx" id="search_go"
								onclick="searchYfpResult();return false;">
								查 询
							</button>
		                  </div>
		                  </td>
		              </tr>
		            </tbody>
				</table>
				</s:form>
			</div>
	 </div>
	
  <div style="width:793px">
		<div  style="width:100%">
			<table id="yfpTabGrid"></table>
			<div id="pagerYfp"></div>
		</div>
	</div>
 	<s:if test="message != null && message !=''">
		<script defer="defer">
			alert('${message}');
		</script>
	</s:if>
</body>
</html>