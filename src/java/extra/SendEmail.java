/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author GT62VR
 */
public class SendEmail {

    /*
    email source1
    https://stackoverflow.com/questions/3649014/send-email-using-java

    email source2
    https://www.tutorialspoint.com/java/java_sending_email.htm
     */
    final String username = "montho.webproject@gmail.com";
    final String password = "monthoproject303";
    //birthday 20/10/1999
    //sex male

    public void sendEmailTo(String to) {

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bccbew164@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setContent("<a href='http://localhost:8080/MyFirst/NewServlet'>click</a>", "text/html");

            Transport.send(message);

            System.out.println("Done Send email to: "+to);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
