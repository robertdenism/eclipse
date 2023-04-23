package proyectoAPIsTODO;

import java.util.ArrayList;
import java.util.Iterator;

public class RecibePeticiones implements Runnable {

	private String tipoDePeticion;
	private String numeroDePericiones;

	public RecibePeticiones(String tipoDePeticion, String numeroDePeticiones) {
		this.tipoDePeticion = tipoDePeticion;
		this.numeroDePericiones = numeroDePeticiones;
		
	};

	@Override
	public void run() {
		//aqui nos encargamos de ver que tipo de respuesta desea el servidor
		//hacemos dos metodos principales los cuales instancian dos clases
		listaPrincipal();
	
	}
	
	//guardamos en un array prncipal los datos obtenidos dependiendo de lo que queramos
	public ArrayList<String> listaPrincipal(){
		ArrayList<String> listadoDatos = new ArrayList<>();
		if("c".equalsIgnoreCase(tipoDePeticion)) {
			//declarar arraylist que devolveremos por un metodo
			listadoDatos.addAll(buscarChiste());
		}else if("d".equalsIgnoreCase(tipoDePeticion)) {
			//buscarDato();
			listadoDatos.addAll(buscarDato());
		}else if("m".equalsIgnoreCase(tipoDePeticion)) {
			listadoDatos.addAll(buscarDato());
			listadoDatos.addAll(buscarChiste());
		}else {
			System.out.println("NO SE HA REALIZADO NINGUNA PETICIÓN");
		}
		
		return listadoDatos;
	}
	
	/*los metodos mas abajo re repiten se podra optimizar añadiando un if pseudocidigo
	 * listadoDatos.add(if(dato){
	 * dato.recibeYdevielveDatoTratado())
		}else{
		chiste.recibeYdevielveDatoTratado())
		};
		
		
	*/
	//instanciamos la subclase que se encarga de buscar datos y nos devuelve un string el cual lo vamos
	//almcenando en unh arraylist que mas adelante lo enviaremos por mail*/
	public ArrayList<String> buscarDato() {
		int numeroPeticionesParseado = Integer.parseInt(numeroDePericiones);
		SubclaseThreadDato dato = new SubclaseThreadDato();
		ArrayList<String> listadoDatos = new ArrayList<>();
		for (int i = 0; i < numeroPeticionesParseado; i++) {
			Thread  ThreadDatos = new Thread(dato);
			try {
				listadoDatos.add(dato.recibeYdevielveDatoTratado());
				ThreadDatos.start();
				ThreadDatos.join();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listadoDatos;
	}

	/*instanciamos la subclase que se encarga de buscar chistes y nos devuelve un string el cual lo vamos
		almcenando en unh arraylist*/
	
	public ArrayList<String> buscarChiste() {
		int numeroPeticionesParseado = Integer.parseInt(numeroDePericiones);
		SubclaseThreadChiste chiste = new SubclaseThreadChiste();
		ArrayList<String> listadoChistes = new ArrayList<>();
		for (int i = 0; i < numeroPeticionesParseado; i++) {
			Thread  chistes = new Thread(chiste);
			try {
				listadoChistes.add(chiste.recibeYdevielveChisteTratado());
				chistes.start();
				chistes.join();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listadoChistes;
	}


}
