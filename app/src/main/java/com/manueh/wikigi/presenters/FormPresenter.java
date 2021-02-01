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
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.models.CharacterModle;
import com.manueh.wikigi.views.Form_activity;
import com.manueh.wikigi.views.MyApplication;

import java.util.List;

public class FormPresenter implements IFormInterface.Presenter {

    private IFormInterface.View view;
    private static String TAG="Formpresenter";
    private CharacterModle charactermodle;

    public FormPresenter(IFormInterface.View view) {
        this.view = view;
        this.charactermodle=new CharacterModle();
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
            case RATING_FORM:
                switch (error_code){
                    case 0:
                        ch=null;
                        break;
                    case 1:
                        ch=MyApplication.getContext().getResources().getString(R.string.ratingbar_error);
                        break;
                    default:
                        ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                }
                break;
            case ELEMENT_FORM:
                    switch (error_code){
                        case 0:
                            ch=null;
                            break;
                        case 1:
                            ch=MyApplication.getContext().getResources().getString(R.string.element_error_null);
                            break;
                        case 2:
                            ch=MyApplication.getContext().getResources().getString(R.string.element_error_novalue);
                            break;
                        default:
                            ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                    }
                break;
            case ROL_FORM:
                    switch (error_code){
                        case 0:
                            ch=null;
                            break;
                        case 1:
                            ch=MyApplication.getContext().getResources().getString(R.string.rol_error_null);
                            break;
                        case 2:
                            ch=MyApplication.getContext().getResources().getString(R.string.rol_error_novalue);
                            break;
                        default:
                            ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                    }
                break;
            case TIER_FORM:
                    switch (error_code){
                        case 0:
                            ch=null;
                            break;
                        case 1:
                            ch=MyApplication.getContext().getResources().getString(R.string.tier_error_null);
                            break;
                        case 2:
                            ch=MyApplication.getContext().getResources().getString(R.string.tier_error_novalue);
                            break;
                        default:
                            ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
                    }
                break;
            case WEAPON_FORM:
                    switch (error_code){
                        case 0:
                            ch=null;
                            break;
                        case 1:
                            ch=MyApplication.getContext().getResources().getString(R.string.weapons_error_null);
                            break;
                        case 2:
                            ch=MyApplication.getContext().getResources().getString(R.string.weapons_error_novalue);
                            break;
                        default:
                            ch= MyApplication.getContext().getResources().getString(R.string.name_error_default);
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

    @Override
    public void onClickSaveButton(CharacterEntity cn) {
        if(charactermodle.insert(cn)){
            view.CharacterSaved();
            view.CloseActivity();
        }else{
            view.NoCharacterSaved();
        }
    }

    @Override
    public void OnClickEditButton(CharacterEntity cn) {
        if(charactermodle.Update(cn)){
            view.CharacterSaved();
            view.CloseActivity();
        }else{
            view.NoCharacterSaved();
        }
    }

    @Override
    public CharacterEntity GetCharacterbyID(String id) {
        return  charactermodle.getCharacterEntity(id);
    }

    @Override
    public List<String> GetValueSpinner(Fields_to_validate spinner) {

        return charactermodle.getSpinnersValues(spinner);
    }

    @Override
    public void DeleteCharacterEntity(String id) {
        if(charactermodle.DeleteCharacter(id)){
            view.ShowDeleteOk();
            this.CloseFormActivity();
        }else{
            view.ShowDeleteFail();
        }
    }
}
