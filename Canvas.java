import java.awt.*;
import javax.swing.*;

public class Canvas extends JComponent {
    public void paint(Graphics g){  
        for(int y=0; y<3; y++) {
            for(int x=0; x<4; x++ ) {
                g.drawRect(x*100, y*100, 100, 100);

            }
        }
    }
}
