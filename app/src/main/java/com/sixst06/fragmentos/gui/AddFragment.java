package com.sixst06.fragmentos.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.databinding.FragmentAddBinding;
import com.sixst06.fragmentos.gui.components.NavigationHost;
import com.sixst06.fragmentos.model.Juego;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddFragment extends Fragment {


    private static final int RC_GALLERY = 21;

    private static final String MI_PHOTO = "my_photo";

    private static final String PATH_PROFILE = "profile";

    private FragmentAddBinding binding;

    private AppCompatImageView imgJuego;
    private EditText titulo;
    private EditText descripcion;
    private AppCompatRatingBar clasificacion;
    private Button guarda;
    private Button cancela;
    private ConstraintLayout constraintLayout;


    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference Game;

    private String currentPhotoPath;
    private Uri photoSelectedUri;
    private String my_photo = "";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        imgJuego = view.findViewById(R.id.imgJuego);
        titulo = view.findViewById(R.id.txtTitulo);
        descripcion = view.findViewById(R.id.txtDescripcion);
        guarda = view.findViewById(R.id.btnGuardar);
        cancela = view.findViewById(R.id.btnCancelar);
        clasificacion = view.findViewById(R.id.rbClasificacion);
        constraintLayout = view.findViewById(R.id.container);

        imgJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromGallery();
            }
        });

        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference().child("topJuegos");
                storageReference = FirebaseStorage.getInstance().getReference();
                Game = databaseReference.push();
                Juego juego = new Juego();
                juego.setIdJuego(Game.getKey());
                juego.setClasificacion((int) clasificacion.getRating());
                juego.setDescripcion(descripcion.getText().toString());
                juego.setImagen("image");
                juego.setTitulo(titulo.getText().toString());
                my_photo = Game.getKey();
                Game.setValue(juego, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        UploadImage();
                    }
                });


                Toast.makeText(getActivity(), "Se ha añadido el juego", Toast.LENGTH_SHORT).show();

                ((NavigationHost) getActivity()).navigateTo(new Administrar(), true);
            }

        });

        cancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new Administrar(), true);
            }
        });


        return view;
    }


    private void UploadImage() {
        if (photoSelectedUri == null) {
            Snackbar.make(constraintLayout, R.string.main_message_uri_null, BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }
        StorageReference profileReference = storageReference.child(PATH_PROFILE);
        StorageReference photoReference = profileReference.child(my_photo);
        StorageReference urlReference = photoReference.child("imagen");
        urlReference.putFile(photoSelectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        savePhotoUrl(task.getResult());
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(constraintLayout, R.string.main_message_upload_error, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }
    private void savePhotoUrl(Uri downloadUri) {
        Game.child("imagen").setValue(downloadUri.toString());
    }

    private void configPhotoProfile() {
        storageReference.child(PATH_PROFILE).child(MI_PHOTO).child("imagen").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        RequestOptions options = new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

                                .centerCrop();

                        Glide.with(AddFragment.this)
                                .load(uri)
                                .placeholder(R.drawable.ic_good)
                                .error(R.drawable.ic_nogood)
                                .into(binding.imgJuego);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Snackbar.make(constraintLayout, R.string.main_message_error_notfound, BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_GALLERY:
                    if (data != null) {
                        photoSelectedUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoSelectedUri);
                            imgJuego.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
    }
    private void fromGallery() {
        //Intent se utiliza para abrir activitys nuevas o elementos propios del telefono
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RC_GALLERY);

    }

}