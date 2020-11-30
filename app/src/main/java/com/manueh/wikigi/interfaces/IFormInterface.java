package com.manueh.wikigi.interfaces;

import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.views.MyApplication;

public interface IFormInterface {
    public interface View {
        void GoBackToList();
        void CloseActivity();

    }

    public interface Presenter {
        void ReturnToList();
        void CloseFormActivity();
    }

}
