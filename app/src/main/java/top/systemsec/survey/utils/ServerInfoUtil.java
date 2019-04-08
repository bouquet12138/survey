package top.systemsec.survey.utils;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerInfoUtil {

    public static String getData(String json) {

        if (TextUtils.isEmpty(json))
            return "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
