
import java.io.*;
import java.net.*;
import java.util.*;
// by Taha ( White Druid ) 
// in class file ro az adres download mikone
public class Download extends Observable implements Runnable {

	// hadaxr sorat download 
		private static final int MAX_BUFFER_SIZE = 10024 ;
			
		// Just names
		public static final String STATUSES[] = {"Downloading","Paused", "Complete", "Cancelled", "Error"};
	
		// code hay status
		public static final int DOWNLODING = 0;
		public static final int PAUSED = 1;
		public static final int COMPLETE = 2;
		public static final int CANCELLED = 3;
		public static final int ERROR = 4;
	
		// addres of download 
		private URL url;
		//size of download in bytes
		private int size;
		// number of bytes downloaded
		private int Downloaded;
		// current status of download
		private int status;
		
		// Constructor baray class  Download
		public Download(URL url) {
	        this.url = url;
	        size = -1;
	        Downloaded = 0;
	        status = DOWNLODING;
	         
	        // Begin the download.
	        download();
	    }
		
		// Get download url 
			public String getUrl() {
				return url.toString();
			}
		
		// get download size
			public int getSize() {
				return size;
			}
			
		// get the donwload progress
			public float getProgress() {
				return ((float) Downloaded / size ) * 100 ;
			}

		// get download status
			public int getStatus() {
				return status;
			}
			
		// Pause download 
			public void pause() {
				status = PAUSED ;
				stateChanged();
			}
		// Resume download 
			public void resume() {
				status = DOWNLODING;
				stateChanged();
				download();
			}

		// Cancel download 
			public void cancle() {
				status = CANCELLED ;
				stateChanged();
			}
		// Error with download	
			public void error() {
				status = ERROR ;
				stateChanged();
			}
		
		// statrt ot resume downloding 
		private void download() {
		Thread thread = new Thread(this);
		thread.start();
		}
		
		// get name this file of url 
		private String getFileName(URL url) {
			String fileName = url.getFile();
			return fileName.substring(fileName.lastIndexOf('/'));
		}
		
		// download file 
		public void run() {
		 RandomAccessFile file = null ; 
		 InputStream stream = null ;
		 	try { 
		 		   // open connection to url 
		 		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 		   // Specify what portion of file to download.
		 		connection.setRequestProperty("Range" , "bytes" + Downloaded+ "-");
		 		   // Connect to server.
		 		connection.connect();
		 		
		 		// make sure response code is in the 200 range 
		 		if (connection.getResponseCode() / 100 !=2 ) {
		 			error();
		 			}
		 		  // Check for valid content length.
		 		int  contentLength =  connection.getContentLength();	
		 		if ( contentLength < 1 ) {
		 			error();
		 		}
		 		
		 		if (size == -1 ) {
		 			size = contentLength;
		 			stateChanged();
		 		}
		 		
		 	 // Open file and seek to the end of it
		 		file = new RandomAccessFile(getFileName(url) , "rw");
		 		file.seek(Downloaded);	
		 		stream = connection.getInputStream();
		 		while (status == DOWNLODING){
		 			 /* Size buffer according to how much of the
		            file is left to download. */
		 			byte buffer[] ;
		 					if (size - Downloaded > MAX_BUFFER_SIZE ) {
		 						buffer = new byte[MAX_BUFFER_SIZE];
		 					} else {
		 						buffer = new byte[size - Downloaded ];
		 					}
		 					
		 	 // Read from server into buffer.
		 			int read = stream.read(buffer);
		 			if ( read == -1) {
		 				break;
		 			}
		 					
		 	 // write 	buffer to file 
		 			file.write(buffer , 0 , read);
		 				Downloaded += read; 
		 				stateChanged();		
		 		}
		 		
		 		if (status == DOWNLODING ) {
		 			status = COMPLETE ;
		 			stateChanged();
		 		}
		 	}   catch (Exception e){
		 		error();
		 	} finally { 
		 		// close file
		 			if(file != null ) {
		 				try {
		 					file.close();
		 				} catch (Exception e) {	
		 			}
		 		}
		 			// close the connection to the server
		 			 if (stream != null ) {
		 				 try { 
		 					 stream.close();
		 				 } catch (Exception e){					 
		 			 }
		 		 }
		 	}
		}
		//Notify observers that this download's status has changed.
	    private void stateChanged() {
			setChanged();
			notifyObservers();
		}
	
}
