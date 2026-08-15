package reporting;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import utilities.EmailBodyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import jakarta.mail.Session;

public class EmailExtentReport {

    public static void sendReport() {
    	
    	String reportPath = "test-output/ExtentReport.html";

    	final String senderEmail = System.getenv("EMAIL_USERNAME");
    	final String appPassword = System.getenv("EMAIL_APP_PASSWORD");
    	final String receiverEmail = System.getenv("EMAIL_RECIPIENT");

        if (senderEmail == null || appPassword == null || receiverEmail == null) {
            throw new IllegalStateException(
                "Email configuration is missing. Set EMAIL_USERNAME, EMAIL_APP_PASSWORD and EMAIL_RECIPIENT."
            );
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        /*try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject("API Automation Execution Report");

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText("Hello,\n\nPlease find attached API Automation Extent Report.\n\nThanks\nAbhimanyu Tiwary");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportPath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Extent Report Email Sent Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmail));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );
            LocalDate currentDate = LocalDate.now();

            // Format date (optional)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = currentDate.format(formatter);
            message.setSubject("API Automation Execution Report "+formattedDate);

            // Email Body
            MimeBodyPart bodyPart = new MimeBodyPart();

            bodyPart.setContent(
                    EmailBodyBuilder.buildBody(),
                    "text/html"
            );

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();

            FileDataSource source = new FileDataSource(reportPath);

            attachmentPart.setDataHandler(new DataHandler(source));

            attachmentPart.setFileName("ExtentReport.html");

            // Combine body + attachment
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(bodyPart);

            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("Extent Report Email Sent Successfully!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}