package com.magody.simplechatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewMensajesAdapter extends RecyclerView.Adapter<RecyclerViewMensajesAdapter.HolderMensaje> {

    List<MensajeRecibir> listaMensajes = new ArrayList<>();
    Context mContext;

    public RecyclerViewMensajesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void agregarMensaje(MensajeRecibir mensaje){
        listaMensajes.add(mensaje);
        notifyItemInserted(listaMensajes.size()-1);
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mensaje,parent,false);
        return new HolderMensaje(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {

        final MensajeRecibir mensaje = listaMensajes.get(position);
        holder.nombre.setText(mensaje.getNombre());
        holder.mensaje.setText(mensaje.getMensaje());

        if(mensaje.getTipo().equals("2")){

            holder.imageViewFotoMensaje.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(mensaje.getUrl_foto())
                    .into(holder.imageViewFotoMensaje);

        }else{
            holder.imageViewFotoMensaje.setVisibility(View.INVISIBLE);
        }

        long codigo_hora = mensaje.getHora();
        Date fecha = new Date(codigo_hora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd ");
        holder.hora.setText(sdf.format(fecha));

    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    public class HolderMensaje extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView mensaje;
        private TextView hora;
        private ImageView imageViewFotoMensaje;
        private CircleImageView fotoPerfilMensaje;

        public HolderMensaje(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewLayoutNombre);
            mensaje = itemView.findViewById(R.id.textViewLayoutMensaje);
            hora = itemView.findViewById(R.id.textViewLayoutHora);
            fotoPerfilMensaje = itemView.findViewById(R.id.circleImageViewLayoutFotoPerfil);
            imageViewFotoMensaje = itemView.findViewById(R.id.imageViewFotoMensaje);

        }
    }


}
