<!-- footer --> 
<div id="footerID" class="footer hidden-lg hidden-md hidden-sm">
	<p>${messageUtil("niutal.info.copyright")} ${messageUtil("niutal.info.company")}<br/>${messageUtil("niutal.info.addr")}</p>
</div>
<!-- footer-end -->
<script type="text/javascript">
jQuery(function($) {
	var pageHeight = $(window).height() - $('#navbar_container').outerHeight(true) - $('#footerID').outerHeight(true);
	$('#bodyContainer').css("min-height",pageHeight);
});
</script>