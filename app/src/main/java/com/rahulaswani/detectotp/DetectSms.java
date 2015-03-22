package com.rahulaswani.detectotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DetectSms extends BroadcastReceiver {

    private String OTPcode;
    private static final String TAG = "DetectSMS";

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
                    String sendername = currentMessage.getOriginatingAddress();
                    Log.d(TAG,"sendername is "+sendername);
                    String servicecntradd = currentMessage.getServiceCenterAddress();
                    Log.d(TAG,"servicecenteraddress is : "+servicecntradd);
                    String senderNum = phoneNumber;
                    Log.d(TAG,"Displayoriginationg address is : "+sendername);
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(TAG, "senderNum: " + senderNum + "; message: " + message);

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
                        }
                   /* }
                    else { Toast.makeText(context,"didn't identified the number", Toast.LENGTH_LONG).show(); }*/
                }// end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
}



