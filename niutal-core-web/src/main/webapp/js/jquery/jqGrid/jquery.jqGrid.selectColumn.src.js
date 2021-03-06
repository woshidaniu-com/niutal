;(function($){
$.jgrid.extend({setColumns : function(p) {
          p = $.extend({
              top : 0,
              left: 0,
              width: 160,
              height: 'auto',
              dataheight: 'auto',
              modal: false,
              drag: true,
              beforeShowForm: null,
              afterShowForm: null,
              afterSubmitForm: null,
              closeOnEscape : true,
              ShrinkToFit : false,
              jqModal : false,
              colnameview : false,
              recreateForm : false
          }, $.jgrid.col, p ||{});
          return this.each(function(){
              var $t = this;
              if (!$t.grid ) { return; }
              var onBeforeShow = typeof p.beforeShowForm === 'function' ? true: false;
              var onAfterShow = typeof p.afterShowForm === 'function' ? true: false;
              var onAfterSubmit = typeof p.afterSubmitForm === 'function' ? true: false;            
              var gID = $t.p.id,
              dtbl = "ColTbl_"+gID,
              IDs = {themodal:'colmod'+gID,modalhead:'colhd'+gID,modalcontent:'colcnt'+gID, scrollelm: dtbl};
              if(p.recreateForm===true && $("#"+IDs.themodal).html() != null) {
                  $("#"+IDs.themodal).remove();
              }
              if ( $("#"+IDs.themodal).html() != null ) {
                  if(onBeforeShow) { p.beforeShowForm($("#"+dtbl)); }
                  $.jgrid.viewModal("#"+IDs.themodal,{gbox:"#gbox_"+gID,jqm:p.jqModal, jqM:false, modal:p.modal});
                  if(onAfterShow) { p.afterShowForm($("#"+dtbl)); }
              } else {
                  var dh = isNaN(p.dataheight) ? p.dataheight : p.dataheight+"px";
                  var formdata = "<div id='"+dtbl+"' class='formdata' >";
                  formdata += "<table class='ColTable' cellspacing='1' cellpading='2' border='0'><tbody>";
                  for(i=0;i<this.p.colNames.length;i++){
                      if(!$t.p.colModel[i].hidedlg) {
                          formdata += "<tr><td ><input type='checkbox' id='col_" + this.p.colModel[i].name + "' class='cbox' value='T' " + 
                          ((this.p.colModel[i].hidden===false)?"checked":"") + "/>" +  "<label for='col_" + this.p.colModel[i].name + "'>" + this.p.colNames[i] + ((p.colnameview) ? " (" + this.p.colModel[i].name + ")" : "" )+ "</label></td></tr>";
                      }
                  }
                  formdata += "</tbody></table></div>";
                  p.gbox = "#gbox_"+gID;
                  $.jgrid.createModal(IDs,formdata,p,"#gview_"+$t.p.id,$("#gview_"+$t.p.id)[0]);
                  
                  $(":input","#"+dtbl).click(function(e){
                     var cn = this.id.substr(4);
                     if(cn){
                         if(this.checked) {
                             $($t).jqGrid("showCol",cn);
                         } else {
                             $($t).jqGrid("hideCol",cn);
                         }
                         if(p.ShrinkToFit===true) {
                             $($t).jqGrid("setGridWidth",$t.grid.width-0.001,true);
                         }
                     }
                     return this;
                 });
                  
                 if(onBeforeShow) { p.beforeShowForm($("#"+dtbl)); }
                 $.jgrid.viewModal("#"+IDs.themodal,{gbox:"#gbox_"+gID,jqm:p.jqModal, jqM: true, modal:p.modal});
                 if(onAfterShow) { p.afterShowForm($("#"+dtbl)); }
             }
         });
     }
 });
 })(jQuery);