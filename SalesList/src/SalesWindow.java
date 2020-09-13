import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class SalesWindow {

	protected Shell shell;
	private Text itemField;
	private Text costField;
	private Text text;
	private Text text_1;

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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
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
		
		costField = new Text(shell, SWT.BORDER);
		costField.setBounds(101, 82, 102, 21);
		
		text = new Text(shell, SWT.BORDER);
		text.setText("");
		text.setBounds(97, 117, 106, 21);
		
		Button addItemBtn = new Button(shell, SWT.NONE);
		addItemBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
			}
		});
		addItemBtn.setBounds(40, 153, 250, 21);
		addItemBtn.setText("Add Item to the Sales List");
		
		text_1 = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
		text_1.setBounds(40, 181, 338, 70);

	}
}
