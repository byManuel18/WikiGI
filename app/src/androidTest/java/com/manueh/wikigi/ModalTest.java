package com.manueh.wikigi;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.models.CharacterModle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ModalTest {

    private CharacterModle modal_test;

    @Before
    public void setUp(){this.modal_test= new CharacterModle();};

    @Test
    public void  TestAllModle(){
        CharacterEntity add_new=new CharacterEntity();
        add_new.setEquip(true);
        add_new.setRating(3);
        add_new.setConstellation("Constelacion de prueba");
        add_new.setAtk("3");
        add_new.setDef("3");
        add_new.setHp("3");
        add_new.setTier("A");
        add_new.setRol("Primero");
        add_new.setElement("Ele");
        add_new.setWeapon("Armita");
        add_new.setCreate_date("1/2/2021");
        add_new.setName("Personaje");


        String code=this.modal_test.insert(add_new);
        assertNotEquals("",code);


        List<CharacterEntity> alls=this.modal_test.getAllItemsToList();
        assertNotEquals(0,alls.size());
        List<String> strings_waepons=this.modal_test.getSpinnersValues(Fields_to_validate.WEAPON_FORM);
        assertNotEquals(0,strings_waepons.size());
        List<String> strings_tier=this.modal_test.getSpinnersValues(Fields_to_validate.TIER_FORM);
        assertNotEquals(0,strings_tier.size());
        List<String> strings_rol=this.modal_test.getSpinnersValues(Fields_to_validate.ROL_FORM);
        assertNotEquals(0,strings_rol.size());
        List<String> strings_element=this.modal_test.getSpinnersValues(Fields_to_validate.ELEMENT_FORM);
        assertNotEquals(0,strings_element.size());
        CharacterEntity viewInsert=this.modal_test.getCharacterEntity(code);
        assertNotEquals(null,viewInsert);

        List<CharacterEntity> byname=this.modal_test.SearchbyName(add_new.getName());
        assertNotEquals(0,byname.size());
        List<CharacterEntity> bydate=this.modal_test.SearchbyDate(add_new.getCreate_date());
        assertNotEquals(0,bydate.size());
        List<CharacterEntity> bytier=this.modal_test.SearchbyTier(add_new.getTier());
        assertNotEquals(0,bytier.size());
        List<CharacterEntity> byall=this.modal_test.SearchbyDateNameTier(add_new.getName(),add_new.getCreate_date(),add_new.getTier());
        assertNotEquals(0,byall.size());


        CharacterEntity toUpdate=add_new;
        toUpdate.setId(code);
        toUpdate.setName("UPDATE");
        assertEquals(true,this.modal_test.Update(toUpdate));


        assertEquals(true,this.modal_test.DeleteCharacter(code));

        add_new.setId(code);
        assertEquals(true,this.modal_test.insertNOIDGENERATE(add_new));

        assertEquals(true,this.modal_test.DeleteCharacter(code));


    }

}
