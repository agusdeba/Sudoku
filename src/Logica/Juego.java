package Logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Modela el juego sudoku.
 * @author Agustin Escobar.
 *
 */
public class Juego {

	private Celda[][] tablero;
	private Celda[] opciones;
	private int[][] matriz_archivo;
	private Celda celda_presionada;
	private boolean continua_juego; // Indica si puede continuar jugando.

	public Juego() throws InvalidFileException {

		tablero = new Celda[9][9];
		matriz_archivo = new int[9][9];
		String ruta = "File/archivo.txt"; 
		
		// Inicializo el tablero de celdas con los valores de la matriz del archivo.
		if (validar_archivo(ruta)) {
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					Random rand = new Random();
					int value = rand.nextInt(2); // 2
					tablero[i][j] = new Celda();
					// Seteo a cada celda su fila y columna en el tablero.
					tablero[i][j].set_fila(i);
					tablero[i][j].set_columna(j);
					setear_cuadrante(i, j, tablero[i][j]);
		
					if (value == 0) { // De acuerdo a value decidir si asignar un valor o no
						tablero[i][j].setValor(9);
					} else {
						tablero[i][j].setValor(matriz_archivo[i][j] - 1);
						tablero[i][j].set_habilitada(false);
					}
				}
			}
			// A cada boton del panel de opciones, le establezco una celda con su imagen
			// correspondiente.
			opciones = new Celda[10];
			for (int i = 0; i < 10; i++) {
				opciones[i] = new Celda();
				opciones[i].setValor(i);
			}	
			continua_juego = true;		
			mostrar_tablero(); // SACAR
		}else {
			throw new InvalidFileException("Archivo invalido. ");
		}
	}	
	// Chequea si el tablero esta completo para indicar si termino el juego.
	public boolean termino() {
		boolean esta = true;
		for (int i = 0; i < 9 && esta; i++) {
			for (int j = 0; j < 9 && esta; j++) {
				// Si la celda tiene una imagen "nula".
				if (tablero[i][j].getValor() == 9 ) {
					esta = false;
				}
			}
		}
		return esta;
	}
	
	// celda presionada y el valor del boton que apreto
	/**
	 * Actualiza la celda presionada.
	 * @param c Celda a actualizar.
	 * @param valor Valor del boton apretado.
	 */
	public void accionar(Celda c, int valor) {
		c.actualizar(valor);
	}

	public void set_celda_presionada(Celda c) {
		celda_presionada = c;
	}

	public Celda get_celda_presionada() {
		return celda_presionada;
	}
	
	public void set_continua_juego(boolean continua){
		continua_juego = continua;
	}
	
	public boolean get_continua_juego() {
		return continua_juego;
	}
	
	public Celda get_opcion(int i) {
		return this.opciones[i];
	}

	public Celda get_celda(int i, int j) {
		return this.tablero[i][j];
	}
	
	// Setea el cuadrante en el que se encuentra cada celda del tablero.
	private void setear_cuadrante(int fila, int col, Celda c) {
		if (fila < 3) {
			if (col < 3)
				c.set_cuadrante(1);
			else if (col > 2 && col < 6)
				c.set_cuadrante(2);
			else
				c.set_cuadrante(3);
		} else if (fila > 2 && fila < 6) {
			if (col < 3)
				c.set_cuadrante(4);
			else if (col > 2 && col < 6)
				c.set_cuadrante(5);
			else
				c.set_cuadrante(6);
		} else {
			if (col < 3)
				c.set_cuadrante(7);
			else if (col > 2 && col < 6)
				c.set_cuadrante(8);
			else
				c.set_cuadrante(9);
		}
	}

	// Chequea que el archivo sea valido.
	private boolean validar_archivo(String ruta) {

		File a = null; // Crea un objeto File "a" que representa el archivo.
		FileReader fr = null;
		BufferedReader br = null;
		String[] arreglo;
		String linea;
		boolean archivo_valido = true;
		int f = 0, c = 0;

		try {
			// Apertura del archivo y creacion de BufferedReader para poder hacer una
			// lectura comoda (disponer del metodo readLine())
			
			InputStream in = Juego.class.getClassLoader().getResourceAsStream(ruta);
            InputStreamReader inr = new InputStreamReader(in);
            br = new BufferedReader(inr);
			
			// Lectura del archivo
			while ((linea = br.readLine()) != null && f < matriz_archivo.length && archivo_valido) {
				System.out.println(linea); // SACAR
				arreglo = linea.split(" "); // Crea un arreglo que almacena las palabras del archivo por linea,						// separandolas por espacios.
					if (arreglo.length == 9) { // Paso el contenido del archivo a matriz_archivo.
						for (int i = 0; i < arreglo.length; i++) {
							if (!isNumeric(arreglo[i])) {
								archivo_valido = false;
							}else {
								matriz_archivo[f][c] = Integer.parseInt(arreglo[i]);
								c++;
							}
						}
						f++;
						c = 0;
					} else {
						archivo_valido = false;
					}
			}
			
			if (linea != null) {
				archivo_valido = false;
			}
			
			System.out.println("Arhivo matriz "); // SACAR
			mostrar_matriz(); // SACAR
			
			for (int i = 0; i < 9 && archivo_valido; i++) {
				if (archivo_valido)
					archivo_valido = validar_fila(i);
				else {
					System.out.print("Fila invalida"); // LANZAR EXCEPCION
				}
			}
			for (int i = 0; i < 9 && archivo_valido; i++) {
				if (archivo_valido)
					archivo_valido = validar_columna(i);
				else {
					System.out.println("Columna invalida"); // LANZAR EXCEPCION
				}
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-----------" + archivo_valido + "---------"); // SACAR
		return archivo_valido;
	}
	
	// cambiar nombre por es_numero
	private static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

	// Chequea que la fila no contenga numeros repetidos.
	public boolean fila_valida(int num, Celda celda_presionada) {
		boolean es_valida = true;
		int fila = celda_presionada.get_fila();
		
		for (int i = 0; i < 9; i++) {
			if (tablero[fila][i].getValor() == num && tablero[fila][i] != celda_presionada) {
				tablero[fila][i].set_invalida(true);
				es_valida = false;
			}else {
				tablero[fila][i].set_invalida(false);
			}
				
		}
		return es_valida;
	}
	
	// Chequea que la columna no contenga numeros repetidos.
	public boolean columna_valida(int num, Celda celda_presionada) {
		boolean es_valida = true;
		int col = celda_presionada.get_columna();
		
		for (int i = 0; i < 9; i++) {
			if (tablero[i][col].getValor() == num && tablero[i][col] != celda_presionada) {
				tablero[i][col].set_invalida(true);
				es_valida = false;
			}else {
				tablero[i][col].set_invalida(false);
			}
		}
		return es_valida;
	}
	

	/**
	 * Chequea que el cuadrante sea valido.
	 * @param num Es el numero de contiene la celda presionada.
	 * @param celda_presionada Celda presionada.
	 * @return Verdadero si el cuadrante es valido, falso caso contrario.
	 */
	public boolean cuadrante_valido(int num, Celda celda_presionada) {
		int cuadrante = celda_presionada.get_cuadrante()-1;
		boolean es_valido = true;
		int fila = (cuadrante / 3) * 3;
		int columna = (cuadrante  % 3) * 3;
		
		 for (int i = fila; i < fila + 3; i++) {
		     for (int j = columna; j < columna + 3; j++) {
		    	 if (tablero[i][j].getValor() == num && tablero[i][j] != celda_presionada) {
					es_valido = false;
					tablero[i][j].set_invalida(true);
				}else {
					tablero[i][j].set_invalida(false);
				}
		     }
		 }	
		return es_valido;
	}
	
	// Chequea que la fila del archivo sea valida.
	// Asegurandose que no contenga numeros repetidos entre el 1 y el 9.
	public boolean validar_fila(int fil) {
		boolean es_valida = true;
		boolean esta_num = false;
		int num = 1;

		while (es_valida && num < 10) {
			for (int i = 0; i < 9 && !esta_num; i++) {
				if (matriz_archivo[fil][i] == num) {
					esta_num = true;
					num++;
				} else {
					if (i == 8)
						es_valida = false;
				}
			}
			esta_num = false;
		}
		return es_valida;
	}


	// Chequea que la fila del archivo sea valida.
	// Asegurandose que no contenga numeros repetidos entre el 1 y el 9.
	public boolean validar_columna(int col) {
		boolean es_valida = true;
		boolean esta_num = false;
		int num = 1;

		while (es_valida && num < 10) {
			for (int i = 0; i < 9 && !esta_num; i++) {
				if (matriz_archivo[i][col] == num) {
					esta_num = true;
					num++;
				} else {
					if (i == 8)
						es_valida = false;
				}
			}
			esta_num = false;
		}
		return es_valida;
	}
	
	// SACAR
	private void mostrar_matriz() {
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(matriz_archivo[f][c] + " ");
			}
			System.out.println();
		}
	}

	// SACAR
	private void mostrar_tablero() {
		System.out.println("Tablero: ");
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(tablero[f][c].getValor() + " ");
			}
			System.out.println();
		}
	}


}
