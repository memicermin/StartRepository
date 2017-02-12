package notifiers;

import models.users.User;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import play.Configuration;

/**
 * Created by Enver on 2/2/2017.
 */
public class Emails {

    public static void sendEmailMessage(String email, String message) {
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg(message);
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Configuration.reference().getString("EMAIL_USERNAME_ENV"),
                    Configuration.reference().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
            System.out.println("Email send to: " + email);
        } catch (EmailException e) {
             e.printStackTrace();
            System.out.println("Email failed to: " + email);
        }
    }

    public static void confirmToken(String email, String token, String message){
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg(message);
            mail.setHtmlMsg("<html><body><p>" + message + "</p><p>Potrebno je da potvrditie vasu email adresu</p><a href=\"http://localhost:9000/confirmemail?tok=" + token + "\">click here</a><p>to confirm email</p></body></html>");
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Configuration.reference().getString("EMAIL_USERNAME_ENV"),
                    Configuration.reference().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
            System.out.println("Email send to: " + email);
        } catch (EmailException e) {
           // e.printStackTrace();
            System.out.println("Email failed to: " + email);
        }
    }
}
