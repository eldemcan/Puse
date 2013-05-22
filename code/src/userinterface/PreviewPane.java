package userinterface;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;

/**
 * This a custom file chooser which includes a preview panel for svg file format 
 */
public class PreviewPane extends JPanel implements PropertyChangeListener {

	public JLabel label;
	public JSVGCanvas svg_preview;

	/**
	 * Instantiates a new preview pane.
	 */
	public PreviewPane() {
		label = new JLabel();
		setLayout(new BorderLayout(5,5));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		add(new JLabel("Preview:"), BorderLayout.NORTH);
		svg_preview = new JSVGCanvas();
		add(svg_preview,BorderLayout.CENTER);
		add(label,BorderLayout.SOUTH);
		svg_preview.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 * when user chooses another svg file it displays on preview panel
	 */
	public void propertyChange(PropertyChangeEvent evt) {

		if(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt
				.getPropertyName())) {
			File newFile = (File) evt.getNewValue();
			if(newFile != null && newFile.getName().endsWith(".svg")) {
				try {
					svg_preview.setURI(newFile.toURL().toString());
					System.out.println("reload is complete:"+newFile.getAbsolutePath());
				} catch (MalformedURLException e) {
					label.setText("Could not load preview");
				}
				label.setText("");
			}// file control

			else if(newFile !=null && !newFile.getName().endsWith(".svg")){
				label.setText("Please select Svg file");
			}
		}
	}
}//end of class 