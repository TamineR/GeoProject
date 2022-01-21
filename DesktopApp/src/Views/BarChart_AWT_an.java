package Views;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import service.UserService;

import org.jfree.data.category.CategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class BarChart_AWT_an extends JFrame {
	BarChart_AWT_an() {
	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
			UserService ms = new UserService();
			ResultSet rs = ms.findUserByAge();	
		    try {
				while (rs.next()) {
					dataset.addValue(rs.getInt("nbrA"),rs.getString("age"),"");
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    JFreeChart barChart = ChartFactory.createBarChart(
		            "Users par Age",           
		            "Age",            
		            "Nombre users",            
		            dataset,          
		            PlotOrientation.VERTICAL,           
		            true, true, false);
		      ChartPanel chartPanel = new ChartPanel( barChart );        
		      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
		      setContentPane( chartPanel ); 
	}
}
