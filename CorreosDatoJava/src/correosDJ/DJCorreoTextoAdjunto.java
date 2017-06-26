package correosDJ;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author datojava.blogspot.com
 */
public class DJCorreoTextoAdjunto {

	public void enviarCorreo() {
		// El correo gmail de env�o
		String correoEnvia = "tuCorreo@gmail.com";
		String claveCorreo = "tuClave";

		// La configuraci�n para enviar correo
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", correoEnvia);
		properties.put("mail.password", claveCorreo);

		// Obtener la sesion
		Session session = Session.getInstance(properties, null);

		try {
			// Crear el cuerpo del mensaje
			MimeMessage mimeMessage = new MimeMessage(session);

			// Agregar quien env�a el correo
			mimeMessage.setFrom(new InternetAddress(correoEnvia, "Dato Java"));

			// Los destinatarios
			InternetAddress[] internetAddresses = {
					new InternetAddress("correo1@gmail.com"),
					new InternetAddress("correo2@gmail.com"),
					new InternetAddress("correo3@gmail.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO,
					internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setSubject("Dato Java Enviando Correo.");

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart
					.setText("Siguiendo el Tutorial de datojava.blogspot.com env�o el correo.");

			MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
			mimeBodyPartAdjunto.attachFile("URL del ARCHIVO.");

			// Crear el multipart para agregar la parte del mensaje anterior
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(mimeBodyPartAdjunto);

			// Agregar el multipart al cuerpo del mensaje
			mimeMessage.setContent(multipart);

			// Enviar el mensaje
			Transport transport = session.getTransport("smtp");
			transport.connect(correoEnvia, claveCorreo);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Correo enviado");
	}

	public static void main(String[] args) {
		DJCorreoTextoAdjunto correoTextoAdjunto = new DJCorreoTextoAdjunto();
		correoTextoAdjunto.enviarCorreo();

	}

}
