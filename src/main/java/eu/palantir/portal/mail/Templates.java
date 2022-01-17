package eu.palantir.portal.mail;

import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.CheckedTemplate;

@CheckedTemplate
public class Templates {
    public static native MailTemplate.MailTemplateInstance reset_password(String link);

    public static native MailTemplate.MailTemplateInstance verify_email(String link);
}
