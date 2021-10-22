package Logica;

import java.util.Timer;
import java.util.TimerTask;

public class Cronometro {
	private Timer timer;
	private int segundos; 
	private int minutos; 
	private int horas;

	public Cronometro(){
		timer = new Timer();	
		segundos = 0;
		minutos = 0;
		horas = 0;
	}
	
	// se puede sacar
	public Timer get_timer() {
		return timer;
	}
	
	public void tiempo(int sec) {
		int resto = 0;

		if (sec >= 3600) // si tenemos una hora o mas
		{
			horas = sec / 3600;
			resto = sec % 3600; // podr�a ser m�s o menos de un minuto

			if (resto >= 60) // comprobar si el resto es mayor o igual a un min
			{
				minutos = resto / 60;
				segundos = resto % 60;
			} else { // si es menos de un minuto
				segundos = resto;
				minutos = 0;
			}
		}
		// si tenemos un min o mas
		else if (sec >= 60) {
			horas = 0; 
			minutos = sec / 60;
			segundos = sec % 60;
		}
		// si tenemos solo unos segundos
		else if (sec < 60) {
			horas = 0;
			minutos = 0;
			segundos = sec;
		}
	}
	
	public int get_segundos() {
		return segundos;
	}
	public int get_minutos() {
		return minutos;
	}
	public int get_horas() {
		return horas;
	}
	public void parar_cronometro() {
		timer.cancel();
	}	
}
