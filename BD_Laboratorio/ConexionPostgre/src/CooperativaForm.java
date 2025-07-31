import java.awt.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;

public class CooperativaForm extends JFrame {
    JTextField tfIden = new JTextField(20);
    JTextField tfNombre = new JTextField(20);
    JTextField tfSigla = new JTextField(10);
    JTextField tfDireccion = new JTextField(30);
    JTextField tfTelefono = new JTextField(20);
    JTextField tfCorreo = new JTextField(30);
    JTextField tfUsuario = new JTextField(20);
    JLabel lblLogo = new JLabel();
    String rutaLogo = null;

    JButton btnModificar = new JButton("Modificar");
    JButton btnGuardar = new JButton("Guardar");
    JButton btnCancelar = new JButton("Cancelar");
    JButton btnVolver = new JButton("Volver");
    JButton btnCargarLogo = new JButton("Cargar Logo");

    int idCooperativa = -1;

    public CooperativaForm() {
        setTitle("Cooperativa - Mantenimiento");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Cooperativa"));

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Identificador:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfIden, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfNombre, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Sigla:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfSigla, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = row; panelForm.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; panelForm.add(tfUsuario, gbc);

        JPanel logoWrapper = new JPanel(new BorderLayout(5, 5));
        logoWrapper.setBorder(BorderFactory.createTitledBorder("Logo"));

        lblLogo.setPreferredSize(new Dimension(200, 100));
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        lblLogo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        logoWrapper.add(lblLogo, BorderLayout.CENTER);
        logoWrapper.add(btnCargarLogo, BorderLayout.SOUTH);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panelForm.add(logoWrapper, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnModificar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVolver);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
        setEditable(false);

        btnModificar.addActionListener(e -> setEditable(true));

        btnCancelar.addActionListener(e -> {
            cargarDatos();
            setEditable(false);
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new MenuMaestras();
        });

        btnGuardar.addActionListener(e -> guardarDatos());

        btnCargarLogo.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                rutaLogo = file.getAbsolutePath();
                mostrarLogo(rutaLogo);
            }
        });

        setVisible(true);
    }

    void cargarDatos() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cooperativa LIMIT 1")) {

            if (rs.next()) {
                idCooperativa = rs.getInt("coo_cod");
                tfIden.setText(rs.getString("coo_iden"));
                tfNombre.setText(rs.getString("coo_nom"));
                tfSigla.setText(rs.getString("coo_sig"));
                tfDireccion.setText(rs.getString("coo_dir"));
                tfTelefono.setText(rs.getString("coo_tel"));
                tfCorreo.setText(rs.getString("coo_cor"));
                tfUsuario.setText(rs.getString("coo_usu"));
                rutaLogo = rs.getString("coo_log");

            if (rutaLogo != null && !rutaLogo.isEmpty()) {
                SwingUtilities.invokeLater(() -> mostrarLogo(rutaLogo));
            } else {
                lblLogo.setIcon(null);
            }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }

    void guardarDatos() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql;
            if (idCooperativa == -1) {
                sql = "INSERT INTO cooperativa (coo_iden, coo_nom, coo_sig, coo_dir, coo_tel, coo_cor, coo_usu, coo_log) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "UPDATE cooperativa SET coo_iden=?, coo_nom=?, coo_sig=?, coo_dir=?, coo_tel=?, coo_cor=?, coo_usu=?, coo_log=? WHERE coo_cod=?";
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfIden.getText());
            ps.setString(2, tfNombre.getText());
            ps.setString(3, tfSigla.getText());
            ps.setString(4, tfDireccion.getText());
            ps.setString(5, tfTelefono.getText());
            ps.setString(6, tfCorreo.getText());
            ps.setString(7, tfUsuario.getText());
            ps.setString(8, rutaLogo);
            if (idCooperativa != -1) ps.setInt(9, idCooperativa);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Datos guardados exitosamente");
            cargarDatos();
            setEditable(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    void setEditable(boolean editable) {
        tfIden.setEditable(editable);
        tfNombre.setEditable(editable);
        tfSigla.setEditable(editable);
        tfDireccion.setEditable(editable);
        tfTelefono.setEditable(editable);
        tfCorreo.setEditable(editable);
        tfUsuario.setEditable(editable);
        btnCargarLogo.setEnabled(editable);
        btnGuardar.setEnabled(editable);
        btnModificar.setEnabled(!editable);
    }

    private void mostrarLogo(String ruta) {
        ImageIcon originalIcon = new ImageIcon(ruta);
        int labelWidth = lblLogo.getWidth();
        int labelHeight = lblLogo.getHeight();

        if (labelWidth == 0 || labelHeight == 0) {
            labelWidth = 200;
            labelHeight = 100;
        }

        int imgWidth = originalIcon.getIconWidth();
        int imgHeight = originalIcon.getIconHeight();

        double scale = Math.max((double)labelWidth / imgWidth, (double)labelHeight / imgHeight);

        int scaledWidth = (int)(imgWidth * scale);
        int scaledHeight = (int)(imgHeight * scale);

        Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledImage));
    }
}
