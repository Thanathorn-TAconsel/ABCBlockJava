import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

class Main {
    JFrame window = new JFrame("ABCBlocks");
    Canvas canvas = new Canvas();
    char BoardArray[][] = {{'A','B','C','D'},{'E','F','G','H'},{'I','J','K','N'}};
    Graphics2D g2d;
    Point selectedBlock;
    Point mouseClickLocation;
    Point nextBlock;
    Main() {
        window.setBounds(100,100,401,301);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBounds(0,0,400,300);
        window.add(canvas);
        window.setUndecorated(true);
        window.setVisible(true);   
        window.getContentPane().setBackground(Color.white);
        g2d = (Graphics2D) canvas.getGraphics();
        //randommap();
        updateMap();
        
        window.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedBlock = mousePostitionToArray(e.getX(), e.getY());
                mouseClickLocation = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (nextBlock != null) {
                    BoardArray[nextBlock.y][nextBlock.x] = BoardArray[selectedBlock.y][selectedBlock.x];
                    BoardArray[selectedBlock.y][selectedBlock.x] = 'N';
                }
                updateMap();
                if (checkwin()) {
                    System.out.println("You Win");
                    System.exit(0);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });
        window.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moveBlock(selectedBlock.y,selectedBlock.x, e.getX() - mouseClickLocation.x ,e.getY() - mouseClickLocation.y);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }
    private void moveBlock(int y,int x,int offsetX,int offsetY) {
        switch (CheckAvilableMove(y, x)) {
            case 'L':
            offsetY = 0;
            if (offsetX > 0) offsetX = 0;
            nextBlock = new Point(x-1,y);
            break;
            case 'R':
            offsetY = 0;
            if (offsetX < 0) offsetX = 0;
            nextBlock = new Point(x+1,y);
            break;
            case 'U':
            offsetX = 0;
            if (offsetY > 0) offsetY = 0;
            nextBlock = new Point(x,y-1);
            break;
            case 'D':
            offsetX = 0;
            if (offsetY < 0) offsetY = 0;
            nextBlock = new Point(x,y+1);
            break;
            default:
                offsetX = 0;
                offsetY = 0;
                nextBlock = null;
            break;
        }
        
        g2d.setColor(Color.white);
        g2d.fillRect(x*100, y*100, 100, 100);
        if (nextBlock != null)g2d.fillRect(nextBlock.x*100, nextBlock.y*100, 100, 100);
        g2d.setColor(Color.black);
        g2d.drawRect(x*100, y*100, 100, 100);
        if (nextBlock != null) g2d.drawRect(nextBlock.x*100, nextBlock.y*100, 100, 100);
        g2d.drawString(BoardArray[y][x] + "", (35 + (x*100)) + offsetX, (65 + (y*100)) + offsetY);
        if (Math.abs(offsetX) < 50 && Math.abs(offsetY) < 50) {
            nextBlock = null;
            System.out.println("NOT SET");
        }
    }
    private char CheckAvilableMove(int y,int x) { 
        System.out.println(y + "," + x);
        if(x-1 >= 0) {
            if (BoardArray[y][x-1] == 'N')
            return 'L';
        }
        if(x+1 <= 3) {
            if (BoardArray[y][x+1] == 'N')
            return 'R';
        }
        if(y-1 >= 0) {
            if (BoardArray[y-1][x] == 'N')
            return 'U';
        }
        if(y+1 <= 2) {
            if (BoardArray[y+1][x] == 'N')
            return 'D';
        }
        return '0';   
        
    }
    private boolean checkwin() {
        char BoardarrayWin[][] = { {'A','B','C','D'},{'E','F','G','H'},{'I','J','K','N'}};
        for (int y =0;y < 3;y++) {
            for (int x =0;x < 4;x++) {
                if (BoardArray[y][x] != BoardarrayWin[y][x])return false;
            }
        }
        return true;
    }
    private void updateMap() {
        g2d.setStroke(new BasicStroke(2));
        g2d.setFont(new Font ("TimesRoman", Font.PLAIN, 48 ));
        //g2d.setBackground(Color.white);
        
        for(int y=0; y<3; y++) {
            for(int x=0; x<4; x++ ) {
                g2d.setColor(Color.white);
                g2d.fillRect(x*100, y*100, 100, 100);
                g2d.setColor(Color.black);
                g2d.drawRect(x*100, y*100, 100, 100);
                if (BoardArray[y][x] == 'N') {
                    g2d.drawString("",35 + (x*100), 65 + (y*100));
                }else {
                    g2d.drawString(BoardArray[y][x] + "",35 + (x*100), 65 + (y*100));
                }
            }
        }
    }
    
    private Point mousePostitionToArray(int mousex,int mousey) {
        return new Point(mousex/100,mousey/100);
    }
    private void randommap(){   
        char abc[] = {'A','B','C','D','E','F','G','H','I','J','K'};
    
        for (int y = 0;y < 3;y++) {
            for (int x = 0;x < 4;x++) {
                if (abc.length > 0) {
                    int indextoremove = (int) (Math.random() * abc.length);
                    BoardArray[y][x] = abc[indextoremove];
                    abc = removeArray(abc,indextoremove);
                }
            }
        }
        BoardArray[2][3] = 'N';
    }
    private char[] removeArray(char[] inputarray,int indextoremove) {
        if (inputarray.length > 0) {
            char[] outputarray = new char[inputarray.length - 1];
            int index = 0;
            for (int i = 0;i < inputarray.length;i++) {
                if (i != indextoremove) {
                    outputarray[index] = inputarray[i];
                    index++;
                }
            }
            return outputarray;
        }
        return new char[0];
    }
    private void saveGame() {
        
    }
    public static void main(String[] args) {
        new Main();
    }
}
