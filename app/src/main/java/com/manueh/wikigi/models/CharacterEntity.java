package com.manueh.wikigi.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.manueh.wikigi.R;
import com.manueh.wikigi.views.MyApplication;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CharacterEntity extends RealmObject {

    @PrimaryKey
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

    public int setRating(double rating) {
        int error=0;
        if(rating<0||rating>5){
            error=1;
        }else{
            this.rating = rating;
        }
        return error;
    }

    public String getTier() {
        return tier;
    }

    public int setTier(String tier) {
        int error=0;
            if(tier==null){
                error=1;
            }else if(tier.equals(MyApplication.getContext().getString(R.string.spinner_tier_title))){
                error=2;
            }else{
                this.tier = tier;
            }
        return error;
    }

    public String getWeapon() {
        return weapon;
    }

    public int setWeapon(String weapon){
        int error=0;
        if(weapon==null){
            error=1;
        }else if(weapon.equals(MyApplication.getContext().getString(R.string.spinner_title))){
            error=2;
        }else{
            this.weapon = weapon;
        }
        return error;
    }


    public String getElement() {
        return element;
    }

    public int setElement(String element) {
        int error=0;
        if(element==null){
            error=1;
        }else if(element.equals(MyApplication.getContext().getString(R.string.spinner_elements))){
            error=2;
        }else{
            this.element = element;
        }
        return error;
    }

    public String getRol() {
        return rol;
    }

    public int setRol(String rol) {
        int error=0;
        if(rol==null){
            error=1;
        }else if(rol.equals(MyApplication.getContext().getString(R.string.spinner_rol_title))){
            error=2;
        }else{
            this.rol = rol;
        }
        return error;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", create_date=" + create_date +
                ", constellation='" + constellation + '\'' +
                ", tier='" + tier + '\'' +
                ", weapon='" + weapon + '\'' +
                ", element='" + element + '\'' +
                ", rol='" + rol + '\'' +
                ", hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", rating=" + rating +
                '\'' +
                '}';
    }
}
