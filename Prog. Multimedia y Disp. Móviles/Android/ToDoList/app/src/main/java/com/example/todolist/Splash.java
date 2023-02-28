package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Ocultamos el ActionBar
        getSupportActionBar().hide();

        // Creamos las variables que vamos a necesitar

        ImageView rueda_arriba = findViewById(R.id.rueda_arriba);
        ImageView rueda_debajo = findViewById(R.id.rueda_debajo);


        TextView carga = findViewById(R.id.carga);

        Animation animCarga = AnimationUtils.loadAnimation(this,R.anim.anim_carga);
        Animation animRueda = AnimationUtils.loadAnimation(this,R.anim.anim_rueda);
        Animation animRueda2 = AnimationUtils.loadAnimation(this,R.anim.anim_rueda2);


        // Iniciamos la animación animRueda asociándola con la rueda de arriba
        rueda_arriba.startAnimation(animRueda);

        // Iniciamos la animación animRueda2 asociándola con la rueda de abajo
        rueda_debajo.startAnimation(animRueda2);

        // Iniciamos la animación animCarga asociándolo con el texto de loading
        carga.startAnimation(animCarga);

        // Creamos el listener para la animación "animCarga"
        animCarga.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Cuando acabe la animación de "animCarga" cambiará de activity y pasará a la Login.
        // Además finalizará esta para que no se pueda volver a ella.

        Intent intent = new Intent(Splash.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}