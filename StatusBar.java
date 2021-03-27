package BackEnd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatusBar extends JPanel{
    private static JLabel statusBar=new JLabel("status");

    public StatusBar(){
        Dimension size=getSize();
        setBackground(new Color(6, 91, 93));
        setPreferredSize(new Dimension((int)size.getWidth(),50));
        statusBar.setForeground(Color.black);
        statusBar.setBackground(Color.CYAN);


        add(statusBar);
    }
}
