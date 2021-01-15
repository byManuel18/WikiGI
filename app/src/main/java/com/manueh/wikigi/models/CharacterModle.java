package com.manueh.wikigi.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CharacterModle {


    public ArrayList<CharacterEntity> getAllItemsToList(){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();
        try{
            Realm realm=Realm.getDefaultInstance();
            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            realm.close();
            for (CharacterEntity c: allitems) {
                Log.d("RECOGIDA DE DATOS", c.toString());
                CharacterEntity ir = new CharacterEntity();
                ir.setImage(c.getImage());
                ir.setId(c.getId());
                ir.setName(c.getName());
                ir.setWeapon(c.getWeapon());
                ir.setElement(c.getElement());
                ir.setRol(c.getRol());
                ir.setTier(c.getTier());
                ir.setHp(Integer.toString(c.getHp()));
                ir.setDef(Integer.toString(c.getDef()));
                ir.setAtk(Integer.toString(c.getAtk()));
                ir.setConstellation(c.getConstellation());
                ir.setRating(c.getRating());
                resumeAll.add(ir);
            }
        }catch (Exception e){

        }

        return resumeAll;
    }
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
