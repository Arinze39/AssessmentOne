package tech.arinzedroid.assessmentone;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.parceler.Parcels;

import tech.arinzedroid.assessmentone.dummy.DummyContent;
import tech.arinzedroid.assessmentone.interfaces.ItemClickedInterface;
import tech.arinzedroid.assessmentone.models.GithubModel;
import tech.arinzedroid.assessmentone.viewmodels.AppViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ItemClickedInterface{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private AppViewModel appViewModel;
    ArrayList<GithubModel.Item> items;
    ProgressBar progressBar;
    private final int m_permissionCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 23) {
            startApp();
        }
        else {
            checkPermissions(m_permissionCode);
        }
    }

    private void checkPermissions(int permissionCode){
        String permission = Manifest.permission.INTERNET;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},permissionCode);
        }else startApp();
    }

    private void startApp(){
        setContentView(R.layout.activity_item_list);

        progressBar = findViewById(R.id.progress_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        appViewModel.getGitRepos("android","stars").observe(this, response -> {
            if(response != null){
                progressBar.setVisibility(View.GONE);
                items = response.getItems();
                recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ItemListActivity.this,items,ItemListActivity.this));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case m_permissionCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startApp();
                }else finish();
            }
        }
    }

    @Override
    public void ItemPosition(int position) {
        GithubModel.Item item = items.get(position);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, Parcels.wrap(item));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            ItemListActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,Parcels.wrap(item));
            ItemListActivity.this.startActivity(intent);
        }
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final ArrayList<GithubModel.Item> mValues;
        private final ItemClickedInterface itemClickedInterface;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent, ArrayList<GithubModel.Item> items,ItemClickedInterface itemClickedInterface) {
            mValues = items;
            mParentActivity = parent;
            this.itemClickedInterface = itemClickedInterface;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view,itemClickedInterface);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mValues.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            final TextView mIdView;
            final TextView mContentView;
            final LinearLayout mLinearLayout;
            final ItemClickedInterface itemClickedInterface;

            ViewHolder(View view, ItemClickedInterface itemClickedInterface) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
                mLinearLayout = view.findViewById(R.id.linear_layout);
                this.itemClickedInterface = itemClickedInterface;
                mLinearLayout.setOnClickListener(this);
                mIdView.setOnClickListener(this);
                mContentView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                itemClickedInterface.ItemPosition(this.getLayoutPosition());
            }
        }
    }
}
