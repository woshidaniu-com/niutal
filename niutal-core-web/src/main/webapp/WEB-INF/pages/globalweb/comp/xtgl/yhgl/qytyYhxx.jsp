<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>启用/停用</title>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	<script type="text/javascript">
		jQuery(function($){
			$("#el_submit").click(function(){
				subForm();
			});
		});
	</script>
  </head>
  <body>
	<s:form method="post" action="yhgl_bcQyty.html" theme="simple">
		<s:hidden name="pkValue" id="el_pkValue"></s:hidden>
		<div class="tab">
			<table width="100%" border="0" class="formlist">
				<tbody>
					<tr>
						<th align="right" style="width: 23%">已选用户数
						</th>
						<td>
							<s:property value="yhs"/>
						</td>
					</tr>
					<tr>
						<th align="right" style="width: 23%">类别
						</th>
						<td>
							<s:select list="#{0:'停用'}" name="sfqy" headerKey="1" headerValue="启用" ></s:select>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="btn">
								<button type="button" id="el_submit">保存</button>
								<button type="button" onclick="iFClose();return false;">关闭</button>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</s:form>
  </body>
  <s:if test="result != null && result != ''">
  	<script>
  		alertMessage('${result}',function(){this.close();refershParent()});
 	</script>
  </s:if>
</html>

