import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jdatepicker.impl.*;

public class TasaForm extends JFrame {
    JComboBox<String> cbProducto = new JComboBox<>();
    Map<String, Integer> productosDisponibles = new LinkedHashMap<>();
    JTextField tfIdentificador = new JTextField(15);
    JTextField tfDescripcion = new JTextField(15);
    JTextField tfTasa = new JTextField(15);
    JTextField tfPlazoDias = new JTextField(15);
    JTextField tfEstado = new JTextField("A", 1);
    JDatePickerImpl dateInicio = crearDatePicker(true);
    JDatePickerImpl dateFin = crearDatePicker(false);

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
    JButton btnSalir = new JButton("Salir");

    public TasaForm() {
        setTitle("Mantenimiento de Tasa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfEstado.setEditable(false);

        JPanel panelRegistro = new JPanel(new GridLayout(8, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Tasa"));
        panelRegistro.add(new JLabel("Producto asociado:"));
        panelRegistro.add(cbProducto);
        panelRegistro.add(new JLabel("Identificador:"));
        panelRegistro.add(tfIdentificador);
        panelRegistro.add(new JLabel("Descripción:"));
        panelRegistro.add(tfDescripcion);
        panelRegistro.add(new JLabel("Tasa (%):"));
        panelRegistro.add(tfTasa);
        panelRegistro.add(new JLabel("Plazo en días:"));
        panelRegistro.add(tfPlazoDias);
        panelRegistro.add(new JLabel("Fecha Inicio:"));
        panelRegistro.add(dateInicio);
        panelRegistro.add(new JLabel("Fecha Fin:"));
        panelRegistro.add(dateFin);
        panelRegistro.add(new JLabel("Estado:"));
        panelRegistro.add(tfEstado);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnSalir);

        modelo = new DefaultTableModel(new String[]{"ID", "Identificador", "Descripción", "Tasa", "Plazo", "Inicio", "Fin", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Tabla Tasa"));

        add(panelRegistro, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        listarDatos();
        cargarProductosSinTasa();

        btnAdicionar.addActionListener(e -> {
            limpiarCampos();
            operacion = "adicionar";
            flagActualizar = 1;
            habilitarCamposEdicion();
            tabla.clearSelection();
            tabla.setEnabled(false);
            bloquearBotonesExcepto(btnAdicionar, btnActualizar, btnCancelar, btnSalir);
        });

        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                tfIdentificador.setText(modelo.getValueAt(fila, 1).toString());
                tfDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                tfTasa.setText(modelo.getValueAt(fila, 3).toString());
                tfPlazoDias.setText(modelo.getValueAt(fila, 4).toString());
                Date fechaIni = (Date) modelo.getValueAt(fila, 5);
                Date fechaFin = (Date) modelo.getValueAt(fila, 6);

                Calendar calInicio = Calendar.getInstance();
                calInicio.setTime(fechaIni);
                dateInicio.getModel().setDate(calInicio.get(Calendar.YEAR), calInicio.get(Calendar.MONTH), calInicio.get(Calendar.DAY_OF_MONTH));
                dateInicio.getModel().setSelected(true);

                Calendar calFin = Calendar.getInstance();
                calFin.setTime(fechaFin);
                dateFin.getModel().setDate(calFin.get(Calendar.YEAR), calFin.get(Calendar.MONTH), calFin.get(Calendar.DAY_OF_MONTH));
                dateFin.getModel().setSelected(true);
                tfEstado.setText(modelo.getValueAt(fila, 7).toString());
                habilitarCamposEdicion();
                operacion = "modificar";
                flagActualizar = 1;
                tabla.setEnabled(true);
                bloquearBotonesExcepto(btnModificar, btnActualizar, btnCancelar, btnSalir);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                tfIdentificador.setText(modelo.getValueAt(fila, 1).toString());
                tfDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                tfTasa.setText(modelo.getValueAt(fila, 3).toString());
                tfPlazoDias.setText(modelo.getValueAt(fila, 4).toString());
                dateInicio.getModel().setDate(((Date) modelo.getValueAt(fila, 5)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 5)).getMonth(), ((Date) modelo.getValueAt(fila, 5)).getDate());
                dateInicio.getModel().setSelected(true);
                dateFin.getModel().setDate(((Date) modelo.getValueAt(fila, 6)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 6)).getMonth(), ((Date) modelo.getValueAt(fila, 6)).getDate());
                dateFin.getModel().setSelected(true);
                tfEstado.setText(modelo.getValueAt(fila, 7).toString());
                deshabilitarCamposEdicion();
                operacion = "eliminar";
                flagActualizar = 1;
                tabla.setEnabled(true);
                bloquearBotonesExcepto(btnEliminar, btnActualizar, btnCancelar, btnSalir);
            }
        });

        btnInactivar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                tfIdentificador.setText(modelo.getValueAt(fila, 1).toString());
                tfDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                tfTasa.setText(modelo.getValueAt(fila, 3).toString());
                tfPlazoDias.setText(modelo.getValueAt(fila, 4).toString());
                dateInicio.getModel().setDate(((Date) modelo.getValueAt(fila, 5)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 5)).getMonth(), ((Date) modelo.getValueAt(fila, 5)).getDate());
                dateInicio.getModel().setSelected(true);
                dateFin.getModel().setDate(((Date) modelo.getValueAt(fila, 6)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 6)).getMonth(), ((Date) modelo.getValueAt(fila, 6)).getDate());
                dateFin.getModel().setSelected(true);
                tfEstado.setText(modelo.getValueAt(fila, 7).toString());
                deshabilitarCamposEdicion();
                operacion = "inactivar";
                flagActualizar = 1;
                tabla.setEnabled(true);
                bloquearBotonesExcepto(btnInactivar, btnActualizar, btnCancelar, btnSalir);
            }
        });

        btnReactivar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                tfIdentificador.setText(modelo.getValueAt(fila, 1).toString());
                tfDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                tfTasa.setText(modelo.getValueAt(fila, 3).toString());
                tfPlazoDias.setText(modelo.getValueAt(fila, 4).toString());
                dateInicio.getModel().setDate(((Date) modelo.getValueAt(fila, 5)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 5)).getMonth(), ((Date) modelo.getValueAt(fila, 5)).getDate());
                dateInicio.getModel().setSelected(true);
                dateFin.getModel().setDate(((Date) modelo.getValueAt(fila, 6)).getYear() + 1900, ((Date) modelo.getValueAt(fila, 6)).getMonth(), ((Date) modelo.getValueAt(fila, 6)).getDate());
                dateFin.getModel().setSelected(true);
                tfEstado.setText(modelo.getValueAt(fila, 7).toString());
                deshabilitarCamposEdicion();
                operacion = "reactivar";
                flagActualizar = 1;
                tabla.setEnabled(true);
                bloquearBotonesExcepto(btnReactivar, btnActualizar, btnCancelar, btnSalir);
            }
        });

        btnActualizar.addActionListener(e -> actualizar());

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            flagActualizar = 0;
            operacion = "";
            idSeleccionado = -1;
            tabla.setEnabled(true);
            cbProducto.setEnabled(true);
            desbloquearTodosLosBotones();
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new MenuReferencial();
        });

        setVisible(true);
    }

    void habilitarCamposEdicion() {
    tfIdentificador.setEditable(true);
    tfDescripcion.setEditable(true);
    tfTasa.setEditable(true);
    tfPlazoDias.setEditable(true);
    cbProducto.setEnabled(true);
    }

    void deshabilitarCamposEdicion() {
    tfIdentificador.setEditable(false);
    tfDescripcion.setEditable(false);
    tfTasa.setEditable(false);
    tfPlazoDias.setEditable(false);
    cbProducto.setEnabled(false);
    }

    void prepararOperacion(String op) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            operacion = op;
            flagActualizar = 1;
            tabla.setEnabled(true);
            bloquearBotonesExcepto(btnActualizar, btnCancelar, btnSalir);
        }
    }

    void listarDatos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasa")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("pro_cod"),
                        rs.getString("tas_iden"),
                        rs.getString("tas_desc"),
                        rs.getDouble("tas_tasa"),
                        rs.getInt("tas_plaz_dias"),
                        rs.getDate("tas_ini_fec"),
                        rs.getDate("tas_fin_fec"),
                        rs.getString("tas_estado")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    void cargarProductosSinTasa() {
        productosDisponibles.clear();
        cbProducto.removeAllItems();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT p.pro_cod, p.pro_iden FROM producto p WHERE NOT EXISTS (SELECT 1 FROM tasa t WHERE t.pro_cod = p.pro_cod)")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pro_cod = rs.getInt("pro_cod");
                String pro_iden = rs.getString("pro_iden");
                productosDisponibles.put(pro_iden, pro_cod);
                cbProducto.addItem(pro_iden);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }
    }

    void actualizar() {
        if (flagActualizar == 1) {
            try (Connection conn = ConexionDB.conectar()) {
                Date fechaIni = (Date) dateInicio.getModel().getValue();
                Date fechaFin = (Date) dateFin.getModel().getValue();
                if (fechaIni == null || fechaFin == null || fechaFin.before(fechaIni)) {
                    JOptionPane.showMessageDialog(this, "⚠ Las fechas no son válidas.");
                    return;
                }

                PreparedStatement ps;
                switch (operacion) {
                    case "adicionar":
                        String seleccionado = (String) cbProducto.getSelectedItem();
                        if (seleccionado == null || !productosDisponibles.containsKey(seleccionado)) {
                            JOptionPane.showMessageDialog(this, "⚠ Selecciona un producto válido.");
                            return;
                        }
                        int pro_cod = productosDisponibles.get(seleccionado);
                        ps = conn.prepareStatement("INSERT INTO tasa (pro_cod, tas_iden, tas_desc, tas_tasa, tas_plaz_dias, tas_ini_fec, tas_fin_fec, tas_estado) VALUES (?, ?, ?, ?, ?, ?, ?, 'A')");
                        ps.setInt(1, pro_cod);
                        ps.setString(2, tfIdentificador.getText());
                        ps.setString(3, tfDescripcion.getText());
                        ps.setDouble(4, Double.parseDouble(tfTasa.getText()));
                        ps.setInt(5, Integer.parseInt(tfPlazoDias.getText()));
                        ps.setDate(6, new java.sql.Date(fechaIni.getTime()));
                        ps.setDate(7, new java.sql.Date(fechaFin.getTime()));
                        ps.executeUpdate();
                        break;
                    case "modificar":
                        ps = conn.prepareStatement("UPDATE tasa SET tas_iden=?, tas_desc=?, tas_tasa=?, tas_plaz_dias=?, tas_ini_fec=?, tas_fin_fec=? WHERE pro_cod = ?");
                        ps.setString(1, tfIdentificador.getText());
                        ps.setString(2, tfDescripcion.getText());
                        ps.setDouble(3, Double.parseDouble(tfTasa.getText()));
                        ps.setInt(4, Integer.parseInt(tfPlazoDias.getText()));
                        ps.setDate(5, new java.sql.Date(fechaIni.getTime()));
                        ps.setDate(6, new java.sql.Date(fechaFin.getTime()));
                        ps.setInt(7, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "eliminar":
                        ps = conn.prepareStatement("UPDATE tasa SET tas_estado='*' WHERE pro_cod = ?");
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "inactivar":
                        ps = conn.prepareStatement("UPDATE tasa SET tas_estado='I' WHERE pro_cod = ?");
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "reactivar":
                        ps = conn.prepareStatement("UPDATE tasa SET tas_estado='A' WHERE pro_cod = ?");
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                }

                listarDatos();
                limpiarCampos();
                cargarProductosSinTasa();
                flagActualizar = 0;
                operacion = "";
                idSeleccionado = -1;
                cbProducto.setEnabled(true);
                tabla.setEnabled(true);
                desbloquearTodosLosBotones();
                JOptionPane.showMessageDialog(this, "✅ Acción realizada correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    void limpiarCampos() {
        tfIdentificador.setText("");
        tfDescripcion.setText("");
        tfTasa.setText("");
        tfPlazoDias.setText("");
        tfEstado.setText("A");
        cbProducto.setSelectedIndex(-1);
        cbProducto.setEnabled(true);
    }

    void bloquearBotonesExcepto(JButton... permitidos) {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnSalir};
        for (JButton b : botones) b.setEnabled(false);
        for (JButton b : permitidos) b.setEnabled(true);
    }

    void desbloquearTodosLosBotones() {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnSalir};
        for (JButton b : botones) b.setEnabled(true);
    }

    private JDatePickerImpl crearDatePicker(boolean esInicio) {
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true);
        if (esInicio) {
            model.setValue(new java.util.Date());
        }
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }
}
