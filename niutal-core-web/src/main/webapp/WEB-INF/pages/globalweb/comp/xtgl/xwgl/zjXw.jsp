<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>  
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/My97DatePicker/WdatePicker.js?_rv_=<%=resourceVersion%>"></script>
		<script language="javascript">
		var editor;
		jQuery(function(){
			editor = KindEditor.create('textarea[name="fbnr"]', defaultOption);
		});
	
	
		function fbxw() { 
			var xwbt = trim(jQuery("#xwbt").val());
			var fbsj = jQuery('#fbsj').val();
			var fbnr = editor.html();
			
			jQuery("#fbnr").val(fbnr);

			if (xwbt == "") {
				alert("请填写新闻标题！");
				jQuery("#xwbt").focus();
				return false;
			}
	
			if (fbsj == ''){
				alert('请选择发布时间！');
				return false;
			}

			//验证发布对象
			if(!checkFbdx()){
				alert("请选择发布对象！");
				return false;
			}

			if (trim(fbnr) == ''){
				alert('请填写您要发布的新闻内容！');
				return false;
			}

			jQuery('form[name=newForm]').submit();
		}

		//验证发布对象是否为空   为空：false  不为空：true
		function checkFbdx(){
			var jqFbdx=jQuery("input[name='fbdxs']");
			var result=false;
			jqFbdx.each(function(index,obj){
				if(obj.checked == true){
					result=true;
					return false;
				}
			});
			return result;
		}
		</script>
	</head>
	<body>
		<s:form name="newForm" method="post" action="/xtgl/xwgl_zjBcXw.html" theme="simple">
			<div class="tab">
				<table width="90%" border="0" class="formlist">
					<thead>
						<tr>
							<th colspan="2">
								<span>增加新闻</span>
							</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="2">
								<div class="btn">
									<s:hidden id="xwbh" name="xwbh"></s:hidden>
									<button name="保存" onclick="fbxw();" type="button">
										保存
									</button>
									<button name="关闭" onclick="iFClose();" type="button">
										关闭
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>

						<tr>
							<th  width="13%">
								<font color="red">*</font>新闻标题
							</th>
							<td  width="87%">
								<s:textfield id="xwbt" name="xwbt" maxlength="100" cssStyle="width:80%" 
									></s:textfield>
							</td>
						</tr>
						<tr>
							<th>
								<font color="red">*</font>发布时间
							</th>
							<td>
								<s:textfield id="fbsj" name="fbsj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"></s:textfield>
							</td>
						</tr>
						<tr>
							<th>
								<font color="red">*</font>发布对象
							</th>
							<td>
								<s:checkboxlist list="fbdxList" name="fbdxs" listKey="key" listValue="value" ></s:checkboxlist>
							</td>
						</tr>
						<tr>
							<th>
								是否发布
							</th>
							<td>
								<s:radio name="sffb" list="sfList" listKey="key"
									listValue="value">
								</s:radio>
							</td>
						</tr>
						<tr>
							<th>
								是否置顶
							</th>
							<td>
								<s:radio name="sfzd" list="sfList" listKey="key"
									listValue="value">
								</s:radio>
							</td>
						</tr>
						<tr>
							<th>
								是否重要
							</th>
							<td>
								<s:radio name="sfzy" list="sfList" listKey="key"
									listValue="value">
								</s:radio>
							</td>
						</tr>
						<tr>
							<th>
								<font color="red">*</font>新闻内容
							</th>
							<td>
								<s:textarea cssStyle="width:100%;height:400px" name="fbnr" id="fbnr"></s:textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</s:form>
		<s:if test="message != null && message !=''">
			<script defer="defer">
				alert('${message}','',{'clkFun':function(){
					refershParent();
				}});
			</script>
		</s:if>
	</body>
</html>
