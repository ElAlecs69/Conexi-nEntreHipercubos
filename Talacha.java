package ConexiónDeHipercubos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.QuadCurve2D;
import java.util.concurrent.Semaphore;

public class Talacha extends JPanel implements Hipercubo {
	
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private List<Nodos> nodos;
    private List<String> camino1;
    private List<String> camino2;
    private JTextField itxt1;
    private JTextField ftxt1;
    private JTextField itxt2;
    private JTextField ftxt2;

    private String lbl = "";

    public Talacha() {
    	
        nodos = new ArrayList<>();
        camino1 = new ArrayList<>();
        camino2 = new ArrayList<>();

        // Nombre y Coordenadas de los nodos
        nodos.add(new Nodos("0100", 200, 200));
        nodos.add(new Nodos("0110", 400, 200));
        nodos.add(new Nodos("0000", 100, 300));
        nodos.add(new Nodos("0010", 300, 300));
        nodos.add(new Nodos("0101", 200, 400));
        nodos.add(new Nodos("0111", 400, 400));
        nodos.add(new Nodos("0001", 100, 500));
        nodos.add(new Nodos("0011", 300, 500));

        nodos.add(new Nodos("1100", 600, 200));
        nodos.add(new Nodos("1110", 800, 200));
        nodos.add(new Nodos("1000", 500, 300));
        nodos.add(new Nodos("1010", 700, 300));
        nodos.add(new Nodos("1101", 600, 400));
        nodos.add(new Nodos("1111", 800, 400));
        nodos.add(new Nodos("1001", 500, 500));
        nodos.add(new Nodos("1011", 700, 500));

        itxt1 = new JTextField(5);
        ftxt1 = new JTextField(5);
        itxt2 = new JTextField(5);
        ftxt2 = new JTextField(5);

        JButton findPathButton = new JButton("Iniciar");
        findPathButton.addActionListener(new ActionListener() {
            Semaphore semaforo1 = new Semaphore(1);

            @Override
            public void actionPerformed(ActionEvent e) {
                Thread hilo1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            camino1.clear();
                            encontrarCamino(itxt1.getText(), ftxt1.getText(), semaforo1, camino1, "Camino 1", 1);
                            repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }
                });

                Thread hilo2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            camino2.clear();
                            encontrarCamino(itxt2.getText(), ftxt2.getText(), semaforo1, camino2, "Camino 2", 2);
                            repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        repaint();
                    }
                });

                hilo1.start();
                hilo2.start();
            }
        });

        setLayout(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(10, 550, 1000, 500);

        JLabel logo1 = new JLabel();
        logo1.setIcon(new ImageIcon("Imagenes/logouaem.jpg"));
        logo1.setBounds(10,10,128,116);
        add(logo1);

        JLabel logo2 = new JLabel();
        logo2.setIcon(new ImageIcon("Imagenes/logofi.jpg"));
        logo2.setBounds(850,10,120,112);
        add(logo2);

        JLabel logo3 = new JLabel();
        logo3.setIcon(new ImageIcon("Imagenes/logouaemgrande.jpeg"));
        logo3.setBounds(30,610,922,166);
        add(logo3);
        
        JLabel leyenda1 = new JLabel("HILO 1");
        leyenda1.setBounds(20, 10, 120, 25);
        inputPanel.add(leyenda1);
        leyenda1.setFont(new Font("", Font.HANGING_BASELINE, 15));
        
        JLabel CuadroAmarillo = new JLabel("■");
        CuadroAmarillo.setBounds(80, 10, 120, 25);
        CuadroAmarillo.setForeground(Color.YELLOW);        
        inputPanel.add(CuadroAmarillo);
        
        JLabel leyenda2 = new JLabel("HILO 2");
        leyenda2.setBounds(20, 50, 120, 25);
        inputPanel.add(leyenda2);
        leyenda2.setFont(new Font("", Font.HANGING_BASELINE, 15));
        
        JLabel CuadroVerde = new JLabel("■");
        CuadroVerde.setBounds(80, 50, 120, 25);
        CuadroVerde.setForeground(Color.GREEN);
        inputPanel.add(CuadroVerde);
        
        JLabel lbl1 = new JLabel("NODO INICIAL 1:");
        lbl1.setBounds(20, 100, 120, 25);
        inputPanel.add(lbl1);
        itxt1.setBounds(140, 100, 50, 25);
        inputPanel.add(itxt1);

        JLabel lbl2 = new JLabel("NODO DESTINO 1:");
        lbl2.setBounds(200, 100, 120, 25);
        inputPanel.add(lbl2);
        ftxt1.setBounds(330, 100, 50, 25);
        inputPanel.add(ftxt1);

        JLabel lbl3 = new JLabel("NODO INICIAL 2:");
        lbl3.setBounds(390, 100, 120, 25);
        inputPanel.add(lbl3);
        itxt2.setBounds(520, 100, 50, 25);
        inputPanel.add(itxt2);

        JLabel lbl4 = new JLabel("NODO DESTINO 2:");
        lbl4.setBounds(580, 100, 120, 25);
        inputPanel.add(lbl4);
        ftxt2.setBounds(710, 100, 50, 25);
        inputPanel.add(ftxt2);

        findPathButton.setBounds(810, 80, 120, 50);
        findPathButton.setFont(new Font("", Font.BOLD, 15));
        inputPanel.add(findPathButton);

        add(inputPanel);

    }

    @Override
    public void Ventana() {
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(1000, 750); 
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6));

        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("CONEXIÓN ENTRE NODOS DE HIPERCUBOS", 40, 60);
        g2.setColor(Color.red);
        g2.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
        g2.drawString("Ingrese un nodo de partida y un nodo final, puede elegir dos. ", 210, 120);

        g2.setColor(Color.CYAN);
        
        g2.drawString(lbl, 200, 650);

        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

        g2.setColor(Color.black);

        g2.drawRect(200, 200, 200, 200);
        g2.drawRect(100, 300, 200, 200);
        g2.drawLine(200, 200, 100, 300);
        g2.drawLine(400, 200, 300, 300);
        g2.drawLine(200, 400, 100, 500);
        g2.drawLine(400, 400, 300, 500);
        g2.drawRect(600, 200, 200, 200);
        g2.drawRect(500, 300, 200, 200);
        g2.drawLine(600, 200, 500, 300);
        g2.drawLine(800, 200, 700, 300);
        g2.drawLine(600, 400, 500, 500);
        g2.drawLine(800, 400, 700, 500);

        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

        for (Nodos nodo : nodos) {
            g2.setColor(Color.orange);
            g2.drawString(nodo.getIdInt(), nodo.getX() + 35, nodo.getY() - 10);
            g2.fillOval(nodo.getX() - 5, nodo.getY() - 5, 10, 10);
        }

        dibujarCamino(g2, camino2, Color.GREEN);
        dibujarCamino(g2, camino1, Color.YELLOW);

        g2.setColor(Color.BLACK);
    }

    private void dibujarCamino(Graphics2D g2, List<String> camino, Color color) {
        if (!camino.isEmpty()) {
            Nodos prevNodo = null;
            for (String nodeId : camino) {
                Nodos nodo = nodos.stream().filter(n -> n.getId().equals(nodeId)).findFirst().orElse(null);
                if (nodo != null) {
                    if (prevNodo != null) {
                        boolean sameEdgeInOtherCamino = (camino1.contains(prevNodo.getId()) && camino1.contains(nodo.getId())) 
                                                       && (camino2.contains(prevNodo.getId()) && camino2.contains(nodo.getId()));

                        // Dibuja la curva o línea para representar el camino
                        if (prevNodo.getId().charAt(0) != nodo.getId().charAt(0)) {
                            // Dibuja la curva
                            double midX = (prevNodo.getX() + nodo.getX()) / 2;
                            double midY = Math.min(prevNodo.getY(), nodo.getY()) - 100;
                            QuadCurve2D q = new QuadCurve2D.Float();
                            q.setCurve(prevNodo.getX(), prevNodo.getY() + (color == Color.RED ? 10 : 0), midX, midY + (color == Color.RED ? 10 : 0), nodo.getX(), nodo.getY() + (color == Color.RED ? 10 : 0));
                            g2.setColor(color);
                            g2.draw(q);

                            // Dibuja en color magenta si la arista es compartida
                            if (sameEdgeInOtherCamino) {
                                g2.setColor(Color.MAGENTA);
                                g2.draw(q);  // Dibuja nuevamente en el nuevo color
                            }
                        } else {
                            // Dibuja la línea normal
                            g2.setColor(color);
                            g2.drawLine(prevNodo.getX(), prevNodo.getY() + (color == Color.RED ? 10 : 0), nodo.getX(), nodo.getY() + (color == Color.RED ? 10 : 0));

                            // Dibuja en color magenta si la arista es compartida
                            if (sameEdgeInOtherCamino) {
                                g2.setColor(Color.MAGENTA);
                                g2.drawLine(prevNodo.getX(), prevNodo.getY(), nodo.getX(), nodo.getY());
                            }
                        }
                    }
                    prevNodo = nodo; // Actualiza el nodo previo
                }
            }
        }
    }

    public void encontrarCamino(String inicioInt, String finInt, Semaphore semaforo, List<String> camino, String nombre, int Hilo) throws InterruptedException {

    	if (inicioInt.isEmpty() && finInt.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar al menos un NODO INICIAL " + Hilo + " y un NODO DESTINO " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    	if(inicioInt.isEmpty() && !finInt.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Debe ingresar un NODO INICIAL " + Hilo + " para poder hacer la conexión " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
    	}
    	
    	if(!inicioInt.isEmpty() && finInt.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Debe ingresar un NODO DESTINO " + Hilo + " para poder hacer la conexión " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
    	}
    	
        // Verificar el nodo inicial
        int InicioInt = -1;  // Valor por defecto
        boolean validInicio = true; // Para controlar si el nodo inicial es válido

        if (!inicioInt.isEmpty()) {
            try {
                InicioInt = Integer.parseInt(inicioInt);
                if (InicioInt < 0 || InicioInt > 16) {
                    validInicio = false;
                    JOptionPane.showMessageDialog(null, "Debe ingresar un NODO INICIAL " + Hilo + " válido (entre 0 y 16)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                validInicio = false;
                JOptionPane.showMessageDialog(null, "Debe ingresar valores numéricos enteros válidos para el NODO INICIAL " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Verificar el nodo destino
        int FinInt = -1;  // Valor por defecto
        boolean validFin = true; // Para controlar si el nodo destino es válido

        if (!finInt.isEmpty()) {
            try {
                FinInt = Integer.parseInt(finInt);
                if (FinInt < 0 || FinInt > 16) {
                    validFin = false;
                    JOptionPane.showMessageDialog(null, "Debe ingresar un NODO DESTINO " + Hilo + " válido (entre 0 y 16)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                validFin = false;
                JOptionPane.showMessageDialog(null, "Debe ingresar valores numéricos enteros válidos para el NODO DESTINO " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Si ambos nodos son válidos, continuar
        if (validInicio && validFin) {
            if (InicioInt == FinInt) {
                JOptionPane.showMessageDialog(null, "El NODO INICIAL " + Hilo + " no puede ser igual al NODO DESTINO " + Hilo, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        String inicio = Integer.toBinaryString(Integer.parseInt(inicioInt));
        String fin = Integer.toBinaryString(Integer.parseInt(finInt));

        inicio = "0000".substring(inicio.length()) + inicio;
        fin = "0000".substring(fin.length()) + fin;

        camino.add(inicio);

        Thread.sleep(500);

        for (int i = 0; i < inicio.length(); i++) {
            if (semaforo.availablePermits() == 0) {
                lbl = nombre + " en espera...";
            }
            semaforo.acquire();

            if (inicio.charAt(i) != fin.charAt(i)) {
                inicio = inicio.substring(0, i) + fin.charAt(i) + inicio.substring(i + 1);
                camino.add(inicio);
            }
            repaint();
            semaforo.release();

            Thread.sleep(1000);
        }
    }
    }

    public static void main(String[] args) {
        // Mostrar la ventana de portada
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Portada().setVisible(true);
            }
        });
    }
}