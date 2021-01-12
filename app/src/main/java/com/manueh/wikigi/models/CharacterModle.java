package com.manueh.wikigi.models;

import android.util.Log;

import java.util.UUID;

import io.realm.Realm;

public class CharacterModle {


    public boolean insert(CharacterEntity character){
        boolean inserted=true;
        character.setId(UUID.randomUUID().toString());
        Realm realm=Realm.getDefaultInstance();
        if(character!=null){
            try{
                realm.beginTransaction();
                realm.copyToRealm(character);
                realm.commitTransaction();

                realm.close();
                Log.d("SI", "SIVA");
            }catch (Exception ex){
                inserted=false;
                Log.d("NO", "NOVA");
            }
        }else{
            inserted=false;
        }
        return inserted;
    }
}
