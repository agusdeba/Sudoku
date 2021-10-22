package Logica;

public class Celda {
	private Integer valor;
	private Entidad_celda entidadGrafica;
	private int fila;
	private int columna;
	private int cuadrante;
	private boolean habilitada;	// Habilitadas son las que no te establece por default el juego.
	private boolean invalida;
	
	public Celda() { 
		this.valor = null;
		this.entidadGrafica = new Entidad_celda();
		fila = 0;
		columna = 0;
		cuadrante = 0;
		habilitada = true;
		invalida = false;
	}
	
	/*
	 * Pone la imagen que corresponde a la celda.
	 * Recibir valor del boton, y cambiar la imagen por ese valor.
	 */
	public void actualizar(int valor_numero) {
		
		Integer v = valor_numero;
		
		if (habilitada) { 
			valor = v; //Aca me cambia a la siguiente imagen. En mi caso tendria que poner la apretada // por el boton.
			entidadGrafica.actualizar(valor); // Actualiza la imagen de la entidad grafica, con el valor (indice)
			// de arreglo de imagenes.
		}
	}
	
	public int getCantElementos() {
		return this.entidadGrafica.get_imagenes().length;
	}
	
	
	public Integer getValor() {
		return this.valor;
	}
	
	public void setValor(Integer valor) {
		if (valor!=null && valor < this.getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizar(this.valor);
		}else {
			this.valor = null;
		}
	}
	
	public Entidad_celda getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	public void setGrafica(Entidad_celda g) {
		this.entidadGrafica = g;
	}
	
	public int get_fila() {
		return fila;
	}
	
	public void set_fila(int f) {
		fila = f;
	}
	
	public int get_columna() {
		return columna;
	}
	
	public void set_columna(int c) {
		columna = c;
	}
	
	public int get_cuadrante() {
		return cuadrante;
	}
	
	public void set_cuadrante(int cuadrante) {
		this.cuadrante = cuadrante;
	}
	
	public void set_habilitada(boolean es) {
		habilitada = es;
	}
	
	public boolean get_habilitada() {
		return habilitada;
	}
	
	public void set_invalida(boolean es) {
		invalida = es;
	}
	
	public boolean get_invalida() {
		return invalida;
	}
	
}
