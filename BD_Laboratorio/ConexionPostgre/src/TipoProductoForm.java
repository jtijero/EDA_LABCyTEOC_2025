import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TipoProductoForm extends JFrame {
    JTextField tfTipProNom = new JTextField(15);
    JTextField tfTipProEstado = new JTextField("A", 1);

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

    public TipoProductoForm() {
        setTitle("Mantenimiento de Tipo de Producto");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfTipProEstado.setEditable(false);

        // Panel de Registro
        JPanel panelRegistro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Tipo de Producto"));
        panelRegistro.add(new JLabel("Nombre del Tipo de Producto:"));
        panelRegistro.add(tfTipProNom);
        panelRegistro.add(new JLabel("Estado:"));
        panelRegistro.add(tfTipProEstado);

        // Panel de Botones
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

        // Tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Tabla Tipo Producto"));

        add(panelRegistro, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        listarDatos();

        // Eventos
        btnAdicionar.addActionListener(e -> {
            limpiarCampos();
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
                tfTipProNom.setText(modelo.getValueAt(fila, 1).toString());
                tfTipProEstado.setText(modelo.getValueAt(fila, 2).toString());
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
                tfTipProNom.setText(modelo.getValueAt(fila, 1).toString());
                tfTipProEstado.setText(modelo.getValueAt(fila, 2).toString());
                tfTipProNom.setEditable(false);
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
                tfTipProNom.setText(modelo.getValueAt(fila, 1).toString());
                tfTipProEstado.setText(modelo.getValueAt(fila, 2).toString());
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
                tfTipProNom.setText(modelo.getValueAt(fila, 1).toString());
                tfTipProEstado.setText(modelo.getValueAt(fila, 2).toString());
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
                        tfTipProNom.setText(modelo.getValueAt(fila, 1).toString());
                        tfTipProEstado.setText(modelo.getValueAt(fila, 2).toString());
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM tipoproducto")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("tip_pro_cod"),
                        rs.getString("tip_pro_nom"),
                        rs.getString("tip_pro_estado")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    void actualizar() {
        if (flagActualizar == 1) {
            try (Connection conn = ConexionDB.conectar()) {
                PreparedStatement ps;
                switch (operacion) {
                    case "adicionar":
                        ps = conn.prepareStatement("INSERT INTO tipoproducto (tip_pro_nom, tip_pro_estado) VALUES (?, 'A')");
                        ps.setString(1, tfTipProNom.getText());
                        ps.executeUpdate();
                        break;
                    case "modificar":
                        ps = conn.prepareStatement("UPDATE tipoproducto SET tip_pro_nom = ? WHERE tip_pro_cod = ?");
                        ps.setString(1, tfTipProNom.getText());
                        ps.setInt(2, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "eliminar":
                        ps = conn.prepareStatement("UPDATE tipoproducto SET tip_pro_estado = '*' WHERE tip_pro_cod = ?");
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "inactivar":
                        ps = conn.prepareStatement("UPDATE tipoproducto SET tip_pro_estado = 'I' WHERE tip_pro_cod = ?");
                        ps.setInt(1, idSeleccionado);
                        ps.executeUpdate();
                        break;
                    case "reactivar":
                        ps = conn.prepareStatement("UPDATE tipoproducto SET tip_pro_estado = 'A' WHERE tip_pro_cod = ?");
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
                JOptionPane.showMessageDialog(this, "✅ Acción realizada correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    void limpiarCampos() {
        tfTipProNom.setText("");
        tfTipProEstado.setText("A");
        tfTipProNom.setEditable(true);
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
}
