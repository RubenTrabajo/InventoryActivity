package com.example.inventoryactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.net.Uri;

public class InventarioAdapter extends FirestoreRecyclerAdapter<InventoryModel, InventarioAdapter.InventarioModelViewHolder> {

    Context context;
    public InventarioAdapter(@NonNull FirestoreRecyclerOptions<InventoryModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull InventarioModelViewHolder holder, int position, @NonNull InventoryModel model) {

        holder.tvNombre.setText(model.getNombre());
        holder.tvCategoria.setText(model.getCategoria());
        holder.tvValorCompra.setText("Valor compra: "+model.getPrecioCompra());
        holder.tvPrecioVenta.setText("Precio venta: "+calcularPrecioVenta(model.getPrecioCompra()));

        //AÑADIR AQUI LA IMAGEN
          /*  InventoryDetailsActivity.getImagenActualProducto().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri uri =task.getResult();
                    Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(holder.imgProducto);
                }
            }
        });*/
    }

    @NonNull
    @Override
    public InventarioModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_inventory_item, parent, false);
        return new InventarioModelViewHolder(view);
    }

    class InventarioModelViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProducto;
        private TextView tvNombre, tvCategoria,tvValorCompra, tvPrecioVenta;

        InventarioModelViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProducto = itemView.findViewById(R.id.producto_img_view);
            tvNombre = itemView.findViewById(R.id.tvNombreInventario);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaInventario);
            tvValorCompra = itemView.findViewById(R.id.tvValorCompraInventario);
            tvPrecioVenta = itemView.findViewById(R.id.tvPrecioVentaInventario);
        }
    }

    private double calcularPrecioVenta(double precioCompra) {
        // Calcula el precio de venta por unidad con un 20% de beneficio
        double porcentajeBeneficio = 0.20;
        double beneficio = precioCompra * porcentajeBeneficio;
        double precioVentaPorUnidad = precioCompra + beneficio;

        return precioVentaPorUnidad;
    }
}
