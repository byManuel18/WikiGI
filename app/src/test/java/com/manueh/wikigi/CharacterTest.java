package com.manueh.wikigi;

import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.views.MyApplication;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CharacterTest {
    private CharacterEntity character_test;

    @Before
    public void setUp(){this.character_test= new CharacterEntity();

    };

    @Test
    public void Test_Name(){
        assertEquals(0,this.character_test.setName("Diluc"));
        assertEquals(1,this.character_test.setName(null));
        assertEquals(2,this.character_test.setName("Diluc3"));
        assertEquals(1,this.character_test.setName(""));
        assertEquals("DILUC",this.character_test.getName());
    }

    @Test
    public void Test_Constellation(){
        assertEquals(0,this.character_test.setConstellation("Prueba"));
        assertEquals(1,this.character_test.setConstellation(null));
        assertEquals(2,this.character_test.setConstellation("Prueba3"));
        assertEquals(1,this.character_test.setConstellation(""));
        assertEquals("PRUEBA",this.character_test.getConstellation());
    }

    @Test
    public void Test_CretateDate(){
        assertEquals(2,this.character_test.setCreate_date("Prueba"));
        assertEquals(1,this.character_test.setCreate_date(""));
        assertEquals(1,this.character_test.setCreate_date(null));
        assertEquals(0,this.character_test.setCreate_date("01-02-2021"));
        assertEquals(0,this.character_test.setCreate_date("01/02/2021"));
        assertEquals(2,this.character_test.setCreate_date("2021/02/01"));
        assertEquals(2,this.character_test.setCreate_date("2021/2/1"));
        assertEquals(2,this.character_test.setCreate_date("2021-2-1"));
        assertEquals(2,this.character_test.setCreate_date("2021-02-01"));
        assertEquals(0,this.character_test.setCreate_date("28-02-2021"));
        assertEquals(3,this.character_test.setCreate_date("29-02-2021"));
        assertEquals(0,this.character_test.setCreate_date("1/2/2021"));
        assertEquals("01/02/2021",this.character_test.getCreate_date());
        assertEquals(0,this.character_test.setCreate_date("1-2-2021"));
        assertEquals("01/02/2021",this.character_test.getCreate_date());
    }

    @Test
    public void Test_Hp(){
        assertEquals(2,this.character_test.setHp("Prueba3"));
        assertEquals(1,this.character_test.setHp(null));
        assertEquals(1,this.character_test.setHp(""));
        assertEquals(2,this.character_test.setHp("-23"));
        assertEquals(0,this.character_test.setHp("456"));
        assertEquals(456,this.character_test.getHp());
    }

    @Test
    public void Test_Atk(){
        assertEquals(2,this.character_test.setAtk("Prueba4"));
        assertEquals(1,this.character_test.setAtk(null));
        assertEquals(1,this.character_test.setAtk(""));
        assertEquals(2,this.character_test.setAtk("-89"));
        assertEquals(0,this.character_test.setAtk("342"));
        assertEquals(342,this.character_test.getAtk());
    }

    @Test
    public void Test_Def(){
        assertEquals(2,this.character_test.setDef("Prueba5"));
        assertEquals(1,this.character_test.setDef(null));
        assertEquals(1,this.character_test.setDef(""));
        assertEquals(2,this.character_test.setDef("-189"));
        assertEquals(0,this.character_test.setDef("23"));
        assertEquals(23,this.character_test.getDef());
    }

    @Test
    public void Test_Rating(){
        assertEquals(0,this.character_test.setRating(1));
        assertEquals(0,this.character_test.setRating(0));
        assertEquals(0,this.character_test.setRating(5));
        assertEquals(1,this.character_test.setRating(6));
        assertEquals(1,this.character_test.setRating(-12));
        assertEquals(0,this.character_test.setRating(3.5));

    }
}
