/*
 * This project requires JDK/JRE 11 or later due to the SalesList class using the replace function
 */

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class SalesWindow {

	protected Shell shell;
	private Text itemField;
	private Text dollarField;
	private Text quantityField;
	private Text salesListField;
	private Label label;
	private Text centsField;
	private Label totalSales;
	private Label totalOutputField;
	private Label costError;
	private Label quantityError;
	private Label itemError;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SalesWindow window = new SalesWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setVisible(true);
		shell.setSize(485, 344);
		shell.setText("SWT Application");
		
		// Create Sales List variable
		SalesList sales = new SalesList();
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(160, 10, 102, 30);
		lblNewLabel.setText("Sales List");
		
		Label lblItem = new Label(shell, SWT.NONE);
		lblItem.setBounds(40, 50, 55, 15);
		lblItem.setText("Item:");
		
		Label lblCost = new Label(shell, SWT.NONE);
		lblCost.setText("Cost: $");
		lblCost.setBounds(40, 88, 55, 15);
		
		Label lblQuantity = new Label(shell, SWT.NONE);
		lblQuantity.setText("Quantity:");
		lblQuantity.setBounds(40, 123, 55, 15);
		
		itemField = new Text(shell, SWT.BORDER);
		itemField.setBounds(101, 44, 250, 21);
		// Arbitrary item name length
		itemField.setTextLimit(25);
		
		dollarField = new Text(shell, SWT.BORDER);
		dollarField.setBounds(101, 82, 55, 21);
				
		centsField = new Text(shell, SWT.BORDER);
		centsField.setBounds(172, 82, 25, 21);
		// Cents should only have 2 digits so we limit it here
		centsField.setTextLimit(2);
		
		quantityField = new Text(shell, SWT.BORDER);
		quantityField.setText("");
		quantityField.setBounds(101, 117, 106, 21);
		
		Button addItemBtn = new Button(shell, SWT.NONE);
		
		// All functionality stems from this button:
		// Step 1: Check field for valid inputs
		// Step 2: Add sales to sales object
		// Step 3: Output sales items
		// Step 4: Sum new total and output
		// Step 5: Clear input fields (only if input was valid)
		addItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// reset error messages from previous entries
				itemError.setVisible(false);
				costError.setVisible(false);
				quantityError.setVisible(false);
				// Validate all field at once errors (hence the use of the flag)
				boolean inputError = false;
				// Assuming that we want only alphabetic characters and spaces
				if(!itemField.getText().matches("^[a-zA-Z\s]+$")) {
					inputError = true;
					itemError.setVisible(true);
				}
				if(!dollarField.getText().matches("^[0-9]+$") || !centsField.getText().matches("^[0-9]+$")) {
					inputError = true;
					costError.setVisible(true);
				}
				if(!quantityField.getText().matches("^[0-9]+$")) {
					inputError = true;
					quantityError.setVisible(true);
				}
				if(inputError) {
					return;
				}
				
				String item = itemField.getText();
				Double cost = Double.parseDouble(dollarField.getText()+"."+centsField.getText());
				Integer number = Integer.parseInt(quantityField.getText());
				
				// add to sales object
				sales.addNewItem(item, cost, number);
				
				// Output new sale - this is for the text window output and not the currently in use table output
				//salesListField.append(sales.printLastItem());
				
				// Output new sale into table
				TableItem newItem = new TableItem(table, SWT.NONE);
				newItem.setText(new String[] {sales.getLastItem(), "$"+String.format("%.2f" ,sales.getLastPrice()), Integer.toString(sales.getLastQuantity())});
				
				// Recalculate new total sales
				totalOutputField.setText(String.format("%.2f", Double.parseDouble(totalOutputField.getText()) + sales.lastPurchasePrice()));
				
				// Clear input fields
				itemField.setText("");
				dollarField.setText("");
				centsField.setText("");
				quantityField.setText("");
			}
		});
		addItemBtn.setBounds(40, 153, 250, 21);
		addItemBtn.setText("Add Item to the Sales List");
		
		label = new Label(shell, SWT.NONE);
		label.setBounds(160, 88, 6, 15);
		label.setText(".");
		
		totalSales = new Label(shell, SWT.NONE);
		totalSales.setBounds(40, 267, 116, 15);
		totalSales.setText("Total Sales:\t$");
		
		totalOutputField = new Label(shell, SWT.NONE);
		totalOutputField.setText("0.00");
		totalOutputField.setBounds(156, 267, 134, 15);
		
		costError = new Label(shell, SWT.NONE);
		costError.setVisible(false);
		costError.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		costError.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		costError.setBounds(207, 82, 102, 21);
		costError.setText("ERROR in Cost");
		
		quantityError = new Label(shell, SWT.NONE);
		quantityError.setVisible(false);
		quantityError.setText("ERROR in Quantity");
		quantityError.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		quantityError.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		quantityError.setBounds(217, 117, 134, 21);
		
		itemError = new Label(shell, SWT.NONE);
		itemError.setVisible(false);
		itemError.setText("ERROR in Item");
		itemError.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		itemError.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		itemError.setBounds(357, 44, 102, 21);
		
		// Alternative to the table below
		//salesListField = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
		//salesListField.setBounds(40, 181, 338, 80);
		
		table = new Table(shell, SWT.NO_SCROLL | SWT.V_SCROLL);
		table.setBounds(40, 186, 419, 75);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn itemClmn = new TableColumn(table, SWT.NONE);
		itemClmn.setWidth(136);
		itemClmn.setText("Item");
		
		TableColumn priceClmn = new TableColumn(table, SWT.NONE);
		priceClmn.setWidth(136);
		priceClmn.setText("Price");
		
		TableColumn quatityClmn = new TableColumn(table, SWT.NONE);
		quatityClmn.setWidth(142);
		quatityClmn.setText("Quantity");

	}
}
