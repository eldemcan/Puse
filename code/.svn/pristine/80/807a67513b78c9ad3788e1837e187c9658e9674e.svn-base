package userinterface;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JList;
import javax.swing.TransferHandler;


import javax.swing.*;



import java.awt.*;
import java.awt.datatransfer.*;

/**
 * The Class ListHandler. Handles drag and drop events
 */
public class ListHandler extends TransferHandler {

	/** The indices. */
	private int[] indices = null;

	/** The add index. */
	private int addIndex = -1; //Location where items were added

	/** The add count. */
	private int addCount = 0;  //Number of items added.

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean canImport(TransferHandler.TransferSupport info) {
		// Check for String flavor
		if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	protected Transferable createTransferable(JComponent c) {
		return new StringSelection(exportString(c));
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY_OR_MOVE;
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}

		JList list = (JList)info.getComponent();
		DefaultListModel listModel = (DefaultListModel)list.getModel();
		JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
		int index = dl.getIndex();
		boolean insert = dl.isInsert();

		// Get the string that is being dropped.
		Transferable t = info.getTransferable();
		String data;
		try {
			data = (String)t.getTransferData(DataFlavor.stringFlavor);
		} 
		catch (Exception e) { return false; }

		// Perform the actual import.  
		if (insert) {
			listModel.add(index, data);
		} else {
			listModel.set(index, data);
		}
		return true;
	}

	/*
	 * Export string.
	 *
	 * @param c the c
	 * @return the string
	 */
	protected String exportString(JComponent c) {
		JList list = (JList)c;
		indices = list.getSelectedIndices();
		Object[] values = list.getSelectedValues();

		StringBuffer buff = new StringBuffer();

		for (int i = 0; i < values.length; i++) {
			Object val = values[i];

			DrawerItem temp =(DrawerItem) val;

			buff.append(val == null ? "" : temp.description.toString());
			if (i != values.length - 1) {
				buff.append("\n");
			}
		}

		return buff.toString();
	}

}// end of class 