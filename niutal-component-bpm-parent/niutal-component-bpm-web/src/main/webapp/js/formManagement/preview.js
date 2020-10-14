$(function() {

	var form_defination_tab = $('#form_defination_task_tab');
	var formDefinitionId = $('#formDefinitionId').val();

	$('#show_form_diagram_link')
			.click(
					function() {
						if ($('#form_diagram_el').attr('data-status') == 'initial') {
							var formDefinitionId = $(this).attr(
									'data-form-definition-id');
							var diagram_url = _path
									+ '/form-service/preview-definition.html?formDefinitionId='
									+ formDefinitionId;
							var diagram_iframe = $('<iframe allowtransparency="true" name="form-diagram-view-iframe" width="100%" height="100%" frameborder="0"></iframe');
							diagram_iframe.attr('src', diagram_url);
							$('#form_diagram_el').attr('data-status', 'loaded');
							$('#form_diagram_el').css('display', 'block');
							$('#form_diagram_el').append(diagram_iframe);
						} else {
							$('#form_diagram_el').toggle();
						}
					});

});