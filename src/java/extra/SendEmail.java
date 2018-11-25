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

    public void sendEmailTo(String to, String subject, String link) {

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
            message.setFrom(new InternetAddress("montho.webproject@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            //Set Subject Feild
            //String subject = "Test";

            // Send the actual HTML message, as big as you like
            //String content = "<a href='http://localhost:8080/MyFirst/NewServlet'>click</a>";
            //message.setContent(link, "text/html");
            
            //html form https://github.com/mailgun/transactional-email-templates#inline-the-css
            if (subject.equals("activate")) {
                message.setSubject("Montho: Activate your Account");
                message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n"
                        + "<head>\n"
                        + "<meta name=\"viewport\" content=\"width=device-width\" />\n"
                        + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                        + "<title>Actionable emails e.g. reset password</title>\n"
                        + "\n"
                        + "\n"
                        + "<style type=\"text/css\">\n"
                        + "img {\n"
                        + "max-width: 100%;\n"
                        + "}\n"
                        + "body {\n"
                        + "-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em;\n"
                        + "}\n"
                        + "body {\n"
                        + "background-color: #f6f6f6;\n"
                        + "}\n"
                        + "@media only screen and (max-width: 640px) {\n"
                        + "  body {\n"
                        + "    padding: 0 !important;\n"
                        + "  }\n"
                        + "  h1 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h2 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h3 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h4 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h1 {\n"
                        + "    font-size: 22px !important;\n"
                        + "  }\n"
                        + "  h2 {\n"
                        + "    font-size: 18px !important;\n"
                        + "  }\n"
                        + "  h3 {\n"
                        + "    font-size: 16px !important;\n"
                        + "  }\n"
                        + "  .container {\n"
                        + "    padding: 0 !important; width: 100% !important;\n"
                        + "  }\n"
                        + "  .content {\n"
                        + "    padding: 0 !important;\n"
                        + "  }\n"
                        + "  .content-wrap {\n"
                        + "    padding: 10px !important;\n"
                        + "  }\n"
                        + "  .invoice {\n"
                        + "    width: 100% !important;\n"
                        + "  }\n"
                        + "}\n"
                        + "</style>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body itemscope itemtype=\"http://schema.org/EmailMessage\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n"
                        + "\n"
                        + "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n"
                        + "		<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\">\n"
                        + "			<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">\n"
                        + "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n"
                        + "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										Please activate your account by clicking the link below.\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										We may need to send you critical information about our service and it is important that we have an accurate email address.\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" itemprop=\"handler\" itemscope itemtype=\"http://schema.org/HttpActionHandler\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										<a href=" + link + "class=\"btn-primary\" itemprop=\"url\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; color: #FFF; text-decoration: none; line-height: 2em; font-weight: bold; text-align: center; cursor: pointer; display: inline-block; border-radius: 5px; text-transform: capitalize; background-color: #348eda; margin: 0; border-color: #348eda; border-style: solid; border-width: 10px 20px;\">Acitivate your Account</a>\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										&mdash; Montho.Webproject\n"
                        + "									</td>\n"
                        + "								</tr></table></td>\n"
                        + "					</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">\n"
                        + "					<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://localhost:8080/WEBPROJECT/montho.jsp\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@Montho</a>Market Site</td>\n"
                        + "						</tr></table></div></div>\n"
                        + "		</td>\n"
                        + "		<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n"
                        + "	</tr></table></body>\n"
                        + "</html>",
                        "text/html");
            }else if (subject.equals("reset")) {
                message.setSubject("Montho: Reset your Account Password");
                message.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n"
                        + "<head>\n"
                        + "<meta name=\"viewport\" content=\"width=device-width\" />\n"
                        + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                        + "<title>Actionable emails e.g. reset password</title>\n"
                        + "\n"
                        + "\n"
                        + "<style type=\"text/css\">\n"
                        + "img {\n"
                        + "max-width: 100%;\n"
                        + "}\n"
                        + "body {\n"
                        + "-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em;\n"
                        + "}\n"
                        + "body {\n"
                        + "background-color: #f6f6f6;\n"
                        + "}\n"
                        + "@media only screen and (max-width: 640px) {\n"
                        + "  body {\n"
                        + "    padding: 0 !important;\n"
                        + "  }\n"
                        + "  h1 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h2 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h3 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h4 {\n"
                        + "    font-weight: 800 !important; margin: 20px 0 5px !important;\n"
                        + "  }\n"
                        + "  h1 {\n"
                        + "    font-size: 22px !important;\n"
                        + "  }\n"
                        + "  h2 {\n"
                        + "    font-size: 18px !important;\n"
                        + "  }\n"
                        + "  h3 {\n"
                        + "    font-size: 16px !important;\n"
                        + "  }\n"
                        + "  .container {\n"
                        + "    padding: 0 !important; width: 100% !important;\n"
                        + "  }\n"
                        + "  .content {\n"
                        + "    padding: 0 !important;\n"
                        + "  }\n"
                        + "  .content-wrap {\n"
                        + "    padding: 10px !important;\n"
                        + "  }\n"
                        + "  .invoice {\n"
                        + "    width: 100% !important;\n"
                        + "  }\n"
                        + "}\n"
                        + "</style>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body itemscope itemtype=\"http://schema.org/EmailMessage\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n"
                        + "\n"
                        + "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n"
                        + "		<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\">\n"
                        + "			<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">\n"
                        + "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n"
                        + "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										Please reset your password by clicking the link below.\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										We may need to send you critical information about our service and it is important that we have an accurate email address.\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" itemprop=\"handler\" itemscope itemtype=\"http://schema.org/HttpActionHandler\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										<a href=" + link + "class=\"btn-primary\" itemprop=\"url\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; color: #FFF; text-decoration: none; line-height: 2em; font-weight: bold; text-align: center; cursor: pointer; display: inline-block; border-radius: 5px; text-transform: capitalize; background-color: #348eda; margin: 0; border-color: #348eda; border-style: solid; border-width: 10px 20px;\">Reset Password</a>\n"
                        + "									</td>\n"
                        + "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n"
                        + "										&mdash; Montho.Webproject\n"
                        + "									</td>\n"
                        + "								</tr></table></td>\n"
                        + "					</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">\n"
                        + "					<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://localhost:8080/WEBPROJECT/montho.jsp\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@Montho</a>Market Site</td>\n"
                        + "						</tr></table></div></div>\n"
                        + "		</td>\n"
                        + "		<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n"
                        + "	</tr></table></body>\n"
                        + "</html>",
                        "text/html");
            }
            Transport.send(message);

            System.out.println("Done Send email to: " + to);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
