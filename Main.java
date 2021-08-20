import javax.swing.JFrame;

class Main {
    JFrame f1 = new JFrame();
    Main() {
        f1.setBounds(100,100,100,100);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}