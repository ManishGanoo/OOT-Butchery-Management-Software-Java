package GUI;
import BackEnd.*;
import BackEnd.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Home extends GuiComponents{
    private JButton sales;
    private JButton purchases;
    private JButton client;
    private JButton supplier;
    private JButton product;
    private JButton payments;

    public Home(){
        JLabel body=new JLabel();
        Icon icon=new ImageIcon(getClass().getResource("b"));
        body.setIcon(icon);
        body.setLayout(layout);

        sales=new JButton("Sales");
        purchases=new JButton("Purchases");
        client=new JButton("Client");
        supplier=new JButton("Supplier");
        product=new JButton("Product");
        payments=new JButton("Payments");

        setButton(sales);
        setButton(purchases);
        setButton(client);
        setButton(supplier);
        setButton(product);
        setButton(payments);

        constraints.insets=new Insets(100,100,100,100);


        constraints.gridx=0;
        constraints.gridy=0;
        layout.setConstraints(sales,constraints);
        body.add(sales);


        constraints.gridx=1;
        constraints.gridy=0;
        layout.setConstraints(purchases,constraints);
        body.add(purchases);

        constraints.gridx=2;
        constraints.gridy=0;
        layout.setConstraints(client,constraints);
        body.add(client);

        constraints.gridx=0;
        constraints.gridy=1;
        layout.setConstraints(supplier,constraints);
        body.add(supplier);

        constraints.gridx=1;
        constraints.gridy=1;
        layout.setConstraints(product,constraints);
        body.add(product);

        constraints.gridx=2;
        constraints.gridy=1;
        layout.setConstraints(payments,constraints);
        body.add(payments);


        add(body,BorderLayout.CENTER);

        Handler handler=new Handler();
        sales.addActionListener(handler);
        purchases.addActionListener(handler);
        client.addActionListener(handler);
        supplier.addActionListener(handler);
        product.addActionListener(handler);
        payments.addActionListener(handler);

    }

    private class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Sales":{
                    Nav.changePanel(Page.SALES);
                }break;
                case "Product":{
                    Nav.changePanel(Page.PRODUCT);
                }
                break;
                case "Supplier":{
                    Nav.changePanel(Page.SUPPLIER);
                }break;
                case "Client":{
                    Nav.changePanel(Page.CLIENT);
                }break;
                case "Purchases":{
                    Nav.changePanel(Page.PURCHASES);
                }break;
                case "Payments":{
                    Nav.changePanel(Page.PAYMENT);
                }break;
            }

        }
    }

    public void setButton(JButton button){
        Dimension buttonSize=new Dimension(200,100);
        button.setPreferredSize(buttonSize);
        button.setBackground(new Color(0, 0, 0));
        Font police1 = new Font("Tahoma", Font.BOLD, 22);
        button.setFont(police1);
        button.setForeground(new Color(255, 0, 99));
    }
}
