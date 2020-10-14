<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
	<s:form id="myForm" namespace="/zxzx/zxwt" action="fhBcZxwt" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>咨询信息</span></th>
	        </tr>
	    </thead>
	    <tbody>
	      <tr>
	         <th width="15%">咨询人</th>
	         <td>
		        <s:label name="zxrModel.xm"></s:label>
	         </td>
	         <th width="15%">咨询时间</th>
	         <td>
		        <s:label name="zxsj"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="15%">咨询版块</th>
	         <td>
		        <s:label name="kzdkModel.bkmc"></s:label>
	         </td>
	         <th></th>
	         <td></td>
	      </tr>
	      <thead>
	    	<tr>
	        	<th colspan="4"><span>提问信息</span></th>
	        </tr>
	      </thead>
	      <tr>
	      	 <th>咨询主题</th>
	         <td colspan="3">
				<s:label name="kzdt"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th>咨询内容</th>
	         <td colspan="3">
				<s:label name="zxnr"></s:label>
	         </td>
	      </tr>
	      <thead>
	    	<tr>
	        	<th colspan="4"><span>回复信息</span></th>
	        </tr>
	      </thead>
	      <tr>
	        <th colspan="1"><span>回复状态</span></th>
	    	<td colspan="3">
	    		<s:if test='hfzt == "1"'>
	    			已回复
	    		</s:if>
	    		<s:elseif test='hfzt == "0"'>
	    			未回复
	    		</s:elseif>
	    		<s:else>
	    			未知
	    		</s:else>
	    	</td>
	       </tr>
	      <s:if test='hfzt == "1"'>
	      	 <tr>
		        <th colspan="1"><span>回复内容</span></th>
		    	<td colspan="3">
		    		<s:label name="zxhfModel.hfnr"></s:label>
		    	</td>
		       </tr>
		       <tr>
		       	<th colspan="1"><span>回复后是否公开</span></th>
		    	<td colspan="3">
		    		<s:if test='sfgk == "1"'>
		    			公开
		    		</s:if>
		    		<s:elseif test='sfgk == "0"'>
		    			不公开
		    		</s:elseif>
		    		<s:else>
		    			未设置
		    		</s:else>
		    	</td>
		       </tr>
	      </s:if>
	    </tbody>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	  </table>
  </div>
</s:form>
</body>
</html>