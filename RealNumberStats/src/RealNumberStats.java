import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class RealNumberStats {

	protected Shell shell;
	private Text pathValue;
	JFileChooser fn = new JFileChooser();
	LinkedList values;
	private Label lblMeanOfValues;
	private Label meanValue;
	private Label lblstdDevValues;
	private Label stdDevValue;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RealNumberStats window = new RealNumberStats();
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
		shell.setText("File Calculations");

		pathValue = new Text(shell, SWT.BORDER);
		pathValue.setEnabled(false);
		pathValue.setEditable(false);
		pathValue.setBounds(10, 50, 307, 21);

		Label errorLbl = new Label(shell, SWT.NONE);
		errorLbl.setVisible(false);
		errorLbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		errorLbl.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.NORMAL));
		errorLbl.setBounds(10, 87, 312, 37);
		
		// Only allows for the selection of text files
		fn.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text files", "txt");
		fn.addChoosableFileFilter(textFilter);

		Button btnChooseFile = new Button(shell, SWT.NONE);
		btnChooseFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// Remove any previous error message
				errorLbl.setText("");
				errorLbl.setVisible(false);
				
				// Open JFileChooser
				int fileChooserFlag = fn.showOpenDialog(null);

				if (fileChooserFlag == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fn.getSelectedFile();
					String filepath = file.getAbsolutePath();
					// Try catch just in case they somehow got around the filter
					try {
						String sanityCheck = filepath.substring(filepath.length() - 4);
						assert  sanityCheck.equals(".txt");
					} catch (AssertionError err) {
						errorLbl.setText("Invalid File Type!");
						errorLbl.setVisible(true);
						return;
					}
					pathValue.setText(filepath);
					ArrayList<Double> readinValues = new ArrayList<Double>();
					// Try reading the file
					try (FileReader freader = new FileReader(file)) {
						BufferedReader br = new BufferedReader(freader);
						String s;
						// This program accepts whole numbers floats and fractions per line
						// No mathematical constants (such as the pi character)
						while ((s = br.readLine()) != null) {
							Double value;
							// Check if fraction
							if (s.contains("/")) {
								int divPos = s.indexOf('/');
								// Check for invalid characters it still reads numbers appended with d or f, since it is interpreted as
								// formatting. I am ok with this
								try {
									value = Double.parseDouble(s.substring(0, divPos))
											/ Double.parseDouble(s.substring(divPos + 1));
								} catch (NumberFormatException excep) {
									errorLbl.setText("Invalid Number Format");
									errorLbl.setVisible(true);
									freader.close();
									return;
								}
							}

							else {
								// Check for invalid characters it still reads numbers appended with d or f, since it is interpreted as
								// formatting. I am ok with this
								try {
									value = Double.parseDouble(s);
								} catch (NumberFormatException excep) {
									errorLbl.setText("Invalid Number Format");
									errorLbl.setVisible(true);
									freader.close();
									return;
								}
							}

							readinValues.add(value);
						}
						values = new LinkedList(readinValues);
						freader.close();
					} catch (IOException excep) {
						errorLbl.setText("Error Opening File");
						errorLbl.setVisible(true);
						excep.printStackTrace();
					}

					if (!values.isEmpty()) {
						meanValue.setText(Double.toString(values.getMean()));
						stdDevValue.setText(Double.toString(values.getStdDev()));
					}
				} else {
					errorLbl.setText("No File Selected");
					errorLbl.setVisible(true);
				}
				
				// Not sure if Java garbage collector will clean up if I don't do this?
				values = null;
			}
		});
		btnChooseFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnChooseFile.setBounds(331, 50, 93, 25);
		btnChooseFile.setText("Select Text File");

		lblMeanOfValues = new Label(shell, SWT.NONE);
		lblMeanOfValues.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblMeanOfValues.setBounds(10, 130, 121, 30);
		lblMeanOfValues.setText("Mean of Values:");

		meanValue = new Label(shell, SWT.NONE);
		meanValue.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		meanValue.setBounds(137, 130, 287, 30);

		lblstdDevValues = new Label(shell, SWT.NONE);
		lblstdDevValues.setText("Mean of Values:");
		lblstdDevValues.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblstdDevValues.setBounds(10, 184, 121, 30);

		stdDevValue = new Label(shell, SWT.NONE);
		stdDevValue.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		stdDevValue.setBounds(137, 184, 287, 30);
	}
}
