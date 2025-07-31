import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProcedimientosPanel extends JFrame {
    private JTextField txtDniSocio;
    private JButton btnReporteSocio, btnResumenGeneral, btnListadoSocios, btnVolver;
    private JTable tablaResultados;
    private Connection conexion;

    public ProcedimientosPanel() {
        setTitle("Procedimientos Almacenados - Cooperativa");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            conexion = ConexionDB.conectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
            System.exit(1);
        }

        initUI();
    }

    private void initUI() {
        txtDniSocio = new JTextField(15);
        txtDniSocio.setToolTipText("Ingrese el DNI del socio");

        btnReporteSocio = new JButton("Reporte por Socio");
        btnResumenGeneral = new JButton("Resumen General");
        btnListadoSocios = new JButton("Listado de Socios");
        btnVolver = new JButton("Volver");

        btnReporteSocio.addActionListener(this::ejecutarReportePorSocio);
        btnResumenGeneral.addActionListener(e -> ejecutarYMostrar("SELECT * FROM fn_resumen_general_productos()"));
        btnListadoSocios.addActionListener(e -> ejecutarYMostrar("SELECT * FROM fn_listado_socios_detalle()"));
        btnVolver.addActionListener(e -> {
            dispose();
            new Menu();
        });

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(btnReporteSocio);
        panelSuperior.add(new JLabel("DNI Socio:"));
        panelSuperior.add(txtDniSocio);
        panelSuperior.add(btnResumenGeneral);
        panelSuperior.add(btnListadoSocios);
        panelSuperior.add(btnVolver);

        tablaResultados = new JTable();
        JScrollPane scroll = new JScrollPane(tablaResultados);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void ejecutarReportePorSocio(ActionEvent e) {
        String dni = txtDniSocio.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el DNI del socio.");
            return;
        }

        String sql = "SELECT * FROM fn_reporte_personalizado_socio(?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            mostrarResultadosEnTabla(rs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar reporte: " + ex.getMessage());
        }
    }

    private void ejecutarYMostrar(String sql) {
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            mostrarResultadosEnTabla(rs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar resultados: " + ex.getMessage());
        }
    }

    private void mostrarResultadosEnTabla(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();
        DefaultTableModel modelo = new DefaultTableModel();

        for (int i = 1; i <= columnas; i++) {
            modelo.addColumn(meta.getColumnLabel(i));
        }

        while (rs.next()) {
            Object[] fila = new Object[columnas];
            for (int i = 1; i <= columnas; i++) {
                fila[i - 1] = rs.getObject(i);
            }
            modelo.addRow(fila);
        }

        tablaResultados.setModel(modelo);
    }
}
