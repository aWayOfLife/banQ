package com.thetripod.banq;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class OTPFragment extends Fragment {

    private String phone_number;
    private String name;
    private EditText OTP;
    private FirebaseAuth mAuth;
    private OnFragmentInteractionListener mListener;
    private String verificationCode;
    private String otp_final;
    private String city,branch;

    private FirebaseUser use;
    private String bankerId ;
    DatabaseReference firebaseDatabase;

    public OTPFragment() {
        // Required empty public constructor
    }


    public static OTPFragment newInstance(String param1, String param2) {
        OTPFragment fragment = new OTPFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone_number = getArguments().getString("NUMBER_TRANSFER");
            name = getArguments().getString("NAME_BANKER");
            city = getArguments().getString("CITY");
            branch = getArguments().getString("BRANCH");

            Toast.makeText(getContext(),branch, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_otp, container, false);

        Button confirm_otp = rootView.findViewById(R.id.button_confirm_otp);
        OTP=rootView.findViewById(R.id.enter_otp);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Banker");
        mAuth = FirebaseAuth.getInstance();
        /*use = FirebaseAuth.getInstance().getCurrentUser();

        bankerId = use.getUid();*/
        phoneVerificationCode(phone_number);
        confirm_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                otp_final=OTP.getText().toString().trim();
                if(otp_final.isEmpty()||otp_final.length()<6)
                {
                    Toast.makeText(getContext(),"Wrong OTP", Toast.LENGTH_LONG).show();

                }
                else
                verifyCode(otp_final);
            }
        });
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    private void verifyCode(String code)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCode,code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //use = FirebaseAuth.getInstance().getCurrentUser();
                    bankerId =FirebaseAuth.getInstance().getCurrentUser().getUid();
                    BankerDetails bankerDetails = new BankerDetails(bankerId,name,phone_number,city,branch);
                    firebaseDatabase.child(bankerId).setValue(bankerDetails);


                    Log.i("Post firebase", "Database user details added !"+bankerId);
                    Intent intent=new Intent(getContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void phoneVerificationCode(String number)
    {
        // progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallback);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode=s;
        }
        // esheche bol 713017

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {

                OTP.setText(code);
                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };



}

