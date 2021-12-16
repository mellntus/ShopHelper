package com.example.warkopproject.page.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warkopproject.LoginActivity;
import com.example.warkopproject.MainActivity;
import com.example.warkopproject.R;
import com.example.warkopproject.databinding.ActivityMainBinding;
import com.example.warkopproject.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth fAuth;
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                getActivity().finish();
            }
        });
        return view;
    }
}