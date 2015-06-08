package onestonetwobirds.capstonuitest3.user;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by New on 2015-06-04.
 */
public class SendGmail extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            System.out.println("send here!");
            final String username = "jjang9cc@gmail.com";
            final String password = "gozldshsh1";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                System.out.println(params[0]);
                System.out.println(params[1]);

                // Send Mail.
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("jjang9cc@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(params[0].toString()));
                message.setSubject("Econo account reset mail");
                message.setText("Hi, " + params[1].toString() + ". This mail will reset your 'Econo' account password. If you want to do it, click url below."
                        + "\n\nhttp://192.168.25.22:3000/resetPassword?email="+params[0].toString()+"&code="+params[2].toString() +
                        "\n\nWhen you activate this, your account's password will be set as a random key.\n\n Remember this key that will be shown on a next page."
                );

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}

