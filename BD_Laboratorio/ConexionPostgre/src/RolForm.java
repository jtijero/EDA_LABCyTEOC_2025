import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RolForm extends JFrame {
    JTextField tfRolRol = new JTextField(15);
    JTextField tfRolNom = new JTextField(15);
    JTextField tfRolEstado = new JTextField("A", 1);

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

    public RolForm() {
        setTitle("Mantenimiento de Rol");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfRolEstado.setEditable(false);

        // Panel de Registro
        JPanel panelRegistro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Rol"));
        panelRegistro.add(new JLabel("Código Interno del Rol:"));
        panelRegistro.add(tfRolRol);
        panelRegistro.add(new JLabel("Nombre del Rol:"));
        panelRegistro.add(tfRolNom);
        panelRegistro.add(new JLabel("Estado:"));
        panelRegistro.add(tfRolEstado);

        // Botones
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

        // Tabla de datos
        modelo = new DefaultTableModel(new String[]{"ID", "Código", "Nombre", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Tabla Rol"));

        add(panelRegistro, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        listarDatos();

        btnAdicionar.addActionListener(e -> {
            limpiarCampos();
            tfRolRol.setEditable(true);
            operacion = "adicionar";
            flagActualizar = 1;
            tabla.clearSelection();
            tabla.setEnabled(false);
            bloquearBotonesExcepto(btnAdicionar, btnActualizar, btnCancelar, btnSalir);
        });

        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                tfRolRol.setText(modelo.getValueAt(fila, 1).toString());
                tfRolNom.setText(modelo.getValueAt(fila, 2).toString());
                tfRolRol.setEditable(false);
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
                tfRolRol.setText(modelo.getValueAt(fila, 1).toString());
                tfRolNom.setText(modelo.getValueAt(fila, 2).toString());
                tfRolRol.setEditable(false);
                tfRolNom.setEditable(false);
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
                tfRolRol.setText(modelo.getValueAt(fila, 1).toString());
                tfRolNom.setText(modelo.getValueAt(fila, 2).toString());
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
                tfRolRol.setText(modelo.getValueAt(fila, 1).toString());
                tfRolNom.setText(modelo.getValueAt(fila, 2).toString());
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
            desbloquearTodosLosBotones();
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new MenuReferencial();
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled()) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0) {
                        idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                        tfRolRol.setText(modelo.getValueAt(fila, 1).toString());
                        tfRolNom.setText(modelo.getValueAt(fila, 2).toString());
                        tfRolEstado.setText(modelo.getValueAt(fila, 3).toString());
                    }
                }
            }
        });

        setVisible(true);
    }

    void listarDatos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rol")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("rol_cod"),
                        rs.getString("rol_rol"),
                        rs.getString("rol_nom"),
                        rs.getString("rol_estado")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    void actualizar() {
        if (flagActualizar == 1) {
            try (Connection conn = ConexionDB.conectar()) {
                String sql = "";
                PreparedStatement ps;

                switch (operacion) {
                    case "adicionar":
                        sql = "INSERT INTO rol (rol_rol, rol_nom, rol_estado) VALUES (?, ?, 'A')";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, tfRolRol.getText());
                        ps.setString(2, tfRolNom.getText());
                        ps.executeUpdate();
                        break;
                    case "modificar":
                        sql = "UPDATE rol SET rol_nom = ? WHERE rol_cod = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, tfRolNom.getText());
                        ps.setInt(2, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "eliminar":
                        sql = "UPDATE rol SET rol_estado = '*' WHERE rol_cod = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "inactivar":
                        sql = "UPDATE rol SET rol_estado = 'I' WHERE rol_cod = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "reactivar":
                        sql = "UPDATE rol SET rol_estado = 'A' WHERE rol_cod = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                }

                listarDatos();
                limpiarCampos();
                flagActualizar = 0;
                operacion = "";
                idSeleccionado = -1;
                tabla.setEnabled(true);
                desbloquearTodosLosBotones();
                JOptionPane.showMessageDialog(this, "Acción realizada correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    void limpiarCampos() {
        tfRolRol.setText("");
        tfRolNom.setText("");
        tfRolEstado.setText("A");
        tfRolRol.setEditable(true);
        tfRolNom.setEditable(true);
    }

    void bloquearBotonesExcepto(JButton... permitidos) {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnSalir};
        for (JButton b : botones) {
            b.setEnabled(false);
        }
        for (JButton b : permitidos) {
            b.setEnabled(true);
        }
    }

    void desbloquearTodosLosBotones() {
        JButton[] botones = {btnAdicionar, btnModificar, btnEliminar, btnInactivar, btnReactivar, btnActualizar, btnCancelar, btnSalir};
        for (JButton b : botones) {
            b.setEnabled(true);
        }
    }
}
