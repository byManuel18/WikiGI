package com.manueh.wikigi.presenters;

import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IListInterface;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.models.CharacterModle;
import com.manueh.wikigi.views.MyApplication;

import java.util.ArrayList;

public class ListPresenter implements IListInterface.Presenter {
    private IListInterface.View view;
    public ListPresenter (IListInterface.View view) {
        this.view = view;

    }
    @Override
    public void onClickAddNewPerson() {
        //Log.d("")
        view.startFormAcrivity();
    }

    @Override
    public void onClickAbout() {
        view.startAboutActivity();
    }

    @Override
    public void OnClickSearch() {
        view.startSearchActivity();
    }

    @Override
    public void OnClickRecyclerViewItem(String id) {
        view.startFormAcrivity(id);
    }

    @Override
    public void CharacterDeleted() {
        view.MessageCharacterDeleted();
    }

    @Override
    public ArrayList<CharacterEntity> getAllItems() {
        CharacterModle data=new CharacterModle();

        return data.getAllItemsToList();
    }

}
