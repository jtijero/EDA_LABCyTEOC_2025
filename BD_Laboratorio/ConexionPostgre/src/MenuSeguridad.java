import java.awt.*;
import javax.swing.*;

public class MenuSeguridad extends JFrame {
    public MenuSeguridad() {
        setTitle("");
        setSize(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Control de Seguridad"));

        JButton btnUsuarioRol = new JButton("Usuario - Roles");
        JButton btnVolver = new JButton("Volver");

        panel.add(btnUsuarioRol);
        panel.add(btnVolver);

        add(panel);

        btnUsuarioRol.addActionListener(e -> {
            dispose();
            new UsuarioRolForm();
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        setVisible(true);
    }
}
