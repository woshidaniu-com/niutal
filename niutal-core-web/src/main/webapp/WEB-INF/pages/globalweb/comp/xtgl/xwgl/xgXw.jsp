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
		<script language="javascript">
			var editor;
			jQuery(function(){
				editor = KindEditor.create('textarea[name="fbnr"]', defaultOption);
			});
			
			function xgxw() { 
				//验证发布对象
				if(!checkFbdx()){
					alert("请选择发布对象！");
					return false;
				}
				
				if(editor.text().length==0){
					alert('请填写您要发布的新闻内容！');
					return false;
				}
				
				var url = _path+'/xtgl/xwgl_xgBcXw.html';
				ajaxSubFormWithFun("xgxwForm",url,{xwnr:editor.html()},function (data){
					alert(data,{},{"clkFun":function(){
						if(data==''){
							return false;
						}else{
							refershParent();
						}
					}});
				});
				
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
		<s:form id="xgxwForm" name="xgxwForm" method="post" action="/xtgl/xwgl_xgBcXw.html" theme="simple">

			<div class="tab">
				<table width="100%" border="0" class="formlist">
					<thead>
						<tr>
							<th colspan="4">
								<span>修改新闻</span>
							</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<s:hidden id="xwbh" name="xwbh"></s:hidden>
									<button type="button" name="保存" onclick="xgxw();return false;">
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
							<th>
								新闻标题
							</th>
							<td>
								<s:hidden id="xwbt" name="xwbt" />
								<s:label name="xwbt"></s:label>
							</td>
						</tr>
						<tr>
							<th>
								发布时间
							</th>
							<td>
								<s:hidden id="fbsj" name="fbsj" />
								${fbsj }
							</td>
						</tr>
						<tr>
							<th width="16%">
								<font color="red">*</font>发布对象
							</th>
							<td width="84%">
								<s:checkboxlist list="fbdxList" name="fbdxs" listKey="key" listValue="value" ></s:checkboxlist>
							</td>
						</tr>
						<tr>
							<th>
								是否发布
							</th>
							<td>
								<s:radio name="sffb" list="sfList" listKey="key"
									listValue="value" >
								</s:radio>
							</td>
						</tr>
						<tr>
							<th>
								是否置顶
							</th>
							<td>
								<s:radio name="sfzd" list="sfList" listKey="key"
									listValue="value" >
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
   								<textarea name="fbnr"  id="fbnr" cols="100" rows="8" style="width:100%;height:400px;">${fbnr}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</s:form>
		
	</body>
</html>
