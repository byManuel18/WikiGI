package com.manueh.wikigi.interfaces;

public interface IListInterface {
    public interface View {
        void startFormAcrivity();
        void startAboutActivity();
    }

    public interface Presenter {
        void onClickAddNewPerson();
        void onClickAbout();
    }
}
