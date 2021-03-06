package com.manueh.wikigi.interfaces;

import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.views.MyApplication;

import java.util.List;

public interface IFormInterface {
    public interface View {
        void GoBackToList();
        void CloseActivity();
        void PermissionsDenied();
        void PermissionsGranted();
        void RequestPermissions();
        void SelectPicture();
        void CharacterSaved();
        void NoCharacterSaved();
        void ShowDeleteOk();
        void ShowDeleteFail();
        void ShowHelp();

    }

    public interface Presenter {
        void ReturnToList();
        void CloseFormActivity();
        void WriteExternalStoragePermission();
        void PermisionsAcepted();
        void PermisionDenied();
        void ShowGalery();
        void onClickSaveButton(CharacterEntity cn);
        void OnClickEditButton(CharacterEntity cn);
        CharacterEntity GetCharacterbyID(String id);
        List<String> GetValueSpinner(Fields_to_validate spinner);
        void DeleteCharacterEntity(String id);
        void GoHelp();
    }

}
