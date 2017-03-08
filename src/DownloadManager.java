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
	  private JButton cancleButton, clearButton;
	  
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
	  			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
	  	        downloadsPanel.add(new JScrollPane(table) , BorderLayout.CENTER );
	  	      
	  	   // Set up buttons panel.
	  	        JPanel buttonsPanel = new JPanel();
	  	        pauseButton = new JButton("Pause");
	  	        pauseButton.addActionListener(new ActionListener() {
	  	        	public void actionPerformed(ActionEvent e) {
	  	        		actionPause();
	  	        	}
	  	        });
	  }
	  
	private JTextField JTextField(int i) {
		return null;
	}
	
	private void actionExit() {	
	}
	
	private void actionAdd() {
	
	}
	
	private void actionPause() {
		
	}
	
	private void tableSelectionChanged() {
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
	
		}



}