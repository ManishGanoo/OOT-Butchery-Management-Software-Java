package BackEnd;

import javax.swing.*;
import java.awt.*;

/**
 * Created by erosennin on 3/5/17.
 */
public interface Panel{
    void setFont(JComponent component);

    void setComponent(JComponent component,int x,int y);

    void setComponent(JComponent component,int x,int y,Dimension size);

    void refresh();
}
