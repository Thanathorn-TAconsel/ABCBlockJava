import javax.swing.JFrame;

class Main {
    JFrame window = new JFrame("ABCBlocks");
    Main() {
        window.setBounds(100,100,300,300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        
    }
    public static void main(String[] args) {
        new Main();
    }
}