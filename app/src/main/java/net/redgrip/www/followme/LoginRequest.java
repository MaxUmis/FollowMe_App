package net.redgrip.www.followme;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 2017/02/20.
 */

public class LoginRequest extends StringRequest{
    private static final String LoginRequestURL ="http://followmewebapi.redgrip.net/api/login/";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LoginRequestURL,listener,null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
