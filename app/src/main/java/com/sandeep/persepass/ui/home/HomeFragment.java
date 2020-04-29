package com.sandeep.persepass.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.sandeep.persepass.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
//    private MyCustomAdapter adapter;
    private ArrayAdapter adapter;
    private Button savePassButton;
    private TextView key;
    private TextView pass;

    ArrayList<HashMap<String,String>> animalList = new ArrayList();

//    animalList.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new
                ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);

        ListView simpleList = (ListView) root.findViewById(R.id.listView1);
        savePassButton = root.findViewById(R.id.button3);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.fragment_home, R.id.textView, animalList);
//        simpleList.setAdapter(arrayAdapter);

        HashMap m = new HashMap();
        m.put("fb", "fb_pass");
        animalList.add(m);
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1 , animalList);
//        adapter = new MyCustomAdapter(this.getActivity(), animalList);
        key = root.findViewById(R.id.editText1);
        pass = root.findViewById(R.id.editText2);

        simpleList.setAdapter(adapter);
        Log.i("HomeFragment", "Adapter set");
        savePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button3:
                        if (!key.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty()) {
                            HashMap m = new HashMap();
                            m.put(key.getText().toString().trim(), pass.getText().toString().trim());
                            animalList.add(m);
                            Log.i("Added to PassList", key.getText().toString());
                            adapter.notifyDataSetChanged();
                            key.setText("");
                            pass.setText("");
                        }
                        break;
                    default:
                        break;
                }
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
    }
