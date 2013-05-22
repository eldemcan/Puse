package Controller;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.batik.swing.svg.LinkActivationEvent;
import org.apache.batik.swing.svg.LinkActivationListener;

import userinterface.NoteGui;

import Svg.Data;

// TODO: Auto-generated Javadoc
/**
 * The Class LinkActivation.
 */
public class LinkActivation implements LinkActivationListener {

	Control c;   /**<control object*/

	/**
	 * Instantiates a new link activation.
	 *
	 * @param temp the control object assginment 
	 */
	public LinkActivation(Control temp) {
		c = temp ;
	}

	/* (non-Javadoc)
	 * @see org.apache.batik.swing.svg.LinkActivationListener#linkActivated(org.apache.batik.swing.svg.LinkActivationEvent)
	 * this part works when user clicks a link on canvas 
	 */
	@Override
	public void linkActivated(LinkActivationEvent arg0) {

		String clicked_link=Data.pruneFilePath(arg0.getReferencedURI());
		System.out.println("clicked link is:"+clicked_link);

		if(Data.open_ref_new_window==true){
			c.canvasjobs.reload(c.userinferface.linkwindow.seperate_svg_display,clicked_link);
			c.userinferface.linkwindow.setVisible(true);

		}//if
		else{  //Data==false

			if(Data.is_saved==true){
				if(Data.incremental_model==true){
					String ref_diagram_code=Data.pruneFilePath(arg0.getReferencedURI().replace(".svg",".txt"));
					String new_code=c.canvasjobs.CombineStrings(c.userinferface.code_text_area.getText().trim(),ref_diagram_code);

					if(new_code.equals("")){
						JOptionPane.showMessageDialog(c.userinferface.code_text_area, ref_diagram_code+"Can not found");
					}
					else{
						c.userinferface.code_text_area.setText(new_code);
						c.canvasjobs.installSvg(new_code);
						c.userinferface.save_button.setEnabled(true);
						c.userinferface.menuitem_save.setEnabled(true);
						c.userinferface.filename_label.setText("New File");
						Data.is_saved=false;
						Data.incremental_model_link=true;
						c.canvasjobs.reload(c.userinferface.svgcanvas,Data.temp_output_svgfile_name);
					}//else 

				}//incremental model
				else{
					c.userinferface.back_button.setEnabled(true);
					String input_file_format=null;
					try {
						input_file_format=Data.pruneFilePath(arg0.getReferencedURI().replace(".svg",".txt"));
						System.out.println(input_file_format);
						c.userinferface.code_text_area.read(new BufferedReader(new FileReader(input_file_format)), null);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(c.userinferface.code_text_area, input_file_format+"file could not found");
					} catch (IOException e) {
						e.printStackTrace();
					}
					c.userinferface.validation_label.setText(c.canvasjobs.installSvg(c.userinferface.code_text_area.getText()));
					File f = new File(input_file_format);
					c.userinferface.filename_label.setText(f.getName().replaceFirst(".txt",""));
					Data.browse_file_names.push(Data.currentfile);
				}
			}//if Data.is_saved==false
			else {
				JOptionPane.showMessageDialog(c.userinferface.code_text_area, "Please save file before opening a diagram");
				c.userinferface.save_button.doClick();
			}
		}

	}// end of linkActivated

}// end of class
