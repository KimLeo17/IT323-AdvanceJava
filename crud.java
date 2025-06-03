package CRUD_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class crud extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldSearch;
	private JTextField textFieldLastname;
	private JTextField textFieldFirstname;
	private JTextField textFieldMiddleInitial;
	private JTextField textFieldStudentNumber;
	private String selectedStudentID;
	private JTable studentsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					crud frame = new crud();
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
	public crud() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, -32, 639, 596);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(246, 245, 244));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(214, 39, 114, 19);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setLabelFor(textFieldSearch);
		lblSearch.setBounds(162, 41, 70, 15);
		contentPane.add(lblSearch);
		
		JButton btnSearch = new JButton("Go");
		btnSearch.setBounds(326, 39, 117, 18);
		contentPane.add(btnSearch);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(141, 86, 70, 15);
		contentPane.add(lblLastname);
		
		textFieldLastname = new JTextField();
		lblLastname.setLabelFor(textFieldLastname);
		textFieldLastname.setBounds(262, 82, 114, 19);
		contentPane.add(textFieldLastname);
		textFieldLastname.setColumns(10);
		
		JLabel lblMiddleInitial = new JLabel("Middle Initial");
		lblMiddleInitial.setBounds(141, 146, 99, 15);
		contentPane.add(lblMiddleInitial);
		
		textFieldFirstname = new JTextField();
		textFieldFirstname.setColumns(10);
		textFieldFirstname.setBounds(262, 113, 114, 19);
		contentPane.add(textFieldFirstname);
		
		textFieldMiddleInitial = new JTextField();
		lblMiddleInitial.setLabelFor(textFieldMiddleInitial);
		textFieldMiddleInitial.setColumns(10);
		textFieldMiddleInitial.setBounds(262, 144, 114, 19);
		contentPane.add(textFieldMiddleInitial);
		
		textFieldStudentNumber = new JTextField();
		textFieldStudentNumber.setColumns(10);
		textFieldStudentNumber.setBounds(262, 175, 114, 19);
		contentPane.add(textFieldStudentNumber);
		
		JLabel lblFirststname = new JLabel("Firstname");
		lblFirststname.setLabelFor(textFieldFirstname);
		lblFirststname.setBounds(141, 119, 78, 15);
		contentPane.add(lblFirststname);
		
		JLabel lblLastname_1_2 = new JLabel("Student Number");
		lblLastname_1_2.setLabelFor(textFieldStudentNumber);
		lblLastname_1_2.setBounds(141, 177, 117, 15);
		contentPane.add(lblLastname_1_2);
		
		JLabel lblStudentManagementSystem = new JLabel("Student Management System");
		lblStudentManagementSystem.setBounds(196, 12, 216, 15);
		contentPane.add(lblStudentManagementSystem);
		
		JComboBox<String> comboBoxCourse = new JComboBox<>();
		comboBoxCourse.setModel(new DefaultComboBoxModel<>(new String[] {"BSA", "BSAIS", "BSCrim", "BSHM", "BSIT", "BEEd", "BSEd"}));
		comboBoxCourse.setBounds(262, 206, 114, 19);
		contentPane.add(comboBoxCourse);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setLabelFor(comboBoxCourse);
		lblCourse.setBounds(141, 208, 70, 15);
		contentPane.add(lblCourse);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(141, 245, 117, 25);
		contentPane.add(btnAdd);

		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setBounds(272, 245, 117, 25);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(401, 245, 117, 25);
		contentPane.add(btnDelete);
		
		studentsTable = new JTable();
		studentsTable.setBounds(141, 312, 377, 208);
		contentPane.add(studentsTable);	
		
		
		loadStudentsIntoTable(studentsTable);
		
		JLabel lblPalaraKimLeoII = new JLabel("Palarca, Kim Leo II C. - BSIT 3A");
		lblPalaraKimLeoII.setBounds(196, 532, 284, 15);
		contentPane.add(lblPalaraKimLeoII);
		
		// Add User
		btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lastName = textFieldLastname.getText().trim();
                String firstName = textFieldFirstname.getText().trim();
                String middle_Initial = textFieldMiddleInitial.getText().trim();
                String studentNumber = textFieldStudentNumber.getText().trim();
                String course = comboBoxCourse.getSelectedItem().toString().trim();

                if (lastName.isEmpty() || firstName.isEmpty() || middle_Initial.isEmpty() || studentNumber.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                    return;
                }

                try (Connection conn = dbConnection.getConnection()) {
                    String sql = "INSERT INTO users (lastname, firstname, middle_initial, student_no, course) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, lastName);
                    ps.setString(2, firstName);
                    ps.setString(3, middle_Initial);
                    ps.setString(4, studentNumber);
                    ps.setString(5, course);

                    int inserted = ps.executeUpdate();
                    if (inserted > 0) {
                        JOptionPane.showMessageDialog(null, "Student record inserted successfully!");
                        textFieldLastname.setText("");
                        textFieldFirstname.setText("");
                        textFieldMiddleInitial.setText("");
                        textFieldStudentNumber.setText("");
                        comboBoxCourse.setSelectedIndex(-1);
                        
                        loadStudentsIntoTable(studentsTable);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to insert student record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });
		
		// Update User
		btnUpdate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String lastName = textFieldLastname.getText().trim();
		        String firstName = textFieldFirstname.getText().trim();
		        String middle_Initial = textFieldMiddleInitial.getText().trim();
		        String studentNumber = textFieldStudentNumber.getText().trim();
		        String course = comboBoxCourse.getSelectedItem().toString().trim();

		        if (selectedStudentID == null) {
		            JOptionPane.showMessageDialog(null, "No student selected for update!");
		            return;
		        }

		        if (lastName.isEmpty() || firstName.isEmpty() || middle_Initial.isEmpty() || studentNumber.isEmpty() || course.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please fill all fields");
		            return;
		        }

		        try (Connection conn = dbConnection.getConnection()) {
		            String sql = "UPDATE users SET lastname = ?, firstname = ?, middle_initial = ?, student_no = ?, course = ? WHERE student_no = ?";
		            PreparedStatement ps = conn.prepareStatement(sql);
		            ps.setString(1, lastName);
		            ps.setString(2, firstName);
		            ps.setString(3, middle_Initial);
		            ps.setString(4, studentNumber);
		            ps.setString(5, course); 
		            ps.setString(6, selectedStudentID); // Use the correct identifier

		            int updated = ps.executeUpdate();
		            if (updated > 0) {
		                JOptionPane.showMessageDialog(null, "Student record updated successfully!");
		                textFieldLastname.setText("");
		                textFieldFirstname.setText("");
		                textFieldMiddleInitial.setText("");
		                textFieldStudentNumber.setText("");
		                comboBoxCourse.setSelectedIndex(-1);
		                selectedStudentID = null; 

		                loadStudentsIntoTable(studentsTable); 
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to update student record.");
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		        }
		    }
		});
		
		// Delete User
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (selectedStudentID == null) {
		            JOptionPane.showMessageDialog(null, "No student selected for deletion!");
		            return;
		        }

		        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
		        if (confirm == JOptionPane.YES_OPTION) {
		            try (Connection conn = dbConnection.getConnection()) {
		                String sql = "DELETE FROM users WHERE student_no = ?";
		                PreparedStatement ps = conn.prepareStatement(sql);
		                ps.setString(1, selectedStudentID);

		                int deleted = ps.executeUpdate();
		                if (deleted > 0) {
		                    JOptionPane.showMessageDialog(null, "Student record deleted successfully!");
		                    selectedStudentID = null;
		                    textFieldLastname.setText("");
		                    textFieldFirstname.setText("");
		                    textFieldMiddleInitial.setText("");
		                    textFieldStudentNumber.setText("");
		                    comboBoxCourse.setSelectedIndex(-1);

		                    loadStudentsIntoTable(studentsTable);
		                } else {
		                    JOptionPane.showMessageDialog(null, "Failed to delete student record.");
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		            }
		        }
		    }
		});

		
		// Search User
		btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentID = textFieldSearch.getText().trim(); 
                selectedStudentID = studentID;

                if (studentID.isEmpty()) {
                	 loadStudentsIntoTable(studentsTable);
                }else {
                searchStudent(studentID); 
                }
            }
        });
		
		// Selecting User in the JTable
		studentsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = studentsTable.getSelectedRow();
                if (row >= 0) { 
                	String studentNo = studentsTable.getValueAt(row, 0).toString();
                    String lastName = studentsTable.getValueAt(row, 1).toString();
                    String firstName = studentsTable.getValueAt(row, 2).toString();
                    String middle_Initial = studentsTable.getValueAt(row, 3).toString();
                    String course = studentsTable.getValueAt(row, 4).toString();

                    textFieldStudentNumber.setText(studentNo);
                    textFieldLastname.setText(lastName);
                    textFieldFirstname.setText(firstName);
                    textFieldMiddleInitial.setText(middle_Initial);
                    comboBoxCourse.setSelectedItem(course);
                    
                    selectedStudentID = studentNo;
                }
            }
        });
	}
	
	
	// Search User Method
	public void searchStudent(String searchUser) {
		DefaultTableModel model = (DefaultTableModel) studentsTable.getModel();
		model.setRowCount(0);

		String[] columns = {"Student No", "Lastname", "Firstname", "Middle Initial","Course"};
		model.setColumnIdentifiers(columns);

    try (Connection conn = dbConnection.getConnection()) {
    	String sql = """
    		    SELECT student_no, lastname, firstname, middle_initial, course
    		    FROM users
    		    WHERE LOWER(student_no) LIKE LOWER(?) 
    		       OR LOWER(lastname) LIKE LOWER(?) 
    		       OR LOWER(firstname) LIKE LOWER(?) 
    		       OR LOWER(middle_initial) LIKE LOWER(?) 
    		       OR LOWER(course) LIKE LOWER(?)
    		""";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i <= 5; i++) {
        ps.setString(i, "%" + searchUser + "%");
        }
        
        ResultSet rs = ps.executeQuery();
        boolean found = false;
        
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("student_no"), 
                rs.getString("lastname"),
                rs.getString("firstname"),
                rs.getString("middle_initial"),
                rs.getString("course")
            });
            found = true;
        } 
        if(!found) {
        	JOptionPane.showMessageDialog(null, "No matching student found.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
    	}
	}
	
	// Load Student Info Table Method
	public void loadStudentsIntoTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 
        
        String[] columns = {"Student No", "Lastname", "Firstname", "Middle Initial", "Course"};
        model.setColumnIdentifiers(columns);
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("student_no"	), 
                    rs.getString("lastname"),
                    rs.getString("firstname"),
                    rs.getString("middle_initial"),
                    rs.getString("course")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }
}
