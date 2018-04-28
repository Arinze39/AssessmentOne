package tech.arinzedroid.assessmentone;

import android.app.Application;
import android.os.Bundle;

/**
 * Created by ACER on 4/27/2018.
 */

public class AssessmentOne extends Application {
    private static AssessmentOne instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static AssessmentOne getInstance(){
        return instance;
    }
}
