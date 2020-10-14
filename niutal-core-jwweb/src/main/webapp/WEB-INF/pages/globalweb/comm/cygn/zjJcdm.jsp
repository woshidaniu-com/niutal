<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
</head>
<body>
<s:form name="ajaxForm" id="ajaxForm" cssClass="form-horizontal sl_all_form"  action="/cygn/jcdm_zjBcjcdm.html" method="post" theme="simple">
 	<!-- 防止浏览器自动填充密码:增加 class="ignore"忽略校验-->
	<input type="text" class="ignore" style="display: none;" /> 
	<input type="password" class="ignore" style="display: none;" />
 	<input type="hidden" id="table" name="table" value="${table}"/>
 	<s:iterator value="colList" id="col">
 	  <s:if test="#col.type=='hidden'">
 	 	 <input type="hidden" id="<s:property value="name"/>" name="<s:property value="name"/>" value="<s:property value="value"/>"/>
 	  </s:if>
 	  <s:elseif test="#col.type=='text'">
	  		<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<input type="text" id="<s:property value="name"/>" name="<s:property value="name"/>"  class="form-control"
			       		 value="<s:property value="value"/>" 
				       		 <s:if test="validateMap!=''">
				       		  	validate="<s:property value="validateMap"/>"
				       		 </s:if>
			       		 />
			       		 <s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		 </s:if>
					</div>
				</div>
			</div>
	  		</div>
 	  </s:elseif>
 	  <s:elseif test="#col.type=='date'">
 	  	<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<input type="text" id="<s:property value="name"/>" name="<s:property value="name"/>" 
			       		placeholder="点击选择日期或时间" onfocus="WdatePicker({dateFmt:'<s:property value="dateFmt"/>'})"
			       		class="form-control" value="<s:property value="value"/>" readonly="readonly"/>
			       		 <s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		 </s:if>
					</div>
				</div>
			</div>
  		</div>
 	  </s:elseif>
 	  <s:elseif test="#col.type=='radio'">
 	   	<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						 <s:iterator value="#col.boxList" id="sub" status="st">
						 	<label class="radio-inline">
			       			<s:if test="#col.value==value or #col.value==#sub.key ">
			       				<input class="ignore" type="radio" value="<s:property value="key"/>" name="<s:property value="name"/>" checked="checked"/><s:property value="value"/>
			       			</s:if>
							<s:elseif test="#st.index==0">
								<input class="ignore" type="radio" value="<s:property value="key"/>" name="<s:property value="name"/>" checked="checked"/><s:property value="value"/>
							</s:elseif>
			       			<s:else>
			       				<input class="ignore" type="radio" value="<s:property value="key"/>" name="<s:property value="name"/>"  /><s:property value="value"/>
			       			</s:else>
			       			</label>
			       		</s:iterator>
			       		 <s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		 </s:if>
					</div>
				</div>
			</div>
	  		</div>
 	  	</s:elseif>
 	  	<s:elseif test="#col.type=='textarea'">
 	   	<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<textarea id="<s:property value="name"/>" name="<s:property value="name"/>"
		       		 	<s:if test="validateMap!=''">
				       		  	<%--validate="<s:property value="validateMap"/>" 添加时textarea框的提示语句--%>
				       	</s:if>
		       		 	class="form-control" style="height:auto" ><s:property value="value"/></textarea>
		       		 	 <s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		 </s:if>
					</div>
				</div>
			</div>
	  		</div>
 	 	</s:elseif>
 	  	<s:elseif test="#col.type=='select'">
 	  	<div class="row">
	  		<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<select id="<s:property value="name"/>" name="<s:property value="name"/>" class="form-control chosen-select"
							<s:if test="validateMap!=''">
				       		  	validate="<s:property value="validateMap"/>"
				       		</s:if>
						>
		       		 		<option value="">--请选择--</option>
		       		 		<s:iterator value="#col.selectList" id="sub" status="st">
		       		 			<option value="<s:property value="#sub.ID"/>"><s:property value="#sub.MC"/></option>
		       		 		</s:iterator>
			       		</select>
			       		<s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		</s:if>
			       		<script type="text/javascript">
			       			jQuery("#${name}").trigger('chosen');
			       		</script>
					</div>
				</div>
			</div>
	  	</div>
 	 	</s:elseif>
 	 	<s:elseif test="#col.type=='password'">
	  		<div class="row">
	  			<div class="col-md-12 col-sm-12">
				<div class="form-group" <s:if test="desc != null">style="margin-bottom: 5px;"</s:if>>
					<label for="" class="col-sm-3 control-label">
						<s:if test="Nullable==false">
			        		<span style="color:red;">*</span>
			        	</s:if>
			        	<s:property value="label"/>
					</label>
					<div class="col-sm-8">
						<input type="password" id="<s:property value="name"/>" name="<s:property value="name"/>"  class="form-control"
			       		 value="<s:property value="value"/>" 
				       		 <s:if test="validateMap!=''">
				       		  	validate="<s:property value="validateMap"/>"
				       		 </s:if>
			       		 />
			       		 <s:if test="desc != null">
			       			<p class="form-control-static smaller-80 red" style="padding-top: 2px;padding-bottom: 2px;">注：<s:property value="desc"/></span>
			       		 </s:if>
					</div>
				</div>
			</div>
	  		</div>
 	  </s:elseif>
 	</s:iterator>
</s:form>
</body>
<script type="text/javascript">
	//扩展自己的方法
	jQuery(function($) {
		$("#ajaxForm").validateForm({
			beforeValidated:function(){
				//验证字段是否唯一
				var isValidate = true;
				jQuery.ajax({
					url		: _path+'/cygn/jcdm_cxUniqueValidate.html?doType=insert',
					async	: false,
					type	: "post",
					dataType: "json",
					data	: $("#ajaxForm").serialize(),					
					success	: function(data){
						if(data!="-1"){
							isValidate = false;
							$.alert(data);
						}
					}
				});
				return  isValidate;
			}
		});
	});
</script>
</html>