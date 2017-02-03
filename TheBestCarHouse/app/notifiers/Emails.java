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

    public static void sendtest(String email, String message) {
        try {
            HtmlEmail mail = new HtmlEmail();

            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg(message);
            mail.setHtmlMsg("<html><body><strong>Welcome</strong> <p> To </p> <p> Auto </p> <p> House</p>  </body></html>");
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

    public static void sendTokenForVerify(String email, String token) {
        User user = User.findByEmail(email);
        try {
            HtmlEmail mail = new HtmlEmail();

            mail.setSubject("Welcome to our Car House");
            mail.setFrom("House <carhouse.thebestcar@gmail.com>");
            mail.addTo("Contact <carhouse.thebestcar@gmail.com>");
            mail.addTo(email);
            mail.setMsg("Thank you for the register in our Auto House, \nThis code must be used when the first application to confirm your email.");
            mail.setMsg(user.getToken());
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
