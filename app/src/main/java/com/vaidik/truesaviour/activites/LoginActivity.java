package com.vaidik.truesaviour.activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.Utils.StringUtils;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int GOOGLE_SIGN_IN_CODE = 212;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    //private Session session;
    EditText _idText;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //session = new Session(LoginActivity.this);

        //if (session.getUsername().equals(StringUtils.INVALID_SESSION)) {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {


            setContentView(R.layout.activity_login);

            _idText = findViewById(R.id.input_id);
            _passwordText = findViewById(R.id.input_password);
            _loginButton = findViewById(R.id.btn_login);
            _signupLink = findViewById(R.id.link_signup);

            _loginButton.setOnClickListener(v -> {
                login();
            });

            findViewById(R.id.button_google_sign_in).setOnClickListener(view12 -> {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, GOOGLE_SIGN_IN_CODE);
            });

            _signupLink.setOnClickListener(v -> {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            });

        } else {
            launchSucceedingActivity();
        }



    }

    public void login() {

        /*if (!validate()) {

            return;
        }*/

        Toast.makeText(this, "COMING SOON! Use Google Sign in please.", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(false);
        String id = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        /*Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(new LoginResponse(id, password));

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();
                if (loginResponse.getMessage().equals("login successful.")) {

                    Toast.makeText(LoginActivity.this, StringUtils.LOGIN_SUCCESSFUL, Toast.LENGTH_LONG).show();

                    session.setUsername(id);
                    launchSucceedingActivity();

                } else {
                    onLoginFailed();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(StringUtils.RESPONSE, StringUtils.FAILURE);
            }
        });*/

        /*GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .requestProfile()
                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            session.setUsername(user.getEmail());
            launchSucceedingActivity();





        } else {
            findViewById(R.id.button_google_sign_in).setOnClickListener(view12 -> {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, GOOGLE_SIGN_IN_CODE);
            });

        }*/

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage() + " check network connection.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {

                        /*this.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new TrackLoad())
                                .commit();*/

                        final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
                        _loginButton.setOnClickListener(v -> {
                            konfettiView.build()
                                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                                    .setDirection(0.0, 359.0)
                                    .setSpeed(1f, 5f)
                                    .setFadeOutEnabled(true)
                                    .setTimeToLive(2000L)
                                    .addShapes(Shape.RECT, Shape.CIRCLE)
                                    .addSizes(new Size(12, 5f))
                                    .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                                    .streamFor(300, 5000L);

                        });

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(this, "Login Failure", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), StringUtils.LOGIN_FAILED, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String id = _idText.getText().toString();
        String password = _passwordText.getText().toString();

        if (id.isEmpty() || id.length() < 3) {
            _idText.setError(StringUtils.USER_ID_CHARACTER_LENGTH_CHECK);
            valid = false;
        } else {
            _idText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError(StringUtils.USER_ID_PASSWORD_RESTRICTIONS_CHECK);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void launchSucceedingActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}