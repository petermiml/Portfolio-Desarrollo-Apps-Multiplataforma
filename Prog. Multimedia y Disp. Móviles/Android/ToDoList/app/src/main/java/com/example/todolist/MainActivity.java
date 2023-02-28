package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Declaramos el objeto autenticador
    FirebaseAuth mAuth;

    // Declaramos una instancia de la base de datos
    FirebaseFirestore db;

    // Declaramos las variables/objetos que vamos a necesitar (las inicializamos en el onCreate)
    String emailUser;
    ListView listViewTareas;
    List<String> listaTareas = new ArrayList<>();
    List<String> listaIdTareas = new ArrayList<>();
    ArrayAdapter<String> mAdapterTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos las variables
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUser = mAuth.getCurrentUser().getEmail();
        listViewTareas = findViewById(R.id.listaTareas);

        // Se ejecuta el método actualizarUI()
        actualizarUI();
    }

    // Función que actualiza la interfaz con las tareas del usuario logado.
    private void actualizarUI() {
        db.collection("Tareas")
                .whereEqualTo("emailUsuario", emailUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }

                        listaTareas.clear();
                        listaIdTareas.clear();

                        for (QueryDocumentSnapshot doc : value) {
                            listaIdTareas.add(doc.getId());
                            listaTareas.add(doc.getString("nombreTarea"));
                        }

                        if(listaTareas.size() == 0){
                            listViewTareas.setAdapter(null);
                        }else{
                            mAdapterTareas = new ArrayAdapter<>(MainActivity.this, R.layout.item_tarea, R.id.textoItem,listaTareas);
                            listViewTareas.setAdapter(mAdapterTareas);
                        }
                    }
                });
    }

    // Función para asociar el menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Función que se ejecutará si pulsamos algún item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            // Se ejecutará lo siguiente en el caso de que pulsemos en el simbolo "mas"
            case R.id.mas:
                final EditText taskEditText = new EditText(this); // Creamos una caja de texto

                // Creamos cuadro de diálogo para añadir tarea.
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva tarea")
                        .setMessage("¿Qué quieres hacer a continuación?")
                        .setView(taskEditText)  // Metemos la caja de texto creada anteriormente
                        .setPositiveButton("Añadir tarea", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Añadir tarea a la base de datos.

                                String miTarea = taskEditText.getText().toString(); // Obtenemos la tarea

                                Map<String, Object> tarea = new HashMap<>();    // Creamos el mapa
                                tarea.put("nombreTarea", miTarea);
                                tarea.put("emailUsuario",emailUser);

                                // Lo siguiente seria como decir "dentro de la base de datos, en la tabla "tareas" añádeme el registro "tarea"
                                db.collection("Tareas").add(tarea); // Creamos la coleccion/tabla "Tareas" y añadimos el mapa de objetos
                                                                                // La primera vez, si no existe, se crea.
                                Toast.makeText(MainActivity.this,"Tarea añadida",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
                return true;

            // En el caso de que pulsemos en el simbolo de logout
            case R.id.logout:
                // Cierre de sesión de Firebase
                mAuth.signOut();
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Función que se ejecuta al pulsar en el botón "DONE"
    public void borrarTarea(View view){

        View parent = (View) view.getParent();
        TextView tareaTextView = parent.findViewById(R.id.textoItem);
        String tarea = tareaTextView.getText().toString();


        int posicion = listaTareas.indexOf(tarea);

        // Eliminamos la tarea de la BBDD firestore y por consiguiente, al refrescarse la página con
        // el método que creamos para ello, se elimina de nuestra app.
        db.collection("Tareas").document(listaIdTareas.get(posicion)).delete();

        // Iniciamos el archivo de sonido done.mp3
        MediaPlayer doneSound = MediaPlayer.create(this,R.raw.done);
        doneSound.start();

        // Toast personalizado
        Toast toast2 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Tarea realizada");
        toast2.setDuration(Toast.LENGTH_SHORT);
        toast2.setView(layout);
        toast2.show();
    }


    // Función que se ejecuta al pulsar en el botón "EDIT"
    public void editTarea(View view){
        // Obtenemos el texto del TextView
        View parent = (View) view.getParent();
        TextView tareaTextView = parent.findViewById(R.id.textoItem);
        String tarea = tareaTextView.getText().toString();  // Texto del textview

        int posicion = listaTareas.indexOf(tarea);  // Posicion del textview en la pantalla

        final EditText taskEditText = new EditText(this); // Creamos una caja de texto
        taskEditText.setText(tarea);

        // Creamos cuadro de diálogo
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Editar tarea")
                .setMessage("Realice la modificación que desee")
                .setView(taskEditText)  // Metemos la caja de texto creada anteriormente
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Añadir tarea a la base de datos.

                        String miTarea = taskEditText.getText().toString(); // Obtenemos el texto escrito


                        Map<String, Object> tareaMap = new HashMap<>();    // Creamos el mapa
                        tareaMap.clear();
                        tareaMap.put("nombreTarea", miTarea);
                        tareaMap.put("emailUsuario",emailUser);


                        // Actualizamos el documento de firestore
                        db.collection("Tareas").document(listaIdTareas.get(posicion)).update(tareaMap);

                        // Indicamos al usuario que la tarea ha sido actualizada.
                        Toast.makeText(MainActivity.this, "Tarea actualizada", Toast.LENGTH_SHORT).show();


                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();

    }
}