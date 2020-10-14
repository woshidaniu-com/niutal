<#if parameters.jsName?exists> 
<#-- 功能 js 引入 start. -->
<script type="text/javascript" src="${parameters.systemPath?html}/js/dynamic/${parameters.jsName?string}?ver=${parameters.jsVersion?default('')}"></script><#rt/>
<#-- 功能 js 引入  end. -->
</#if><#rt/>