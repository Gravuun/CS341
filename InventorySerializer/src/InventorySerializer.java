import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class InventorySerializer {

	protected Shell shell;
	static HashMap<Integer, Textbook> inventory = new HashMap<Integer, Textbook>();
	private Text skuIn;
	private Text titleIn;
	private Text priceIn;
	private Text quantityIn;
	private Text outputBox;
	
	public static void readInventory() {
		File inventoryFile = new File("inventory.txt");
		try {
			if(!inventoryFile.createNewFile() && inventoryFile.length() != 0) {
				ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(inventoryFile));
				int size = objIn.readInt();
				
				for(int i = 0; i < size; i++) {
					Textbook t = (Textbook) objIn.readObject();
					inventory.put(t.getSKU(), t);
				}
				
				objIn.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void saveInventory() {
		File inventoryFile = new File("inventory.txt");
		
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(inventoryFile));
		
			objOut.writeInt(inventory.size());
		
			Iterator<Textbook> itr = inventory.values().iterator();
		
			while(itr.hasNext()) {
				objOut.writeObject(itr.next());
			}
		
			objOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			readInventory();
			InventorySerializer window = new InventorySerializer();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveInventory();
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
	
	public boolean checkSKU(String s) {
		return s.matches("[0-9]+");
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Inventory Management");
		
		Label lblSku = new Label(shell, SWT.NONE);
		lblSku.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblSku.setBounds(10, 20, 55, 25);
		lblSku.setText("SKU");
		
		skuIn = new Text(shell, SWT.BORDER);
		skuIn.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		skuIn.setBounds(76, 20, 125, 25);
		
		Label lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setText("Title");
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblTitle.setBounds(217, 20, 45, 25);
		
		titleIn = new Text(shell, SWT.BORDER);
		titleIn.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		titleIn.setBounds(270, 20, 154, 25);
		
		Label lblPrice = new Label(shell, SWT.NONE);
		lblPrice.setText("Price");
		lblPrice.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblPrice.setBounds(10, 60, 55, 25);
		
		priceIn = new Text(shell, SWT.BORDER);
		priceIn.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		priceIn.setBounds(76, 60, 125, 25);
		
		Label lblQuantity = new Label(shell, SWT.NONE);
		lblQuantity.setText("Quantity");
		lblQuantity.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblQuantity.setBounds(217, 60, 68, 25);
		
		quantityIn = new Text(shell, SWT.BORDER);
		quantityIn.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		quantityIn.setBounds(300, 60, 125, 25);
		
		Combo listOptions = new Combo(shell, SWT.READ_ONLY);
		listOptions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listOptions.getText().equals("Add textbook")) {
					titleIn.setEnabled(true);
					priceIn.setEnabled(true);
					quantityIn.setEnabled(true);
				}
				else {
					titleIn.setEnabled(false);
					priceIn.setEnabled(false);
					quantityIn.setEnabled(false);
				}
			}
		});
		listOptions.setItems(new String[] {"Add textbook", "Remove textbook", "Display textbook"});
		listOptions.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		listOptions.setBounds(10, 100, 275, 30);
		listOptions.select(0);
		
		outputBox = new Text(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		outputBox.setBounds(10, 170, 403, 81);
		
		Button btnMenuAction = new Button(shell, SWT.NONE);
		btnMenuAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String sku = skuIn.getText();
				if(listOptions.getText().equals("Add textbook")) {
					boolean errorFlag = false;
					String errorMsg = "";
					// RegEx expression for money is from https://stackoverflow.com/a/354276
					String moneyRegex = "^\\$?\\-?([1-9]{1}[0-9]{0,2}(\\,\\d{3})*(\\.\\d{0,2})?|[1-9]{1}\\d{0,}(\\.\\d{0,2})?|0(\\.\\d{0,2})?|(\\.\\d{1,2}))$|^\\-?\\$?([1-9]{1}\\d{0,2}(\\,\\d{3})*(\\.\\d{0,2})?|[1-9]{1}\\d{0,}(\\.\\d{0,2})?|0(\\.\\d{0,2})?|(\\.\\d{1,2}))$|^\\(\\$?([1-9]{1}\\d{0,2}(\\,\\d{3})*(\\.\\d{0,2})?|[1-9]{1}\\d{0,}(\\.\\d{0,2})?|0(\\.\\d{0,2})?|(\\.\\d{1,2}))\\)$";
					if(!checkSKU(sku)) 
					{
						errorMsg += "Invalid SKU!\n";
						errorFlag = true;
					}
					if (!quantityIn.getText().matches("[0-9]+"))
					{
						errorMsg += "Invalid quantity!\n";
						errorFlag = true;
					}
					if(!priceIn.getText().matches(moneyRegex))
					{
						errorMsg += "Invalid price!\n";
						errorFlag = true;
					}
					if(errorFlag) {
						outputBox.setText(errorMsg);
						return;
					}
					Integer skuInt = Integer.parseInt(sku);
					if(inventory.containsKey(skuInt)) {
						outputBox.setText("SKU already exists in inventory!");
						return;
					}
					else {
						Textbook t = new Textbook(skuInt , titleIn.getText(), Double.parseDouble(priceIn.getText()), Integer.parseInt(quantityIn.getText()));
						inventory.put(skuInt,  t);
						outputBox.setText(skuInt + " has been added to inventory.");
					}
				}
				else  {
					if(!checkSKU(sku)) {
						outputBox.setText("Invalid SKU!");
						return;
					}
					else if(!inventory.containsKey(Integer.parseInt(sku))) {
						outputBox.setText("SKU does not exist in inventory!");
						return;
					}
					Integer skuInt = Integer.parseInt(sku);
					if(listOptions.getText().equals("Remove textbook")) {
						inventory.remove(skuInt);
						outputBox.setText(skuInt + " has been removed from inventory.");
					}
					else if(listOptions.getText().equals("Display textbook")) {
						outputBox.setText(inventory.get(skuInt).print());
					}
				}
				
			}
		});
		btnMenuAction.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnMenuAction.setBounds(300, 100, 100, 29);
		btnMenuAction.setText("Take Action");
		
		Button btnInventory = new Button(shell, SWT.NONE);
		btnInventory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String allInventory = "";
				
				Iterator<Textbook> itr = inventory.values().iterator();
				
				while(itr.hasNext()) {
					allInventory += itr.next().print();
				}
				
				outputBox.setText(allInventory);
			}
		});
		btnInventory.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnInventory.setBounds(165, 135, 125, 29);
		btnInventory.setText("Show Inventory");

	}
}
