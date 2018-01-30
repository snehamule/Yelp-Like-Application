

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import homework3.ReviewJframe;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sneha Mule
 */
public class HW3 extends javax.swing.JFrame {

    /**
     * Creates new form HW3
     */
    public HW3() throws SQLException {
        initComponents();
        displayMainCategoryData();
    	firstTableChanged();
        
    }
    List businessCategory= new ArrayList();
    

 RetriveData r = new RetriveData();
    List mainCategoryList = new ArrayList();
    List subCategoryList= new ArrayList();
    List attributeList= new ArrayList();
    
    public void displayMainCategoryData() throws SQLException{
        DefaultTableModel m= (DefaultTableModel)firstTable.getModel();
        businessCategory= r.getBusinessCategory();
        Object[] row = new Object[2];
        for(int i=0; i<businessCategory.size(); i++){
            m.addRow(new Object[]{false,businessCategory.get(i)});
        }
    }
    public void firstTableChanged(){
        firstTable.getModel().addTableModelListener(new TableModelListener() {
         public void tableChanged(TableModelEvent e) {
        	 try {
        		 System.out.println("Fisrst Table  Changedcall");
				firstTableMethodCall();
				secondTableChanged();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
         }
   });
   }
    public void secondTableChanged(){
    	secondTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
	        		 System.out.println("Second Table  Changedcall");

					secondTableMethodCall();
					thirdTableChanged();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
        	
        
   } 
    
    public void thirdTableChanged(){
    	thirdTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
	        		 System.out.println("Third Table  Changedcall");

					thirdTableMethodCall();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
        	
        
   } 
    public void firstTableMethodCall() throws SQLException{
    	mainCategoryList.clear();
    	Set mainCategorySet = new HashSet();
    	if(firstTable.getRowCount()>0){
    		boolean flag= false;	
    	for(int i=0;i<firstTable.getRowCount();i++){
    		flag =(boolean) firstTable.getValueAt(i, 0);
    		if(flag==true){
    			String mainString= (String) (firstTable.getValueAt(i, 1));
    			mainCategorySet.add(mainString);
    		}
    	}	
    	mainCategoryList= new ArrayList(mainCategorySet);
    	System.out.println("First Table"+mainCategoryList.size());

        displayDayOfTheWeek();
        displayFromHour();
        displayToHour();
        displayLocation();
        displaySubCategory();


    }
   }
    
    public void secondTableMethodCall() throws SQLException{
    	Set subCategorySet = new HashSet();
    	if(secondTable.getRowCount()>0){
    		boolean flag= false;	
    	for(int i=0;i<secondTable.getRowCount();i++){
    		flag =(boolean) secondTable.getValueAt(i, 0);
    		if(flag==true){
    			String mainString= (String) (secondTable.getValueAt(i, 1));
    			subCategorySet.add(mainString);
    		}
    	}	
    	 subCategoryList= new ArrayList(subCategorySet);
     	System.out.println("Second Table"+subCategoryList.size());

    	 displayDayOfTheWeek();
         displayFromHour();
         displayToHour();
         displayLocation();
         displayAttribute();
    }
   }
    
    public void thirdTableMethodCall() throws SQLException{
    	Set attributeSet = new HashSet();
    	if(thirdTable.getRowCount()>0){
    		boolean flag= false;	
    	for(int i=0;i<thirdTable.getRowCount();i++){
    		flag =(boolean) thirdTable.getValueAt(i, 0);
    		if(flag==true){
    			String mainString= (String) (thirdTable.getValueAt(i, 1));
    			attributeSet.add(mainString);
    		}
    	}	
    	attributeList= new ArrayList(attributeSet);
    	displayDayOfTheWeek();
        displayFromHour();
        displayToHour();
        displayLocation();
    }
   }
    
    public void displaySubCategory() throws SQLException{
        String searchforString= (String) searchForComboBox.getSelectedItem();
        List sub_CategoryList= new ArrayList();
    	sub_CategoryList= r.getSubCategories(mainCategoryList, searchforString);
    	DefaultTableModel m= (DefaultTableModel)secondTable.getModel();
    	Object []row= new Object[2];
    	m.setRowCount(0);
    		for(int i=0; i<sub_CategoryList.size(); i++){
    			if(sub_CategoryList.get(i)!=null && !sub_CategoryList.get(i).equals("null")  ){
    				m.addRow(new Object[]{false,sub_CategoryList.get(i)});
    		}
         }
    }
    
    public void displayAttribute() throws SQLException{
        String searchforString= (String) searchForComboBox.getSelectedItem();
        List attribute_List = new ArrayList(); 
        attribute_List= r.getAttributes(mainCategoryList,subCategoryList, searchforString);
    	DefaultTableModel m= (DefaultTableModel)thirdTable.getModel();
    	Object []row= new Object[2];
    	m.setRowCount(0);
    		for(int i=0; i<attribute_List.size(); i++){
             m.addRow(new Object[]{false,attribute_List.get(i)});
         }
    }
    
    public void displayDayOfTheWeek() throws SQLException{
    	if(dayOfTheWeekComboBox!=null){
    		dayOfTheWeekComboBox.removeAllItems();
    	}	
    	dayOfTheWeekComboBox.addItem("Any Day");
        String searchforString= (String) searchForComboBox.getSelectedItem();
    	List dayOfTheWeekList = new ArrayList();
    	dayOfTheWeekList= r.getDayOfTheWeek(mainCategoryList,subCategoryList,attributeList, searchforString);
    	for(int i=0;i<dayOfTheWeekList.size();i++){
			dayOfTheWeekComboBox.addItem((String) dayOfTheWeekList.get(i)+"".trim());
    	}
    }
    
    public void displayFromHour() throws SQLException{
    	if(fromComboBox!=null){
    		fromComboBox.removeAllItems();
    	}	
    	fromComboBox.addItem("Any Time");

        String searchforString= (String) searchForComboBox.getSelectedItem();
    	List fromHoursList = new ArrayList();
    	fromHoursList= r.getFromHours(mainCategoryList,subCategoryList,attributeList, searchforString);
    	for(int i=0;i<fromHoursList.size();i++){
			fromComboBox.addItem((double) fromHoursList.get(i)+"".trim());
    	}
    }
    
    public void displayToHour() throws SQLException{
    	if(toComboBox!=null){
    		toComboBox.removeAllItems();
    	}	
    	toComboBox.addItem("Any Time");
        String searchforString= (String) searchForComboBox.getSelectedItem();
    	List toHoursList = new ArrayList();
    	toHoursList= r.getToHours(mainCategoryList,subCategoryList,attributeList, searchforString);
    	for(int i=0;i<toHoursList.size();i++){
			toComboBox.addItem((double) toHoursList.get(i)+"".trim());
    	}
    }
    
    public void displayLocation() throws SQLException{
    	if(locationComboBox!=null){
    		locationComboBox.removeAllItems();
    	}
    	locationComboBox.addItem("Any Location");
        String searchforString= (String) searchForComboBox.getSelectedItem();
    	List locationList = new ArrayList();
    	locationList= r.getLocation(mainCategoryList,subCategoryList,attributeList, searchforString);
    	for(int i=0;i<locationList.size();i++){
			locationComboBox.addItem((String) locationList.get(i)+"".trim());
    	}
    }
    
    
    
    public void businessTableDisplay(List businessTableDataList){
   	 DefaultTableModel m= (DefaultTableModel)fourTable.getModel();
       Object[] row = new Object[6];
       if(businessTableDataList.size()>0){
       for(int i=0; i< businessTableDataList.size(); i++){
    	 row[0]=((BusinessDataDisplay) businessTableDataList.get(i)).getBusinessName();
       	 row[1]=((BusinessDataDisplay) businessTableDataList.get(i)).getBusinessID();
       	 row[2]=((BusinessDataDisplay) businessTableDataList.get(i)).getCity();
       	 row[3]=((BusinessDataDisplay) businessTableDataList.get(i)).getState();
       	 row[4]=((BusinessDataDisplay) businessTableDataList.get(i)).getStars();
       	 row[5]=((BusinessDataDisplay) businessTableDataList.get(i)).getReviewCount();
         m.addRow(row);
        }
       }
   }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        firstTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        secondTable = new javax.swing.JTable();
        thirdPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        fourTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dayOfTheWeekComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        fromComboBox = new javax.swing.JComboBox<>();
        toComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        locationComboBox = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        closeButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        searchForComboBox = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        thirdTable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 255));

        jPanel1.setBackground(new java.awt.Color(204, 102, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );

        firstTable.setBackground(new java.awt.Color(255, 153, 0));
        firstTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Main Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(firstTable);
        if (firstTable.getColumnModel().getColumnCount() > 0) {
            firstTable.getColumnModel().getColumn(0).setMinWidth(20);
            firstTable.getColumnModel().getColumn(0).setMaxWidth(20);
            firstTable.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );

        secondTable.setBackground(new java.awt.Color(153, 153, 255));
        secondTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Sub Categories"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(secondTable);
        if (secondTable.getColumnModel().getColumnCount() > 0) {
            secondTable.getColumnModel().getColumn(0).setMinWidth(20);
            secondTable.getColumnModel().getColumn(0).setMaxWidth(20);
        }

        fourTable.setBackground(new java.awt.Color(204, 255, 255));
        fourTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Business Name", "Buisness ID", "City", "States", "Stars", "Review Count"
            }
        ));
        fourTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fourTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(fourTable);
        if (fourTable.getColumnModel().getColumnCount() > 0) {
            fourTable.getColumnModel().getColumn(2).setMinWidth(80);
            fourTable.getColumnModel().getColumn(2).setMaxWidth(80);
            fourTable.getColumnModel().getColumn(3).setMinWidth(40);
            fourTable.getColumnModel().getColumn(3).setMaxWidth(40);
            fourTable.getColumnModel().getColumn(4).setMinWidth(40);
            fourTable.getColumnModel().getColumn(4).setMaxWidth(40);
            fourTable.getColumnModel().getColumn(5).setMinWidth(40);
            fourTable.getColumnModel().getColumn(5).setMaxWidth(40);
        }

        javax.swing.GroupLayout thirdPanelLayout = new javax.swing.GroupLayout(thirdPanel);
        thirdPanel.setLayout(thirdPanelLayout);
        thirdPanelLayout.setHorizontalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 154, Short.MAX_VALUE))
        );
        thirdPanelLayout.setVerticalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(0, 255, 204));

        jLabel1.setText("Day Of The Week");

        jLabel2.setText("From Hours");

        toComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("To Hours");

        jLabel4.setText("Location");

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        resetButton.setText("Reload");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					resetButtonActionPerformed(evt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Search For");

        searchForComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OR", "AND" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dayOfTheWeekComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fromComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2)))
                .addGap(74, 74, 74)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addGap(66, 66, 66)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(searchForComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(170, 170, 170)
                        .addComponent(jLabel5)))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dayOfTheWeekComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fromComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(resetButton)
                        .addComponent(searchButton)
                        .addComponent(closeButton)
                        .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(searchForComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        thirdTable.setBackground(new java.awt.Color(102, 255, 102));
        thirdTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Attributes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(thirdTable);
        if (thirdTable.getColumnModel().getColumnCount() > 0) {
            thirdTable.getColumnModel().getColumn(0).setMinWidth(20);
            thirdTable.getColumnModel().getColumn(0).setMaxWidth(20);
        }

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 0, 51));
        jTextField1.setText("   Yelp Application");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(thirdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 1345, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thirdPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void toComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
    	/*DefaultTableModel m= (DefaultTableModel)secondTable.getModel();
    	m.setRowCount(0);
    	DefaultTableModel m1= (DefaultTableModel)thirdTable.getModel();
    	m1.setRowCount(0);
    	
    	//displayMainCategoryData();
    	//setValueA not working
    	//firstTable.getSelectionModel().clearSelection();
    	
    	  DefaultTableModel x= (DefaultTableModel)firstTable.getModel();
          Object[] row = new Object[2];
          for(int i=0; i<businessCategory.size(); i++){
              x.addRow(new Object[]{false,businessCategory.get(i)});
          }*/
    	fromComboBox.removeAllItems();
    	locationComboBox.removeAllItems();
    	dayOfTheWeekComboBox.removeAllItems();
    	toComboBox.removeAllItems();
    	mainCategoryList.clear();
    	attributeList.clear();
    	subCategoryList.clear();
    	this.setVisible(false);
    	new HW3().setVisible(true);
    	
    	
    	
    	
    }
    
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String fromStringValue= (String) fromComboBox.getSelectedItem();
          String toStringValue= (String) toComboBox.getSelectedItem();
          String locationValue= (String) locationComboBox.getSelectedItem();
          String dayOfTheWeekValue= (String)dayOfTheWeekComboBox.getSelectedItem();
          double toValue=0.0;
          double fromValue=0.0;
          if(toStringValue!=null&& !toStringValue.equalsIgnoreCase("null")) {
          		if(toStringValue.equalsIgnoreCase("Any Time")){
          			toValue= Double.POSITIVE_INFINITY;
          		}else{
          			toValue=Double.parseDouble(toStringValue.replace(":", "."));
          		}
          }		
          
          if(fromStringValue!=null&& !fromStringValue.equalsIgnoreCase("null")){
          		if( fromStringValue.equalsIgnoreCase("Any Time")){
          			fromValue= Double.POSITIVE_INFINITY;
          		}else{
          			fromValue=Double.parseDouble(fromStringValue.replace(":", "."));
          		}
          }	
          if(toStringValue!=null && fromStringValue!=null &&
          		toStringValue!=null && fromStringValue!=null &&
          		!toStringValue.equalsIgnoreCase("null") && !fromStringValue.equalsIgnoreCase("null")&&
          		!toStringValue.equalsIgnoreCase("null")&& !fromStringValue.equalsIgnoreCase("null") ){
          	
          if(toStringValue.equalsIgnoreCase("Any Time") && !fromStringValue.equalsIgnoreCase("Any Time")&& fromValue>toValue ){
          				JOptionPane.showMessageDialog(null, "To Hours should be greater than From hours ");
          		}
          }	
          String operation= (String) searchForComboBox.getSelectedItem();
          List businessData = new ArrayList();
          try {
              businessData = r.getBusinessData(mainCategoryList, subCategoryList,attributeList, operation 
          			,toValue,fromValue, dayOfTheWeekValue,locationValue);
              
              System.out.println("businessData.size()"+businessData.size());
              if(businessData.size()<=0){
      		JOptionPane.showMessageDialog(null, "No Business Data Found.");
      		return;
      	}
              businessTableDisplay(businessData);
              
          } catch (Exception ex) {
              System.out.println("Exception"+ex);
          } 

    }                                            

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
                 this.dispose();

    }                                           

    private void fourTableMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
         // TODO add your handling code here:
     	int[] index = fourTable.getSelectedRows();
     	String selectedBusinessID=(String) fourTable.getValueAt(fourTable.getSelectedRow(), 1);
     	Object[] row= new  Object [5];
     	ReviewJframe reviewFrame= new ReviewJframe();
     	
     	DefaultTableModel m= (DefaultTableModel)reviewFrame.reviewDataTable.getModel();
     	List reviewData = new ArrayList();
         try {
             reviewData = r.getReviewData(selectedBusinessID);
         } catch (Exception ex) {
             System.out.println("Exception"+ex);
         } 
         for(int i=0;i<reviewData.size();i++){
         	row[0]=((ReviewDataDisplay) reviewData.get(i)).getReviewDate();
        	 	row[1]=((ReviewDataDisplay) reviewData.get(i)).getStars();
        	 	row[2]=((ReviewDataDisplay) reviewData.get(i)).getText();
        	 	row[3]=((ReviewDataDisplay) reviewData.get(i)).getUserID();
        	 	row[4]=((ReviewDataDisplay) reviewData.get(i)).getStars();
        	 	m.addRow(row);
        	
         }    	
         reviewFrame.setVisible(true);    	
    }                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HW3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HW3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HW3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HW3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HW3().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(HW3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox<String> dayOfTheWeekComboBox;
    private javax.swing.JTable firstTable;
    private javax.swing.JTable fourTable;
    private javax.swing.JComboBox<String> fromComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> locationComboBox;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton resetButton;

    private javax.swing.JComboBox<String> searchForComboBox;
    private javax.swing.JTable secondTable;
    private javax.swing.JPanel thirdPanel;
    private javax.swing.JTable thirdTable;
    private javax.swing.JComboBox<String> toComboBox;
    // End of variables declaration                   
}
