package tech.arinzedroid.assessmentone.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACER on 4/27/2018.
 */

public class ErrorModel {

    @SerializedName("message")
    @Expose
    private String message = "Error occurred while performing task";

    @SerializedName("documentation_url")
    @Expose
    private String url = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
