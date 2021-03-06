package com.manueh.wikigi.interfaces;

public interface ISearchPresenter {
    public interface View {
        void ReturnList();
        void CloseSearch();
        void ShowHelp();
    }

    public interface Presenter {
        void onClickReturn();
        void onClickClose();
        void GoHelp();
    }
}
