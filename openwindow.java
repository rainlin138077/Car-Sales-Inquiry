
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class openwindow {

	private JFrame frame;
	private JTextField text;
	private JTextArea textArea=new JTextArea();
	private JTable table;
	private DefaultTableModel model;
	private JTextField Name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					openwindow window = new openwindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public openwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(36, 27, 550, 90);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("汽車搜尋系統");
		lblNewLabel.setFont(new Font("標楷體", Font.PLAIN, 32));
		lblNewLabel.setBounds(160, 10, 205, 75);
		panel.add(lblNewLabel);
		
		JLabel lblEnterQuery = new JLabel("輸入需求");
		lblEnterQuery.setFont(new Font("標楷體", Font.PLAIN, 26));
        lblEnterQuery.setBounds(36, 127, 115, 34); // Set position and size
        frame.getContentPane().add(lblEnterQuery);
        
        text = new JTextField();
        text.setFont(new Font("新細明體", Font.PLAIN, 16));
        text.setBounds(238, 127, 76, 34);
        frame.getContentPane().add(text);
        text.setColumns(16);

	//這裡是下拉選單，選完後系統會自動顯示出所選列表的所有資料
        JComboBox comboBox = new JComboBox<>(new String[]{"Brand ", "Model ", "Supplier ", "Plant ", "Dealer ", "Customer ", "Vehicle ", "Supply ", "Manufacture ", "Inventory "});
        comboBox.addActionListener(e -> runQuery("SELECT * FROM "+comboBox.getSelectedItem().toString()));
        comboBox.setBounds(324, 127, 97, 35);
        frame.getContentPane().add(comboBox);
        
        JButton Search = new JButton("搜尋");
        Search.setFont(new Font("標楷體", Font.PLAIN, 18));
        Search.setBounds(489, 127, 97, 34);
        frame.getContentPane().add(Search);
        
        JButton Quest_botton = new JButton("？");
        Quest_botton.setFont(new Font("新細明體", Font.PLAIN, 10));
        Quest_botton.setBounds(431, 127, 49, 34);
        frame.getContentPane().add(Quest_botton);
        
        JButton Query1 = new JButton("Query1");
        Query1.setFont(new Font("標楷體", Font.PLAIN, 16));
        Query1.setBounds(489, 185, 97, 34);
        frame.getContentPane().add(Query1);
        
        JButton Query2 = new JButton("Query2");
        Query2.setFont(new Font("標楷體", Font.PLAIN, 16));
        Query2.setBounds(489, 235, 97, 34);
        frame.getContentPane().add(Query2);
        
        JButton Query3 = new JButton("Query3");
        Query3.setFont(new Font("標楷體", Font.PLAIN, 16));
        Query3.setBounds(489, 285, 97, 34);
        frame.getContentPane().add(Query3);
        
        JButton Query4 = new JButton("Query4");
        Query4.setFont(new Font("標楷體", Font.PLAIN, 16));
        Query4.setBounds(489, 335, 97, 34);
        frame.getContentPane().add(Query4);
        
        JButton Query5 = new JButton("Query5");
        Query5.setFont(new Font("標楷體", Font.PLAIN, 16));
        Query5.setBounds(489, 385, 97, 34);
        frame.getContentPane().add(Query5);
        
        //textArea.setEditable(false);
        //JScrollPane scrollPane = new JScrollPane(textArea);
        
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(36, 185, 437, 248);
        frame.getContentPane().add(scrollPane); 
        
        Name = new JTextField();
        Name.setFont(new Font("新細明體", Font.PLAIN, 16));
        Name.setColumns(16);
        Name.setBounds(152, 127, 76, 34);
        frame.getContentPane().add(Name);
       
        JFrame jFrame = new JFrame();
        JDialog jd = new JDialog(jFrame);
        jd.setBounds(500, 300, 400, 300);
        JLabel jLabel = new JLabel(//這裡是規則說明，在內文有問號按鈕點下去會介紹
        		"<html><body><p	align=\"center\">以下是查詢規則介紹：<br/>搜尋資料旁的列表選項可以提供特定列表，如果你在特定列表有特殊需求，即在輸入選項內輸入即可</p></body></html>"
        		,SwingConstants.CENTER);
        jLabel.setFont(new Font("標楷體", Font.PLAIN, 20));
        jd.getContentPane().add(jLabel);
        
        Quest_botton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳出新界面
				jd.setVisible(true);
			}
		});
        
        Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到search
				System.out.print(text.getText());
				if(text.getText() != null && Name.getText() != null) {//兩個都有的時候是搜尋特定table的特定列表的特定名稱
					String findword = "SELECT ALL."+text.getText()+" FROM "+comboBox.getSelectedItem().toString()+" ALL "+Name.getText()+" "; // 根據 ModelName 字段搜尋
	                runQuery(findword);
				}
				else if (text.getText() != null && Name.getText() == null) {//顯示特定table的特定列表
	                String findword = "SELECT "+text.getText()+" FROM "+comboBox.getSelectedItem().toString()+" "; // 根據 ModelName 字段搜尋
	                runQuery(findword);
				}
				else if(text.getText() == null && Name.getText() != null) {//顯示特定table的特定名稱
					String ALLget="";
					switch(comboBox.getSelectedItem().toString()) {
					case "Brand":
						ALLget=" ALL.BrandID , ALL.BrandName";
						break;
					case "Customer":
						ALLget=" ALL.CustomerID , ALL.CustomerName , ALL.Address , ALL.Phone , ALL.Gender , ALL.AnnualIncome , ALL.IsCompany ";
						break;
					case "Dealer":
						ALLget=" ALL.DealerID , ALL.DealerName , ALL.Address , ALL.Phone ";
						break;
					case "Inventory":
						ALLget=" ALL.InventoryID , ALL.DealerID , ALL.VIN , ALL.InventoryStatus , ALL.Date ";
						break;
					case "Manufacture":
						ALLget=" ALL.ManufactureID , ALL.PlantID , ALL.ModelID , ALL.PartName ";
						break;
					case "Model":
						ALLget=" ALL.ModelID , ALL.ModelName , ALL.BodyStyle , ALL.BrandID ";
						break;
					case "Plant":
						ALLget=" ALL.PlantID , ALL.PartName";
						break;
					case "Sales":
						ALLget=" ALL.SalesID , ALL.SalesDate , ALL.DealerID , ALL.CustomerID , ALL.VIN ";
						break;
					case "Supplier":
						ALLget=" ALL.SupplierID , ALL.SupplierName ";
						break;
					case "Supply":
						ALLget=" ALL.SupplyID , ALL.SupplierID , ALL.ModelID , PartName ";
						break;
					case "vehicle":
						ALLget=" ALL.VIN , ALL.Color , ALL.Engine , ALL.Transmission , ALL.ModelID , ALL.BrandID , ALL.Price ";
						break;
					}
					String findword = "SELECT "+ALLget+" FROM "+comboBox.getSelectedItem().toString()+" ALL "+Name.getText()+" "; // 根據 ModelName 字段搜尋
	                runQuery(findword);
				}

			}
		});
        
        Query1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到query1
				query1();
			}
		});
        
        Query2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到query2
				query2();
			}
		});
        
        Query3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到query3
				query3();
			}
		});
        
        Query4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到query4
				query4();
			}
		});
        
        Query5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳到query5
				query5();
			}
		});
	}
	
	private void query1() {
	    try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
	        String query = "SELECT v.VIN, c.CustomerName "
	        		+ "FROM Vehicle v "
	        		+ "JOIN Model m ON v.ModelID = m.ModelID  "
	        		+ "JOIN Brand b ON v.BrandID = b.BrandID "
	        		+ "JOIN Supply s ON m.ModelID = s.ModelID "
	        		+ "JOIN Supplier sup ON s.SupplierID = sup.SupplierID "
	        		+ "JOIN Inventory i ON v.VIN = i.VIN "
	        		+ "JOIN Sales sa ON i. DealerID = sa. DealerID "
	        		+ "JOIN customer c ON sa.CustomerID = c.CustomerID "
	        		+ "WHERE sup.SupplierName = 'Getrag' "
	        		+ "  AND sa.SalesDate BETWEEN '2023-05-01' AND '2023-07-10'";
	        ResultSet rs = stmt.executeQuery(query);
	        boolean hasResults = true;
	        while (rs.next()) {
	            hasResults = false;
	            System.out.println("VIN: " + rs.getString("VIN") + ", 客戶: " + rs.getString("CustomerName"));
	            }
	        
	        if (hasResults) {
	            textArea.setText("No results found.");
	        }
	        
	        runQuery(query);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void query2() {
	    try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
	        String query = "SELECT d.DealerName, SUM(v.Price) AS TotalSalesAmount "
	        		+ "FROM Dealer d "
	        		+ "JOIN Inventory i ON d.DealerID = i.DealerID "
	        		+ "JOIN Vehicle v ON i.VIN = v.VIN "
	        		+ "JOIN Sales s ON i.DealerID = s.DealerID "
	        		+ "WHERE s.SalesDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE() "
	        		+ "GROUP BY d.DealerID\r\n"
	        		+ "ORDER BY TotalSalesAmount DESC "
	        		+ "LIMIT 1";
	        ResultSet rs = stmt.executeQuery(query);
	        boolean hasResults = true;
	        while (rs.next()) {
	            hasResults = false;
	            System.out.println("Name: " + rs.getString("DealerName"));
	            }
	        
	        if (hasResults) {
	            textArea.setText("No results found.");
	        }
	        
	        runQuery(query);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

	private void query3() {
	    try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
	        String query = 
	        		"SELECT b.BrandName, COUNT(*) AS SalesCount  "
	        		+ "FROM Brand b  "
	        		+ "JOIN Vehicle v ON b.BrandID = v.BrandID  "
	        		+ "JOIN Inventory i ON v.VIN = i.VIN  "
	        		+ "JOIN Sales s   "
	        		+ "WHERE s.SalesDate BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE()  "
	        		+ "GROUP BY b.BrandID  "
	        		+ "ORDER BY SalesCount DESC "
	        		+ "LIMIT 2";
	        ResultSet rs = stmt.executeQuery(query);
	        boolean hasResults = true;
	        while (rs.next()) {
	            hasResults = false;
	            System.out.println("BrandName: " + rs.getString("BrandName")+"SalesCount： "+rs.getInt("SalesCount"));
	            }
	        
	        if (hasResults) {
	            textArea.setText("No results found.");
	        }
	        
	        runQuery(query);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void query4() {
	    try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
	        String query = 
	        		"SELECT MONTH(s.SalesDate) AS Month, COUNT(*) AS SalesCount  "
	        		+ "FROM Sales s  "
	        		+ "JOIN Inventory i   "
	        		+ "JOIN Vehicle v ON i.VIN = v.VIN  "
	        		+ "JOIN Model m ON v.ModelID = m.ModelID "
	        		+ "WHERE m.BodyStyle = 'SUV' "
	        		+ "GROUP BY MONTH(s.SalesDate) "
	        		+ "ORDER BY SalesCount DESC "
	        		+ "LIMIT 1";
	        ResultSet rs = stmt.executeQuery(query);
	        boolean hasResults = true;
	        while (rs.next()) {
	            hasResults = false;
	            System.out.println("SUV最好銷售月: " + rs.getInt("Month")+" 月， 銷售量為： "+rs.getInt("SalesCount"));
	            }
	        
	        if (hasResults) {
	            textArea.setText("No results found.");
	        }
	        
	        runQuery(query);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void query5() {
	    try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
	        String query = 
	        		"SELECT    d.DealerName,    AVG(DATEDIFF(CURDATE(), i.Date)) AS AvgDaysInInventory  "
	        		+ "FROM    Dealer d  "
	        		+ "JOIN    Inventory i ON d.DealerID = i.DealerID  "
	        		+ "JOIN    Vehicle v ON i.VIN = v.VIN  "
	        		+ "WHERE    i.InventoryStatus = 'Available'  "
	        		+ "GROUP BY    d.DealerID  "
	        		+ "ORDER BY    AvgDaysInInventory DESC  "
	        		+ "LIMIT 1";
	        ResultSet rs = stmt.executeQuery(query);
	        boolean hasResults = true;
	        while (rs.next()) {
	            hasResults = false;
	            System.out.println("DealerName: " + rs.getString("DealerName")+" ,平均時長為： "+rs.getInt("AvgDaysInInventory"));
	            }
	        
	        if (hasResults) {
	            textArea.setText("No results found.");
	        }
	        
	        runQuery(query);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private void runQuery(String query) {
		clearTable();
		text.removeAll();
        try (Connection connection = ConnectionDB.Connection(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
//            StringBuilder result = new StringBuilder();
//            for (int i = 1; i <= columnCount; i++) {
//                result.append(rsmd.getColumnName(i)).append("\t");
//            }
//            result.append("\n");
//            
//            
//            while (rs.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//                    result.append(rs.getString(i)).append("\t");
//                }
//                result.append("\n");
//            }
//            textArea.setText(result.toString());
            
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }

            // 添加數據
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "沒有此列表名稱，請重新輸入");
            textArea.setText("Error executing query: " + e.getMessage());
        }
    }

	private void clearTable() {
        model.setRowCount(0);
        model.setColumnCount(0);
    }
}
