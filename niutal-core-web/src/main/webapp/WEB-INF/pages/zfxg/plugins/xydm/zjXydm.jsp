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
				 ajaxSubFormWithFun("xydmForm",'<%=systemPath%>/zfxg/xydm/zjBcXydm.html',{},function(data){
				 	alert(data,{},{"clkFun":function(){
				 		refershParent();
				 	}});
				 });
				 }
			}
		
			var MyValidator1 = zfValidate.BV.ext({ 
			   v:function(trimedValue, indexOfElements, elements, field){
					
					var isExists = true;
					jQuery.ajaxSetup({async:false});
					jQuery.ajax({
							url:"<%=systemPath%>/zfxg/xydm/valideXydm.html",
							type:"post",
							dataType:"json",
							data:{pkValue:trimedValue},
							success:function(data){
							if(data!=null){
								isExists =  false;
							}
						}
					});
					jQuery.ajaxSetup({async:true});
					return isExists;
				},
				getI18 : function(label){  
			        return "该院系代码已存在!";  
			    },
			    getTip : function(e,f,v,val) {  
			        return "该院系代码可以使用!";  
			    } 
			});
			
			jQuery(function(){
				zfValidate.vf.req.addId("bmdm_id", "bmmc");
				new MyValidator1().addId("bmdm_id");
			});
			
	</script>
</head>

<body>
<s:form action="/zfxg/xydm/zjBcXydm.html" method="post" theme="simple" id="xydmForm">
	
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加学院</span></th>
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
	        <th width="30%"><span class="red">*</span>学院代码</th>
	        <td width="70%">
	       
	        <s:textfield maxlength="15" name="bmdm_id" id="bmdm_id"></s:textfield> 
	       </td>
	      </tr>
	      <tr>
	     	<th width="30%"><span class="red">*</span>学院名称</th>
	        <td width="70%">
	        <s:textfield maxlength="25" name="bmmc" id="bmmc">
	        </s:textfield>
	         </td>
	      </tr>
	       <tr>
	     	<th width="30%"><span class="red">*</span>部门类型</th>
	        <td width="70%">
				<s:select list="bmlxList" name="bmlx" listKey="dm" listValue="mc"></s:select>
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