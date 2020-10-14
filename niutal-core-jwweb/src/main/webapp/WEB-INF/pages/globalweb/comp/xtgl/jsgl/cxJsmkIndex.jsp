<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
<!-- Custom CSS -->
<link rel="stylesheet" type="text/css" href="<%=stylePath%>/css/zygl/sb-admin.css?ver=<%=cssVersion%>" />
<link rel="stylesheet" type="text/css" href="<%=stylePath%>/css/zygl/tree.css?ver=<%=cssVersion%>" />
<style type="text/css">
#container_fluid label {
	display: inline-block;
	/* margin-bottom: 5px; */
	font-weight: bold;
	max-width: 100%;
    margin-left: 5px;
}

#container_fluid  .form-control {
	display: inline;
	width: 70%;
	height: 34px;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.42857143;
	color: #555;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
		ease-in-out .15s;
	-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
		.15s;
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}

#container_fluid .help-block {
	display: inline;
	margin-top: 5px;
	margin-bottom: 10px;
	color: #737373;
}
</style>
</head>
<html>
<body>
<s:if test="sfejsqFlag == 0">
<div class="row" style="padding-top: 0px;">
	<div class="col-md-12 col-sm-12">
		<div class="alert alert-error align-center bigger-180 red " style="margin:160px 0px;padding: 15px 150px ;">
	    	当前登录角色无二级授权权限，如有多个角色，可切换其他角色再次尝试！
	    </div>
	</div>
</div>
</s:if>
<s:else>
<div class="container-fluid" id="container_fluid">
	<div class="tree well " style="height: 500px;overflow-x:auto;">
		<ul>
			<li>
				<span><i class="icon-folder-open"></i> 功能菜单</span>
				<ul>
					<s:iterator value="jsgnList[0]" status="status" var="val0">
						<li>
							<span><s:property value="#val0.yjgnmkmc"/></span>
							<ul>
								<s:iterator value="jsgnList[1]" var="val1">
									<s:if test="#val0.yjgnmkdm==#val1.yjgnmkdm">
										<li data-gnmkdm="<s:property value="#val1.ejgndm"/>" class="ejgndm_li">
											<span><s:property value="#val1.ejgnmc"/></span>
											<s:iterator value="jsgnList[2]" var="val2">
												<s:if test="#val1.ejgndm==#val2.ejgndm">
													| <label style="cursor: pointer;">
														<s:if test="#val2.sfxz=='checked'">
															<input name="gnczdm" value="<s:property value="#val2.czdm"/>" checked="checked" type="checkbox">
														</s:if>
														<s:else>
															<input name="gnczdm" value="<s:property value="#val2.czdm"/>" type="checkbox">
														</s:else>
														<s:property value="#val2.czmc"/>
													</label>
												</s:if>
											</s:iterator>
										</li>
									</s:if>
								</s:iterator>	
							</ul>
						</li>
					</s:iterator>
				</ul>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
 //为节点添加展开，关闭的操作
$(function(){
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '折叠节点');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', '收起节点').find(' > i');//.addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', '折叠节点').find(' > i');//.addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
});
</script>
</s:else>
</body>
</html>
