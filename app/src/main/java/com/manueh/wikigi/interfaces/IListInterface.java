package com.manueh.wikigi.interfaces;


import com.manueh.wikigi.models.CharacterEntity;

import java.util.ArrayList;

public interface IListInterface {
    public interface View {
        void startFormAcrivity();
        void startFormAcrivity(String id);
        void startAboutActivity();
        void startSearchActivity();
        void MessageCharacterDeleted();
        void ShowHelp();
    }

    public interface Presenter {
        void onClickAddNewPerson();
        void onClickAbout();
        void OnClickSearch();
        void OnClickRecyclerViewItem(String id);
        void CharacterDeleted();
        ArrayList<CharacterEntity> getAllItems();
        void SetItemsFristTime();
        boolean DeleteCharacterEntity(String id);
        boolean InsertItemAgain(CharacterEntity ce);
        ArrayList<CharacterEntity> searchbyName(String name);
        ArrayList<CharacterEntity> searchbyDate(String date);
        ArrayList<CharacterEntity> searchbyTier(String tier);
        ArrayList<CharacterEntity> searchbyDateNameTier(String name,String date,String tier);
        void GoHelp();
    }
}
