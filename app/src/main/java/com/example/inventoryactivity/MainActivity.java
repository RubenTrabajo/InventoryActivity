package com.example.inventoryactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    InventoryFragment inventoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inventoryFragment= new InventoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, inventoryFragment).commit();
    }
}