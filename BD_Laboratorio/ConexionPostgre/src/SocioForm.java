    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.*;
    import java.sql.*;
    import java.text.SimpleDateFormat;
    import java.util.*;
    import org.jdatepicker.impl.*;

    public class SocioForm extends JFrame {
        JTextField tfIdentificador = new JTextField(15);
        JTextField tfApellidoPaterno = new JTextField(15);
        JTextField tfApellidoMaterno = new JTextField(15);
        JTextField tfNombre = new JTextField(15);
        JDatePickerImpl dateNacimiento = crearDatePicker();
        JTextField tfCorreo = new JTextField(15);
        JTextField tfEstado = new JTextField("A", 1);
        JTextField tfCooperativa = new JTextField("MICREDIVISION", 15);
        JComboBox<String> cbUbicacion = new JComboBox<>();
        Map<String, Integer> mapaUbicaciones = new HashMap<>();

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
        JButton btnVolver = new JButton("Salir");

        public SocioForm() {
            setTitle("Mantenimiento de Socio");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));
            getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setLocationRelativeTo(null);

            tfEstado.setEditable(false);
            tfCooperativa.setEditable(false);

            JPanel panelRegistro = new JPanel(new GridLayout(9, 2, 10, 10));
            panelRegistro.setBorder(BorderFactory.createTitledBorder("Datos de Socio"));
            panelRegistro.add(new JLabel("Identificador:"));
            panelRegistro.add(tfIdentificador);
            panelRegistro.add(new JLabel("Apellido Paterno:"));
            panelRegistro.add(tfApellidoPaterno);
            panelRegistro.add(new JLabel("Apellido Materno:"));
            panelRegistro.add(tfApellidoMaterno);
            panelRegistro.add(new JLabel("Nombres:"));
            panelRegistro.add(tfNombre);
            panelRegistro.add(new JLabel("Fecha Nacimiento:"));
            panelRegistro.add(dateNacimiento);
            panelRegistro.add(new JLabel("Correo Electrónico:"));
            panelRegistro.add(tfCorreo);
            panelRegistro.add(new JLabel("Cooperativa:"));
            panelRegistro.add(tfCooperativa);
            panelRegistro.add(new JLabel("Ubicación:"));
            panelRegistro.add(cbUbicacion);
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

            modelo = new DefaultTableModel(new String[]{"ID", "Identificador", "Paterno", "Materno", "Nombre", "Nacimiento", "Correo", "Ubicación", "Estado"}, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tabla = new JTable(modelo);
            JScrollPane scrollTabla = new JScrollPane(tabla);
            scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Socios"));

            add(panelRegistro, BorderLayout.NORTH);
            add(scrollTabla, BorderLayout.CENTER);
            add(panelBotones, BorderLayout.SOUTH);

            listarDatos();

            btnAdicionar.addActionListener(e -> iniciarOperacion("adicionar"));
            btnModificar.addActionListener(e -> iniciarOperacion("modificar"));
            btnEliminar.addActionListener(e -> iniciarOperacion("eliminar"));
            btnInactivar.addActionListener(e -> iniciarOperacion("inactivar"));
            btnReactivar.addActionListener(e -> iniciarOperacion("reactivar"));
            btnActualizar.addActionListener(e -> actualizar());
            btnCancelar.addActionListener(e -> cancelarOperacion());
            btnVolver.addActionListener(e -> {
                dispose();
                new MenuMaestras();
            });

            setVisible(true);
            cargarUbicaciones();
        }

        void cargarUbicaciones() {
            try (Connection conn = ConexionDB.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ubi_cod, ubi_dep, ubi_pro, ubi_dis FROM ubicacion WHERE ubi_estado = 'A'")) {

                while (rs.next()) {
                    int codigo = rs.getInt("ubi_cod");
                    String nombre = rs.getString("ubi_dep") + " - " +
                                    rs.getString("ubi_pro") + " - " +
                                    rs.getString("ubi_dis");

                    cbUbicacion.addItem(nombre);
                    mapaUbicaciones.put(nombre, codigo);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar ubicaciones: " + e.getMessage());
            }
        }

        void listarDatos() {
            modelo.setRowCount(0);
            try (Connection conn = ConexionDB.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM socio")) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                            rs.getInt("soc_cod"),
                            rs.getString("soc_iden"),
                            rs.getString("soc_ape_pat"),
                            rs.getString("soc_ape_mat"),
                            rs.getString("soc_nom"),
                            rs.getDate("soc_fec_nac"),
                            rs.getString("soc_cor"),
                            rs.getInt("ubi_cod"),
                            rs.getString("soc_estado")
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
            }
        }

        void iniciarOperacion(String op) {
            int fila = tabla.getSelectedRow();
            if (!op.equals("adicionar") && fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila primero.");
                return;
            }
            operacion = op;
            flagActualizar = 1;
            if (!op.equals("adicionar")) {
                idSeleccionado = (int) modelo.getValueAt(fila, 0);
                tfIdentificador.setText(modelo.getValueAt(fila, 1).toString());
                tfApellidoPaterno.setText(modelo.getValueAt(fila, 2).toString());
                tfApellidoMaterno.setText(modelo.getValueAt(fila, 3).toString());
                tfNombre.setText(modelo.getValueAt(fila, 4).toString());
                ((UtilDateModel) dateNacimiento.getModel()).setValue((java.util.Date) modelo.getValueAt(fila, 5));
                tfCorreo.setText(modelo.getValueAt(fila, 6).toString());
                int ubiCod = (int) modelo.getValueAt(fila, 7);
                for (Map.Entry<String, Integer> entry : mapaUbicaciones.entrySet()) {
                    if (entry.getValue() == ubiCod) {
                        cbUbicacion.setSelectedItem(entry.getKey());
                        break;
                    }
                }
                tfEstado.setText(modelo.getValueAt(fila, 8).toString());
            } else {
                limpiarCampos();
            }
            bloquearBotonesExcepto(btnActualizar, btnCancelar, btnVolver);
        }

        void actualizar() {
            try (Connection conn = ConexionDB.conectar()) {
                String sql = switch (operacion) {
                    case "adicionar" -> "INSERT INTO socio (soc_iden, soc_ape_pat, soc_ape_mat, soc_nom, soc_fec_nac, soc_cor, soc_coo, ubi_cod, soc_estado) VALUES (?, ?, ?, ?, ?, ?, 1, ?, 'A')";
                    case "modificar" -> "UPDATE socio SET soc_iden=?, soc_ape_pat=?, soc_ape_mat=?, soc_nom=?, soc_fec_nac=?, soc_cor=?, ubi_cod=? WHERE soc_cod=?";
                    case "inactivar" -> "UPDATE socio SET soc_estado='I' WHERE soc_cod=?";
                    case "eliminar" -> "UPDATE socio SET soc_estado='*' WHERE soc_cod=?";
                    case "reactivar" -> "UPDATE socio SET soc_estado='A' WHERE soc_cod=?";
                    default -> null;
                };
                if (sql == null) return;

                PreparedStatement ps = conn.prepareStatement(sql);
                if (operacion.equals("adicionar") || operacion.equals("modificar")) {
                    ps.setString(1, tfIdentificador.getText());
                    ps.setString(2, tfApellidoPaterno.getText());
                    ps.setString(3, tfApellidoMaterno.getText());
                    ps.setString(4, tfNombre.getText());
                    ps.setDate(5, new java.sql.Date(((java.util.Date) dateNacimiento.getModel().getValue()).getTime()));
                    ps.setString(6, tfCorreo.getText());
                    String ubicacionSeleccionada = (String) cbUbicacion.getSelectedItem();
                    ps.setInt(7, mapaUbicaciones.get(ubicacionSeleccionada));
                    if (operacion.equals("modificar")) {
                        ps.setInt(8, idSeleccionado);
                    }
                } else {
                    ps.setInt(1, idSeleccionado);
                }
                ps.executeUpdate();

                if (operacion.equals("adicionar")) {
                    PreparedStatement ps2 = conn.prepareStatement("SELECT MAX(soc_cod) FROM socio");
                    ResultSet rs = ps2.executeQuery();
                    int nuevoSocio = rs.next() ? rs.getInt(1) : -1;
                    PreparedStatement insertCuenta = conn.prepareStatement("INSERT INTO socioproducto (id_socio, id_producto, num_cuenta, fec_apertura, saldo) VALUES (?, 1, ?, CURRENT_DATE, 0.00)");
                    insertCuenta.setInt(1, nuevoSocio);
                    insertCuenta.setString(2, generarNumeroCuenta(nuevoSocio));
                    insertCuenta.executeUpdate();
                }

                listarDatos();
                limpiarCampos();
                flagActualizar = 0;
                operacion = "";
                idSeleccionado = -1;
                desbloquearTodosLosBotones();
                JOptionPane.showMessageDialog(this, "✅ Operación realizada correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en la operación: " + e.getMessage());
            }
        }

        String generarNumeroCuenta(int idSocio) {
            return "C" + String.format("%08d", idSocio);
        }

        void cancelarOperacion() {
            limpiarCampos();
            operacion = "";
            flagActualizar = 0;
            idSeleccionado = -1;
            desbloquearTodosLosBotones();
        }

        void limpiarCampos() {
            tfIdentificador.setText("");
            tfApellidoPaterno.setText("");
            tfApellidoMaterno.setText("");
            tfNombre.setText("");
            tfCorreo.setText("");
            if (cbUbicacion.getItemCount() > 0) {
                cbUbicacion.setSelectedIndex(0);
            }
            tfEstado.setText("A");
            ((UtilDateModel) dateNacimiento.getModel()).setValue(new java.util.Date());
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

        private JDatePickerImpl crearDatePicker() {
            UtilDateModel model = new UtilDateModel();
            model.setSelected(true);
            Properties p = new Properties();
            p.put("text.today", "Hoy");
            p.put("text.month", "Mes");
            p.put("text.year", "Año");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            return new JDatePickerImpl(datePanel, new DateComponentFormatter());
        }
    }
