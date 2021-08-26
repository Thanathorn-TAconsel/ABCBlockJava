import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

class Main {
    JFrame window = new JFrame("ABCBlocks");
    Canvas canvas = new Canvas();
    char BoardArray[][] = {{'A','B','C','D'},{'E','F','G','H'},{'I','J','K',' '}};
    Graphics2D g2d;
    Point selectedBlock;
    Point mouseClickLocation;
    Point nextBlock;
    int gameversion = 3;
    int movecount = 0;
    long playtime = 0,starttime = System.currentTimeMillis();
    Main() {
        window.setBounds(100,100,401,321);
        window.setResizable(false);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBounds(0,0,400,300);
        window.add(canvas);
        window.setVisible(true);   

        window.getContentPane().setBackground(Color.white);
        g2d = (Graphics2D) canvas.getGraphics();
        
        
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
                    BoardArray[selectedBlock.y][selectedBlock.x] = ' ';
                    movecount++;
                }
                updateMap();
                saveGame();
                if (checkwin()) {
                    System.out.println("You Win");
                    randommap();
                    saveGame();
                    JOptionPane.showMessageDialog(null, "You Win " + (playtime + millis())/1000 + " seconds " + movecount + " Move");
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
        if (!loadGame()) {
            randommap();
        }
        updateMap();
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
        }
    }
    private char CheckAvilableMove(int y,int x) { 
        if(x-1 >= 0) {
            if (BoardArray[y][x-1] == ' ')
            return 'L';
        }
        if(x+1 <= 3) {
            if (BoardArray[y][x+1] == ' ')
            return 'R';
        }
        if(y-1 >= 0) {
            if (BoardArray[y-1][x] == ' ')
            return 'U';
        }
        if(y+1 <= 2) {
            if (BoardArray[y+1][x] == ' ')
            return 'D';
        }
        return '0';   
        
    }
    private boolean checkwin() {
        char BoardarrayWin[][] = { {'A','B','C','D'},{'E','F','G','H'},{'I','J','K',' '}};
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
                
                    g2d.drawString(BoardArray[y][x] + "",35 + (x*100), 65 + (y*100));
                
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
        BoardArray[2][3] = ' ';
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
    private long millis() {
        return System.currentTimeMillis() - starttime;
    }
    private void saveGame() {
        try {
            String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ABCBlockMAP>\n  <information>\n    <GameVersion>" + gameversion + "</GameVersion>\n    <PlayTime>" + (playtime + millis()) +"</PlayTime>\n    <MoveCount>" + movecount + "</MoveCount>\n  </information>\n  <Map>\n    <Row1>" + BoardArray[0][0] + BoardArray[0][1] + BoardArray[0][2] + BoardArray[0][3] + "</Row1>\n    <Row2>" +  BoardArray[1][0] + BoardArray[1][1] + BoardArray[1][2] + BoardArray[1][3] + "</Row2>\n    <Row3>"+  BoardArray[2][0] + BoardArray[2][1] + BoardArray[2][2] + BoardArray[2][3] + "</Row3>\n  </Map>\n</ABCBlockMAP>";
            FileOutputStream fout = new FileOutputStream("gamesave.xml");
            fout.write(data.getBytes(StandardCharsets.UTF_8));
            fout.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
    }
    private boolean loadGame() {
        try {
            File inputFile = new File("gamesave.xml");
            String alline = "";
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                   alline += line;
                }
            }
            this.gameversion = Integer.parseInt(alline.substring(alline.indexOf("<GameVersion>") + 13,alline.indexOf("</GameVersion>")));
            this.playtime = Integer.parseInt(alline.substring(alline.indexOf("<PlayTime>") + 10,alline.indexOf("</PlayTime>")));
            this.movecount = Integer.parseInt(alline.substring(alline.indexOf("<MoveCount>") + 11,alline.indexOf("</MoveCount>")));
            String row = alline.substring(alline.indexOf("<Row1>") + 6,alline.indexOf("</Row1")) + alline.substring(alline.indexOf("<Row2>") + 6,alline.indexOf("</Row2")) + alline.substring(alline.indexOf("<Row3>") + 6,alline.indexOf("</Row3"));
            System.out.println(playtime + ", " + movecount);
            System.out.println(row);
            int rl = 0;
            for (int y = 0;y < 3;y++) {
                for (int x = 0;x < 4;x++) {
                    BoardArray[y][x] = row.charAt(rl);
                    rl++;
                }
            }
            /*
            NodeList nList = ;
            
            System.out.println("----------------------------");
            System.out.println(nList.getLength());
            System.out.println(nList.item(0));
            */
            return true;
         } catch (Exception e) {
            e.printStackTrace();
         }
         return false;
    }
    public static void main(String[] args) {
        new Main();
    }
}
