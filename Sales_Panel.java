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

/**
 * Created by erosennin on 2/24/17.
 */
public class Sales_Panel extends GuiComponents implements Table{
    private JTable table;
    private Vector columnNamesVector=new Vector();
    private Vector row_dataVector=new Vector();
    private FillTable fill;
    private JPanel Panel_search;
    private JPanel body;
    private ArrayList columnNames;
    private JComboBox CBcustomer;
    private JComboBox CBproduct;
    private JTextField txtInvoice;
    private JButton go;
    private DefaultTableModel tableModel;
    private JButton reset;
    private JComboBox orderBY;
    private JLabel lblorderBY;
    private JButton from;
    private JButton to;
    private JLabel lblFrom;
    private JLabel lblTo;
    private JButton addSales;
    private JButton total;
    private JComboBox CBsearch;
    private String defaultDate=DatePicker.getDefaultDate();
    private String previousMonth=DatePicker.getPreviousMonth();
    private JButton btnRefresh;
    private String dfrom=previousMonth;
    private String dto=defaultDate;
    private String search_sql="SELECT * FROM tblSales WHERE (sDate>='"+dfrom+"' AND sDate<='"+dto+"')";
    private String ori_sql="SELECT * FROM tblSales WHERE (sDate>='"+dfrom+"' AND sDate<='"+dto+"')";

    public Sales_Panel() {

        body=new JPanel(new BorderLayout());
        add(body);

        Panel_search=new JPanel();
        Panel_search.setBackground(new Color(61, 61, 61));
        layout=new GridBagLayout();
        Panel_search.setLayout(layout);
        constraints.insets=new Insets(5,5,5,5);


        CBcustomer=new JComboBox(cname);
        CBcustomer.setSelectedIndex(-1);
        CBcustomer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    search_sql=" AND Cid="+customers.get(index).getCid();
                    search(ori_sql,search_sql);
                }
            }
        });


        txtInvoice=new JTextField("InvoiceNo");
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
        String[] type={"Customer","Product","Invoice","Date","Paid"};
        String[] type2={"Customer","Product","Invoice"};

        String[] dbName={"Cid","Pid","Invoice_No","sDate","Paid"};
        JComponent[] components={CBcustomer,CBproduct,txtInvoice};

        CBsearch=new JComboBox(type2);
        CBsearch.setSelectedIndex(-1);
        CBsearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    Panel_search.remove(CBsearch);
                    if (Objects.equals(components[index], txtInvoice)){
                        txtInvoice.setEnabled(true);
                    }
                    setComponent(components[index],1,0);
                    Panel_search.revalidate();
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


        body.add(Panel_search,BorderLayout.NORTH);

        createTable();

        go=new JButton("GO");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtInvoice.isEnabled()){
                    search_sql=" AND Invoice_No="+txtInvoice.getText();
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
                    Panel_search.remove(components[CBsearch.getSelectedIndex()]);
                    setComponent(CBsearch,1,0);
                    CBproduct.setSelectedIndex(-1);
                    CBcustomer.setSelectedIndex(-1);
                    CBsearch.setSelectedIndex(-1);
                    txtInvoice.setEnabled(false);
                }
                orderBY.setSelectedIndex(-1);
                to.setText(defaultDate);
                from.setText(previousMonth);
                setSearch(previousMonth,defaultDate);
                Panel_search.revalidate();
                refresh();
            }
        });
        setComponent(reset,1,3);

        btnRefresh=new JButton("REFRESH");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Status.isChangeInSales()){
                    refresh();
                }
            }
        });
        setComponent(btnRefresh,4,3);

        addSales=new JButton("ADD SALES");
        addSales.setAlignmentX(RIGHT_ALIGNMENT);
        addSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nav.changePanel(Page.ADDFORM);
            }
        });
        constraints.anchor=GridBagConstraints.LAST_LINE_END;
        setComponent(addSales,13,4);

        total=new JButton("TOTAL");
        total.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="SELECT SUM(Total) as TOTAL\n" +
                        "FROM tblSales\n" +
                        "where Paid='n'";
                double total=fill.getTotal(sql);
                PopUp.DisplayMessage(new JFrame(),
                        "Total is: "+total,"Total Unpaid",
                        JOptionPane.CLOSED_OPTION);
            }
        });
        setComponent(total,13,5);
        setColor(Panel_search);
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
        columnNames.add("Paid(Y/N)");
        columnNames.add("Delivered By");
        columnNames.add("Customer");
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
                        ViewSales.setSelectedID((int)target.getValueAt(row,0));
                    }
                    catch (ArrayIndexOutOfBoundsException exp){}
                    Nav.changePanel(Page.VIEWSALES);
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
            Object oCid=table.getValueAt(i,9);
            int iCid=(int)oCid;
            String sql2="SELECT Name FROM tblCustomer WHERE CustID="+iCid;
            table.setValueAt(fill.changeValue(sql2),i,9);
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
        }
        if (Status.isChangeInCustomer()){
            resetCombo(this.CBcustomer,cname);
            this.CBcustomer.setSelectedIndex(-1);
        }
        CBcustomer.setSelectedIndex(-1);
    }

    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        Panel_search.add(component);
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
        search_sql="SELECT * FROM tblSales WHERE (sDate>='"+d1+"' AND sDate<='"+d2+"')";
    }
}