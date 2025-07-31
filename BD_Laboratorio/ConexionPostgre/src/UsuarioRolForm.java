import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class UsuarioRolForm extends JFrame {
    private JComboBox<String> cbUsuarios = new JComboBox<>();
    private JPanel panelRoles = new JPanel(new GridLayout(0, 1));
    private JButton btnGuardar = new JButton("Guardar Cambios");
    private JButton btnVolver = new JButton("Volver");

    private Map<String, Integer> mapUsuarios = new HashMap<>();
    private Map<String, Integer> mapRoles = new HashMap<>();
    private Map<Integer, JCheckBox> checkboxes = new HashMap<>();

    public UsuarioRolForm() {
        setTitle("Asignar Roles a Usuario");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Seleccionar Usuario:"), BorderLayout.NORTH);
        topPanel.add(cbUsuarios, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(panelRoles), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        cargarUsuarios();
        cargarRoles();

        cbUsuarios.addActionListener(e -> cargarRolesDelUsuario());
        btnGuardar.addActionListener(e -> guardarCambios());
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuSeguridad();
        });;

        setVisible(true);
    }

    private void cargarUsuarios() {
        mapUsuarios.clear();
        cbUsuarios.removeAllItems();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT usu_cod, usu_usu FROM usuario WHERE usu_estado = 'A'")
        ) {
            while (rs.next()) {
                int id = rs.getInt("usu_cod");
                String nombre = rs.getString("usu_usu");
                mapUsuarios.put(nombre, id);
                cbUsuarios.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void cargarRoles() {
        mapRoles.clear();
        panelRoles.removeAll();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT rol_cod, rol_nom FROM rol WHERE rol_estado = 'A'")
        ) {
            while (rs.next()) {
                int id = rs.getInt("rol_cod");
                String nombre = rs.getString("rol_nom");
                JCheckBox check = new JCheckBox(nombre);
                checkboxes.put(id, check);
                panelRoles.add(check);
                mapRoles.put(nombre, id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar roles: " + e.getMessage());
        }
        panelRoles.revalidate();
        panelRoles.repaint();
    }

    private void cargarRolesDelUsuario() {
        if (cbUsuarios.getSelectedItem() == null) return;
        int usuarioId = mapUsuarios.get((String) cbUsuarios.getSelectedItem());

        for (JCheckBox cb : checkboxes.values()) cb.setSelected(false);

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT rol_id FROM usuarioroles WHERE usu_id = ?")
        ) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int rolId = rs.getInt("rol_id");
                if (checkboxes.containsKey(rolId)) checkboxes.get(rolId).setSelected(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar roles del usuario: " + e.getMessage());
        }
    }

    private void guardarCambios() {
        if (cbUsuarios.getSelectedItem() == null) return;
        int usuarioId = mapUsuarios.get((String) cbUsuarios.getSelectedItem());

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM usuarioroles WHERE usu_id = ?")) {
                deleteStmt.setInt(1, usuarioId);
                deleteStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO usuarioroles (usu_id, rol_id) VALUES (?, ?)")
            ) {
                for (Map.Entry<Integer, JCheckBox> entry : checkboxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        insertStmt.setInt(1, usuarioId);
                        insertStmt.setInt(2, entry.getKey());
                        insertStmt.addBatch();
                    }
                }
                insertStmt.executeBatch();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "✅ Roles actualizados correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar cambios: " + e.getMessage());
        }
    }
} 