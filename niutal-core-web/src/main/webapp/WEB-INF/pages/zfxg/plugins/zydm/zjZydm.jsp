<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/validate/talent-validate-all-min.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" type="text/css" href="<%=systemPath %>/js/validate/css/validate.css?_rv_=<%=resourceVersion%>">
		<script type="text/javascript">
		function save(){
			if (zfValidate.validate()){
				 ajaxSubFormWithFun("xydmForm",'<%=systemPath%>/zfxg/zydm/zjBcZydm.html',{},function(data){
				 	alert(data,{},{"clkFun":function(){
				 		refershParent();
				 	}});
				 });
			}
		}
		
		jQuery(function(){
			zfValidate.vf.req.addId("zydm", "zymc","xz","bmdm_id_lsbm");
		});
			
	</script>
</head>
<body>
<s:form action="/zfxg/zydm/zjBcZydm.html" method="post" theme="simple" id="xydmForm">
	
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加专业</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="30%"><span class="red">*</span>专业代码</th>
	        <td width="70%">
	        	<s:textfield maxlength="10" name="zydm" id="zydm" ></s:textfield> 
	       	</td>
	      </tr>
	      <tr>
	     	<th width="30%"><span class="red">*</span>专业名称</th>
	        <td width="70%">
	        <s:textfield maxlength="25" name="zymc" id="zymc" >
	        </s:textfield>
	         </td>
	      </tr>
	       <tr>
	      <th><span class="red">*</span>所属学院</th>
			<td>
				<s:select list="xyxxList" id="bmdm_id_lsbm" name="bmdm_id_lsbm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="---请选择---" ></s:select>
			</td>
	      </tr>
	      
	       <tr>
	     	<th width="30%"><span class="red">*</span>学制</th>
	        <td width="70%">
	        <s:textfield maxlength="4" name="xz" id="xz" onkeyup="value=value.replace(/[^(\d||/.)]/g,'')">
	        </s:textfield>
	         </td>
	      </tr>
	      
	       <tr>
	     	<th width="30%">国家专业代码</th>
	        <td width="70%">
	        <s:textfield maxlength="15" name="cydm_id_gbzydm" id="cydm_id_gbzydm">
	        </s:textfield>
	         </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script>
  		alert('${result}','',{'clkFun':function(){refershParent();}});
  	</script>
  </s:if>
</s:form>
</body>
</html>