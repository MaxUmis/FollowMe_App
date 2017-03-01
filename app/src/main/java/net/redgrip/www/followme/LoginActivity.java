package net.redgrip.www.followme;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static net.redgrip.www.followme.UserFuntions.isValidEmail;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        final ProgressBar pbLogin = (ProgressBar) findViewById(R.id.pbLogin);
        pbLogin.setVisibility(View.INVISIBLE);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLogin.setVisibility(View.VISIBLE);
                if (ValidateVars(etEmail,etPassword)){
                    final String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                //boolean success = true;
                                //Toast.makeText(RegisterActivity.this,jsonResponse.toString(), Toast.LENGTH_LONG).show();
                                if (success) {
                                    JSONObject jsonUserDetailsResponse = jsonResponse.getJSONObject("loggedinuser");
                                    Toast.makeText(LoginActivity.this, "Login successful. Welcome home "+jsonUserDetailsResponse.getString("firstname")+".", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this, UserAreaActivity.class);
                                    startActivity(i);
                                } else {
                                    AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this);
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
                    };// new Response.listener
                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue q = Volley.newRequestQueue(LoginActivity.this);
                    q.add(loginRequest);
                    //Toast.makeText(LoginActivity.this,"Register request added to queue", Toast.LENGTH_LONG).show();
                }//if(ValidateVars)
                pbLogin.setVisibility(View.INVISIBLE);
            }
        });//btnLogin.setOnClickListener

    }//onCreate

    protected boolean ValidateVars(EditText etEmail, EditText etPassword){
        boolean go = true;
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

         if (!isValidEmail(email)){
            Toast.makeText(LoginActivity.this, "Please check your email address and try again.", Toast.LENGTH_LONG).show();
            etEmail.requestFocus();
            go = false;
        }
        else if (password.length() < 6){
            Toast.makeText(LoginActivity.this, "Passwords should be 6 characters or more.", Toast.LENGTH_LONG).show();
            etPassword.requestFocus();
            go = false;
        }

        return go;
    }//ValidateVars
}
