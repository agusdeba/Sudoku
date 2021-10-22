package Gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Logica.Celda;
import Logica.Cronometro;
import Logica.Entidad_grafica;
import Logica.Entidad_reloj;
import Logica.InvalidFileException;
import Logica.Juego;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.BorderLayout;

public class GUI_Sudoku extends JFrame {

	private JPanel contentPane;
	private Juego juego;
	private JLabel[][] matriz_labels; 
	private JLabel label_anterior;
	private JButton [] opciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Sudoku frame = new GUI_Sudoku();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public GUI_Sudoku() {
		try {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_Sudoku.class.getResource("/Imagenes_celdas/20201009_225559_0001.png")));
		setTitle("Sudoku");
		setResizable(false);
		setLocationRelativeTo(null); // Crea la ventana al centro
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 622); // Tamaño de la ventana
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		label_anterior = new JLabel();
		matriz_labels = new JLabel[9][9];	
		opciones = new JButton[10];
		
		JPanel panel_cuadrante = new JPanel();
		panel_cuadrante.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_cuadrante.setBackground(new Color(240, 248, 255));
		contentPane.add(panel_cuadrante); // agrega el panel
		panel_cuadrante.setLayout(new GridLayout(9, 0, 0, 0));
		panel_cuadrante.setBounds(25, 135, 428, 428);
		
		juego = new Juego();
		
		JPanel panel_der = new JPanel();
		panel_der.setBackground(new Color(240, 248, 255));
		panel_der.setBounds(479, 135, 338, 428);
		contentPane.add(panel_der);
		panel_der.setLayout(null);
		
		JPanel panel_mensajes = new JPanel();
		panel_mensajes.setBounds(2, 223, 334, 208);
		panel_mensajes.setBorder(new LineBorder(new Color(240, 248, 255), 10));
		panel_mensajes.setBackground(new Color(240, 248, 255));
		panel_der.add(panel_mensajes);
		panel_mensajes.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_mensaje = new JLabel("Mensaje");
		lbl_mensaje.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_mensajes.add(lbl_mensaje, BorderLayout.CENTER);
		
		// ----------- PANEL AUXILIAR. (Contiene panel cronometro y panel botones).
		JPanel panel_aux = new JPanel();
		panel_aux.setBounds(0, 2, 334, 222);
		panel_aux.setBackground(new Color(240, 248, 255));
		panel_der.add(panel_aux);
		panel_aux.setLayout(new GridLayout(2, 0, 0, 0));
				
		JPanel panel_cronometro = new JPanel();
		panel_cronometro.setBackground(new Color(240, 248, 255));
		panel_cronometro.setBounds(0, 0, 334, 111);
		panel_cronometro.setLayout(null);
			
		JLabel lbl_hora_1 = new JLabel("h1");
		lbl_hora_1.setBounds(34, 34, 30, 30);
		panel_cronometro.add(lbl_hora_1);
		
		JLabel lbl_hora_2 = new JLabel("h2");
		lbl_hora_2.setBounds(63, 34, 30, 30);
		panel_cronometro.add(lbl_hora_2);
		
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/Imagenes_cronometro/dos_p.png"));
		JLabel lbl_dos_puntos1 = new JLabel("separador_1");
		lbl_dos_puntos1.setForeground(new Color(0, 0, 0));
		lbl_dos_puntos1.setBounds(92, 34, 30, 30);
		panel_cronometro.add(lbl_dos_puntos1);
		lbl_dos_puntos1.setIcon(imageIcon);
		redimensionar(lbl_dos_puntos1,imageIcon);
		
		JLabel lbl_min_1 = new JLabel("m1");
		lbl_min_1.setBounds(121, 34, 30, 30);
		panel_cronometro.add(lbl_min_1);
		
		JLabel lbl_min_2 = new JLabel("m2");
		lbl_min_2.setBounds(150, 34, 30, 30);
		lbl_min_2.setBackground(new Color(240, 240, 240));
		panel_cronometro.add(lbl_min_2);
		
		JLabel lbl_dos_puntos2 = new JLabel("separador_2");
		lbl_dos_puntos2.setBounds(179, 34, 30, 30);
		panel_cronometro.add(lbl_dos_puntos2);
		lbl_dos_puntos2.setIcon(imageIcon);
		redimensionar(lbl_dos_puntos1,imageIcon);
		
		JLabel lbl_seg_1 = new JLabel("s1");
		lbl_seg_1.setBounds(208, 34, 30, 30);
		panel_cronometro.add(lbl_seg_1);
		
		panel_aux.add(panel_cronometro);
		
		JLabel lbl_seg_2 = new JLabel("s2");
		lbl_seg_2.setBounds(236, 34, 30, 30);
		panel_cronometro.add(lbl_seg_2);
		
		JPanel panel_botones = new JPanel();
		panel_botones.setBackground(new Color(240, 248, 255));
		panel_aux.add(panel_botones);
		panel_botones.setLayout(null);

		Entidad_grafica hora_1 = new Entidad_reloj();
		Entidad_grafica hora_2 = new Entidad_reloj();
		Entidad_grafica min_1 = new Entidad_reloj();
		Entidad_grafica min_2 = new Entidad_reloj();
		Entidad_grafica seg_1 = new Entidad_reloj();
		Entidad_grafica seg_2 = new Entidad_reloj();
		
		Cronometro cronom = new Cronometro();
		TimerTask task = new TimerTask() {
			int t = 0;
			public void run() {
	
				cronom.tiempo(t);
				int s = cronom.get_segundos();	
				int m = cronom.get_minutos();
				int h = cronom.get_horas();

				hora_2.actualizar(h % 10); // Obtengo el 2do digito de la hora.
				lbl_hora_2.setIcon(hora_2.get_grafico());
				redimensionar(lbl_hora_2,hora_2.get_grafico());
				lbl_hora_2.repaint();
				h = h / 10; // Obtengo el 1er digito de la hora.
				hora_1.actualizar(h);
				lbl_hora_1.setIcon(hora_1.get_grafico());
				redimensionar(lbl_hora_1,hora_1.get_grafico());
				lbl_hora_1.repaint();
				
				min_2.actualizar(m % 10);
				lbl_min_2.setIcon(min_2.get_grafico());
				redimensionar(lbl_min_2,min_2.get_grafico());
				lbl_min_2.repaint();
				m = m / 10;
				min_1.actualizar(m);
				lbl_min_1.setIcon(min_1.get_grafico());
				redimensionar(lbl_min_1,min_1.get_grafico());
				lbl_min_1.repaint();
				
				seg_2.actualizar(s % 10);
				lbl_seg_2.setIcon(seg_2.get_grafico());
				redimensionar(lbl_seg_2,seg_2.get_grafico());
				lbl_seg_2.repaint();
				s = s / 10;	
				seg_1.actualizar(s);
				lbl_seg_1.setIcon(seg_1.get_grafico());
				redimensionar(lbl_seg_1,seg_1.get_grafico());
				lbl_seg_1.repaint();
				t++;
			}
		};
		runTimer(task, cronom);
		
		// -----------------------------------------------

		// Inserta las celdas en el tablero (o sea los label).
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Celda c = juego.get_celda(i, j); // Ya que la matriz es de celdas.
				ImageIcon grafico = c.getEntidadGrafica().get_grafico();
				JLabel label = new JLabel();
				label.setBorder(new LineBorder(Color.BLACK));
				
				if (c.get_cuadrante() == 1 || c.get_cuadrante() == 3 || c.get_cuadrante() == 5 || c.get_cuadrante() == 7 || c.get_cuadrante() == 9) {
					label.setBorder(new LineBorder(new Color(0,0,0),3));
					if (!c.get_habilitada()) {
						label.setBackground(Color.LIGHT_GRAY);
					}
				}else {
					if (!c.get_habilitada()) {
						label.setBackground(Color.LIGHT_GRAY);
					}
				}
			
				
				panel_cuadrante.add(label);
				label.setOpaque(true);
				matriz_labels[i][j] = label;

				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						redimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});

				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/Imagenes_celdas/gris.jpg"));

						if (c.get_habilitada()) {
							habilitar_opciones(true);
							if (!juego.get_continua_juego()) {
								if (label == label_anterior) {
									label_anterior.setBorder(new LineBorder(new Color(255, 255, 255), 3));
								} else {
									label_anterior.setBorder(new LineBorder(Color.BLACK));
									lbl_mensaje.setIcon(imageIcon);
									System.out.println("Corriga la opcion. ");
									label_anterior.setBorder(new LineBorder(new Color(200, 0, 0), 5));
								}
							} else {
								if (juego.get_celda_presionada() == null) {
									lbl_mensaje.setIcon(null);
									label_anterior = label;
									label_anterior.setBorder(new LineBorder(new Color(255, 255, 255), 3));
									juego.set_celda_presionada(c); // Seteo como activada la celda presionada.
									
								} else {
									//label_mensajes.setVisible(false);
									lbl_mensaje.setIcon(null);
									label_anterior.setBorder(new LineBorder(Color.BLACK));
									label_anterior = label;
									label_anterior.setBorder(new LineBorder(new Color(255, 255, 255), 3));
									juego.set_celda_presionada(c); // Seteo como activada la celda presionada.
								}
							}
						}else {
							habilitar_opciones(false);
						}
					}
				});
			}
		}

		//
		JPanel panel_opciones = new JPanel();
		panel_opciones.setBackground(new Color(240, 248, 255));
		panel_opciones.setBounds(25, 58, 792, 65);
		contentPane.add(panel_opciones);
		panel_opciones.setLayout(new GridLayout(0, 10, 0, 0));

		// ---------------- BOTONES de opciones
		for (int i = 0; i < 10; i++) {
			Celda c = juego.get_opcion(i);
			JButton boton = new JButton();
			ImageIcon grafico = c.getEntidadGrafica().get_grafico();
			panel_opciones.add(boton);
			opciones[i] = boton;
			boton.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					redimensionar(boton, grafico);
					boton.setIcon(grafico);
					
				}
			});
			boton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Vincula la celda que este seleccionada y cambia su imagen por la del boton.
					
					if (boton.isEnabled()) {
						int num_boton = c.getValor(); // Obtengo el valor del boton que aprete.
						Celda celda_presionada = juego.get_celda_presionada(); // obtengo la celda presionada que quedo
																				// habilitada
						juego.accionar(celda_presionada, num_boton);
						System.out.println("valor de boton: " + num_boton); // SACAR
	
						JLabel label_presionado = matriz_labels[celda_presionada.get_fila()][celda_presionada.get_columna()];
						// volver a redimensionar para que quede bien la imagen.
						redimensionar(label_presionado, celda_presionada.getEntidadGrafica().get_grafico()); 
						label_presionado.repaint(); // Repinta el label
						label_presionado.setBorder(new LineBorder(Color.BLACK));
						
						// CHEQUEAR QUE SEA VALIDA LA OPCION INGRESADA.
						chequear_opcion(num_boton, celda_presionada);
						
						if (juego.termino() && juego.get_continua_juego()) {
							cronom.parar_cronometro();
							habilitar_opciones(false);
							celda_presionada.set_habilitada(false);
							// IMAGEN DE JUEGO COMPLETADO.
							JOptionPane.showMessageDialog(contentPane, "¡GANASTE!", "Completaste el juego correctamente",
				                    JOptionPane.INFORMATION_MESSAGE);
							System.out.print("GANASTE!!! "); // SACAR
						}
					}
				}
			});
		}

		// --------------------

		JTextPane txtpnOpciones = new JTextPane();
		txtpnOpciones.setForeground(new Color(0, 0, 0));
		txtpnOpciones.setEditable(false);
		txtpnOpciones.setFont(new Font("Aharoni", Font.PLAIN, 13));
		txtpnOpciones.setBackground(new Color(240, 248, 255));
		txtpnOpciones.setText("Opciones:");
		txtpnOpciones.setBounds(25, 37, 71, 20);
		contentPane.add(txtpnOpciones);

		JLabel label_sudoku = new JLabel("SUDOKU");
		label_sudoku.setForeground(SystemColor.windowBorder);
		label_sudoku.setBounds(361, 22, 108, 25);
		contentPane.add(label_sudoku);
		label_sudoku.setFont(new Font("Aharoni", Font.PLAIN, 25));
		
		
		}catch(InvalidFileException error){
			System.out.println("Excepcion capturada. "); // SACAR
			JOptionPane.showMessageDialog(contentPane, "Archivo invalido. ", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	/**
	 * Chequea si la opcion es valida o no. Marca las celdas donde hay errores.
	 * @param num
	 * @param celda_presionada
	 */
	private void chequear_opcion(int num, Celda celda_presionada) {
		boolean cuadrante_valido, fila_valida, columna_valida;
		
		fila_valida = juego.fila_valida(num, celda_presionada);	
		columna_valida = juego.columna_valida(num, celda_presionada);
		cuadrante_valido = juego.cuadrante_valido(num, celda_presionada);	
		System.out.println("Fila valida? "+fila_valida); // SACAR
		System.out.println("Columna valida? "+columna_valida); // SACAR
		System.out.println("Cuadrante_valido? "+cuadrante_valido); // SACAR
		System.out.println("Tablero: "); // SACAR

		if (fila_valida && columna_valida && cuadrante_valido || num == 9) {
			juego.set_continua_juego(true);
			System.out.println("Continua juego? "+juego.get_continua_juego()); // SACAR
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					Celda celda_actual;
					celda_actual = juego.get_celda(i, j);
					celda_actual.set_invalida(false);
					if (celda_actual.get_habilitada()) {
						//matriz_labels[i][j].setBorder(new LineBorder(Color.BLACK));
						if (celda_actual.get_cuadrante() == 1 || celda_actual.get_cuadrante() == 3 || celda_actual.get_cuadrante() == 5 || celda_actual.get_cuadrante() == 7 || celda_actual.get_cuadrante() == 9) {
							matriz_labels[i][j].setBackground(null);
							matriz_labels[i][j].setBorder(new LineBorder(new Color(0,0,0),3));
						}else {
							matriz_labels[i][j].setBackground(null);
						}
					}else {
						//matriz_labels[i][j].setBorder(new LineBorder(new Color(0, 0, 0), 2));
						matriz_labels[i][j].setBackground(Color.LIGHT_GRAY);
					}
				}
			}	
		}
		else {
			juego.set_continua_juego(false);
			System.out.println("Continua juego? "+juego.get_continua_juego()); // SACAR
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					Celda celda_actual;
					celda_actual = juego.get_celda(i, j);
					if (celda_actual.get_invalida()) {
						matriz_labels[i][j].setBackground(new Color(255, 0, 0));
					}else {
						if (celda_actual.get_habilitada())
							matriz_labels[i][j].setBackground(null);
						else {
							//matriz_labels[i][j].setBorder(new LineBorder(new Color(0, 0, 0), 2));
							matriz_labels[i][j].setBackground(Color.LIGHT_GRAY);
						}
					}
				}
			}
			matriz_labels[celda_presionada.get_fila()][celda_presionada.get_columna()].setBorder(new LineBorder(new Color(255, 0, 0), 5));
		}
	}
	
	/*
	 * Metodo para marcar los cuadrantes 1, 3, 5, 7 y 9.
	 * Las celdas "por default" pintarle el fondo en gris clarito.
	 * 
	 */
	
	// Habilita o deshabilita las opciones del juego.
	public void habilitar_opciones(boolean es) {
		for (int i = 0; i < opciones.length; i++) {
			opciones[i].setEnabled(es);
		}
	}
	
	// Redimensiona las imagenes de los botones.
	private void redimensionar(JButton boton, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image new_img = image.getScaledInstance(boton.getWidth(), boton.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(new_img);
			boton.repaint();
		}
	}
	
	//Adapta el tamaño de la imagen al tamaño del label.
	private void redimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {
			Image new_img = image.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(new_img);
			label.repaint();
		}
	}

	public void runTimer(TimerTask task, Cronometro temp) {
		temp.get_timer().schedule(task, 0, 1000);
	}
}
