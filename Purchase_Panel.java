package GUI;
import BackEnd.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class Purchase_Panel extends GuiComponents implements Table {
    private JTable table;
    private Vector columnNamesVector=new Vector();
    private Vector row_dataVector=new Vector();
    private FillTable fill;
    private ArrayList columnNames;
    private DefaultTableModel tableModel;
    private static int selectedID;

    private GridBagConstraints constraints2;
    private GridBagLayout layout2;


    private JPanel Search_Panel,body,OPanel;
    private JLabel WPanel;
    private JCheckBox chkUpdate, chkNewPurchase;
    private JLabel lblSupName,lblIno,lblDate,lblProdName,lblQuantity,lblWeight,lblPrice,lblTotal;
    private JTextField txtSupName,txtIno,txtDate,txtProdName,txtQuantity,txtWeight,txtPrice,txtTotal;
    private JButton btnUpdate,btnDelete, btnCreate;

    private String defaultDate=DatePicker.getDefaultDate();
    private String previousMonth=DatePicker.getPreviousMonth();
    private JButton btnRefresh,btnDate;
    private String dfrom=previousMonth;
    private String dto=defaultDate;

    private JComboBox CBsupplier,CBSup, CBproduct,CBprod, CBsearch, orderBY;
    private JLabel lblFrom,lblTo,lblorderBY,lblInvoice;
    private JTextField txtInvoice;
    private JButton from,to,go,reset;


    private String search_sql="SELECT * FROM tblPurchases WHERE (P_Date>='"+dfrom+"' AND P_Date<='"+dto+"')";
    private String ori_sql="SELECT * FROM tblPurchases WHERE (P_Date>='"+dfrom+"' AND P_Date<='"+dto+"')";

    private ID purchasesID = new ID();
    private Purchases purchases;

    private boolean valid;
    double weight;
    String total;

    public Purchase_Panel(){
        body=new JPanel(new BorderLayout());
        add(body);

        OPanel = new JPanel();
        OPanel.setLayout(new BorderLayout());

        Search_Panel =new JPanel();
        Search_Panel.setBackground(new Color(56, 111, 164));
        layout2=new GridBagLayout();
        constraints2=new GridBagConstraints();
        Search_Panel.setLayout(layout2);
        constraints2.insets=new Insets(5,5,5,5);

        txtInvoice=new JTextField("Invoice No");
        txtInvoice.setEnabled(false);

        CBproduct=new JComboBox(pname);
        CBproduct.setSelectedIndex(-1);
        CBproduct.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    if (!Status.isChangeInProduct()){
                        search_sql=" AND Pid="+products.get(index).getProdID();
                        search(ori_sql,search_sql);
                    }
                }
            }
        });

        CBsupplier=new JComboBox(sname);

        CBsupplier.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    if (!Status.isChangeInProduct()){
                        search_sql=" AND S_id="+suppliers.get(index).getSupid();
                        search(ori_sql,search_sql);
                    }
                }
            }
        });


        lblFrom=new JLabel("From");
        setFont(lblFrom);
        setComponent(lblFrom,3,0,new Dimension(60,30));


        from=new JButton();
        from.setText(previousMonth);
        from.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable getDate=new Runnable() {
                    @Override
                    public void run() {
                        DatePicker datePicker=new DatePicker();
                        from.setText(datePicker.getDate());
                        dfrom=datePicker.getDate();
                        setSearch(dfrom,dto);
                    }
                };
                getDate.run();
            }
        });
        setComponent(from,4,0);


        lblTo=new JLabel("TO");
        setFont(lblTo);
        setComponent(lblTo,5,0,new Dimension(40,30));
        to=new JButton();
        to.setText(defaultDate);
        to.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatePicker datePicker=new DatePicker();
                to.setText(datePicker.getDate());
                dto=datePicker.getDate();
                setSearch(dfrom,dto);
            }
        });

        setComponent(to,6,0);


        JLabel lblSearch=new JLabel("Search By:");
        setFont(lblSearch);
        setComponent(lblSearch,0,0);

        String[] type={"Supplier","Product","Invoice","Date"};
        String[] type2={"Supplier","Product","Invoice"};

        String[] dbName={"Sid","Pid","Invoice_No","P_Date","Paid"};
        JComponent[] components={CBsupplier,CBproduct,txtInvoice};

        CBsearch=new JComboBox(type2);
        CBsearch.setSelectedIndex(-1);
        CBsearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    Search_Panel.remove(CBsearch);
                    if (Objects.equals(components[index], txtInvoice)){
                        txtInvoice.setEnabled(true);
                    }
                    setComponent(components[index],1,0);
                    Search_Panel.revalidate();
                    Search_Panel.repaint();
                }
            }
        });
        setComponent(CBsearch,1,0);

        lblorderBY=new JLabel("ORDER BY: ");
        setFont(lblorderBY);
        setComponent(lblorderBY,7,0);

        orderBY=new JComboBox(type);
        orderBY.setSelectedIndex(-1);
        orderBY.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    String str=" ORDER BY "+dbName[index];
                    search_sql=Validation.isSqlValid(search_sql);
                    search(search_sql,str);
                }
            }
        });
        setComponent(orderBY,8,0);

        body.add(Search_Panel,BorderLayout.NORTH);

        createTable();

        go=new JButton("GO");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtInvoice.isEnabled()){
                    search_sql=" WHERE Invoice_No="+txtInvoice.getText();
                    search(ori_sql,search_sql);
                }
                refresh();
            }
        });
        setComponent(go,0,3);

        reset=new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CBsearch.getSelectedIndex()!=-1) {
                    Search_Panel.remove(components[CBsearch.getSelectedIndex()]);
                    setComponent(CBsearch,1,0);
                    CBproduct.setSelectedIndex(-1);
                    CBsupplier.setSelectedIndex(-1);
                    CBsearch.setSelectedIndex(-1);
                    txtInvoice.setEnabled(false);
                }
                orderBY.setSelectedIndex(-1);
                to.setText(defaultDate);
                from.setText(previousMonth);
                setSearch(previousMonth,defaultDate);
                Search_Panel.revalidate();
                Search_Panel.repaint();
                refresh();
            }
        });
        setComponent(reset,1,3);

        btnRefresh=new JButton("REFRESH");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    refresh();
            }
        });
        setComponent(btnRefresh,4,3);


        WPanel=new JLabel();

        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        WPanel.setPreferredSize(new Dimension((int)(screensize.width*0.25),(screensize.height)));

        Icon icon=new ImageIcon(getClass().getResource("b2"));
        WPanel.setIcon(icon);
        layout=new GridBagLayout();
        constraints=new GridBagConstraints();
        WPanel.setBackground(Color.cyan);
        WPanel.setLayout(layout);

        CBSup=new JComboBox(sname);
        CBSup.setSelectedIndex(-1);

        CBprod=new JComboBox(pname);
        CBprod.setSelectedIndex(-1);
        CBprod.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    double iPrice=products.get(index).getBp();
                    txtPrice.setText(String.valueOf(iPrice));
                }
            }
        });

        lblSupName= new JLabel("Supplier: ");
        setFont(lblSupName);
        setComponentWPanel(lblSupName,0,2);

        txtSupName = new JTextField();
        setFont(txtSupName);
        setComponentWPanel(txtSupName,1,2);
        txtSupName.setEditable(false);

        lblIno = new JLabel("Invoice No: ");
        setFont(lblIno);
        setComponentWPanel(lblIno,0,3);

        txtIno = new JTextField();
        setFont(txtIno);
        setComponentWPanel(txtIno,1,3);


        lblDate = new JLabel("Date: ");
        setFont(lblDate);
        setComponentWPanel(lblDate,0,4);

        txtDate=new JTextField();
        txtDate.setEditable(false);
        txtDate.setText(DatePicker.getDefaultDate());
        setFont(txtDate);
        txtDate.setEditable(false);
        setComponentWPanel(txtDate,1,4);

        Icon iconDate=new ImageIcon(getClass().getResource("calender"));
        btnDate=new JButton(iconDate);
        btnDate.setOpaque(false);
        btnDate.setContentAreaFilled(false);
        btnDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatePicker datePicker=new DatePicker();
                txtDate.setText(datePicker.getDate());
            }
        });
        constraints.insets=new Insets(5,0,5,5);
        setComponentWPanel(btnDate,2,4, new Dimension(30,30));
        constraints.insets=new Insets(5,30,5,30);

        lblProdName  = new JLabel("Product: ");
        setFont(lblProdName);
        setComponentWPanel(lblProdName,0,5);

        txtProdName = new JTextField();
        setFont(txtProdName);
        setComponentWPanel(txtProdName,1,5);
        txtProdName.setEditable(false);

        lblQuantity = new JLabel("Quantity : ");
        setFont(lblQuantity);
        setComponentWPanel(lblQuantity,0,6);

        txtQuantity= new JTextField();
        setFont(txtQuantity);
        setComponentWPanel(txtQuantity,1,6);

        lblWeight  = new JLabel("Weight: ");
        setFont(lblWeight);
        setComponentWPanel(lblWeight,0,7);

        txtWeight= new JTextField();
        setFont(txtWeight);
        setComponentWPanel(txtWeight,1,7);
        txtWeight.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent keyEvent) {
                try{
                    weight = Double.parseDouble(txtWeight.getText());
                    total = String.valueOf(weight * Double.parseDouble(txtPrice.getText()));
                    txtTotal.setText(total);
                }
                catch(NumberFormatException e){}
            }
            public void keyTyped(KeyEvent e) {

            }
            public void keyPressed(KeyEvent e) {

            }
        });

        lblPrice = new JLabel("Price: ");
        setFont(lblPrice);
        setComponentWPanel(lblPrice,0,8);

        txtPrice = new JTextField();
        setFont(txtPrice);
        txtPrice.setEditable(false);
        setComponentWPanel(txtPrice,1,8);

        lblTotal = new JLabel("Total: ");
        setFont(lblTotal);
        setComponentWPanel(lblTotal,0,9);

        txtTotal = new JTextField();
        setFont(txtTotal);
        txtTotal.setEditable(false);
        setComponentWPanel(txtTotal,1,9);

        btnUpdate = new JButton("Update");
        setFont(btnUpdate);
        btnUpdate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    String Testinvoice = txtIno.getText();
                    String Testdate = txtDate.getText();
                    String Testqty = txtQuantity.getText();
                    String Testweight = txtWeight.getText();
                    //int invoice, String date, int qty, double weight
                    valid = Validation.isPurchaseFormValid(Testinvoice,Testdate,Testqty,Testweight);

                    if(valid){
                        int invoice = Integer.parseInt(txtIno.getText());
                        String date = txtDate.getText();
                        int qty = Integer.parseInt(txtQuantity.getText());
                        double weight = Double.parseDouble(txtWeight.getText());
                        double total = Double.parseDouble(txtTotal.getText());

                        System.out.println("valid");
                        String sql="UPDATE tblPurchases SET Invoice_No = "+invoice+" , P_Date = '"+date+"' ,quantity = "+qty+" ,Weight_Kg = "+weight+" ,Total = "+total+" WHERE PurchasesID = "+selectedID;
                        dbManipulation.Insert(sql);
                        refresh();
                    }
                }catch (NumberFormatException exp){}
            }
        });
        setComponentWPanel(btnUpdate,0,10);

        btnCreate = new JButton("Create");
        setFont(btnCreate);
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String Testinvoice = txtIno.getText();
                    String Testdate = txtDate.getText();
                    String Testqty = txtQuantity.getText();
                    String Testweight = txtTotal.getText();

                    valid = Validation.isPurchaseFormValid(Testinvoice,Testdate,Testqty,Testweight);

                    if(valid){
                        int supid = suppliers.get(CBsupplier.getSelectedIndex()).getSupid();
                        int invoice = Integer.parseInt(txtIno.getText());
                        String date = txtDate.getText();
                        int prodid = products.get(CBproduct.getSelectedIndex()).getProdID();
                        int qty = Integer.parseInt(txtQuantity.getText());
                        double weight = Double.parseDouble(txtWeight.getText());
                        double total = Double.parseDouble(txtTotal.getText());

                        String sql="INSERT INTO tblPurchases(Invoice_No,P_Date,Pid,quantity,Weight_Kg,Total,S_id) VALUES( "+invoice+", '"+date+"', "+prodid+" , "+qty+" , "+weight+" , "+total+" , "+supid+")";
                        dbManipulation.Insert(sql);


                        if (date.equals(DatePicker.getDefaultDate())){
                            int Oldqty=products.get(CBproduct.getSelectedIndex()).getStock();
                            int Newqty=Oldqty+qty;
                            String sStock="UPDATE tblProduct\n" +
                                    "SET Stock="+Newqty+"\n" +
                                    "WHERE ProductID= "+prodid;
                            Status.setChangeInProduct(true);
                            dbManipulation.Insert(sStock);
                            refresh();
                        }
                        setSearch(DatePicker.getPreviousMonth(),DatePicker.getDefaultDate());
                        refresh();
                    }
                }
                catch(Exception exp){}
            }
        });

        btnDelete = new JButton("Delete");
        setFont(btnDelete);
        btnDelete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String sql = "DELETE FROM tblPurchases WHERE PurchasesID = "+selectedID;
                dbManipulation.Delete(sql);
                refresh();
            }
        });
        setComponentWPanel(btnDelete,1,10);

        constraints.insets=new Insets(5,30,5,30);
        chkUpdate = new JCheckBox("Update");
        setFont(chkUpdate);
        setComponentWPanel(chkUpdate,0,0);
        chkUpdate.setSelected(true);
        chkUpdate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkUpdate.isSelected()){
                    EmptyAll();
                    chkNewPurchase.setSelected(false);

                    WPanel.remove(btnCreate);
                    WPanel.remove(CBSup);
                    WPanel.remove(CBprod);
                    setComponentWPanel(btnUpdate,0,10);
                    setComponentWPanel(btnDelete,1,10);
                    setComponentWPanel(txtSupName,1,2);
                    setComponentWPanel(txtProdName,1,5);


                    WPanel.revalidate();
                    WPanel.repaint();
                }
            }
        });

        chkNewPurchase = new JCheckBox("Create");
        setFont(chkNewPurchase);
        setComponentWPanel(chkNewPurchase,1,0);
        chkNewPurchase.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkNewPurchase.isSelected()){
                    EmptyAll();
                    chkUpdate.setSelected(false);
                    WPanel.remove(btnUpdate);
                    WPanel.remove(btnDelete);
                    WPanel.remove(txtSupName);
                    WPanel.remove(txtProdName);

                    setComponentWPanel(CBSup,1,2);
                    setComponentWPanel(CBprod,1,5);
                    setComponentWPanel(btnCreate,0,10);

                    WPanel.revalidate();
                    WPanel.repaint();
                }
            }
        });

        OPanel.add(WPanel,BorderLayout.EAST);
        body.add(Search_Panel,BorderLayout.PAGE_START);
        body.add(OPanel,BorderLayout.CENTER);

        Search_Panel.setBackground(new Color(61, 61, 61));
        setColor(Search_Panel);
        setColor(WPanel);
        add(body);

    }

    public void EmptyAll(){
        for(Component control : WPanel.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
            else if (control instanceof JComboBox)
            {
                JComboBox ctr = (JComboBox) control;
                ctr.setSelectedIndex(-1);
            }
        }
        txtDate.setText(DatePicker.getDefaultDate());
    }

    public void createTable(){
        columnNames=new ArrayList();
        columnNames.add("ID");
        columnNames.add("Invoice");
        columnNames.add("Date");
        columnNames.add("Product");
        columnNames.add("Quantity");
        columnNames.add("Weight(Kg)");
        columnNames.add("Total");
        columnNames.add("Supplier");

        table=new JTable(){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        reloadData();

        tableModel=new DefaultTableModel(row_dataVector,columnNamesVector);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
        tableModel = (DefaultTableModel) table.getModel();
        table.setModel(tableModel);

        changeTableValue();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    try {
                        setSelectedID((int)target.getValueAt(row,0));
                        setData();
                    }
                    catch (ArrayIndexOutOfBoundsException exp){}
                }
            }
        });

        setTableUI(table);
        JScrollPane scrollPane=new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        OPanel.add(scrollPane,BorderLayout.CENTER);
    }

    public void reloadData(){
        columnNamesVector.clear();
        row_dataVector.clear();
        fill=new FillTable(search_sql,columnNames);
        columnNamesVector=fill.getColumnNamesVector();
        row_dataVector=fill.getDataVector();
    }

    public void changeTableValue(){
        for (int i=0;i<row_dataVector.size();i++){
            Object oPid=table.getValueAt(i,3);
            int iPid=(int)oPid;

            String sql="SELECT Name FROM tblProduct WHERE ProductID="+iPid;
            table.setValueAt(fill.changeValue(sql),i,3);

            Object oSid=table.getValueAt(i,7);
            int iSid=(int)oSid;
            String sql2="SELECT Name FROM tblSupplier WHERE SupplierID="+iSid;
            table.setValueAt(fill.changeValue(sql2),i,7);
        }
    }

    public void refresh(){
        try{
            reloadData();
            tableModel.setDataVector(row_dataVector,columnNamesVector);
            tableModel.fireTableDataChanged();
            changeTableValue();
        }catch (Exception exp){}

        if (Status.isChangeInProduct()){
            resetCombo(this.CBproduct,pname);
            resetCombo(this.CBprod,pname);
        }
        if (Status.isChangeInSupplier()){
            resetCombo(this.CBsupplier,sname);
            resetCombo(this.CBSup,sname);

            this.CBsupplier.setSelectedIndex(-1);
            this.CBSup.setSelectedIndex(-1);
        }
    }

    public void setComponent(JComponent component,int x,int y){
        constraints2.gridx=x;
        constraints2.gridy=y;
        layout2.setConstraints(component,constraints2);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        Search_Panel.add(component);
    }

    public void setComponent(JComponent component,int x,int y,Dimension size){
        setComponent(component,x,y);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    public void search(String ori,String str){
        search_sql=ori+str;
    }

    public void setSearch(String d1,String d2){
        search_sql="SELECT * FROM tblPurchases WHERE (P_Date>='"+d1+"' AND P_Date<='"+d2+"')";
    }

    public static void setSelectedID(int id){
        selectedID=id;
    }

    public void setComponentWPanel(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        WPanel.add(component);
    }

    public void setComponentWPanel(JComponent component,int x,int y,Dimension size){
        setComponentWPanel(component,x,y);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    public void setData(){

        purchases = purchasesID.searchPurchaseRecord("Select * FROM tblPurchases WHERE PurchasesID ="+selectedID);

        txtSupName.setText(getSupplierName(purchases.getS_id()));
        txtIno.setText(String.valueOf(purchases.getInvoice_No()));
        txtDate.setText(String.valueOf(purchases.getP_Date()));
        txtProdName.setText(getProductName(purchases.getPid()));
        txtQuantity.setText(String.valueOf(purchases.getQuantity()));
        txtWeight.setText(String.valueOf(purchases.getWeight_Kg()));
        txtPrice.setText(getProductPrice(purchases.getPid()));
        txtTotal.setText(String.valueOf(purchases.getTotal()));

    }
}
