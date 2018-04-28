package tech.arinzedroid.assessmentone.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.arinzedroid.assessmentone.models.GithubModel;

/**
 * Created by ACER on 4/27/2018.
 */

public interface ApiService {

   @GET("/search/repositories")
   Call<GithubModel> getRepos(@Query("q") String query,@Query("sort") String sort);

    @Headers("Accept: application/vnd.github.VERSION.raw+json")
    @GET("/repos/{owner}/{repo}/readme")
    Call<ResponseBody> getReadMe(@Path("owner")String owner, @Path("repo")String repo);



}
