package com.manueh.wikigi.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.IFormInterface;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.presenters.FormPresenter;
import com.manueh.wikigi.presenters.ListPresenter;

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

        Log.d(TAG,"Creación de la ToolBar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"Creación de la flecha hacia atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_form_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Al clikar vuelve hacia detrás");
                fpresenter.ReturnToList();
            }
        });
        character=new CharacterEntity();
        nameET=findViewById(R.id.name_form);
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
        dateformET=findViewById(R.id.date_textInputEdit);
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
        constellationformET=findViewById(R.id.constellation_textinputedit);
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
        hpformET=findViewById(R.id.hp_textinputEditText);
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

        atkformET=findViewById(R.id.atk_textInputEditText);
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

        defET=findViewById(R.id.def_textInputEditText);
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

        Button save=findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdelete = new AlertDialog.Builder(Form_activity.this);
                // alertdelete.setTitle(MyApplication.getContext().getResources().getString(R.string.button_delete));
                alertdelete.setMessage(MyApplication.getContext().getResources().getString(R.string.title_alert_save));

                alertdelete.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.title_alert_save_acept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,"Yes button clicked");
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.title_alert_save_acept_done), Toast.LENGTH_LONG);

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
        });

        Button clear_form=findViewById(R.id.clear_button);
        clear_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        imagedate=findViewById(R.id.imagedate);
        imagedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha seleccionada por defecto
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir la acción al pulsar OK en el calendario
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Asignar la fecha a un campo de texto
                        dateformET.setText(String.valueOf(year) + "/" + String.valueOf(month+1) + "/" + String.valueOf(day));
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });

        Log.d(TAG,"Da valores al spiner de armas");
        List<String> list_weapons=new ArrayList<>();
        Spinner spinner_weapons = (Spinner) findViewById(R.id.spinner_arm);
        list_weapons.add(getResources().getString(R.string.spinner_title));
        list_weapons.add(getResources().getString(R.string.spinner_catalyst));
        list_weapons.add(getResources().getString(R.string.spinner_claymore));
        list_weapons.add(getResources().getString(R.string.spinner_spear));
        list_weapons.add(getResources().getString(R.string.spinner_sword));
        list_weapons.add(getResources().getString(R.string.spinner_bow));
        adapter_weapons=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_weapons);
        adapter_weapons.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_weapons.setAdapter(adapter_weapons);
        image_weapons=findViewById(R.id.image_weapons);
        image_weapons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        Log.d(TAG,"Da valores al spiner de elementos");
        Spinner spinner_element = (Spinner) findViewById(R.id.spinner_element);
        List<String> list_elements=new ArrayList<>();
        list_elements.add(getResources().getString(R.string.spinner_elements));
        list_elements.add(getResources().getString(R.string.spinner_ele1));
        list_elements.add(getResources().getString(R.string.spinner_ele2));
        list_elements.add(getResources().getString(R.string.spinner_ele3));
        list_elements.add(getResources().getString(R.string.spinner_ele4));
        list_elements.add(getResources().getString(R.string.spinner_ele5));
        list_elements.add(getResources().getString(R.string.spinner_ele6));
        adapter_elements=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_elements);
        adapter_elements.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_element.setAdapter(adapter_elements);
        image_elements=findViewById(R.id.image_elements);
        image_elements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        Log.d(TAG,"Da valores al spiner de tier");
        Spinner spinner_tier = (Spinner) findViewById(R.id.spinner_tier);
        List<String> list_tier=new ArrayList<>();
        list_tier.add(getResources().getString(R.string.spinner_tier_title));
        list_tier.add(getResources().getString(R.string.spinner_tiers));
        list_tier.add(getResources().getString(R.string.spinner_tiera));
        list_tier.add(getResources().getString(R.string.spinner_tierb));
        list_tier.add(getResources().getString(R.string.spinner_tierc));
        list_tier.add(getResources().getString(R.string.spinner_tierd));
        adapter_tier=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_tier);
        adapter_tier.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_tier.setAdapter(adapter_tier);
        imagetier=findViewById(R.id.imagetier);
        imagetier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });

        Log.d(TAG,"Da valores al spiner de rol");
        Spinner spinner_rol = (Spinner) findViewById(R.id.spinner_rol);
        List<String> list_rol=new ArrayList<>();
        list_rol.add(getResources().getString(R.string.spinner_rol_title));
        list_rol.add(getResources().getString(R.string.spinner_rol1));
        list_rol.add(getResources().getString(R.string.spinner_rol2));
        list_rol.add(getResources().getString(R.string.spinner_rol3));
        list_rol.add(getResources().getString(R.string.spinner_rol4));
        adapter_rol=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list_rol);
        adapter_rol.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_rol.setAdapter(adapter_rol);
        imagerol=findViewById(R.id.imagerol);
        imagerol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_delete){

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
}