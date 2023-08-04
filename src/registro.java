import javax.swing.*;

public class registro {
    private JPanel rootPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton BUSCARPORCODIGOButton;
    private JButton BUSCARPORNOMBREButton;
    private JButton BUSCARPORSIGNOButton;
    private JButton BORRARELREGISTROButton;
    private JButton ACTUALIZARREGISTROButton;
    private JButton INGRESARREGISTROButton;
    private JButton LIMPIARFORMULARIOButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("registro");
        frame.setContentPane(new registro().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

