package GUI;

import BackEnd.*;
import BackEnd.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddForm extends GuiComponents{
    protected JLabel body;
    protected JLabel title;
    protected JRadioButton existing;
    protected JRadioButton newCust;
    protected ButtonGroup gp;
    protected JLabel lblCust;
    protected JComboBox CBcustomer;
    protected JLabel lblDate;
    protected JTextField txtDate;
    protected JButton btnDate;
    protected JLabel lblInvoice;
    protected JTextField txtInvoice;
    protected JLabel lblProduct;
    protected JComboBox CBProd;
    protected JLabel lblQuantity;
    protected JTextField txtQuantity;
    protected JLabel lblWeight;
    protected JTextField txtWeight;
    protected JLabel lblIprice;
    protected JTextField txtIprice;
    protected JLabel lblDiscount;
    protected JTextField txtDiscount;
    protected JLabel lblFprice;
    protected JTextField txtFprice;
    protected JLabel lblTotal;
    protected JTextField txtTotal;
    protected JLabel lblDelivered;
    protected JTextField txtDelivered;
    protected double iPrice;
    protected double discount;
    protected double fPrice;
    protected double total;
    protected double weight;
    protected char paid;
    protected JLabel lblPaid;
    protected JRadioButton rbYes;
    protected JRadioButton rbNo;
    protected ButtonGroup gp2;
    protected Dimension size=new Dimension(170,30);
    protected JButton save;
    protected JButton cancel;

    public AddForm(){
        Handler handler=new Handler();

        body=new JLabel();
        body.setLayout(layout);
        constraints.insets=new Insets(10,10,10,10);

        title=new JLabel("ADD SALES");
        title.setFont(new Font("ARIEL BLACK",Font.BOLD,22));
        title.setForeground(Color.ORANGE);
        setComponent(title,1,0,new Dimension(200,30));

        existing=new JRadioButton("EXISTING");
        newCust=new JRadioButton("New Customer");
        newCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerForm form=new CustomerForm();
                JFrame frame=new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
                frame.setSize(size);
                frame.setVisible(true);
                frame.add(form.getBody());
                refresh();
            }
        });
        setFont(existing);
        setFont(newCust);
        setComponent(existing,0,2);
        setComponent(newCust,1,2);
        gp=new ButtonGroup();
        gp.add(existing);
        gp.add(newCust);
        existing.setSelected(true);

        lblCust=new JLabel("Customer Name");
        setFont(lblCust);
        setComponent(lblCust,0,4);

        CBcustomer=new JComboBox(cname);
        CBcustomer.setSelectedIndex(-1);
        CBcustomer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();


                }
            }
        });
        setComponent(CBcustomer,1,4);

        constraints.insets=new Insets(10,0,10,0);
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
        btnDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatePicker datePicker=new DatePicker();
                txtDate.setText(datePicker.getDate());
            }
        });
        setComponent(btnDate,2,6,new Dimension(30,30));
        constraints.insets=new Insets(10,10,10,10);


        lblInvoice=new JLabel("Invoice No");
        setFont(lblInvoice);
        setComponent(lblInvoice,0,8);
        txtInvoice=new JTextField();
        setComponent(txtInvoice,1,8);

        lblProduct=new JLabel("Product");
        setFont(lblProduct);
        setComponent(lblProduct,0,10);

        this.CBProd=new JComboBox(pname);
        this.CBProd.setSelectedIndex(-1);
        this.CBProd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox combo = (JComboBox) e.getSource();
                    int index = combo.getSelectedIndex();
                    iPrice=products.get(index).getSp();
                    txtIprice.setText(String.valueOf(iPrice));
                }
            }
        });
        setComponent(CBProd,1,10);

        lblQuantity=new JLabel("Quantity(Box)");
        setFont(lblQuantity);
        setComponent(lblQuantity,0,12);

        txtQuantity=new JTextField();
        setComponent(txtQuantity,1,12);

        lblWeight=new JLabel("Weight(Kg)");
        setFont(lblWeight);
        setComponent(lblWeight,0,14);

        txtWeight=new JTextField();
        setComponent(txtWeight,1,14);
        txtWeight.addActionListener(handler);

        lblIprice=new JLabel("Price");
        setFont(lblIprice);
        setComponent(lblIprice,0,16);

        txtIprice=new JTextField();
        txtIprice.setEditable(false);
        setComponent(txtIprice,1,16);

        lblDiscount=new JLabel("Discount");
        setFont(lblDiscount);
        setComponent(lblDiscount,2,16,new Dimension(90,30));

        txtDiscount=new JTextField();
        txtDiscount.setText("0");
        setComponent(txtDiscount,3,16);
        txtDiscount.addActionListener(handler);

        lblFprice=new JLabel("Final Price");
        setFont(lblFprice);
        setComponent(lblFprice,4,16,new Dimension(120,30));

        txtFprice=new JTextField();
        txtFprice.setEditable(false);
        setComponent(txtFprice,5,16);

        lblTotal=new JLabel("Total");
        setFont(lblTotal);
        setComponent(lblTotal,0,18);

        txtTotal=new JTextField();
        txtTotal.setEditable(false);
        setComponent(txtTotal,1,18);

        lblDelivered=new JLabel("Delivered By");
        setFont(lblDelivered);
        setComponent(lblDelivered,0,20);

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
        setComponent(rbYes,1,22);
        setComponent(rbNo,2,22);
        rbNo.setSelected(true);

        gp2=new ButtonGroup();
        gp2.add(rbYes);
        gp2.add(rbNo);

        save=new JButton("SAVE");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fbtnSave();
            }
        });
        cancel=new JButton("CANCEL");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmptyAll();
                Nav.back();
            }
        });
        setComponent(save,5,24);
        setComponent(cancel,4,24);

        Icon icon=new ImageIcon(getClass().getResource("b"));
        body.setIcon(icon);
        setColor(body);
        add(body,BorderLayout.CENTER);

    }

    public void EmptyAll(){
        for(Component control : body.getComponents())
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
            else if (control instanceof JRadioButton){
                JRadioButton ct=(JRadioButton) control;
                ct.setSelected(false);
            }
        }
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

    public void getVal(){
        discount=Integer.parseInt(txtDiscount.getText());
        weight=Double.parseDouble(txtWeight.getText());
    }

    public void CalculateTotal(){
        try{
            getVal();
            if (!Validation.isDiscountValid(discount)){
                return;
            }
            fPrice=iPrice-discount;
            txtFprice.setText(String.valueOf(fPrice));
            if (!Validation.isWeightValid(weight)){
                return;
            }
            total=weight*fPrice;
            txtTotal.setText(String.valueOf(total));
        }catch (Exception exp){}
    }

    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        body.add(component,constraints);
    }

    public void setComponent(JComponent component,int x,int y,Dimension size){
        setComponent(component,x,y);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    public void fbtnSave(){
        boolean valid;
        int ans;
        try{
            CalculateTotal();
            valid=Validation.isFormValid(CBcustomer.getSelectedIndex(),txtInvoice.getText(),
                    CBProd.getSelectedIndex(), txtQuantity.getText(),
                    txtWeight.getText(),txtDelivered.getText());


            if (txtDate.getText().equals(DatePicker.getDefaultDate())){
                int level=ID.getStockLevel(products.get(CBProd.getSelectedIndex()).getProdID());
                int quan=Integer.parseInt(txtQuantity.getText());
                if (quan>level){
                    PopUp.DisplayMessage(new JFrame(),"Not Enough in Stock","Stock Level",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    int remaining=level-quan;
                    String sql="UPDATE tblProduct\n" +
                                "SET Stock="+remaining+"\n" +
                                "WHERE ProductID="+products.get(CBProd.getSelectedIndex()).getProdID();
                    Status.setChangeInSales(true);
                    dbManipulation.Insert(sql);
                }
            }


            if (valid){
                ans=PopUp.ConfirmMessage(new JFrame(),"Save?","SAVE",
                        JOptionPane.OK_CANCEL_OPTION);

                Save(ans);
            }
        }catch (Exception exp){}
    }

    public void Save(int ans){
        if (ans==JOptionPane.OK_OPTION){
            Status.setChangeInSales(true);
            String sql="INSERT INTO tblSales(Invoice_No,sDate,Pid,quantity,Weight_Kg,Total," +
                    "Paid,DeliveredBy,Cid)\n" +
                    "VALUES ("+
                    txtInvoice.getText()+",'"+
                    txtDate.getText()+"',"+
                    products.get(CBProd.getSelectedIndex()).getProdID()+ ","+
                    txtQuantity.getText()+","+
                    txtWeight.getText()+","+
                    txtTotal.getText()+",'"+
                    paid+"','"+
                    txtDelivered.getText()+"'," +
                    customers.get(CBcustomer.getSelectedIndex()).getCid()+")";
            dbManipulation.Insert(sql);
            EmptyAll();
            Nav.back();
        }
    }

    public void refresh(){
        if (Status.isChangeInProduct()){
            resetCombo(this.CBProd,pname);
        }
        if (Status.isChangeInCustomer()){
            resetCombo(this.CBcustomer,cname);
        }
        EmptyAll();
    }

}

