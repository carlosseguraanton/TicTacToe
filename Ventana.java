package tictactoe;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class Ventana extends JFrame {

    public static final int ESCENARIO_MENU_PRINCIPAL = 0;
    public static final int ESCENARIO_MENU_PREVIO_AL_JUEGO = 1;
    public static final int ESCENARIO_JUEGO = 2;
    public static final int ESCENARIO_CREDITOS = 3;

    JButton[][] TableroGrafico = new JButton[3][3];

    final Juego juego = new Juego();

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    BufferedImage logo;
    Font FuenteDeTablero = null;

    Color colorJ1A = Color.WHITE;
    Color colorJ2A = new Color(0x990000);
    Color colorJ1B = Color.YELLOW;
    Color colorJ2B = new Color(0x11aaff);

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Selección del Look and feel ">
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }
        //</editor-fold>

        Ventana ventana = new Ventana();
    }

    public Ventana() {//<editor-fold defaultstate="collapsed" desc="Implementación del constructor »">
        initComponents();

        logo = Logo();
        try {
            BufferedImage icono = ImageIO.read(
                    new URL(
                            "https://docs.google.com/drawings/d/e/2PACX-1vRPEjrmk241Xa2jwhv1aK88A9IyfSoBTjTIwe"
                            + "ZHQWkrs5lAAYkeMAHY9oJX6VnaqCZCVtbl1znKGYLv/pub?w=200&h=200"
                    )
            );
            setIconImage(icono);
            BufferedImage logo = ImageIO.read(
                    new URL(
                            "https://docs.google.com/drawings/d/e/2PACX-1vQ4bebmHUan3es_6PxZWKJooQmno9L2aQeu-"
                            + "6ldc9_SnV8TOEBcqcjFIRsEmiXM9WQlVDkzglIg2Yaz/pub?w=220&h=220"
                    )
            );
            jButton18.setText("");
            AsignarImagenBoton(jButton18, logo);
            jButton19.setText("");
            AsignarImagenBoton(jButton19, icono);
        } catch (Exception e) {
        }
        try {
            Font fuente = fuenteCargadaDeInternet("http://webpagepublicity.com/free-fonts/v/VAG%20Round.ttf");
            AsignarFuentes(this, fuente);
        } catch (Exception e) {
        }

        {
            //<editor-fold defaultstate="collapsed" desc="Transparencia de los JTextField »">
            jTextField1.setBackground(new Color(0, true));
            jTextField2.setBackground(new Color(0, true));
            //</editor-fold>
        }
        {
            //<editor-fold defaultstate="collapsed" desc="Asignar colores interactivos de la fuente de los JButtons »">
            AsignarColorInteractivoFuenteJButton(jButton1, new Color(0x70FFAA));
            AsignarColorInteractivoFuenteJButton(jButton2, new Color(0xAC63FF));
            AsignarColorInteractivoFuenteJButton(jButton3, Color.PINK);

            JButton[] botonesExteriores = {jButton13, jButton14, jButton15, jButton16};
            for (JButton botonExterior : botonesExteriores) {
                AsignarColorInteractivoFuenteJButton(botonExterior, new Color(0xDFCDB9));
            }

            AsignarColorInteractivoFuenteJButton(jButton17, Color.LIGHT_GRAY);
            //</editor-fold>
        }

        OcultarPestanias();
        CargarTableroGrafico();
        DarTransparenciaALosJButtons(this);
        DarTransparenciaALosJPanels(this);
        OcultarFocos(this);

        setTitle("Tic tac toe - Carlos Segura Ant�n");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }//</editor-fold>

    void AsignarFuentes(Container c, Font f) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        for (Component component : c.getComponents()) {
            component.setFont(f.deriveFont((float) component.getFont().getSize()));
            if (component instanceof Container) {
                AsignarFuentes((Container) component, f);
            }
        }
    }//</editor-fold>

    private void OcultarFocos(Container c) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        for (Component component : c.getComponents()) {
            if (!(component instanceof JTextField)) {
                component.setFocusable(false);
            }
            if (component instanceof Container) {
                OcultarFocos((Container) component);
            }
        }
    }//</editor-fold>

    private void OcultarPestanias() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        jTabbedPane1.setUI(new BasicTabbedPaneUI() {
            @Override
            public void paint(Graphics grphcs, JComponent jc) {
            }

            @Override
            protected int calculateTabHeight(int i, int i1, int i2) {
                return 0;
            }

            @Override
            protected int calculateTabWidth(int i, int i1, FontMetrics fm) {
                return 0;
            }
        });
    }//</editor-fold>

    void CargarTableroGrafico() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        int c = 0;
        int f = 0;
        {
            //<editor-fold defaultstate="collapsed" desc="Cargar la fuente del tablero (de internet) »">
            try {
                FuenteDeTablero = fuenteCargadaDeInternet("http://webpagepublicity.com/free-fonts/t/TANTOR.ttf");
            } catch (Exception ex) {
                FuenteDeTablero = jLabel1.getFont();
            }
            //</editor-fold>
        }
        for (int i = 0; i < 9; i++) {
            final int columna = c;
            final int fila = f;
            TableroGrafico[c][f] = (JButton) jPanel13.getComponent(i);
            if (FuenteDeTablero != null) {
                //<editor-fold defaultstate="collapsed" desc="Cambiarle la fuente a los botones del tablero »">
                CambiarFuenteJButton(TableroGrafico[c][f],
                        FuenteDeTablero
                );
                //</editor-fold>
            }
            TableroGrafico[c][f].addActionListener((ActionEvent ae) -> {
                //<editor-fold defaultstate="collapsed" desc="Acción de cada botón »">
                if (juego.JuegoTerminado()) {
                    jButton14ActionPerformed(null);
                    return;
                }
                int ganadorPrev = juego.Ganador;
                juego.registrarJugada(columna, fila);
                int ganadorSeg = juego.Ganador;
                if (ganadorPrev != ganadorSeg) {
                    //<editor-fold defaultstate="collapsed" desc="Aumentar el marcador del ganador »">
                    if (ganadorSeg == 1) {
                        jugador1.Marcador++;
                    } else {
                        jugador2.Marcador++;
                    }
                    SincronizarMarcador();
                    //</editor-fold>
                }
                SincronizarTablero();
                //</editor-fold>
            });
            {
                //<editor-fold defaultstate="collapsed" desc="Llevar control de las posiciones de los botones en el tablero »">
                c++;
                if (c == 3) {
                    c = 0;
                    f++;
                }
                //</editor-fold>
            }
        }
    }//</editor-fold>

    void SincronizarMarcador() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        jLabel8.setText(
                jugador1.Nombre + ": " + jugador1.Marcador
                + " - "
                + jugador2.Nombre + ": " + jugador2.Marcador
        );
    }//</editor-fold>

    void SincronizarTablero() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        for (int c = 0; c < 3; c++) {
            for (int f = 0; f < 3; f++) {
                switch (juego.Tablero[c][f]) {
                    case 0:
                        TableroGrafico[c][f].setText("");
                        break;
                    case 1:
                        TableroGrafico[c][f].setText("X");
                        TableroGrafico[c][f].setForeground(jugador1.color);
                        break;
                    case 2:
                        TableroGrafico[c][f].setText("O");
                        TableroGrafico[c][f].setForeground(jugador2.color);
                        break;
                }
            }
        }
    }//</editor-fold>

    void DarTransparenciaALosJPanels(Container c) {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        for (Component component : c.getComponents()) {
            if (component instanceof JPanel) {
                ((JPanel) component).setOpaque(false);
            }
            if (component instanceof Container) {
                DarTransparenciaALosJPanels((Container) component);
            }
        }
    }//</editor-fold>

    void DarTransparenciaALosJButtons(Container c) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        for (Component component : c.getComponents()) {
            if (component instanceof JButton) {
                //<editor-fold defaultstate="collapsed" desc="Ocultar las propiedades gráficas del botón »">
                JButton btn = ((JButton) component);
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.setContentAreaFilled(false);
                //</editor-fold>
            }
            if (component instanceof Container) {
                DarTransparenciaALosJButtons((Container) component);
            }
        }
    }//</editor-fold>

    JLabel marcador() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        return new JLabel() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                g.setColor(new Color(0xAE7F3E));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(0xE8B973));
                g.fillRect(0, 0, getWidth(), 10);
                super.paint(grphcs);
            }
        };
    }//</editor-fold>

    JPanel panelFondo() {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        return new JPanel() {
            BufferedImage patronFondo = PatronLadrillos(Color.BLACK);

            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintar el degradado de fondo »">
                    g.setPaint(
                            new GradientPaint(
                                    0, 0, new Color(0x5A4544),
                                    0, getHeight(),
                                    new Color(0x8C7C6F)
                            )
                    );
                    g.fillRect(0, 0, getWidth(), getHeight());
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintar la textura de ladrillo »">
                    g.setPaint(
                            new TexturePaint(
                                    patronFondo,
                                    new Rectangle2D.Double(
                                            0, 0,
                                            patronFondo.getWidth() * .5, patronFondo.getHeight() * .4
                                    )
                            )
                    );
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
                    g.fillRect(0, 0, getWidth(), getHeight());
                    //</editor-fold>
                }
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                super.paint(grphcs);
            }
        };
    }//</editor-fold>

    JPanel panelContenedor() {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        return new JPanel() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintado de la parte interna »">
                    g.setPaint(
                            new GradientPaint(
                                    0, 0,
                                    new Color(0x505E65),
                                    0, getHeight(),
                                    new Color(0x1A2C32)
                            )
                    );
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 80, 80);
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Dibujado de la parte externa »">
                    g.setStroke(new BasicStroke(10));
                    g.setPaint(
                            new GradientPaint(
                                    0, 0,
                                    new Color(0x754929),
                                    0, getHeight(),
                                    new Color(0xB66F11)
                            )
                    );
                    g.drawRoundRect(4, 4, getWidth() - 8, getHeight() - 6, 80, 80);
                    //</editor-fold>
                }
                super.paint(grphcs);
            }

        };
    }//</editor-fold>

    JPanel panelTablero() {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        return new JPanel() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintado de la parte interna »">
                    g.setPaint(
                            new GradientPaint(
                                    0, 0,
                                    new Color(0x505E65),
                                    0, getHeight(),
                                    new Color(0x1A2C32)
                            )
                    );
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 80, 80);
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Dibujado de la parte externa »">
                    g.setStroke(new BasicStroke(10));
                    g.setPaint(
                            new GradientPaint(
                                    0, 0,
                                    new Color(0x754929),
                                    0, getHeight(),
                                    new Color(0xB66F11)
                            )
                    );
                    g.drawRoundRect(4, 4, getWidth() - 8, getHeight() - 6, 80, 80);
                    //</editor-fold>
                }
                double delta = .08;
                int distanciaDivicionesVerticales = getWidth() / 3;
                int distanciaDivicionesHorizontales = getHeight() / 3;

                {
                    //<editor-fold defaultstate="collapsed" desc="Dibujar las divisiones »">
                    g.setColor(Color.WHITE);
                    g.setStroke(
                            new BasicStroke(
                                    5,
                                    BasicStroke.CAP_ROUND,
                                    BasicStroke.JOIN_ROUND,
                                    10,
                                    new float[]{10, 10},
                                    0
                            )
                    );

                    for (int i = 1; i <= 2; i++) {
                        g.drawLine(
                                (int) (getWidth() * (delta)),
                                i * distanciaDivicionesHorizontales,
                                (int) (getWidth() * (1 - delta)),
                                i * distanciaDivicionesHorizontales
                        );
                    }
                    for (int i = 1; i <= 2; i++) {
                        g.drawLine(
                                i * distanciaDivicionesVerticales,
                                (int) (getHeight() * (delta)),
                                i * distanciaDivicionesVerticales,
                                (int) (getHeight() * (1 - delta))
                        );
                    }
                    //</editor-fold>
                }
                super.paint(grphcs);

                g.setColor(Color.WHITE);

                if (juego.Ganador != 0) {
                    switch (juego.Linea_Terminar) {
                        //<editor-fold defaultstate="collapsed" desc="Dibujado de las líneas de fin de juego »">
                        case Juego.TERMINAR_COL1:
                        case Juego.TERMINAR_COL2:
                        case Juego.TERMINAR_COL3:
                            //<editor-fold defaultstate="collapsed" desc="Dibujado de la línea de las columnas »">
                            int x = (int) ((juego.Linea_Terminar + .5) * distanciaDivicionesVerticales);
                            g.drawLine(
                                    x,
                                    (int) (getHeight() * (delta)),
                                    x,
                                    (int) (getHeight() * (1 - delta))
                            );
                            //</editor-fold>
                            break;
                        case Juego.TERMINAR_FIL1:
                        case Juego.TERMINAR_FIL2:
                        case Juego.TERMINAR_FIL3:
                            //<editor-fold defaultstate="collapsed" desc="Dibujado de la línea de las filas »">
                            int y = (int) (((juego.Linea_Terminar - 3) + .5) * distanciaDivicionesHorizontales);
                            g.drawLine(
                                    (int) (getWidth() * (delta)),
                                    y,
                                    (int) (getWidth() * (1 - delta)),
                                    y
                            );
                            //</editor-fold>
                            break;
                        case Juego.TERMINAR_DIAG1: {
                            //<editor-fold defaultstate="collapsed" desc="Dibujado de la línea de la diagonal principal »">
                            int x1 = distanciaDivicionesVerticales / 2;
                            int y1 = distanciaDivicionesHorizontales / 2;
                            int x2 = getWidth() - distanciaDivicionesVerticales / 2;
                            int y2 = getHeight() - distanciaDivicionesHorizontales / 2;
                            g.drawLine(
                                    x1, y1,
                                    x2, y2
                            );
                            //</editor-fold>
                        }
                        break;
                        case Juego.TERMINAR_DIAG2:
                            //<editor-fold defaultstate="collapsed" desc="Dibujado de la línea de la diagonal secundaria »">
                            int x1 = getWidth() - distanciaDivicionesVerticales / 2;
                            int y1 = distanciaDivicionesHorizontales / 2;
                            int x2 = distanciaDivicionesVerticales / 2;
                            int y2 = getHeight() - distanciaDivicionesHorizontales / 2;
                            g.drawLine(
                                    x1, y1,
                                    x2, y2
                            );
                            //</editor-fold>
                            break;
                        //</editor-fold>
                    }
                    SwingUtilities.updateComponentTreeUI(jPanel13);
                }
            }

        };
    }//</editor-fold>

    JLabel PresentarLogo() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        return new JLabel() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                {
                    //<editor-fold defaultstate="collapsed" desc="Suavizar la imagen al redimensionarla">
                    g.setRenderingHint(
                            RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR
                    );
                    //</editor-fold>
                }
                double proporcion = 1;
                {
                    //<editor-fold defaultstate="collapsed" desc="Cálculo de la proporción para la redimensión »">
                    if (getWidth() < getHeight()) {
                        proporcion = (double) getWidth() / logo.getWidth();
                        if (logo.getHeight() * proporcion > getHeight()) {
                            proporcion = (double) getHeight() / logo.getHeight();
                        }
                    } else {
                        proporcion = (double) getHeight() / logo.getHeight();
                        if (logo.getWidth() * proporcion > getWidth()) {
                            proporcion = (double) getWidth() / logo.getWidth();
                        }
                    }
                    //</editor-fold>
                }
                double x;
                double y;
                {
                    //<editor-fold defaultstate="collapsed" desc="Cálculo de la posición »">
                    double w = logo.getWidth() * proporcion;
                    double h = logo.getHeight() * proporcion;
                    x = (getWidth() - w) / 2;
                    y = (getHeight() - h) / 2;
                    //</editor-fold>
                }
                AffineTransform at = new AffineTransform(
                        proporcion, 0,
                        0, proporcion,
                        x, y
                );
                g.drawImage(logo, at, null);
            }
        };
    }//</editor-fold>

    JRadioButton RaddioButtonDePrimeraJugada() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        return new JRadioButton() {
            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                {
                    //<editor-fold defaultstate="collapsed" desc="Suavizado del borde del texto »">
                    g.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON
                    );
                    //</editor-fold>
                }
                if (isSelected()) {
                    //<editor-fold defaultstate="collapsed" desc="Dibujado del rectángulo cuando está seleccionado »">
                    g.setColor(Color.WHITE);
                    g.setStroke(new BasicStroke(6));
                    g.drawRect(0, 0, getWidth(), getHeight());
                    //</editor-fold>
                }
                g.setFont(FuenteDeTablero.deriveFont(60f));
                FontMetrics fm = g.getFontMetrics();
                String txt = getText();
                Shape figura;
                {
                    //<editor-fold defaultstate="collapsed" desc="Generar el shape del texto »">
                    int x = (getWidth() - fm.stringWidth(txt)) / 2;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    TextLayout Silueta = new TextLayout(txt, g.getFont(), g.getFontRenderContext());
                    figura = Silueta.getOutline(AffineTransform.getTranslateInstance(x, y));
                    //</editor-fold>
                }
                g.fill(figura);
            }
        };
    }//</editor-fold>

    JRadioButton RaddioButtonDeTema(String Tipo) {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        return new JRadioButton() {
            BufferedImage icono;

            {
                switch (Tipo) {
                    //<editor-fold defaultstate="collapsed" desc="Generación del icono según el tipo »">
                    case "A":
                        //<editor-fold defaultstate="collapsed" desc="Generación del icono tipo A »">
                        icono = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB) {
                            {
                                Graphics2D g = createGraphics();
                                g.setColor(colorJ1A);
                                g.fillRect(0, 0, getWidth() / 2, getHeight());
                                g.setColor(colorJ2A);
                                g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
                            }
                        };
                        //</editor-fold>
                        break;
                    case "B":
                        //<editor-fold defaultstate="collapsed" desc="Generación del icono tipo B »">
                        icono = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB) {
                            {
                                Graphics2D g = createGraphics();
                                g.setColor(colorJ1B);
                                g.fillRect(0, 0, getWidth() / 2, getHeight());
                                g.setColor(colorJ2B);
                                g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
                            }
                        };
                        //</editor-fold>
                        break;
                    case "C":
                        //<editor-fold defaultstate="collapsed" desc="Generación del icono tipo C »">
                        icono = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB) {
                            {
                                Graphics2D g = createGraphics();
                                int wc = getWidth() / 3;
                                int hc = getHeight() / 3;
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 3; j++) {
                                        g.setColor(new Color(new Random().nextInt()));
                                        g.fillRect(i * wc, j * hc, wc, hc);
                                    }
                                }
                            }
                        };
                        //</editor-fold>
                        break;
                    //</editor-fold>
                }
            }

            @Override
            public void paint(Graphics grphcs) {
                Graphics2D g = (Graphics2D) grphcs;
                if (isSelected()) {
                    //<editor-fold defaultstate="collapsed" desc="Dibujado del rectángulo cuando está seleccinado »">
                    g.setColor(Color.WHITE);
                    g.setStroke(new BasicStroke(6));
                    g.drawRect(0, 0, getWidth(), getHeight());
                    //</editor-fold>
                }
                int x = (getWidth() - icono.getWidth()) / 2;
                int y = (getHeight() - icono.getHeight()) / 2;
                g.drawImage(icono, x, y, null);
            }
        };
    }//</editor-fold>

    static Font fuenteCargadaDeInternet(String ruta) throws Exception {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        URL url = new URL(ruta);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (Exception e) {
            return Font.createFont(Font.TYPE1_FONT, url.openStream());
        }
    }//</editor-fold>

    static void CambiarFuenteJButton(JButton btn, Font ft) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        try {
            float sz = btn.getFont().getSize();
            ft = ft.deriveFont(sz);
            btn.setFont(ft);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }//</editor-fold>

    static void AsignarColorInteractivoFuenteJButton(JButton btn, Color color) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        btn.setForeground(color);

        Color colorEncima;
        {
            //<editor-fold defaultstate="collapsed" desc="Cálculo del color aclarado »">
            int r = color.getRed() + 100;
            r = r > 255 ? 255 : r;
            int g = color.getGreen() + 100;
            g = g > 255 ? 255 : g;
            int b = color.getBlue() + 100;
            b = b > 255 ? 255 : b;
            colorEncima = new Color(r, g, b);
            //</editor-fold>
        }
        Color colorPresionado;
        {
            //<editor-fold defaultstate="collapsed" desc="Cálculo del color oscurecido »">
            int r = color.getRed() - 100;
            r = r < 0 ? 0 : r;
            int g = color.getGreen() - 100;
            g = g < 0 ? 0 : g;
            int b = color.getBlue() - 100;
            b = b < 0 ? 0 : b;
            colorPresionado = new Color(r, g, b);
            //</editor-fold>
        }
        btn.addMouseListener(new MouseListener() {
            //<editor-fold defaultstate="collapsed" desc="Generador del efecto de cambio de color de fuente »">
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                btn.setForeground(colorPresionado);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.getX() > btn.getWidth() || me.getY() > btn.getHeight() || me.getX() < 0 || me.getY() < 0) {
                    btn.setForeground(color);
                } else {
                    btn.setForeground(colorEncima);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                btn.setForeground(colorEncima);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                btn.setForeground(color);
            }
            //</editor-fold>
        });
    }//</editor-fold>

    static BufferedImage PatronLadrillos(Color color) {//<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        return new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB) {
            {
                Graphics2D g = createGraphics();
                g.setColor(color);
                g.setStroke(new BasicStroke(10));
                g.drawRect(0, 0, getWidth(), getHeight() / 2);
                g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
                g.drawRect(0, getHeight(), getWidth(), 1);
            }
        };
    }//</editor-fold>

    void AsignarImagenBoton(JButton btn, BufferedImage img) {
        btn.setIcon(new ImageIcon(img));
        btn.setPressedIcon(new ImageIcon(new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB) {
            {
                Graphics2D g = createGraphics();
                g.drawImage(img, 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, .2f));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }));
        btn.setRolloverIcon(new ImageIcon(new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB) {
            {
                Graphics2D g = createGraphics();
                g.drawImage(img, 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, .2f));
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }));
    }

    BufferedImage Logo() {//<editor-fold defaultstate="collapsed" desc="Implementación de código">
        String txt = "Tic Tac Toe #";

        Font fuenteCargada = null;
        {
            //<editor-fold defaultstate="collapsed" desc="Cargar la fuente »">
            try {
                fuenteCargada = fuenteCargadaDeInternet(
                        "http://webpagepublicity.com/free-fonts/w/Wobbles.ttf"
                );
            } catch (Exception e) {
                fuenteCargada = jLabel1.getFont();
            }
            fuenteCargada = fuenteCargada.deriveFont(90f).deriveFont(Font.BOLD);
            //</editor-fold>
        }

        final Font fuenteLogo = fuenteCargada;

        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(fuenteLogo);
        int borde = 27;

        return new BufferedImage(
                fm.stringWidth(txt) + borde, fm.getHeight() + borde, BufferedImage.TYPE_INT_ARGB
        ) {//<editor-fold defaultstate="collapsed" desc="Generar el gráfico del logo »">
            {
                Graphics2D g = createGraphics();
                {
                    //<editor-fold defaultstate="collapsed" desc="Asignar las propiedades iniciales al Graphics2D »">
                    g.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON
                    );
                    g.setFont(fuenteLogo);
                    //</editor-fold>
                }
                Shape figura;
                {
                    //<editor-fold defaultstate="collapsed" desc="Generar el shape del texto »">
                    int x = (getWidth() - fm.stringWidth(txt)) / 2;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() + fm.getDescent();
                    TextLayout Silueta = new TextLayout(txt, fuenteLogo, g.getFontRenderContext());
                    figura = Silueta.getOutline(AffineTransform.getTranslateInstance(x, y));
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintar la sombra »">
                    int k = 12;
                    g.translate(k, k);
                    g.setColor(Color.BLACK);
                    g.fill(figura);
                    g.translate(-k, -k);
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Dibujar el borde »">
                    g.setStroke(new BasicStroke(15));
                    g.setColor(Color.WHITE);
                    g.draw(figura);
                    //</editor-fold>
                }
                {
                    //<editor-fold defaultstate="collapsed" desc="Pintar el texto »">
                    g.setPaint(
                            new GradientPaint(
                                    0, (getHeight() - fm.getHeight()) / 2,
                                    new Color(0xE5D88E),
                                    0, (getHeight() - fm.getHeight()) / 2 + (fm.getHeight()) / 2,
                                    new Color(0xDEAB38),
                                    true
                            )
                    );
                    g.fill(figura);
                    //</editor-fold>
                }
            }
        };//</editor-fold>

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Código generado con ayuda de Netbeans para la GUI">
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = panelFondo();
        jPanel4 = panelContenedor();
        jLabel1 = PresentarLogo();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = panelFondo();
        jPanel5 = panelContenedor();
        jLabel2 =  PresentarLogo();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jRadioButton1 = RaddioButtonDePrimeraJugada();
        jRadioButton2 = RaddioButtonDePrimeraJugada();
        jRadioButton3 = RaddioButtonDePrimeraJugada();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jRadioButton4 = RaddioButtonDeTema("A");
        jRadioButton5 = RaddioButtonDeTema("B");
        jRadioButton6 = RaddioButtonDeTema("C");
        jButton17 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel3 = panelFondo();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = panelTablero();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel7 =  PresentarLogo();
        jLabel8 = marcador();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel14 = panelFondo();
        jButton16 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setLayout(new java.awt.GridLayout(4, 1));

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Logo del juego");
        jPanel4.add(jLabel1);

        jButton1.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        jButton1.setText("Nuevo juego");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton2.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        jButton2.setText("Créditos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jButton3.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        jButton3.setText("Salir del juego");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addGap(159, 159, 159))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGap(108, 108, 108))
        );

        jTabbedPane1.addTab("Menú principal", jPanel1);

        jPanel5.setLayout(new java.awt.GridLayout(6, 1, 10, 0));

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Logo del juego");
        jPanel5.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Jugador 1:");

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("J1");
        jTextField1.setBorder(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );

        jPanel5.add(jPanel6);

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Jugador 2:");

        jTextField2.setBackground(new java.awt.Color(0, 0, 0));
        jTextField2.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("J2");
        jTextField2.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2))
                .addContainerGap())
        );

        jPanel5.add(jPanel7);

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Primer movimiento");

        jPanel10.setLayout(new java.awt.GridLayout(1, 3));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("X");
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton2.setText("O");
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton3.setText("XO");
        jRadioButton3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel10.add(jRadioButton3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.add(jPanel8);

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Tema");

        jPanel11.setLayout(new java.awt.GridLayout(1, 3));

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton4.setText("A");
        jRadioButton4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel11.add(jRadioButton4);

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton5.setText("B");
        jRadioButton5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        jPanel11.add(jRadioButton5);

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jRadioButton6.setSelected(true);
        jRadioButton6.setText("C");
        jRadioButton6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        jPanel11.add(jRadioButton6);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.add(jPanel9);

        jButton17.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jButton17.setText("Empezar a jugar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton17);

        jButton15.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jButton15.setText("Volver");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(96, 96, 96))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton15)
                .addGap(25, 25, 25)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(58, 58, 58))
        );

        jTabbedPane1.addTab("Menú previo al juego", jPanel2);

        jPanel12.setBorder(null);

        jPanel13.setLayout(new java.awt.GridLayout(3, 3, 5, 5));

        jButton4.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton4.setText("X");
        jPanel13.add(jButton4);

        jButton5.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton5.setText("O");
        jPanel13.add(jButton5);

        jButton6.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton6.setText("O");
        jPanel13.add(jButton6);

        jButton7.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton7.setText("O");
        jPanel13.add(jButton7);

        jButton8.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton8.setText("X");
        jPanel13.add(jButton8);

        jButton9.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jPanel13.add(jButton9);

        jButton10.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton10.setText("X");
        jPanel13.add(jButton10);

        jButton11.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jPanel13.add(jButton11);

        jButton12.setFont(new java.awt.Font("sansserif", 0, 60)); // NOI18N
        jButton12.setText("X");
        jPanel13.add(jButton12);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Logo del juego");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(78, 56, 39));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Marcador");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton13.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jButton13.setText("Volver");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jButton14.setText("Nuevo");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton14)
                            .addComponent(jButton13))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Juego", jPanel3);

        jButton16.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jButton16.setText("Volver");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton18.setText("jButton18");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel9.setText("Texto de presentación");
        jLabel9.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel9ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton19.setText("jButton19");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel10.setText("Texto de presentación");
        jLabel10.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel10ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Créditos", jPanel14);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void CambiarEscenario(int i) {
        jTabbedPane1.setSelectedIndex(i);
    }

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed

    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CambiarEscenario(ESCENARIO_MENU_PREVIO_AL_JUEGO);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        CambiarEscenario(ESCENARIO_MENU_PRINCIPAL);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        CambiarEscenario(ESCENARIO_MENU_PREVIO_AL_JUEGO);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        CambiarEscenario(ESCENARIO_MENU_PRINCIPAL);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        juego.reiniciarJuego();
        jugador1.Nombre = jTextField1.getText().trim();
        jugador2.Nombre = jTextField2.getText().trim();
        {
            if (jRadioButton1.isSelected()) {
                juego.Jugador = 1;
            }
            if (jRadioButton2.isSelected()) {
                juego.Jugador = 2;
            }
            if (jRadioButton3.isSelected()) {
                juego.Jugador = new Random().nextInt(2) + 1;
            }
        }
        {
            if (jRadioButton4.isSelected()) {
                jugador1.color = colorJ1A;
                jugador2.color = colorJ2A;
            }
            if (jRadioButton5.isSelected()) {
                jugador1.color = colorJ1B;
                jugador2.color = colorJ2B;
            }
            if (jRadioButton6.isSelected()) {
                jugador1.color = new Color(new Random().nextInt());
                jugador2.color = new Color(new Random().nextInt());
            }
        }
        SincronizarMarcador();
        SincronizarTablero();
        CambiarEscenario(ESCENARIO_JUEGO);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CambiarEscenario(ESCENARIO_CREDITOS);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        juego.reiniciarJuego();
        {
            if (jRadioButton1.isSelected()) {
                juego.Jugador = 1;
            }
            if (jRadioButton2.isSelected()) {
                juego.Jugador = 2;
            }
            if (jRadioButton3.isSelected()) {
                juego.Jugador = new Random().nextInt(2) + 1;
            }
        }
        SincronizarTablero();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed

    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jLabel9ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel9ComponentResized
        //<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        jLabel9.setText(
                "<html>"
                + "<table style=\"width: " + jLabel9.getWidth() * .75 + "px;\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td>\n"
                + "<p style=\"font-size:40px;color: #ffcc00;\">Bienvenido</p>\n"
                + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td>\n"
                + "<h1><span style=\"color: #ffffff;\">"
                + "Soy un desarrollador Independiente en mis ratos libres, "
                + "grabo como ayuda para explicar este mundillo de algoritmos a todo aquel que "
                + "sienta curiosidad."
                + "</span></h1>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>"
        );
        //</editor-fold>
    }//GEN-LAST:event_jLabel9ComponentResized

    private void jLabel10ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel10ComponentResized
        //<editor-fold defaultstate="collapsed" desc="Implementación del código »">
        jLabel10.setText(
                "<html>"
                + "<table style=\"width: " + jLabel10.getWidth() * .75 + "px;\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td>\n"
                + "<p style=\"font-size:40px;color: #99ee00;\">Tic Tac Toe</p>\n"
                + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td>\n"
                + "<h1><span style=\"color: #ffffff;\">"
                + "Software de código abierto desarrollado en Java y documentado en "
                + "una serie de videos para YouTube donde se explica paso a paso el desarrollo del código"
                + "</span></h1>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>"
        );
        //</editor-fold>
    }//GEN-LAST:event_jLabel10ComponentResized

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UC1zgh-6xm_a-PI49zb6e5HA"));
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/playlist?list=PLHijG0kpWet0xu3AfPTdZJiF_Bg1gmqEA"));
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    //<editor-fold defaultstate="collapsed" desc="Declaración de las variables usadas por Netbeans para la GUI »">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    //</editor-fold>

}
