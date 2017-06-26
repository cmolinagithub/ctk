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
public class DJCorreoTexto {

	public void enviarCorreo() {
		// El correo gmail de env�o
		String correoEnvia = "cmolinabastidas@gmail.com";
		String claveCorreo = "1984molina";

		// La configuraci�n para enviar correo
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", "cmolinabastidas@gmail.com");
		properties.put("mail.password", "1984molina");

		// Obtener la sesion
		Session session = Session.getInstance(properties, null);

		try {
			// Crear el cuerpo del mensaje
			MimeMessage mimeMessage = new MimeMessage(session);

			// Agregar quien env�a el correo
			mimeMessage.setFrom(new InternetAddress(correoEnvia, "Dato Java"));

			// Los destinatarios
			InternetAddress[] internetAddresses = {
					new InternetAddress("dianacatalinacruzcaiza@gmail.com"),
					new InternetAddress("cmolinabastidas@gmail.com"),
					new InternetAddress("cmolinabastidas@yahoo.com") };

			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO,
					internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setSubject("Dato Java Enviando Correo.");

			// Creo la parte del mensaje
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart
					.setText("Siguiendo el Tutorial de datojava.blogspot.com envo el correo.");

			MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
			mimeBodyPartAdjunto
					.attachFile("/home/diana/Documentos/CURSOLINUX/vacio.txt");

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
		DJCorreoTexto correoTexto = new DJCorreoTexto();
		correoTexto.enviarCorreo();

	}

}
