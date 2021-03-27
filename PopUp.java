package BackEnd;

import javax.swing.*;
import java.awt.*;

/**
 * Created by erosennin on 3/3/17.
 */
public abstract class PopUp {
    public static void DisplayMessage(Component component, String msg, String title, int optionType){
        JOptionPane.showMessageDialog(component,msg,title,optionType);
    }
    public static int ConfirmMessage(Component component, String msg, String title, int optionType){
        int ans;
        ans=JOptionPane.showConfirmDialog(component,msg,title,optionType);
        return ans;
    }
}
