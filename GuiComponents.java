package GUI;

import BackEnd.*;
import BackEnd.MenuBar;
import BackEnd.Panel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by erosennin on 3/8/17.
 */
public class GuiComponents extends JPanel implements Panel{
    protected static MenuBar menuBar;
    protected DefaultComboBoxModel sname=new DefaultComboBoxModel();
    protected DefaultComboBoxModel cname=new DefaultComboBoxModel();
    protected DefaultComboBoxModel pname=new DefaultComboBoxModel();
    protected FillCBcustomer fillCBcustomer=new FillCBcustomer();
    protected FillCBProduct fillCBProduct=new FillCBProduct();
    protected FillCBsupplier fillCBSupplier=new FillCBsupplier();
    protected GridBagConstraints constraints=new GridBagConstraints();
    protected GridBagLayout layout=new GridBagLayout();
    protected ArrayList<Customer> customers=new ArrayList<>();
    protected ArrayList<Product> products=new ArrayList<>();
    protected ArrayList<Supplier> suppliers=new ArrayList<>();
    protected Dimension size=new Dimension(150,30);


    public GuiComponents(){
        menuBar= new MenuBar();
        setLayout(new BorderLayout());
        add(menuBar,BorderLayout.NORTH);

        setCustomer();
        setProduct();
        setSupplier();
    }

    public void setColor(JComponent panel){
        for(Component control : panel.getComponents())
        {
            if(control instanceof JButton)
            {
                JButton ctrl = (JButton) control;
                ctrl.setForeground(new Color(0x94000F));
            }
            else if (control instanceof JLabel){
                JLabel lbl=(JLabel) control;
                lbl.setForeground(new Color(150, 0, 61));
            }
            else if (control instanceof JTextField){
                JTextField txt=(JTextField) control;
                txt.setForeground(new Color(254, 58, 196));
                txt.setFont(new Font("Tahoma",Font.ITALIC,14));
            }
            else if (control instanceof JCheckBox){
                JCheckBox cb=(JCheckBox) control;
                cb.setBackground(new Color(100,10,50));
                cb.setForeground(new Color(150, 0, 61));
                cb.setFont(new Font("Tahoma",Font.BOLD,16));
            }
            else if (control instanceof JRadioButton){
                JRadioButton rb=(JRadioButton) control;
                rb.setBackground(new Color(100,10,50));
                rb.setForeground(new Color(150, 0, 61));
                rb.setFont(new Font("Tahoma",Font.BOLD,16));
            }
        }
    }

    public void setTableUI(JTable table){
        table.setBackground(Color.BLACK);
        table.setBorder(new BevelBorder(BevelBorder.LOWERED));
        table.setFont(new Font("Ariel Black",Font.BOLD,14));
        table.setForeground(new Color(0xAFAFAF));
        table.getTableHeader().setForeground(new Color(0, 0, 0));
        table.getTableHeader().setBackground(Color.RED);
        table.getTableHeader().setFont(new Font("Ariel Black",Font.BOLD,16));
    }

    @Override
    public void setFont(JComponent component){
        Font font=new Font("Tahoma",Font.BOLD,16);
        component.setFont(font);
        component.setForeground(Color.BLACK);
    }

    @Override
    public void setComponent(JComponent component, int x, int y) {}

    @Override
    public void setComponent(JComponent component, int x, int y, Dimension size) {}

    @Override
    public void refresh(){}

    public void setCustomer(){
        customers.clear();
        cname.removeAllElements();
        fillCBcustomer.Query("SELECT CustID,Name FROM tblCustomer");
        customers=fillCBcustomer.getCustomers();
        for (Customer c:customers){
            cname.addElement(c.getName());
        }
    }

    public String getCustomerName(int Cid){
        String name="";
        for (Customer x:customers){
            if (x.getCid()==Cid){
                name=x.getName();
            }
        }
        return name;
    }

    public void setProduct(){
        products.clear();
        pname.removeAllElements();
        fillCBProduct.Query("SELECT * FROM tblProduct");
        products=fillCBProduct.getProducts();
        for (Product y :products){
            pname.addElement(y.getName());
        }
    }

    public void resetCombo(JComboBox comboBox,DefaultComboBoxModel model){
        comboBox.removeAllItems();
        setProduct();
        setCustomer();
        comboBox.setModel(model);
        comboBox.setSelectedIndex(-1);
    }

    public String getProductName(int Pid){
        String name="";
        for (Product x:products){
            if (x.getProdID()==Pid){
                name=x.getName();
            }
        }
        return name;
    }

    public String getProductPrice(int Pid){
        double price=0;
        for (Product x:products){
            if (x.getProdID()==Pid){
                price=x.getSp();
            }
        }
        return String.valueOf(price);
    }

    public void setSupplier(){
        suppliers.clear();
        sname.removeAllElements();
        fillCBSupplier.Query("SELECT SupplierID,Name FROM tblSupplier");
        suppliers=fillCBSupplier.getSuppliers();
        for (Supplier s:suppliers){
            sname.addElement(s.getName());
        }
    }

    public String getSupplierName(int Sid){
        String name="";
        for (Supplier x:suppliers){
            if (x.getSid()==Sid){
                name=x.getName();
            }
        }
        return name;
    }
}
