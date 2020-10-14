<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
<style type="text/css">
.has-error .ke-container {
	border-color: #a94442;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
}
</style>
</head>
<body style="background: #fff;">
	<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/xtgl/xwgl_xgBcXw.html"
		theme="simple">
		<s:hidden id="xwbh" name="xwbh" />
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">
						通知标题
					</label>
					<div class="col-sm-9">
						<s:textfield id="xwbt" name="xwbt" maxlength="100"
		           			cssClass="form-control" validate="{required:true,stringMinLength:6,stringMaxLength:100}" ></s:textfield>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>发布时间</label>
		          <div class="col-sm-5">
		            <s:textfield id="fbsj" name="fbsj" readonly="true" placeholder="点击选择时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'%{currentDate}'})"
		              validate="{required:true}" cssClass="form-control Wdate"></s:textfield>
		          </div>
		          <div class="col-sm-4">
		          		<div class="bg-warning" style="line-height:33px"><p class="">&gt;&gt;通知发布后最早可以查询到通知的时间!</p></div>
		          </div>
		        </div>
		      </div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						发布对象
					</label>
					<div class="col-sm-9">
						<s:select cssClass="form-control chosen-select" validate="{required:true}"
							list="#{'0':'全校','1':'学生','2':'教师','3':'岗位'}" listKey="key"
							listValue="value" onchange="selectObj(this);" id="mxdxlb"
							name="mxdxlb"></s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#mxdxlb').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
		</div>
		<div class="row"  id="xzdx-div" <s:if test="mxdxlb == 0"> style="display: none;"</s:if>>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						<span style="color: red;">*</span>选择面向对象
					</label>
					<div class="col-sm-9">
						<div class="input-group">
							<input type="hidden" name="groupId" id="groupId">
							<input type="text" id="_mc" name="_mc" value="${xzlb}"
								class="form-control" readonly="readonly" placeholder="选择面向对象" />
							<span class="input-group-btn">
								<button class="btn btn-default" id="selectID" type="button">&gt;</button> </span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">

				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						是否发布
					</label>
					<div class="col-sm-9">
						<s:iterator value="sfList">
							<s:if test="model.sffb == key">
								<label class="radio-inline">
									<input type="radio" name="sffb" checked="checked"
										validate="{required:true}" value="<s:property value="key"/>" class="ignore" />
									<s:property value="value" />
								</label>
							</s:if>
							<s:else>
								<label class="radio-inline">
									<input type="radio" name="sffb" validate="{required:true}"
										value="<s:property value="key"/>" class="ignore" />
									<s:property value="value" />
								</label>
							</s:else>
						</s:iterator>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						是否置顶
					</label>
					<div class="col-sm-9">
						<s:iterator value="sfList">
							<s:if test="model.sfzd == key">
								<label class="radio-inline">
									<input type="radio" name="sfzd" checked="checked"
										validate="{required:true}" value="<s:property value="key"/>" class="ignore"/>
									<s:property value="value" />
								</label>
							</s:if>
							<s:else>
								<label class="radio-inline">
									<input type="radio" name="sfzd" validate="{required:true}"
										value="<s:property value="key"/>" class="ignore" />
									<s:property value="value"/>
								</label>
							</s:else>
						</s:iterator>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						通知内容
					</label>
					<div class="col-sm-9" id="ke_control">
						<s:textarea cssStyle="width:100%;height:330px;display:none;" name="fbnr"
							id="fbnr" cssClass="form-control">&nbsp;</s:textarea>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xwgl/xgXw.js?ver=<%=jsVersion%>"></script>
<script language="javascript"> 
if ('${mxdxlb}' != '0') {
	$("#xzdx-div").show();
	$("#groupId").val("-1");
}
</script>
</html>
