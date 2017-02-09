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

    public static void sendTokenForVerify(String email, String token) {
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg("Thank you for the register in our Auto House, \nThis code must be used when the first application to confirm your email. \n" + token + "\n");
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Configuration.reference().getString("EMAIL_USERNAME_ENV"),
                    Configuration.reference().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public static void sendTokenForVerify(String email, String token, String message) {
        try {
            HtmlEmail mail = new HtmlEmail();

            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg(message +"\n" + token);
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Configuration.reference().getString("EMAIL_USERNAME_ENV"),
                    Configuration.reference().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public static void confirmToken(String email, String token){
        try {
            HtmlEmail mail = new HtmlEmail();

            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg("Potrebno je da potvrditie vasu email adresu");
            mail.setHtmlMsg("<html><body><a href=\"http://localhost:9000/confirmemail?tok=" + token + "\">click here</a><p>to confirm email</p></body></html>");
            mail.setHostName("smtp.gmail.com");
            mail.setStartTLSEnabled(true);
            mail.setSSLOnConnect(true);
            mail.setAuthenticator(new DefaultAuthenticator(
                    Configuration.reference().getString("EMAIL_USERNAME_ENV"),
                    Configuration.reference().getString("EMAIL_PASSWORD_ENV")
            ));
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}
