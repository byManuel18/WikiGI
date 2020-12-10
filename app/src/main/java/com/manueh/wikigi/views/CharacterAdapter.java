package com.manueh.wikigi.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manueh.wikigi.R;
import com.manueh.wikigi.models.CharacterEntity;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>
        implements View.OnClickListener {

    private ArrayList<CharacterEntity> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class CharacterViewHolder
            extends RecyclerView.ViewHolder {

        private TextView textView_name;
        private TextView textView_constelllation;
        private TextView textView_tier;
        private TextView textView_weapons;
        private TextView textView_element;
        private TextView textView_rol;
        private TextView textView_def;
        private TextView textView_atk;
        private TextView textView_hp;
        private RatingBar ratingBar_rate;
        private ImageView imageView_character;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
            textView_constelllation = (TextView) itemView.findViewById(R.id.textView_constelllation);
            textView_tier=(TextView) itemView.findViewById(R.id.textView_tier);
            textView_weapons=(TextView) itemView.findViewById(R.id.textView_weapons);
            textView_element=(TextView) itemView.findViewById(R.id.textView_element);
            textView_rol=(TextView) itemView.findViewById(R.id.textView_rol);
            textView_def=(TextView) itemView.findViewById(R.id.textView_def);
            textView_atk=(TextView) itemView.findViewById(R.id.textView_atk);
            textView_hp=(TextView) itemView.findViewById(R.id.textView_hp);
            imageView_character=(ImageView) itemView.findViewById(R.id.imageView_character);
            ratingBar_rate=(RatingBar) itemView.findViewById(R.id.ratingBar_rate);
        }

        public void CharacterBind(CharacterEntity item) {
            if(item.getImage()!=null){
                byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView_character.setImageBitmap(decodedByte);
                
            }
            textView_name.setText(item.getName());
            textView_constelllation.setText(item.getConstellation());
            textView_tier.setText(item.getTier());
            textView_weapons.setText(item.getWeapon());
            textView_element.setText(item.getElement());
            textView_rol.setText(item.getRol());
            textView_def.setText(Integer.toString(item.getDef()));
            textView_atk.setText(Integer.toString(item.getAtk()));
            textView_hp.setText(Integer.toString(item.getHp()));
            ratingBar_rate.setRating((float)item.getRating());
            ratingBar_rate.setEnabled(false);
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public CharacterAdapter(@NonNull ArrayList<CharacterEntity> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ropeperson, parent, false);
        row.setOnClickListener(this);

        CharacterViewHolder avh = new CharacterViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(CharacterViewHolder viewHolder, int position) {
        CharacterEntity item = items.get(position);
        viewHolder.CharacterBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

}
