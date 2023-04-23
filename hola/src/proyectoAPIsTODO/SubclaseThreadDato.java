package proyectoAPIsTODO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SubclaseThreadDato  implements Runnable{
	private final static String TIPO_SPLIT = "\"";

	@Override
	public void run() {
		recibeYdevielveDatoTratado();
		
	}

	public String recibeYdevielveDatoTratado() {
		String datoSinTratar = "";
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://numbersapi.p.rapidapi.com/random/trivia?min=10&max=20&fragment=true&json=true"))
				.header("X-RapidAPI-Key", "8016579147mshdffff6fc2911bfep11fbbcjsna986b712e5e1")
				.header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			datoSinTratar = response.body().toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return devuelveDatoTratado(datoSinTratar);
		
	}

	public String devuelveDatoTratado(String datoSinTratar) {
		String datoTratado = "";
		String split [] = datoSinTratar.split(TIPO_SPLIT);
		datoTratado = (split[3]+split[6]);
		return datoTratado;
	}
	
	

}
