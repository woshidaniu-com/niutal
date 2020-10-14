<!DOCTYPE html>
<html>
	<head></head>
	<body>
		<form name="selectColumnForm" id="selectColumnForm" action="niutal/dr/import/importData.zf" method="post" enctype="multipart/form-data">
			<input type="hidden" id="drmkdm" name="drmkdm" value="${drmkdm}" />
			<div class="export">
			    <table class="table table-bordered table-condensed">
			        <tbody>
			            <tr>
			                <td style="width:120px;">导入方式</td>
			                <td name="crfstd">
			                </td>
			            </tr>
			            <tr>
			                <td>操作</td>
			                <td>
			                    <label class="radio-inline">
			                        <input id="selectall" type="radio" name="select" onclick="selectAll();"/> 全选
			                    </label>
			                    <label class="radio-inline">
			                        <input id="cleanselect" type="radio" name="select" onclick="unselect();"/> 清空
			                    </label>
			                </td>
			            </tr>
			            <tr>
			                <td>待导入列</td>
			                <td id="drl">
			                   
			                </td>
			            </tr>
			             <tr>
			                <td>提示</td>
			                <td id="message">
			                	无
			                </td>
			            </tr>
			            <tr>
			                <td>帮助信息</td>
			                <td>
			                    <div class="example-exprot-help">
			                        <p>1. 导入方式为 ‘插入’ 或  ‘插入并更新’ 时，必填项默认全部选中，且选中状态不可取消，不必填项可选择插入或更新。</p>
			                        <p>2. 导入方式为 ‘更新’ 时，主键项默认选中，且选中状态不可取消, 其他项可选择更新。</p>
			                        <p>3. 当导入数据中存在其他错误数据列时，系统将提示 ‘没有对应列，此列不导入’， 并且该列不能导入。</p>
			                        <p>4. 当导入时，缺少必要的必填项列时，系统将提示 ‘必填列，列必须存在导入模板中’ 。</p>
			                    </div>
			                </td>
			            </tr>
			        </tbody>
			    </table>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="${base}/js/dr/selectColumn.js"></script>
	<script type="text/javascript">
		$(function(){
			loadSelectColumn();
		});
	</script>
</html>