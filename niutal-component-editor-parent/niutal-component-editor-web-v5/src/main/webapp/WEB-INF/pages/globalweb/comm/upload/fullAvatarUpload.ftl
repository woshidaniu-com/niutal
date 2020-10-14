<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
</head>
<body>
<!-- 
    富头像上传编辑器是一款支持本地上传、预览、视频拍照和网络加载的flash头像编辑上传插件，可缩放、裁剪、旋转、定位和调色等...
    小巧的身材：文件大小仅仅只有 48 KB，而新浪的头像编辑器组件有 58.1 KB，更有甚者达 80 KB 之多。 
    漂亮的外观：灰色系的外观设计，适用于所有风格的站点。还可轻松地实现换肤。 
    强大的功能：原图支持本地上传、视频拍照和网络加载，图片编辑有缩放、裁剪、旋转、定位和调色等功能。 
    丰富的接口：可自定义头像（大小、数目...）、所有文本（包括其字体，大小，颜色，方便扩展多语言站点）、可在外部接口中定义选项卡、按钮... 
    跨平台兼容：运行环境Flash Player 10.1+，任何浏览器 IE All、Firefox、 Chrome、Opera...都正常使用。 
    极致的体验：人性化的设计方式，细节上精心的处理，用户使用起来得心应手。 
 -->
<form class="form-horizontal sl_all_form" method="post" theme="simple" enctype="multipart/form-data">		    	
    <div class="row">
		<div class="col-sm-12 col-md-12" style="min-height: 300px;">
			<ul class="nav nav-tabs" id="avatar-tab">
                <li class="active" id="upload"><a href="javascript:;">本地上传</a> </li>
            </ul>
			<div id="flash1" class="margin-t10">
 				<div id="swf_container" style="display:none" class="col-sm-12 col-md-12 ">
					<h3 class="algin-center">本组件需要安装Flash Player后才可使用 !</h3>
					</br>
		        	<p style="line-height: 2.5">
			        	你可以去<a href="http://www.adobe.com/go/getflashplayer">Adobe官方地址</a>&nbsp;下载安装。
			        	或者去<a href="http://rj.baidu.com/search/index/?kw=Adobe%2520Flash%2520Player">百度软件中心</a>&nbsp;下载安装。
			        	或者从系统下载。
		        	</p>
		        	<div class="thumbnail" style="margin-bottom: 5px;padding: 0px;">
			          	<div class="caption">
			            	<h5><a href="http://www.adobe.com/go/getflashplayer">Adobe Flash Player for IE </a>&nbsp;（ 大小：16.8M&nbsp;版本：17.0.0.134&nbsp;位数：32/64）</h5>
			            	<p style="line-height: 1.5;font-size: 12px;margin-top: 5px;">Adobe Flash Player for IE 是IE浏览器专用的flash播放器插件,可以播放Adobe Flash制作的flash文件,运行时，它可以跨浏览器和操作系统、原汁原味地呈现具有表现力的应用程序、内容和视频，功能强大，兼容性高。</p>
			          	</div>
			        </div> 
		        	<div class="thumbnail" style="margin-bottom: 5px;padding: 0px;">
			          	<div class="caption">
			            	<h5><a href="http://www.adobe.com/go/getflashplayer">Adobe Flash Player Plugin（非IE内核） </a>&nbsp;（ 大小：17.4M&nbsp;版本：17.0.0.134&nbsp;位数：32/64）</h5>
			            	<p style="line-height: 1.5;font-size: 12px;margin-top: 5px;">适用于Firefox/Safari/Opera等非IE内核浏览器的FALSH插件，功能强大，兼容性高。</p>
			          	</div>
			        </div>
			        <div class="thumbnail" >
			          	<div class="caption"> 
			            	<h5><a href="http://www.adobe.com/go/getflashplayer">Adobe Flash Player Plugin MAC版 </a>&nbsp;（大小：0.9M&nbsp;版本：12.0.0.17&nbsp;位数：32/64）</h5>
			            	<p style="line-height: 1.5;font-size: 12px;margin-top: 5px;">适用于Firefox/Safari/Opera等非IE内核浏览器的FALSH插件，功能强大，兼容性高。</p>
			          	</div>
			        </div>
			  	</div>
			</div>
		</div>
	</div>           
<form>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/fullavatareditor.ini"%>
<script type="text/javascript">	
var swf_object = null;
jQuery(function($){	
	var times_1 = 0;
	var interval_1 = window.setInterval(function(){
		if(times_1 >= 10){
			$("#swf_container").show();
			window.clearInterval(interval_1);
		}
		if(swfobject){
			
			swfobject.addDomLoadEvent(function () {
				
			     //初始化图片选择控件
			     swf_object = $.fullAvatarEditor({
			    	//用以包裹Flash的HTML元素的ID
				    "container" 	: 'swf_container', 
			    	//flash文件的宽度
			 		"width"			: 630,
			 		//flash文件的高度
			 		"height"		: 335,
			    	 //插件主swf文件的路径，文件名必须是FullAvatarEditor.swf。
					"file"			: _path + "/js/plugins/fullavatareditor/fullAvatarEditor.swf",
					 //expressInstall.swf文件的路径，文件名必须是expressInstall.swf。
					"expressInstall":  _path + "/js/plugins/fullavatareditor/expressInstall.swf",
					 //flashvars 配置参数（object，必须）
					"flashvars"		: {
				    	//接收消息的swf的ID，用以区分同一页面如果存在多个组件。
					    id			: 'swf_container',

					    //------------上传路径参数---------------------
					  
					    //上传图片的接口。该接口需返回一个json字符串，且会原样输出到 callback 回调函数 的参数对象的属性content中
				        upload_url 			: '${upload_url}',
				        //上传提交的方式，值为 get 或 post，不分大小写。
				        method				: "post",
				        //生成的头像图片的质量，取值范围1-100，数值越大生成的图片越清晰，相对地文件也会越大。
				        quality				: ${quality},
				        
						//------------原图片参数---------------------
						
				        //默认加载的原图片的url
				        src_url 			: "${src_url}",
				        //选择的本地图片文件所允许的最大值，必须带单位，如888Byte，88KB，8MB			
				        src_size			: "${src_size}",
				      	//是否上传原图片的选项：2-显示复选框由用户选择，0-不上传，1-上传,2 -显示复选框由用户选择	
				        src_upload 			: ${src_upload}||0,	
						//当选择的原图片文件的大小超出指定最大值时的提示文本。可使用占位符{0}表示选择的原图片文件的大小。
				        src_size_over_limit : '选择的文件大小（{0}）超出限制（${src_size}）\n请重新选择',
				        //------------选项卡参数---------------------
				        
				      	//不显示选项卡，外部自定义
				        tab_visible 		: false,

			   			//------------图片选择框	参数---------------------
			   			
			   			//图片选择框的水平对齐方式。left：左对齐；center：居中对齐；right：右对齐；数值：相对于舞台的x坐标
				        browse_box_align 	: 'left',
				        //图片选择框的宽度。	
				        browse_box_width 	: 300,
				        //图片选择框的高度。
				        browse_box_height	: 300,
				        
				      	//------------按钮和复选框参数---------------------
				      	
				      	//不显示按钮，外部自定义
				        button_visible 		: false,			
				      	//不显示复选框，外部自定义
				        checkbox_visible 	: false,	
				        		
				      	//------------ 摄像头相关参数---------------------
				      
				      	//摄像头拍照框的水平对齐方式，如上。			
						webcam_box_align 	: 38,	
						//头像拍照框对齐方式      
				        webcam_box_align	: 'left', 

				        //------------处理后的头像参数---------------------
				        
						//定义头像尺寸：表示一组或多组头像的尺寸。其间用"|"号分隔。
						avatar_sizes 		: '${avatar_sizes}',			
						//头像尺寸的提示文本。多个用"|"号分隔，与上一项对应
						avatar_sizes_desc 	: '${avatar_sizes_desc}',	
						//头像的表单域名称，多个用"|"号分隔，与 avatar_sizes 项对应。
						avatar_field_names	: '${avatar_field_names}',  
						//头像简介
						avatar_intro 		: '最终会生成以下尺寸的头像，请注意是否清晰',
						//是否显示头像颜色调整工具。
						avatar_tools_visible: true
						
					},
			    	 
					"onEvent" 	: function (json) {
				        switch (json.code) {
				        	//选择的原图片文件大小超出了指定的值
				            case 4:{
				                $.error("您选择的原图片文件大小（" + json.content + "）超出了指定的值(${src_size})。");
				            };break;
				            case 5:{
				            	switch (json.type) {
				            		//表示图片上传成功
					            	case 0:{
					            		$.closeModal("FullAvatarUpload");
						            };break;
				            		//表示图片上传失败，失败原因由上传接口定义！
					            	case 1:{
						            	////will output:头像上传失败，原因：上传的原图文件大小超出限值了！
					            		$.error('上传失败，原因：' + json.content.msg);
						            };break;
						           	// 表示图片上传失败，指定的上传地址不存在或有问题！
					            	case 2:{
						                $.error("头像上传失败，原因：指定的上传地址不存在或有问题!");
						            };break;
						            //表示图片上传失败，发生了安全性错误！
					            	case 3:{
						                $.error("头像上传失败，原因：发生了安全性错误！请联系站长添加crossdomain.xml到网站根目录。");
						            };break;
				            	}
				            };break;
				        }
				    }
			     });
			     
			});
			window.clearInterval(interval_1);
		}
		times_1 += 1;
	}, 1000);
	
	
});
</script>
</html>