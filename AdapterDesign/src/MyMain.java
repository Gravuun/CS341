
/*
 * Adapter Design Pattern
 * TASKS:
 * 1. Construct an UML class diagram for this application. Include
 *    relationships (dependence, aggregation, inheritance, and implementation) 
 *    for all Java classes and interfaces explicitly referenced in the code.
 *    NOTE: You do not need to include any of the their superclasses, such as Frame,
 *    Container, Component, etc.).
 * 2. You will have noticed that the Sort button adds multiple copies of the original list to the
 *    ListPanel. Modify the code to ensure that only one copy of the original list
 *    appears.
 * 3. The application does not yet sort the items.  Write the code so that all ListItems are sorted
 *     from highest to lowest based on their values. Do not manually sort the list.
 * 4. This program does not correctly implement the ADAPTER pattern.  Specifically, the ItemIcon class  
 *    should serve as an ADAPTER between the ListItem class and the JLabel class, which is expecting an 
 *    Icon object. Determine how/why the ItemIcon and ListItem classes implement the ADAPTER pattern
 *    incorrectly, and then modify these classes (and any associated dependencies in classes that use them)
 *    to correctly the ADAPTER pattern.
 *     
 *    
 */

public class MyMain {
	public static void main(String[] args) {
		int[] array = {25, 37, 12, 8, -5};
		int[] array2 = {-5, 15, 400, 0, -2};
		
		DataStructsFrame frame = new DataStructsFrame ("Adapter Design Pattern Demonstration", array, array2);		
	}
}