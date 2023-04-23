package musica;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotyPrincipal {

	public static final String INICIO = "\nLISTADO DE CANCIONES \n--------------------";

	public static void main(String[] args) {

		String direccion = args[0];
		int puerto = Integer.parseInt(args[1]);

		Thread t = new Thread(new Thread1(direccion, puerto));
		t.start();

	}

	public static class Thread1 implements Runnable {
		private static final String GET = "get\n";
		private static final String PLAY = "play";
		private static final int BAJAR_INIDICE = 1;
		private String direccion;
		private int puerto;

		public Thread1(String direccion, int puerto) {
			this.direccion = direccion;
			this.puerto = puerto;
		}

		@Override
		public void run() {
			try {
				DatagramSocket socket = new DatagramSocket(puerto);
				byte[] buffer = new byte[1024];

				while (true) {
			
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String datosRecibidos = new String(packet.getData(), 0, packet.getLength());
					System.out.println("Dato " + datosRecibidos + " " + packet.getAddress() + ":" + packet.getPort());
					InetAddress ipCliente = packet.getAddress();
					int puertoCliente = packet.getPort();
					
					
					if (datosRecibidos.equalsIgnoreCase(GET)) {
						
						devuelveLista(socket, packet, ipCliente, puertoCliente, direccion);
						
						
					} else if (datosRecibidos.toLowerCase().contains(PLAY.toLowerCase())) {
				
						
						reproducirCancion(datosRecibidos, direccion);

					}

				}

			} catch (SocketException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public String[] paraSaberCancion(String datos) {
			String[] arreglo = datos.split(" ");
			return arreglo;
		};
		
		public void reproducirCancion(String datosRecibidos, String ruta){
			
			//recorrerDirectorio();
			
			int numeroObtenido = obtenerNumero(datosRecibidos)-BAJAR_INIDICE;
			
			//recorremos directorio
			
			File directorio = new File(ruta);
			File[] archivos = directorio.listFiles();
			
			//recogemos ruta absoluta del archivo
			String rutaCancionElegida = archivos[numeroObtenido].getAbsolutePath();
			
			MakeSound speaker = new MakeSound();
			String file = rutaCancionElegida;
			speaker.playSound(file);
			
			
		}
		
		//con esto obtenemos el numero que ha introducido el usuiario despues del play
		public static int obtenerNumero(String texto) {
	        // Crear una expresión regular para buscar un número
	        String regex = "\\d+";  // \\d significa "cualquier dígito", y + significa "uno o más"
	        
	        // Crear un objeto Pattern y un objeto Matcher para buscar la expresión regular
	        Pattern patron = Pattern.compile(regex);
	        Matcher matcher = patron.matcher(texto);
	        
	        // Si se encuentra un número, convertirlo a un entero y devolverlo
	        if (matcher.find()) {
	            return Integer.parseInt(matcher.group());
	        } else {
	            // Si no se encuentra un número, devolver -1 (o lanzar una excepción, según sea apropiado)
	            return -1;
	        }
	    }
		
		

		public void devuelveLista(DatagramSocket soc, DatagramPacket paqueteEnvio, InetAddress ipCli, int pueroCli,
				String direc) {

			try {

				File directorio = new File(direc);
				// Crear un ArrayList para guardar los índices de los archivos
				ArrayList<String> titulos = new ArrayList<>();
				titulos.add(INICIO);
				// Obtener una lista de los archivos en el directorio
				File[] archivos = directorio.listFiles();
				for (int i = 0; i < archivos.length; i++) {
					if (archivos[i].isFile() && archivos[i].getName().endsWith(".wav")) {
						titulos.add(i+1 + " " + archivos[i].getName()+ " "+ archivos[i].getAbsolutePath());
					}

				}

				// Mostrar los índices de los archivos
				for (String indice : titulos) {

					String indiceS = indice + "\n";

					byte[] datos = indiceS.getBytes();
					soc.send(new DatagramPacket(datos, datos.length, ipCli, pueroCli));
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
