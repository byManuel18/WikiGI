package com.manueh.wikigi.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements IListInterface.View {
    private IListInterface.Presenter presenter;
    private ArrayList<CharacterEntity> items = new ArrayList<>();;
    private  String TAG="Wikigi/Main_Activity";
    private Window window;
    private CharacterAdapter adaptador;
    private RecyclerView recyclerView;
    private TextView n_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences prefs =
                getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_toolbar_MainActiity);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        presenter = new ListPresenter(this);
        if(!prefs.getBoolean("fristTime",false)){
            presenter.SetItemsFristTime();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("fristTime", true);
            editor.commit();
        }
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
        n_items=findViewById(R.id.n_items);
        
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewListCharacter);

        // Crea el Adaptador con los datos de la lista anterior
         adaptador = new CharacterAdapter(items);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                //items.get(position).setId(Integer.toString(position));
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
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




    }

    CharacterEntity addagain=null;
    int position2=0;
    ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            position2=position;
            switch (direction){
                case ItemTouchHelper.LEFT:
                    addagain=items.get(position);
                    items.remove(position);
                    n_items.setText(Integer.toString(items.size())+getString(R.string.quantity_list_result));
                    adaptador.notifyItemRemoved(position);
                    presenter.CharacterDeleted();
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    };

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
    public void MessageCharacterDeleted() {
        Snackbar.make(recyclerView,R.string.deletedforlist,
                Snackbar.LENGTH_LONG).setAction(getString(R.string.undo)+addagain.getName()+"?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(position2,addagain);
                n_items.setText(Integer.toString(items.size())+getString(R.string.quantity_list_result));
                adaptador.notifyItemInserted(position2);
            }
        }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        items.clear();
        items.addAll(presenter.getAllItems());
        n_items.setText(Integer.toString(items.size())+getString(R.string.quantity_list_result));
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