import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ProgressRenderer extends JProgressBar implements TableCellRenderer {
	
		public ProgressRenderer(int min , int max) {
			super(min , max);
		}
		
		/* Returns this JProgressBar as the renderer
	     for the given table cell. */
		public Component  getTableRenderComponent( JTable table , Object value , boolean isSelected ,
				 boolean hasFocus , int row , int colum ) {
		       // Set JProgressBar's percent complete value.
			         setValue((int) ((Float) value).floatValue());
					return this;
			
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			return null;
		}		
		
	}
