<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<s:form id="klwhForm" action="" method="post" theme="simple">
     <input type="hidden" id="pkValue" name="pkValue" value="${pkValue}"/>   
     <input type="hidden" id="type" name="type" value="${type}"/>    
	 <div class="tab">
		 <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
		  	<thead>
		    	<tr>
		        	<th colspan="3"><span>规则选择</span></th>
		        </tr>
		    </thead>
		    <tfoot>
		      <tr>
		        <td colspan="3">
		          <div class="btn">
		            <button name="btn_tj" ty onclick="save();return false;">保 存</button>
		            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
		          </div></td>
		      </tr>
		    </tfoot>
		    <tbody>
			    <s:if test="type == 'qb'">
			      <tr align="center">
				      <td colspan="3" > 
				    	 <font color="red">您选择了全部初始化，请慎重操作！</font>
				      </td>
			     </tr>
			    </s:if>
		      	<tr>
		      		<td width = "10%" align="center">
		      			<input type="radio" id="sfz" name="gzlx" value="0" onclick=""/>
		      		</td>
			      	<td width = "70%" align="left" colspan="2">
			    		  按身份证后6位，无身份证按6个0
			      	</td>
		     	</tr>
		     	<tr>
			     	<td  align="center"><input type="radio" id="sdsr" name="gzlx" value="1" checked="checked"/></td>
			     	<td align="left" width="20%">
			     	手动输入${result}
			     	</td>
			     	<td>
			     	<s:textfield maxlength="20" name="mm" id="mm" cssStyle="width:180px" onfocus="changeCheck();"></s:textfield> 
			     	</td>
		      	</tr>
		      	<tr>
		     		<td  colspan="3" height="15px;"></td>
		      	</tr>
		    </tbody>
	  	</table>
  	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript">
function save(){
	var flag = jQuery("input[name='gzlx']:checked").val();
	var postUrl ="";
	if(flag==1&&(null==jQuery("#mm").val()||jQuery("#mm").val().length <6)){
		$.alert("密码长度不能小于6位！",function(){
			jQuery("#mm").focus();
		});
	}else{
		if(inputResult() && null!=$("#type").val()&&$("#type").val()=='qb'){
			postUrl = "<%=systemPath%>/xtgl/klwh_qbcsh.html";	 
		}else if(inputResult() && null!=$("#type").val()&&$("#type").val()=='pl'){
			postUrl = "<%=systemPath%>/xtgl/klwh_plcsh.html";
			
		}
		jQuery.ajax({
			url:postUrl,
			type:"post",
			dataType:"json",
			data:{"model.gzlx":flag,"model.mm":jQuery("#mm").val(),"model.pkValue":jQuery("#pkValue").val()},
			success:function(data){
				$.alert(data,function(){
					this.close();
					refershParent();
					iFClose();
					return false;
				});
			}
		});
	}
}

function changeCheck(){
	$('#sdsr').attr("checked","checked");
}
</script>
</html>