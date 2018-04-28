package tech.arinzedroid.assessmentone.repo;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.arinzedroid.assessmentone.AssessmentOne;
import tech.arinzedroid.assessmentone.interfaces.ApiService;
import tech.arinzedroid.assessmentone.interfaces.ErrorInterface;
import tech.arinzedroid.assessmentone.models.ErrorModel;
import tech.arinzedroid.assessmentone.models.GithubModel;

/**
 * Created by ACER on 4/27/2018.
 */

public class AppRepo implements Application.ActivityLifecycleCallbacks{

    private static final String BASE_URL = "https://api.github.com/";
    private ApiService apiService;
    private ErrorInterface errorInterface;
    Gson gson;
    ErrorModel errorModel;

    public AppRepo(){

        //register activity lifecycleCallbacks
        AssessmentOne.getInstance().registerActivityLifecycleCallbacks(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<GithubModel> getRepos(String query,String sort){
        final MutableLiveData<GithubModel> githubModelMutableLiveData = new MutableLiveData<>();
        apiService.getRepos(query,sort).enqueue(new Callback<GithubModel>() {
            @Override
            public void onResponse(Call<GithubModel> call, Response<GithubModel> response) {
                if(response.isSuccessful()){
                    githubModelMutableLiveData.postValue(response.body());
                }else{
                    if(errorInterface != null){
                        try {
                            errorModel = gson.fromJson(response.errorBody().string(),ErrorModel.class);
                            errorInterface.onError(errorModel.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GithubModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return githubModelMutableLiveData;
    }

    public LiveData<String> getRepoReadMe(String owner, String repo){
        final MutableLiveData<String> readMe = new MutableLiveData<>();
        apiService.getReadMe(owner,repo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String msg = response.body().string();
                        readMe.postValue(msg);
                        Log.e("ResponseBody", msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    if(errorInterface != null){
                        try {
                            errorModel = gson.fromJson(response.errorBody().string(),ErrorModel.class);
                            errorInterface.onError(errorModel.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return readMe;
    }

    private void attachInterface(Activity activity){
        if(activity instanceof ErrorInterface){
            errorInterface = (ErrorInterface) activity;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        attachInterface(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        errorInterface = null;
    }
}
