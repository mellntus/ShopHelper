package com.example.warkopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.warkopproject.adapter.AdapterBarangRecyclerView;
import com.example.warkopproject.databinding.ActivityMainBinding;
import com.example.warkopproject.model.Barang;
import com.example.warkopproject.page.history.HistoryFragment;
import com.example.warkopproject.page.home.HomeFragment;
import com.example.warkopproject.page.listMenu.ListFragment;
import com.example.warkopproject.page.profile.ProfileFragment;
import com.example.warkopproject.page.stock.StockFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements AdapterBarangRecyclerView.FirebaseDataListener {

    private DatabaseReference databaseReference;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = LoginActivity.class.getSimpleName();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();

        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.v(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else{
                    Toast.makeText(MainActivity.this, "User Logout\n",
                            Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(MainActivity.this,
                            LoginActivity.class));
                    finish();
                }
            }
        };

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new HomeFragment());
        ft.commit();

        listener();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        databaseReference = FirebaseDatabase
                .getInstance("https://warkopproject-dfeab-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("stock_item");

    }

    private void listener(){
        /*
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_logout:
                        //LOGOUT HERE
                        MainActivity.this.finish();
                        break;

                }
                return true;
            }
        });*/

        binding.bnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch(item.getItemId()){
                    case R.id.m_home:
                        ft.replace(R.id.fragment_container, new HomeFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.m_menu:
                        ft.replace(R.id.fragment_container, new StockFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.m_order:
                        ft.replace(R.id.fragment_container, new ListFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.m_history:
                        ft.replace(R.id.fragment_container, new HistoryFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.m_profile:
                        ft.replace(R.id.fragment_container, new ProfileFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteData(Barang barang, int position) {
        databaseReference.child(barang.getKey()).removeValue().addOnSuccessListener
                (new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"success delete",
                                Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                });
    }
}