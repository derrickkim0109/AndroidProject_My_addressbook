package com.android.mypeople.jiseok.Activity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {
    String user = "wltjr318@gmail.com";
    // 보내는 계정의 id
    String password = "Wltjrghtj75786";
    // 보내는 계정의 pw

    static String pwCode = null;
    static String emailCode = null;

    public void sendSecurityCode(Context context, String sendTo) {

        try {
            Log.v("여기","SenMailClass1");
            GmailSender gMailSender = new GmailSender(user, password);
            Log.v("여기","SenMailClass2");
            String code = gMailSender.getEmailCode();
            Log.v("여기","SenMailClass3");
            pwCode = code;
            Log.v("여기","SenMailClass4");
            gMailSender.sendMail("비밀번호", "변경된 비밀번호는 : " + code + " 입니다.", sendTo);
            Log.v("여기","SenMailClass5");
            Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
            Log.v("여기","SenMailClass6");
        } catch (SendFailedException e) {
            Log.v("여기","SenMailClass7");
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Log.v("여기","SenMailClass8");
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("여기","SenMailClass9");
            e.printStackTrace();
        }
    }
    public void sendSecurityCode2(Context context, String sendTo) {

        try {
            Log.v("여기","SenMailClass1");
            GmailSender gMailSender2 = new GmailSender(user, password);
            Log.v("여기","SenMailClass2");
            String code = gMailSender2.getEmailCode2();
            Log.v("여기","SenMailClass3");
            emailCode = code;
            Log.v("여기","SenMailClass4");
            gMailSender2.sendMail("이메일인증", "이메일인증코드는 : " + code + " 입니다.", sendTo);
            Log.v("여기","SenMailClass5");
            //Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
            Log.v("여기","SenMailClass6");
        } catch (SendFailedException e) {
            Log.v("여기","SenMailClass7");
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Log.v("여기","SenMailClass8");
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("여기","SenMailClass9");
            e.printStackTrace();
        }
    }
}


