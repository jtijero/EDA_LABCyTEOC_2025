import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductoForm extends JFrame {
    JTextField tfIdentificador = new JTextField(15);
    JTextArea taDescripcion = new JTextArea(3, 15);
    JComboBox<String> cbMoneda = new JComboBox<>();
    JComboBox<String> cbTipoProducto = new JComboBox<>();
    JTextField tfEstado = new JTextField("A", 1);

    Map<String, Integer> monedas = new LinkedHashMap<>();
    Map<String, Integer> tiposProducto = new LinkedHashMap<>();

    JTable tabla;
    DefaultTableModel modelo;
    int idSeleccionado = -1;
    String operacion = "";

    JButton btnAdicionar = new JButton("Adicionar");
    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnInactivar = new JButton("Inactivar");
    JButton btnReactivar = new JButton("Reactivar");
    JButton btnActualizar = new JButton("Actualizar");
    JButton btnCancelar = new JButton("Cancelar");
    JButton btnVolver = new JButton("Volver");

    public ProductoForm() {
        setTitle("Mantenimiento de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfEstado.setEditable(false);
        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);

        JPanel panelRegistro = new JPanel(new GridLayout(6, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        panelRegistro.add(new JLabel("Identificador:"));
        panelRegistro.add(tfIdentificador);
        panelRegistro.add(new JLabel("Descripción:"));
        panelRegistro.add(new JScrollPane(taDescripcion));
        panelRegistro.add(new JLabel("Moneda:"));
        panelRegistro.add(cbMoneda);
        panelRegistro.add(new JLabel("Tipo de Producto:"));
        panelRegistro.add(cbTipoProducto);
        panelRegistro.add(new JLabel("Estado:"));
        panelRegistro.add(tfEstado);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVolver);

        modelo = new DefaultTableModel(new String[]{"ID", "Identificador", "Descripción", "Moneda", "Tipo", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelRegistro, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarMonedas();
        cargarTiposProducto();
        listarProductos();
        cancelarOperacion();

        // Eventos
        btnAdicionar.addActionListener(e -> iniciarOperacion("adicionar"));
        btnModificar.addActionListener(e -> iniciarOperacion("modificar"));
        btnEliminar.addActionListener(e -> iniciarOperacion("eliminar"));
        btnInactivar.addActionListener(e -> iniciarOperacion("inactivar"));
        btnReactivar.addActionListener(e -> iniciarOperacion("reactivar"));
        btnActualizar.addActionListener(e -> actualizar());
        btnCancelar.addActionListener(e -> cancelarOperacion());
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuTransacciones();
        });

        setVisible(true);
    }

    void cargarMonedas() {
        monedas.clear();
        cbMoneda.removeAllItems();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT mon_cod, mon_nom FROM moneda")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("mon_nom");
                int id = rs.getInt("mon_cod");
                monedas.put(nombre, id);
                cbMoneda.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando monedas: " + e.getMessage());
        }
    }

    void cargarTiposProducto() {
        tiposProducto.clear();
        cbTipoProducto.removeAllItems();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT tip_pro_cod, tip_pro_nom FROM tipoproducto")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("tip_pro_nom");
                int id = rs.getInt("tip_pro_cod");
                tiposProducto.put(nombre, id);
                cbTipoProducto.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando tipos: " + e.getMessage());
        }
    }

    void listarProductos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT p.pro_cod, p.pro_iden, p.pro_des, m.mon_nom, t.tip_pro_nom, p.pro_estado " +
                             "FROM producto p " +
                             "JOIN moneda m ON p.mon_cod = m.mon_cod " +
                             "JOIN tipoproducto t ON p.tip_pro_cod = t.tip_pro_cod")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar productos: " + e.getMessage());
        }
    }
    
    void iniciarOperacion(String op) {
        int fila = tabla.getSelectedRow();
        if (!op.equals("adicionar") && fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla");
            return;
        }

        operacion = op;
        if (!op.equals("adicionar")) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            tfIdentificador.setText((String) modelo.getValueAt(fila, 1));
            taDescripcion.setText((String) modelo.getValueAt(fila, 2));
            cbMoneda.setSelectedItem(modelo.getValueAt(fila, 3));
            cbTipoProducto.setSelectedItem(modelo.getValueAt(fila, 4));
            tfEstado.setText((String) modelo.getValueAt(fila, 5));
        } else {
            cancelarOperacion();
        }

        bloquearBotonesExcepto(btnActualizar, btnCancelar, btnVolver);
    }

    void actualizar() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = switch (operacion) {
                case "adicionar" -> "INSERT INTO producto (pro_iden, pro_des, mon_cod, tip_pro_cod, pro_estado) VALUES (?, ?, ?, ?, 'A')";
                case "modificar" -> "UPDATE producto SET pro_iden=?, pro_des=?, mon_cod=?, tip_pro_cod=? WHERE pro_cod=?";
                case "eliminar" -> "UPDATE producto SET pro_estado='*' WHERE pro_cod=?";
                case "inactivar" -> "UPDATE producto SET pro_estado='I' WHERE pro_cod=?";
                case "reactivar" -> "UPDATE producto SET pro_estado='A' WHERE pro_cod=?";
                default -> null;
            };
            if (sql == null) return;

            PreparedStatement ps = conn.prepareStatement(sql);
            if (operacion.equals("adicionar") || operacion.equals("modificar")) {
                ps.setString(1, tfIdentificador.getText());
                ps.setString(2, taDescripcion.getText());
                ps.setInt(3, monedas.get((String) cbMoneda.getSelectedItem()));
                ps.setInt(4, tiposProducto.get((String) cbTipoProducto.getSelectedItem()));
                if (operacion.equals("modificar")) {
                    ps.setInt(5, idSeleccionado);
                }
            } else {
                ps.setInt(1, idSeleccionado);
            }

            ps.executeUpdate();
            listarProductos();
            cancelarOperacion();
            JOptionPane.showMessageDialog(this, "✅ Operación realizada correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar operación: " + e.getMessage());
        }
    }

    void cancelarOperacion() {
        limpiarCampos();
        operacion = "";
        idSeleccionado = -1;
        desbloquearTodosLosBotones();
    }

    void limpiarCampos() {
        tfIdentificador.setText("");
        taDescripcion.setText("");
        cbMoneda.setSelectedIndex(0);
        cbTipoProducto.setSelectedIndex(0);
        tfEstado.setText("A");
    }

    void bloquearBotonesExcepto(JButton... permitidos) {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnVolver};
        for (JButton b : botones) b.setEnabled(false);
        for (JButton b : permitidos) b.setEnabled(true);
    }

    void desbloquearTodosLosBotones() {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnVolver};
        for (JButton b : botones) b.setEnabled(true);
    }
}
