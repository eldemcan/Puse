package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;

import Svg.*;
import userinterface.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Control. Provides communication with model and view
 */
public class Control {

	Svg canvasjobs;   /**< The canvasjobs.  */
	Gui userinferface;   /**< The userinterface.  */

	/**
	 * Add listeners to view 
	 *
	 * @param tempgui the tempgui
	 * @param tempsvg the tempsvg
	 */
	public Control(Gui tempgui,Svg tempsvg){
		canvasjobs=(Svg) tempsvg;
		userinferface=(Gui) tempgui;
		userinferface.addcodetextListener(new TypeCode(this));
		userinferface.addsaveListener(new SaveItem());
		userinferface.addloadListener(new LoadItemToText());
		userinferface.addsvgcanvasListener(new SvgMouseAction(this));
		userinferface.addrightmenutocodeListener(new SvgMouseAction(this));
		userinferface.addpopupactionListener(new ExportDiagram());
		userinferface.addlinkactivationListener(new LinkActivation(this));
		userinferface.addreferenceListener(new ReferenceAction(this));
		userinferface.addbackListener(new BackButtonListener());
		userinferface.addnoteListener();
		userinferface.note_screen.addnoteListener2(new AddNoteaction(this));
		userinferface.addgeneratelinkListener(new GenerateLinks());
		userinferface.linkwindow.addSVgListener(new SeperateWindowHandling());
		userinferface.addClosingListener(new ClosingHangler());
		userinferface.adddynamicrefListener(new DynamicRef());
	}

	/**
	 * This class creates ref operators. User can choose an area in main diagram and with right click on mouse user will activate this class
	 * This object will take selected area and create svg file. Later referancing code will be placed in base  diagram's code.    
	 */
	public class DynamicRef implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String selected_code=userinferface.code_text_area.getSelectedText().trim();
			String svg="";
			int returnVal = userinferface.fc_save.showSaveDialog(userinferface.code_text_area);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				String removed_code = selected_code;
				selected_code="@startuml \n "+selected_code+" \n @enduml";
				String filename=userinferface.fc_save.getSelectedFile().getAbsolutePath();
				canvasjobs.installSvg(selected_code, filename);
				String main_code=userinferface.code_text_area.getText();
				int insert_index=main_code.indexOf(removed_code);
				main_code=main_code.replaceFirst(removed_code,"");
				String object_name=JOptionPane.showInputDialog(null,"Write object name(s)?");
				if(object_name.equals("") || object_name==null){
					object_name="Object name  here";
				}
				
				String ref_message="";
				String link="";
				try {
					link = "[["+ userinferface.fc_save.getSelectedFile().toURL()+".svg "+userinferface.fc_save.getSelectedFile().getName()+"]]";
				} catch (MalformedURLException e2) {
					JOptionPane.showMessageDialog(userinferface.code_text_area,"Can not generate reference link");
				}
				ref_message="ref over "+object_name.trim()+" : init"+link;
				StringBuffer sb = new StringBuffer(main_code);
				sb.insert(insert_index,ref_message);
				main_code=sb.toString();
				userinferface.code_text_area.setText(main_code);
				File file1 = new File(filename+".txt");
				FileWriter fw = null;
				BufferedWriter out=null;  
				try {

					fw = new FileWriter(file1);
					out = new BufferedWriter(fw);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out.write(selected_code);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				canvasjobs.installSvg(main_code);
				canvasjobs.reload(userinferface.svgcanvas, Data.temp_output_svgfile_name);
				userinferface.save_button.setVisible(true);
				userinferface.menuitem_save.setEnabled(true);
				userinferface.rdbtnmntmOpenInNew.setEnabled(false);
				userinferface.chckbxmntmIncrementalModel.setEnabled(false);
				
			} //APPROVE_OPTION
		}// Action
	}//DynamicRef

	/**
	 * This class handles event for closing main program basically it ask user to whether they want to save the work or not  
	 */
	public class ClosingHangler extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			if(Data.is_saved==false && userinferface.code_text_area.getText().equals("")==false){
				int ref=JOptionPane.showConfirmDialog(userinferface.code_text_area,"Do you want save changes?");

				if(ref==0){
					int returnVal = userinferface.fc_save.showSaveDialog(userinferface.code_text_area);
					if(returnVal==JFileChooser.APPROVE_OPTION){
						String filename=userinferface.fc_save.getSelectedFile().getAbsolutePath();
						canvasjobs.exp.Convert(Data.temp_output_svgfile_name,filename+".svg",".svg");
						if(!filename.endsWith(".txt")){filename=filename+".txt";}
						File file1 = new File(filename);
						if(file1.exists()){
							try
							{
								FileWriter fw = new FileWriter(file1);
								fw.write(userinferface.code_text_area.getText());
								fw.close();
							}
							catch(IOException ioe)
							{
								System.err.println("IOException: " + ioe.getMessage());
							}
						}//if
						else{
							FileWriter fw=null;
							try {
								fw = new FileWriter(file1.getAbsoluteFile(), true);
								userinferface.code_text_area.write(fw);
							} catch (IOException en) {
								System.out.println("could not save the file");
							}
						}//else
					}
				}
			}
		}
	}//end of class

	/**
	 * This class handles event for svg canvas when user chooses "open link in seperate window" 
	 * option.
	 *
	 */
	public class SeperateWindowHandling implements SVGDocumentLoaderListener{

		@Override
		public void documentLoadingCancelled(SVGDocumentLoaderEvent arg0) {

		}

		@Override
		public void documentLoadingCompleted(SVGDocumentLoaderEvent arg0) {
			System.out.println("document loaded***************");
			String filename=userinferface.linkwindow.seperate_svg_display.getSVGDocument().getDocumentURI().toString();
			File f = new File(filename);
			userinferface.linkwindow.filename_label.setText(f.getName().replaceFirst(".svg",""));
		}

		@Override
		public void documentLoadingFailed(SVGDocumentLoaderEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void documentLoadingStarted(SVGDocumentLoaderEvent arg0) {
			if(Data.open_ref_new_window==true){
				canvasjobs.reload(userinferface.svgcanvas,Data.temp_output_svgfile_name);
				System.out.println("loading********");
			}
		}
	}

	/**
	 * The Class GenerateLinks for referencing function. 
	 */
	public class GenerateLinks implements ActionListener{


		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String 	Object_name=userinferface.ref_screen.ref_object_name.getText().trim();
			String ref_message=""; 
			if(	Object_name==null || 	Object_name.equals("")){
				Object_name="Object 1";
			}

			if(userinferface.ref_screen.rdbtnNewRadioButton.isSelected()){// Ref On Object is selected
				String link="";
				try {

					link = "[["+ userinferface.fc_reference.getSelectedFile().toURL()+" "+userinferface.fc_reference.getSelectedFile().getName()+"]]";
				} catch (MalformedURLException e1) {
					System.out.println("problem creating link");
				}
				ref_message="participant "+" \" "+Object_name.trim()+" &<u>"+link+"\" as "+Object_name.trim();

				String code= userinferface.code_text_area.getText();
				if(code.contains("@startuml")){
					code=code.replaceFirst("@startuml","@startuml\n"+ref_message);
					code=code.replace("&","\\n");
				}
				ref_message="";

				userinferface.code_text_area.setText(code);
			}
			else{
				String link="";
				try {
					link = "[["+ userinferface.fc_reference.getSelectedFile().toURL()+" "+userinferface.fc_reference.getSelectedFile().getName()+"]]";
				} catch (MalformedURLException e1) {
					System.out.println("problem creating link");
				}
				ref_message="ref over "+Object_name.trim()+" : init"+link;
				int get_position= userinferface.code_text_area.getCaretPosition();

				if(get_position>0){
					userinferface.code_text_area.insert( ref_message, userinferface.code_text_area.getCaretPosition());
				}
				else{
					userinferface.code_text_area.append( ref_message);
				}
			}//else 
			canvasjobs.installSvg(userinferface.code_text_area.getText());
			canvasjobs.reload(userinferface.svgcanvas, Data.temp_output_svgfile_name);
			userinferface.ref_screen.dispose();
			userinferface.ref_screen.ref_object_name.setText("");
			userinferface.ref_screen.group.clearSelection();
			userinferface.ref_screen.rdbtnNewRadioButton.setSelected(true);
			userinferface.fc_reference.setSelectedFile(new File(""));
		}// end of action
		
	}//generate links

	/**
	 * Saves current file 
	 */
	public class SaveItem implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//In response to a button click:
			int returnVal = userinferface.fc_save.showSaveDialog(userinferface.code_text_area);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				String filename=userinferface.fc_save.getSelectedFile().getAbsolutePath();
				canvasjobs.exp.Convert(Data.temp_output_svgfile_name,filename+".svg",".svg");
				if(!filename.endsWith(".txt")){filename=filename+".txt";}
				File file1 = new File(filename);

				if(file1.exists()){
					try
					{
						FileWriter fw = new FileWriter(file1);
						fw.write(userinferface.code_text_area.getText());
						fw.close();
					}
					catch(IOException ioe)
					{
						System.err.println("IOException: " + ioe.getMessage());
					}
				}//if

				else{
					FileWriter fw=null;
					try {
						fw = new FileWriter(file1.getAbsoluteFile(), true);
						userinferface.code_text_area.write(fw);
					} catch (IOException en) {
						System.out.println("could not save the file");
					}
				}//else
				Data.is_saved=true;
				Data.currentfile=userinferface.fc_save.getSelectedFile().getAbsolutePath();
				userinferface.save_button.setEnabled(false);
				userinferface.menuitem_save.setEnabled(false);
				userinferface.rdbtnmntmOpenInNew.setEnabled(true);
				userinferface.chckbxmntmIncrementalModel.setEnabled(true);
				userinferface.filename_label.setText(userinferface.fc_save.getSelectedFile().getName().replaceFirst(".txt","").toString());
			}
		}
	}// end of saveitem class 

	/**
	 * Loads item from text 
	 */
	public class LoadItemToText implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			int return_val=userinferface.fc_load.showOpenDialog(userinferface.code_text_area);
			if(return_val==JFileChooser.APPROVE_OPTION){
				try {
					userinferface.code_text_area.read(new BufferedReader(new FileReader(userinferface.fc_load.getSelectedFile().getAbsolutePath())), null);
					userinferface.validation_label.setText(canvasjobs.installSvg(userinferface.code_text_area.getText()));
					canvasjobs.reload(userinferface.svgcanvas,Data.temp_output_svgfile_name);
					Data.currentfile=userinferface.fc_load.getSelectedFile().getAbsolutePath();
					userinferface.filename_label.setText(userinferface.fc_load.getSelectedFile().getName().replaceFirst(".txt","").toString());
					Data.is_saved=true;
					userinferface.save_button.setEnabled(false);
					userinferface.menuitem_save.setEnabled(false);
					userinferface.rdbtnmntmOpenInNew.setEnabled(true);
					userinferface.chckbxmntmIncrementalModel.setEnabled(true);
				} catch (FileNotFoundException en) {
					System.out.println("could not find svg file");
				} catch (IOException en) {
					System.out.println("could not load the file");
				}
				userinferface.save_button.setEnabled(false);
				userinferface.menuitem_save.setEnabled(false);
			}//approve option
		} 
	}//end of LoadItemToText 

	/**
	 * Exports sequence diagrams in different formats
	 */
	public class ExportDiagram implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int return_val=userinferface.fc_export.showSaveDialog(userinferface.svgcanvas);
			if(return_val==JFileChooser.APPROVE_OPTION){

				String input_file_format=Data.pruneFilePath(userinferface.svgcanvas.getSVGDocument().getDocumentURI());
				String output_file_format=userinferface.fc_export.getSelectedFile().getAbsolutePath();
				String file_format_option=userinferface.fc_export.getFileFilter().getDescription();
				if(!output_file_format.endsWith(".png") && file_format_option.equalsIgnoreCase(".png")){
					output_file_format=output_file_format+".png";
				}
				else if(!output_file_format.endsWith(".jpg") && file_format_option.equalsIgnoreCase(".jpg")){
					output_file_format=output_file_format+".jpg";
				}
				else if(!output_file_format.endsWith(".svg") && file_format_option.equalsIgnoreCase(".svg")){
					output_file_format=output_file_format+".svg";
				}
				canvasjobs.exp.Convert(input_file_format,output_file_format,userinferface.fc_export.getFileFilter().getDescription());
				System.out.println(userinferface.svgcanvas.getURI());
			}
		}

	}//end of export diagram

	/**
	 * The listener interface for receiving backButton events.
	 * The class that is interested in processing a backButton
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addBackButtonListener<code> method. When
	 * the backButton event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see BackButtonEvent
	 */
	public class BackButtonListener implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			if(Data.browse_file_names.size()>0){
				String load_path=Data.browse_file_names.pop();
				File f= new File(load_path);
				userinferface.code_text_area.setText("");
				try {
					userinferface.code_text_area.read(new BufferedReader(new FileReader(f)), null);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(userinferface.code_text_area,"Could not find the file");
					canvasjobs.reload(userinferface.svgcanvas,Data.temp_output_svgfile_name);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				canvasjobs.installSvg(userinferface.code_text_area.getText());
				canvasjobs.reload(userinferface.svgcanvas,Data.temp_output_svgfile_name);
				Data.currentfile=load_path;
				userinferface.filename_label.setText(f.getName().replaceFirst(".txt",""));
			}
			else{
				userinferface.back_button.setEnabled(false);
			}
			if(Data.browse_file_names.size()==0){
				userinferface.back_button.setEnabled(false);
			}
		}// Data.browse_file_names.size()>0
	}//end of bakclistener class

}//end of control class
