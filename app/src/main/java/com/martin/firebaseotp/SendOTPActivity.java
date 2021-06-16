package com.martin.firebaseotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        final EditText inputMobile = findViewById(R.id.edt_input_mobile);
        Button btnGetOtp = findViewById(R.id.btn_get_otp);



        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = inputMobile.getText().toString().trim();
                if (phoneNumber.isEmpty()) {
                    inputMobile.setError("Phone Number required");
                    return;
                }
                if (phoneNumber.length() < 10) {
                    inputMobile.setError("Phone number not valid");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                intent.putExtra("mobile", phoneNumber);
                startActivity(intent);
            }
        });
    }
}