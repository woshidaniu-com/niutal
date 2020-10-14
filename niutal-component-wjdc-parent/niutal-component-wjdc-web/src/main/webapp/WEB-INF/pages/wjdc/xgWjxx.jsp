<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/My97DatePicker/WdatePicker.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
		var jsyeditor,jwyeditor;
		
		function save(){
			 //jQuery("#dwjj").val(editor.html());
			 
			 if (inputResult() && checkInputNotNull('wjmc!!wjlx')){ 
			 	if(jQuery("#wjlx").val()=="CPL"&&!checkInputNotNull("wjzf")){
			 		return false;
			 	} 
			 	
			 	 jQuery("#gqsj").val(jQuery("#gqsj").val().replace(/-/g,''));
			 	
			 	 jQuery('#jsy').val(jsyeditor.html());
				 jQuery('#jwy').val(jwyeditor.html())
				 subForm(_path+'/zfxg/wjdc/wjgl_xgBcWjxx.html');
			 }
		}
		
		
		function wjlxChange(){
			var wjlx=jQuery("#wjlx").val();
			if(wjlx=="CPL"){
				jQuery("#th_span_wjzf").css("display","");
				jQuery("#td_span_wjzf").css("display","");
			}else{
				jQuery("#wjzf").val("");
				jQuery("#th_span_wjzf").css("display","none");
				jQuery("#td_span_wjzf").css("display","none");
			}
		}
		
		jQuery(function(){
			wjlxChange();
			jsyeditor = KindEditor.create('textarea[name="jsy"]', simpleOption2);
			jwyeditor = KindEditor.create('textarea[name="jwy"]', simpleOption2);
		}); 
		</script>
	</head>
	<body>
	<div class="tab_cur">
		<p class="location">
			<em>您的当前位置:</em><a>问卷调查 - 修改问卷</a>
		</p>
	</div>
	<s:form method="post" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>问卷信息</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();" type="button">保 存</button>
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	      	 <th width="15%"><span class="red">*</span>问卷名称</th>
	         <td colspan="3">
	         	<s:hidden name="wjid"></s:hidden>
		        <s:textfield maxlength="20" name="wjmc" id="wjmc" cssStyle="width:76%"></s:textfield> 
	         </td>
	      </tr>
	      <tr>
	      	 <th width="15%"><span class="red">*</span>发布类型</th>
	         <td width="35%">
		        <s:select list="wjfblxList" listKey="DM" listValue="MC"  name="fblx" id="fblx" cssStyle="width:40%"></s:select>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="15%">过期时间</th>
	         <td colspan="3">
		        <s:textfield  maxlength="20" name="gqsj" id="gqsj" cssStyle="width:40%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" ></s:textfield> 
	         	<font color="red">(提示：不设置表示该问卷不会过期)</font>
	         </td>
	      </tr>
	      <tr style="display: none;">
	        <th width="15%"><span class="red">*</span>问卷类型</th>
	        <td width="35%">
		    <!--   <s:select list="wjlxList" listKey="DM" listValue="MC" headerKey="" headerValue="请选择" name="wjlx" id="wjlx" cssStyle="width:40%" onchange="wjlxChange();"></s:select> -->  
		        <s:select list="wjlxList" listKey="DM" listValue="MC"  name="wjlx" id="wjlx" cssStyle="width:40%" onchange="wjlxChange();"></s:select>
	        </td>
	      	<th>
	      		<span id="th_span_wjzf"><span class="red">*</span>问卷总分</span>
	      	</th>
	      	<td width="35%">
	      		<span id="td_span_wjzf">
	      		<s:textfield maxlength="3" name="wjzf" id="wjzf" cssStyle="width:40%" ></s:textfield>
	      		</span>
	      	</td>
	      </tr>
	      <tr style="display: none;">
	      	<th width="15%"><span class="red">*</span>单行选项个数</th>
	      	<td>
	      		<s:textfield maxlength="2" name="dags" id="dags" cssStyle="width:40%" ></s:textfield>
	      	</td>
	      	<th></th>
	      	<td>
	      	</td>
	      </tr>
	    	<tr>
	        	<th colspan="1"><span>卷首语</span></th>
	    		<td colspan="3">
	    			<s:textarea name="jsy" id="jsy" cssStyle="width:100%;height:50px" maxlength="250"></s:textarea>
	    		</td>
	        </tr>
	    	<tr>
	        	<th colspan="1"><span>卷尾语</span></th>
	    		<td colspan="3">
	    			<s:textarea name="jwy" id="jwy" cssStyle="width:100%;height:50px" maxlength="250"></s:textarea>
	    		</td>
	        </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script defer="defer">
  		alert('${result}','',{'clkFun':function(){
  			refershParent();
  		}});
  	</script>
  </s:if>
</s:form>
</body>
</html>