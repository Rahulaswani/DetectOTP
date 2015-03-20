package com.rahulaswani.detectotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.*;
import android.util.Log;
import android.os.Bundle;
import android.widget.Toast;
import java.util.regex.*;


public class DetectSms extends BroadcastReceiver {

    private String mMessage;
    private String OTPcode;
    private static final String TAG = "DetectSMS";
    private static final String verification =  "code for Olacabs is ";

    // Get the object of SmsManager
    final SmsManager smsmgr = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent)
    {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i(TAG, "senderNum: " + senderNum + "; message: " + message);

                    int duration = Toast.LENGTH_LONG;
                    //if (senderNum.equalsIgnoreCase("Parthiban Chennai") || senderNum.equalsIgnoreCase("AD-bytwoo"))
                    //{
                        if (!message.isEmpty()) {


                            Pattern intsOnly = Pattern.compile("\\d{4}");
                            Matcher makeMatch = intsOnly.matcher(message);
                            makeMatch.find();
                            OTPcode = makeMatch.group();
                            Toast.makeText(context, "senderNum: " + senderNum + " OTP is " + OTPcode , Toast.LENGTH_LONG).show();


                            Intent intentNew = new Intent();
                            intentNew.setAction("SMS_RECEIVED");
                            intentNew.putExtra("otp_code",OTPcode);
                            context.sendBroadcast(intentNew);

                            System.out.println(OTPcode);
                            /*if (message.lastIndexOf(verification) > 0) {


                                if (!message.isEmpty()) {
                                    int index = message.lastIndexOf(verification);
                                    int veri = verification.length();
                                    System.out.println("length of verification is : " + veri);
                                    System.out.println("Total length of string is : " + message.length());
                                    System.out.println("index is :" + index);
                                    int pickup = index + veri;
                                    if (index > 0) {
                                        Log.d(TAG, "OTP found");
                                        String code = message.substring(pickup, pickup + 4)
                                        Toast toast = Toast.makeText(context, "senderNum: " + senderNum + " OTP is " + code, duration).show();


                                    } else
                                        Log.d(TAG, "not found");
                                }
                            }*/
                        }


                   /* }
                    else
                    {
                        Toast.makeText(context,"didn't identified the number", Toast.LENGTH_LONG).show();
                    }*/
                }// end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }

    }



    /*@Override
    public void onReceive(Context context, Intent intent) {



        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    mMessage = currentMessage.getDisplayMessageBody();
                    //mMessage = mMessage.replace(AppConstants.SMS_VERIFY_FORMAT,"");
                    Log.d(TAG, "SmsReceiver : senderNum: " + senderNum + "; message: " + mMessage);

                   // ((YeloApplication) context.getApplicationContext()).getBus().post(new SmsVerification(mMessage));
                    Log.d(TAG, "SmsReceiver : senderNum: " + senderNum + "; message: " + mMessage);



                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e(TAG, "Exception smsReceiver" + e);

        }
    }*/
}



