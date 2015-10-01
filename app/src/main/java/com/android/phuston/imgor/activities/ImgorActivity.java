package com.android.phuston.imgor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.phuston.imgor.R;
import com.android.phuston.imgor.adapters.GridViewAdapter;
import com.android.phuston.imgor.api.APIClient;
import com.android.phuston.imgor.data.ArrayHelper;
import com.android.phuston.imgor.fragments.GalleryFragment;
import com.android.phuston.imgor.fragments.SearchFragment;
import com.android.phuston.imgor.models.ImageQuery;
import com.android.phuston.imgor.models.Item;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ImgorActivity extends AppCompatActivity implements SearchFragment.OnImageSearchListener, SearchFragment.OnImageSaveListener, GalleryFragment.OnImageDeleteListener {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private final String title = "Search Imgor";
    private final String API_KEY = "AIzaSyB4JJeo-t-bpQCHQw1BCJlMkdxQw_jspaU";
    private final String CX = "016517584568088842047:wzc2owtisfu";
    private final String SEARCHTYPE = "image";

    private ArrayHelper mImageDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgor);

        mImageDbHelper = new ArrayHelper(this);

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchFragment())
                    .commit();
        }
    }

    @Override
    public void onImageSearch(final String query) {
        SearchFragment mSearchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.container);
        final GridViewAdapter adapter = mSearchFragment.getGridViewAdapter();
        APIClient.getImageSearchClient().getImages(API_KEY, CX, SEARCHTYPE, query, new Callback<ImageQuery>() {
            @Override
            public void success(ImageQuery imageQuery, Response response) {

                // Grabs result of call, casts it to ArrayList<Item> - see POJO in 'models'
                ArrayList<Item> queryResult = (ArrayList<Item>) imageQuery.getItems();
                ArrayList<String> urls = new ArrayList<>();

                // Populates list of urls from 'Item' objects returned from call
                for (Item i : queryResult) {
                    urls.add(i.getLink());
                }
                adapter.setGridData(urls);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Encountered an error in Retrofit");
                Log.e(TAG, error.getUrl());
                Log.e(TAG, error.getMessage());
            }
        });
    }


    /**
     * Overrides onImageSave method of saveListener interface to run with image is saved
     * @param url - url that's passed in
     */
    @Override
    public void onImageSave(String url) {
        mImageDbHelper.saveImage(url);
        Toast.makeText(this, "Image added to Imgor Gallery", Toast.LENGTH_SHORT).show();
    }


    /**
     * Overrides omImageDelete method of deleteListener interface to run when image is deleted
     * @param url
     */
    @Override
    public void onImageDelete(String url) {
        if (mImageDbHelper.deleteImage(url)) {
            Toast.makeText(this, "Image was deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Image deletion unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}
