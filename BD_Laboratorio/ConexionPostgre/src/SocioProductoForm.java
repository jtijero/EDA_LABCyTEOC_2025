import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import org.jdatepicker.impl.*;
import java.text.SimpleDateFormat;

public class SocioProductoForm extends JFrame {
    JComboBox<String> cbSocio = new JComboBox<>();
    JComboBox<String> cbProducto = new JComboBox<>();
    JTextField tfCuenta = new JTextField(20);
    JDatePickerImpl dateApertura = crearDatePicker();
    JTextField tfSaldo = new JTextField("0.00", 10);
    JTextField tfEstado = new JTextField("A", 1);

    Map<String, Integer> socios = new LinkedHashMap<>();
    Map<String, Integer> productos = new LinkedHashMap<>();

    JTable tabla;
    DefaultTableModel modelo;
    int flagActualizar = 0;
    String operacion = "";
    int idSeleccionado = -1;

    JButton btnAdicionar = new JButton("Adicionar");
    JButton btnModificar = new JButton("Modificar");
    JButton btnEliminar = new JButton("Eliminar");
    JButton btnInactivar = new JButton("Inactivar");
    JButton btnReactivar = new JButton("Reactivar");
    JButton btnActualizar = new JButton("Actualizar");
    JButton btnCancelar = new JButton("Cancelar");
    JButton btnVolver = new JButton("Volver");

    public SocioProductoForm() {
        setTitle("Socio-Producto");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfEstado.setEditable(false);

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de Asociación"));
        panelDatos.add(new JLabel("Socio:"));
        panelDatos.add(cbSocio);
        panelDatos.add(new JLabel("Producto:"));
        panelDatos.add(cbProducto);
        panelDatos.add(new JLabel("Número de Cuenta:"));
        panelDatos.add(tfCuenta);
        panelDatos.add(new JLabel("Fecha de Apertura:"));
        panelDatos.add(dateApertura);
        panelDatos.add(new JLabel("Saldo Inicial:"));
        panelDatos.add(tfSaldo);
        panelDatos.add(new JLabel("Estado:"));
        panelDatos.add(tfEstado);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVolver);

        modelo = new DefaultTableModel(new String[]{
            "ID", "Socio", "Producto", "Cuenta", "Fecha", "Saldo", "Estado", "Fecha Modificación"
        }, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Relaciones Registradas"));

        add(panelDatos, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarCombos();
        listarDatos();

        tfEstado.setEditable(false);

        btnAdicionar.addActionListener(e -> iniciarOperacion("adicionar"));
        btnModificar.addActionListener(e -> iniciarOperacion("modificar"));
        btnEliminar.addActionListener(e -> iniciarOperacion("eliminar"));
        btnInactivar.addActionListener(e -> iniciarOperacion("inactivar"));
        btnReactivar.addActionListener(e -> iniciarOperacion("reactivar"));
        btnActualizar.addActionListener(e -> actualizar());
        btnCancelar.addActionListener(e -> cancelar());
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuTransacciones();
        });

        setVisible(true);
    }

    void cargarCombos() {
        socios.clear();
        productos.clear();
        cbSocio.removeAllItems();
        cbProducto.removeAllItems();
        try (Connection conn = ConexionDB.conectar()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT soc_cod, soc_nom FROM socio WHERE soc_estado='A'");
            while (rs.next()) {
                socios.put(rs.getString(2), rs.getInt(1));
                cbSocio.addItem(rs.getString(2));
            }
            rs = conn.createStatement().executeQuery("SELECT pro_cod, pro_iden FROM producto WHERE pro_estado='A'");
            while (rs.next()) {
                productos.put(rs.getString(2), rs.getInt(1));
                cbProducto.addItem(rs.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando combos: " + e.getMessage());
        }
    }

    void listarDatos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT sp.id_socpro, s.soc_nom, p.pro_iden, sp.num_cuenta, sp.fec_apertura, " +
                        "sp.saldo, sp.socpro_estado, sp.fecha_modificacion " +
                        "FROM socioproducto sp " +
                        "JOIN socio s ON sp.id_socio=s.soc_cod " +
                        "JOIN producto p ON sp.id_producto=p.pro_cod";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4),  
                    rs.getDate(5),   
                    rs.getBigDecimal(6),
                    rs.getString(7),  
                    rs.getTimestamp(8) 
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    void iniciarOperacion(String op) {
        int fila = tabla.getSelectedRow();
        if (!op.equals("adicionar") && fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }
        operacion = op;
        flagActualizar = 1;

        if (!op.equals("adicionar")) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            cbSocio.setSelectedItem(modelo.getValueAt(fila, 1));
            cbProducto.setSelectedItem(modelo.getValueAt(fila, 2));
            tfCuenta.setText(modelo.getValueAt(fila, 3).toString());
            ((UtilDateModel) dateApertura.getModel()).setValue((java.util.Date) modelo.getValueAt(fila, 4));
            tfSaldo.setText(modelo.getValueAt(fila, 5).toString());
            tfEstado.setText(modelo.getValueAt(fila, 6).toString());

            cbSocio.setEnabled(false);
            cbProducto.setEnabled(false);
            tfCuenta.setEditable(false);
            dateApertura.setEnabled(false);
            tfEstado.setEditable(false);
            tfSaldo.setEditable(true);
        } else {
            limpiarCampos();
            cbSocio.setEnabled(true);
            cbProducto.setEnabled(true);
            tfCuenta.setEditable(true);
            dateApertura.setEnabled(true);
            tfSaldo.setEditable(true);
        }

        bloquearBotonesExcepto(btnActualizar, btnCancelar, btnVolver);
    }

    void actualizar() {
        System.out.println(">>> Operación actual: " + operacion);

        try (Connection conn = ConexionDB.conectar()) {
            String sql = switch (operacion) {
                case "adicionar" -> "INSERT INTO socioproducto (id_socio, id_producto, num_cuenta, fec_apertura, saldo, socpro_estado) VALUES (?, ?, ?, ?, ?, 'A')";
                case "modificar" -> "UPDATE socioproducto SET id_socio=?, id_producto=?, num_cuenta=?, fec_apertura=?, saldo=? WHERE id_socpro=?";
                case "eliminar" -> "UPDATE socioproducto SET socpro_estado='*' WHERE id_socpro=?";
                case "inactivar" -> "UPDATE socioproducto SET socpro_estado='I' WHERE id_socpro=?";
                case "reactivar" -> "UPDATE socioproducto SET socpro_estado='A' WHERE id_socpro=?";
                default -> null;
            };
            if (sql == null) return;

            PreparedStatement ps = conn.prepareStatement(sql);
            if (operacion.equals("adicionar") || operacion.equals("modificar")) {
                ps.setInt(1, socios.get((String) cbSocio.getSelectedItem()));
                ps.setInt(2, productos.get((String) cbProducto.getSelectedItem()));
                ps.setString(3, tfCuenta.getText());
                ps.setDate(4, new java.sql.Date(((java.util.Date) dateApertura.getModel().getValue()).getTime()));
                ps.setBigDecimal(5, new java.math.BigDecimal(tfSaldo.getText()));
                if (operacion.equals("modificar")) ps.setInt(6, idSeleccionado);
            } else {
                ps.setInt(1, idSeleccionado);
            }
            ps.executeUpdate();
            listarDatos();
            cancelar();
            JOptionPane.showMessageDialog(this, "✅ Operación exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    void cancelar() {
        limpiarCampos();
        operacion = "";
        flagActualizar = 0;
        idSeleccionado = -1;
        desbloquearTodosLosBotones();
    }

    void limpiarCampos() {
        cbSocio.setSelectedIndex(0);
        cbProducto.setSelectedIndex(0);
        tfCuenta.setText("");
        ((UtilDateModel) dateApertura.getModel()).setValue(new java.util.Date());
        tfSaldo.setText("0.00");
        tfEstado.setText("A");
    }

    JDatePickerImpl crearDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(panel, new DateComponentFormatter());
    }

    void bloquearBotonesExcepto(JButton... permitidos) {
        JButton[] todos = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnVolver};
        for (JButton b : todos) b.setEnabled(false);
        for (JButton b : permitidos) b.setEnabled(true);
    }

    void desbloquearTodosLosBotones() {
        JButton[] todos = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnVolver};
        for (JButton b : todos) b.setEnabled(true);
    }
}
