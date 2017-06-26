package correosDJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class DJCorreoHTML {

	public void mandarCorreo() {
		// El correo gmail de env�o
				String correoEnvia = "administrativo@diagnossis.com";
				String claveCorreo = "admin##2016";

				// La configuraci�n para enviar correo
				Properties properties = new Properties();
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.port", "587");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.user", "administrativo@diagnossis.com");
				properties.put("mail.password", "admin##2016");

		// Obtener la sesion
		Session session = Session.getInstance(properties, null);

		try {
			// Crear el cuerpo del mensaje
			MimeMessage mimeMessage = new MimeMessage(session);

			// Agregar quien env�a el correo
			mimeMessage.setFrom(new InternetAddress(correoEnvia, "Laboratorios Diagnossis"));

			// Los destinatarios
			InternetAddress[] internetAddresses = {
					new InternetAddress("dianacatalinacruzcaiza@gmail.com"),
					new InternetAddress("cmolinabastidas@gmail.com"),
					new InternetAddress("cmolinabastidas@yahoo.com") };
			// Agregar los destinatarios al mensaje
			mimeMessage.setRecipients(Message.RecipientType.TO,
					internetAddresses);

			// Agregar el asunto al correo
			mimeMessage.setSubject("Promoción Inicio de Clases.");

			// Crear el multipart para agregar la parte del mensaje anterior
			Multipart multipart = new MimeMultipart();

			// Leer la plantilla
			InputStream inputStream = getClass().getResourceAsStream(
					"PlantillaDJ");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

			// Almacenar el contenido de la plantilla en un StringBuffer
			String strLine;
			StringBuffer msjHTML = new StringBuffer();
			while ((strLine = bufferedReader.readLine()) != null) {
				msjHTML.append(strLine);
			}

			//Url del directorio donde estan las imagenes
			String urlImagenes = "../home/diana/workspace/CorreosDatoJava/CorreosDatoJava/src/img";
			System.out.println("URL imagenes : " +urlImagenes);
			File directorio = new File("gplus.png");
			
			// Obtener los nombres de las imagenes en el directorio
						String[] imagenesDirectorio = directorio.list();

			// Creo la parte del mensaje HTML
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msjHTML.toString(), "text/html");

			// Validar que el directorio si tenga las imagenes
			if (imagenesDirectorio != null) {
				for (int count = 0; count < imagenesDirectorio.length; count++) {

					MimeBodyPart imagePart = new MimeBodyPart();
					imagePart.setHeader("Content-ID", "<"
							+ imagenesDirectorio[count] + ">");
					imagePart.setDisposition(MimeBodyPart.INLINE);
					imagePart.attachFile(urlImagenes
							+ imagenesDirectorio[count]);
					multipart.addBodyPart(imagePart);
				}
			} else {
				System.out.println("No hay imagenes en el directorio");
			}

			// Agregar la parte del mensaje HTML al multiPart
			multipart.addBodyPart(mimeBodyPart);

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
		DJCorreoHTML correoHTML = new DJCorreoHTML();
		correoHTML.mandarCorreo();

	}

}
