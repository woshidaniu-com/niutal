<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
</head>
<s:form name="ajaxForm" id="ajaxForm" cssClass="form-horizontal sl_all_form"  action="" method="post" theme="simple">
 	<s:iterator value="colList" id="col">
 	  <s:if test="#col.type=='hidden'">
 	 	 <input type="hidden" id="<s:property value="name"/>" name="<s:property value="name"/>" value="<s:property value="value"/>"/>
 	  </s:if>
 	  <s:elseif test="#col.type=='text'">
	  		<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<p class="form-control-static"><s:property value="value"/></p>
					</div>
				</div>
			</div>
	  		</div>
 	  </s:elseif>
 	  <s:elseif test="#col.type=='password'">
 	  		<s:if test="value != null and value != ''">
 	  			<div class="row">
		  			<div class="col-md-12 col-sm-12">
						<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
							<label for="" class="col-sm-3 control-label">
					        	<s:property value="label"/>
							</label>
							<div class="col-sm-8">
								<p class="form-control-static"><s:property value="value"/></p>
							</div>
						</div>
					</div>
		  		</div>
 	  		</s:if>
 	  </s:elseif>
 	</s:iterator>
</s:form>
</html>