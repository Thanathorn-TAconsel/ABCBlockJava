import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

class Main {
    JFrame window = new JFrame("ABCBlocks");
    Canvas canvas = new Canvas();
    char BoardArray[][] = {{'A','B','C','D'},{'E','F','G','H'},{'I','J','K','N'}};
    Graphics2D g2d;
    Main() {
        window.setBounds(100,100,401,301);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBounds(0,0,400,300);
        window.add(canvas);
        window.setUndecorated(true);
        window.setVisible(true);   
        g2d = (Graphics2D) canvas.getGraphics();
        g2d.setStroke(new BasicStroke(2));
        g2d.setFont(new Font ( "TimesRoman", Font.PLAIN, 48 ));
        g2d.setBackground(Color.white);
        for(int y=0; y<3; y++) {
            for(int x=0; x<4; x++ ) {
                g2d.drawRect(x*100, y*100, 100, 100);
                g2d.drawString(BoardArray[y][x] + "",35 + (x*100), 65 + (y*100));
            }
        }
        window.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point pr = mousePostitionToArray(e.getX(), e.getY());
                g2d.setColor(Color.gray);
                g2d.fillRect(pr.x * 100, pr.y * 100, 100, 100);
                System.out.println(pr.toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
    }
    private Point mousePostitionToArray(int mousex,int mousey) {
        return new Point(mousex/100,mousey/100);
    }
    public static void main(String[] args) {
        new Main();
    }
}
