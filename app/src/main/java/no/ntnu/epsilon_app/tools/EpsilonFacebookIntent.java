package no.ntnu.epsilon_app.tools;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class EpsilonFacebookIntent {

    private static final String FACEBOOK_ID = "1007001496115565";
    private static final String FACEBOOK_URL = "https://www.facebook.com/EpsilonAalesund";


    public static Intent newFaceBookIntent(PackageManager pm) {
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_ID));
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
    }
}
