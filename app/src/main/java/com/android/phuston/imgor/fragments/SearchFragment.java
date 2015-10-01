package com.android.phuston.imgor.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.phuston.imgor.R;
import com.android.phuston.imgor.adapters.GridViewAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();

    OnImageSearchListener mSearchListener;
    OnImageSaveListener mSaveListener;

    private EditText mSearchEditText;
    private GridViewAdapter mGridviewAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSearchListener = (OnImageSearchListener) activity;
        mSaveListener = (OnImageSaveListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_gallery) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new GalleryFragment())
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        ArrayList<String> mImages = new ArrayList<>();

        mGridviewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mImages);

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        GridView mGridView = (GridView) rootView.findViewById(R.id.imageGridView);
        mGridView.setEmptyView(rootView.findViewById(R.id.empty_grid_view));
        mGridView.setAdapter(mGridviewAdapter);

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String urltosave = mGridviewAdapter.getItem(position);
                mSaveListener.onImageSave(urltosave);
                return true;
            }
        });

        ImageButton mSearchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mSearchEditText.getText().toString();
                if (query != null) {
                    mSearchListener.onImageSearch(query);
                }
            }
        });

        mSearchEditText = (EditText) rootView.findViewById(R.id.searchEditText);

        return rootView;
    }

    public GridViewAdapter getGridViewAdapter() {
        return mGridviewAdapter;
    }

    // Interface to keep track of data updates
    public interface OnImageSearchListener {
        void onImageSearch(String query);
    }

    // Interface to keep track of Image Save requests
    public interface OnImageSaveListener {
        void onImageSave(String url);
    }
}
