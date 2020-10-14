<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/xsxxgl/pldrzpXsxx.js?_rv_=<%=resourceVersion%>"></script>
</head>
<body>
<div class="tab">
  	<s:form action="/zfxg/xsxxgl/pldrzpXsxxBc.html" method="post" enctype="multipart/form-data">
  	<input type="hidden" name="zpType" value="${zpType}"/>
    <table class="formlist" width="100%" align="center">
    	<thead>
			<tr>
				<th colspan="4">
					<span>照片批量导入</span>
				</th>
			</tr>
		</thead>
		<tbody>
		<tr>
		
			<th>照片命名类型：</th>
			<td>
			<SELECT id="photoNameType" name="photoNameType" style="width:120px">
					 	 <option value="xh">学号</option>
					 	 <option value="sfzh">身份证号</option>
					 </SELECT>
			</td>
		</tr>
		<tr>
			<th>上传文件：</th>
			<td >
			<input type="file" name="file" id="file" value="" contenteditable="false"/>
			</td>
		</tr>
		<tr><td colspan="2">
			<div class="readme">
			  <h2>说明：</h2>
			  <div class="readcon">
			    <ul>
			      <li>只能导入zip格式的压缩文件（不能直接使用rar修改后缀为zip）。</li>
			      <li>照片必须以学号或身份证号命名。</li>
			      <li>照片的格式必须为"jpg，gif，png或bmp"。</li>
			      <li>单张照片大小请不要超过150k,zip包大小不要超过50M。</li>
			      <li>如果学生的照片已存在，会将该生的照片覆盖。</li>
			    </ul>  
			  </div>
			</div>
			</td></tr>
		</tbody>
	</table>
	</s:form>
	</div>
	 

</body>
</html>