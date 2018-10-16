package com.prasanna.yelpreviewapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prasanna.yelpreviewapp.model.BusinessSearchResponse;
import com.prasanna.yelpreviewapp.model.category.CategoryResponse;
import com.prasanna.yelpreviewapp.utils.AppConstants;
import com.prasanna.yelpreviewapp.utils.CallBackToView;
import com.prasanna.yelpreviewapp.utils.DataManager;

import java.io.IOException;

/**
 * Created by rampreethajasmi on 2018-10-16.
 */

public class SearchRepository {
    private static SearchRepository sSearchRepositoryInstance;

    public static SearchRepository getInstance() {
        if (sSearchRepositoryInstance == null) {
            sSearchRepositoryInstance = new SearchRepository();
        }
        return sSearchRepositoryInstance;
    }

    private SearchRepository() {

    }

    public LiveData<RepositoryResponse<CategoryResponse>> getCategories() {
        final MutableLiveData<RepositoryResponse<CategoryResponse>> liveData = new MutableLiveData();
        RepositoryResponse<CategoryResponse> response = new RepositoryResponse<>();
        //Category
        try {
            DataManager.getInstance().getCategories(new CallBackToView() {
                @Override
                public void onSuccess(final String responseModel) {
                    CategoryResponse categoryResponse = new Gson().fromJson(responseModel, CategoryResponse.class);
                    response.setStatus(AppConstants.ResponseStatus.RESPONSE_SUCCESS);
                    response.setData(categoryResponse);
                    liveData.postValue(response);
                }

                @Override
                public void onFailure(final String errorMsg) {
                    response.setStatus(AppConstants.ResponseStatus.RESPONSE_ERROR);
                    response.setErrorMsg(errorMsg);
                    liveData.postValue(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liveData;
    }

    public LiveData<RepositoryResponse<BusinessSearchResponse>> getBusiness(String filterText) {
        final MutableLiveData<RepositoryResponse<BusinessSearchResponse>> liveData = new MutableLiveData();
        RepositoryResponse<BusinessSearchResponse> response = new RepositoryResponse<>();
        //Search View
        try {
            DataManager.getInstance().getBusinessSearch(filterText, new CallBackToView() {
                @Override
                public void onSuccess(final String responseModel) {
                    BusinessSearchResponse businessSearchResponse = new Gson().fromJson(responseModel, BusinessSearchResponse.class);
                    response.setStatus(AppConstants.ResponseStatus.RESPONSE_SUCCESS);
                    response.setData(businessSearchResponse);
                    liveData.postValue(response);
                }

                @Override
                public void onFailure(final String errorMsg) {
                    response.setStatus(AppConstants.ResponseStatus.RESPONSE_ERROR);
                    response.setErrorMsg(errorMsg);
                    liveData.postValue(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liveData;
    }
}