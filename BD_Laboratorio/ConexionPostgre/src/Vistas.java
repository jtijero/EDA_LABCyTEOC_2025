import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Vistas extends JFrame {

    public Vistas() {
        setTitle("Vistas de la Cooperativa");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "VISTAS DISPONIBLES",
            TitledBorder.CENTER,
            TitledBorder.TOP
        ));

        JButton btnVistaResumen = new JButton("Resumen General");
        JButton btnVistaProducto = new JButton("Socios con Productos");
        JButton btnVistaUbicacion = new JButton("Socios por Ubicación");
        JButton btnVistaUsuarios = new JButton("Usuarios y Roles");
        JButton btnCerrar = new JButton("Volver al Menú");

        panel.add(btnVistaResumen);
        panel.add(btnVistaProducto);
        panel.add(btnVistaUbicacion);
        panel.add(btnVistaUsuarios);
        panel.add(btnCerrar);

        add(panel);

        btnVistaResumen.addActionListener(e -> new VistaResumenGeneral().setVisible(true));
        btnVistaProducto.addActionListener(e -> new VistaSocioProducto().setVisible(true));
        btnVistaUbicacion.addActionListener(e -> new VistaSocioUbicacion().setVisible(true));
        btnVistaUsuarios.addActionListener(e -> new VistaUsuariosRoles().setVisible(true));
        btnCerrar.addActionListener(e -> {
            dispose();
            new Menu();
        });
    }
}
