import javax.swing.JFrame;

class Main {
    JFrame window = new JFrame("ABCBlocks");
    Main() {
        window.setBounds(100,100,401,301);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new Canvas());
        window.setUndecorated(true);
        window.setVisible(true);   
    }
    public static void main(String[] args) {
        new Main();
    }
}

