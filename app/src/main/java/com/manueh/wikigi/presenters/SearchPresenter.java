package com.manueh.wikigi.presenters;

import com.manueh.wikigi.interfaces.ISearchPresenter;

public class SearchPresenter implements ISearchPresenter.Presenter {
    private ISearchPresenter.View view;

    public SearchPresenter(ISearchPresenter.View view) {
        this.view = view;
    }

    @Override
    public void onClickReturn() {
         view.ReturnList();
    }

    @Override
    public void onClickClose() {
        view.CloseSearch();
    }
}
