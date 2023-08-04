import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class registro extends JFrame{
    private JPanel rootPanel;
    private JTextField ingresocodigo;
    private JTextField ingresocedula;
    private JTextField ingresonombre;
    private JTextField ingresofecha;
    private JComboBox ingresosigno;
    private JButton BUSCARPORCODIGOButton;
    private JButton BUSCARPORNOMBREButton;
    private JButton BUSCARPORSIGNOButton;
    private JButton BORRARELREGISTROButton;
    private JButton ACTUALIZARREGISTROButton;
    private JButton INGRESARREGISTROButton;
    private JButton LIMPIARFORMULARIOButton;
    private JTextField textoPanel;

    static final String DB_URL="jdbc:mysql://localhost/REGISTRO";
    static final String USER="root";
    static final String PASS="root_bas3";
    static final String QUERY= "SELECT * FROM ESTUDIANTES";

    public registro() {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while (rs.next()) {
                System.out.println("CODIGO: " + rs.getInt("CODIGO"));
                System.out.println("CEDULA: " + rs.getString("CEDULA"));
                System.out.println("NOMBRE: " + rs.getString("NOMBRE"));
                System.out.println("FECHA: " + rs.getString("FECHA"));
                System.out.println("SIGNO: " + rs.getString("SIGNOSIGNO"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BORRARELREGISTROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textoPanel.getText());
                String query = "DELETE FROM ESTUDIANTES WHERE id = " + id;
                try (
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                ) {
                    stmt.executeUpdate(query);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        INGRESARREGISTROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(ingresocodigo.getText());
                String cedula = ingresocedula.getText();
                String nombre = ingresonombre.getText();
                String fecha = ingresofecha.getText();

                String query = "INSERT INTO ESTUDIANTES VALUES (" + id + ", '" + cedula + "', " + nombre + ", '" + fecha + "')";
                try (
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                ) {
                    stmt.executeUpdate(query);
                    System.out.println("Nuevo estudiante registrado correctamente.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ACTUALIZARREGISTROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(ingresocodigo.getText());
                String nombre = ingresocedula.getText();
                String query = "UPDATE ESTUDIANTES SET NOMBRE = '" + nombre + "' WHERE ID = '" + id + "'";
                try (
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                ) {
                    int rowsAffected = stmt.executeUpdate(query);
                    if (rowsAffected > 0) {
                        System.out.println("Nombre del estudiante actualizado correctamente.");
                    } else {
                        System.out.println("No se encontró ningún estudiante con el ID especificado.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("registro");
        frame.setContentPane(new registro().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
