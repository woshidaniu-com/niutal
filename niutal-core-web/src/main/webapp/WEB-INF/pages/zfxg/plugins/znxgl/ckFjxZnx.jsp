<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>

<body>
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>信件发送</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2">
	          <div class="btn">
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="15%"><span class="red"></span>收件人</th>
	        <td>
	        	${jsrmc }
	       </td>
	      </tr>
	       <tr>
	        <th><span class="red"></span>主题</th>
	        <td>${zt } </td>
	      </tr>
	       <tr>
	        <th><span class="red"></span>发送内容</th>
	        <td style="word-break:break-all;">
	        	<div style="width: 660px; height: 400px; overflow: auto;">
	        	${fsnr }
	        	</div>
	        </td>
	        </tr>
	    </tbody>
	  </table>
  </div>
</body>
</html>