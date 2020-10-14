<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			function save() { 
				//编辑器
				var fsnr = editor.html();
				jQuery("#fsnr").val(fsnr);
				
				var jsrzghs = jQuery.trim(jQuery("#jsrzghs").val());
				var zt = jQuery.trim(jQuery('#zt').val());
				var fsnr = jQuery('#fsnr').val();
			
				if (jsrzghs == "") {
					alert("请填写收件人！");
					jQuery("#jsrzghs").focus();
					return false;
				}
			
				if (zt == ''){
					alert('请输入主题！');
					jQuery("#zt").focus();
					return false;
				}

				if (zt.length > 100){
					alert('主题内容过长,请修改！');
					jQuery("#zt").focus();
					return false;
				}

				//编辑器
				if(editor.text().length == 0){
					alert('请输入发送内容！');
					return false;
				}
			
				jQuery('form[name=newForm]').submit();
			}

			var editor;
			
			//初始化页面
			jQuery(function(){
				//编辑器
				editor = KindEditor.create('textarea[name="fsnr"]', defaultOption);
			});
		</script>
	</head>
<body>
<s:form action="/zfxg!plugins/znxgl_bczjZnx.html" name="newForm" method="post" theme="simple">
	<s:hidden name="jsrzghs" id="jsrzghs" value="%{fsrzgh}"></s:hidden>
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>信件回复</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2" ><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();return false;">发 送</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="15%">收件人</th>
	        <td>
	        	${fsrxm } 
	       </td>
	      </tr>
	       <tr>
	        <th><span class="red">*</span>信件主题</th>
	        <td>
	        	<s:textfield maxlength="100" name="zt" id="zt" cssStyle="width:95%" ></s:textfield>
	        </td>
	      </tr>
	       <tr>
	        <th><span class="red">*</span>发送内容</th>
	        <td>
	       		 <s:textarea name="fsnr" id="fsnr" rows="8"  cssStyle="width:95%; height:400px;"></s:textarea>
	        </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script type="text/javascript">
  		jQuery(function(){
	  		alert('${result}','',{'clkFun':function(){refershParent();}});
  		});
  	</script>
  </s:if>
</s:form>
</body>
</html>