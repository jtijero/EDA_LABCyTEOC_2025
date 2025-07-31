import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Menú Principal - Cooperativa");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "MENÚ PRINCIPAL",
            TitledBorder.CENTER,
            TitledBorder.TOP
        ));

        JButton btnRegistro = new JButton("Registro de Datos");
        JButton btnVistas = new JButton("Vistas");
        JButton btnProcesos = new JButton("Reportes y Procedimientos");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnRegistro);
        panel.add(btnVistas);
        panel.add(btnProcesos);
        panel.add(btnSalir);

        add(panel);

        btnRegistro.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        btnVistas.addActionListener(e -> {
            new Vistas().setVisible(true);
            dispose();
        });
        btnProcesos.addActionListener(e -> {
            new ProcedimientosPanel().setVisible(true);
            dispose();
        });
        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
