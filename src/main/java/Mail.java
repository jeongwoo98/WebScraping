import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
    public static void main(String[]args){
        //authentication information
        final String username = "jeongwoomoon@hotmail.com";
        final String password = "Moon19981224!";
        String fromEmail = "jeongwoomoon@hotmail.com";
        String toEmail = "jeongwoomoon@hotmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.live.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        //Start our mail message:
        Message msg = new MimeMessage(session);
        try{
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Daily update");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(new Weather().getInformation()+"\n"
                    + new Coronavirus().getInformation() + "\n"
                    + new AmazonDeals().getInformation() + "\n");

            //attach multiple image parts. Send three images from directory
            new Images();
            for(int i=1; i<=3; i++){
                MimeBodyPart pdfAttachment = new MimeBodyPart();
                pdfAttachment.attachFile("/Users/moon_98/IdeaProjects/WebScraping/cats/" + i + ".jpg");
                emailContent.addBodyPart(pdfAttachment);
            }

            //Attach text part
            emailContent.addBodyPart(textBodyPart);


            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Message sent");
        }catch(MessagingException | IOException e){
            e.printStackTrace();
        }
    }
}
