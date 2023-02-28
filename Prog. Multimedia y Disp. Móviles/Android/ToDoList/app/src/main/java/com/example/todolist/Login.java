package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    // Declaramos las variables que vamos a necesitar:
    Button botonLogin;
    TextView botonRegistro;
    EditText emailText, passText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializamos las variables emailText y passText con las views correspondientes
        emailText = findViewById(R.id.cajaCorreo);
        passText = findViewById(R.id.pass);
        botonRegistro = findViewById(R.id.enlaceCrearCuenta);
        botonLogin = findViewById(R.id.botonLogin);

        // Ocultamos la barra de arriba
        getSupportActionBar().hide();

        // Ponemos al botón a la escucha de que se haga clic en él:
        botonLogin.setOnClickListener(view -> {

            //LOGIN EN FIREBASE

            String email = emailText.getText().toString();
            String password = passText.getText().toString();

            // Verificamos que los campos email y contraseña no estén vacíos.
            if(email.isEmpty() && password.isEmpty()) {
                emailText.setError("El campo no puede estar vacío.");
                passText.setError("El campo no puede estar vacío.");
            }else if (email.isEmpty()) {
                emailText.setError("El campo no puede estar vacío.");
            } else if (password.isEmpty()) {
                passText.setError("El campo no puede estar vacío.");
            }else{
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Creamos un objeto Intent que pase de esta activity a la MainActivity
                                    Intent intent = new Intent(Login.this, MainActivity.class);

                                    // Ejecutamos el intent
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Usuario o contraseña incorrectos.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // REGISTRO EN FIREBASE
        botonRegistro.setOnClickListener(view -> {
            String email = emailText.getText().toString();
            String password = passText.getText().toString();

            // Verificamos que los campos email y contraseña no estén vacíos.
            if(email.isEmpty() && password.isEmpty()) {
                emailText.setError("El campo no puede estar vacío.");
                passText.setError("El campo no puede estar vacío.");
            }else if(email.isEmpty()){
                emailText.setError("El campo no puede estar vacío");
            }else if(password.isEmpty()){
                passText.setError("El campo no puede estar vacío.");
            }else if(password.length()<6){
                passText.setError("La contraseña debe contener 6 o más caracteres.");
            }else if(!comprobarCorreo(email)){
                // Verificamos que el correo esté correctamente escrito.
                emailText.setError("Nombre de correro inválido. Por favor, revisa que no contenga" +
                        " más de un @, que tenga menos de un punto, o que no contenga caracteres " +
                        " como espacios en blanco o \"ñ\"");

            // Si no se da ninguno de los casos anteriores, se procede al registro del usuario
            }else {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Toast personalizado
                                    Toast toast2 = new Toast(getApplicationContext());
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.lytLayout));
                                    TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                    txtMsg.setText("Usuario registrado con éxito");
                                    toast2.setDuration(Toast.LENGTH_SHORT);
                                    toast2.setView(layout);
                                    toast2.show();

                                    // Creamos un objeto Intent que pase de esta activity a la MainActivity
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    // Ejecutamos el intent
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "No ha podido crearse la cuenta.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

                });

    }

    // Método que realiza unas comprobaciones para ver si el correo está correctamente escrito.
    // Devuelve true si es correcto y false si no lo es.
    public boolean comprobarCorreo(String correo){

        // Podríamos haber controlado más casos, pero creo que con esto es suficiente.

        int nArrobas = 0;
        int nPuntos = 0;
        int nEspacios = 0;
        int nEnhes = 0;

        for(int i=0; i<correo.length(); i++){

            switch (correo.charAt(i)){
                case '@':
                    nArrobas++;
                    break;
                case '.':
                    nPuntos++;
                    break;
                case ' ':
                    nEspacios++;
                    break;
                case 'ñ':
                case 'Ñ':
                    nEnhes++;
                    break;
                default:
                    //
            }

        }
        // No puede haber correos que contengan más que el arroba permitido. Tampoco puede haber un
        // correo que no use como mínimo el "." del dominio y no hay ningún correo que acepte espacios
        // ni "ñ".
        if(nArrobas > 1 || nPuntos < 1 || nEspacios > 0 || nEnhes > 0){
            return false;
        }else{
            return true;
        }
    }
}