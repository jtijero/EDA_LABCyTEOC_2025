import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jdatepicker.impl.*;


public class PersonaForm extends JFrame {   
    final int ID_COOPERATIVA_FIJA = 1;

    JTextField tfIdentificador = new JTextField(15);
    JTextField tfApellidoPaterno = new JTextField(15);
    JTextField tfApellidoMaterno = new JTextField(15);
    JTextField tfNombre = new JTextField(15);
    JTextField tfCorreo = new JTextField(15);
    JTextField tfEstado = new JTextField("A", 1);
    JDatePickerImpl dateNacimiento = crearDatePicker();
    JTextField cbCooperativa = new JTextField("MICREDIVISION");

    Map<String, Integer> mapCooperativas = new LinkedHashMap<>();

    JLabel lblFoto = new JLabel();
    JButton btnSeleccionarFoto = new JButton("Seleccionar Foto");
    byte[] fotoBytes = null;

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

    public PersonaForm() {
        cbCooperativa.setEditable(false);
        setTitle("Mantenimiento de Persona");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);

        tfEstado.setEditable(false);
        lblFoto.setPreferredSize(new Dimension(120, 120));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Datos de Persona"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Identificador:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfIdentificador, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Apellido Paterno:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfApellidoPaterno, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Apellido Materno:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfApellidoMaterno, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfNombre, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 1; panelRegistro.add(dateNacimiento, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Correo Electrónico:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfCorreo, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Foto:"), gbc);
        gbc.gridx = 1; panelRegistro.add(btnSeleccionarFoto, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Cooperativa:"), gbc);
        gbc.gridx = 1; panelRegistro.add(cbCooperativa, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; panelRegistro.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; panelRegistro.add(tfEstado, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = y + 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelRegistro.add(lblFoto, gbc);

        // Resetear para otras configuraciones si las usas después
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;


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

        modelo = new DefaultTableModel(new String[]{"ID", "Identificador", "Paterno", "Materno", "Nombre", "Nacimiento", "Correo", "Estado"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Personas"));

        add(panelRegistro, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        listarDatos();

        btnSeleccionarFoto.addActionListener(e -> seleccionarFoto());

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
    }

    void seleccionarFoto() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                fotoBytes = Files.readAllBytes(file.toPath());
                ImageIcon originalIcon = new ImageIcon(fotoBytes);
                Image imagenEscalada = recortarYEscalarImagen(originalIcon.getImage(), 120, 120);
                lblFoto.setIcon(new ImageIcon(imagenEscalada));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error cargando imagen: " + ex.getMessage());
            }
        }
    }
    private Image recortarYEscalarImagen(Image img, int ancho, int alto) {
        // Convertir a BufferedImage
        BufferedImage bi = new BufferedImage(
            img.getWidth(null),
            img.getHeight(null),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        int originalWidth = bi.getWidth();
        int originalHeight = bi.getHeight();

        // Calcular tamaño cuadrado centrado
        int size = Math.min(originalWidth, originalHeight);
        int x = (originalWidth - size) / 2;
        int y = (originalHeight - size) / 2;

        BufferedImage cropped = bi.getSubimage(x, y, size, size);

        // Escalar proporcionalmente al label
        Image scaled = cropped.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return scaled;
    }

    void listarDatos() {
        modelo.setRowCount(0);
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM persona")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("per_cod"),
                        rs.getString("per_iden"),
                        rs.getString("per_ape_pat"),
                        rs.getString("per_ape_mat"),
                        rs.getString("per_nom"),
                        rs.getDate("per_fec_nac"),
                        rs.getString("per_cor"),
                        rs.getString("per_estado")
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
            tfEstado.setText(modelo.getValueAt(fila, 7).toString());
            try (Connection conn = ConexionDB.conectar();
                PreparedStatement ps = conn.prepareStatement("SELECT per_fot FROM persona WHERE per_cod = ?")) {
                ps.setInt(1, idSeleccionado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    fotoBytes = rs.getBytes("per_fot");
                    if (fotoBytes != null && fotoBytes.length > 0) {
                        ImageIcon icon = new ImageIcon(fotoBytes);
                        Image imagenEscalada = recortarYEscalarImagen(icon.getImage(), 120, 120);
                        lblFoto.setIcon(new ImageIcon(imagenEscalada));
                    } else {
                        lblFoto.setIcon(null);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar imagen: " + ex.getMessage());
            }
        } else {
            limpiarCampos();
        }
        bloquearBotonesExcepto(btnActualizar, btnCancelar, btnVolver);
    }

    void actualizar() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = switch (operacion) {
                case "adicionar" -> "INSERT INTO persona (per_iden, per_ape_pat, per_ape_mat, per_nom, per_fec_nac, per_cor, per_fot, per_coo, per_estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'A')";
                case "modificar" -> "UPDATE persona SET per_iden=?, per_ape_pat=?, per_ape_mat=?, per_nom=?, per_fec_nac=?, per_cor=?, per_fot=?, per_coo=? WHERE per_cod=?";
                case "eliminar" -> "UPDATE persona SET per_estado='*' WHERE per_cod=?";
                case "inactivar" -> "UPDATE persona SET per_estado='I' WHERE per_cod=?";
                case "reactivar" -> "UPDATE persona SET per_estado='A' WHERE per_cod=?";
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
                ps.setBytes(7, fotoBytes);
                ps.setInt(8, ID_COOPERATIVA_FIJA);
                if (operacion.equals("modificar")) {
                    ps.setInt(9, idSeleccionado);
                }
            } else {
                ps.setInt(1, idSeleccionado);
            }
            ps.executeUpdate();
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
        tfEstado.setText("A");
        lblFoto.setIcon(null);
        fotoBytes = null;
        cbCooperativa.setText("MICREDIVISION");
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

        datePanel.addActionListener(e -> {
            java.util.Date selectedDate = (java.util.Date) model.getValue();
            java.util.Date hoy = new java.util.Date();
            if (selectedDate != null && selectedDate.after(hoy)) {
                JOptionPane.showMessageDialog(null, "❌ La fecha de nacimiento no puede ser posterior a hoy.");
                model.setValue(hoy); // vuelve a la fecha actual
            }
        });

        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }
    
}
