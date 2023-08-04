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

        BORRARELREGISTROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = ingresocodigo.getText();

                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    String deleteQuery = "DELETE FROM REGISTROS WHERE Codigo=" + codigo;

                    Statement statement = connection.createStatement();
                    int rowsDeleted = statement.executeUpdate(deleteQuery);

                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(rootPanel, "Registro eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        ingresocedula.setText("");
                        ingresonombre.setText("");
                        ingresofecha.setText("");
                        ingresosigno.setSelectedItem(null);
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "No se encontró ningún registro con el código ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
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
        LIMPIARFORMULARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        BUSCARPORCODIGOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(QUERY + " WHERE Codigo=" + ingresocodigo.getText());

                    if (resultSet.next()) {
                        ingresocedula.setText(resultSet.getString("cedula"));
                        ingresonombre.setText(resultSet.getString("nombre"));
                        ingresofecha.setText(resultSet.getString("nacimiento"));

                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "No se encontraron resultados para el código ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                        ingresocedula.setText("");
                        ingresonombre.setText("");
                        ingresofecha.setText("");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        BUSCARPORNOMBREButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(QUERY + " WHERE Nombre='" + ingresonombre.getText() + "'");

                    if (resultSet.next()) {
                        ingresocodigo.setText(resultSet.getString("codigo"));
                        ingresocedula.setText(resultSet.getString("cedula"));
                        ingresofecha.setText(resultSet.getString("nacimiento"));
                        ingresosigno.setSelectedItem(resultSet.getString("signo"));

                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "No se encontraron resultados para el nombre ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                        ingresocedula.setText("");
                        ingresonombre.setText("");
                        ingresofecha.setText("");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        BUSCARPORSIGNOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(QUERY + " WHERE Signo='" + ingresosigno.getSelectedItem() + "'");

                    if (resultSet.next()) {
                        ingresocodigo.setText(resultSet.getString("codigo"));
                        ingresocedula.setText(resultSet.getString("cedula"));
                        ingresonombre.setText(resultSet.getString("nombre"));
                        ingresofecha.setText(resultSet.getString("nacimiento"));

                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "No se encontraron resultados para el signo ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                        ingresocedula.setText("");
                        ingresonombre.setText("");
                        ingresocedula.setText("");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
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
