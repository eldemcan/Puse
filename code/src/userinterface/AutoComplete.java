
package userinterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.text.JTextComponent;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * The Class AutoComplete. This class provides autocomplete functionality for writing  plantuml codes easily 
 * 
 */
public class AutoComplete {

	RSyntaxTextArea code_text_area;   /**<text area for writing code */
	CompletionProvider provider;   /**<provides autocompletiton */
	AutoCompletion ac;   /**< autocompletiion engine */

	/**
	 * Instantiates a new auto complete.
	 *
	 * @param textarea the textarea
	 */
	public AutoComplete(RSyntaxTextArea textarea) {

		code_text_area=textarea;

	}//end of method

	/**
	 * it install grammar rules from given file for autocomplete  
	 *
	 * @param filename the filename for installing grammer rules
	 */
	void installGrammar(String filename){

		DefaultCompletionProvider provider_temp = new DefaultCompletionProvider();
		FileReader file=null;
		try {
			file = new FileReader(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("could not read: "+filename);
		}

		BufferedReader br = new BufferedReader(file);
		try {
			String line=br.readLine();
			while(line!=null){
				provider_temp.addCompletion(new BasicCompletion(provider_temp,line));
				line =br.readLine();
			}

			br.close();
			provider= provider_temp;
			ac = new AutoCompletion(provider);
			ac.install(code_text_area);
			System.out.println("installation completed");

		} catch (IOException e) {
			System.out.println("could not read the line");
		}
	}//end of installer 
}
