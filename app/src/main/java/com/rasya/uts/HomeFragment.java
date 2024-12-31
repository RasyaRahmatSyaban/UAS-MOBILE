package com.rasya.uts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<ModelTrending> modelTrendingList = new ArrayList<>();
    private TrendingAdapter trendingAdapter;
    private RecyclerView rvTrending;
    private Spinner spinnerOutlet; // Spinner untuk outlet
    private static final String ARG_USERNAME = "username";
    private String username;

    public static HomeFragment newInstance(String username){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout HomeFragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi komponen
        rvTrending = view.findViewById(R.id.rvTrending);
        spinnerOutlet = view.findViewById(R.id.spinnerOutlet);

        System.out.println("Username: " + username);

        TextView tvWelcome = view.findViewById(R.id.welcome);
        if (username != null) {
            tvWelcome.setText("Welcome, " + username);
        }

        ImageView ivCart = view.findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment cartFragment = new CartFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, cartFragment)
                        .addToBackStack(null) // Menambahkan ke back stack agar bisa kembali ke Home
                        .commit();
            }
        });


        setInitLayout();
        setTrending();
        setupSpinner();

        return view;
    }

    private void setInitLayout() {
        // Atur RecyclerView
        rvTrending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTrending.setHasFixedSize(true);
    }

    private void setTrending() {
        // Tambahkan data dummy ke RecyclerView
        modelTrendingList.add(new ModelTrending(R.drawable.complete_1, "Menu 1", "2.200 disukai"));
        modelTrendingList.add(new ModelTrending(R.drawable.complete_2, "Menu 2", "1.220 disukai"));
        modelTrendingList.add(new ModelTrending(R.drawable.complete_3, "Menu 3", "345 disukai"));
        modelTrendingList.add(new ModelTrending(R.drawable.complete_4, "Menu 4", "590 disukai"));

        // Set adapter untuk RecyclerView
        trendingAdapter = new TrendingAdapter(getActivity(), modelTrendingList);
        rvTrending.setAdapter(trendingAdapter);
    }

    private void setupSpinner() {
        // Data Spinner untuk Outlet
        String[] outlets = {"Juanda", "Diponegoro"};

        // Adapter Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, outlets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOutlet.setAdapter(adapter);
    }
}
