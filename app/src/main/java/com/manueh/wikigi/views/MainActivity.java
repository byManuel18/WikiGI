package com.manueh.wikigi.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.manueh.wikigi.R;
import com.manueh.wikigi.interfaces.IListInterface;
import com.manueh.wikigi.models.CharacterEntity;
import com.manueh.wikigi.presenters.ListPresenter;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IListInterface.View {
    private IListInterface.Presenter presenter;
    private ArrayList<CharacterEntity> items;
    private  String TAG="Wikigi/Main_Activity";
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_toolbar_MainActiity);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        presenter = new ListPresenter(this);
        Log.d(TAG, "Poner barra de abajo en negro");
        this.window = getWindow();
        this.window.setNavigationBarColor(getResources().getColor(R.color.black));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickAddNewPerson();
            }
        });
        items = new ArrayList<>();
        CharacterEntity uno = new CharacterEntity();

        uno.setName("Manuel");
        uno.setConstellation("Preuba");
        uno.setAtk(Integer.toString(2));
        uno.setDef(Integer.toString(3));
        uno.setHp(Integer.toString(4));
        uno.setTier("S");
        uno.setRol("Suop");
        uno.setElement("agua");
        uno.setRating(3.5);
        uno.setWeapon("Espada");

        items.add(uno);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewListCharacter);

        // Crea el Adaptador con los datos de la lista anterior
        CharacterAdapter adaptador = new CharacterAdapter(items);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                items.get(position).setId(Integer.toString(position));
                /*Toast.makeText(MainActivity.this, "Posición: " + String.valueOf(position) + " Constelacion: " + items.get(position).getConstellation() + " Name: " + items.get(position).getName(), Toast.LENGTH_SHORT)
                        .show();

                 */
                presenter.OnClickRecyclerViewItem(items.get(position).getId());
            }
        });

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_about){
            presenter.onClickAbout();
        }
        if(id==R.id.action_search){
            presenter.OnClickSearch();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startFormAcrivity() {
        Intent intent = new Intent(getApplicationContext(), Form_activity.class);
        startActivity(intent);
    }

    @Override
    public void startFormAcrivity(String id) {
        Intent intent = new Intent(getApplicationContext(), Form_activity.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }

    @Override
    public void startAboutActivity() {
        Intent intent=new Intent(getApplicationContext(),About_Activity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
        startActivity(intent);
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