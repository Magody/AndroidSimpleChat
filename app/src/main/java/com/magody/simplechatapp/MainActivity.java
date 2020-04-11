package com.magody.simplechatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 223;
    private static final String TAG = "MainActivity";

    private CircleImageView circleImageViewFotoPerfil;
    private TextView textViewNombre;
    private RecyclerView recyclerViewMensajes;
    private EditText editTextMensaje;
    private Button buttonEnviar, buttonEnviarImagen;

    private RecyclerViewMensajesAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleImageViewFotoPerfil = findViewById(R.id.circleImageViewFotoPerfil);
        textViewNombre = findViewById(R.id.textViewNombre);
        recyclerViewMensajes = findViewById(R.id.recyclerViewMensajes);
        editTextMensaje = findViewById(R.id.editTextMensaje);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonEnviarImagen = findViewById(R.id.buttonEnviarImagen);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(
                "chat/danny-robot"
        );  //sala de chat donde se guarda los mensajes

        firebaseStorage = FirebaseStorage.getInstance();


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Cuando se agregue el dato a la bse de datos
                MensajeRecibir mensaje = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.agregarMensaje(mensaje);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        adapter = new RecyclerViewMensajesAdapter(this);
        recyclerViewMensajes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMensajes.setAdapter(adapter);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, ""+ServerValue.TIMESTAMP, Toast.LENGTH_LONG).show();

                databaseReference.push().setValue(new MensajeEnviar(
                        editTextMensaje.getText().toString(),
                        textViewNombre.getText().toString(),
                        null,
                        "1",
                        ServerValue.TIMESTAMP
                    )
                );
                /*adapter.agregarMensaje(new Mensaje(
                        editTextMensaje.getText().toString(),
                        textViewNombre.getText().toString(),
                        null,
                        "1",
                        "00:00:00"
                ));*/
                editTextMensaje.setText(null);
            }
        });



        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                recyclerViewMensajes.scrollToPosition(adapter.getItemCount()-1);

            }
        });

        buttonEnviarImagen.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Selecciona una foto"), REQUEST_CODE_GALLERY);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_GALLERY){
                if(data != null) {
                    Uri uri = data.getData();
                    storageReference = firebaseStorage.getReference("imagenes/danny-robot");

                    if (uri != null)
                        if (uri.getLastPathSegment() != null) {
                            final StorageReference fotoReferencia = storageReference.child(uri.getLastPathSegment());
                            UploadTask uploadTask = fotoReferencia.putFile(uri);
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task){
                                    if (!task.isSuccessful()) {
                                        return null;
                                    }

                                    // Continue with the task to get the download URL
                                    return fotoReferencia.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        if(downloadUri != null){
                                            String downloadURL = downloadUri.toString();
                                                Log.i(TAG, "onSuccess: Upload " +downloadURL);
                                                MensajeEnviar mensaje = new MensajeEnviar("Danny te ha enviado una foto",
                                                        textViewNombre.getText().toString(),
                                                        null,
                                                         downloadURL,
                                                        "2",
                                                        ServerValue.TIMESTAMP
                                                );
                                                databaseReference.push().setValue(mensaje);


                                        }else{
                                            Toast.makeText(MainActivity.this, "Error al obtener la foto enviada", Toast.LENGTH_LONG).show();
                                        }
                                    }



                                }
                            }
                            );
                        }else{
                            Toast.makeText(MainActivity.this, "Error al obtener la foto enviada", Toast.LENGTH_LONG).show();
                        }
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener la foto enviada", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            d(TAG, "onActivityResult: fallido" + data + ", resultcode " + resultCode + ", request code " + requestCode);
        }
    }
}
