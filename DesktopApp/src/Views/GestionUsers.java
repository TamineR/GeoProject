package Views;

import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;



import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.UserService;
import beans.Machine;
import beans.User;
import mainView.MainView;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class GestionUsers extends JFrame {

	private JPanel contentPane;
	private JTextField a_fname;
	private JTextField a_lname;
	private JTextField a_age;
	private JTextField a_sexe;
	private JTextField s_id;
	private JTextField m_fname;
	private JTextField m_lname;
	private JTextField m_age;
	private JTextField m_sex;
	private JTextField m_addr;
	private JTable table;
	private JTextField a_addr;
	private JTextField m_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionUsers frame = new GestionUsers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestionUsers() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1083, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		
		JButton btnSuprimer = new JButton("Suprimer");
		btnSuprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id   = s_id.getText();
				if(id.equals("")) {
					JOptionPane.showMessageDialog(null, "veuillez remplir tous les champs.");
				}else {
					int _id = Integer.parseInt(id);
					UserService ms = new UserService();
					ms.delete(new User(_id,"","",0,"",""));
					JOptionPane.showMessageDialog(null, "User Deleted.");
				}
			}
		});
		

		
		JLabel lblId = new JLabel("id");
		
		s_id = new JTextField();
		s_id.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(47)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(22)
							.addComponent(btnSuprimer)
							.addContainerGap(64, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblId)
							.addGap(31)
							.addComponent(s_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(28))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(80, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(s_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId))
					.addGap(59)
					.addComponent(btnSuprimer)
					.addGap(30))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		
		JLabel lblNewLabel_4 = new JLabel("Firstname");
		
		m_fname = new JTextField();
		m_fname.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Lastname");
		
		m_lname = new JTextField();
		m_lname.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Age");
		
		m_age = new JTextField();
		m_age.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Sex");
		
		m_sex = new JTextField();
		m_sex.setColumns(10);
		
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fname = m_fname.getText();
				String lname = m_lname.getText();
				String id = m_id.getText();
				String age = m_age.getText();
				int a = Integer.parseInt(age);
				String sex = m_sex.getText();
				String addr   = m_addr.getText();
				
				if(fname.equals("") || lname.equals("") || id.equals("") || age.equals("")|| sex.equals("")|| addr.equals("")) {
					JOptionPane.showMessageDialog(null, "veuillez remplir tous les champs.");
				}else {
					UserService ms = new UserService();
					int _id = Integer.parseInt(id);
					
					ms.update(new User(_id, fname,lname,a ,sex, addr));
					JOptionPane.showMessageDialog(null, "User Modifier.");
				}
			}
		});
		
		m_addr = new JTextField();
		m_addr.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Adresse");
		
		m_id = new JTextField();
		m_id.setColumns(10);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("Id");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_4)
							.addGap(21)
							.addComponent(m_fname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_1_1)
									.addGap(23))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(m_lname)
								.addComponent(m_age)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_3_1)
							.addGap(28)
							.addComponent(m_sex, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_3_1_1)
								.addComponent(lblNewLabel_3_1_1_1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(m_addr)
								.addComponent(m_id, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(69)
					.addComponent(btnModifier)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(m_fname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_lname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_age, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_sex, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_addr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_1_1))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_1_1_1))
					.addGap(18)
					.addComponent(btnModifier)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fname = a_fname.getText();
				String lname = a_lname.getText();
				String age = a_age.getText();
				String sexe = a_sexe.getText();
				String addr = a_addr.getText();
				if(fname.equals("") || lname.equals("") || age.equals("") || sexe.equals("") || addr.equals("")) {
					
				}else {
					UserService ms = new UserService();
					int ag = Integer.parseInt(age);
					ms.create(new User(fname,lname,ag ,sexe, addr));
					JOptionPane.showMessageDialog(null, "U Created.");
				}
			}
		});
		
		a_fname = new JTextField();
		a_fname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Firstname");
		
		JLabel lblNewLabel_1 = new JLabel("lastname");
		
		a_lname = new JTextField();
		a_lname.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Age");
		
		a_age = new JTextField();
		a_age.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Sex");
		
		a_sexe = new JTextField();
		a_sexe.setColumns(10);
		
		a_addr = new JTextField();
		a_addr.setColumns(10);
		
		JLabel lblNewLabel_3_2 = new JLabel("Adresse");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(21)
							.addComponent(a_fname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(a_lname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(a_age, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addGap(18)
							.addComponent(a_sexe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(29, Short.MAX_VALUE)
					.addComponent(lblNewLabel_3_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(a_addr, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(70)
					.addComponent(btnNewButton)
					.addContainerGap(75, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(a_fname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(a_lname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_2))
						.addComponent(a_age, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_3))
						.addComponent(a_sexe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(a_addr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_2))
					.addGap(37)
					.addComponent(btnNewButton)
					.addContainerGap(62, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				 m_id.setText(model.getValueAt(selectedRowIndex, 0).toString());
			     m_fname.setText(model.getValueAt(selectedRowIndex, 1).toString());
			     m_lname.setText(model.getValueAt(selectedRowIndex, 2).toString());
			     m_sex.setText(model.getValueAt(selectedRowIndex, 3).toString());
			     m_age.setText(model.getValueAt(selectedRowIndex, 4).toString());
			     m_addr.setText(model.getValueAt(selectedRowIndex, 5).toString());
			     s_id.setText(model.getValueAt(selectedRowIndex, 0).toString());
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		JButton btnNewButton_1 = new JButton("R");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.getActionCommand());
				UserService ms = new UserService();
				model.setRowCount(0);
		        model.addRow(new Object[]{"id", "firtname", "lastname", "age", "sex", "addr"});
		        for(User u : ms.findAll()) {
		        	model.addRow(new Object[]{u.getId(),u.getFirsname(),u.getLastname(),u.getSex(), u.getAge(),u.getAddr()});
		        }

			}
		});
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBackground(Color.GRAY);
		
		JButton btnSuprimer_1_1 = new JButton("Plot");

		btnSuprimer_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			      BarChart_AWT_an chart = new BarChart_AWT_an();
			      chart.pack( );        
			      RefineryUtilities.centerFrameOnScreen( chart );        
			      chart.setVisible( true ); 	      
			}
		});
		
		JLabel lblNewLabel_6 = new JLabel("Users par age");
		
		JButton btnSuprimer_1_1_1 = new JButton("Plot");
		btnSuprimer_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			      BarChart_AWT_marque chart = new BarChart_AWT_marque();
			      chart.pack( );        
			      RefineryUtilities.centerFrameOnScreen( chart );        
			      chart.setVisible( true ); 
			}
		});
		
		JLabel lblNewLabel_6_1 = new JLabel("Users par sex");
		GroupLayout gl_panel_1_1_1 = new GroupLayout(panel_1_1_1);
		gl_panel_1_1_1.setHorizontalGroup(
			gl_panel_1_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1_1_1.createSequentialGroup()
					.addGroup(gl_panel_1_1_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1_1_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
						.addGroup(gl_panel_1_1_1.createSequentialGroup()
							.addGap(42)
							.addComponent(btnSuprimer_1_1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1_1_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1_1_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_6_1, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_1_1_1.createSequentialGroup()
									.addGap(30)
									.addComponent(btnSuprimer_1_1_1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_panel_1_1_1.setVerticalGroup(
			gl_panel_1_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1_1_1.createSequentialGroup()
					.addGap(34)
					.addComponent(lblNewLabel_6)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSuprimer_1_1)
					.addGap(18)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_6_1)
					.addGap(6)
					.addComponent(btnSuprimer_1_1_1)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_1_1_1.setLayout(gl_panel_1_1_1);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MainView().setVisible(true);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(table, GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
									.addGap(48)
									.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(82)
									.addComponent(panel_1_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
									.addComponent(btnReturn))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(1018, Short.MAX_VALUE)
							.addComponent(btnNewButton_1)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(panel_1_1_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
						.addComponent(btnReturn))
					.addGap(39)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(table, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
							.addGap(12))
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
