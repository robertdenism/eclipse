package LosMail;

import java.io.IOException;
import java.util.Base64;

//import org.simplejavamail.api.email.CalendarMethod;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.config.ConfigLoader;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.impl.StaticLoggerBinder;

public class Mail1 {

	public static void main(String[] args) {
		
		
		
		//Logger logger = LoggerFactory.getLogger(Mail1.class);
		ConfigLoader.loadProperties("simplejavamail.properties", true); // optional default
		ConfigLoader.loadProperties("overrides.properties", true); // optional extra
		
		String mensaje = "hola, esto es el cuerpo del mensaje enviado con java";
		
		
		//String entradaOriginalUsu = "robert.munteanu";
        //String cadenaCodificadaUsu = Base64.getEncoder().encodeToString(entradaOriginalUsu.getBytes());
		
		Email email = EmailBuilder.startingBlank()
				
		          .to("Conseguidooo", "@educa.madrid.org")
		          //la terminacion tiene que ser s o si con educa.madrid en eclipse
		          .from("robert", "robertConNuevoMail@educa.madrid.org")
		          //.withReplyTo("lollypop", "lolly.pop@othermail.com")
		          .withSubject("prueba mensaje desde java")
		          //.withHTMLText("<img src='cid:wink1'><b>We should meet up!</b><img src='cid:wink2'>")
		          .withPlainText(mensaje)
		          .buildEmail();
		         

		Mailer mailer = MailerBuilder
		          .withSMTPServer("smtp.educa.madrid.org", 587, "robert.munteanu", "pass")
		          .withTransportStrategy(TransportStrategy.SMTP_TLS)
		          .clearEmailValidator() // turns off email validatio
		          .buildMailer();

		mailer.sendMail(email);
		
		System.out.println("Mensaje enviado");
		

	}

}
