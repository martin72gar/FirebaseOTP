package com.martin.firebaseotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        Timber.i("Timber started");

        final EditText inputMobile = findViewById(R.id.edt_input_mobile);
        Button btnGetOtp = findViewById(R.id.btn_get_otp);

        final ProgressBar progressBar = findViewById(R.id.progress_bar);

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Button Get OTP clicked");
                String phoneNumber = inputMobile.getText().toString().trim();
                if (phoneNumber.isEmpty()) {
                    inputMobile.setError("Phone Number required");
                    return;
                }
                if (phoneNumber.length() < 10) {
                    inputMobile.setError("Phone number not valid");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btnGetOtp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        SendOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                                Timber.d("Verify completed");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Timber.d("Verify failed");
                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String verificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                                Timber.d("Code sent");

                                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                intent.putExtra("mobile", phoneNumber);
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        }
                );


            }
        });
    }
}