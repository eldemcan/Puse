package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import Svg.Data;

/*
 * Action listener controller for adding note functionality
 */
public class AddNoteaction implements ActionListener{

	Control c;   /**< controller object */
  

	public AddNoteaction(Control temp) {
		c=temp;

	} //end of constroctor 
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String note=c.userinferface.note_screen.note_text.getText().trim();
		String alignment=(getSelectedButtonText(c.userinferface.note_screen.group)).toLowerCase();
		String list_name=" #"+c.userinferface.note_screen.list.getSelectedValue().toString().trim();
		String code="note "+alignment+" "+ list_name+": "+ note+" end note";

		if(alignment.equalsIgnoreCase("over")){
			String actor_names_field=c.userinferface.note_screen.text_field_actor_names.getText().trim();
			System.out.println(actor_names_field);
			code="note "+ alignment + " of " + actor_names_field + list_name +": "+ note+ " end note"; 
		}// over

		if(c.userinferface.code_text_area.getCaretPosition()<=0){
			c.userinferface.code_text_area.append(code);
		}
		else{
			c.userinferface.code_text_area.insert(code,c.userinferface.code_text_area.getCaretPosition());
		}

		c.userinferface.note_screen.note_text.setText("");
		String temp=c.canvasjobs.installSvg(c.userinferface.code_text_area.getText());
		c.userinferface.validation_label.setText(temp);
		c.canvasjobs.reload(c.userinferface.svgcanvas,Data.temp_output_svgfile_name);
		c.userinferface.note_screen.dispose();
		
	}// end of action

	/**
	 * Gets the selected button text.
	 *
	 * @param buttonGroup the button group
	 * @return the selected button text
	 */
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}
}//end of class