package com.martin.firebaseotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView textMobile = findViewById(R.id.txt_mobile);
        textMobile.setText(String.format(
                "+62-%s", getIntent().getStringExtra("mobile")
        ));

        inputCode1 = findViewById(R.id.input_code1);
        inputCode2 = findViewById(R.id.input_code2);
        inputCode3 = findViewById(R.id.input_code3);
        inputCode4 = findViewById(R.id.input_code4);
        inputCode5 = findViewById(R.id.input_code5);
        inputCode6 = findViewById(R.id.input_code6);

        setupOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        final Button btnVerify = findViewById(R.id.btn_Verify);

        verificationId = getIntent().getStringExtra("verificationId");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Button Verify clicked");
                String code1, code2, code3, code4, code5, code6;
                code1 = inputCode1.getText().toString().trim();
                code2 = inputCode2.getText().toString().trim();
                code3 = inputCode3.getText().toString().trim();
                code4 = inputCode4.getText().toString().trim();
                code5 = inputCode5.getText().toString().trim();
                code6 = inputCode6.getText().toString().trim();

                if(code1.isEmpty()
                        ||code2.isEmpty()
                        ||code3.isEmpty()
                        ||code4.isEmpty()
                        ||code5.isEmpty()
                        ||code6.isEmpty()
                ) {
                    Toast.makeText(VerifyOTPActivity.this, "Please enter valid code : ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = code1+code2+code3+code4+code5+code6;

                if(verificationId != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    btnVerify.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(VerifyOTPActivity.this, "The OTP invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        TextView txtResend = findViewById(R.id.textResendOTP);
        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
//                                progressBar.setVisibility(View.GONE);
//                                btnVerify.setVisibility(View.VISIBLE);
                                Timber.d("Verify completed");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {
//                                progressBar.setVisibility(View.GONE);
//                                btnVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Timber.d("Verify failed");
                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String newVerificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                progressBar.setVisibility(View.GONE);
//                                btnVerify.setVisibility(View.VISIBLE);
                                Timber.d("Code sent");

                                verificationId = newVerificationId;
                                Toast.makeText(VerifyOTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

            }
        });

    }

    private void  setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}