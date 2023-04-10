package musica;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class POC {
	
	private static String ruta = "hola5";
	
	public POC(String ruta) {
		this.ruta = ruta;
	}
	

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public static void main(String[] args){
		
		
		MakeSound speaker = new MakeSound();
		// Debe ser ruta absoluta
		System.out.println(ruta);
		String file = "/home/robert/Escritorio/spotiudp/Avicii-Wake-Me-Up.wav";
		speaker.playSound(file);
		
		
	}
}