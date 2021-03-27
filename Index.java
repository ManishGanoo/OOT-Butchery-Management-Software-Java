package GUI; /**
 * Created by erosennin on 2/19/17.
 */
import javax.swing.*;
import java.awt.*;

public class Index {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }

    public static void create(){

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }


        Nav frame=new Nav();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(ScreenSize);
        frame.setVisible(true);
        frame.pack();
    }
}