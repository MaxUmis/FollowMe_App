package net.redgrip.www.followme;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static net.redgrip.www.followme.UserFuntions.isValidEmail;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);

        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        final Button btnRegister = (Button) findViewById(R.id.btnRegister);
        final ProgressBar pbRegister = (ProgressBar) findViewById(R.id.pbRegister);
        pbRegister.setVisibility(View.INVISIBLE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbRegister.setVisibility(View.VISIBLE);
                if (ValidateVars(etFirstname,etSurname,etUsername,etEmail,etMobileNumber,etPassword,etConfirmPassword)){
                    final String firstname = etFirstname.getText().toString();
                    final String surname = etSurname.getText().toString();
                    final String username = etUsername.getText().toString();
                    final String email = etEmail.getText().toString();
                    final String mobilenumber = etMobileNumber.getText().toString();
                    final String password = etPassword.getText().toString();
                    final String confpassword = etConfirmPassword.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                //boolean success = true;
                                //Toast.makeText(RegisterActivity.this,jsonResponse.toString(), Toast.LENGTH_LONG).show();
                                if (success) {
                                    Toast.makeText(RegisterActivity.this, "Registration successful. Please log in.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    AlertDialog.Builder adb = new AlertDialog.Builder(RegisterActivity.this);
                                    adb.setMessage("Register failed."+ System.getProperty("line.separator") + jsonResponse.getString("message") + "")
                                            .setNegativeButton("Close", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                //Toast.makeText(RegisterActivity.this,response.toString(), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(firstname, surname, username, email, mobilenumber, password, responseListener);
                    RequestQueue q = Volley.newRequestQueue(RegisterActivity.this);
                    q.add(registerRequest);
                    Toast.makeText(RegisterActivity.this,"Register request added to queue", Toast.LENGTH_LONG).show();
                }
                pbRegister.setVisibility(View.INVISIBLE);
            }
        });
    }

    protected boolean ValidateVars(EditText etFirstname, EditText etSurname, EditText etUsername, EditText etEmail, EditText etMobileNumber, EditText etPassword, EditText etConfirmPassword){
        boolean go = true;
        final String firstname = etFirstname.getText().toString();
        final String surname = etSurname.getText().toString();
        final String username = etUsername.getText().toString();
        final String email = etEmail.getText().toString();
        final String mobilenumber = etMobileNumber.getText().toString();
        final String password = etPassword.getText().toString();
        final String confpassword = etConfirmPassword.getText().toString();

        if (firstname.length() < 1){
            Toast.makeText(RegisterActivity.this, "Please enter your firstname.", Toast.LENGTH_LONG).show();
            etFirstname.requestFocus();
            go = false;
        }
        else if (surname.length() < 1){
            Toast.makeText(RegisterActivity.this, "Please enter your surname.", Toast.LENGTH_LONG).show();
            etSurname.requestFocus();
            go = false;
        }
        else if (username.length() < 1){
            Toast.makeText(RegisterActivity.this, "Please enter your preferred nickname.", Toast.LENGTH_LONG).show();
            etUsername.requestFocus();
            go = false;
        }
        else if (!isValidEmail(email)){
            Toast.makeText(RegisterActivity.this, "Please check your email address and try again.", Toast.LENGTH_LONG).show();
            etEmail.requestFocus();
            go = false;
        }
        else if (password.length() < 6){
            Toast.makeText(RegisterActivity.this, "Please enter a password of 6 characters or more.", Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
            go = false;
        }
        else if (confpassword.length() < 6){
            Toast.makeText(RegisterActivity.this, "Remember to confirm your password.", Toast.LENGTH_LONG).show();
            etConfirmPassword.requestFocus();
            go = false;
        }
        else if (!password.equals(confpassword)){
            Toast.makeText(RegisterActivity.this, "Oops, your passwords do not match. Please retype them both and try again.", Toast.LENGTH_SHORT).show();
            etPassword.getText().clear();
            etConfirmPassword.getText().clear();
            etPassword.requestFocus();
            go = false;
        }
        return go;
    }
}
