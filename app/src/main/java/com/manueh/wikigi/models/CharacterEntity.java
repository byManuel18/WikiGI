package com.manueh.wikigi.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterEntity {
    private String id;
    private String name;
    private LocalDate create_date;
    private String constellation;
    private String tier;
    private String weapon;
    private String element;
    private String rol;
    private int hp;
    private int atk;
    private int def;
    private double rating;
    private String image="";


    public CharacterEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    /*
        Set name
        return 0----->Correct
        return 1 ----> Error,name empty
        return 2---> Error, name can only contain letters

     */
    public int setName(String name) {
        int error=0;
        if(name!=null&&name.length()>0){
            Pattern pat = Pattern.compile("[A-Za-zÑñ ]+");
            Matcher mat = pat.matcher(name);
           if(mat.matches()){
               this.name = name.toUpperCase();
           }else{
               error=2;
           }
        }else{
            error=1;
        }
        return error;
    }


    public String getConstellation() {
        return constellation;
    }
    /*
       Set Constellation
       return 0----->Correct
       return 1 ----> Error,name empty
       return 2---> Error, name can only contain letters

    */
    public int setConstellation(String constellation) {
        int error=0;
        if(constellation!=null&&constellation.length()>0){
            Pattern pat = Pattern.compile("[A-Za-zÑñ ]+");
            Matcher mat = pat.matcher(constellation);
            if(mat.matches()){
                this.constellation = constellation.toUpperCase();
            }else{
                error=2;
            }
        }else{
            error=1;
        }

        return error;
    }

    public LocalDate getCreate_date() {
        return create_date;
    }
    /*
       Set Create_date
       return 0----->Correct
       return 1 ----> Error,date empty
       return 2---> Error, invalid date format
       return 3---> Error, invalid date

    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int setCreate_date(String create_date) {
        int rsult=-1;
        if(create_date!=null&&create_date.length()>0){
            Pattern pat = Pattern.compile("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");
            Pattern pat2= Pattern.compile("([12]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01]))");
            Matcher mat = pat.matcher(create_date);
            if(mat.matches()){
                LocalDate datetoadd;
                try{
                    datetoadd=LocalDate.parse(create_date);
                    this.create_date =datetoadd;
                    rsult=0;
                }catch (Exception e){
                    try{
                        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        datetoadd=LocalDate.parse(create_date,formatters);
                        this.create_date =datetoadd;
                    }catch (Exception ex){
                        rsult=3;
                    }

                }
            }else{
                rsult=2;
            }
        }else{
            rsult=1;
        }
        return rsult;
    }

    public int getHp() {
        return hp;
    }
    /*
        Set hp
       return 0----->Correct
       return 1 ----> Error,hp empty
       return 2---> Error, hp can only contain integer numbers
     */
    public int setHp(String hp) {
        int result=0;
        if(hp!=null&&hp.length()>0){
            Pattern pat = Pattern.compile("^\\d+$");
            Matcher mat = pat.matcher(hp);
            if(mat.matches()){
                this.hp = Integer.parseInt(hp);
            }else{
                result=2;
            }
        }else {
            result = 1;
        }
        return  result;
    }

    public int getAtk() {
        return atk;
    }
    /*
        Set atk
       return 0----->Correct
       return 1 ----> Error,atk empty
       return 2---> Error, atk can only contain integer numbers
     */
    public int setAtk(String atk) {
        int result=0;
        if(atk!=null&&atk.length()>0){
            Pattern pat = Pattern.compile("^\\d+$");
            Matcher mat = pat.matcher(atk);
            if(mat.matches()){
                this.atk = Integer.parseInt(atk);
            }else{
                result=2;
            }
        }else {
            result = 1;
        }
        return  result;
    }

    public int getDef() {
        return def;
    }
    /*
        Set def
       return 0----->Correct
       return 1 ----> Error,def empty
       return 2---> Error, def can only contain integer numbers
     */
    public int setDef(String def) {
        int result=0;
        if(def!=null&&def.length()>0){
            Pattern pat = Pattern.compile("^\\d+$");
            Matcher mat = pat.matcher(def);
            if(mat.matches()){
                this.def = Integer.parseInt(def);
            }else{
                result=2;
            }
        }else {
            result = 1;
        }
        return  result;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
