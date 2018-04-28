package tech.arinzedroid.assessmentone;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.arinzedroid.assessmentone.models.GithubModel;
import tech.arinzedroid.assessmentone.viewmodels.AppViewModel;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private GithubModel.Item mItem;
    private AppViewModel appViewModel;


    @BindView(R.id.title) TextView Title;
    @BindView(R.id.folks) TextView folks;
    @BindView(R.id.stars) TextView star;
    @BindView(R.id.repo_details) TextView repoDetails;
    @BindView(R.id.repo_url) TextView repoUrl;
    @BindView(R.id.repo_topics) TextView repoTopics;
    @BindView(R.id.owner_text) TextView ownerText;
    @BindView(R.id.type_text) TextView typeText;
    @BindView(R.id.homepage_text) TextView homePageText;
    @BindView(R.id.created_text) TextView createdText;
    @BindView(R.id.updated_text) TextView updatedText;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.desc_text) TextView desc;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        if(getArguments() != null)
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = Parcels.unwrap(getArguments().getParcelable(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        ButterKnife.bind(this,rootView);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout =  activity.findViewById(R.id.toolbar_layout);
        ImageView imageView = activity.findViewById(R.id.title_image);
        if (appBarLayout != null) {
            if(mItem != null){
                appBarLayout.setTitle(mItem.getName());
                Picasso.get().load(mItem.getOwner().getAvatarUrl()).into(imageView);
            }

        }

        if (mItem != null) {
            String _folks = "Folks "+ mItem.getForks().toString();
            String _star  = "Stars " + mItem.getStargazersCount().toString();
           Title.setText(mItem.getFullName());
           star.setText(_star);
           folks.setText(_folks);
           repoDetails.setText(mItem.getDescription());
           repoUrl.setText(mItem.getHtmlUrl());
           ownerText.setText(mItem.getOwner().getLogin());
           typeText.setText(mItem.getOwner().getType());
           homePageText.setText(mItem.getHomepage());
           createdText.setText(mItem.getCreatedAt());
           updatedText.setText(mItem.getUpdatedAt());
           progressBar.setVisibility(View.VISIBLE);
            appViewModel.getReadMe(mItem.getOwner().getLogin(),mItem.getName()).observe(this, response -> {
                if(response != null){
                    progressBar.setVisibility(View.GONE);
                    desc.setText(response);
                }
                else Log.e(ItemDetailFragment.class.getSimpleName(),"Response for readme is null");
            });
        }

        return rootView;
    }
}
