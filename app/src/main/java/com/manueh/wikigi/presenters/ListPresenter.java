package com.manueh.wikigi.presenters;

import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IListInterface;
import com.manueh.wikigi.views.MyApplication;

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

}
