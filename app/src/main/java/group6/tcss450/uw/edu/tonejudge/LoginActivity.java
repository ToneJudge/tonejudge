package group6.tcss450.uw.edu.tonejudge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Check for empty field and validate the user account
     * @param view
     */
    public void clickLoginButton(View view) {
        Boolean checkUserName = true;
        Boolean checkPassword = true;

        //checking if the username edit text field is empty
        EditText userNameEditText = (EditText) findViewById(R.id.edit_text_login_email);
        String userName = userNameEditText.getText().toString();
        if(TextUtils.isEmpty(userName)) {
            checkUserName = false;
            userNameEditText.setError("Please enter an email");
        }
        //checking if the password edit text field is empty
        EditText userPasswordEditText = (EditText) findViewById(R.id.edit_text_login_password);
        String userPassword = userPasswordEditText.getText().toString();
        if(TextUtils.isEmpty(userPassword)) {
            checkPassword = false;
            userPasswordEditText.setError("Please enter a password");
        }

        if(checkUserName && checkPassword) {
            AuthenticateTask task = null;
            if(view.getId() == R.id.button_login) {
                task = new AuthenticateTask();
            } else{
                throw new IllegalStateException("Not Implemented");
            }
            JSONObject request = new JSONObject();
            try {
                request.put("email", userName);
                request.put("password", userPassword);
                request.put("action", AuthenticateTask.ACTION);
            } catch (JSONException e) {

            }
            task.execute(request);
        }
    }

    /**
     * switch to the register activity
     * when user click the register
     * @param view
     */
    public void clickRegisterButton(View view) {
        //switch to the register activity using intent
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private class AuthenticateTask extends JsonPostErrorTask {

        private static final String ACTION = "authenticate";
        private ProgressDialog progressDialog;

        public AuthenticateTask() {
            super("https://xk6ntzqxr2.execute-api.us-west-2.amazonaws.com/tonejudge/users");
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onFinish(String errorMessage) {
            progressDialog.dismiss();
            if (errorMessage == null) {
                Intent judgeIntent = new Intent(getApplicationContext(), JudgeActivity.class);
                startActivity(judgeIntent);
            } else if (errorMessage.contains("Invalid email or password")) {
                EditText userPasswordEditText = (EditText) findViewById(R.id.edit_text_login_password);
                userPasswordEditText.setError(errorMessage);
            } else {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}

