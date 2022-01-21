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

import service.PhoneService;
import service.UserService;
import beans.Machine;
import beans.User;
import beans.phone;
import mainView.MainView;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;



public class GestionPhones extends JFrame {

	private JPanel contentPane;
	private JTextField a_prov;
	private JTextField a_num;
	private JTextField a_isa;
	private JTextField s_id;
	private JTextField m_provider;
	private JTextField m_number;
	private JTextField m_isadm;
	private JTextField m_userid;
	private JTable table;
	private JTextField h_id;
	private JTextField m_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionPhones frame = new GestionPhones();
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
	
	
	
	public GestionPhones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1083, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		
		JTable table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table_1.getModel();
				int selectedRowIndex = table_1.getSelectedRow();
				h_id.setText(model.getValueAt(selectedRowIndex, 0).toString());	
			}
		});
		
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"New column", "New column", "New column"
				}
			));
		
		DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
		
		UserService ms = new UserService();
		model1.setRowCount(0);
		model1.addRow(new Object[]{"id", "firtname", "lastname"});
        for(User u : ms.findAll()) {
        	model1.addRow(new Object[]{u.getId(),u.getFirsname(),u.getLastname()});
        }
		
		
		JButton btnSuprimer = new JButton("Suprimer");
		btnSuprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id   = s_id.getText();
				if(id.equals("")) {
					JOptionPane.showMessageDialog(null, "veuillez remplir tous les champs.");
				}else {
					int _id = Integer.parseInt(id);
					PhoneService ms = new PhoneService();
					ms.delete(new phone(_id,"","",0,""));
					JOptionPane.showMessageDialog(null, "Phone Deleted.");
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
					.addContainerGap(45, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(lblId)
							.addGap(31)
							.addComponent(s_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(btnSuprimer)
							.addGap(66))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(s_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId))
					.addGap(73)
					.addComponent(btnSuprimer)
					.addContainerGap(137, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		
		JLabel lblNewLabel_4 = new JLabel("Provider");
		
		m_provider = new JTextField();
		m_provider.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Number");
		
		m_number = new JTextField();
		m_number.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("isAdmin");
		
		m_isadm = new JTextField();
		m_isadm.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Userid");
		
		m_userid = new JTextField();
		m_userid.setColumns(10);
		
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String prov = m_provider.getText();
				String num = m_number.getText();
				String isadm = m_isadm.getText();
				String userid = m_userid.getText();
				String id = m_id.getText();
				if(prov.equals("") || num.equals("") || isadm.equals("")|| userid.equals("")) {
					JOptionPane.showMessageDialog(null, "veuillez remplir tous les champs.");
				}else {
					PhoneService ms = new PhoneService();
					
					int _id = Integer.parseInt(id);
					
					ms.update(new phone(_id, prov,num,Integer.parseInt(userid) ,isadm));
					JOptionPane.showMessageDialog(null, "Phone Modifier.");
				}
			}
		});
		
		JLabel sd = new JLabel("id");
		
		m_id = new JTextField();
		m_id.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(25, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_4)
							.addGap(21)
							.addComponent(m_provider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_1_1)
									.addGap(23))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_2_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(m_number)
								.addComponent(m_isadm)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1)
								.addComponent(sd, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addGap(40)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(m_id, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(m_userid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(68)
					.addComponent(btnModifier)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(m_provider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_number, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_isadm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3_1)
						.addComponent(m_userid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(sd)
						.addComponent(m_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addComponent(btnModifier)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String prov = a_prov.getText();
				String num = a_num.getText();
				String userid = h_id.getText();
				String isa = a_isa.getText();

				if(prov.equals("") || num.equals("") ||  userid.equals("") || isa.equals("")) {
					
				}else {
					PhoneService ms = new PhoneService();
					ms.create(new phone(prov,num,Integer.parseInt(userid),isa));
					JOptionPane.showMessageDialog(null, "Phone Created.");
				}
			}
		});
		
		a_prov = new JTextField();
		a_prov.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Provider");
		
		JLabel lblNewLabel_1 = new JLabel("Number");
		
		a_num = new JTextField();
		a_num.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("isAdmin");
		
		a_isa = new JTextField();
		a_isa.setColumns(10);
		

		
		JLabel lblNewLabel_3_2 = new JLabel("User");
		
		h_id = new JTextField();
		h_id.setColumns(10);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblNewLabel_1)
							.addComponent(lblNewLabel))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblNewLabel_3)
							.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(h_id, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(table_1, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
						.addComponent(a_isa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(a_prov, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(a_num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(176, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addGap(169))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(a_prov, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(a_num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(a_isa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(table_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_2)
						.addComponent(h_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addGap(30))
		);
		panel.setLayout(gl_panel);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				 m_id.setText(model.getValueAt(selectedRowIndex, 0).toString());
			     m_provider.setText(model.getValueAt(selectedRowIndex, 1).toString());
			     m_number.setText(model.getValueAt(selectedRowIndex, 2).toString());
			     m_userid.setText(model.getValueAt(selectedRowIndex, 3).toString());
			     m_isadm.setText(model.getValueAt(selectedRowIndex, 4).toString());
			     s_id.setText(model.getValueAt(selectedRowIndex, 0).toString());
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"New column", "New column", "New column", "New column", "New column"
				}
			));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		JButton btnNewButton_1 = new JButton("R");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.getActionCommand());
				PhoneService ms = new PhoneService();
				model.setRowCount(0);
		        model.addRow(new Object[]{"id", "provider", "number", "userid", "isadmin"});
		        for(phone u : ms.findAll()) {
		        	model.addRow(new Object[]{u.getId(),u.getProvider(),u.getNumber(),u.getUserId(), u.getIsAdmin()});
		        }

			}
		});
		
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
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
									.addComponent(btnReturn)
									.addGap(25))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(1018, Short.MAX_VALUE)
							.addComponent(btnNewButton_1)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_1, 0, 0, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(31)
							.addComponent(btnReturn)))
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
