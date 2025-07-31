import java.awt.*;
import javax.swing.*;

public class MenuTransacciones extends JFrame {
    public MenuTransacciones() {
        setTitle("Tablas Transaccionales");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Tablas Transaccionales"));

        JButton btnProducto = new JButton("Producto");
        JButton btnSocioProducto = new JButton("Socio Producto");
        JButton btnVolver = new JButton("Volver");

        panel.add(btnProducto);
        panel.add(btnSocioProducto);
        panel.add(btnVolver);

        add(panel);

        btnProducto.addActionListener(e -> {
            dispose();
            new ProductoForm();
        });

        btnSocioProducto.addActionListener(e -> {
            dispose();
            new SocioProductoForm();
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        setVisible(true);
    }
}
