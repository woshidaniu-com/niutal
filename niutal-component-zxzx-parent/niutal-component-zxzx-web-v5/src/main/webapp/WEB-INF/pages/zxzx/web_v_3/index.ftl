[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
<head>

</head>
<body>
	<form action="index.zf?layout=zxzxWebindexLayout" id="zxzx_web_form" method="post">
	<input type="hidden" name="webSearchValue" id="webSearchValue" value="${webSearchValue}" />
	<input type="hidden" name="webSearchBkdmValue" id="webSearchBkdmValue" value="${webSearchBkdmValue}" />
	<div class="panel-body">
		<div role="tabpanel" class="tab-pane fade in active" id="all_q">
            <div class="search-box">
                <dl class="dl-horizontal">
                    <dt>热门类别：</dt>
                    <dd>
                        <ul class="tag-list">
                            [#if webSearchBkdmValue == null || webSearchBkdmValue == '']
								<li><a datatype="search-bkdm" datavalue="" href="#" class="cur" style="cursor: pointer;">全部</a></li>
							[#else]
								<li><a datatype="search-bkdm" datavalue="" class="" style="cursor: pointer;">全部</a></li>
							[/#if]
							
							[#list kzdkList as kzdk]
								<li><a datatype="search-bkdm" datavalue="${kzdk.bkdm}" class=" 
								[#if kzdk.bkdm==webSearchBkdmValue]
									cur"
								[#else]
									"
								[/#if] 
								style="cursor: pointer;">${kzdk.bkmc}</a></li>
							[/#list]
							
							
                        </ul>
                    </dd>
                </dl>
                <dl class="dl-horizontal">
                    <dd>
                        <div class="form-inline">
                            <div class="form-group">
                                <div class="input-group input-group-sm" style="width:500px">
									<input type="text" class="form-control" id="zxzxSearch" value="${webSearchValue}" placeholder="输入关键字...">
							      	<span class="input-group-btn">
							        	<button id="searchZxkzdtn" class="btn btn-primary" type="button"><i class="glyphicon glyphicon-search"></i> 查 询 </button>
							      	</span>
							    </div>
                            </div>
                            <div class="form-group">
                                [#if openStatus.isOpen]
									<button id="askQuestion"  class="btn btn-primary btn-sm btn-warning" type="button">  我 要 提 问  </button>
								[#else]
									<button id="askQuestion_disabled"  class="btn btn-warning btn-block disabled" type="button">${openStatus.messageValue}</button>
								[/#if]
                            </div>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="question-box">
                <ul>
                [#list zxwtList as zxwt]	
                    <li>
				        <div class="question-part question-part-answered" style="padding-left:0px;">
				            <a href="javascript:void(0);" datatype="zxzx-kzdt" datavalue="${zxwt.zxid}" databkdm="${zxwt.bkdm}" class="text-primary">${zxwt.kzdt}</a>
				            <div>
				            	<img src="${base}/css/zxzx/images/icon-question.png" class="margin_r15">
				            	<span style="font-size: 13px;" class="text-primary">${zxwt.zxnr}</span>
							</div>
				            <div class="question-part-desc">
				                <span>咨询人：
				                	<a href="javascript:void(0);">
										[#if zxwt.zxr != null]
											${zxwt.zxr}
										[#else]
											匿名
										[/#if]
									 </a>
								</span>
								<span class="padding_l5">版块：
									<a href="javascript:void(0);">${zxwt.kzdkModel.bkmc}</a>
								</span>
								<span class="padding_l5">咨询时间：
									<a href="javascript:void(0);">${zxwt.zxsj}</a>
								</span>
				                <!--<span class="question-date">点击量：${zxwt.djl}</span>-->
				            </div>
				        </div>
				        <div datarelate="${zxwt.zxid}" class="answer-part">
				            <img src="${base}/css/zxzx/images/icon-answer.png" class="margin_r15">
				            <div class="answer-part-desc text-danger">
				              ${zxwt.zxhfModel.hfnr}
				            </div>
				        </div>
				    </li>
				    [/#list]
                </ul>
            </div>
        </div>
		[#include "/zxzx/web_v_2/pageFootMenu.ftl" /]
		<script type="text/javascript">
			$(function() {
				var clickData = {};
				/***/
				$("a[datatype='zxzx-kzdt']").click(
					function() {
						var dataValue = $(this).attr("datavalue");
						var bkdm = $(this).attr("databkdm");
						//async update 'djl' of zxwt
						if (!clickData[dataValue]) {
							$.post(_path + "/zxzx/web/updateDjl.zf", {
								"zxid" : dataValue,
								"bkdm" : bkdm
							}, function() {
								clickData[dataValue] = true;
							});
						}
						return;
					});
			});
		</script>
	</div>
	</form>	
</body>
</html>
