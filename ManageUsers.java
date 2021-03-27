package GUI;

import BackEnd.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ManageUsers extends GuiComponents implements Table{
    private JTable table;
    private Vector columnNamesVector=new Vector();
    private Vector row_dataVector=new Vector();
    private FillTable fill;
    private JLabel WPanel;
    private JPanel body;
    private ArrayList columnNames;
    private String search_sql="SELECT * FROM tblUser ";
    private DefaultTableModel tableModel;
    private Dimension size=new Dimension(200,35);
    private ID userID=new ID();
    private Users users;
    private String query="SELECT UserID FROM tblUser";
    private String CLName="UserID";
    private ArrayList<Integer> id=userID.getUsersid(query,CLName);
    private static int position=0;
    private JButton prevRec,updateRec,nextRec,btnDelete,btnCreateUser;
    private static int selectedID;
    private JLabel lblUname,lblPass;
    private JTextField txtUname;
    private JTextField txtPass;

    private JCheckBox chkNewUser,chkUpdate;
    private boolean valid;

    public ManageUsers(){

        body=new JPanel(new BorderLayout());
        createTable();
        add(body);

        WPanel=new JLabel();
        WPanel.setBackground(new Color(56, 111, 164));

        Icon icon=new ImageIcon(getClass().getResource("b2"));
        WPanel.setIcon(icon);
        WPanel.setLayout(layout);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        WPanel.setPreferredSize(new Dimension((int)(screensize.width*0.5),(screensize.height)));

        layout=new GridBagLayout();
        WPanel.setLayout(layout);
        constraints=new GridBagConstraints();


        constraints.insets=new Insets(50,5,50,5);
        chkUpdate = new JCheckBox("Update User Info");
        setFont(chkUpdate);
        setComponent(chkUpdate,0,0);
        chkUpdate.setSelected(true);
        chkUpdate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkUpdate.isSelected()){
                    chkNewUser.setSelected(false);
                    updateRec.setVisible(true);
                    btnCreateUser.setVisible(false);

                    btnDelete.setVisible(true);
                    nextRec.setVisible(true);
                    prevRec.setVisible(true);
                }
            }
        });

        chkNewUser = new JCheckBox("Create New User");
        setFont(chkNewUser);
        setComponent(chkNewUser,1,0);
        chkNewUser.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if (chkNewUser.isSelected()){
                    chkUpdate.setSelected(false);
                    btnCreateUser.setVisible(true);
                    updateRec.setVisible(false);
                    btnDelete.setVisible(false);
                    nextRec.setVisible(false);
                    prevRec.setVisible(false);

                    txtUname.setText("");
                    txtPass.setText("");
                }
            }
        });


        constraints.insets=new Insets(5,5,5,5);
        lblUname=new JLabel("Username:");
        setFont(lblUname);
        setComponent(lblUname,0,1);

        txtUname=new JTextField();
        setFont(txtUname);
        setComponent(txtUname,1,1);

        lblPass=new JLabel("Password:");
        setFont(lblPass);
        setComponent(lblPass,0,3);

        txtPass=new JTextField();
        setFont(txtPass);
        setComponent(txtPass,1,3);

        constraints.insets=new Insets(20,0,20,0); //change insets for buttons

        prevRec = new JButton("Previous Record");
        prevRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchPosition();
                position--;
                setPosition();
                selectedID=id.get(position);
                setData();
            }
        });
        setFont(prevRec);
        setComponent(prevRec,0,6,new Dimension(200,100));

        updateRec = new JButton("Update Record");
        updateRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String uname = txtUname.getText();
                String pass = txtPass.getText();
                valid = Validation.isFormValid(uname,pass);
                if(valid){
                    String sql="UPDATE tblUser\n" +
                            "SET \n" +
                            "  Name='"+uname+"'\n" +
                            "  ,l_password='"+pass+"'\n" +
                            "WHERE UserID="+selectedID;
                    dbManipulation.Insert(sql);
                    setData();
                    refresh();
                }
            }
            }
        );
        setFont(updateRec);
        setComponent(updateRec,1,6,new Dimension(200,100));

        nextRec = new JButton("Next Record");
        nextRec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchPosition();
                position++;
                setPosition();
                selectedID=id.get(position);
                setData();
            }
        });
        setFont(nextRec);
        setComponent(nextRec,2,6,new Dimension(200,100));

        btnDelete = new JButton("Delete Record");
        btnDelete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String sql="DELETE FROM tblUser\n" +
                        "WHERE UserID="+selectedID;

                dbManipulation.Delete(sql);
                refresh();
                EmptyAll();

                id.clear();
                id=userID.getUsersid(query,CLName);
            }
        });
        setFont(btnDelete);
        setComponent(btnDelete, 1,7, new Dimension(200,100));

        btnCreateUser = new JButton("Add Record");
        btnCreateUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String uname = txtUname.getText();
                String pass = txtPass.getText();

                valid = Validation.isFormValid(uname,pass);
                if(valid){
                    String sql="INSERT INTO tblUser(Name,l_password) VALUES('"+uname+"','"+pass+"')";
                    dbManipulation.Insert(sql);
                    refresh();
                    EmptyAll();
                }
            }
        });
        setFont(btnCreateUser);
        setComponent(btnCreateUser,1,6,new Dimension(200,100));

        setColor(WPanel);
        body.add(WPanel,BorderLayout.EAST);

    }

    public void EmptyAll(){
        for(Component control : WPanel.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
        }
        setColor(WPanel);
    }

    @Override
    public void createTable(){
        columnNames=new ArrayList();
        columnNames.add("UserID");
        columnNames.add("Name");
        columnNames.add("Password");

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
        body.add(scrollPane,BorderLayout.CENTER);

    }

    public void reloadData(){
        this.columnNamesVector.clear();
        row_dataVector.clear();
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
        WPanel.add(component);
    }

    public void setComponent(JComponent component,int x,int y,Dimension size){
        setComponent(component,x,y);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    public void SearchPosition(){
        id.clear();
        id=userID.getUsersid(query,CLName);
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
        users=userID.searchUserRecord("SELECT * FROM tblUser WHERE UserID ="+selectedID);
        txtUname.setText(users.getName());
        txtPass.setText(users.getPassword());

    }

    public static void setSelectedID(int id){
        selectedID=id;
    }
}