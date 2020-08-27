/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Luis Carlos Cabal
 */
@RequestScoped
@Named
public class EmailController {

    private String body;
    private String subject;
    private String to;
    
    private String templateCliente;
    private String templateAsesor; 

    

    public String getTemplateCliente() {
        return templateCliente;
    }

    public void setTemplateCliente(String templateCliente) {
        this.templateCliente = templateCliente;
    }

    public String getTemplateAsesor() {
        return templateAsesor;
    }

    public void setTemplateAsesor(String templateAsesor) {    
        this.templateAsesor = templateAsesor;
    }

    public EmailController() {
    }
    
   public void sendMailAdjunto(String para , String asunto, String correo, String adjunto , String nombreAdjunto) throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = para;
        // Sender's email ID needs to be mentioned
         String from = "notificaciones@ekazipper.com";
        final String username = "notificaciones@ekazipper.com";//change accordingly
        final String password = "Nto*20-03Aut";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(asunto);
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText(correo);
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            
            String filename = FacesContext.getCurrentInstance().getExternalContext().getRealPath(adjunto);
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(nombreAdjunto);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
            FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+para);
            RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            limpiar();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    
    public void sendMail(String para , String asunto, String cuerpo) throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = para;
        
        // Sender's email ID needs to be mentioned
        String from = "notificaciones@ekazipper.com";
        final String username = "notificaciones@ekazipper.com";//change accordingly
        final String password = "Nto*20-03Aut";//change accordingly
        
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(asunto);
            // Now set the actual message
            message.setContent(cuerpo, "text/html");
            // Create a multipar message
            Transport.send(message);
            //FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+para);
            //RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            limpiar();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void limpiar() {
        setBody("");
        setSubject("");
        setTo("");
    }

    
    public String getBody() {
        return body;
    }

    
    public void setBody(String body) {
        this.body = body;
    }

   
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    
    public String generarTemplate(String p1, String p2,String p3, String p4, String p5){
        templateCliente = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
"    <!--[if gte mso 9]><xml>\n" +
"     <o:OfficeDocumentSettings>\n" +
"      <o:AllowPNG/>\n" +
"      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"     </o:OfficeDocumentSettings>\n" +
"    </xml><![endif]-->\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width\">\n" +
"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
"    <title></title>\n" +
"    \n" +
"    \n" +
"    <style type=\"text/css\" id=\"media-query\">\n" +
"      body {\n" +
"  margin: 0;\n" +
"  padding: 0; }\n" +
"\n" +
"table, tr, td {\n" +
"  vertical-align: top;\n" +
"  border-collapse: collapse; }\n" +
"\n" +
".ie-browser table, .mso-container table {\n" +
"  table-layout: fixed; }\n" +
"\n" +
"* {\n" +
"  line-height: inherit; }\n" +
"\n" +
"a[x-apple-data-detectors=true] {\n" +
"  color: inherit !important;\n" +
"  text-decoration: none !important; }\n" +
"\n" +
"[owa] .img-container div, [owa] .img-container button {\n" +
"  display: block !important; }\n" +
"\n" +
"[owa] .fullwidth button {\n" +
"  width: 100% !important; }\n" +
"\n" +
"[owa] .block-grid .col {\n" +
"  display: table-cell;\n" +
"  float: none !important;\n" +
"  vertical-align: top; }\n" +
"\n" +
".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\n" +
"  width: 600px !important; }\n" +
"\n" +
".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\n" +
"  line-height: 100%; }\n" +
"\n" +
".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\n" +
"  width: 200px !important; }\n" +
"\n" +
".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\n" +
"  width: 400px !important; }\n" +
"\n" +
".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\n" +
"  width: 300px !important; }\n" +
"\n" +
".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\n" +
"  width: 200px !important; }\n" +
"\n" +
".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\n" +
"  width: 150px !important; }\n" +
"\n" +
".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\n" +
"  width: 120px !important; }\n" +
"\n" +
".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\n" +
"  width: 100px !important; }\n" +
"\n" +
".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\n" +
"  width: 85px !important; }\n" +
"\n" +
".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\n" +
"  width: 75px !important; }\n" +
"\n" +
".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\n" +
"  width: 66px !important; }\n" +
"\n" +
".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\n" +
"  width: 60px !important; }\n" +
"\n" +
".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\n" +
"  width: 54px !important; }\n" +
"\n" +
".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\n" +
"  width: 50px !important; }\n" +
"\n" +
"@media only screen and (min-width: 620px) {\n" +
"  .block-grid {\n" +
"    width: 600px !important; }\n" +
"  .block-grid .col {\n" +
"    vertical-align: top; }\n" +
"    .block-grid .col.num12 {\n" +
"      width: 600px !important; }\n" +
"  .block-grid.mixed-two-up .col.num4 {\n" +
"    width: 200px !important; }\n" +
"  .block-grid.mixed-two-up .col.num8 {\n" +
"    width: 400px !important; }\n" +
"  .block-grid.two-up .col {\n" +
"    width: 300px !important; }\n" +
"  .block-grid.three-up .col {\n" +
"    width: 200px !important; }\n" +
"  .block-grid.four-up .col {\n" +
"    width: 150px !important; }\n" +
"  .block-grid.five-up .col {\n" +
"    width: 120px !important; }\n" +
"  .block-grid.six-up .col {\n" +
"    width: 100px !important; }\n" +
"  .block-grid.seven-up .col {\n" +
"    width: 85px !important; }\n" +
"  .block-grid.eight-up .col {\n" +
"    width: 75px !important; }\n" +
"  .block-grid.nine-up .col {\n" +
"    width: 66px !important; }\n" +
"  .block-grid.ten-up .col {\n" +
"    width: 60px !important; }\n" +
"  .block-grid.eleven-up .col {\n" +
"    width: 54px !important; }\n" +
"  .block-grid.twelve-up .col {\n" +
"    width: 50px !important; } }\n" +
"\n" +
"@media (max-width: 620px) {\n" +
"  .block-grid, .col {\n" +
"    min-width: 320px !important;\n" +
"    max-width: 100% !important;\n" +
"    display: block !important; }\n" +
"  .block-grid {\n" +
"    width: calc(100% - 40px) !important; }\n" +
"  .col {\n" +
"    width: 100% !important; }\n" +
"    .col > div {\n" +
"      margin: 0 auto; }\n" +
"  img.fullwidth, img.fullwidthOnMobile {\n" +
"    max-width: 100% !important; }\n" +
"  .no-stack .col {\n" +
"    min-width: 0 !important;\n" +
"    display: table-cell !important; }\n" +
"  .no-stack.two-up .col {\n" +
"    width: 50% !important; }\n" +
"  .no-stack.mixed-two-up .col.num4 {\n" +
"    width: 33% !important; }\n" +
"  .no-stack.mixed-two-up .col.num8 {\n" +
"    width: 66% !important; }\n" +
"  .no-stack.three-up .col.num4 {\n" +
"    width: 33% !important; }\n" +
"  .no-stack.four-up .col.num3 {\n" +
"    width: 25% !important; }\n" +
"  .mobile_hide {\n" +
"    min-height: 0px;\n" +
"    max-height: 0px;\n" +
"    max-width: 0px;\n" +
"    display: none;\n" +
"    overflow: hidden;\n" +
"    font-size: 0px; } }\n" +
"\n" +
".tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}\n" +
".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-top-width:1px;border-bottom-width:1px;border-color:#ccc;color:#333;background-color:#fff;}\n" +
".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-top-width:1px;border-bottom-width:1px;border-color:#ccc;color:#333;background-color:#f0f0f0;}\n" +
".tg .tg-w6x5{font-family:Tahoma, Geneva, sans-serif !important;;border-color:inherit;text-align:left;vertical-align:top}\n" +
".tg .tg-hxn6{font-weight:bold;font-family:Tahoma, Geneva, sans-serif !important;;border-color:#ffffff;text-align:left;vertical-align:top}"+                
"    </style>\n" +
"</head>\n" +
"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #B8CCE2\">\n" +
"  <style type=\"text/css\" id=\"media-query-bodytag\">\n" +
"    @media (max-width: 520px) {\n" +
"      .block-grid {\n" +
"        min-width: 320px!important;\n" +
"        max-width: 100%!important;\n" +
"        width: 100%!important;\n" +
"        display: block!important;\n" +
"      }\n" +
"\n" +
"      .col {\n" +
"        min-width: 320px!important;\n" +
"        max-width: 100%!important;\n" +
"        width: 100%!important;\n" +
"        display: block!important;\n" +
"      }\n" +
"\n" +
"        .col > div {\n" +
"          margin: 0 auto;\n" +
"        }\n" +
"\n" +
"      img.fullwidth {\n" +
"        max-width: 100%!important;\n" +
"      }\n" +
"			img.fullwidthOnMobile {\n" +
"        max-width: 100%!important;\n" +
"      }\n" +
"      .no-stack .col {\n" +
"				min-width: 0!important;\n" +
"				display: table-cell!important;\n" +
"			}\n" +
"			.no-stack.two-up .col {\n" +
"				width: 50%!important;\n" +
"			}\n" +
"			.no-stack.mixed-two-up .col.num4 {\n" +
"				width: 33%!important;\n" +
"			}\n" +
"			.no-stack.mixed-two-up .col.num8 {\n" +
"				width: 66%!important;\n" +
"			}\n" +
"			.no-stack.three-up .col.num4 {\n" +
"				width: 33%!important;\n" +
"			}\n" +
"			.no-stack.four-up .col.num3 {\n" +
"				width: 25%!important;\n" +
"			}\n" +
"      .mobile_hide {\n" +
"        min-height: 0px!important;\n" +
"        max-height: 0px!important;\n" +
"        max-width: 0px!important;\n" +
"        display: none!important;\n" +
"        overflow: hidden!important;\n" +
"        font-size: 0px!important;\n" +
"      }\n" +
"    }\n" +
"  </style>\n" +
"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #B8CCE2;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"	<tbody>\n" +
"	<tr style=\"vertical-align: top\">\n" +
"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #B8CCE2;\"><![endif]-->\n" +
"\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    \n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider mobile_hide\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"    <tbody>\n" +
"        <tr style=\"vertical-align: top\">\n" +
"            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 5px;padding-left: 5px;padding-top: 5px;padding-bottom: 5px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                <table class=\"divider_content\" height=\"40px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid transparent;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                    <tbody>\n" +
"                        <tr style=\"vertical-align: top\">\n" +
"                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 40px;line-height: 40px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                                <span>&#160;</span>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                    </tbody>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 20px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 20px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div align=\"left\" class=\"img-container left fixedwidth \" style=\"padding-right: 25px;  padding-left: 25px;\">\n" +
"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px;line-height:0px;\"><td style=\"padding-right: 25px; padding-left: 25px;\" align=\"left\"><![endif]-->\n" +
"<div style=\"line-height:25px;font-size:1px\">&#160;</div>  <img class=\"left fixedwidth\" align=\"left\" border=\"0\" src=\"https://ekazipper.com/wp-content/uploads/2019/02/Logos-Header-4-unidades.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 434.6px\" width=\"434.6\">\n" +
"<div style=\"line-height:25px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1a5493;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: #1a5493;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#1a5493;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 10px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 10px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:150%; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:18px;color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 18px\"><span style=\"color: rgb(255, 255, 255); font-size: 12px; line-height: 18px;\"><strong><span style=\"font-size: 24px; line-height: 36px;\">Solicitud de pedido "+p1+"</span></strong></span></p></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#555555;line-height:120%;font-family:Tahoma, Verdana, Segoe, sans-serif; padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 16px; line-height: 19px;\">Hola "+p2 +"."+"&#160;</span></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><br><span style=\"font-size: 16px; line-height: 19px;\">Tu orden de compra <strong>" + p3+"</strong> ha sido procesada con la solicitud de pedido "+p1+". Si encontramos alguna novedad te lo estaremos informando así como la fecha estimada de despacho, cualquier inquietud no dudes en comunicarte con tu asesor comercial.</span></p></div>" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid  no-stack\">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 35px; padding-left: 35px; padding-top:15px; padding-bottom:2px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:15px; padding-bottom:2px; padding-right: 35px; padding-left: 35px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 15px; padding-bottom: 15px;\"><![endif]-->\n" +
"	<div style=\"color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%; padding-right: 10px; padding-left: 10px; padding-top: 15px; padding-bottom: 15px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 14px\"><span style=\"font-size: 16px; line-height: 19px;\"><img title=\"Image\" src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/271/illo.png\" alt=\"Image\" width=\"532\" height=\"auto\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none\"></span></p></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 15px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#000000;line-height:120%;font-family:Tahoma, Verdana, Segoe, sans-serif; padding-right: 15px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:16px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 17px; line-height: 20px;\"><strong> Detalles del pedido</strong></span></p><br></br>\n"+
                "<table class=\"tg\">\n" +
                "<tr>\n" +
                "<th class=\"tg-hxn6\">C&#243digo del item</th>\n" +
                "<th class=\"tg-hxn6\">Descripci&#243n</th>\n" +
                "<th class=\"tg-hxn6\">Extensi&#243n 1</th>\n" +
                "<th class=\"tg-hxn6\">Extensi&#243n 2</th>\n" +
                "<th class=\"tg-hxn6\">U. medida</th>\n" +
                "<th class=\"tg-hxn6\">Cantidad</th>\n" +
                "</tr>\n" +
                "<tr>\n"+
                p4+
                "</table></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    &#160;\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<div style=\"color:#000000;line-height:120%;font-family:Tahoma, Verdana, Segoe, sans-serif; padding-right: 20px; padding-left: 20px; padding-top: 1px; padding-bottom: 2px;\">	\n" +
"		<div style=\"font-size:14px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 17px; line-height: 20px;\"><strong> Detalles del env&#237o</strong></span></p>\n"
                + p5+
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    &#160;\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1a5493;\" class=\"block-grid two-up no-stack\">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#1a5493;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#1a5493;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\" width:300px; padding-right: 0px; padding-left: 25px; padding-top:15px; padding-bottom:15px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num6\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:15px; padding-bottom:15px; padding-right: 0px; padding-left: 25px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#F8F8F8;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;color:#F8F8F8;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 16px;line-height: 17px\"><strong>EKA CORPORACI&#211N S.A.</strong></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Calle 46 A No 1-61</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">(+57) 2 486 2222</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">www.ekazipper.com</p></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"              <!--[if (mso)|(IE)]></td><td align=\"center\" width=\"300\" style=\" width:300px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num6\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    \n" +
"<div align=\"right\" style=\"padding-right: 35px; padding-left: 10px; padding-bottom: 10px;\" class=\"\">\n" +
"  <div style=\"line-height:20px;font-size:1px\">&#160;</div>\n" +
"  <div style=\"display: table; max-width:154px;\">\n" +
"  <!--[if (mso)|(IE)]><table width=\"109\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse; padding-right: 35px; padding-left: 10px; padding-bottom: 10px;\"  align=\"right\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:109px;\"><tr><td width=\"32\" style=\"width:32px; padding-right: 10px;\" valign=\"top\"><![endif]-->\n" +
"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 10px\">\n" +
"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
"        <a href=\"https://www.facebook.com/EKACORPORACION/\" title=\"Facebook\" target=\"_blank\">\n" +
"        </a>\n" +
"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\n" +
"      </td></tr>\n" +
"    </tbody></table>\n" +
"      <!--[if (mso)|(IE)]></td><td width=\"32\" style=\"width:32px; padding-right: 0;\" valign=\"top\"><![endif]-->\n" +
"    <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;Margin-right: 0\">\n" +
"      <tbody><tr style=\"vertical-align: top\"><td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
"        <a href=\"https://www.youtube.com/watch?v=DP1G7E2DcOs\" title=\"YouTube\" target=\"_blank\">\n" +
"        </a>\n" +
"      <div style=\"line-height:5px;font-size:1px\">&#160;</div>\n" +
"      </td></tr>\n" +
"    </tbody></table>\n" +
"    <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"  </div>\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    \n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider \" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"    <tbody>\n" +
"        <tr style=\"vertical-align: top\">\n" +
"            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 5px;padding-left: 5px;padding-top: 5px;padding-bottom: 5px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                <table class=\"divider_content\" height=\"30px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid transparent;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                    <tbody>\n" +
"                        <tr style=\"vertical-align: top\">\n" +
"                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 30px;line-height: 30px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                                <span>&#160;</span>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                    </tbody>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
"		</td>\n" +
"  </tr>\n" +
"  </tbody>\n" +
"  </table>\n" +
"  <!--[if (mso)|(IE)]></div><![endif]-->\n" +
"\n" +
"\n" +
"</body></html><br></br><br></br>";
    return templateCliente;
    }
    
    public void generarTemplateAsesor(String p1, String p2,String p3, String p4, String p5,String p6){
        templateAsesor = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
"    <!--[if gte mso 9]><xml>\n" +
"     <o:OfficeDocumentSettings>\n" +
"      <o:AllowPNG/>\n" +
"      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"     </o:OfficeDocumentSettings>\n" +
"    </xml><![endif]-->\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width\">\n" +
"    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
"    <title></title>\n" +
"    \n" +
"    \n" +
"    <style type=\"text/css\" id=\"media-query\">\n" +
"      body {\n" +
"  margin: 0;\n" +
"  padding: 0; }\n" +
"\n" +
"table, tr, td {\n" +
"  vertical-align: top;\n" +
"  border-collapse: collapse; }\n" +
"\n" +
".ie-browser table, .mso-container table {\n" +
"  table-layout: fixed; }\n" +
"\n" +
"* {\n" +
"  line-height: inherit; }\n" +
"\n" +
"a[x-apple-data-detectors=true] {\n" +
"  color: inherit !important;\n" +
"  text-decoration: none !important; }\n" +
"\n" +
"[owa] .img-container div, [owa] .img-container button {\n" +
"  display: block !important; }\n" +
"\n" +
"[owa] .fullwidth button {\n" +
"  width: 100% !important; }\n" +
"\n" +
"[owa] .block-grid .col {\n" +
"  display: table-cell;\n" +
"  float: none !important;\n" +
"  vertical-align: top; }\n" +
"\n" +
".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\n" +
"  width: 600px !important; }\n" +
"\n" +
".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\n" +
"  line-height: 100%; }\n" +
"\n" +
".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\n" +
"  width: 200px !important; }\n" +
"\n" +
".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\n" +
"  width: 400px !important; }\n" +
"\n" +
".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\n" +
"  width: 300px !important; }\n" +
"\n" +
".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\n" +
"  width: 200px !important; }\n" +
"\n" +
".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\n" +
"  width: 150px !important; }\n" +
"\n" +
".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\n" +
"  width: 120px !important; }\n" +
"\n" +
".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\n" +
"  width: 100px !important; }\n" +
"\n" +
".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\n" +
"  width: 85px !important; }\n" +
"\n" +
".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\n" +
"  width: 75px !important; }\n" +
"\n" +
".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\n" +
"  width: 66px !important; }\n" +
"\n" +
".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\n" +
"  width: 60px !important; }\n" +
"\n" +
".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\n" +
"  width: 54px !important; }\n" +
"\n" +
".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\n" +
"  width: 50px !important; }\n" +
"\n" +
"@media only screen and (min-width: 620px) {\n" +
"  .block-grid {\n" +
"    width: 600px !important; }\n" +
"  .block-grid .col {\n" +
"    vertical-align: top; }\n" +
"    .block-grid .col.num12 {\n" +
"      width: 600px !important; }\n" +
"  .block-grid.mixed-two-up .col.num4 {\n" +
"    width: 200px !important; }\n" +
"  .block-grid.mixed-two-up .col.num8 {\n" +
"    width: 400px !important; }\n" +
"  .block-grid.two-up .col {\n" +
"    width: 300px !important; }\n" +
"  .block-grid.three-up .col {\n" +
"    width: 200px !important; }\n" +
"  .block-grid.four-up .col {\n" +
"    width: 150px !important; }\n" +
"  .block-grid.five-up .col {\n" +
"    width: 120px !important; }\n" +
"  .block-grid.six-up .col {\n" +
"    width: 100px !important; }\n" +
"  .block-grid.seven-up .col {\n" +
"    width: 85px !important; }\n" +
"  .block-grid.eight-up .col {\n" +
"    width: 75px !important; }\n" +
"  .block-grid.nine-up .col {\n" +
"    width: 66px !important; }\n" +
"  .block-grid.ten-up .col {\n" +
"    width: 60px !important; }\n" +
"  .block-grid.eleven-up .col {\n" +
"    width: 54px !important; }\n" +
"  .block-grid.twelve-up .col {\n" +
"    width: 50px !important; } }\n" +
"\n" +
"@media (max-width: 620px) {\n" +
"  .block-grid, .col {\n" +
"    min-width: 320px !important;\n" +
"    max-width: 100% !important;\n" +
"    display: block !important; }\n" +
"  .block-grid {\n" +
"    width: calc(100% - 40px) !important; }\n" +
"  .col {\n" +
"    width: 100% !important; }\n" +
"    .col > div {\n" +
"      margin: 0 auto; }\n" +
"  img.fullwidth, img.fullwidthOnMobile {\n" +
"    max-width: 100% !important; }\n" +
"  .no-stack .col {\n" +
"    min-width: 0 !important;\n" +
"    display: table-cell !important; }\n" +
"  .no-stack.two-up .col {\n" +
"    width: 50% !important; }\n" +
"  .no-stack.mixed-two-up .col.num4 {\n" +
"    width: 33% !important; }\n" +
"  .no-stack.mixed-two-up .col.num8 {\n" +
"    width: 66% !important; }\n" +
"  .no-stack.three-up .col.num4 {\n" +
"    width: 33% !important; }\n" +
"  .no-stack.four-up .col.num3 {\n" +
"    width: 25% !important; }\n" +
"  .mobile_hide {\n" +
"    min-height: 0px;\n" +
"    max-height: 0px;\n" +
"    max-width: 0px;\n" +
"    display: none;\n" +
"    overflow: hidden;\n" +
"    font-size: 0px; } }\n" +
"\n" +
".tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}\n" +
".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-top-width:1px;border-bottom-width:1px;border-color:#ccc;color:#333;background-color:#fff;}\n" +
".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-top-width:1px;border-bottom-width:1px;border-color:#ccc;color:#333;background-color:#f0f0f0;}\n" +
".tg .tg-w6x5{font-family:Tahoma, Geneva, sans-serif !important;;border-color:inherit;text-align:left;vertical-align:top}\n" +
".tg .tg-hxn6{font-weight:bold;font-family:Tahoma, Geneva, sans-serif !important;;border-color:#ffffff;text-align:left;vertical-align:top}"+
"    </style>\n" +
"</head>\n" +
"<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #B8CCE2\">\n" +
"  <style type=\"text/css\" id=\"media-query-bodytag\">\n" +
"    @media (max-width: 520px) {\n" +
"      .block-grid {\n" +
"        min-width: 320px!important;\n" +
"        max-width: 100%!important;\n" +
"        width: 100%!important;\n" +
"        display: block!important;\n" +
"      }\n" +
"\n" +
"      .col {\n" +
"        min-width: 320px!important;\n" +
"        max-width: 100%!important;\n" +
"        width: 100%!important;\n" +
"        display: block!important;\n" +
"      }\n" +
"\n" +
"        .col > div {\n" +
"          margin: 0 auto;\n" +
"        }\n" +
"\n" +
"      img.fullwidth {\n" +
"        max-width: 100%!important;\n" +
"      }\n" +
"			img.fullwidthOnMobile {\n" +
"        max-width: 100%!important;\n" +
"      }\n" +
"      .no-stack .col {\n" +
"				min-width: 0!important;\n" +
"				display: table-cell!important;\n" +
"			}\n" +
"			.no-stack.two-up .col {\n" +
"				width: 50%!important;\n" +
"			}\n" +
"			.no-stack.mixed-two-up .col.num4 {\n" +
"				width: 33%!important;\n" +
"			}\n" +
"			.no-stack.mixed-two-up .col.num8 {\n" +
"				width: 66%!important;\n" +
"			}\n" +
"			.no-stack.three-up .col.num4 {\n" +
"				width: 33%!important;\n" +
"			}\n" +
"			.no-stack.four-up .col.num3 {\n" +
"				width: 25%!important;\n" +
"			}\n" +
"      .mobile_hide {\n" +
"        min-height: 0px!important;\n" +
"        max-height: 0px!important;\n" +
"        max-width: 0px!important;\n" +
"        display: none!important;\n" +
"        overflow: hidden!important;\n" +
"        font-size: 0px!important;\n" +
"      }\n" +
"    }\n" +
"  </style>\n" +
"  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
"  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
"  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #B8CCE2;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"	<tbody>\n" +
"	<tr style=\"vertical-align: top\">\n" +
"		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
"    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #B8CCE2;\"><![endif]-->\n" +
"\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    \n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider mobile_hide\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"    <tbody>\n" +
"        <tr style=\"vertical-align: top\">\n" +
"            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 5px;padding-left: 5px;padding-top: 5px;padding-bottom: 5px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                <table class=\"divider_content\" height=\"40px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid transparent;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                    <tbody>\n" +
"                        <tr style=\"vertical-align: top\">\n" +
"                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 40px;line-height: 40px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                                <span>&#160;</span>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                    </tbody>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 20px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 20px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div align=\"left\" class=\"img-container left fixedwidth \" style=\"padding-right: 25px;  padding-left: 25px;\">\n" +
"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px;line-height:0px;\"><td style=\"padding-right: 25px; padding-left: 25px;\" align=\"left\"><![endif]-->\n" +
"<div style=\"line-height:25px;font-size:1px\">&#160;</div>  <img class=\"left fixedwidth\" align=\"left\" border=\"0\" src=\"https://ekazipper.com/wp-content/uploads/2019/02/Logos-Header-4-unidades.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 434.6px\" width=\"434.6\">\n" +
"<div style=\"line-height:25px;font-size:1px\">&#160;</div><!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FF0000;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FF0000;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FF0000;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 10px; padding-top:0px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 10px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:150%; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:18px;color:#555555;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 12px;line-height: 18px\"><span style=\"color: rgb(255, 255, 255); font-size: 12px; line-height: 18px;\"><strong><span style=\"font-size: 24px; line-height: 36px;\">Solicitud de pedido numero:"+p1+"</span></strong></span></p></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#000000;font-family:Tahoma, Verdana, Segoe, sans-serif;line-height:120%; padding-right: 20px; padding-left: 20px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 16px; background-color: transparent; line-height: 19px;\">Se informa que la solicitud de pedido con c&#243digo <strong>"+p1+" </strong> creado por el usuario <strong>"+p2+" - "+p6+"</strong> no fue procesado debido a errores en en el cargue de la informaci&#243n, a continuaci&#243n se encuentra el mensaje de error generado:</span></p></div><br>	\n" +
                "<span style=\"color: red; \"><strong>"+p3+"</strong></span>"+
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 15px; padding-left: 15px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#000000;font-family:Tahoma, Verdana, Segoe, sans-serif;line-height:120%; padding-right: 15px; padding-left: 15px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 17px; line-height: 20px;\"><strong> Detalles del pedido</strong></span></p></div><br>\n" +
                "<table class=\"tg\">\n" +
                "<tr>\n" +
                "<th class=\"tg-hxn6\">C&#243digo del item</th>\n" +
                "<th class=\"tg-hxn6\">Descripci&#243n</th>\n" +
                "<th class=\"tg-hxn6\">Extensi&#243n 1</th>\n" +
                "<th class=\"tg-hxn6\">Extensi&#243n 2</th>\n" +
                "<th class=\"tg-hxn6\">U. medida</th>\n" +
                "<th class=\"tg-hxn6\">Cantidad</th>\n" +
                "</tr>\n" +
                "<tr>\n"+
                p4+
                "</table></div>	\n" +	
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #FFFFFF;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 15px; padding-left: 15px; padding-top: 5px; padding-bottom: 5px;\"><![endif]-->\n" +
"	<div style=\"color:#000000;font-family:Tahoma, Verdana, Segoe, sans-serif;line-height:120%; padding-right: 15px; padding-left: 15px; padding-top: 5px; padding-bottom: 5px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;font-family:Tahoma, Verdana, Segoe, sans-serif;color:#000000;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><span style=\"font-size: 17px; line-height: 20px;\"><strong> Detalles del env&#237o</strong></span></p></div>	\n" +
                p5+
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #132f40;\" class=\"block-grid two-up no-stack\">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#132f40;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:#132f40;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\" width:300px; padding-right: 0px; padding-left: 25px; padding-top:15px; padding-bottom:15px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num6\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:15px; padding-bottom:15px; padding-right: 0px; padding-left: 25px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    <div class=\"\">\n" +
"	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
"	<div style=\"color:#F8F8F8;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%; padding-right: 0px; padding-left: 0px; padding-top: 10px; padding-bottom: 10px;\">	\n" +
"		<div style=\"font-size:12px;line-height:14px;color:#F8F8F8;font-family:'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><strong>EKA CORPORACI&#211N S.A.</strong></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Calle 46 A No 1–61</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">(+57) 2 486 2222</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">www.ekazipper.com</p></div>	\n" +
"	</div>\n" +
"	<!--[if mso]></td></tr></table><![endif]-->\n" +
"</div>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"              <!--[if (mso)|(IE)]></td><td align=\"center\" width=\"300\" style=\" width:300px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num6\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    &#160;\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style=\"background-color:transparent;\">\n" +
"      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" +
"        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
"          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 600px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" +
"\n" +
"              <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\" width:600px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
"            <div class=\"col num12\" style=\"min-width: 320px;max-width: 600px;display: table-cell;vertical-align: top;\">\n" +
"              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
"              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
"\n" +
"                  \n" +
"                    \n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider \" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"    <tbody>\n" +
"        <tr style=\"vertical-align: top\">\n" +
"            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 5px;padding-left: 5px;padding-top: 5px;padding-bottom: 5px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                <table class=\"divider_content\" height=\"30px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid transparent;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                    <tbody>\n" +
"                        <tr style=\"vertical-align: top\">\n" +
"                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 30px;line-height: 30px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
"                                <span>&#160;</span>\n" +
"                            </td>\n" +
"                        </tr>\n" +
"                    </tbody>\n" +
"                </table>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table>\n" +
"                  \n" +
"              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
"              </div>\n" +
"            </div>\n" +
"          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>\n" +
"   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
"		</td>\n" +
"  </tr>\n" +
"  </tbody>\n" +
"  </table>\n" +
"  <!--[if (mso)|(IE)]></div><![endif]-->\n" +
"\n" +
"\n" +
"</body></html><br></br><br></br>";
    }

   
}
