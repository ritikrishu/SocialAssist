package android.g38.ritik;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by ritik on 3/17/2016.
 */
public class ScheduleMail extends BroadcastReceiver {
    private static Gmail mService;
    private static final String[] SCOPES = {GmailScopes.MAIL_GOOGLE_COM};
    private GoogleAccountCredential mCredential;
    @Override
    public void onReceive(Context context, final Intent intent) {
        Toast.makeText(context,intent.getStringExtra("accountName"),Toast.LENGTH_LONG).show();
        mCredential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(intent.getStringExtra("accountName"));
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.gmail.Gmail.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName("Gmail API ")
                .build();
        new Thread() {
            @Override
            public void run() {
                try {
                        sendMessage(createMessageWithEmail(createEmail(intent.getStringExtra("to"), "me",
                            intent.getStringExtra("sub"),
                            intent.getStringExtra("body"))));
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();

        message.setRaw(encodedEmail);
        message.setId("this is fake id");
        return message;

    }

    public static void sendMessage(Message message)
            throws MessagingException, IOException {
        if(mService != null)
            mService.users().messages().send("me", message).execute();
    }

    public static MimeMessage createEmail(String to, String from, String subject,
                                          String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setContent(bodyText, "text/html; charset=ISO-8859-1");
        email.setText(bodyText);
        return email;
    }
}
