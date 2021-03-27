package GUI;

import BackEnd.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.DoubleSummaryStatistics;

public class ViewSales extends AddForm{
    private JButton update;
    private JTextField txtCustomer;
    private JTextField txtProduct;
    private ID salesID=new ID();
    private Sales sales;
    private String query="SELECT SalesID FROM tblSales";
    private String CLName="SalesID";
    private ArrayList<Integer> id=salesID.getId(query,CLName);
    private static int selectedID=2;
    private JTextField txtPaid;
    private JButton next;
    private JButton previous;
    private JButton delete;
    private JButton first;
    private JButton last;
    private JButton back;
    private int ProdID;
    private static int position=0;



    public ViewSales(){
        body.removeAll();
        body.revalidate();
        body.repaint();

        Handler handler=new Handler();

        constraints.insets=new Insets(10,15,10,15);
        constraints.anchor=GridBagConstraints.WEST;

        title=new JLabel("View SALES");
        title.setFont(new Font("ARIEL BLACK",Font.BOLD,22));
        title.setForeground(Color.ORANGE);
        setComponent(title,2,0,new Dimension(200,30));

        lblCust=new JLabel("Customer Name");
        setFont(lblCust);
        setComponent(lblCust,0,4,new Dimension(250,30));

        txtCustomer=new JTextField();
        setComponent(txtCustomer,1,4);

        CBcustomer=new JComboBox(cname);

        lblDate=new JLabel("Date");
        setFont(lblDate);
        setComponent(lblDate,0,6);

        txtDate=new JTextField();
        txtDate.setEditable(false);
        txtDate.setText(DatePicker.getDefaultDate());
        setComponent(txtDate,1,6);

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
        setComponent(btnDate,2,6,new Dimension(50,30));

        lblInvoice=new JLabel("Invoice No");
        setFont(lblInvoice);
        setComponent(lblInvoice,0,8);
        txtInvoice=new JTextField();
        setComponent(txtInvoice,1,8);

        lblProduct=new JLabel("Product");
        setFont(lblProduct);
        setComponent(lblProduct,0,10);


        txtProduct=new JTextField();
        setComponent(txtProduct,1,10);


        CBProd=new JComboBox(pname);
        CBProd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    iPrice=products.get(index).getSp();
                    txtIprice.setText(String.valueOf(iPrice));
                    CalculateTotal();
                    txtProduct.setText(products.get(index).getName());
                }
            }
        });

        CBcustomer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index=combo.getSelectedIndex();
                    txtCustomer.setText(customers.get(index).getName());
                }
            }
        });

        lblQuantity=new JLabel("Quantity(Box)");
        setFont(lblQuantity);
        setComponent(lblQuantity,0,12,new Dimension(250,30));

        txtQuantity=new JTextField();
        setComponent(txtQuantity,1,12);

        lblWeight=new JLabel("Weight(Kg)");
        setFont(lblWeight);
        setComponent(lblWeight,0,14);

        txtWeight=new JTextField();
        setComponent(txtWeight,1,14);
        txtWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalculateTotal();
            }
        });

        lblIprice=new JLabel("Price");
        setFont(lblIprice);
        setComponent(lblIprice,0,16);

        txtIprice=new JTextField();
        txtIprice.setEditable(false);
        setComponent(txtIprice,1,16);

        lblDiscount=new JLabel("Discount");
        setFont(lblDiscount);

        txtDiscount=new JTextField();
        txtDiscount.setText("0");
        txtDiscount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalculateTotal();
            }
        });

        lblFprice=new JLabel("Final Price");
        setFont(lblFprice);

        txtFprice=new JTextField();
        txtFprice.setEditable(false);

        lblTotal=new JLabel("Total");
        setFont(lblTotal);
        setComponent(lblTotal,0,18);

        txtTotal=new JTextField();
        txtTotal.setEditable(false);
        setComponent(txtTotal,1,18);

        lblDelivered=new JLabel("Delivered By");
        setFont(lblDelivered);
        setComponent(lblDelivered,0,20,new Dimension(250,30));

        txtDelivered=new JTextField();
        setComponent(txtDelivered,1,20);

        lblPaid=new JLabel("Paid(Y/N)");
        setFont(lblPaid);
        setComponent(lblPaid,0,22);

        rbYes=new JRadioButton("Yes");
        rbNo=new JRadioButton("No");
        rbYes.addActionListener(handler);
        rbNo.addActionListener(handler);
        setFont(rbYes);
        setFont(rbNo);

        txtPaid=new JTextField();
        setComponent(txtPaid,1,22);


        gp2=new ButtonGroup();
        gp2.add(rbYes);
        gp2.add(rbNo);

        cancel=new JButton("CANCEL");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnCancel();
            }
        });

        save=new JButton("SAVE");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid;
                int ans;
                    CalculateTotal();
                    valid=Validation.isFormViewValid(txtInvoice.getText(),
                            txtQuantity.getText(),
                            txtWeight.getText(),txtDelivered.getText());


                    if (valid){
                        ans=PopUp.ConfirmMessage(new JFrame(),"Save?","SAVE",
                                JOptionPane.OK_CANCEL_OPTION);

                        Save(ans);
                    }
            }
        });
        update=new JButton("UPDATE");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnUpdate();
            }
        });
        setComponent(update,2,24);

        next=new JButton("NEXT RECORD");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchPosition();
                position++;
                setPosition();
                selectedID=id.get(position);
                setData();
            }
        });
        setComponent(next,4,26);

        previous=new JButton("PREVIOUS RECORD");
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchPosition();
                position--;
                setPosition();
                selectedID=id.get(position);
                setData();
            }
        });
        setComponent(previous,1,26);

        first=new JButton("FIRST");
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                position=0;
                selectedID=id.get(position);
                setData();
            }
        });
        setComponent(first,0,26);

        delete=new JButton("DELETE");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans=PopUp.ConfirmMessage(new JFrame(),"This Record is going to be deleted.Are You Sure?","Delete Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (ans==JOptionPane.YES_OPTION){
                    Delete();
                }
            }
        });
        setComponent(delete,3,24);

        last=new JButton("LAST");
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                position=id.size()-1;
                selectedID=id.get(position);
                setData();
            }
        });
        setComponent(last,5,26);

        back=new JButton("BACK");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nav.back();
            }
        });
        setComponent(back,5,24);

        ToggleAll(false);
        setColor(body);
        setData();


        add(body,BorderLayout.CENTER);

    }

    public void getVal(){
        iPrice=Double.parseDouble(txtIprice.getText());
        discount=Integer.parseInt(txtDiscount.getText());
        weight=Double.parseDouble(txtWeight.getText());
    }

    public void fbtnUpdate(){
        body.remove(update);
        body.remove(txtPaid);
        setComponent(CBcustomer,2,4);
        setComponent(CBProd,2,10);
        setComponent(txtDiscount,3,16);
        setComponent(txtFprice,5,16);
        setComponent(lblDiscount,2,16,new Dimension(90,30));
        setComponent(lblFprice,4,16,new Dimension(120,30));
        setComponent(save,1,24);
        setComponent(save,0,24);
        setComponent(cancel,2,24);
        setComponent(rbYes,1,22);
        setComponent(rbNo,2,22);
        setComponent(lblPaid,0,22);
        next.setEnabled(false);
        previous.setEnabled(false);
        last.setEnabled(false);
        first.setEnabled(false);
        back.setEnabled(false);
        ToggleAll(true);
        setColor(body);

        body.revalidate();
        body.repaint();
    }

    public void fbtnCancel(){
        body.remove(save);
        body.remove(CBcustomer);
        body.remove(CBProd);
        body.remove(lblDiscount);
        body.remove(txtDiscount);
        body.remove(lblFprice);
        body.remove(txtFprice);
        body.remove(cancel);
        body.remove(rbNo);
        body.remove(rbYes);
        setComponent(txtPaid,1,22);
        setComponent(update,2,24);
        next.setEnabled(true);
        previous.setEnabled(true);
        first.setEnabled(true);
        last.setEnabled(true);
        back.setEnabled(true);

        ToggleAll(false);
        setColor(body);
        body.revalidate();
        body.repaint();
    }

    private void ToggleAll(boolean toggle){
        for(Component control : body.getComponents())
        {
            if(control instanceof JTextField)
            {
                JTextField ctrl = (JTextField) control;
                ctrl.setEditable(toggle);
                setFont(ctrl);
            }
            else if (control instanceof JComboBox)
            {
                JComboBox ctr = (JComboBox) control;
                ctr.setSelectedIndex(-1);
            }
        }
        txtCustomer.setEditable(false);
        txtProduct.setEditable(false);
        txtFprice.setText("");
        txtFprice.setEditable(false);
        txtTotal.setEditable(false);
        txtDate.setEditable(false);
        btnDate.setEnabled(toggle);
        txtDiscount.setText("0");
        txtDate.setText(DatePicker.getDefaultDate());
    }

    private class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            CalculateTotal();
            paid=e.getActionCommand().toLowerCase().charAt(0);
        }
    }

    public void setData(){
        sales=salesID.searchSalesRecord("SELECT Invoice_No,sDate,Pid,quantity,Weight_Kg,Total,Paid,DeliveredBy,Cid\n" +
                "FROM tblSales\n" +
                "WHERE SalesID="+selectedID);
        txtCustomer.setText(getCustomerName(sales.getCid()));
        txtDate.setText(sales.getDate());
        txtInvoice.setText(String.valueOf(sales.getInvNo()));
        txtProduct.setText(getProductName(sales.getPid()));
        ProdID=sales.getPid();
        txtQuantity.setText(String.valueOf(sales.getQuantity()));
        txtWeight.setText(String.valueOf(sales.getWeight()));
        txtIprice.setText(getProductPrice(sales.getPid()));
        txtTotal.setText(String.valueOf(sales.getTotal()));
        txtDelivered.setText(sales.getDelivered());
        txtPaid.setText(String.valueOf(sales.getPaid()));
    }

    public void SearchPosition(){
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
        if (position==id.size()){
            position=0;
        }
        if (position==-1){
            position=id.size()-1;
        }
    }

    public static void setSelectedID(int id){
        selectedID=id;
    }

    public void Save(int ans){
        if (ans==JOptionPane.OK_OPTION){
            try{
                Status.setChangeInSales(true);

                int cid=sales.getCid();
                if (CBcustomer.getSelectedIndex()>=0){
                    cid=customers.get(CBcustomer.getSelectedIndex()).getCid();
                }

                int pid=sales.getPid();
                if (CBProd.getSelectedIndex()>=0){
                    pid=products.get(CBProd.getSelectedIndex()).getProdID();
                }

                String sql="UPDATE tblSales\n" +
                        "SET Invoice_No="+txtInvoice.getText()+",\n" +
                        "    sDate='"+txtDate.getText()+"',\n" +
                        "    Pid="+pid+",\n" +
                        "    quantity="+txtQuantity.getText()+",\n" +
                        "    Weight_Kg="+txtWeight.getText()+",\n" +
                        "    Total="+txtTotal.getText()+",\n" +
                        "    Paid='"+txtPaid.getText()+"',\n" +//paid not set
                        "    DeliveredBy='"+txtDelivered.getText()+"',\n" +
                        "    Cid="+cid+"\n" +
                        "WHERE SalesID="+selectedID;

                dbManipulation.Insert(sql);

            }
            catch(ArrayIndexOutOfBoundsException e){}
            finally {
                fbtnCancel();
                setData();
            }

        }
    }

    public void Delete(){
        SearchPosition();

        String sql="DELETE\n" +
                    "FROM tblSales\n" +
                    "WHERE SalesID="+selectedID;
        dbManipulation.Delete(sql);
        Status.setChangeInSales(true);

        position++;
        setPosition();
        selectedID=id.get(position);
        setData();
    }
}

