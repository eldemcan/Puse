
package userinterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderListener;

import Svg.Data;
import Svg.Svg;

import Controller.Control;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Provides a separate screen for displaying sequence diagrams in case user changes Preferences
 */
public class SeperateDisplay extends JFrame {

	public JPanel contentPane;
	public JSVGCanvas seperate_svg_display;
	public JSVGCanvas main_display;

	/** The seperate_svg_display_scroll_pane. */
	public JScrollPane seperate_svg_display_scroll_pane;
	private JPanel menu_bar;
	public Svg svg_operation;
	public JLabel filename_label;

	/**
	 * Create the frame.
	 */
	public SeperateDisplay(Svg control, JSVGCanvas	temp) {
		svg_operation=control;
		main_display=temp;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		seperate_svg_display = new JSVGCanvas();
		seperate_svg_display_scroll_pane= new JScrollPane(seperate_svg_display); 
		contentPane.add(seperate_svg_display_scroll_pane,BorderLayout.CENTER);
		menu_bar = new JPanel();
		seperate_svg_display_scroll_pane.setColumnHeaderView(menu_bar);
		
		filename_label = new JLabel(" ");
		menu_bar.add(filename_label);
		setContentPane(contentPane);
	}

	public void addSVgListener(SVGDocumentLoaderListener e){
		seperate_svg_display.addSVGDocumentLoaderListener(e);
	}
}
