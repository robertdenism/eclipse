package ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class EnvioRecepcionServidorTCP_HTTP {
	private static final int PUERTO = 8000;

	public static void main(String[] args) {
		
	
		try {
			//SERVERSOCKET que este fuera del while importante ya que seria un bucle infinito
			
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			//al ponerlo en un while true se convierte en un servicio
			//tambien le podemos añadir threasd y cinvertirlo en un miltihread
			while(true) {
				Socket socket = serverSocket.accept();
				InputStream input = socket.getInputStream(); 
				
				//esta parte es para recibier un mensaje TCP
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String line = reader.readLine();
				System.out.println(line);
				
				//esta parte es para enviar un mensaje TCP
				
				//este string es un mensaje que queremos enviar (cifrado, primos...)
				String mensajeQueEnviamos= "holaaaa";
				//este estrin es el que contiene el formato correcto para el envio
				String mensaje = "HTTP/1.1 200 OK\r\n\n"
						+"<html>\r\n"
						+"<body>\r\n"
						+"<h1>\r\n"
						+mensajeQueEnviamos
						+"\r\n</h1>\r\n"
						+"</body>\r\n"
						+"</html>";
				//aqui construimos el mensaje y lo enviamos 
				byte[] mensajeCifrado = mensaje.getBytes("UTF-8");
				 OutputStream out = socket.getOutputStream();
				 out.write(mensajeCifrado);
				 out.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
