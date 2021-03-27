package GUI;

import BackEnd.Status;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by erosennin on 2/20/17.
 */
public class Nav extends JFrame {
    private static JPanel contentPanel=new JPanel();
    private static CardLayout cardLayout=new CardLayout();
    private static Sales_Panel sales_panel;
    private static Home home;
    private static AddForm addForm;
    private static ViewSales viewSales;
    private static ManageUsers manageUsers;
    private static Product_Panel product_panel;
    private static Supplier_Panel supplier_panel;
    private static CustomerForm customerForm;
    private static Purchase_Panel purchase_panel;
    private static Payment payment_panel;
    private static setPayment payment;
    private static Login login;
    private static String current="home";
    private static Deque<String> pagestack=new ArrayDeque<>();

    public Nav(){
        super("GUI.Home");

        contentPanel.setLayout(cardLayout);

        home=new Home();
        sales_panel=new Sales_Panel();
        addForm=new AddForm();
        viewSales=new ViewSales();
        product_panel=new Product_Panel();
        manageUsers=new ManageUsers();
        supplier_panel=new Supplier_Panel();
        customerForm=new CustomerForm();
        login=new Login();
        purchase_panel=new Purchase_Panel();
        payment_panel=new Payment();
        payment=new setPayment();

        contentPanel.add(home,"home");
        contentPanel.add(sales_panel,"Sales");
        contentPanel.add(addForm,"addform");
        contentPanel.add(viewSales,"viewsales");
        contentPanel.add(product_panel,"product");
        contentPanel.add(manageUsers,"manage");
        contentPanel.add(supplier_panel,"supplier");
        contentPanel.add(customerForm,"customer");
        contentPanel.add(login,"login");
        contentPanel.add(purchase_panel,"purchases");
        contentPanel.add(payment_panel,"payment");
        contentPanel.add(payment,"invoice");

        this.setContentPane(contentPanel);

        cardLayout.show(contentPanel,"login");
    }

    public static void changePanel(Enum Page){
        refresh();
        if (Page.toString()=="login"){
            login.fCancel();
        }

        pagestack.push(current);
        current=Page.toString();
        cardLayout.show(contentPanel,current);
    }

    public static void back(){
        if (!pagestack.isEmpty()){
            refresh();

            current=pagestack.pop();
            cardLayout.show(contentPanel,current);
        }
    }

    public static void refresh(){
        viewSales.setData();
        sales_panel.refresh();
        viewSales.refresh();
        addForm.refresh();
        product_panel.refresh();
        manageUsers.refresh();
        supplier_panel.refresh();
        customerForm.refresh();
        purchase_panel.refresh();
        payment_panel.refresh();
        payment.refresh();

        Status.setChangeInSales(false);
        Status.setChangeInProduct(false);
        Status.setChangeInCustomer(false);
        Status.setChangeInSupplier(false);
    }
}