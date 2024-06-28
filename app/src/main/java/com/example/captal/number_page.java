package com.example.captal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.captal.databinding.ActivityNumberPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class number_page extends AppCompatActivity {

    private ActivityNumberPageBinding numberPageBinding;
    private FirebaseAuth mAuth;
    private  static  String otp_id;
    private FirebaseDatabase firebaseDatabase;

    private  String PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numberPageBinding = ActivityNumberPageBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        setContentView(numberPageBinding.getRoot());
//        numberPageBinding.password.setClickable(false);



        numberPageBinding.getOtp.setOnClickListener(v -> {

            String phone_number = numberPageBinding.phoneNumber.getText().toString().trim();

            if (phone_number.isEmpty() ){

                numberPageBinding.phoneNumber.setError("Please Enter Your Phone Number...");
                return;

            }
            if (phone_number.length()!=10){
                numberPageBinding.phoneNumber.setError("Phone Number must be 10 digit");
                return;
            }

            numberPageBinding.getOtp.setText("VERIFY OTP");
            PhoneNumber= phone_number;
            numberPageBinding.phoneNumber.setText("");
            numberPageBinding.phoneNumber.setHint("Enter Your OTP...");

            Toast.makeText(this, "Verify Captcha Please...", Toast.LENGTH_SHORT).show();


            phone_authentication();


            Toast.makeText(this, "Wait a second for OTP", Toast.LENGTH_LONG).show();

            numberPageBinding.getOtp.setOnClickListener(v1 -> {

                String otp = numberPageBinding.phoneNumber.getText().toString();

                if (otp.isEmpty()){
                    numberPageBinding.phoneNumber.setError("Please Enter Your OTP");
                    return;
                }
                if (otp.length()!=6){

                    numberPageBinding.phoneNumber.setError("OTP LENGTH MUST BE SIX ");
                    return;
                }




                PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(otp_id,String.valueOf(numberPageBinding.phoneNumber.getText()));
                signInWithPhoneAuthCredential(authCredential);





            });

        });



    }

    private void phone_authentication(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+PhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp_id =s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(number_page.this,"Something Went Wrong", Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            Toast.makeText(number_page.this, "Verified Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(number_page.this,choose_business_type.class));
                            finish();
                        } else {
                            Toast.makeText(number_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}