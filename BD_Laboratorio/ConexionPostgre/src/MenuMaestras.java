import java.awt.*;
import javax.swing.*;

public class MenuMaestras extends JFrame {
    public MenuMaestras() {
        setTitle("");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Tablas Maestras"));

        JButton btnPersona = new JButton("Persona");
        JButton btnUsuario = new JButton("Usuario");
        JButton btnSocio = new JButton("Socio");
        JButton btnCooperativa = new JButton("Cooperativa");
        JButton btnVolver = new JButton("Volver");

        panel.add(btnPersona);
        panel.add(btnUsuario);
        panel.add(btnSocio);
        panel.add(btnCooperativa);
        panel.add(btnVolver);

        add(panel);

        btnPersona.addActionListener(e -> {
            dispose();
            new PersonaForm();
        });

        btnUsuario.addActionListener(e -> {
            dispose();
            new UsuarioForm();
        });

        btnSocio.addActionListener(e -> {
            dispose();
            new SocioForm();
        });

        btnCooperativa.addActionListener(e -> {
            dispose();
            new CooperativaForm();
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        setVisible(true);
    }
}
