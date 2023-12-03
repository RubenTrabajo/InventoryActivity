package com.example.inventoryactivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class InventoryFragment extends Fragment {

    private RecyclerView recyclerViewInventario;

    private Button BtnAddProducToInventario;

    private InventarioAdapter inventarioAdapator;
    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_inventory, container, false);
        recyclerViewInventario= view.findViewById(R.id.RecyclerViewInventario);
        BtnAddProducToInventario= view.findViewById(R.id.BtnAgregarProductoInventario);


        BtnAddProducToInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar a la actividad para añadir producto
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (inventarioAdapator!=null){
            inventarioAdapator.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (inventarioAdapator!=null){
            inventarioAdapator.stopListening();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (inventarioAdapator!=null){
            inventarioAdapator.notifyDataSetChanged();
        }

    }
}