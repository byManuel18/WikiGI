package com.manueh.wikigi.models;

import android.util.Log;

import com.manueh.wikigi.enums.Fields_to_validate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CharacterModle {


    public ArrayList<CharacterEntity> getAllItemsToList(){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();

        try (Realm realm = Realm.getDefaultInstance()){
            realm.beginTransaction();
            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            for (CharacterEntity c: allitems) {
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
            realm.commitTransaction();
        }
        /*Realm realm=Realm.getDefaultInstance();
        try{


        }catch (Exception e){
            realm.close();
        }*/

        return resumeAll;
    }
    public String insert(CharacterEntity character){
        String inserted="";
        String id=UUID.randomUUID().toString();
        character.setId(id);
        if(character!=null){
            try (Realm realm = Realm.getDefaultInstance()){
                realm.beginTransaction();
                realm.copyToRealm(character);
                inserted=id;
                Log.d("SI", "SIVA");
                realm.commitTransaction();
            }

        }else{
            inserted="";
        }
        return inserted;
    }

    public boolean insertNOIDGENERATE(CharacterEntity character){
        boolean inserted=false;

        if(character!=null){
            try (Realm realm = Realm.getDefaultInstance()){
                realm.beginTransaction();
                realm.copyToRealm(character);
                realm.commitTransaction();
                inserted=true;
            }
        }else{
            inserted=false;
        }
        return inserted;
    }

    public boolean Update(CharacterEntity character){
        boolean updated=false;

        if(character!=null){
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(character);
                realm.commitTransaction();
                updated = true;
            }
        }

        return updated;
    }

    public CharacterEntity getCharacterEntity(String id){
        CharacterEntity ce=null;
        if(id!=null){
            try (Realm realm = Realm.getDefaultInstance()){
                realm.beginTransaction();
                ce=realm.where(CharacterEntity.class).equalTo("id", id).findFirst();
                realm.commitTransaction();
            }

        }
        return  ce;
    }

    public boolean DeleteCharacter(String  id){
        boolean deleted=false;

        if(id!=null){
            try (Realm realm = Realm.getDefaultInstance()){
                realm.beginTransaction();
                CharacterEntity characterRealm = realm.where(CharacterEntity.class)
                        .equalTo("id",id)
                        .findFirst();
                characterRealm.deleteFromRealm();
                realm.commitTransaction();
                deleted=true;
            }
        }

        return deleted;
    }

    public List<String> getSpinnersValues(Fields_to_validate typeSpinner){
        List<String> valuesSpi=new ArrayList<>();
        List<CharacterEntity> celist=new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        String spinner="";
        if(typeSpinner==Fields_to_validate.ELEMENT_FORM){
            spinner="element";
        }else if (typeSpinner==Fields_to_validate.ROL_FORM){
            spinner="rol";
        }else if (typeSpinner==Fields_to_validate.TIER_FORM){
            spinner="tier";
        }else if (typeSpinner==Fields_to_validate.WEAPON_FORM){
            spinner="weapon";
        }
        if(typeSpinner!=null){
            try{
                realm.beginTransaction();
                RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).distinct(spinner).findAll();
                celist.addAll(realm.copyFromRealm(result));
                realm.commitTransaction();
                realm.close();
            }catch (Exception e){
                realm.close();
            }
            for(CharacterEntity ce:celist){
                if(typeSpinner==Fields_to_validate.ELEMENT_FORM){
                    valuesSpi.add(ce.getElement());
                }else if (typeSpinner==Fields_to_validate.ROL_FORM){
                    valuesSpi.add(ce.getRol());
                }else if (typeSpinner==Fields_to_validate.TIER_FORM){
                    valuesSpi.add(ce.getTier());
                }else if (typeSpinner==Fields_to_validate.WEAPON_FORM){
                    valuesSpi.add(ce.getWeapon());
                }
            }
        }
        return valuesSpi;
    }
    public ArrayList<CharacterEntity> SearchbyName(String name){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        try{

            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).equalTo("name",name).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            realm.close();
            for (CharacterEntity c: allitems) {
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
            realm.close();
        }

        return resumeAll;
    }
    public ArrayList<CharacterEntity> SearchbyTier(String tier){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        try{

            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).equalTo("tier",tier).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            realm.close();
            for (CharacterEntity c: allitems) {
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
            realm.close();
        }

        return resumeAll;
    }

    public ArrayList<CharacterEntity> SearchbyDate(String date){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        try{

            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).equalTo("create_date",date).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            realm.close();
            for (CharacterEntity c: allitems) {
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
            realm.close();
        }

        return resumeAll;
    }

    public ArrayList<CharacterEntity> SearchbyDateNameTier(String name,String date,String tier){
        ArrayList<CharacterEntity> allitems=new ArrayList<>();
        ArrayList<CharacterEntity> resumeAll=new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        try{

            //realm.where(CharacterEntity.class).equalTo("create_date",date).and().equalTo("name",name).and().equalTo("tier",tier).findAll();
            RealmResults<CharacterEntity> result=realm.where(CharacterEntity.class).equalTo("create_date",date).and().
                    equalTo("name",name).and().equalTo("tier",tier).findAll();
            allitems.addAll(realm.copyFromRealm(result));
            realm.close();
            for (CharacterEntity c: allitems) {
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
            realm.close();
        }

        return resumeAll;
    }
}
