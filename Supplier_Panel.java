package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import BackEnd.*;


public class Supplier_Panel extends GuiComponents implements Table{
    private JTable table;
    private Vector columnNamesVector=new Vector();
    private Vector row_dataVector=new Vector();
    private FillTable fill;
    private JLabel Panel_option;
    private JPanel body;
    private ArrayList columnNames;
    private String search_sql = "SELECT * FROM tblSupplier ";
    private String ori_sql = "SELECT * FROM tblSupplier ";
    private DefaultTableModel tableModel;
    private Dimension size = new Dimension(200,35);
    private JLabel lblName,lblTel,lblEmail,lblBalDue,lblSearchSupName;
    private JTextField txtName, txtTel,txtEmail,txtBalDue,txtSearchSupName;
    private JCheckBox chkNewSup,chkUpdate, chkSearch;
    private JButton btnPrevRec,btnUpdateRec,btnNextRec,btnDelete,btnCreate,btnRefresh,btnReset;
    private static int selectedID;
    private ID suppliersID=new ID();
    private Supplier supplier;
    private String query="SELECT SupplierID FROM tblSupplier";
    private String CLName="SupplierID";
    private ArrayList<Integer> id=suppliersID.getId(query,CLName);
    private static int position=0;
    private String searchName="";
    private ButtonGroup cbGroup;

    private boolean valid;

    public Supplier_Panel(){
        body=new JPanel(new BorderLayout());
        add(body);

        createTable();

        Panel_option=new JLabel();
        Panel_option.setBackground(new Color(56, 111, 164));
        layout=new GridBagLayout();
        Panel_option.setLayout(layout);
        constraints=new GridBagConstraints();


        constraints.insets=new Insets(50,5,50,5);
        chkUpdate = new JCheckBox("Update Record");
        setFont(chkUpdate);
        setComponent(chkUpdate,0,0);
        chkUpdate.setSelected(true);

        chkUpdate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkUpdate.isSelected()){
                    EmptyAll();

                    ToggleAll(true);

                    btnCreate.setVisible(false);
                    lblSearchSupName.setVisible(false);
                    txtSearchSupName.setVisible(false);
                }
            }
        });

        chkNewSup = new JCheckBox("Create Record");
        setFont(chkNewSup);
        chkNewSup.setSelected(false);

        setComponent(chkNewSup,1,0);
        chkNewSup.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkNewSup.isSelected()){
                    EmptyAll();
                    ToggleAll(true);
                    btnUpdateRec.setVisible(false);
                    btnNextRec.setVisible(false);
                    btnPrevRec.setVisible(false);
                    lblSearchSupName.setVisible(false);
                    txtSearchSupName.setVisible(false);
                }
            }
        });

        chkSearch = new JCheckBox("Search Record");
        chkSearch.setSelected(false);
        setFont(chkSearch);
        setComponent(chkSearch,2,0);
        chkSearch.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(chkSearch.isSelected()){
                    ToggleAll(false);

                    lblSearchSupName.setVisible(true);
                    txtSearchSupName.setVisible(true);
                }
            }
        });

        cbGroup=new ButtonGroup();
        cbGroup.add(chkNewSup);
        cbGroup.add(chkSearch);
        cbGroup.add(chkUpdate);


        constraints.insets=new Insets(5,5,5,5);

        lblSearchSupName = new JLabel("Search by name: ");
        setFont(lblSearchSupName);
        setComponent(lblSearchSupName,0,1);
        lblSearchSupName.setVisible(false);


        txtSearchSupName = new JTextField();
        txtSearchSupName.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent keyEvent) {
                searchName = txtSearchSupName.getText();

                search_sql=" WHERE Name LIKE '"+searchName+"%'";
                search(ori_sql,search_sql);

                refresh();
            }
            public void keyTyped(KeyEvent e) {

            }
            public void keyPressed(KeyEvent e) {

            }
        });
        setFont(txtSearchSupName);
        txtSearchSupName.setVisible(false);
        setComponent(txtSearchSupName,1,1);

        lblName=new JLabel("Name:");
        setFont(lblName);
        setComponent(lblName,0,2);

        txtName=new JTextField();
        setFont(txtName);
        setComponent(txtName,1,2);

        lblTel=new JLabel("Telephone:");
        setFont(lblTel);
        setComponent(lblTel,0,3);

        txtTel=new JTextField();
        setFont(txtTel);
        setComponent(txtTel,1,3);

        lblEmail=new JLabel("Email:");
        setFont(lblEmail);
        setComponent(lblEmail,0,4);

        txtEmail=new JTextField();
        setFont(txtEmail);
        setComponent(txtEmail,1,4);

        lblBalDue=new JLabel("Balance Due:");
        setFont(lblBalDue);
        setComponent(lblBalDue,0,5);

        txtBalDue=new JTextField();
        setFont(txtBalDue);
        setComponent(txtBalDue,1,5);

        constraints.insets=new Insets(20,0,20,0); //change insets for buttons

        btnPrevRec = new JButton("Previous Record");
        btnPrevRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    SearchPosition();
                    position--;
                    setPosition();
                    selectedID=id.get(position);
                    setData();
                }
                catch (ArrayIndexOutOfBoundsException exp){}

            }
        });
        setFont(btnPrevRec);
        setComponent(btnPrevRec,0,6,new Dimension(200,50));

        btnUpdateRec = new JButton("Update Record");
        btnUpdateRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String getName = txtName.getText();
                    String getEmail = txtEmail.getText();
                    String Tel = txtTel.getText();
                    String BalDue = txtBalDue.getText();

                    valid = Validation.isSupFormValid(getName,Tel,getEmail,BalDue);

                    if(valid){
                        int getTel = Integer.parseInt(txtTel.getText());
                        float getBalDue = Float.parseFloat(txtBalDue.getText());
                        String sql="UPDATE tblSupplier SET Name = '"+getName+"' , telephone = "+getTel+" , email = '"+getEmail+"' , balanceDue = "+getBalDue +" WHERE SupplierID = "
                                +selectedID;
                        dbManipulation.Insert(sql);
                        refresh();
                    }
                }catch (Exception exp){}
            }
           }
        );
        setFont(btnUpdateRec);
        setComponent(btnUpdateRec,1,6,new Dimension(200,50));

        btnNextRec = new JButton("Next Record");
        btnNextRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    SearchPosition();
                    position++;
                    setPosition();
                    selectedID=id.get(position);
                    setData();
                }
                catch (ArrayIndexOutOfBoundsException exp){}

            }
        });
        setFont(btnNextRec);
        setComponent(btnNextRec,2,6,new Dimension(200,50));

        btnCreate = new JButton("Add Record");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String getName = txtName.getText();
                    String getEmail = txtEmail.getText();
                    String Tel = txtTel.getText();
                    String BalDue = txtBalDue.getText();

                    valid = Validation.isSupFormValid(getName,Tel,getEmail,BalDue);

                    if(valid){
                        int getTel = Integer.parseInt(txtTel.getText());
                        double getBalDue = Float.parseFloat(txtBalDue.getText());

                        String sql="INSERT INTO tblSupplier(Name,telephone, email,balanceDue) VALUES('"+getName+"' ,"+getTel+" ,'"+getEmail+"' ,"+getBalDue+")";
                        dbManipulation.Insert(sql);
                        refresh();
                    }
                }
                catch ( Exception exp ){}

            }
        });
        setFont(btnCreate);
        setComponent(btnCreate,1,6,new Dimension(200,50));

        setColor(Panel_option);
        Icon icon=new ImageIcon(getClass().getResource("b2"));
        Panel_option.setIcon(icon);

        Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
        Panel_option.setPreferredSize(new Dimension((int)(screen.width*0.40),(screen.height)));

        body.add(Panel_option,BorderLayout.EAST);
    }

    @Override
    public void createTable() {
        columnNames=new ArrayList();
        columnNames.add("ID");
        columnNames.add("Supplier");
        columnNames.add("Telephone");
        columnNames.add("Email");
        columnNames.add("BalanceDue");

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
                    JTable selectedTable = (JTable) e.getSource();
                    try{
                        int row = selectedTable.getSelectedRow();
                        setSelectedID((int)selectedTable.getValueAt(row,0));
                        setData();
                    }
                    catch (ArrayIndexOutOfBoundsException exp){}
                }
            }
        });

        setTableUI(table);
        JScrollPane scrollPane=new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        body.add(scrollPane,BorderLayout.CENTER);
    }

    public void EmptyAll(){
        for(Component control : Panel_option.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
        }
    }

    public void ToggleAll(boolean toggle){
        for(Component control : Panel_option.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setVisible(toggle);
            }
            else if(control instanceof JButton){
                JButton btn=(JButton) control;
                btn.setVisible(toggle);
            }
            else if (control instanceof JLabel){
                JLabel lbl=(JLabel) control;
                lbl.setVisible(toggle);
            }
        }
        setColor(Panel_option);
    }

    public void reloadData(){
        this.columnNamesVector.clear();
        this.row_dataVector.clear();
        fill=new FillTable(search_sql,columnNames);
        columnNamesVector=fill.getColumnNamesVector();
        row_dataVector=fill.getDataVector();
    }

    @Override
    public void changeTableValue() {

    }

    public void refresh(){
        try{
            reloadData();
            tableModel.setDataVector(row_dataVector,columnNamesVector);
            tableModel.fireTableDataChanged();
        }catch (Exception exp){}
    }

    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        Panel_option.add(component);
    }

    public void setComponent(JComponent component,int x,int y,Dimension size){
        setComponent(component,x,y);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    private void search(String ori,String str){
        search_sql=ori+str;
    }

    public void SearchPosition(){
        id.clear();
        id=suppliersID.getId(query,CLName);
        for (int i=0;i<id.size();i++){
            if (selectedID==id.get(i)) {
                position = i;
                break;
            }
            else {
                position=0;
            }
        }
    }

    public void setPosition(){
        if (position == id.size()){
            position=0;
        }
        if (position == -1){
            position=id.size()-1;
        }
    }

    public void setData(){
        if(chkUpdate.isSelected()){
            supplier =suppliersID.searchSupplierRecord("SELECT * FROM tblSupplier WHERE SupplierID = "+selectedID);
            txtName.setText(supplier.getName());
            txtTel.setText(String.valueOf(supplier.getTel()));
            txtEmail.setText(supplier.getEmail());
            txtBalDue.setText(String.valueOf(supplier.getBalDue()));
        }

    }

    public static void setSelectedID(int id){
        selectedID=id;
    }
}