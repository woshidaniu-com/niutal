<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>	
<script type="text/javascript">			
	function returnData(funcCallBack) {
		var tgzt = jQuery("#tgzt").val();				
		if(tgzt!='pass'){
			if(jQuery("#suggestion").val()==''){
				$.alert('请填写审核意见！');
				return false;
			}
		}
		
		var data = {};					
		data.tgzt = tgzt;       
		data.suggestion = jQuery("#suggestion").val();		
		funcCallBack.call(this, data);
	}
</script>
</head>
<body>		
	<s:form id="plshForm" method="post" cssClass="form-horizontal sl_all_form" theme="simple">
		<div style="margin-right: 15px">
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							<span class="red">*</span>审核结果
						</label>
						<div class="col-sm-8">
							<s:select id="tgzt" name="tgzt" cssClass="form-control chosen-select" list="#{'pass':'通过','unpass':'不通过'}"/>
							<SCRIPT type="text/javascript">
					    		jQuery('#tgzt').trigger("chosen");
					    	</SCRIPT>
						</div>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							审核意见
						</label>
						<div class="col-sm-8">
							<s:textarea name="suggestion" id="suggestion" cssClass="form-control" cssStyle="height:auto"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>
</html>