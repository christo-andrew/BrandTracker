package com.iconasystems.christoandrew.brandtracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iconasystems.christoandrew.brandtracker.adapters.SpinnerAdapter;
import com.iconasystems.christoandrew.brandtracker.api.ApiClient;
import com.iconasystems.christoandrew.brandtracker.api.ApiService;
import com.iconasystems.christoandrew.brandtracker.api.response.AuthResponse;
import com.iconasystems.christoandrew.brandtracker.models.Person;
import com.iconasystems.christoandrew.brandtracker.models.Token;
import com.iconasystems.christoandrew.brandtracker.viewmodel.TokenViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private List<Person> managers = new ArrayList<>();
    private ApiService apiService;
    private TokenViewModel tokenViewModel;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        apiService = ApiClient.getClient(null).create(ApiService.class);

        managers.add(new Person(1, "John", "Kajubi"));
        managers.add(new Person(2, "MacFish", "Kitimbo"));
        managers.add(new Person(3, "Micheal", "Kinosi"));
        managers.add(new Person(4, "Allan", "Womuwaya"));
        managers.add(new Person(5, "Erisa", "Galikuki"));
        managers.add(new Person(6, "Peter", "Owere"));
        managers.add(new Person(7, "Allan", "Zegezinho"));
        managers.add(new Person(8, "Aaron", "Wekoye"));
        managers.add(new Person(9, "Scofield", "Yeko"));
        managers.add(new Person(10, "Alex", "Kipala"));

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUsernameSignInButton = (Button) findViewById(R.id.sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.manager_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, R.layout.manager_list_item, this.managers);
        spinner.setAdapter(spinnerAdapter);

        TextView mBrand = findViewById(R.id.brand);
        TextView mLabelSelectManager = findViewById(R.id.select_manager);
        TextView mSignUp = findViewById(R.id.link_signup);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway/Raleway-Bold.ttf");
        mBrand.setTypeface(font);
        mLabelSelectManager.setTypeface(font);
        mSignUp.setTypeface(font);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean status = false;
            // TODO: attempt authentication against a network service.
            Call<AuthResponse> login = apiService.login(mUsername, mPassword);
            try {
                AuthResponse response = login.execute().body();
                assert response != null;
                tokenViewModel.insertToken(new Token(response.getToken()));
                Log.d(TAG, "Response code => "+ (response.getCode() == 2002));
                status = response.getCode() == 2002;


            }catch (Exception ex){
                Log.d(TAG, ex.getMessage());
            }
            Log.d(TAG, "Response status => "+ status);
            return status;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            Log.d(TAG, "Login Result => ".concat(String.valueOf(success)));
            showProgress(false);

            if (success) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                mUsernameView.setError(getString(R.string.error_invalid_username));
                mPasswordView.requestFocus();
                mUsernameView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

