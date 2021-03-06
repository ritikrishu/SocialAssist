package android.g38.ritik.Gmail;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.sanyam.Services.Notify;
import android.g38.socialassist.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import javax.mail.internet.AddressException;
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
    public void onReceive(final Context context, final Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences("accountName",Context.MODE_PRIVATE);
        final Notify notify=new Notify(context);
        if(isNetworkAvailable(context)) {
            mCredential = GoogleAccountCredential.usingOAuth2(
                    context, Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff())
                    .setSelectedAccountName(preferences.getString("accountName",null));
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.gmail.Gmail.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("Gmail API ")
                    .build();
            final String[] data = {""};
            new Thread() {
                @Override
                public void run() {
                    try {
                        Log.e("sanyam",intent.getStringExtra("extras"));
                        String []mail=intent.getStringExtra("extras").split("---");
                        sendMessage(createMessageWithEmail(createEmail(mail[0], "me",
                                mail[1],
                                mail[2], context)));
                        data[0] ="Subject:"+mail[1]+"\n"+mail[2];
                        notify.buildNotification("Mail Sent.","TO:"+mail[0],intent.getStringExtra("rId"), data[0]);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        notify.buildNotification("Could Not Mail","Provided email ID is not valid",intent.getStringExtra("rId"), data[0]);

                        e.printStackTrace();
                    }
                }
            }.start();
        }
        else {
            String []mail=intent.getStringExtra("extras").split("---");
            String data ="Subject:"+mail[1]+"\n"+mail[2];
            notify.buildNotification("Could Not Mail","No Internet Connection Available",intent.getStringExtra("rId"),data);
        }
    }
    private static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();

        message.setRaw(encodedEmail);
        message.setId("this is fake id");
        return message;

    }

    private static void sendMessage(Message message)
            throws MessagingException, IOException {
        if(mService != null)
            mService.users().messages().send("me", message).execute();
    }

    private static MimeMessage createEmail(String to, String from, String subject,
                                          String bodyText, Context context) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        String[] recipients = extractEmailIds(to);
        for(String emailID : recipients)
                email.addRecipient(javax.mail.Message.RecipientType.TO,
                        new InternetAddress(emailID));
        email.setSubject(subject);
        email.setContent(bodyText, "text/html; charset=ISO-8859-1");
        email.setText(bodyText);
        return email;
    }

    private static String[] extractEmailIds(String to){
        String[] ret = to.split(",");
        for (int i = 0; i < ret.length; i++){
            ret[i] = ret[i].trim();
        }
        return ret;
    }
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
