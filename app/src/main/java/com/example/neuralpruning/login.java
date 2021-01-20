package com.example.neuralpruning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class login extends AppCompatActivity {

    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    MaterialButton signInBtn;
    LinearLayout loginForm;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    SignInButton googleSignInButton;
    LoginButton facebookLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        CallbackManager callbackManager=CallbackManager.Factory.create();
        facebookLoginBtn=findViewById(R.id.fb_login_btn);
       facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
           @Override
           public void onSuccess(LoginResult loginResult) {
               Toast.makeText(login.this, "Facebook sign in", Toast.LENGTH_SHORT).show();
               SaveSharedPreference.setLoggedIn(getApplicationContext(),true);
               startActivity(new Intent(getApplicationContext(),MainActivity.class));

           }

           @Override
           public void onCancel() {

           }

           @Override
           public void onError(FacebookException error) {

           }
       });
        googleSignInButton=findViewById(R.id.googleSignInButton);
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
//        mAuth=FirebaseAuth.getInstance();
//        googleSignInButton=findViewById(R.id.googleSignInButton);
//        googleSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext()))
        {
            goToMainActivity();
        }
        else
        {
            loginForm=findViewById(R.id.loginForm);
            loginForm.setVisibility(View.VISIBLE);
        }
        signInBtn=findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmailLogin();
            }
        });
    }

    private void signIn() {
        Toast.makeText(this, "SingIn()", Toast.LENGTH_SHORT).show();
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(this, "try block executed", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            }
            catch (ApiException e)
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            SaveSharedPreference.setLoggedIn(getApplicationContext(),true);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));


                        } else {

                            Toast.makeText(login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

        private void userEmailLogin() {
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setEmail(emailEditText.getText().toString());
        loginRequest.setPassword(passwordEditText.getText().toString());
        Call<LoginResponse> loginResponseCall=RetrofitClientInstance.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                String status=loginResponse.getStatus();
                if (status.equals("success"))
                {

                    Toast.makeText(login.this, "successfull", Toast.LENGTH_SHORT).show();
                    SaveSharedPreference.setLoggedIn(getApplicationContext(),true);
                    goToMainActivity();


                }
                else
                {
                    Toast.makeText(login.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
    }

}