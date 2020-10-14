<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/wdyy.js?ver=<%=jsVersion%>"></script>
		<script type="text/javascript">
			//modify by wgx 修改jqgrid 缩放和展开时没缩放和展开的BUG
			function changeWin() {
				var left = jQuery("#left");
			
				if (left.attr("class") != "hide") {
					jQuery("#left").attr("class","hide");
					jQuery("#leftBotton").css("display","none");
					jQuery("#rightBotton").css("display","");
					jQuery("#right").css("width","100%");
				} else {
					jQuery("#left").attr("class","typeleft floatleft");
					jQuery("#leftBotton").css("display","");
					jQuery("#rightBotton").css("display","none");
					jQuery("#right").css("width","803px");
				}
				var rightWidth = jQuery(".formbox:eq(0)").innerWidth() - 2;
				jQuery("#tabGrid").setGridWidth(parseInt(rightWidth));
				jQuery("table.tabGrid").setGridWidth(parseInt(rightWidth));
			}
			
			//二级菜单切换
			function showhidediv(o,id){
				
				jQuery(".close ~ul").css("display","none");
				jQuery(".close").attr("class","open");
				
				jQuery(o).attr("class","close");
				jQuery(".close ~ul").css("display","");
			}	

			//定位菜单
			function goToMenu(){
				var curmkdm=jQuery("#curmkdm").val();
				var jqMenu=jQuery("#zj_" + curmkdm);
				if(curmkdm == null || curmkdm == "" || jqMenu.length == 0){
					//显示第一个菜单
					jqMenu.parents(".textlink").find("h3").click();//一级菜单展开
					jQuery(".open_03",jQuery("#left")).eq(0).click();
				}else{
					//显示定位菜单
					jqMenu.parents(".textlink").find("h3").click();//一级菜单展开
	
					var _curMenu = jqMenu.parent().find(".open_03");

					jQuery(".textlink").find("li").removeClass();
					_curMenu.parent("li").addClass("current");

					var menuUrl = _curMenu.find("input[name='menuUrl']").val();
					var params = jQuery("#params").val();
					if(params != null && params != ""){
						menuUrl += "?";
						menuUrl += parseParams(params);
					}
					refRightContent(menuUrl);
				}				
			}

			function parseParams(params){
				var paramStr = "";
				if(params == null || params == ""){
					return paramStr;
				}
				var paramsArr = params.split(",");
				if(paramsArr == null || paramsArr.length == 0){
					return paramStr;
				}
				for ( var i = 0; i < paramsArr.length; i++) {
					var param = paramsArr[i];
					if(param == null || param == ""){
						continue;
					}
					var paramArr = param.split(":");
					if(paramArr == null || paramArr.length < 2){
						continue;
					}
					if(paramStr != ""){
						paramStr += "&";
					}
					paramStr += paramArr[0] + "=" + paramArr[1];
				}
				return paramStr;
			}

			//异步加载--我的应用
			jQuery(function() {
				var fjgndm=jQuery("#fjgndm").val();
				var cddm={"fjgndm":fjgndm};
				var urlRoot="<%=systemPath %>";
				jQuery.ajaxSetup({async : false});
				jQuery.ajax({
					url:urlRoot+"/xtgl/wdyy_cxWdyy.html",
					type: "post",
					dataType:"json",
					data:cddm,
					success:function(wdyyList){	
						var htmls="";
						var cdBh="";
						if(wdyyList.length>0){
							for(var i=0;i<wdyyList.length;i++){						
								var path = urlRoot+wdyyList[i]["dyym"];
								var menuUrlHtml = "<input type='hidden' name='menuUrl' value='"+path+"'/>";
								htmls += "<li>";
								htmls += "<a href='javascript:void(0);' onclick=\"setCurrentMenu(this);\" class='open_03'><span>"+wdyyList[i]["gnmkmc"]+"</span>";
								htmls += menuUrlHtml;
								htmls += "</a>";
								htmls += "<a href='javascript:void(0);' class='cygn_delete' id='sc_"+wdyyList[i]["gnmkdm"]+"' class='cygn_add' onclick='scAn(this);return false;'></a>";
								htmls += "</li>";
								cdBh=wdyyList[i]["gnmkdm"];
								cshAn(cdBh);
							}
						}else{
							htmls=htmls+"<li id='zwszyy'>"+
					 					"<span>暂未设置应用</span>"+
		 				 		"</li>";
						}
						var wdyyObj=jQuery("#wdyyCd");
						wdyyObj.children().remove();
						wdyyObj.append(htmls);
					}
				
				});
                //点击一级菜单后，内容展现div默认加载左边菜单第一个功能 
			//	jQuery(".open_03",jQuery("#left")).eq(0).click();
				goToMenu();
			
				jQuery.ajaxSetup({async : true});
			});	
		</script>
	</head>
	<body>
    <input type="hidden" name="params" id="params" value="${params }"/>
	
		<div class=" ">

			<div class="type_mainframe">
						<!-- 菜单区域 -->
				<div class="typeleft floatleft" id="left">
					<jsp:include page="leftMenu.jsp" />
				</div>
		
				<div class="btn_hide_on" id="leftBotton">
					<button onclick="changeWin();return false;" id="changeid" type="button"></button>
				</div>
				<div class="btn_hide_off" style="display: none;" id="rightBotton">
					<button onclick="changeWin();return false;" type="button"></button>
				</div>
		
				<div class="typeright floatright" id="right">
					<div class="typecon" id="rightContent">
						<!--内容区主体-->
					</div>
				</div>
			</div>

		</div>	
	
	
	

	</body>
</html>
