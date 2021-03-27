package GUI;
import BackEnd.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
/**
 * Created by Rawshirao on 10/03/2017.
 */
public class CustomerForm extends GuiComponents implements Table {

    private ArrayList<String> columnNames;
    private JTable table;
    private DefaultTableModel tableModel;
    private Vector row_dataVector=new Vector();
    private Vector columnNamesVector=new Vector();
    private JPanel body;
    private FillTable fill;
    private String search_sql;
    private JLabel Cform;
    private JLabel lblName = new JLabel("Name");
    private JLabel lbltelephone = new JLabel("Telephone");
    private JLabel lblemail = new JLabel("Email");
    private JLabel lblbalanceDue = new JLabel("Balance Due");
    private JTextField txtName = new JTextField(15);
    private JTextField txttelephone = new JTextField(15);
    private JTextField txtemail = new JTextField(15);
    private JTextField txtBalanceDue = new JTextField(15);
    private JButton btnModify;
    private JButton btnCancel;
    private JButton btnSave;
    private JButton btnSave2;
    private JButton btnAdd;
    private Dimension size=new Dimension(200,30);
    private ID customerID = new ID();
    private JScrollPane scrollPane;
    private Customer customer;
    private static int selectedCustomer;

    public CustomerForm() {

        body=new JPanel(new BorderLayout());
        Cform =new JLabel();
        Icon icon=new ImageIcon(getClass().getResource("b2"));
        Cform.setIcon(icon);
        Cform.setLayout(layout);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Cform.setPreferredSize(new Dimension((int)(screensize.width*0.35),(screensize.height)));



        constraints.insets=new Insets(10,10,10,10);
        constraints.anchor=GridBagConstraints.WEST;

        setFont(lblName);
        setComponent(lblName,0,0);
        setComponent(txtName,1,0);

        setFont(lbltelephone);
        setComponent(lbltelephone,0,2);
        setComponent(txttelephone,1,2);

        setFont(lblemail);
        setComponent(lblemail,0,4);
        setComponent(txtemail,1,4);

        setFont(lblbalanceDue);
        setComponent(lblbalanceDue,0,6);
        setComponent(txtBalanceDue,1,6);
        txtBalanceDue.setEditable(false);

        btnModify=new JButton("MODIFY");
        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toggle(true);
                Cform.remove(btnModify);
                setComponent(btnSave,1,8);
                setComponent(btnCancel,2,8);
                Cform.revalidate();
                Cform.repaint();
            }
        });
        btnModify.setEnabled(false);
        setComponent(btnModify,1,8);

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
                Cform.remove(btnModify);
                btnAdd.setEnabled(false);
                txtBalanceDue.setEditable(false);
                setComponent(btnSave2,1,8);
                setComponent(btnCancel,2,8);

                Toggle(true);
                Cform.revalidate();
                Cform.repaint();
            }
        });
        setComponent(btnAdd,0,8);

        txtBalanceDue.setText("0");


        createTable();

        Toggle(false);

        setColor(Cform);
        body.add(Cform,BorderLayout.CENTER);

        add(body);

    }

    public void Toggle(boolean value){
        for(Component control : Cform.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setEditable(value);
                setFont(ctrl);
            }
        }
        txtBalanceDue.setEditable(false);
        setColor(Cform);
    }

    public void EmptyAll(){
        for(Component control : Cform.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
        }
        txtBalanceDue.setText("0");
        setColor(Cform);
    }

    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        Cform.add(component);
    }

    @Override
    public void createTable(){
        columnNames=new ArrayList();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("telephone");
        columnNames.add("email");
        columnNames.add("balanceDue");

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

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    try{
                        int row = target.getSelectedRow();
                        setSelectedCustomer((int)target.getValueAt(row,0));
                        btnModify.setEnabled(true);
                        setData();
                    }
                    catch (ArrayIndexOutOfBoundsException exp){}

                }
            }
        });


        scrollPane=new JScrollPane(table);

        setTableUI(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(ScreenSize.width,(int)(ScreenSize.height*0.5)));
        body.add(scrollPane,BorderLayout.NORTH);
    }

    @Override
    public void reloadData(){
        search_sql="SELECT * FROM tblCustomer";
        columnNamesVector.clear();
        row_dataVector.clear();
        fill=new FillTable(search_sql,columnNames);
        columnNamesVector=fill.getColumnNamesVector();
        row_dataVector=fill.getDataVector();
    }

    @Override
    public void changeTableValue(){}

    public static void setSelectedCustomer(int id){
        selectedCustomer=id;
    }

    public void setData(){
        customer = customerID.searchCustomerRecord("SELECT Name,telephone,email,balanceDue\n" +
                "FROM tblCustomer\n" +
                "WHERE CustID="+selectedCustomer);

        txtName.setText(customer.getName());
        txttelephone.setText(String.valueOf(customer.getTelephone()));
        txtemail.setText(String.valueOf(customer.getEmail()));
        txtBalanceDue.setText(String.valueOf(customer.getBalanceDue()));
    }

    public void fbtnSave(){
        int ans=PopUp.ConfirmMessage(new JFrame(),"Continue? ","Confirm Modification",JOptionPane.YES_NO_OPTION);
        if (ans==JOptionPane.YES_OPTION){
            String sql="UPDATE tblCustomer\n" +
                    "SET Name='"+txtName.getText()+"'\n" +
                    "  ,telephone="+txttelephone.getText()+"\n" +
                    "  ,email='"+txtemail.getText()+"'\n" +
                    "WHERE CustID="+selectedCustomer;

            dbManipulation.Insert(sql);
            fbtnCancel();
            setData();
            refresh();

            Status.setChangeInCustomer(true);
        }
    }

    public void fbtnCancel(){

        Cform.remove(btnCancel);
        Cform.remove(btnSave);
        Cform.remove(btnSave2);
        Toggle(false);
        setComponent(btnModify,1,8);
        btnAdd.setEnabled(true);
        Cform.revalidate();
        Cform.repaint();
    }

    public void refresh(){
        try{
            reloadData();
            tableModel.setDataVector(row_dataVector,columnNamesVector);
            tableModel.fireTableDataChanged();
        }catch (Exception exp){}
    }

    public void fbtnSave2(){
        try{
            boolean valid=Validation.isFormValidC(txtName.getText(),txttelephone.getText(),txtemail.getText());

            String name = txtName.getText();
            int tel =Integer.parseInt(txttelephone.getText());
            String email = txtemail.getText();

            if (valid){
                int ans=PopUp.ConfirmMessage(new JFrame(),"Are You Sure?","Confirm New Customer",JOptionPane.YES_NO_OPTION);
                if (ans==JOptionPane.YES_OPTION){
                    String sql="INSERT INTO tblCustomer (Name\n" +
                            "                        ,  telephone\n" +
                            "                        ,  email\n" +
                            "                        ,  balanceDue\n" +
                            "                        )\n" +
                            "    VALUES ('"+name+"',"+tel+",'"+email+"',0);";
                    dbManipulation.Insert(sql);
                    fbtnCancel();
                    refresh();
                    Status.setChangeInProduct(true);
                }
            }
        }
        catch(NumberFormatException e){}
    }

    public JPanel getBody(){
        JPanel panel=body;
        panel.remove(menuBar);
        panel.revalidate();
        panel.repaint();
        return panel;
    }

}