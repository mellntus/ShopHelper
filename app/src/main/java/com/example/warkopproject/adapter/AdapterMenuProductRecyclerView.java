package com.example.warkopproject.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warkopproject.MainActivity;
import com.example.warkopproject.R;
import com.example.warkopproject.model.MenuProduct;
import com.example.warkopproject.page.menu.CreateMenuActivity;

import java.util.ArrayList;

public class AdapterMenuProductRecyclerView extends
        RecyclerView.Adapter<AdapterMenuProductRecyclerView.ViewHolder> {

    private ArrayList<MenuProduct> arrayMenu;
    private Context context;
    FirebaseDataListener listener;

    public AdapterMenuProductRecyclerView(ArrayList<MenuProduct> menuProducts, Context ctx){
        arrayMenu = menuProducts;
        context = ctx;
        listener = (MainActivity)ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaMenu, tvKategoriMenu,
                tvPriceMenu, btnEditMenu, btnAddToCart;

        ViewHolder(View v){
            super(v);
            tvNamaMenu = (TextView) v.findViewById(R.id.tv_menuNama);
            tvKategoriMenu = (TextView) v.findViewById(R.id.tv_menuCategory);
            tvPriceMenu = (TextView) v.findViewById(R.id.tv_menuPrice);
            btnEditMenu = (TextView) v.findViewById(R.id.editMenuButton);
            btnAddToCart = (TextView) v.findViewById(R.id.addToCartMenu);
        }
    }

    public interface FirebaseDataListener{
        void onDeleteData(MenuProduct menuProduct, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_barang, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuProduct menuProduct = arrayMenu.get(position);
        holder.btnEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.menu_dialog_view);
                dialog.show();

                Button menuEditButton = (Button) dialog.findViewById(R.id.btn_edit_menu);
                Button menuDeleteButton = (Button) dialog.findViewById(R.id.btn_delete_menu);

                menuEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        context.startActivity(new Intent(context, CreateMenuActivity.class)
                        .putExtra("data", arrayMenu.get(position)));
                    }
                });

                menuDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        listener.onDeleteData(arrayMenu.get(position), position);
                    }
                });

            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.tvNamaMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        holder.tvNamaMenu.setText(menuProduct.getNamaMenu());
        holder.tvKategoriMenu.setText(menuProduct.getKategoriMenu());
        holder.tvPriceMenu.setText(menuProduct.getHargaMenu().toString());
    }

    @Override
    public int getItemCount() {
        return arrayMenu.size();
    }

}
