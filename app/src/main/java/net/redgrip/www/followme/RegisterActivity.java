package net.redgrip.www.followme;

import android.content.Intent;
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

                final String firstname = etFirstname.getText().toString();
                final String surname = etSurname.getText().toString();
                final String username = etUsername.getText().toString();
                final String email = etEmail.getText().toString();
                final String mobilenumber = etMobileNumber.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*AlertDialog.Builder adb = new AlertDialog.Builder(RegisterActivity.this);
                        adb.setMessage("Register failed: " + response.toString() + "")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();*/
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //boolean success = true;
                            //Toast.makeText(RegisterActivity.this,jsonResponse.toString(), Toast.LENGTH_LONG).show();
                            if (success){
                                /*
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                */

                                Toast.makeText(RegisterActivity.this, "yup yup, got back true", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                AlertDialog.Builder adb = new AlertDialog.Builder(RegisterActivity.this);
                                adb.setMessage("Register failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            //Toast.makeText(RegisterActivity.this,response.toString(), Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                        pbRegister.setVisibility(View.INVISIBLE);
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(firstname, surname, username, email, mobilenumber, password, responseListener);
                RequestQueue q = Volley.newRequestQueue(RegisterActivity.this);
                q.add(registerRequest);
                Toast.makeText(RegisterActivity.this,"Register request added to queue", Toast.LENGTH_LONG).show();
            }
        });

    }
}
