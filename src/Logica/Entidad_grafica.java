package Logica;

import javax.swing.ImageIcon;

public class Entidad_grafica {
	protected ImageIcon grafico; // Permite guardar la imagen que tenemos.
	protected String[] imagenes; //arreglo de string, cada indice te retorna una imagen
	
	public Entidad_grafica() {
		this.grafico = new ImageIcon();
	}
	
	public void actualizar(int indice) {
		if (indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon get_grafico() {
		return this.grafico;
	}
	
	public void set_grafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public String[] get_imagenes() {
		return this.imagenes;
	}
	
	public void set_imagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
}
