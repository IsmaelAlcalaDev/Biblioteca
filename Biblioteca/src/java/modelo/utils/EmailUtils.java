package modelo.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class EmailUtils {

    public void enviarNotificacionesPorEmail(List<String> destinatarios, String email, String contrasena) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, contrasena);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setSubject("Notificación de préstamos pendientes");

            for (String destinatario : destinatarios) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario.trim()));
            }

            String contenido = "Estimado socio, \n\nTienes préstamos pendientes que necesitan ser devueltos. Por favor, asegúrate de devolver los libros a tiempo.\n\nAtentamente,\nLa biblioteca";
            message.setText(contenido);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); 
            throw new RuntimeException("Error al enviar las notificaciones por correo electrónico", e);
        }
    }
}
