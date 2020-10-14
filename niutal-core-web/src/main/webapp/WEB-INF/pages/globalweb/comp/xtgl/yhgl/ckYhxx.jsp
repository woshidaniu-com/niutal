<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>

<s:form action="/xtgl/yhgl_add.html" method="post" theme="simple">
	<body>
          <!--   <div class="tab_cur">
				<p class="location">
					<em>您的当前位置:</em><a>${currentMenu}</a>	</div> -->
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>查看用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%">职工号</th>
	        <td width="30%"><e:forHtmlContent value="${model.zgh}"/></td>
	     	<th width="20%">姓名</th>
	        <td width="30%"><e:forHtmlContent value="${model.xm}"/></td>
	      </tr>

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%"><e:forHtmlContent value="${model.lxdh}"/></td>
	   		<th width="20%">Email</th>
	   		<td width="30%"><e:forHtmlContent value="${model.dzyx}"/></td>
	       </tr>
	      
	       <tr>
	    	 <th width="20%">是否启用</th>
	        <td colspan="3">
				<s:if test="sfqy=='0'.toString()">
					否
				</s:if>
				<s:if test="sfqy=='1'.toString()">
					是
				</s:if>
			</td>
	      </tr>
	      
	       <tr>
		       <th width="20%">所属角色</th>
		       <td colspan="3"><e:forHtmlContent value="${model.jsmc}"/></td>
			</tr>
		</table>
	  </div>
	</td>
	        
	</tr>
 </tbody>
</table>
  </div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  		refreshParent($('#result').val());
  	</script>
  </s:if>
</body>
</s:form>
</html>