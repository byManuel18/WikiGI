package com.manueh.wikigi.interfaces;

public interface ISearchPresenter {
    public interface View {
        void ReturnList();
        void CloseSearch();
    }

    public interface Presenter {
        void onClickReturn();
        void onClickClose();
    }
}
