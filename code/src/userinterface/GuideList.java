package userinterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultListModel;

import Svg.Data;

/**
 * The Class GuideList. List on left side. Provides drag an drop feature for items
 */
public class GuideList {

	DefaultListModel<DrawerItem> model = new DefaultListModel<DrawerItem>();

	/**
	 * Load items.
	 *
	 * @return the default list model
	 */
	public DefaultListModel<DrawerItem> loadItems() {

		FileReader file=null;
		try {
			file = new FileReader(new File("listdata.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("could not find the"+Data.left_menu_item_information);
		}

		BufferedReader br = new BufferedReader(file);
		try {
			String line=br.readLine();

			while(line!=null){
				DrawerItem item = new DrawerItem();
				String temp[] = line.split("\\*");
				item.name=temp[0];
				item.description=temp[1].replace('?','\n');
				model.addElement(item);
				line=br.readLine();
			}//while

		} catch (IOException e) {
			System.out.println("could not read"+Data.left_menu_item_information);
		}
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("problem with closing file");
		}
		return model;
	}// end of method
}//end of class 
