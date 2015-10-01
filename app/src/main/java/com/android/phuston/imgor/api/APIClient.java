package com.android.phuston.imgor.api;

import retrofit.RestAdapter;

/**
 * Class to facilitate the use of the Retrofit HTTP Client
 */

public class APIClient {

    private static SearchDbEndpointInterface sSearchDbService;

    public static SearchDbEndpointInterface getImageSearchClient() {
        if (sSearchDbService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://www.googleapis.com/customsearch/v1")
                    .build();

            sSearchDbService = restAdapter.create(SearchDbEndpointInterface.class);
        }

        return sSearchDbService;
    }
}
