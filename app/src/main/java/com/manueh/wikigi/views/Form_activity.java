package com.manueh.wikigi.views;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Codes_Permisions;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IFormInterface;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.presenters.FormPresenter;
import com.manueh.wikigi.presenters.ListPresenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form_activity extends AppCompatActivity implements IFormInterface.View {
    private Window window;
    private IFormInterface.Presenter fpresenter;
    private  String TAG="Wikigi/Form_Activity";
    private TextInputEditText nameET;
    private TextInputEditText dateformET;
    private TextInputEditText constellationformET;
    private TextInputEditText hpformET;
    private TextInputEditText atkformET;
    private TextInputEditText defET;
    private CharacterEntity character;
    private Context myContext;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day ;
    private ImageView imagedate;
    private ArrayAdapter<String> adapter_weapons;
    private ArrayAdapter<String> adapter_tier;
    private ArrayAdapter<String> adapter_rol;
    private ArrayAdapter<String> adapter_elements;
    private ImageView image_weapons;
    private ImageView image_elements;
    private ImageView imagetier;
    private ImageView imagerol;
    private Button save;
    private ImageView img_galery;
    private String id;
    private ImageView image_remuve;
    private ConstraintLayout constraintLayoutFormActivity;
    private RatingBar rtform;
    private static final int REQUEST_SELECT_IMAGE = 201;
    private Switch switch_equip;
    private CharacterEntity toedit=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_activity);
        this.window=getWindow();
        this.window.setNavigationBarColor(getResources().getColor(R.color.black));
        myContext=this;
        fpresenter=new FormPresenter(this);
        Log.d(TAG,"Obtener fecha actual");
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        id=getIntent().getStringExtra("id");
        Log.d(TAG,"Creación de la ToolBar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"Creación de la flecha hacia atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_form_activity));
        save=findViewById(R.id.save_button);
        img_galery=findViewById(R.id.image_galery);
        switch_equip=findViewById(R.id.switch_equip);
        rtform=findViewById(R.id.rtform);
        nameET=findViewById(R.id.name_form);
        dateformET=findViewById(R.id.date_textInputEdit);
        constellationformET=findViewById(R.id.constellation_textinputedit);
        hpformET=findViewById(R.id.hp_textinputEditText);
        atkformET=findViewById(R.id.atk_textInputEditText);
        defET=findViewById(R.id.def_textInputEditText);
        Spinner spinner_weapons = (Spinner) findViewById(R.id.spinner_arm);
        Spinner spinner_element = (Spinner) findViewById(R.id.spinner_element);
        Spinner spinner_tier = (Spinner) findViewById(R.id.spinner_tier);
        Spinner spinner_rol = (Spinner) findViewById(R.id.spinner_rol);
        Button clear_form=findViewById(R.id.clear_button);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Al clikar vuelve hacia detrás");
                fpresenter.ReturnToList();
            }
        });
        constraintLayoutFormActivity=findViewById(R.id.constraintLayout_form);

        img_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Permisos");
                fpresenter.WriteExternalStoragePermission();

            }
        });


        character=new CharacterEntity();

        imagedate=findViewById(R.id.imagedate);
        imagedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Crear calendario al clickar en la imagen");
                // Definir el calendario con la fecha seleccionada por defecto
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir la acción al pulsar OK en el calendario
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Asignar la fecha a un campo de texto
                        String day_=null;
                        String month_=null;
                        String year_=null;
                        month++;
                        year_=Integer.toString(year);
                        if(day>9){
                            day_=Integer.toString(day);
                        }else{
                            day_="0"+day;
                        }
                        if(month>9){
                            month_=Integer.toString(month);
                        }else{
                            month_="0"+month;
                        }
                        dateformET.setText(day_+ "/" + month_+ "/" + year_);
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });

        Log.d(TAG,"Da valores al spiner de armas");
        List<String> list_weapons=new ArrayList<>();
        list_weapons.add(MyApplication.getContext().getString(R.string.spinner_title));
        /*list_weapons.add(getResources().getString(R.string.spinner_title));
        list_weapons.add(getResources().getString(R.string.spinner_catalyst));
        list_weapons.add(getResources().getString(R.string.spinner_claymore));
        list_weapons.add(getResources().getString(R.string.spinner_spear));
        list_weapons.add(getResources().getString(R.string.spinner_sword));
        list_weapons.add(getResources().getString(R.string.spinner_bow));*/
        list_weapons.addAll(fpresenter.GetValueSpinner(Fields_to_validate.WEAPON_FORM));
        adapter_weapons=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_weapons);
        adapter_weapons.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_weapons.setAdapter(adapter_weapons);
        image_weapons=findViewById(R.id.image_weapons);
        image_weapons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Crear el alertdialog");
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.spinner_dialog, null);
                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);
                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.spinner_add_textedit);
                dialogInput.setHint(getResources().getString(R.string.spinner_add));
                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.spinner_add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si aceptamos en el alertdialog");
                                        boolean toadd=true;
                                        Pattern pat = Pattern.compile("[A-Za-zÑñ ]+");
                                        Matcher mat = pat.matcher(dialogInput.getText().toString());
                                        if(mat.matches()){
                                            for(String s:list_weapons){
                                                if(dialogInput.getText().toString().length()>0){
                                                    if(s.toUpperCase().equals(dialogInput.getText().toString().toUpperCase())){
                                                        toadd=false;
                                                        spinner_weapons.setSelection(adapter_weapons.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                        break;
                                                    }
                                                }else{
                                                    toadd=false;
                                                    break;
                                                }

                                            }
                                            if(toadd){
                                                adapter_weapons.add(dialogInput.getText().toString().toUpperCase());
                                                spinner_weapons.setSelection(adapter_weapons.getPosition(dialogInput.getText().toString().toUpperCase()));
                                            }
                                        }else{
                                            toadd=false;
                                        }


                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.spinner_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si cancelamos el alertdialog");
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        Log.d(TAG,"Da valores al spiner de elementos");

        List<String> list_elements=new ArrayList<>();
        list_elements.addAll(fpresenter.GetValueSpinner(Fields_to_validate.ELEMENT_FORM));
        list_elements.add(MyApplication.getContext().getString(R.string.spinner_elements));
        /*list_elements.add(getResources().getString(R.string.spinner_elements));
        list_elements.add(getResources().getString(R.string.spinner_ele1));
        list_elements.add(getResources().getString(R.string.spinner_ele2));
        list_elements.add(getResources().getString(R.string.spinner_ele3));
        list_elements.add(getResources().getString(R.string.spinner_ele4));
        list_elements.add(getResources().getString(R.string.spinner_ele5));
        list_elements.add(getResources().getString(R.string.spinner_ele6));*/
        adapter_elements=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_elements);
        adapter_elements.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_element.setAdapter(adapter_elements);
        image_elements=findViewById(R.id.image_elements);
        image_elements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Crear el alertdialog");
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.spinner_dialog, null);
                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);
                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.spinner_add_textedit);
                dialogInput.setHint(getResources().getString(R.string.spinner_add));
                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.spinner_add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si aceptamos el alertDialog");
                                        boolean toadd=true;
                                        Pattern pat = Pattern.compile("[A-Za-zÑñ ]+");
                                        Matcher mat = pat.matcher(dialogInput.getText().toString());
                                        if(mat.matches()){
                                            for(String s:list_elements){
                                                if(dialogInput.getText().toString().length()>0){
                                                    if(s.toUpperCase().equals(dialogInput.getText().toString().toUpperCase())){
                                                        toadd=false;
                                                        spinner_element.setSelection(adapter_elements.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                        break;
                                                    }
                                                }else{
                                                    toadd=false;
                                                    break;
                                                }

                                            }
                                            if(toadd){
                                                adapter_elements.add(dialogInput.getText().toString().toUpperCase());
                                                spinner_element.setSelection(adapter_elements.getPosition(dialogInput.getText().toString().toUpperCase()));
                                            }
                                        }else{
                                            toadd=false;
                                        }

                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.spinner_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si cancelamos el alertdialog");
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        Log.d(TAG,"Da valores al spiner de tier");

        List<String> list_tier=new ArrayList<>();
        list_tier.addAll(fpresenter.GetValueSpinner(Fields_to_validate.TIER_FORM));
        list_tier.add(MyApplication.getContext().getString(R.string.spinner_tier_title));
        /*list_tier.add(getResources().getString(R.string.spinner_tier_title));
        list_tier.add(getResources().getString(R.string.spinner_tiers));
        list_tier.add(getResources().getString(R.string.spinner_tiera));
        list_tier.add(getResources().getString(R.string.spinner_tierb));
        list_tier.add(getResources().getString(R.string.spinner_tierc));
        list_tier.add(getResources().getString(R.string.spinner_tierd));*/
        adapter_tier=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_tier);
        adapter_tier.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_tier.setAdapter(adapter_tier);
        imagetier=findViewById(R.id.imagetier);
        imagetier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Crear el alertdialog");
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.spinner_dialog, null);
                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);
                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.spinner_add_textedit);
                dialogInput.setHint(getResources().getString(R.string.spinner_add));
                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.spinner_add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si aceptamos el alertdialog");
                                        boolean toadd=true;
                                        Pattern pat = Pattern.compile("[A-Za-zÑñ]");
                                        Matcher mat = pat.matcher(dialogInput.getText().toString());
                                        if(mat.matches()){
                                            for(String s:list_tier){
                                                if(dialogInput.getText().toString().length()>0){
                                                    if(s.toUpperCase().equals(dialogInput.getText().toString().toUpperCase())){
                                                        toadd=false;
                                                        spinner_tier.setSelection(adapter_tier.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                        break;
                                                    }
                                                }else{
                                                    toadd=false;
                                                    break;
                                                }

                                            }
                                            if(toadd){
                                                adapter_tier.add(dialogInput.getText().toString().toUpperCase());
                                                spinner_tier.setSelection(adapter_tier.getPosition(dialogInput.getText().toString().toUpperCase()));
                                            }
                                        }else{
                                            toadd=false;
                                        }

                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.spinner_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si cancelamos el alertdialog");
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });

        Log.d(TAG,"Da valores al spiner de rol");

        List<String> list_rol=new ArrayList<>();
        list_rol.addAll(fpresenter.GetValueSpinner(Fields_to_validate.ROL_FORM));
        list_rol.add(MyApplication.getContext().getString(R.string.spinner_rol_title));
    /*list_rol.add(getResources().getString(R.string.spinner_rol_title));
        list_rol.add(getResources().getString(R.string.spinner_rol1));
        list_rol.add(getResources().getString(R.string.spinner_rol2));
        list_rol.add(getResources().getString(R.string.spinner_rol3));
        list_rol.add(getResources().getString(R.string.spinner_rol4));*/
        adapter_rol=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_rol);
        adapter_rol.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_rol.setAdapter(adapter_rol);
        imagerol=findViewById(R.id.imagerol);
        imagerol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Crear el alertdialog");
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.spinner_dialog, null);
                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);
                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.spinner_add_textedit);
                dialogInput.setHint(getResources().getString(R.string.spinner_add));
                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.spinner_add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si aceptamos el alertdialog");
                                        boolean toadd=true;
                                        Pattern pat = Pattern.compile("[A-Za-zÑñ]+");
                                        Matcher mat = pat.matcher(dialogInput.getText().toString());
                                        if(mat.matches()){
                                            for(String s:list_rol){
                                                if(dialogInput.getText().toString().length()>0){
                                                    if(s.toUpperCase().equals(dialogInput.getText().toString().toUpperCase())){
                                                        toadd=false;
                                                        spinner_rol.setSelection(adapter_rol.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                        break;
                                                    }
                                                }else{
                                                    toadd=false;
                                                    break;
                                                }

                                            }
                                            if(toadd){
                                                adapter_rol.add(dialogInput.getText().toString().toUpperCase());
                                                spinner_rol.setSelection(adapter_rol.getPosition(dialogInput.getText().toString().toUpperCase()));
                                            }
                                        }else{
                                            toadd=false;
                                        }

                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.spinner_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        Log.d(TAG,"Si cancelamos el alertdialog");
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        image_remuve=findViewById(R.id.image_remuve);

        clear_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Baciar campos");
                defET.setText("");
                defET.setError(null);
                atkformET.setText("");
                atkformET.setError(null);
                hpformET.setText("");
                hpformET.setError(null);
                nameET.setText("");
                nameET.setError(null);
                constellationformET.setText("");
                constellationformET.setError(null);
                dateformET.setText("");
                dateformET.setError(null);
                spinner_element.setSelection(0);
                spinner_rol.setSelection(0);
                spinner_tier.setSelection(0);
                spinner_weapons.setSelection(0);
                img_galery.setImageBitmap(null);
                img_galery.setBackground(ContextCompat.getDrawable(myContext,R.drawable.ic_menu_camera));
            }
        });

        if(id!=null){
            //Recupero info
            save.setText("Editar");
            toedit=fpresenter.GetCharacterbyID(id);
            if(toedit!=null){
                nameET.setText(toedit.getName());
                constellationformET.setText(toedit.getConstellation());
                //Log.d(TAG, character.toString());
                atkformET.setText(Integer.toString(toedit.getAtk()));
                defET.setText(Integer.toString(toedit.getDef()));
                dateformET.setText(toedit.getCreate_date());
                hpformET.setText(Integer.toString(toedit.getHp()));
                switch_equip.setChecked(toedit.isEquip());
                rtform.setRating((float)toedit.getRating());
                spinner_rol.setSelection(adapter_rol.getPosition(toedit.getRol()));
                spinner_element.setSelection(adapter_elements.getPosition(toedit.getElement()));
                spinner_tier.setSelection(adapter_tier.getPosition(toedit.getTier()));
                spinner_weapons.setSelection(adapter_weapons.getPosition(toedit.getWeapon()));
                if(character.getImage()!=null&&!toedit.equals("")) {
                    byte[] decodedString = Base64.decode(toedit.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    img_galery.setImageBitmap(decodedByte);
                }
            }

        }else{
            spinner_rol.setSelection(adapter_rol.getPosition(MyApplication.getContext().getString(R.string.spinner_rol_title)));
            spinner_element.setSelection(adapter_elements.getPosition(MyApplication.getContext().getString(R.string.spinner_elements)));
            spinner_tier.setSelection(adapter_tier.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));
            spinner_weapons.setSelection(adapter_weapons.getPosition(MyApplication.getContext().getString(R.string.spinner_title)));
        }

        image_remuve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_galery.setImageBitmap(null);
                img_galery.setBackground(ContextCompat.getDrawable(myContext,R.drawable.ic_menu_camera)); //@android:drawable/ic_menu_camera
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdelete = new AlertDialog.Builder(Form_activity.this);
                // alertdelete.setTitle(MyApplication.getContext().getResources().getString(R.string.button_delete));
                alertdelete.setMessage(MyApplication.getContext().getResources().getString(R.string.title_alert_save));

                alertdelete.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.title_alert_save_acept), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,"Yes button clicked");
                       /* Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.title_alert_save_acept_done), Toast.LENGTH_LONG);

                        toast1.show();*/
                        boolean allcorrect=true;
                        CharacterEntity newCharacter=new CharacterEntity();

                            //Meterle todo y mostrar que esté bien
                            //Si tienen errores no hacer nada

                        int ne=newCharacter.setName(nameET.getText().toString());
                        if(ne==0){
                            nameET.setError(null);
                        }else{
                            allcorrect=false;
                            nameET.setError(FormPresenter.getError(ne,Fields_to_validate.NAME_FORM));
                        }

                        ne=newCharacter.setCreate_date(dateformET.getText().toString());
                        Log.d(TAG, ne+" Error  "+dateformET.getText().toString());
                        if(ne==0){
                            dateformET.setError(null);
                        }else{
                            allcorrect=false;
                            dateformET.setError(FormPresenter.getError(ne,Fields_to_validate.DATE_FORM));
                        }

                        ne=newCharacter.setConstellation(constellationformET.getText().toString());
                        if(ne==0){
                            constellationformET.setError(null);
                        }else{
                            allcorrect=false;
                            constellationformET.setError(FormPresenter.getError(ne,Fields_to_validate.CONSTELLATION_FORM));
                        }

                        ne=newCharacter.setAtk(atkformET.getText().toString());
                        if(ne==0){
                            atkformET.setError(null);
                        }else{
                            allcorrect=false;
                            atkformET.setError(FormPresenter.getError(ne,Fields_to_validate.ATK_FORM));
                        }

                        ne=newCharacter.setDef(defET.getText().toString());
                        if(ne==0){
                            defET.setError(null);
                        }else{
                            allcorrect=false;
                            defET.setError(FormPresenter.getError(ne,Fields_to_validate.DEF_FORM));
                        }

                        ne=newCharacter.setHp(hpformET.getText().toString());
                        if(ne==0){
                            hpformET.setError(null);
                        }else{
                            allcorrect=false;
                            hpformET.setError(FormPresenter.getError(ne,Fields_to_validate.HP_FORM));
                        }

                        ne=newCharacter.setRating(rtform.getRating());
                        if(ne==0){

                        }else{
                            allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),FormPresenter.getError(ne,Fields_to_validate.RATING_FORM), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        ne=newCharacter.setElement(spinner_element.getSelectedItem().toString());
                        if(ne!=0){
                            allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),FormPresenter.getError(ne,Fields_to_validate.ELEMENT_FORM), Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        ne=newCharacter.setRol(spinner_rol.getSelectedItem().toString());
                        if(ne!=0){
                            allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),FormPresenter.getError(ne,Fields_to_validate.ROL_FORM), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        ne=newCharacter.setWeapon(spinner_weapons.getSelectedItem().toString());
                        if(ne!=0){
                            allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),FormPresenter.getError(ne,Fields_to_validate.WEAPON_FORM), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        ne=newCharacter.setTier(spinner_tier.getSelectedItem().toString());
                        if(ne!=0){
                            allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),FormPresenter.getError(ne,Fields_to_validate.TIER_FORM), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        newCharacter.setEquip(switch_equip.isChecked());
                        //Log.d(TAG, pathphotogalery);
                        if(img_galery!=null&&img_galery.getDrawable()!=null){
                            Bitmap bitmap = ((BitmapDrawable) img_galery.getDrawable()).getBitmap();
                            if(bitmap!=null){
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                String imgEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                newCharacter.setImage(imgEnBase64);
                            }else{
                            /*allcorrect=false;
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),R.string.imagengalery_error_novalue, Toast.LENGTH_SHORT);
                            toast.show();*/
                            }
                        }



                        if(allcorrect){
                            if(id==null){
                                fpresenter.onClickSaveButton(newCharacter);
                            }else{
                                newCharacter.setId(toedit.getId());
                                fpresenter.OnClickEditButton(newCharacter);
                            }

                            //Log.d(TAG, newCharacter.toString());
                            //fpresenter.CloseFormActivity();
                        }

                    }
                });

                alertdelete.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.spinner_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "No button clicked");
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText Name form");
                    int error_code=character.setName(nameET.getText().toString());
                    nameET.setError(FormPresenter.getError(error_code, Fields_to_validate.NAME_FORM));
                }else{
                    Log.d(TAG,"Input EditText Name form");
                }
            }
        });

        dateformET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText create date form");
                    int error_code=character.setCreate_date(dateformET.getText().toString());
                    dateformET.setError(FormPresenter.getError(error_code,Fields_to_validate.DATE_FORM));

                }else{
                    Log.d(TAG,"Input EditText create date form");
                }
            }
        });

        constellationformET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText constellation form");
                    int error_code=character.setConstellation(constellationformET.getText().toString());
                    constellationformET.setError(FormPresenter.getError(error_code,Fields_to_validate.CONSTELLATION_FORM));
                }else{
                    Log.d(TAG,"Input EditText constellation form");
                }
            }
        });

        hpformET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText hp form");
                    int err_code=character.setHp(hpformET.getText().toString());
                    hpformET.setError(FormPresenter.getError(err_code,Fields_to_validate.HP_FORM));
                }else{
                    Log.d(TAG,"Input EditText hp form");
                }
            }
        });


        atkformET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText atk form");
                    int err_code=character.setAtk(atkformET.getText().toString());
                    atkformET.setError(FormPresenter.getError(err_code,Fields_to_validate.ATK_FORM));
                }else{
                    Log.d(TAG,"Input EditText atk form");
                }
            }
        });


        defET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit EditText def form");
                    int err_code=character.setDef(defET.getText().toString());
                    defET.setError(FormPresenter.getError(err_code,Fields_to_validate.DEF_FORM));
                }else{
                    Log.d(TAG,"Input EditText def form");
                }
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case   123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    fpresenter.PermisionsAcepted();
                } else {
                    // Permiso rechazado
                    fpresenter.PermisionDenied();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        if(this.id==null){
            MenuItem item = menu.findItem(R.id.action_delete);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_delete){
            Log.d(TAG,"Menú borrar personaje");

            AlertDialog.Builder alertdelete = new AlertDialog.Builder(Form_activity.this);
           // alertdelete.setTitle(MyApplication.getContext().getResources().getString(R.string.button_delete));
            alertdelete.setMessage(MyApplication.getContext().getResources().getString(R.string.title_alert_delete_));

            alertdelete.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.title_alert_delete_acept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG,"Yes button clicked");
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.title_alert_delete_acept_done), Toast.LENGTH_LONG);

                    toast1.show();
                    fpresenter.CloseFormActivity();
                }
            });

            alertdelete.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.spinner_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "No button clicked");
                    dialog.dismiss();
                }
            }).create().show();


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void GoBackToList() {
        onBackPressed();
    }

    @Override
    public void CloseActivity() {
        finish();
    }

    @Override
    public void PermissionsDenied() {
        Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void PermissionsGranted() {
        // Permiso aceptado
        Snackbar.make(constraintLayoutFormActivity, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                .show();
        fpresenter.ShowGalery();
    }

    @Override
    public void RequestPermissions() {
        ActivityCompat.requestPermissions(Form_activity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, Codes_Permisions.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION.getCode());
        // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
        // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
    }
    @Override
    public void SelectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    public void CharacterSaved() {
        Toast toast =
                Toast.makeText(getApplicationContext(),R.string.title_alert_save_acept_done, Toast.LENGTH_SHORT);
    }

    @Override
    public void NoCharacterSaved() {
        Toast toast =
                Toast.makeText(getApplicationContext(),R.string.title_alert_save_acept_done, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        /*
            case (REQUEST_CAPTURE_IMAGE):
                if(resultCode == Activity.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break; */

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();
                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.image_galery);
                        imageView.setImageBitmap(bmp);
                        imageView.setBackground(null);
                    }
                }
                break;
        }
    }
}