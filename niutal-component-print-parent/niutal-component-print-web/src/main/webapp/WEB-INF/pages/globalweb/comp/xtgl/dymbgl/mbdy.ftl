<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/head_v5.ftl" /]
		[#include "/globalweb/comp/xtgl/dymbgl/head.ftl" /]
		[#include "/globalweb/head/niutal-ui-ruler.ftl" /]
		<title>模板打印</title>
		<style type="text/css" media=print>
			.noPrin { display: none; }
		</style>
		<script type="text/javascript">
		jQuery(function($){
			var regu = /[{]{1}[a-z]+.[a-z]+[}]{1}/g;
			var re = new RegExp(regu);
			var macths = $("#nr").val().match(re);
			var list = eval($("#yhglList").val());
			for(var i = 0; i < list.length; i ++){
				var model = list[i];
				var html = $("#nr").val();
				for(var j = 0; j < macths.length; j ++){
					var value = eval('(' + macths[j].substring(1, macths[j].length-1) + ')');
					html = html.replace(macths[j], value);
				}
				$("#contenttext").append(html);
				if(i != list.length - 1){
					$("#contenttext").append("<div style='page-break-after: always;'></div>");
				}
				//console.log($("#contenttext").html());
			}
			$(".edit-block.ui-sortable").each(function(i, o){
				$(o).find(".field.data").each(function(ii, oo){
					//console.log($(oo).html());
					//console.log($(oo).parent().html());
					//console.log($(oo).next("input").next("input").val());
					$(oo).closest(".text-module").css("color", "black");
					if($(oo).next("input").next("input").val() != "undefined" && $(oo).next("input").next("input").val() != undefined){
						$(oo).text($(oo).next("input").next("input").val());
					}else{
						$(oo).text("");
					}
				});
			});
			jQuery("#printBut").fadeIn("slow");
			jQuery(window).scroll(function(){
				var scrTop = jQuery(document).scrollTop();
				if(scrTop > 200){
					jQuery("#gotoTop").fadeIn("slow");
				}else{
					jQuery("#gotoTop").fadeOut("slow");
				}
			});
			PageSetup_Null();
		});
		
		function goToUp(){
			jQuery(window).scrollTop(0);
		}
		
		function printlxd(){
			window.print();
		}
		
		//通用打印事件
		function Print() {  
			//document.all.eprint.InitPrint();
		  	//document.all.eprint.SetMarginMeasure(1);//1mm是default, 2 inch
		  	document.all.eprint.orientation=1; //1:纵向，2：横向
		  	//document.all.eprint.marginTop=10;
		  	//document.all.eprint.marginLeft=6;
		  	//document.all.eprint.marginRight=5;
		 	//document.all.eprint.marginBottom=5;
		 	document.all.eprint.header = "";
		  	document.all.eprint.footer = "";
		  	//document.all.eprint.pageWidth = 197mm; 
		  	//document.all.eprint.pageHeight = 293mm;
		  	//document.all.eprint.paperSize = "16K";
		  	document.all.eprint.Print(true);//直接打印
		  	window.close();
		}

		var HKEY_Root, HKEY_Path, HKEY_Key;
		HKEY_Root = "HKEY_CURRENT_USER";
		HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
		//设置网页打印的页眉页脚为空 
		function PageSetup_Null() {
			try {
				var Wsh = new ActiveXObject("WScript.Shell");
				HKEY_Key = "header";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
				HKEY_Key = "footer";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
				HKEY_Key = "margin_bottom";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");
				HKEY_Key = "margin_left";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
				HKEY_Key = "margin_right";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
				HKEY_Key = "margin_top";
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");
			} catch (e) {
				//alert(e);
			}
		}
		</script>
		<style>
		body {
			margin: 0;
			padding: 0;
			text-align: center;
			font-family: "宋体";
			font-size: 24px;
			text-align: center;
		}
		
		span,img {
			margin: 0;
			padding: 0;
			line-height: 22px;
		}
		</style>
	</head>

	<body>
		<input type="hidden" id="nr" value='${dymbglModel.nr}'/>
		<input type="hidden" id="yhglList" value='${yhglList}'/>
		<div id="contenttext"></div>
		<div class="noPrin" style="background-color:none;z-index: 10;position: fixed;width: 100%;position: fixed;bottom: 80px;" align="right">
			<div style="margin-right: 20%;width:120px;height:210px;" align="center">
				<div style="height: 80px;">
					<img id="gotoTop" alt="返回顶部" title="返回顶部" width="80px" height="80px" style="cursor: pointer;display: none;" src="${messageUtil("system.stylePath")}/assets/images/print/up.png" onclick="goToUp()">
				</div>
				<img id="printBut" alt="打印" title="打印" width="100px" height="100px" style="cursor: pointer;display: none;bottom: 0px;" src="${messageUtil("system.stylePath")}/assets/images/print/printBtn.png" onclick="printlxd()">
			</div>
		</div>
	</body>
</html>