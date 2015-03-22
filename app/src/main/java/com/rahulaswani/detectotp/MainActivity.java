package com.rahulaswani.detectotp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private String otp;
    TextView rcvdOtp;
    LinearLayout otpscreen;
    LinearLayout succscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otpscreen = (LinearLayout) findViewById(R.id.linearlayout_otpscreen);
        succscreen = (LinearLayout) findViewById(R.id.linearlayout_successcreen);

        rcvdOtp = (TextView) otpscreen.findViewById(R.id.textView_rcvdOTP);


        /*IntentFilter filter = new IntentFilter();
        filter.addAction("SMS_RECEIVED");
        registerReceiver(broadcastReceiver, filter);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(2147483647);     set highest priority dynamically
        receiver = new SmsAnalizer();
        ac.registerReceiver(receiver, filter);*/

       /* Button sendotp = (Button) findViewById(R.id.button_sendotp);
        EditText getno = (EditText) findViewById(R.id.editText_takeno);

        number = getno.getText().toString();
        sendotp.setOnClickListener(this);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SMS_RECEIVED");
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new DetectSms() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final Bundle bundle = intent.getExtras();

                rcvdOtp.setText(bundle.getString("otp_code"));
                otp = rcvdOtp.getText().toString().trim();
                Log.d("DetectSMS",rcvdOtp.getText().toString().trim());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    verifyOTP(otp);
                }
            },2500);

        }
    };

    public void verifyOTP(String otp)
    {
        this.otp = otp;
        Log.d("DetectSMS","otp is : "+otp);

        if(otp.equalsIgnoreCase("9880"))
        {
            otpscreen.setVisibility(View.GONE);
            succscreen.setVisibility(View.VISIBLE);
        }else
        {
            Toast.makeText(getApplicationContext(),"wrong OTP ",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        try {

            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e)
        {
            Log.d("rahul","exception in onstop is : "+e.toString());
        }
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e)
        {
            Log.d("rahul","exception in destroy is : "+e.toString());
        }

    }*/
}
   /* @Override
    public void onClick(View v)
    {
        Log.d(TAG,"phone number is : "+number);
        Toast.makeText(getApplicationContext()," phone number is "+number,Toast.LENGTH_LONG).show();
        /*Intent mIntent = new Intent(this,DetectSms.class);
        mIntent.putExtra("phoneno", number);
        startActivity(mIntent);*
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            if (data.hasExtra("returnkey"))
            {
                String result = data.getExtras().getString("returnkey");
                if (result != null && result.length() > 0)
                {

                }
            }
        }
    }*/

