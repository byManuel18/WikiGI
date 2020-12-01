package com.manueh.wikigi.views;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.manueh.wikigi.interfaces.ISearchPresenter;
import com.manueh.wikigi.presenters.SearchPresenter;

import java.util.Calendar;

public class Search_Activity extends AppCompatActivity implements ISearchPresenter.View {
    private ISearchPresenter.Presenter Spresenter;
    private String TAG="Search_Activity";
    private Button button_search;
    private Window window;
    private Spinner spinner_search;
    private ImageView imageViewname;
    private ImageView imageViewdate;
    private TextInputEditText name;
    private TextInputEditText cdate;
    private int Year, Month, Day ;
    private Context myContext;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        Spresenter=new SearchPresenter(this);
        this.window=getWindow();
        this.window.setNavigationBarColor(getResources().getColor(R.color.black));
        myContext=this;
        Log.d(TAG,"Obtener fecha actual");
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
                Spresenter.onClickReturn();
            }
        });
        imageViewdate=findViewById(R.id.imageView_createdate);
        imageViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha seleccionada por defecto
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir la acción al pulsar OK en el calendario
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Asignar la fecha a un campo de texto
                        cdate.setText(String.valueOf(year) + "/" + String.valueOf(month+1) + "/" + String.valueOf(day));
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });
        imageViewdate.setEnabled(false);
        name=findViewById(R.id.textinputeditext_name);
        name.setVisibility(View.INVISIBLE);
        cdate=findViewById(R.id.textinputEditText_cdate);
        cdate.setVisibility(View.INVISIBLE);
        cdate.setEnabled(false);
        button_search=findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cdate.getText().toString().length()>0||cdate.getText().toString().length()>0){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.quantity_list_result), Toast.LENGTH_SHORT);

                    toast1.show();
                    Spresenter.onClickClose();
                }else{
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.error_search), Toast.LENGTH_SHORT);

                    toast1.show();
                }

            }
        });

        spinner_search=findViewById(R.id.spinner_search);
        String[] str={getResources().getString(R.string.butto_search),getResources().getString(R.string.search_byName),getResources().getString(R.string.search_byDate)};
        ArrayAdapter<String> adpater=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, str);
        adpater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_search.setAdapter(adpater);
        spinner_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    imageViewdate.setEnabled(false);
                    name.setVisibility(View.INVISIBLE);
                    cdate.setVisibility(View.INVISIBLE);
                    name.setText(null);
                    cdate.setText(null);
                }
                if(position==1){
                    imageViewdate.setEnabled(false);
                    name.setVisibility(View.VISIBLE);
                    cdate.setText(null);
                    cdate.setVisibility(View.INVISIBLE);

                }
                if(position==2){
                    imageViewdate.setEnabled(true);
                    name.setVisibility(View.INVISIBLE);
                    name.setText(null);
                    cdate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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