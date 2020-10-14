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
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
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
			
				if (jQuery.trim(zt) == ''){
					alert('请输入主题！');
					jQuery("#zt").focus();
					return false;
				}
			
				//编辑器
				if(editor.text().length == 0){
					alert('请输入发送内容！');
					return false;
				}
			
				//jQuery('form[name=newForm]').submit();
				ajaxSubForm('newForm','<%=systemPath%>/zfxg!plugins/znxgl_bczjZnx.html');
			}

			//选择
			function xzsj(){
				var jqYhlx=jQuery("input[name=yhlx]");
				var xzlx="student";//选择类型
				var sfdx="1"; //0：为单选 ;1: 为多选
				jqYhlx.each(function(){
					if(jQuery(this).attr("checked")=="checked"){
						xzlx=jQuery(this).val();
					}
				});
				if(xzlx=="student"){
					//弹出学生选择   sfdx 是否多选
					showWindow('',700,510,_path+'/zfxg!plugins/xsxz_cxXsxz.html?sfdx='+sfdx);
				}else if(xzlx=="company"){
					//弹出企业选择
					showWindow('',750,510,_path+'/zfxg!plugins/dwxz_cxDwxz.html?sfdx='+sfdx);
				}else if(xzlx="teacher"){
					//弹出老师选择
					showWindow('',700,510,_path+'/zfxg!plugins/lsxz_cxLsxz.html?sfdx='+sfdx);
				}
			}

			//弹出选择页面的回调函数
			function setChooseData(dataList,dataType){
				if(dataList != null && dataList.length>0){
					if(dataType=="xsxx"){
						var xh="";
						var xm="";
						for(var i=0;i < dataList.length; i++){
							if(xh==""){
								xh=dataList[i].xh;
								xm=dataList[i].xm;
							}else{
								xh=xh+","+dataList[i].xh;
								xm=xm+","+dataList[i].xm;
							}
						}
						jQuery("#jsrzghs").val(xh);
						jQuery("#jsr").val(xm);
					}else if(dataType=="dwxx"){
						var zzjgdm="";
						var dwmc="";
						for(var i=0;i < dataList.length; i++){
							if(zzjgdm==""){
								zzjgdm=dataList[i].zzjgdm;
								dwmc=dataList[i].dwmc;
							}else{
								zzjgdm=zzjgdm+","+dataList[i].zzjgdm;
								dwmc=dwmc+","+dataList[i].dwmc;
							}
						}
						jQuery("#jsrzghs").val(zzjgdm);
						jQuery("#jsr").val(dwmc);
					}else if(dataType=="lsxx"){
						var zgh="";
						var xm="";
						for(var i=0;i < dataList.length; i++){
							if(zgh==""){
								zgh=dataList[i].zgh;
								xm=dataList[i].xm;
							}else{
								zgh=zgh+","+dataList[i].zgh;
								xm=xm+","+dataList[i].xm;
							}
						}
						jQuery("#jsrzghs").val(zgh);
						jQuery("#jsr").val(xm);
					}
				}
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
<s:form action="/zfxg!plugins/znxgl_bczjZnx.html" id="newForm" method="post" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>信件发送</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2" ><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" type="button" onclick="save();">发 送</button>
	            <button name="btn_gb" type="button" onclick="refRightContent('<%=systemPath%>/zfxg!plugins/znxgl_sjxZnx.html')">返回</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	      	<th width="15%">面向用户</th>
	        <td>
	        	<input type="radio" value="student" name="yhlx" checked="true"/>学生
	        	<input type="radio" value="teacher" name="yhlx"/>老师
	        </td>
	      </tr>
	      <tr>
	        <th>
	        	<span class="red">*</span>收件人
	        </th>
	        <td>
	        	<s:textfield maxlength="20" name="jsr" id="jsr"  cssStyle="width:85%"  disabled="true"></s:textfield>
	        	<input type="hidden" name="jsrzghs" id="jsrzghs"/>
	        	<button class="btn_01" type="button" onclick="xzsj();return ;" >选择</button>
	        </td>
	      </tr>
	       <tr>
	        <th><span class="red">*</span>信件主题</th>
	        <td><s:textfield maxlength="100" name="zt" id="zt" cssStyle="width:95%" ></s:textfield> </td>
	      </tr>
	       <tr>
	        <th><span class="red">*</span>发送内容</th>
	        <td style="word-wrap:break-word;word-break:break-all;">
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