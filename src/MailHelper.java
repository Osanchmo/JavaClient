import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;

import javax.mail.internet.*;

public class MailHelper {

    String usr;
    String pswrd;

    public MailHelper(String usr, String pswrd) {
        this.usr = usr;
        this.pswrd = pswrd;
    }

    /**
     * Sends an email with the input paramteres
     * @param mailTo
     * @param title
     * @param body
     * @param attachment
     */
    public void sendEmail(String mailTo, String title, String body, String attachment) {

        Properties props;
        Session session;
        MimeMessage message;

        try {
            //set up properties
            props = System.getProperties();
            props.put("smtp.mail.yahoo.com", "465");
            props.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");
            props.setProperty("mail.host", "smtp.mail.yahoo.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            //apliquem les propietats a la sessió
            session = Session.getDefaultInstance(props, null);

            //generem el missatge
            message = new MimeMessage( session );
            message.setFrom(new InternetAddress(usr));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(title);
            message.setText(body);

            //preparem el missatge per a fitxers adjunts
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();

            //si no hi han adjunts no l'afegeix
            if (attachment != "") {
                String file = attachment;
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }

            //envía el missatge
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.mail.yahoo.com", usr, pswrd );
            transport.sendMessage( message, message.getAllRecipients());
            transport.close();
            System.out.println("Message send correctly");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * List messages from user
     */
    public void listMessages() {

            Properties props;
            Session session;

            try {
                //set up properties
                props = System.getProperties();
                props.put("smtp.mail.yahoo.com", "587");
                props.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");
                props.setProperty("mail.host", "smtp.mail.yahoo.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.pop3s.port", "995");

                //apliquem les propietats
                session = Session.getDefaultInstance(props, null);

                //configurem el tipus d'emmagatzematge
                Store store = session.getStore("pop3s");

                //iniciem connexió
                store.connect("pop.mail.yahoo.com", 995, usr, pswrd);
                Folder emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_ONLY);

                //obtenim missatges
                Message[] messages = emailFolder.getMessages();

                //llistem missatges
                for (Message mes: messages) {
                    System.out.println("---------------------------------");
                    System.out.println("Título: " + mes.getSubject());
                    System.out.println("De: " + mes.getFrom()[0]);
                    //System.out.println("Cuerpo: " + mes.getContent().toString());
                }
                //tanquem la bústia
                emailFolder.close(false);
                store.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPswrd() {
        return pswrd;
    }

    public void setPswrd(String pswrd) {
        this.pswrd = pswrd;
    }
}