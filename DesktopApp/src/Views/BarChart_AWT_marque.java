package Views;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import service.UserService;


public class BarChart_AWT_marque extends JFrame {
	BarChart_AWT_marque() {
      final DefaultCategoryDataset dataset = 
    	      new DefaultCategoryDataset( );  
		UserService ms = new UserService();
		ResultSet rs = ms.findUserBySex();	
	    try {
			while (rs.next()) {
				dataset.addValue(rs.getInt("nbrS"),rs.getString("sex"),"");
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JFreeChart barChart = ChartFactory.createBarChart(
	            "Users par sex",           
	            "Sex",            
	            "Nombre users",            
	            dataset,          
	            PlotOrientation.VERTICAL,           
	            true, true, false);
	      ChartPanel chartPanel = new ChartPanel( barChart );        
	      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	      setContentPane( chartPanel ); 
	}
}

