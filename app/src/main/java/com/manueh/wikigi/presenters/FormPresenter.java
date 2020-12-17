package com.manueh.wikigi.presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Codes_Permisions;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IFormInterface;
import com.manueh.wikigi.views.Form_activity;
import com.manueh.wikigi.views.MyApplication;

public class FormPresenter implements IFormInterface.Presenter {

    private IFormInterface.View view;
    private static String TAG="Formpresenter";

    public FormPresenter(IFormInterface.View view) {
        this.view = view;
    }

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

    @Override
    public void ReturnToList() {
        view.GoBackToList();
    }

    @Override
    public void CloseFormActivity() {
        view.CloseActivity();
    }

    @Override
    public void WriteExternalStoragePermission() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.d(TAG, "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               view.RequestPermissions();
            } else {
               // view.PermissionsDenied();
                PermisionDenied();
            }
        } else {
           // view.PermissionsGranted();
            PermisionsAcepted();
        }
    }

    @Override
    public void PermisionsAcepted() {
        view.PermissionsGranted();
    }

    @Override
    public void PermisionDenied() {
        view.PermissionsDenied();
    }

    @Override
    public void ShowGalery() {
        view.SelectPicture();
    }
}
