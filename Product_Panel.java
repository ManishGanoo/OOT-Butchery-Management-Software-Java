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
 * Created by erosennin on 3/11/17.
 */
public class Product_Panel extends GuiComponents implements Table{
    private ArrayList<String> columnNames;
    private JTable table;
    private DefaultTableModel tableModel;
    private Vector row_dataVector=new Vector();
    private Vector columnNamesVector=new Vector();
    private JPanel body;
    private FillTable fill;
    private String search_sql;
    private JLabel form;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblBp;
    private JTextField txtBp;
    private JLabel lblSp;
    private JTextField txtSp;
    private JLabel lblStock;
    private JTextField txtStock;
    private JButton btnModify;
    private JButton btnCancel;
    private JButton btnSave;
    private JButton btnSave2;
    private JButton btnAdd;
    private Dimension size=new Dimension(200,30);
    private ID productID=new ID();
    private Product product;
    private static int selectedProduct;


    public Product_Panel(){
        body=new JPanel(new BorderLayout());
        form=new JLabel();
        Icon icon=new ImageIcon(getClass().getResource("b2"));
        form.setIcon(icon);
        form.setLayout(layout);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        form.setPreferredSize(new Dimension((int)(screensize.width*0.35),(screensize.height)));

        constraints.insets=new Insets(10,10,10,10);
        constraints.anchor=GridBagConstraints.WEST;

        lblName=new JLabel("Description");
        setFont(lblName);
        setComponent(lblName,0,0);

        txtName=new JTextField();
        setComponent(txtName,1,0);

        lblBp=new JLabel("Buying Price");
        setFont(lblBp);
        setComponent(lblBp,0,2);

        txtBp=new JTextField();
        setComponent(txtBp,1,2);

        lblSp=new JLabel("Selling Price");
        setFont(lblSp);
        setComponent(lblSp,0,4);

        txtSp=new JTextField();
        setComponent(txtSp,1,4);

        lblStock=new JLabel("Stock");
        setFont(lblStock);
        setComponent(lblStock,0,6);

        txtStock=new JTextField();
        setComponent(txtStock,1,6);

        btnModify=new JButton("MODIFY");
        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toggle(true);
                form.remove(btnModify);
                setComponent(btnSave,1,8);
                setComponent(btnCancel,2,8);
                form.revalidate();
                form.repaint();
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
                form.remove(btnModify);
                btnAdd.setEnabled(false);
                setComponent(btnSave2,1,8);
                setComponent(btnCancel,2,8);

                Toggle(true);
                form.revalidate();
                form.repaint();
            }
        });
        setComponent(btnAdd,0,8);


        createTable();

        Toggle(false);

        setColor(form);
        body.add(form,BorderLayout.CENTER);

        add(body);
    }

    public void Toggle(boolean value){
        for(Component control : form.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setEditable(value);
                setFont(ctrl);
            }
        }
        setColor(form);
    }

    public void EmptyAll(){
        for(Component control : form.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setText("");
            }
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

    @Override
    public void createTable(){
        columnNames=new ArrayList();
        columnNames.add("ProductID");
        columnNames.add("Description");
        columnNames.add("Stock");
        columnNames.add("Buying Price");
        columnNames.add("Selling Price");

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
                        setSelectedProduct((int)target.getValueAt(row,0));
                        btnModify.setEnabled(true);
                        setData();
                    }
                    catch (ArrayIndexOutOfBoundsException exp){}

                }
            }
        });


        JScrollPane scrollPane=new JScrollPane(table);

        setTableUI(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(ScreenSize.width,(int)(ScreenSize.height*0.5)));
        body.add(scrollPane,BorderLayout.NORTH);
    }

    @Override
    public void reloadData(){
        search_sql="SELECT * FROM tblProduct";
        columnNamesVector.clear();
        row_dataVector.clear();
        fill=new FillTable(search_sql,columnNames);
        columnNamesVector=fill.getColumnNamesVector();
        row_dataVector=fill.getDataVector();
    }

    @Override
    public void changeTableValue(){}

    public static void setSelectedProduct(int id){
        selectedProduct=id;
    }

    public void setData(){
        product=productID.searchProductRecord("SELECT Name,Stock,buyingPrice,sellingPrice\n" +
                "FROM tblProduct\n" +
                "WHERE ProductID="+selectedProduct);

        txtName.setText(product.getName());
        txtBp.setText(String.valueOf(product.getBp()));
        txtSp.setText(String.valueOf(product.getSp()));
        txtStock.setText(String.valueOf(product.getStock()));
    }

    public void fbtnSave(){
        int ans=PopUp.ConfirmMessage(new JFrame(),"Continue? ","Confirm Modification",JOptionPane.YES_NO_OPTION);
        if (ans==JOptionPane.YES_OPTION){
            String sql="UPDATE tblProduct\n" +
                    "SET Name='"+txtName.getText()+"'\n" +
                    "  ,Stock="+txtStock.getText()+"\n" +
                    "  ,buyingPrice="+txtBp.getText()+"\n" +
                    "  ,sellingPrice = "+txtSp.getText()+"\n" +
                    "WHERE ProductID="+selectedProduct;

            dbManipulation.Insert(sql);
            fbtnCancel();
            setData();
            refresh();
            Status.setChangeInProduct(true);
        }
    }

    public void fbtnCancel(){
        form.remove(btnCancel);
        form.remove(btnSave);
        form.remove(btnSave2);
        btnAdd.setEnabled(true);
        setComponent(btnModify,1,8);
        Toggle(false);
        form.revalidate();
        form.repaint();
    }

    public void refresh(){
        try{
            reloadData();
            tableModel.setDataVector(row_dataVector,columnNamesVector);
            tableModel.fireTableDataChanged();
        }catch (Exception exp){}
    }

    public void fbtnSave2(){
        boolean valid=Validation.isFormValid(txtName.getText(),txtBp.getText(),txtSp.getText(),txtStock.getText());

        try{
            String name=txtName.getText();
            int bp=Integer.parseInt(txtBp.getText());
            int sp=Integer.parseInt(txtSp.getText());
            int stock=Integer.parseInt(txtStock.getText());

            if (valid){
                int ans=PopUp.ConfirmMessage(new JFrame(),"Are You Sure?","Confirm New Product",JOptionPane.YES_NO_OPTION);
                if (ans==JOptionPane.YES_OPTION){
                    String sql="INSERT INTO tblProduct (Name\n" +
                            "                        ,  Stock\n" +
                            "                        ,  buyingPrice\n" +
                            "                        ,  sellingPrice\n" +
                            "                        )\n" +
                            "    VALUES ('"+name+"',"+stock+","+bp+","+sp+");";
                    dbManipulation.Insert(sql);
                    fbtnCancel();
                    refresh();
                    Status.setChangeInProduct(true);
                }
            }
        }
        catch(Exception e){}
    }

}
