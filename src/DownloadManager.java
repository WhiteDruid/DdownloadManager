import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
		
	// by Taha ( White druid ) 
	public class DownloadManager  extends JFrame implements Observer {
		
	// add download text filed
	  private JTextField addTextField;
	  
	// Download tables data model 
	  private DownloadsTableModel tableModel;
	  
	// Table listing Downloads
	  private JTable table;
	  
	 // These are the tuttons for managine the selected downlaod . 
	  private JButton pauseButton, resumeButton;
	  private JButton cancelButton, clearButton;
	  
	 // Currently selected download 
	  private Download selectedDownload;
	  
	// Flag for whether or not table selection is being cleared
	  private boolean clearing;
	  
	// Constructor for Download Manager.
	  public DownloadManager() {
		  // app title 
		  setTitle("Download&Manage");
		  // set size of window
		  setSize(640 , 480);
		  //handel window closing 
		  addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				  actionExit();
		  }
	});
	  // set up file menu 
	  JMenuBar menuBar = new JMenuBar();
	  JMenu fileMenu = new JMenu("File");
	  fileMenu.setMnemonic(KeyEvent.VK_F );
	  JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
	  fileExitMenuItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            actionExit();
	        }
	    });
	  		fileMenu.add(fileExitMenuItem);
	  		menuBar.add(fileMenu);
	  		setJMenuBar(menuBar);
	  		
	  		
	  		//Set up panel 
		  		JPanel addPanle = new JPanel();
		  		addTextField = JTextField(30);
		  		addPanle.add(addTextField);
		  		JButton addButton = new JButton("add download");
		  		addButton.addActionListener(new ActionListener() {
		  			public void actionPerformed(ActionEvent e) {
		  					actionAdd();
		  			}
		  		});
		  		addPanle.add(addButton);
	  		
	  		// set up download tabel 
		        tableModel = new DownloadsTableModel();
		        table = new JTable(tableModel);
		        table.getSelectionModel().addListSelectionListener(new
		                ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent e) {
		                tableSelectionChanged();
		            }
		        });
	  			
	  		// Allow only one row at a time to be selected 
	  			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  		
	  		// Set up ProgressBar as renderer for progress column.
	  	       ProgressRenderer renderer = new ProgressRenderer(0, 100);
	  	        renderer.setStringPainted(true); 
	  	        // show progress text
	  	        table.setDefaultRenderer(JProgressBar.class, renderer);
	  		   
	  	    // Set table's row height large enough to fit JProgressBar
	  	        table.setRowHeight(
	  	        		(int) renderer.getPreferredSize().getHeight());
	  	        
	  	    // set Download panel
	  	        JPanel downloadsPanel = new JPanel();
	  	        downloadsPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
	  	        downloadsPanel.setLayout(new BorderLayout());
	  	        downloadsPanel.add(new JScrollPane(table), BorderLayout.CENTER);
	  	      
	  	   // Set up buttons panel.
	  	        JPanel buttonsPanel = new JPanel();
	  	        pauseButton = new JButton("Pause");
	  	        pauseButton.addActionListener(new ActionListener() {
	  	        	public void actionPerformed(ActionEvent e) {
	  	        		actionPause();
	  	        	}
	  	        });
	  	
	  	        // resume button
	  	        pauseButton.setEnabled(false);
	  	        buttonsPanel.add(pauseButton);
	  	        resumeButton = new JButton("Resume");
	  	     	resumeButton.addActionListener(new ActionListener() {
	  	        public void actionPerformed(ActionEvent e) {
	  	        actionResume();
	  	     	}
	  	    });
	  	     	
	  	     	//cancel buuton 
		  	      resumeButton.setEnabled(false);
		          buttonsPanel.add(resumeButton);
		          cancelButton = new JButton("Cancel");
		          cancelButton.addActionListener(new ActionListener() {
		              public void actionPerformed(ActionEvent e) {
		                  actionCancel();
		              }
		          });
		        
		         //clear Button
		          cancelButton.setEnabled(false);
		          buttonsPanel.add(cancelButton);
		          clearButton = new JButton("clear");
		          clearButton.addActionListener(new ActionListener() {
		        	  public void actionPerformed(ActionEvent e){
		        		actionClear();  
		        	  }
		          });
		          clearButton.setEnabled(false);
		          buttonsPanel.add(clearButton);
	  	     	
		          //add panel to display 
		          getContentPane().setLayout(new BorderLayout());
		          getContentPane().add(addPanle , BorderLayout.NORTH);
		          getContentPane().add(downloadsPanel, BorderLayout.CENTER);
		          getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		       
	  }



	// Exit form this program 
	private void actionExit() {	
			System.exit(0);
	}
	
	//add new download
	private void actionAdd() {
			URL verifiedUrl = verifyUrl(addTextField.getText());
			if (verifiedUrl != null) {
					tableModel.addDownload(new Download(verifiedUrl));
					addTextField.setText("");
					// reset add text field
			} else { 
				JOptionPane.showMessageDialog(this , "invalid download url" , "Error" , 
						JOptionPane.ERROR_MESSAGE ); 
			}
		}
 
	
	private URL verifyUrl(String url) {
	      // Only allow HTTP
        if (!url.toLowerCase().startsWith("http://"))
            return null;
         
	        // Verify format url
	        URL verifiedUrl = null;
	        try {
	            verifiedUrl = new URL(url);
	        } catch (Exception e) {
	            return null;
	        }
         
        // Make sure URL specifies a file.
        if (verifiedUrl.getFile().length() < 2)
            return null;
         
        return verifiedUrl;
	}


	// Pause the selected download.
	private void actionPause() {
		selectedDownload.pause();
		updateButtons();
	}
	
    // Resume the selected download.
	private void actionResume() {
		selectedDownload.resume();
		updateButtons();
	}
	
	private void tableSelectionChanged() {
	    /* Unregister from receiving notifications
	       from the last selected download. */
		if (selectedDownload != null ) 
				selectedDownload.deleteObserver(DownloadManager.this);
		
	    /* If not in the middle of clearing a download,
	       set the selected download and register to
	       receive notifications from it. */
		if(!clearing){
			selectedDownload = tableModel.getDownload(table.getSelectedRow());	
			selectedDownload.addObserver(DownloadManager.this);
			updateButtons();	
		}
	}
	
    // Cancel the selected download.
	 private void actionCancel() {
	        selectedDownload.cancle();
	        updateButtons();
	   }
	
	// Clear the selected download.
	private void actionClear() {
		clearing = true;
		tableModel.clearDownload(table.getSelectedRow());
		clearing = false ;
		selectedDownload = null ;
        updateButtons();
	}
	
	@Override
	  /* Update is called when a Download notifies its
    observers of any changes. */
	public void update(Observable o, Object arg) {
		if(selectedDownload != null && selectedDownload.equals(o))
			updateButtons();
	}
	
	  /* Update each button's state based off of the
    currently selected download's status. */
	private void updateButtons() {
		if (selectedDownload != null ) {
			int status = selectedDownload.getStatus();
			switch(status) {
			case Download.DOWNLODING:
				pauseButton.setEnabled(true);
				resumeButton.setEnabled(false);
				cancelButton.setEnabled(true);
				clearButton.setEnabled(false);
					break;
			case Download.PAUSED:
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
                cancelButton.setEnabled(true);
                clearButton.setEnabled(false);
					break;
						
			case Download.ERROR:
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
                cancelButton.setEnabled(false);
                clearButton.setEnabled(true);
					break;
			default: //COMPLETE or CANCELLED 
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(false);
                cancelButton.setEnabled(false);
                clearButton.setEnabled(true);	
			}
		}
			else {
	            // No download is selected in table.
				pauseButton.setEnabled(false);
		        resumeButton.setEnabled(false);
		        cancelButton.setEnabled(false);
		        clearButton.setEnabled(false);
		}
	}
	
	public static void main(String[] args) {
	
	    DownloadManager manager = new DownloadManager();
        manager.show();
		
	}
}
