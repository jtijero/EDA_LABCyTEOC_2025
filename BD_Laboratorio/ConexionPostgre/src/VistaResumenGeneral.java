import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaResumenGeneral extends JFrame {
    public VistaResumenGeneral() {
        setTitle("Resumen General de Productos");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel modelo = new DefaultTableModel();
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll);

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM vista_resumen_general_productos")) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        setVisible(true);
    }
}
