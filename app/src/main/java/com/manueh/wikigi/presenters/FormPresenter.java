package com.manueh.wikigi.presenters;

import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IFormInterface;
import com.manueh.wikigi.views.MyApplication;

public class FormPresenter implements IFormInterface {




    public static String getError(int error_code, Fields_to_validate ftv) {
        String ch="";
        switch (ftv){
            case NAME_FORM:
                switch (error_code){
                    case 0:
                        ch=null;
                        break;
                    case 1:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_null);
                        break;
                    case 2:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_validate);
                        break;
                    default:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case DATE_FORM:
                switch (error_code){
                    case 0:
                        ch=null;
                        break;
                    case 1:
                        ch=MyApplication.getContext().getResources().getString(R.string.date_error_null);
                        break;
                    case 2:
                        ch=MyApplication.getContext().getResources().getString(R.string.date_error_validate);
                        break;
                    case 3:
                        ch=MyApplication.getContext().getResources().getString(R.string.date_error_incorrect);
                        break;
                    default:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case CONSTELLATION_FORM:
                switch (error_code){
                    case 0:
                        ch=null;
                        break;
                    case 1:
                        ch= MyApplication.getContext().getResources().getString(R.string.constellation_error_null);
                        break;
                    case 2:
                        ch= MyApplication.getContext().getResources().getString(R.string.constellation_error_validate);
                        break;
                    default:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case HP_FORM:
                switch (error_code) {
                    case 0:
                        ch = null;
                        break;
                    case 1:
                        ch = MyApplication.getContext().getResources().getString(R.string.hp_error_null);
                        break;
                    case 2:
                        ch = MyApplication.getContext().getResources().getString(R.string.hp_error_validate);
                        break;
                    default:
                        ch = MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case ATK_FORM:
                switch (error_code) {
                    case 0:
                        ch = null;
                        break;
                    case 1:
                        ch = MyApplication.getContext().getResources().getString(R.string.atk_error_null);
                        break;
                    case 2:
                        ch = MyApplication.getContext().getResources().getString(R.string.atk_error_validate);
                        break;
                    default:
                        ch = MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case DEF_FORM:
                switch (error_code) {
                    case 0:
                        ch = null;
                        break;
                    case 1:
                        ch = MyApplication.getContext().getResources().getString(R.string.def_error_null);
                        break;
                    case 2:
                        ch = MyApplication.getContext().getResources().getString(R.string.def_error_validate);
                        break;
                    default:
                        ch = MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;

        }

        return ch;
    }
}
