package envioHTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class MainServidorCifradoHTTP {

	private static final String CIFRAR = "cifrar";
	private static final Object DESCIFRAR = "descifrar";
	private static final int PUERTO = 8000;

	public static void main(String[] args) {
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			while(true) {
				Socket socket = serverSocket.accept();
				InputStream input = socket.getInputStream();
				threads(input, socket);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void threads(InputStream input, Socket socket) {
		new Thread(()->{
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String line = reader.readLine();
				System.out.println(line);
				
				//separarEspacios(line);
				
				String[] separador = separarEspacios(line).split("/");
				String parte1 = separador[1];
				String parte2 = separador[2];
				String parte3 = separador[3];
				
				if(parte1.equals(CIFRAR)) {
					String mensajeCifrado = metodoCifrado(parte2,parte3);
					metodoEnvio(mensajeCifrado,socket);
					
				}else if (parte1.equals(DESCIFRAR)) {
					String mensajeDescifrado = metodoDescifrar(parte2,parte3);
					metodoEnvio(mensajeDescifrado,socket);
					
				};
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
	}
	
	private static void metodoEnvio(String cifradoDescifrado, Socket socket) {
		 try {
			 System.out.println(cifradoDescifrado);
			/*PrintWriter out = new PrintWriter(socket.getOutputStream(), true);*/
			String mensaje = "HTTP/1.1 200 OK\r\n\n"
					+"<html>\r\n"
					+"<body>\r\n"
					+"<h1>\r\n"
					+cifradoDescifrado
					+"\r\n</h1>\r\n"
					+"</body>\r\n"
					+"</html>";
			//out.println(mensaje);*/
			
			System.out.println(mensaje);
			 byte[] mensajeCifrado = mensaje.getBytes("UTF-8");
			 OutputStream out = socket.getOutputStream();
			 out.write(mensajeCifrado);
			 out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String separarEspacios(String linea ) {
		String[] separador = linea.split(" ");
		String parte1 = separador[1];
		return parte1;
	}

	private static String metodoDescifrar(String texto, String desplazamiento) {
		StringBuilder descifrado = new StringBuilder();
		int clave = Integer.parseInt(desplazamiento);
		
	    for (int i = 0; i < texto.length(); i++) {
	        char caracter = texto.charAt(i);

	        if (Character.isUpperCase(caracter)) {
	            // Convertir el caracter a su equivalente descifrado en may�sculas
	            char descifradoCaracter = (char) ((caracter - clave - 'A' + 26) % 26 + 'A');
	            descifrado.append(descifradoCaracter);
	        } else if (Character.isLowerCase(caracter)) {
	            // Convertir el caracter a su equivalente descifrado en min�sculas
	            char descifradoCaracter = (char) ((caracter - clave - 'a' + 26) % 26 + 'a');
	            descifrado.append(descifradoCaracter);
	        } else {
	            // El caracter no es una letra, dejarlo sin descifrar
	            descifrado.append(caracter);
	        }
	    }

	    return descifrado.toString();
		
	}

	private static String metodoCifrado(String texto, String desplazamiento) {
		StringBuilder cifrado = new StringBuilder();
		int clave = Integer.parseInt(desplazamiento);
		
		for (int i = 0; i < texto.length(); i++) {
	        char caracter = texto.charAt(i);

	        if (Character.isUpperCase(caracter)) {
	            // Convertir el caracter a su equivalente cifrado en may�sculas
	            char cifradoCaracter = (char) ((caracter + clave - 'A') % 26 + 'A');
	            cifrado.append(cifradoCaracter);
	        } else if (Character.isLowerCase(caracter)) {
	            // Convertir el caracter a su equivalente cifrado en min�sculas
	            char cifradoCaracter = (char) ((caracter + clave - 'a') % 26 + 'a');
	            cifrado.append(cifradoCaracter);
	        } else {
	            // El caracter no es una letra, dejarlo sin cifrar
	            cifrado.append(caracter);
	        }
	    }
		return cifrado.toString();
		
	}

	
	
	

}
