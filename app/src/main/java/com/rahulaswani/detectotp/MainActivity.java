package com.rahulaswani.detectotp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    String number = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendotp = (Button) findViewById(R.id.button_sendotp);
        EditText getno = (EditText) findViewById(R.id.editText_takeno);

        number = getno.getText().toString();
        sendotp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Log.d(TAG,"phone number is : "+number);
        Toast.makeText(getApplicationContext()," phone number is "+number,Toast.LENGTH_LONG).show();
        /*Intent mIntent = new Intent(this,DetectSms.class);
        mIntent.putExtra("phoneno", number);
        startActivity(mIntent);*/
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data)
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
}
