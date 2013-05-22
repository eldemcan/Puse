package Svg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.apache.batik.swing.JSVGCanvas;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.syntax.SyntaxChecker;
import net.sourceforge.plantuml.syntax.SyntaxResult;

/**
 * The Class conducts operations for svg file 
 */
public class Svg {

	SourceStringReader reader;  /**< for reading svg file */
	ByteArrayOutputStream os;  /**< for writing svg file */
	FileFormatOption f_option;  /**< file format */
	public Exporter exp=new Exporter();  /**< exporter object for export diagrams */

	/**
	 * Creates svg file from string, it validates code and creates svg 
	 *
	 * @param code the code
	 * @return the string
	 */
	public String CombineStrings(String main,String referenced_diagram){

		File f = new File(referenced_diagram);

		if(f.exists()==false){
			return "";
		}
		else{
			Scanner scanner = new Scanner(main);
			String search=referenced_diagram.replaceFirst(".txt",".svg");
			int linecount=1;
			while(scanner.hasNextLine()) {

				String line=scanner.nextLine();
				if(line.contains(search)){
					String ref_code=installToString(f);
					ref_code=ref_code.replaceFirst("@startuml","");
					ref_code=ref_code.replaceFirst("@enduml","").trim();
					line=line.trim(); //ref over Actor1 : init[[file:/C:/Users/can/Desktop/workspace/seconddiagram.svg seconddiagram.svg]]
					main=ReplaceLine(main,ref_code,linecount);
				}//if
				else{
					linecount++;
				}
			}
			scanner.close();
			return main;
		}//else
	}

	/**
	 * Creates svg file from string, it validates code and creates svg 
	 *
	 * @param code the code
	 * @return the string
	 */
	public String installSvg(String code){

		code=code.replaceAll ("^[ |\t]*\n$", "");
		code=code.trim();

		if(code.contains("@startuml") && Data.remove_footer==true){
			code=code.replaceFirst("@startuml","@startuml \nhide footbox");
			System.out.println("footer deleted");
		}//preference 

		reader = new SourceStringReader(code);
		System.out.println(code);
		os = new ByteArrayOutputStream();
		f_option = new FileFormatOption(FileFormat.SVG); 
		String validationmessage="";
		String svg="";

		try {
			validationmessage=reader.generateImage(os,f_option);
			System.out.println("validation message is:"+validationmessage);
			// The XML is stored into svg
			svg = new String(os.toByteArray());
		} catch (IOException e) {
			System.out.println("could not generate the image");
			validationmessage="error could not generate the image";
		}

		if(code.contains("@startuml")==false || code.contains("@enduml")==false){
			validationmessage="Please use @startuml and @enduml tags";
			return validationmessage;
		}

		else{ //validation message is:(Error)

			SyntaxResult result=SyntaxChecker.checkSyntax(code); 

	 
			if(result.getErrors().size()>0 && validationmessage.contains("Error")){
				validationmessage=result.getErrors()+" Please look line:"+result.getErrorLinePosition();
			}
			else{
				try {
					FileWriter fstream = new FileWriter(Data.temp_output_svgfile_name);
					BufferedWriter wr = new BufferedWriter(fstream);
					wr.write(svg);
					wr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					os.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}// no error
			return validationmessage;		
		}//else not null for validation
	}//end of displaysvg

	/**
	 * works like installsvg. However, you can explicitly indicate where to save that svg file
	 * 
	 *@see #installSvg(String)
	 *
	 * @param code the code
	 * @param 
	 * @return the string
	 */
	public void installSvg(String code,String filename){

		code=code.replaceAll ("^[ |\t]*\n$", "");
		code=code.trim();

		reader = new SourceStringReader(code);
		os = new ByteArrayOutputStream();
		f_option = new FileFormatOption(FileFormat.SVG); 
		String svg="";

		try {
			reader.generateImage(os,f_option);
			svg = new String(os.toByteArray());
		} catch (IOException e) {
			System.out.println("could not generate the image");
		}

		try {
			FileWriter fstream = new FileWriter(filename+".svg");
			BufferedWriter wr = new BufferedWriter(fstream);
			wr.write(svg);
			wr.close();
			//reload(Data.temp_output_svgfile_name);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}//end of displaysvg




	/**
	 * Load a text file contents as a <code>String<code>.
	 *	This method taken from http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
	 * @param file The input file
	 * @return The file contents as a <code>String</code>
	 * @exception IOException IO Error
	 */
	public static String installToString(File file){

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//while
		try {
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
	
	/**
	 * Replaces given line with another string in multi line strings   
	 *
	 * @param String main string 
	 * @param String replacement
	 * @param int line number  
	 * @return String returns the new string
	 */

	public String  ReplaceLine(String main,String replacement,int line_number){

		String temp[]=main.split("\\n");
		temp[line_number-1]=replacement;
		String removed_string="";

		for(int i=0;i<temp.length;i++){
			removed_string=removed_string+temp[i]+"\n";
		}
		return removed_string;
	}

	/**
	 * Reload svg file to canvas 
	 *
	 * @param svgCanvas component name
	 * @param name the name of file which will be loaded 
	 * @return the jSVG canvas is new canvas which will return
	 */
	public JSVGCanvas reload(JSVGCanvas svgCanvas,String name){

		File f = new  File(name);
		try {
			svgCanvas.setURI(f.toURL().toString());
			System.out.println("reload is complete");
		} catch (MalformedURLException e) {
			System.out.println("could not load the svg");
		}
		return svgCanvas;
	}//end of re-load

}//end of class
