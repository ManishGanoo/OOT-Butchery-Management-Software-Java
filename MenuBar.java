package BackEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import GUI.*;

public class MenuBar extends JMenuBar{
    private JMenuBar bar;
    private JMenu User;
    private JMenu menu;

    private JButton logout;
    private JButton manage;

    private JButton importdb;
    private JButton export;
    private JButton B_home;
    private JButton back;
    private JButton exit;

    public MenuBar() {
        setVisible(true);
        bar = new JMenuBar();
        bar.setBackground(Color.darkGray);
        bar.addNotify();

        ImageIcon account = new ImageIcon(getClass().getResource("acc.png"));
        User = new JMenu("Account");
        User.setIcon(account);


        manage = new JButton("Manage  ");
        logout = new JButton("Log Out   ");
        User.add(manage);
        User.add(logout);

        menu=new JMenu("MENU");

        B_home =new JButton("HOME ");
        back= new JButton("BACK  ");
        exit=new JButton("EXIT    ");

        menu.add(exit);


        bar.add(B_home);
        bar.add(back);
        bar.add(menu);
        bar.add(Box.createHorizontalGlue());
        bar.add(User);
        add(bar);

        Handler handler=new Handler();
        B_home.addActionListener(handler);
        back.addActionListener(handler);
        exit.addActionListener(handler);
        manage.addActionListener(handler);
        logout.addActionListener(handler);
    }

    private class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String location=e.getActionCommand();
            switch (location){
                case "HOME ":{
                    Nav.changePanel(Page.HOME);
                }
                break;
                case "BACK  ":{
                    Nav.back();
                }
                break;
                case "EXIT    ":{
                    System.exit(0);
                }
                break;
                case "Manage  ":{
                    Nav.changePanel(Page.MANAGE);
                }
                break;
                case "Log Out   ":{
                    Nav.changePanel(Page.LOGIN);
                }break;
            }
        }
    }
}