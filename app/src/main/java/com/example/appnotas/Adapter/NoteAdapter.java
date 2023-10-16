package com.example.appnotas.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnotas.EditNoteActitivy;
import com.example.appnotas.R;
import com.example.appnotas.model.Note;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    List<Note> noteslist;
    private Context context;
    private DatabaseReference mDataBase;
    private FirebaseAuth auth;
    public  NoteAdapter(List<Note> noteslist, Context context)
    {
        this.context=context;
        this.noteslist=noteslist;
        mDataBase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        NoteHolder holder = new NoteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note data=noteslist.get(position);
        holder.title.setText(data.getContent());
        holder.time.setText(data.getTime());
        //edit Button
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , EditNoteActitivy.class);
                i.putExtra("uid", data.uid);
                i.putExtra("id",data.id);
                i.putExtra("content",data.content);
                i.putExtra("time",data.time);
                context.startActivity(i);
            }
        });
        //delete button
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("¿Estás seguro de borrar este evento?");
                builder.setMessage("Los eventos eliminados no se pueden recuperar");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDataBase.child("Notes").child(data.id).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Cancelar",Toast.LENGTH_SHORT).show();
                    }
                }); // end builder
                builder.show();
            }
        }); //end button
    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView title, time, edit, delete;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            edit = itemView.findViewById(R.id.bt_EditNote);
            delete = itemView.findViewById(R.id.bt_DeleteNote);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note n = noteslist.get(getAdapterPosition());
                    Intent i = new Intent(context , EditNoteActitivy.class);
                    i.putExtra("id",n.id);
                    i.putExtra("content",n.content);
                    i.putExtra("time",n.time);
                    context.startActivity(i);
                }
            });
        }
    }
}
