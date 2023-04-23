package proyectoAPIsTODO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubclaseThreadChiste implements Runnable {
	private final static String CHISTE_UNICO = "single";
	private final static String TIPO_SPLIT = "\"";


	@Override
	public void run() {
		recibeYdevielveChisteTratado();
		
	}
	

	//en seste metodo recibimos un json
	//se deberia importar una libreria para tratar los datos con "clave":"valor"
	//se puede respver con un split mas abajo
	//da algunso problemas ya que el json contiene caracteres especiales
	public static String recibeYdevielveChisteTratado() {
		String chisteSinTratar = "";
		try {
			URL url = new URL("https://v2.jokeapi.dev/joke/Any");

			// Abrir la conexión HTTP
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// Establecer el método HTTP y otros encabezados necesarios
			con.setRequestMethod("GET");

			// Leer la respuesta del servidor
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			chisteSinTratar = response.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return devuelveChisteString(chisteSinTratar);
	}
	
	//este metodo trata el json y separa lo que necesitamos
	
	public static String devuelveChisteString(String chiste) {
		String chisteTratado = "";
		String[] separadorPuntos = chiste.split(TIPO_SPLIT);

		if (CHISTE_UNICO.equalsIgnoreCase(separadorPuntos[9])) {
			chisteTratado = separadorPuntos[13]+ "\n";
		} else {
			chisteTratado = separadorPuntos[13] + "\n" + separadorPuntos[17]+ "\n";
		}
		return chisteTratado;
	}

}
