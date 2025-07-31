import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "MENÚ REGISTRO",
            TitledBorder.CENTER,
            TitledBorder.DEFAULT_POSITION,
            null
        ));

        JButton btnReferenciales = new JButton("Tablas Referenciales");
        JButton btnMaestras = new JButton("Tablas Maestras");
        JButton btnTransacciones = new JButton("Tablas Transaccionales");
        JButton btnSeguridad = new JButton("Control de Seguridad");
        JButton btnSalir = new JButton("Volver");

        panel.add(btnReferenciales);
        panel.add(btnMaestras);
        panel.add(btnTransacciones);
        panel.add(btnSeguridad);
        panel.add(btnSalir);

        add(panel);

        btnReferenciales.addActionListener(e -> {
            dispose();
            new MenuReferencial();
        });

        btnMaestras.addActionListener(e -> {
            dispose();
            new MenuMaestras();
        });

        btnTransacciones.addActionListener(e -> {
            dispose();
            new MenuTransacciones();
        });

        btnSeguridad.addActionListener(e -> {
            dispose();
            new MenuSeguridad();
        });

        btnSalir.addActionListener(e -> {
            dispose();
            new Menu();
        });

        setVisible(true);
    }
}
