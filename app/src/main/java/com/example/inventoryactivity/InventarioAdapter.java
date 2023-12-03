package com.example.inventoryactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoryactivity.InventoryModel;
import com.example.inventoryactivity.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class InventarioAdapter extends FirestoreRecyclerAdapter<InventoryModel, InventarioAdapter.InventarioModelViewHolder> {

    Context context;
    public InventarioAdapter(@NonNull FirestoreRecyclerOptions<InventoryModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull InventarioModelViewHolder holder, int position, @NonNull InventoryModel model) {

    }

    @NonNull
    @Override
    public InventarioModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_inventory_item, parent, false);
        return new InventarioModelViewHolder(view);
    }

    class InventarioModelViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvCategoria,tvValorCompra, tvPrecioVenta;

        InventarioModelViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombreInventario);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaInventario);
            tvValorCompra = itemView.findViewById(R.id.tvValorCompraInventario);
            tvPrecioVenta = itemView.findViewById(R.id.tvPrecioVentaInventario);
        }
    }
}
