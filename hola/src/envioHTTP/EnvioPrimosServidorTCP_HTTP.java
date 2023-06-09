package envioHTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;

public class EnvioPrimosServidorTCP_HTTP {
	private static final int PUERTO = 8080;

	public static void main(String[] args) {

		try {
			ServerSocket serverSocket = new ServerSocket(PUERTO);

			while (true) {
				Socket socket = serverSocket.accept();
				InputStream input = socket.getInputStream();

				threads(input, socket);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void threads(InputStream input, Socket socket) {
		new Thread(() -> {

			// leemos toda la linea que nos envia el cliente y separamos lo que nos interesa
			// con otro metodo interno
			String datosConBarra = lecturaDatosDeCliente(input);
			String[] separadorBarra = datosConBarra.split("/");
			int N = Integer.parseInt(separadorBarra[1]);
			int M = Integer.parseInt(separadorBarra[2]);

			// procesamos datos y pasamos por numeros primos
			// enviamos deatos de vuelta
			devolverDatosAcliente(socket, arrayNumerosPrimos(N, M));

		}).start();
	}

	private static void devolverDatosAcliente(Socket socket, ArrayList<Integer> arrayNumerosPrimos) {

		// guardamos la lista en un sting
		String listaNumeros = "</ul>\r\n";
		for (Integer datos : arrayNumerosPrimos) {
			listaNumeros += "<li>" + datos + "</li>\r\n";
		}
		listaNumeros += "</ul>\r\n";

		// enviamos el mensaje
		try {
			String mensaje = "HTTP/1.1 200 OK\r\n\n" + "<html>\r\n" + "<body>\r\n" + listaNumeros + "</body>\r\n"
					+ "</html>";
			// aqui construimos el mensaje y lo enviamos
			byte[] mensajeCifrado = mensaje.getBytes("UTF-8");
			OutputStream out = socket.getOutputStream();
			out.write(mensajeCifrado);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// procesamos datos y comprobamos prpimo
	// devuelve un array con los primos calculados
	public static ArrayList<Integer> arrayNumerosPrimos(int n, int m) {
		int contador = 0;
		ArrayList<Integer> arrayCalculado = new ArrayList<Integer>();

		while (contador < m) {
			if (esPrimo(n)) {
				arrayCalculado.add(n);
				contador++;
			}
			n++;
		}

		return arrayCalculado;
	}

	public static boolean esPrimo(int numero) {
		if (numero <= 1) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(numero); i++) {
			if (numero % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static String lecturaDatosDeCliente(InputStream input) {
		String espaciosSeparados = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = reader.readLine();

			espaciosSeparados = separarEspacios(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return espaciosSeparados;
	}

	public static String separarEspacios(String line) {
		String[] separador = line.split(" ");
		String parte1 = separador[1];
		return parte1;
	}

}
