
function pldrzp(){
	var fileValue = document.getElementById("file").value;
	if(fileValue == null || fileValue == ""){
		alertMessage("请先选择要导入的文件！");
		return false;
	}
	var fileValues = fileValue.split(".");
	var fileType = fileValues[fileValues.length - 1];
	if(fileType.toLowerCase() != "zip"){
		alertMessage('导入文件格式不正确，请重新选择！');
		return false;
	}
	document.forms[0].target = "_blank";
	document.forms[0].submit();
	document.forms[0].target = "_self";
}