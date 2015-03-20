package com.rahulaswani.detectotp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private String mMessage;
    private String OTPcode;

    TextView rcvdOtp;

    String number = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvdOtp = (TextView)findViewById(R.id.textView_rcvdOTP);

        IntentFilter filter = new IntentFilter();
        filter.addAction("SMS_RECEIVED");
        registerReceiver(broadcastReceiver, filter);

       /* Button sendotp = (Button) findViewById(R.id.button_sendotp);
        EditText getno = (EditText) findViewById(R.id.editText_takeno);

        number = getno.getText().toString();
        sendotp.setOnClickListener(this);*/
    }

    BroadcastReceiver broadcastReceiver = new DetectSms() {
        @Override
        public void onReceive(Context context, Intent intent) {


                final Bundle bundle = intent.getExtras();

                rcvdOtp.setText("OTP is " + bundle.getString("otp_code"));





        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    /*@Override
    protected void onResume() {
        this.broadcastReceiver = new DetectSms();
        registerReceiver(this.broadcastReceiver,new IntentFilter(DetectSms.SMS_RECEIVED);

        super.onResume();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("SMS_RECEIVED");
        registerReceiver(broadcastReceiver, filter);
        super.onResume();
    }
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

