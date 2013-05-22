package Controller;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import net.sourceforge.plantuml.code.ArobaseStringCompressor;
import net.sourceforge.plantuml.cucadiagram.Code;

import org.apache.batik.swing.JSVGCanvas;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import Svg.Data;

public class SvgMouseAction implements MouseListener, MouseMotionListener{

	Control c;

	public SvgMouseAction(Control temp) {
		c=temp;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * displaying right click menu for export functionality 
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Component lastEntered;
		 
		lastEntered=arg0.getComponent();
		if(SwingUtilities.isRightMouseButton(arg0))
			if(lastEntered instanceof JSVGCanvas){
				if(arg0.isPopupTrigger()){
					c.userinferface.Pmenu.show(arg0.getComponent(), arg0.getX(),arg0.getY());
				}
			}
			else if(lastEntered instanceof RSyntaxTextArea){
				c.userinferface.Pmenu1.show(arg0.getComponent(), arg0.getX(),arg0.getY());
			}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
