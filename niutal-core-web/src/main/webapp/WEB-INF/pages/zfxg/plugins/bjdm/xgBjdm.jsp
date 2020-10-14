<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
			<script type="text/javascript" src="<%=systemPath %>/js/validate/talent-validate-all-min.js?_rv_=<%=resourceVersion%>"></script>
			<link rel="stylesheet" type="text/css" href="<%=systemPath %>/js/validate/css/validate.css?_rv_=<%=resourceVersion%>"></link>
			<script type="text/javascript">
				function save(){
					if (zfValidate.validate()){
						 ajaxSubFormWithFun("xydmForm",'<%=systemPath%>/zfxg/bjdm/xgBcBjdm.html',{},function(data){
						 	alert(data,{},{"clkFun":function(){
						 		refershParent();
					 	 }});
					 });
					}
				}
	
				jQuery(function(){
					zfValidate.vf.req.addId("bjmc", "bmdm_id", "zydm_id", "njdm_id");
					
					jQuery("#bmdm_id").bind("change",function(){
						var xydm = jQuery(this).val();
						jQuery.post(_path+"/zfxg/zydm/cxZydmByXydm.html",{bmdm_id_lsbm:xydm},function(data){
							jQuery("#zydm_id option:not(:first)").remove();
							
							jQuery.each(data,function(i,n){
								jQuery("#zydm_id").append("<option value='"+data[i]["zydm_id"]+"'>"+data[i]["zymc"]+"</option>");
							});
						},'json');
					});
				});
			</script>
	</head>

	<body >
		<s:form action="/zfxg/xydm/xgBcXydm.html" method="post" theme="simple" id="xydmForm">
	     	<s:hidden name="bjdm_id" id="bjdm_id"/>
		 <div class="tab">
		  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>修改班级</span></th>
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
	        <th width="30%">班级代码</th>
	        <td width="70%">
	        	<s:text name="bjdm"/>
	        </td>
	      </tr>
	      <tr>
	     	<th><span class="red">*</span>班级名称</th>
	        <td>
	        	<s:textfield maxlength="25" name="bjmc" id="bjmc"></s:textfield>
	        </td>
	      </tr>
	      <tr>
	     	<th><span class="red">*</span>所属学院</th>
	        <td>
	        	<s:select list="xydmList" id="bmdm_id" 
						  name="bmdm_id" listKey="bmdm_id" 
				  		  listValue="bmmc" headerKey="" headerValue="---请选择---"
				  		  cssStyle="width:180px;"
			    ></s:select>
	        </td>
	      </tr>
	      <tr>
	     	<th><span class="red">*</span>所属专业</th>
	        <td>
	        	<s:select list="zydmList" id="zydm_id" 
						  name="zydm_id" listKey="zydm_id" 
				  		  listValue="zymc" headerKey="" headerValue="---请选择---"
				  		  cssStyle="width:180px;"
			  	></s:select>
	        </td>
	      </tr>
	      <tr>
	     	<th><span class="red">*</span>所属年级</th>
	        <td>
	        	<s:textfield maxlength="4" name="njdm_id" id="njdm_id"></s:textfield>
	        </td>
	      </tr>
	    </tbody>
	  </table>
	  </div>
	</s:form>
	</body>
</html>