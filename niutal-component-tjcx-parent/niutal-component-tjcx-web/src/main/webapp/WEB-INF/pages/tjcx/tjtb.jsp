<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comm/jquery/jquery-1.8.0.min.js?_rv_=<%=resourceVersion%>"></script>
<%-- 		<script type="text/javascript" src="<%=stylePath %>/js/lhgdialog/lhgdialog.min.js?_rv_=<%=resourceVersion%>"></script> --%>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comm/highcharts/highcharts.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comm/highcharts/modules/exporting.src.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjtb.js?_rv_=<%=resourceVersion%>"></script>
	</head>
	<body>
    <s:form namespace="/zfxg/tjcx" action="tjtb_tjtb" method="post" id="form1" >

     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
        <input type="hidden" id="curBbl" name="curBbl" value=""/>
       	<input type="hidden" id="curBblmc" name="curBblmc" value=""/>
       	      	
    	<input type="hidden" id="hxlHead" name="hxlHead" value="${hxlHead}">
    	<input type="hidden" id="hxlContent" name="hxlContent" value="${hxlContent}">
    	<input type="hidden" id="zxlContent" name="zxlContent" value="${zxlContent}">
       	<input type="hidden" id="zxlHeadCode" name="zxlHeadCode" value="${zxlHeadCode}">
 
    	<input type="hidden" id="hxlContentCode" name="hxlContentCode" value="${hxlContentCode}">
    	<input type="hidden" id="zxlContentCode" name="zxlContentCode" value="${zxlContentCode}">
    	
    	<input type="hidden" id="hxlhjContentJson" name="hxlhjContentJson" value="${hxlhjContentJson}">
     	<input type="hidden" id="zxlhjContentJson" name="zxlhjContentJson" value="${zxlhjContentJson}">
     	<input type="hidden" id="tjxJson" name="tjxJson" value="${tjxJson}">
     	<input type="hidden" id="contentJson" name="contentJson" value="${contentJson}">
     	<input type="hidden" id="zxlHead" name="zxlHead" value="${zxlHead}">
    </s:form>
    
 <!-- 过滤条件 -->
    <div class="searchtab">
          <table width="100%">
            <tbody>
              <tr>
                <th>统计项：</th>
                <td id="tjxTd">
                </td>
                <th>统计选项：</th>
                <td id="tjzhTd">
				</td>
              </tr>
              <tr>
                <th>图表类型：</th>
                <td>
                	<label><input type="radio" name="tblx" value="line" checked="checked"/>折线图</label>&nbsp;
                    <label><input type="radio" name="tblx" value="column"/>柱状图</label>&nbsp;
                	<span id="tblx_pie" ><label><input type="radio"  name="tblx" value="pie"/>饼图<font color="red">(仅限一维数据，自动匹配可选选项)</font></label>&nbsp;</span>
                </td>
				<th>图表标题：</th>
                <td><input id="tbbt" name="tbbt" alt="" maxlength="50" value="统计图表" style="width:300px;" /></td>                
              </tr>
          	  </tbody>
			<tfoot>
              <tr>
                <td colspan="4"><div class="bz">
                </div>
                  <div class="btn">
                  </div></td>
              </tr>
            </tfoot>          	  
            </table>
        </div>

		<div id="container" style="margin: 10px 20px 0 20px "></div>
</body>
</html>

