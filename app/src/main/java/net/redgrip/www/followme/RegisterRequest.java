package net.redgrip.www.followme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 2017/02/08.
 */

public class RegisterRequest extends StringRequest {

    private static final String RegisterRequestURL ="";
    private Map<String, String> params;

    public RegisterRequest(String firstname, String surname, String username, String email, String mobileNumber, String password, Response.Listener<String> listener){
        super(Method.POST, RegisterRequestURL,listener,null);
        params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("surname", surname);
        params.put("username", username);
        params.put("email", email);
        params.put("mobilenumber", mobileNumber);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

