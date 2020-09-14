/*
 * This project requires JDK/JRE 11 or later due to the SalesList class using the replace function
 */

import java.util.ArrayList;

class SalesList {
	private ArrayList<String> items;
	private ArrayList<Double> price;
	private ArrayList<Integer> quantity;
	private int listSize;
	
	public SalesList() {
		items = new ArrayList<String>();
		price = new ArrayList<Double>();
		quantity = new ArrayList<Integer>();
		listSize = 0;
	}
	
	public void addNewItem(String name, Double cost, Integer number) {
		items.add(name);
		price.add(cost);
		quantity.add(number);
		listSize++;
	}
	
	// If using text output, I wasn't thrilled with the formatting so I am not using it
	public String printLastItem() {
		String item = items.get(listSize-1);
		if(item.length() < 25) {
			String space = " ";
			item += (space.repeat(25-item.length()));
		}
		return item + " $" + String.format("%.2f" ,price.get(listSize-1)) + "\t" + Integer.toString(quantity.get(listSize-1)) + "\n";
	}
	
	public Double lastPurchasePrice() {
		return price.get(listSize-1) * quantity.get(listSize-1);
	}
	
	public String getLastItem() {
		return items.get(listSize-1);
	}
	
	public Double getLastPrice() {
		return price.get(listSize-1);
	}
	
	public Integer getLastQuantity() {
		return quantity.get(listSize-1);
	}
		
}
