package com.example.avito_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //т.к. при перевороте экрана state фрагмента сохраняется,
        // то добавляем фрагмент только при первом создании экрана, иначе фрагмент сам добавится

        if (savedInstanceState == null) {  //значит это первое создание экрана

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance()) //R.id.container - куда надуваем
                    .commit();
        }
    }
}