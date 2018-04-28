package tech.arinzedroid.assessmentone.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import tech.arinzedroid.assessmentone.models.GithubModel;
import tech.arinzedroid.assessmentone.repo.AppRepo;

/**
 * Created by ACER on 4/27/2018.
 */

public class AppViewModel extends ViewModel{

    private AppRepo appRepo;
    private LiveData<String> readMe;
    private LiveData<GithubModel> gitRepos;

    public AppViewModel(){
        appRepo = new AppRepo();
    }

    public LiveData<String> getReadMe(String owner,String repo){
        if(readMe == null){
            readMe = appRepo.getRepoReadMe(owner,repo);
        }
        return readMe;
    }

    public LiveData<GithubModel> getGitRepos(String query,String sort){
        if(gitRepos == null){
            gitRepos = appRepo.getRepos(query,sort);
        }
        return gitRepos;
    }
}
