package GUI;

import BackEnd.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by erosennin on 3/24/17.
 */
public class Payment extends GuiComponents implements Table{
    private ArrayList<String> columnNames;
    private JTable table;
    private DefaultTableModel tableModel;
    private Vector row_dataVector=new Vector();
    private Vector columnNamesVector=new Vector();
    private JPanel body;
    private FillTable fill;
    private String search_sql="SELECT * FROM tblPaymentCust";
    private JLabel form;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblInv;
    private JTextField txtInv;
    private JLabel lblDate;
    private JTextField txtDate;
    private JLabel lblAmount;
    private JTextField txtAmount;
    private JButton btnModify;
    private JButton btnCancel;
    private JButton btnSave;
    private JButton btnSave2;
    private JButton btnAdd;
    private JButton btnDate;
    private Dimension size=new Dimension(150,30);
    private int selectedPayment;
    private JRadioButton rbCust;
    private JRadioButton rbSupp;
    private ButtonGroup gp;
    private ID id = new ID();
    private Paymentsup sup ;
    private Paymentcust cus;
    private JComboBox CBcustomer;
    private JComboBox CBSup;


    public Payment(){
        body=new JPanel(new BorderLayout());
        form=new JLabel();
        Icon icon=new ImageIcon(getClass().getResource("b2"));
        form.setIcon(icon);
        form.setLayout(layout);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        form.setPreferredSize(new Dimension((int)(screensize.width*0.35),(screensize.height)));

        constraints.insets=new Insets(10,10,10,10);
        constraints.anchor=GridBagConstraints.WEST;

        rbCust=new JRadioButton("Customer");
        rbSupp=new JRadioButton("Supplier");
        gp=new ButtonGroup();
        gp.add(rbCust);
        gp.add(rbSupp);

        rbCust.setSelected(true);

        setComponent(rbCust,0,0);
        setComponent(rbSupp,1,0);

        rbCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmptyAll();
                search_sql="SELECT * FROM tblPaymentCust";
                refresh();
            }
        });

        rbSupp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmptyAll();
                search_sql="SELECT * FROM tblPaymentSup";
                refresh();
            }
        });

        lblName=new JLabel("Name");
        setFont(lblName);
        setComponent(lblName,0,1);

        txtName=new JTextField();
        setComponent(txtName,1,1);
        txtName.setEditable(false);

        CBcustomer=new JComboBox(cname);
        CBcustomer.setSelectedIndex(-1);
        CBcustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox=(JComboBox)e.getSource();
                int index=comboBox.getSelectedIndex();
                try{
                    txtName.setText(customers.get(index).getName());
                }
                catch(ArrayIndexOutOfBoundsException exp){}
            }
        });

        CBSup=new JComboBox(sname);
        CBSup.setSelectedIndex(-1);
        CBSup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox=(JComboBox)e.getSource();
                int index=comboBox.getSelectedIndex();
                try{
                    txtName.setText(suppliers.get(index).getName());
                }
                catch(ArrayIndexOutOfBoundsException exp){}
            }
        });

        lblInv=new JLabel("Invoice Number");
        setFont(lblInv);
        setComponent(lblInv,0,2);

        txtInv=new JTextField();
        setComponent(txtInv,1,2);

        lblDate=new JLabel("Date Paid");
        setFont(lblDate);
        setComponent(lblDate,0,4);

        txtDate=new JTextField();
        setComponent(txtDate,1,4);
        txtDate.setEditable(false);
        txtDate.setText(DatePicker.getDefaultDate());

        Icon iconDate=new ImageIcon(getClass().getResource("calender"));
        btnDate=new JButton(iconDate);
        btnDate.setOpaque(false);
        btnDate.setContentAreaFilled(false);
        btnDate.setRolloverEnabled(true);
        btnDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatePicker datePicker=new DatePicker();
                txtDate.setText(datePicker.getDate());
            }
        });
        setComponent(btnDate,3,4);

        lblAmount=new JLabel("Amount Paid");
        setFont(lblAmount);
        setComponent(lblAmount,0,6);

        txtAmount=new JTextField();
        setComponent(txtAmount,1,6);
        btnModify=new JButton("MODIFY");
        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toggle(true);
                form.remove(btnModify);
                setComponent(btnSave,1,8);
                setComponent(btnCancel,2,8);
                setColor(form);
                if (rbCust.isSelected()){
                    setComponent(CBcustomer,2,1);
                }
                else if (rbSupp.isSelected()){
                    setComponent(CBSup,3,1);
                }
                form.revalidate();
                form.repaint();
            }
        });
        btnModify.setEnabled(false);
        setComponent(btnModify,2,9);

        btnCancel=new JButton("CANCEL");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnCancel();
            }
        });

        btnSave=new JButton("SAVE");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnSave();
            }
        });

        btnSave2=new JButton("SAVE");
        btnSave2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnSave2();
            }
        });

        btnAdd=new JButton("ADD");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmptyAll();
                form.remove(btnModify);
                btnAdd.setEnabled(false);
                setComponent(btnSave2,1,8);
                setComponent(btnCancel,2,8);
                if (rbCust.isSelected()){
                    setComponent(CBcustomer,2,1);
                }
                else if (rbSupp.isSelected()){
                    setComponent(CBSup,3,1);
                }

                Toggle(true);
                form.revalidate();
                form.repaint();
            }
        });
        setComponent(btnAdd,0,8);

        createTable();

        Toggle(false);

        form.setPreferredSize(new Dimension((int)(screensize.width*0.5),(screensize.height)));
        setColor(form);
        body.add(form,BorderLayout.WEST);

        add(body);
    }

    public void Toggle(boolean value){
        for(Component control : form.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setEditable(value);
            }
            txtDate.setEditable(false);
            txtName.setEditable(false);
        }
        setColor(form);
    }

    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        form.add(component);
    }

    public void EmptyAll(){
        for(Component control : form.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
            txtDate.setText(DatePicker.getDefaultDate());
            CBSup.setSelectedIndex(-1);
            CBcustomer.setSelectedIndex(-1);
        }
        setColor(form);
    }

    @Override
    public void createTable() {
        columnNames=new ArrayList();

        columnNames.add("PaymentID");
        columnNames.add("Name");
        columnNames.add("invoice_No");
        columnNames.add("Date_paid");
        columnNames.add("Amount_paid");

        table =new JTable(){
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
                    try{
                        int row = target.getSelectedRow();
                        setSelectedpayment((int)target.getValueAt(row,0));
                        btnModify.setEnabled(true);
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
        body.add(scrollPane,BorderLayout.CENTER);
    }

    @Override
    public void reloadData() {
        columnNamesVector.clear();
        row_dataVector.clear();
        fill=new FillTable(search_sql,columnNames);
        columnNamesVector=fill.getColumnNamesVector();
        row_dataVector=fill.getDataVector();
    }

    public void refresh(){
        try{
            reloadData();
            tableModel.setDataVector(row_dataVector,columnNamesVector);
            tableModel.fireTableDataChanged();
            changeTableValue();
        }catch (Exception exp){}

        if (Status.isChangeInCustomer()){
            resetCombo(this.CBcustomer,cname);
        }
        if (Status.isChangeInSupplier()){
            resetCombo(this.CBSup,sname);
        }
        fbtnCancel();
    }

    public void setSelectedpayment(int value){
        selectedPayment=value;
    }

    public void setData(){
        try{
            if(rbCust.isSelected()) {
                cus = id.searchPaymentcustRecord("SELECT custID,invoice_No,Date_paid,Amount_Paid FROM tblPaymentCust WHERE PaymentID= "+selectedPayment);
                txtName.setText(getCustomerName(cus.getCustID()));
                txtInv.setText(String.valueOf(cus.getInvoice_No()));
                txtDate.setText(String.valueOf(cus.getDate_Paid()));
                txtAmount.setText(String.valueOf(cus.getAmount_Paid()));

            }
            else if(rbSupp.isSelected()) {

                sup = id.searchPaymentsupRecord("SELECT supID,invoice_No,Date_paid,Amount_Paid FROM tblPaymentSup WHERE PaymentID= "+selectedPayment);
                txtName.setText(getSupplierName(sup.getSupID()));
                txtInv.setText(String.valueOf(sup.getInvoice_No()));
                txtDate.setText(String.valueOf(sup.getDate_Paid()));
                txtAmount.setText(String.valueOf(sup.getAmount_Paid()));

            }
        }
        catch(Exception e){}
    }

    @Override
    public void changeTableValue() {
        for (int i=0;i<row_dataVector.size();i++){
            Object oId=table.getValueAt(i,1);
            int iId=(int)oId;
            String sql="";
            if (rbCust.isSelected()){
                sql="SELECT Name FROM tblCustomer WHERE CustID="+iId;
            }
            else if (rbSupp.isSelected()){
                sql="SELECT Name FROM tblSupplier WHERE SupplierID="+iId;
            }
            table.setValueAt(fill.changeValue(sql),i,1);
        }
    }

    public void fbtnSave(){
        if(rbCust.isSelected()) {

            int ans=PopUp.ConfirmMessage(new JFrame(),"Continue? ","Confirm Modification",JOptionPane.YES_NO_OPTION);
            if (ans==JOptionPane.YES_OPTION){
                String sql="UPDATE tblPaymentCust\n" +
                        "SET custID='"+cus.getCustID()+"'\n" +
                        "  ,invoice_No="+txtInv.getText()+"\n" +
                        "  ,Date_Paid='"+txtDate.getText()+"'\n" +
                        "  ,Amount_Paid="+txtAmount.getText()+"\n" +
                        "WHERE PaymentID= "+selectedPayment;

                dbManipulation.Insert(sql);
                fbtnCancel();
                setData();
                refresh();
            }




        }
        else if(rbSupp.isSelected()) {

            int ans=PopUp.ConfirmMessage(new JFrame(),"Continue? ","Confirm Modification",JOptionPane.YES_NO_OPTION);
            if (ans==JOptionPane.YES_OPTION){
                String sql="UPDATE tblPaymentSup\n" +
                        "SET supID='"+sup.getSupID()+"'\n" +
                        "  ,invoice_No="+txtInv.getText()+"\n" +
                        "  ,Date_Paid='"+txtDate.getText()+"'\n" +
                        "  ,Amount_Paid="+txtAmount.getText()+"\n" +
                        "WHERE PaymentID= "+selectedPayment;

                dbManipulation.Insert(sql);
                fbtnCancel();
                setData();
                refresh();
            }

        }


        fbtnCancel();
        setData();
        refresh();
        Status.setChangeInSales(true);
    }

    public void fbtnCancel(){
        form.remove(btnCancel);
        form.remove(btnSave);
        form.remove(btnSave2);
        form.remove(CBcustomer);
        form.remove(CBSup);
        form.remove(btnModify);

        btnAdd.setEnabled(true);
        setComponent(btnModify,2,9);
        btnModify.setEnabled(false);
        Toggle(false);
        form.revalidate();
        form.repaint();
    }

    public void fbtnSave2(){
        boolean valid=false;

        try{
            valid = Validation.isFormValidp(txtName.getText(), txtInv.getText(), txtAmount.getText());
        }
        catch(Exception e){}

        if (valid){
            int sID;

            if (rbCust.isSelected()) {

                String sid = String.valueOf(customers.get(CBcustomer.getSelectedIndex()).getCid());
                int inv = Integer.parseInt(txtInv.getText());
                String date = txtDate.getText();
                float amount = Float.parseFloat(txtAmount.getText());


                int ans = PopUp.ConfirmMessage(new JFrame(), "Are You Sure?", "Confirm New Payment", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    String sql = "INSERT INTO tblPaymentCust (custID\n" +
                            "                        ,  invoice_No\n" +
                            "                        ,  Date_Paid\n" +
                            "                        ,  Amount_Paid\n" +
                            "                        )\n" +
                            "    VALUES ('" + sid + "'," + inv + ",'" + date + "'," + amount + ");";
                    dbManipulation.Insert(sql);
                    fbtnCancel();
                    refresh();
                    Status.setChangeInSales(true);

                    String sqlCust="SELECT Name,telephone,email,balanceDue\n" +
                            "FROM tblCustomer\n" +
                            "WHERE CustID= "+customers.get(CBcustomer.getSelectedIndex()).getCid();
                    Customer customer=id.searchCustomerRecord(sqlCust);
                    float amount2;
                    if (customer.getBalanceDue()>0){
                        amount2 = customer.getBalanceDue() - Float.parseFloat(txtAmount.getText());
                    }
                    else {
                        amount2 = customer.getBalanceDue() + Float.parseFloat(txtAmount.getText());
                    }
                    String sql2="UPDATE tblCustomer\n" +
                            "SET balanceDue='"+amount2+"'\n" +
                            "WHERE CustID= "+customers.get(CBcustomer.getSelectedIndex()).getCid();

                    sID=customers.get(CBcustomer.getSelectedIndex()).getCid();

                    dbManipulation.Insert(sql2);
                    fbtnCancel();
                    refresh();

                    Status.setChangeInPaymentcust(true);

                    setPayment.setSelectedID(sID);
                    Nav.changePanel(Page.INVOICE);
                }


            }
            else if (rbSupp.isSelected()) {


                String sid = String.valueOf(suppliers.get(CBSup.getSelectedIndex()).getSid());
                int inv = Integer.parseInt(txtInv.getText());
                String date = txtDate.getText();
                float amount = Float.parseFloat(txtAmount.getText());


                int ans = PopUp.ConfirmMessage(new JFrame(), "Are You Sure?", "Confirm New Payment", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    String sql = "INSERT INTO tblPaymentsup (supID\n" +
                            "                        ,  invoice_No\n" +
                            "                        ,  Date_Paid\n" +
                            "                        ,  Amount_Paid\n" +
                            "                        )\n" +
                            "    VALUES ('" + sid + "'," + inv + ",'" + date + "'," + amount + ");";
                    dbManipulation.Insert(sql);
                    fbtnCancel();
                    refresh();

                    String sqlSupp="SELECT Name,telephone,email,balanceDue\n" +
                            "FROM tblSupplier\n" +
                            "WHERE SupplierID="+suppliers.get(CBSup.getSelectedIndex()).getSid();

                    double bal=id.searchSupplier2Record(sqlSupp);

                    try{
                        float amount2 ;
                        if (bal>0){
                            amount2 = (float)bal - Float.parseFloat(txtAmount.getText());
                        }
                        else {
                            amount2  = (float)bal + Float.parseFloat(txtAmount.getText());
                        }
                        String sql2="UPDATE tblSupplier\n" +
                                "SET balanceDue='"+amount2+"'\n" +
                                "WHERE SupplierID= "+suppliers.get(CBSup.getSelectedIndex()).getSid();

                        dbManipulation.Insert(sql2);
                    }
                    catch(NullPointerException e){}
                    fbtnCancel();
                    refresh();
                }
            }
        }
    }
}
