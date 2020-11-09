import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DataStructsFrame extends JFrame {
	public DataStructsFrame(String title, int[] numbers, int[] numbers2) {
		super(title);

		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		final ArrayList<ListItem> list = arrayToList(numbers, numbers2);

		final ListPanel unorderedList = new ListPanel("Unordered List");
		unorderedList.setDiameter(75);
		unorderedList.addItems(list);

		final ListPanel orderedListA = new ListPanel("Ordered by A List");
		orderedListA.setDiameter(100);
		
		// New panel to show b value sort
		final ListPanel orderedListB = new ListPanel("Ordered by B List");
		orderedListB.setDiameter(100);


		JButton sortButton = new JButton("Sort List");
		sortButton.setSize(30, 10);
		sortButton.setAlignmentX(CENTER_ALIGNMENT);

		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Collections.sort(list);
				orderedListA.addItems(list);
				ArrayList<ListItem> listB = insertionSortBVals(list);
				orderedListB.addItems(listB);
				panel.add(orderedListA);
				panel.add(orderedListB);
				pack();
			}
		});

		panel.add(unorderedList);
		panel.add(sortButton);
		add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	// Basic insertion sort on the B values
	private ArrayList<ListItem> insertionSortBVals(ArrayList<ListItem> list){
		
		for(int i = 1; i < list.size(); i++) {
			ListItem key = list.get(i);
			int j = i-1;
			
			while(j >= 0 && list.get(j).getValB() > key.getValB()) {
				list.set(j+1, list.get(j));
				j--;
			}
			list.set(j+1, key);
		}
		
		return list;
	}

	// Modified function to combine the two arrays into an ArrayList of pairs
	private ArrayList<ListItem> arrayToList(int[] numbers, int[] numbers2) {
		ArrayList<ListItem> list = new ArrayList<ListItem>();

		for (int i = 0; i < numbers.length; i++) {
			ListItem item = new ListItem(numbers[i], numbers2[i]);
			list.add(item);
		}

		return list;
	}
}
