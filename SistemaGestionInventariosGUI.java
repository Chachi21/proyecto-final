/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_algoritmos;

/**
 *
 * @author Kristofer Rojas
 */
import javax.swing.*;  
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Clase Producto
class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;

    public Producto(String nombre, String categoria, double precio, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}

// Clase Sistema de Gestión de Inventarios con interfaz gráfica
public class SistemaGestionInventariosGUI extends JFrame {
    private final ArrayList<Producto> productos;
    private final ArrayList<String> categorias;

    // Variables para la creación de usuarios
    private String usuarioGuardado;
    private String contrasenaGuardada;
    private String regionSeleccionada;
    private String rolSeleccionado;

    private JTextArea areaDisplay;
    private JTextField nombreProductoField;
    private JTextField categoriaField;
    private JTextField precioField;
    private JTextField cantidadField;

    public SistemaGestionInventariosGUI() {
        productos = new ArrayList<>();
        categorias = new ArrayList<>();

        // Llamar a la pantalla de creación de usuario
        crearPantallaLogin();
    }

    // Pantalla de creación de usuario y contraseña
    private void crearPantallaLogin() {
        JFrame loginFrame = new JFrame("Crear Usuario");
        loginFrame.setSize(400, 300);
        loginFrame.setLayout(new GridLayout(6, 2));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioField = new JTextField();
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        JPasswordField contrasenaField = new JPasswordField();

        // Componentes para seleccionar región y rol
        JLabel regionLabel = new JLabel("Región:");
        String[] regiones = {"Norte", "Sur", "Este", "Oeste"};
        JComboBox<String> regionComboBox = new JComboBox<>(regiones);

        JLabel rolLabel = new JLabel("Rol:");
        String[] roles = {"Administrador", "Vendedor", "Encargado de Bodega"};
        JComboBox<String> rolComboBox = new JComboBox<>(roles);

        JButton guardarBtn = new JButton("Guardar");
        JButton continuarBtn = new JButton("Continuar");

        // Acción del botón Guardar
        guardarBtn.addActionListener((ActionEvent e) -> {
            usuarioGuardado = usuarioField.getText();
            contrasenaGuardada = new String(contrasenaField.getPassword());
            regionSeleccionada = (String) regionComboBox.getSelectedItem();
            rolSeleccionado = (String) rolComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(loginFrame, "Usuario, Contraseña, Región y Rol guardados.");
        });

        // Acción del botón Continuar
        continuarBtn.addActionListener((ActionEvent e) -> {
            String usuarioIngresado = usuarioField.getText();
            String contrasenaIngresada = new String(contrasenaField.getPassword());

            if (usuarioIngresado.equals(usuarioGuardado) && contrasenaIngresada.equals(contrasenaGuardada)) {
                loginFrame.dispose();
                mostrarPantallaPrincipal();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Usuario o Contraseña incorrectos.");
            }
        });

        // Añadir componentes a la ventana
        loginFrame.add(usuarioLabel);
        loginFrame.add(usuarioField);
        loginFrame.add(contrasenaLabel);
        loginFrame.add(contrasenaField);
        loginFrame.add(regionLabel);
        loginFrame.add(regionComboBox);
        loginFrame.add(rolLabel);
        loginFrame.add(rolComboBox);
        loginFrame.add(guardarBtn);
        loginFrame.add(continuarBtn);

        loginFrame.setVisible(true);
    }

    // Pantalla principal del sistema de gestión de inventarios
    private void mostrarPantallaPrincipal() {
        setTitle("Sistema de Gestión de Inventarios - " + rolSeleccionado + " - Región: " + regionSeleccionada);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear los componentes
        areaDisplay = new JTextArea(10, 40);
        areaDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaDisplay);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        nombreProductoField = new JTextField();
        categoriaField = new JTextField();
        precioField = new JTextField();
        cantidadField = new JTextField();

        inputPanel.add(new JLabel("Nombre del Producto:"));
        inputPanel.add(nombreProductoField);
        inputPanel.add(new JLabel("Categoría:"));
        inputPanel.add(categoriaField);
        inputPanel.add(new JLabel("Precio:"));
        inputPanel.add(precioField);
        inputPanel.add(new JLabel("Cantidad:"));
        inputPanel.add(cantidadField);

        // Botones
        JButton guardarProductoBtn = new JButton("Guardar Producto");
        JButton mostrarCategoriasBtn = new JButton("Mostrar Categorías");

        guardarProductoBtn.addActionListener((ActionEvent e) -> {
            guardarProducto();
        });

        mostrarCategoriasBtn.addActionListener((ActionEvent e) -> {
            mostrarCategorias();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(guardarProductoBtn);
        buttonPanel.add(mostrarCategoriasBtn);

        // Agregar componentes a la ventana
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos del sistema
    public void guardarProducto() {
        String nombre = nombreProductoField.getText();
        String categoria = categoriaField.getText();
        double precio = Double.parseDouble(precioField.getText());
        int cantidad = Integer.parseInt(cantidadField.getText());

        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }

        Producto producto = new Producto(nombre, categoria, precio, cantidad);
        productos.add(producto);
        areaDisplay.append("Producto guardado con éxito: " + nombre + "\n");

        // Limpiar los campos
        nombreProductoField.setText("");
        categoriaField.setText("");
        precioField.setText("");
        cantidadField.setText("");
    }

    public void mostrarCategorias() {
        if (categorias.isEmpty()) {
            areaDisplay.append("No hay categorías guardadas.\n");
        } else {
            areaDisplay.append("Categorías guardadas:\n");
            for (String categoria : categorias) {
                areaDisplay.append(categoria + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaGestionInventariosGUI().setVisible(true);
        });
    }
}
