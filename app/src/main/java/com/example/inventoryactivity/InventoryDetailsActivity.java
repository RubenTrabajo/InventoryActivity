package com.example.inventoryactivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.FirebaseStorageKtxRegistrar;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class InventoryDetailsActivity extends AppCompatActivity {

    private boolean isModified=false;

    // Variables para los elementos de la interfaz
    private ImageView imageViewProducto;
    private EditText editTextNombreProducto, editTextPrecioCompraProducto, editTextCantidadCompradaProducto, editTextPrecioActualMercadoProducto;
    private Spinner spinnerCategoriaProducto,spinnerActualizacionPreciosProducto,spinnerMonedaProducto;
    private LineChart graficoProducto;
    private Button btnGuardar, btnBorar;

    private ActivityResultLauncher<Intent> imagePickLauncher;

    private Uri imagenSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        
        // Enlazar variables con elementos del diseño
        imageViewProducto = findViewById(R.id.imageViewProducto);
        editTextNombreProducto = findViewById(R.id.editTextNombreProducto);
        spinnerCategoriaProducto = findViewById(R.id.spinnerCategoriaProducto);
        spinnerMonedaProducto = findViewById(R.id.spinnerMonedaProducto);
        editTextPrecioCompraProducto = findViewById(R.id.editTextPrecioCompraProducto);
        editTextCantidadCompradaProducto = findViewById(R.id.editTextCantidadCompradaProducto);
        editTextPrecioActualMercadoProducto = findViewById(R.id.editTextPrecioActualMercadoProducto);
        spinnerActualizacionPreciosProducto = findViewById(R.id.spinnerActualizacionPrecios);
        graficoProducto = findViewById(R.id.GraficoProducto);
        btnGuardar = findViewById(R.id.btn_Guardar_Producto);
        btnBorar = findViewById(R.id.btn_Borrar_Producto);

        //Aplicar cuando la tarea no sea nueva
        /*getImagenActualProducto().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri uri =task.getResult();
                    Glide.with(InventoryDetailsActivity.this).load(imagenSeleccionada).apply(RequestOptions.circleCropTransform()).into(imageViewProducto);
                }
            }
        });*/


        ArrayAdapter<CharSequence> adapterMoneda = ArrayAdapter.createFromResource(this, R.array.monedas,android.R.layout.simple_spinner_item);
        adapterMoneda.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerMonedaProducto.setAdapter(adapterMoneda);

        imagePickLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if (result.getResultCode()==RESULT_OK){
                Intent data= result.getData();
                if (data!=null&& data.getData()!=null){
                    imagenSeleccionada=data.getData();
                    Glide.with(this).load(imagenSeleccionada).apply(RequestOptions.circleCropTransform()).into(imageViewProducto);
                }
            }
        });

        imageViewProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(InventoryDetailsActivity.this).cropSquare().compress(256).maxResultSize(50,50).createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        imagePickLauncher.launch(intent);
                        return null;
                    }
                });
            }
        });


        cargarDatosGraficoEjemplo();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {
        String NombreProducto = editTextNombreProducto.getText().toString();
        if (NombreProducto==null || NombreProducto.isEmpty()){
            editTextNombreProducto.setError("El nombre de producto es obligatorio");
            return;
        }

        Double precioCompra= toDouble(editTextPrecioCompraProducto) ;
        int cantidadCompra = Integer.parseInt(editTextCantidadCompradaProducto.getText().toString()) ;
        Double precioActMercado = toDouble(editTextPrecioActualMercadoProducto);


        InventoryModel model =new InventoryModel();
        model.setNombre(NombreProducto);
        model.setCategoria(spinnerCategoriaProducto.getSelectedItem().toString());
        model.setPrecioCompra(precioCompra);
        model.setCantidadComprada(cantidadCompra);
        model.setPrecioActualMercado(precioActMercado);
        model.setActualizacionPrecios(spinnerActualizacionPreciosProducto.getSelectedItem().toString());
        model.setMoneda(spinnerMonedaProducto.getSelectedItem().toString());

        DocumentReference documentReference;
        documentReference = FirebaseFirestore.getInstance().collection("productos").document().collection("mis_productos").document();
        documentReference.set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Mostrar Toast con guardado correcto
                    finish();
                }
            }
        });

      /*  if (imagenSeleccionada!=null){
            getImagenActualProducto().putFile(imagenSeleccionada);
        }*/

    }


/*    public static StorageReference getImagenActualProducto(){
        return FirebaseStorage.getInstance().getReference().child("img_productos").child();*//*CAMBIAR POR USER ID ACTUAL*//*
    }*/

    public static double toDouble(EditText editText) {
        try {
            return Double.valueOf(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void cargarDatosGraficoEjemplo() {
        List<String> Xvalues;
        Description description = new Description();
        description.setText("VALOR EJE X (EJEMPLO: PRECIO DE 0 a 100 )");
        description.setPosition(150f,15f);
        graficoProducto.setDescription(description);
        graficoProducto.getAxisRight().setDrawLabels(false);

        Xvalues = Arrays.asList("FECHA DE COMPRA","FECHA ACTUAL");

        XAxis axis = graficoProducto.getXAxis();
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(Xvalues));
        axis.setLabelCount(4);
        axis.setGranularity(1f);


        YAxis yAxis = graficoProducto.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setZeroLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,10f));
        entries.add(new Entry(1,10f));
        entries.add(new Entry(2,15f));
        entries.add(new Entry(3,45f));


        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0,5f));
        entries2.add(new Entry(1,15f));
        entries2.add(new Entry(2,25f));
        entries2.add(new Entry(3,30f));


        LineDataSet lineDataSet = new LineDataSet(entries,"PRECIO DE COMPRA");
        lineDataSet.setColor(Color.BLUE);

        LineDataSet lineDataSet1 =new LineDataSet(entries2,"PRECIO ACTUAL MERCADO");
        lineDataSet1.setColor(Color.RED);

        LineData lineData = new LineData(lineDataSet,lineDataSet1);
        graficoProducto.setData(lineData);
        graficoProducto.invalidate();



    }
}