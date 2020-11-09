import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ListPanel extends JPanel {
	private int diameter = 30;
	private ArrayList<ListItem> mList = null;

	public ListPanel(String title) {
		super();
		mList = new ArrayList<ListItem>();
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), title));
	}

	public ListPanel(String title, ArrayList<ListItem> list) {
		this(title);
		addItems(list);
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
		repaint();
	}

	public void addItems(ArrayList<ListItem> list) {
		mList = (ArrayList<ListItem>) list.clone();
		removeAll();
		for (ListItem item : mList)
			add(new JLabel(item.createIcon(diameter)));
	}
}