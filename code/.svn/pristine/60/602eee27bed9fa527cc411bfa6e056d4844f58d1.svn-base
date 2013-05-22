
package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Svg.Data;

/*
 * This class handles user input from keyboard. When user enters input into code area it class realted functions and triggers svg creataion
 * methods  
 */
public class TypeCode implements KeyListener {

	Control c;   /**< control object */

	/**
	 * Instantiates a new type code.
	 *
	 * @param temp the temp
	 */
	public TypeCode(Control temp) {
		c=temp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String code=c.userinferface.code_text_area.getText().trim();
		String validationtext=c.canvasjobs.installSvg(code);
		String key=e.getKeyChar()+"";
		if(key.equals(" ")==false){ //user inputs something 
			Data.is_saved=false;
			c.userinferface.save_button.setEnabled(true);
			c.userinferface.menuitem_save.setEnabled(true);
			c.userinferface.rdbtnmntmOpenInNew.setEnabled(false);
			c.userinferface.chckbxmntmIncrementalModel.setEnabled(false);
			c.userinferface.filename_label.setText("New File");
			if(validationtext.equalsIgnoreCase(" ") || validationtext.contains("@")){
				c.userinferface.validation_label.setText(validationtext);
				c.userinferface.svgcanvas.setURI(null);

			}
			else{
				c.userinferface.svgcanvas=c.canvasjobs.reload(c.userinferface.svgcanvas,Data.temp_output_svgfile_name);
				c.userinferface.validation_label.setText(validationtext);
			}
		}
		else{
			if(Data.is_saved==true){
				c.userinferface.save_button.setEnabled(false);
				c.userinferface.menuitem_save.setEnabled(false);
				c.userinferface.rdbtnmntmOpenInNew.setEnabled(true);
				c.userinferface.chckbxmntmIncrementalModel.setEnabled(true);
			}
		}
	}//keypressed

	@Override
	public void keyReleased(KeyEvent e) {
		String code=c.userinferface.code_text_area.getText().trim();
		String validationtext=c.canvasjobs.installSvg(code);
		String key=e.getKeyChar()+"";
		if(key.equals(" ")==false){ //user inputs something 
			Data.is_saved=false;
			c.userinferface.save_button.setEnabled(true);
			c.userinferface.menuitem_save.setEnabled(true);
			c.userinferface.rdbtnmntmOpenInNew.setEnabled(false);
			c.userinferface.chckbxmntmIncrementalModel.setEnabled(false);
			c.userinferface.filename_label.setText("New File");
			if(validationtext.equalsIgnoreCase(" ") || validationtext.contains("@")){
				c.userinferface.validation_label.setText(validationtext);
				c.userinferface.svgcanvas.setURI(null);

			}
			else{
				c.userinferface.svgcanvas=c.canvasjobs.reload(c.userinferface.svgcanvas,Data.temp_output_svgfile_name);
				c.userinferface.validation_label.setText(validationtext);
			}
		}
		else{
			if(Data.is_saved==true){
				c.userinferface.save_button.setEnabled(false);
				c.userinferface.menuitem_save.setEnabled(false);
				c.userinferface.rdbtnmntmOpenInNew.setEnabled(true);
				c.userinferface.chckbxmntmIncrementalModel.setEnabled(true);
			}
		}
	}//key released

	@Override
	public void keyTyped(KeyEvent e) {
		String code=c.userinferface.code_text_area.getText().trim();
		String validationtext=c.canvasjobs.installSvg(code);
		String key=e.getKeyChar()+"";
		if(key.equals(" ")==false){ //user inputs something 
			Data.is_saved=false;
			c.userinferface.save_button.setEnabled(true);
			c.userinferface.menuitem_save.setEnabled(true);
			c.userinferface.rdbtnmntmOpenInNew.setEnabled(false);
			c.userinferface.chckbxmntmIncrementalModel.setEnabled(false);
			c.userinferface.filename_label.setText("New File");
			if(validationtext.equalsIgnoreCase(" ") || validationtext.contains("@")){
				c.userinferface.validation_label.setText(validationtext);
				c.userinferface.svgcanvas.setURI(null);

			}
			else{
				c.userinferface.svgcanvas=c.canvasjobs.reload(c.userinferface.svgcanvas,Data.temp_output_svgfile_name);
				c.userinferface.validation_label.setText(validationtext);
			}
		}
		else{
			if(Data.is_saved==true){
				c.userinferface.save_button.setEnabled(false);
				c.userinferface.menuitem_save.setEnabled(false);
				c.userinferface.rdbtnmntmOpenInNew.setEnabled(true);
				c.userinferface.chckbxmntmIncrementalModel.setEnabled(true);
			}
		}
	}//keytyped
}//end of class 
