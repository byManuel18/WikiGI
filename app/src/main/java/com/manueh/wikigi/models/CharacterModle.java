package com.manueh.wikigi.models;

import java.util.UUID;

import io.realm.Realm;

public class CharacterModle {


    public boolean insert(CharacterEntity character){
        boolean inserted=true;
        character.setId(UUID.randomUUID().toString());

        Realm realm=Realm.getDefaultInstance();

        try{
            realm.beginTransaction();
            realm.copyToRealm(character);
            realm.commitTransaction();

            realm.close();
        }catch (Exception ex){
            inserted=false;
        }

        return inserted;
    }
}
