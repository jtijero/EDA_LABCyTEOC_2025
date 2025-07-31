import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioForm extends JFrame {
    JTextField tfIdentificador = new JTextField(15);
    JTextField tfUsuario = new JTextField(15);
    JTextField tfPassword = new JTextField(15);
    JTextField tfEstado = new JTextField("A", 1);
    JComboBox<String> cbPersona = new JComboBox<>();
    JTextField cbCooperativa = new JTextField();
    JTextField tfRoles = new JTextField();
    Map<String, Integer> personas = new LinkedHashMap<>();

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

    public UsuarioForm() {
        setTitle("Mantenimiento de Usuario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfEstado.setEditable(false);
        tfRoles.setEditable(false);
        cbCooperativa.setEditable(false);

        JPanel panelRegistro = new JPanel(new GridLayout(7, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));
        panelRegistro.add(new JLabel("Identificador:"));
        panelRegistro.add(tfIdentificador);
        panelRegistro.add(new JLabel("Usuario:"));
        panelRegistro.add(tfUsuario);
        panelRegistro.add(new JLabel("Contraseña:"));
        panelRegistro.add(tfPassword);
        panelRegistro.add(new JLabel("Persona:"));
        panelRegistro.add(cbPersona);
        panelRegistro.add(new JLabel("Cooperativa:"));
        panelRegistro.add(cbCooperativa);
        panelRegistro.add(new JLabel("Estado:"));
        panelRegistro.add(tfEstado);
        panelRegistro.add(new JLabel("Roles Asignados:"));
        panelRegistro.add(tfRoles);

        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));
        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVolver);

        modelo = new DefaultTableModel(new String[]{"ID", "Usuario", "Identificador", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelRegistro, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPersonas();
        listarUsuarios();

        btnAdicionar.addActionListener(e -> {
            limpiarCampos();
            operacion = "adicionar";
            flagActualizar = 1;
            tabla.clearSelection();
            bloquearBotonesExcepto(btnAdicionar, btnActualizar, btnCancelar, btnVolver);
        });

        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = (int) modelo.getValueAt(fila, 0);
                tfUsuario.setText((String) modelo.getValueAt(fila, 1));
                tfIdentificador.setText((String) modelo.getValueAt(fila, 2));
                tfEstado.setText((String) modelo.getValueAt(fila, 3));
                cargarPersonaCooperativa(idSeleccionado);
                cbCooperativa.setText("MICREDIVISION");
                cargarRoles(idSeleccionado);
                operacion = "modificar";
                flagActualizar = 1;
                bloquearBotonesExcepto(btnModificar, btnActualizar, btnCancelar, btnVolver);
            }
        });

        btnEliminar.addActionListener(e -> prepararOperacion("eliminar"));
        btnInactivar.addActionListener(e -> prepararOperacion("inactivar"));
        btnReactivar.addActionListener(e -> prepararOperacion("reactivar"));

        btnActualizar.addActionListener(e -> actualizar());
        btnCancelar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuMaestras();
        });;

        setVisible(true);
    }

    void cargarPersonas() {
        cbPersona.removeAllItems();
        personas.clear();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT per_cod, per_nom FROM persona")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("per_nom");
                int id = rs.getInt("per_cod");
                personas.put(nombre, id);
                cbPersona.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando personas: " + e.getMessage());
        }
    }

    void cargarPersonaCooperativa(int usu_cod) {
        try (Connection conn = ConexionDB.conectar();
            PreparedStatement ps = conn.prepareStatement("SELECT per_cod FROM usuario WHERE usu_cod=?")) {
            ps.setInt(1, usu_cod);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int personaId = rs.getInt("per_cod");
                for (Map.Entry<String, Integer> entry : personas.entrySet()) {
                    if (entry.getValue() == personaId) {
                        cbPersona.setSelectedItem(entry.getKey());
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar persona asociada: " + e.getMessage());
        }
    }

    void cargarRoles(int usu_cod) {
        try (Connection conn = ConexionDB.conectar();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT r.rol_nom FROM usuarioroles ur JOIN rol r ON ur.rol_id = r.rol_cod WHERE ur.usu_id = ?")
        ) {
            ps.setInt(1, usu_cod);
            ResultSet rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(rs.getString("rol_nom"));
            }
            tfRoles.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando roles: " + e.getMessage());
        }
    }

    void listarUsuarios() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT usu_cod, usu_usu, usu_ide, usu_estado FROM usuario")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    void prepararOperacion(String op) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            tfUsuario.setText((String) modelo.getValueAt(fila, 1));
            tfIdentificador.setText((String) modelo.getValueAt(fila, 2));
            tfEstado.setText((String) modelo.getValueAt(fila, 3));
            cargarRoles(idSeleccionado);
            cbCooperativa.setText("MICREDIVISION");
            operacion = op;
            flagActualizar = 1;
            bloquearBotonesExcepto(btnActualizar, btnCancelar, btnVolver);
        }
    }

    void actualizar() {
        try (Connection conn = ConexionDB.conectar()) {
            if (operacion.equals("adicionar")) {
                String sql = "INSERT INTO usuario (usu_ide, usu_usu, usu_pas, coo_cod, per_cod, usu_estado) VALUES (?, ?, ?, ?, ?, 'A')";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tfIdentificador.getText());
                ps.setString(2, tfUsuario.getText());
                ps.setString(3, tfPassword.getText());
                ps.setInt(4, 1);
                ps.setInt(5, personas.get((String) cbPersona.getSelectedItem()));
                ps.executeUpdate();
            } else if (operacion.equals("modificar")) {
                String sql = "UPDATE usuario SET usu_ide=?, usu_usu=?, usu_pas=?, coo_cod=?, per_cod=? WHERE usu_cod=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tfIdentificador.getText());
                ps.setString(2, tfUsuario.getText());
                ps.setString(3, tfPassword.getText());
                ps.setInt(4, 1);
                ps.setInt(5, personas.get((String) cbPersona.getSelectedItem()));
                ps.setInt(6, idSeleccionado);
                ps.executeUpdate();
            } else if (operacion.equals("eliminar")) {
                PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET usu_estado='*' WHERE usu_cod=?");
                ps.setInt(1, idSeleccionado);
                ps.executeUpdate();
            } else if (operacion.equals("inactivar")) {
                PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET usu_estado='I' WHERE usu_cod=?");
                ps.setInt(1, idSeleccionado);
                ps.executeUpdate();
            } else if (operacion.equals("reactivar")) {
                PreparedStatement ps = conn.prepareStatement("UPDATE usuario SET usu_estado='A' WHERE usu_cod=?");
                ps.setInt(1, idSeleccionado);
                ps.executeUpdate();
            }
            listarUsuarios();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Operación realizada correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar operación: " + e.getMessage());
        }
    }

    void limpiarCampos() {
        tfIdentificador.setText("");
        tfUsuario.setText("");
        tfPassword.setText("");
        tfEstado.setText("A");
        tfRoles.setText("");
        cbPersona.setSelectedIndex(0);
        cbCooperativa.setText("");;
        flagActualizar = 0;
        operacion = "";
        idSeleccionado = -1;
        desbloquearTodosLosBotones();
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
