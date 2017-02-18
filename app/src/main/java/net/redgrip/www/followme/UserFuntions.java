package net.redgrip.www.followme;

import android.text.TextUtils;

/**
 * Created by Mark on 2017/02/17.
 */

public class UserFuntions {
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
