package com.pulsenet.api.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Service for sending email notifications and reports
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${report.sender.from-email:no-reply@pulsenet.com}")
    private String fromEmail;

    @Value("${report.sender.from-name:PulseNet}")
    private String fromName;

    /**
     * Constructor for EmailService
     * 
     * @param mailSender The JavaMailSender bean
     * @param templateEngine The Thymeleaf template engine
     */
    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Send a simple text email
     * 
     * @param to The recipient email address
     * @param subject The email subject
     * @param content The email content (plain text)
     */
    public void sendSimpleEmail(String to, String subject, String content) {
        try {
            logger.info("Sending simple email to: {} with subject: {}", to, subject);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            
            logger.info("Simple email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send simple email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Send an HTML email using a Thymeleaf template
     * 
     * @param to The recipient email address
     * @param subject The email subject
     * @param templateName The name of the Thymeleaf template (without extension)
     * @param variables The variables to be used in the template
     * @throws MessagingException If there's an error with the email
     */
    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables) 
            throws MessagingException {
        try {
            logger.info("Sending template email to: {} with subject: {} using template: {}", 
                    to, subject, templateName);
            
            // Process the Thymeleaf template
            Context context = new Context();
            variables.forEach(context::setVariable);
            String htmlContent = templateEngine.process(templateName, context);
            
            // Create a MIME message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indicates HTML content
            
            mailSender.send(mimeMessage);
            
            logger.info("Template email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send template email to: {}", to, e);
            throw new MessagingException("Failed to send email", e);
        }
    }

    /**
     * Send an HTML email with attachments
     * 
     * @param to The recipient email address
     * @param subject The email subject
     * @param htmlContent The HTML content
     * @param attachments Map of attachment name to byte array content
     * @throws MessagingException If there's an error with the email
     */
    public void sendEmailWithAttachments(String to, String subject, String htmlContent, 
                                       Map<String, byte[]> attachments) throws MessagingException {
        try {
            logger.info("Sending email with attachments to: {} with subject: {}", to, subject);
            
            // Create a MIME message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indicates HTML content
            
            // Add attachments
            if (attachments != null) {
                for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                    helper.addAttachment(entry.getKey(), new javax.mail.util.ByteArrayDataSource(
                            entry.getValue(), "application/octet-stream"));
                }
            }
            
            mailSender.send(mimeMessage);
            
            logger.info("Email with attachments sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email with attachments to: {}", to, e);
            throw new MessagingException("Failed to send email with attachments", e);
        }
    }
    
    /**
     * Send a daily summary email to a user
     * 
     * @param to The recipient email address
     * @param userName The name of the user
     * @param subject The email subject
     * @param summaryContent The summary content
     */
    public void sendDailySummaryEmail(String to, String userName, String subject, String summaryContent) {
        try {
            logger.info("Sending daily summary email to: {} with subject: {}", to, subject);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            
            // Create a personalized greeting
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Hello ").append(userName).append(",\n\n");
            emailContent.append(summaryContent);
            emailContent.append("\n\nBest regards,\nThe PulseNet Team");
            
            message.setText(emailContent.toString());
            
            mailSender.send(message);
            
            logger.info("Daily summary email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send daily summary email to: {}", to, e);
            throw new RuntimeException("Failed to send daily summary email", e);
        }
    }
}