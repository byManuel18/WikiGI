package com.manueh.wikigi.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.manueh.wikigi.R;
import com.manueh.wikigi.enums.Fields_to_validate;
import com.manueh.wikigi.interfaces.ISearchPresenter;
import com.manueh.wikigi.models.CharacterModle;
import com.manueh.wikigi.presenters.FormPresenter;
import com.manueh.wikigi.presenters.SearchPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Search_Activity extends AppCompatActivity implements ISearchPresenter.View {
    private ISearchPresenter.Presenter Spresenter;
    private String TAG="Search_Activity";
    private Button button_search;
    private Window window;
    private Spinner spinner_search;
    private Spinner spinner_tier_search;
    private ImageView imageViewname;
    private ImageView imageViewdate;
    private TextInputEditText name;
    private TextInputEditText cdate;
    private int Year, Month, Day ;
    private Context myContext;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int code=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        Spresenter=new SearchPresenter(this);
        Log.d(TAG,"Poner barra de abajo en negro");
        this.window=getWindow();
        this.window.setNavigationBarColor(getResources().getColor(R.color.black));
        myContext=this;
        Log.d(TAG,"Get actual date");
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"Creación de la flecha hacia atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_toolbar_search));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Al clikar vuelve hacia detrás");
                setResult(RESULT_CANCELED);
                Spresenter.onClickReturn();
            }
        });


        imageViewdate=findViewById(R.id.imageView_createdate);
        Log.d(TAG,"Crear calendario al clickar en la imagen");
        imageViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        cdate.setText(day_+ "/" + month_+ "/" + year_);
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });
        spinner_tier_search=findViewById(R.id.spinner_tier_search);

        spinner_tier_search.setEnabled(false);
        imageViewdate.setEnabled(false);
        name=findViewById(R.id.textinputeditext_name);
        name.setVisibility(View.INVISIBLE);
        cdate=findViewById(R.id.textinputEditText_cdate);
        cdate.setVisibility(View.INVISIBLE);
        cdate.setEnabled(false);
        button_search=findViewById(R.id.button_search);
        Log.d(TAG,"Acción buscar");
        CharacterModle bd=new CharacterModle();
        List<String> lista=new ArrayList<>();
        lista.add(MyApplication.getContext().getString(R.string.spinner_tier_title));
        lista.addAll(bd.getSpinnersValues(Fields_to_validate.TIER_FORM));
        ArrayAdapter<String> adpater2=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lista);
        adpater2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_tier_search.setAdapter(adpater2);
        spinner_tier_search.setSelection(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));

        spinner_search=findViewById(R.id.spinner_search);
        Log.d(TAG,"Añadir los metodos de busqueda al spinner");

        String[] str={getResources().getString(R.string.butto_search),getResources().getString(R.string.search_byName),getResources().getString(R.string.search_byDate),getResources().getString(R.string.search_by_tier),getResources().getString(R.string.search_by_all)};
        ArrayAdapter<String> adpater=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, str);
        adpater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_search.setAdapter(adpater);
        Log.d(TAG,"Listener que según la opción del spinner oculta o muestra el método a buscar");
        spinner_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    imageViewdate.setEnabled(false);
                    name.setVisibility(View.INVISIBLE);
                    cdate.setVisibility(View.INVISIBLE);
                    name.setText(null);
                    cdate.setText(null);
                    spinner_tier_search.setEnabled(false);
                    spinner_tier_search.setSelection(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));
                    code=0;
                }
                if(position==1){
                    imageViewdate.setEnabled(false);
                    name.setVisibility(View.VISIBLE);
                    cdate.setText(null);
                    cdate.setVisibility(View.INVISIBLE);
                    spinner_tier_search.setEnabled(false);
                    spinner_tier_search.setSelection(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));
                    code=1;
                }
                if(position==2){
                    imageViewdate.setEnabled(true);
                    name.setVisibility(View.INVISIBLE);
                    name.setText(null);
                    cdate.setVisibility(View.VISIBLE);
                    spinner_tier_search.setEnabled(false);
                    spinner_tier_search.setSelection(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));
                    code=2;
                }
                if(position==3){
                    imageViewdate.setEnabled(true);
                    name.setVisibility(View.INVISIBLE);
                    name.setText(null);
                    cdate.setText(null);
                    cdate.setVisibility(View.INVISIBLE);
                    spinner_tier_search.setEnabled(true);
                    code=3;

                }
                if(position==4){
                    spinner_tier_search.setSelection(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)));
                    spinner_tier_search.setEnabled(true);
                    name.setText(null);
                    cdate.setText(null);
                    cdate.setVisibility(View.VISIBLE);
                    imageViewdate.setEnabled(true);
                    name.setVisibility(View.VISIBLE);
                    code=4;

                }

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                /*if(cdate.getText().toString().length()>0||name.getText().toString().length()>0){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.quantity_list_result), Toast.LENGTH_SHORT);

                    toast1.show();
                    Spresenter.onClickClose();
                }else{
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);

                    toast1.show();
                }*/
                if(code==0){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);

                    toast1.show();
                }else if(code==1){
                    if(name!=null&&name.getText().toString().length()>0){
                        i.putExtra("NAME",name.getText().toString().toUpperCase());
                        setResult(RESULT_OK, i);
                    }else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }else if(code==2){
                    if(cdate!=null&&cdate.getText().toString().length()>0){
                        i.putExtra("DATE",cdate.getText().toString());
                        setResult(RESULT_OK, i);
                    }else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }else if(code==3){
                    if(spinner_tier_search!=null&&!spinner_tier_search.getSelectedItem().toString().equals(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)))){
                        i.putExtra("TIER",spinner_tier_search.getSelectedItem().toString());
                        setResult(RESULT_OK, i);
                    }else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                }else if(code==4){

                    if(name!=null&&name.getText().toString().length()>0&&cdate!=null&&cdate.getText().toString().length()>0&&spinner_tier_search!=null&&!spinner_tier_search.getSelectedItem().toString()
                            .equals(adpater2.getPosition(MyApplication.getContext().getString(R.string.spinner_tier_title)))){
                        i.putExtra("TIER",spinner_tier_search.getSelectedItem().toString());
                        i.putExtra("DATE",cdate.getText().toString());
                        i.putExtra("NAME",name.getText().toString().toUpperCase());
                        setResult(RESULT_OK, i);
                    }else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);
                        toast1.show();
                    }

                }
                if(code>0){
                    finish();
                }


            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void ReturnList() {
        onBackPressed();
    }

    @Override
    public void CloseSearch() {
        finish();
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

}