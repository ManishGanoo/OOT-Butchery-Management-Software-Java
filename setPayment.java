package GUI;
import BackEnd.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;


/**
 * Created by Rawshirao on 13/03/2017.
 */
public class setPayment extends GuiComponents{

    private JPanel frame = new JPanel();
    private JLabel main = new JLabel();
    private JPanel panelbtn = new JPanel();
    private JCheckBox checkb;
    private JButton confirm = new JButton("Confirm");
    private JButton cancel = new JButton("Cancel");
    private JLabel head = new JLabel("Checking Invoice");
    private static ArrayList invoice =new ArrayList<Integer>();
    private GridBagConstraints constraints=new GridBagConstraints();
    private Dimension size = new Dimension(150,30);
    private static int selectedID;
    private GridBagLayout layout = new GridBagLayout();

    public setPayment() {
        remove(menuBar);
        revalidate();
        repaint();

        frame.setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setPreferredSize(screenSize);
        main.setPreferredSize(new Dimension((screenSize.width),(int)(screenSize.height*0.25)));


        Icon icon=new ImageIcon(getClass().getResource("b2"));
        main.setIcon(icon);
        main.setLayout(layout);

        constraints.insets=new Insets(10,0,0,400);

        setData();

        head.setFont(new Font("Helvetica", Font.BOLD, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100,0,0,0);
        main.add(head,gbc);


        for (int i=0; i<invoice.size(); i++){

            checkb = new JCheckBox(String.valueOf(invoice.get(i)));

            setComponent(checkb,0,i+1);

        }

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char ok = 'y';

                int ans= PopUp.ConfirmMessage(new JFrame(),"Continue? ","Confirm payment",JOptionPane.YES_NO_OPTION);
                for(Component control : main.getComponents()) {
                    if (control instanceof JCheckBox) {
                        JCheckBox ctrl = (JCheckBox) control;
                        if (ctrl.isSelected()){
                            if (ans==JOptionPane.YES_OPTION){
                                String sql="UPDATE tblSales\n" +
                                        "SET Paid='"+ok+"'\n" +
                                        "WHERE Invoice_No="+ctrl.getText();

                                dbManipulation.Insert(sql);
                                Status.setChangeInSales(true);
                            }
                        }
                    }
                }

                if (ans==JOptionPane.YES_OPTION){
                    Nav.changePanel(Page.PAYMENT);
                }

                main.revalidate();
                main.repaint();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelbtn.add(confirm);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.revalidate();
                main.repaint();
                Nav.changePanel(Page.PAYMENT);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelbtn.add(cancel);

        frame.add(main,BorderLayout.CENTER);
        frame.add(panelbtn,BorderLayout.SOUTH);
        setColor(frame);
        setColor(panelbtn);
        setColor(main);
        add(frame);

    }

    public static void setSelectedID(int value){
        selectedID=value;
        setData();
    }

    public static void setData(){

        invoice=dbManipulation.getList(selectedID);

    }

    public void refresh(){
        for (int i=0; i<invoice.size(); i++){

            checkb = new JCheckBox(String.valueOf(invoice.get(i)));

            setComponent(checkb,0,i+1);

        }
        main.revalidate();
        main.repaint();
    }


    public void setComponent(JComponent component,int x,int y){
        constraints.gridx=x;
        constraints.gridy=y;
        layout.setConstraints(component,constraints);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
        main.add(component);
    }
}