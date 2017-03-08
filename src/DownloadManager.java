		import java.awt.event.ActionEvent;
		import java.awt.event.ActionListener;
		import java.awt.event.KeyEvent;
		import java.awt.event.WindowAdapter;
		import java.awt.event.WindowEvent;
		import java.awt.event.WindowListener;
		import java.util.Observable;
		import java.util.Observer;
		import javax.swing.JButton;
		import javax.swing.JFrame;
		import javax.swing.JMenu;
		import javax.swing.JMenuBar;
		import javax.swing.JMenuItem;
		import javax.swing.JPanel;
		import javax.swing.JTable;
		import javax.swing.JTextField;
	
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
	  			
	  		
	  		
	  		
	  }
	  
	private JTextField JTextField(int i) {
		return null;
	}
	
	private void actionExit() {	
	}
	
	private void actionAdd() {
	
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	
	}
	
	public static void main(String[] args) {
	
		}

}