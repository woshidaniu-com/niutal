var NOT_PAGE_COUNT = 100;// 不分页时显示的条数
var PAGE_COUNT = 10;// 分页时默认显示的条数

/**
 * 得到学期名称
 * 
 * @param xqdm
 * @param xqList
 * @return
 */
function getXqmc(xqdm, xqList) {
	var xqmc = xqdm;
	if (xqList == null || xqList.length == 0) {
		return xqmc;
	}
	for ( var i = 0; i < xqList.length; i++) {
		var o = xqList[i];
		var curXqdm = o.xqdm;
		if (xqdm == curXqdm) {
			xqmc = o.xqmc;
			break;
		}
	}
	return xqmc;
}

function setNullDataTip(sTip) {
	if (sTip == null) {
		sTip = "无更多记录";
	}
	if ($("#pullUp").length > 0) {
		$("#pullUp").remove();
	}
	var pullUpHtml = "<div id='pullUp'>";
	pullUpHtml += "<span>" + sTip + "</span>";
	pullUpHtml += "</div>";
	$("#wrapper .mainframe1").append(pullUpHtml);
}

// 点击显示下一页内容
function submitNextPage() {
	var pageIndex = parseInt($('#pageIndex').val());
	$('#pageIndex').val(pageIndex + 1);
}

/*
 * 得到分页参数
 */
function getPageParams() {
	var postData = {};
	var pageIndex = 0;
	var itemsPerPage = PAGE_COUNT;

	if ($("#pageIndex").length > 0) {
		pageIndex = $("#pageIndex").val();
	}

	itemsPerPage = PAGE_COUNT;
	$("#itemsPerPage").val(itemsPerPage);
	postData.pageIndex = pageIndex;
	postData.itemsPerPage = itemsPerPage;
	return postData;
}

/*
 * 生成分页隐藏代码
 */
function createPageHidDiv() {
	if ($("#pageHidDiv").length > 0) {
		return;
	}
	var pageHtml = "";
	pageHtml += "<div id='pageHidDiv' style='display:none;'>";
	pageHtml += "<input type='hidden' id='recordCount' name='recordCount' value='0'>";
	pageHtml += "<input type='hidden' id='itemsPerPage' name='itemsPerPage' value='"
			+ NOT_PAGE_COUNT + "'>";
	pageHtml += "<input type='hidden' id='pageIndex' name='pageIndex' value='0'>";
	pageHtml += "</div>";
	$("body").append(pageHtml);
}

function owlCarousel(owlBanner) {
	var owl_index_banner = $("#" + owlBanner);
	if (owl_index_banner.length > 0) {
		owl_index_banner.owlCarousel( {
			autoPlay : 5000,
			singleItem : true
		});
	}
}

/**
 * 跳转至我的应用页面
 * 
 * @param cdlb
 * @return
 */
function toWdyyPage(cdlb) {
	var url;
	if (cdlb == null || cdlb == "") {
		url = _path + "/";
	} else {
		url = _path + "/ydyx/wdyy_ydyxWdyy.do?cdlb=" + cdlb;
	}
	location.href = url;
}

function Quit() {
	window.close();
}

function GoBack() {
	history.back(-1);
}

// 添加动态列表
function appendList(dataList,callback) {
	var sHtml = createListHtml(dataList);
	if ($(document.body).find(".loadingDialog1").length > 0) {
		$(document.body).find(".loadingDialog1").remove();
	}
	$(document.body)
			.append(
					"<div class='loadingDialog1'>"
							+ sHtml
							+ "<p class='data_list1'><a href='javascript:;' onclick='remove_list()'>取消</a><a href='javascript:;' onclick='list_update("+callback+")'>确认</a></p></div><div class='fishDialogMask'></div>");
	$(".data_list a:last-child").css("border-bottom-width", "0px");
	$(".fishDialogMask").animate( {
		opacity : "0.5"
	});
	$(".loadingDialog1").animate( {
		opacity : "1"
	});
}

function createListHtml(dataList) {
	if (dataList == null) {
		return;
	}
	var sHtml = "";
	for ( var i = 0; i < dataList.length; i++) {
		var o = dataList[i];
		var curDataKey = o["key"];
		var curDataValue = o["value"];
		sHtml += "<a href='javascript:;' onclick='javascript:list_xz(this)'>"
				+ curDataValue;
		sHtml += "<input type='hidden' name='curDataKey' value='" + curDataKey
				+ "'>";
		sHtml += "</a>";
	}
	sHtml = "<p class='data_list' >" + sHtml + "</p>";

	return sHtml;
}

function remove_list() {
	$(".loadingDialog1").animate( {
		top : "0"
	});
	$(".loadingDialog1").remove();
	$(".fishDialogMask").remove();
}
function list_update(callback) {
	var list_xz = $(".loadingDialog1 .data_list a.cur").html();
	var p_id = $(".loadingDialog1 .data_list").attr("id");
	if (list_xz) {
		var curDataKey = $(
				".loadingDialog1 .data_list a.cur [name='curDataKey']").val();
		var curDataValue = $(".loadingDialog1 .data_list a.cur").text();
		var obj = {
			key : curDataKey,
			value : curDataValue
		};
		try{
			eval(callback(obj));
		}catch(ex){}
	
		remove_list();
	} else {
		remove_list();
	}
}

function list_xz(obj) {
	$(".loadingDialog1 .data_list a").removeClass("cur");
	$(obj).addClass("cur");
}
