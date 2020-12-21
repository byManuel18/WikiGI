package com.manueh.wikigi.interfaces;



public interface IListInterface {
    public interface View {
        void startFormAcrivity();
        void startFormAcrivity(String id);
        void startAboutActivity();
        void startSearchActivity();
        void MessageCharacterDeleted();
    }

    public interface Presenter {
        void onClickAddNewPerson();
        void onClickAbout();
        void OnClickSearch();
        void OnClickRecyclerViewItem(String id);
        void CharacterDeleted();
    }
}
