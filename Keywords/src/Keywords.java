import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashSet;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Keywords {

	protected Shell shell;
	JFileChooser fn = new JFileChooser();
	HashSet<String> keywords = new HashSet<String>();
	Timer stopWatch = new Timer();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Keywords window = new Keywords();
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
	
	public int countKeywords(String s) {
		String[] words = s.split(" ");
		int num_found = 0;
		boolean parsingString = false;
		
		for( String word : words){
			// Check for and ignore words in quotes
			if(word.contains("\"") || parsingString) {
				if(word.contains("\"") && parsingString) {
					parsingString = false;
					continue;
				}
				parsingString = true;
				continue;
			}
			// If there is a semicolon check prior word and then break (end of statement)
			if(word.contains(";")) {
				word = word.split(";")[0];
				if(keywords.contains(word)) {
					num_found++;
				}
				break;
			}
			if(word.contains("(")) {
				// To catch something like for(int i...
				String[] smushed_words = word.split("[()]");
				for(String unsmushed: smushed_words) {
					if(keywords.contains(unsmushed)) {
						num_found++;
					}
				}
				continue;
			}
			if(keywords.contains(word)) {
				num_found++;
			}
		}
		
		return num_found;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setVisible(true);
		shell.setSize(450, 300);
		shell.setText("Keyword Counter");
		
		Label lblFilePath = new Label(shell, SWT.NONE);
		lblFilePath.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblFilePath.setBounds(10, 54, 300, 25);
		
		Label lblKeywords = new Label(shell, SWT.NONE);
		lblKeywords.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblKeywords.setBounds(10, 23, 300, 25);
		

		Label errorLbl = new Label(shell, SWT.NONE);
		errorLbl.setVisible(false);
		errorLbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		errorLbl.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		errorLbl.setBounds(10, 85, 188, 25);
		errorLbl.setText("Error: No file Selected!");
		
		Label lblCompleted = new Label(shell, SWT.NONE);
		lblCompleted.setVisible(false);
		lblCompleted.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblCompleted.setBounds(10, 116, 300, 36);
		lblCompleted.setText("Statistics Written to File");
		
		// Only allows for the selection of text files
		fn.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter codeFilter = new FileNameExtensionFilter("Java files", "java");
		FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Keyword text file", "txt");
						
		Button btnChooseFile = new Button(shell, SWT.NONE);
		btnChooseFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				fn.addChoosableFileFilter(codeFilter);
				// Reset state
				errorLbl.setVisible(false);
				lblCompleted.setVisible(false);
				// Initialize Counter Variables
				Integer codeLines = 0;
				Integer keywordsSeen = 0;
				// Open JFileChooser
				int fileChooserFlag = fn.showOpenDialog(null);

				if (fileChooserFlag == JFileChooser.APPROVE_OPTION) {
					stopWatch.startTimer();
					java.io.File file = fn.getSelectedFile();
					lblFilePath.setText(file.getAbsolutePath());
					Scanner scan;
					try {
						scan = new Scanner(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						fn.removeChoosableFileFilter(codeFilter);
						return;
					}
					
					String s;
					
					while(scan.hasNextLine()) {
						s = scan.nextLine();
						s = s.trim();
						if(s.equals("")) {
							continue;
						}
						String commentCheck = "";
						if(s.length() >= 2) {
							commentCheck = s.substring(0, 2);
						}
						
						if(commentCheck.equals("//")) {
							continue;
						}
						else if ((s.contains("/*") && !s.contains("\"")) || (s.contains("/*") && ((s.indexOf("/*") < s.indexOf("\"") || s.lastIndexOf("/*") > s.lastIndexOf("\""))))) {
							if(!commentCheck.equals("/*")) {
								keywordsSeen += countKeywords(s.split("/*")[0]);
								codeLines++;
							}
							while(!s.contains("*/")) {
								if(!scan.hasNextLine()) {
									break;
								}
								s= scan.nextLine();
							}
							
							// Check for poor style where code occurs in line w/ end of block comment
							int checkEndofBlock = s.indexOf("*/")+2;
							if(checkEndofBlock < s.length()) {
								// Make sure this poor styling is not just more comments
								if(s.substring(checkEndofBlock).contains("//")) {
									continue;
								}
								else if(s.substring(checkEndofBlock).contains("/*")){
									while(!s.contains("*/")) {
										if(!scan.hasNextLine()) {
											break;
										}
										s= scan.nextLine();
									}
								}
								// if not a comment then we have a poorly placed line of code
								else {
									keywordsSeen += countKeywords(s.split("*/")[1]);
									codeLines++;
								}
							}
							continue;
						}
						keywordsSeen += countKeywords(s);
						codeLines++;
					}
					scan.close();
					stopWatch.stopTimer();
					// Write stats to file
					try {
						File resultsFile = new File("results.txt");
						resultsFile.createNewFile();
						String content = "Lines of Code read:\t";
						content= content + Integer.toString(codeLines) + "\nKeywords Found:\t" + Integer.toString(keywordsSeen) + "\nTime Elapsed:\t" + stopWatch.getTime() + " milliseconds\n";
						FileWriter fw = new FileWriter(resultsFile.getAbsolutePath());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(content);
						bw.close();
						lblCompleted.setVisible(true);
					}
					catch (IOException e2) {
						e2.printStackTrace();
						fn.removeChoosableFileFilter(codeFilter);
						return;
					}
				} else {
					errorLbl.setVisible(true);
				}
				
				fn.removeChoosableFileFilter(codeFilter);
			}
		});
		btnChooseFile.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnChooseFile.setBounds(316, 56, 105, 25);
		btnChooseFile.setText("Select Code");
		
		Button btnChooseKeywords = new Button(shell, SWT.NONE);
		btnChooseKeywords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				fn.addChoosableFileFilter(textFilter);
				// Reset state
				errorLbl.setVisible(false);
				lblCompleted.setVisible(false);
				stopWatch.resetTimer();
				// Clear HashSet
				keywords.clear();
				// Open JFileChooser
				int fileChooserFlag = fn.showOpenDialog(null);

				if (fileChooserFlag == JFileChooser.APPROVE_OPTION) {
					stopWatch.startTimer();
					java.io.File file = fn.getSelectedFile();
					lblKeywords.setText(file.getAbsolutePath());
					Scanner scan;
					try {
						scan = new Scanner(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						fn.removeChoosableFileFilter(textFilter);
						return;
					}
					
					String s;
					
					while(scan.hasNextLine()) {
						// Read each key word into HashSet
						s = scan.nextLine();
						keywords.add(s);
					}
					stopWatch.stopTimer();
					scan.close();
				} else {
					errorLbl.setVisible(true);
				}
				
				
				fn.removeChoosableFileFilter(textFilter);
			}
		});
		btnChooseKeywords.setText("Select Keywords");
		btnChooseKeywords.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnChooseKeywords.setBounds(316, 20, 105, 25);

	}
}
