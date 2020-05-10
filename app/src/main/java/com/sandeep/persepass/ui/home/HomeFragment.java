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

import com.google.android.material.snackbar.Snackbar;
import com.sandeep.persepass.R;
import com.sandeep.persepass.data.LoginDataSource;
import com.sandeep.persepass.data.LoginRepository;
import com.sandeep.persepass.data.model.PersePass;
import com.sandeep.persepass.security.Secure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author sandeep
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LoginRepository loginRepository;
    private ArrayAdapter adapter;
    private Button savePassButton;
    private TextView key;
    private TextView pass;
    static final String FILE_NAME = "persePassFile.txt";
    private String CHILD_FOLDER = "";

    ArrayList<HashMap<String, String>> passList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new
                ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loginRepository = LoginRepository.getInstance(new LoginDataSource());

        CHILD_FOLDER = loginRepository.getLoggedInUser().getDisplayName();

        // encrypt and decrypt
//        String enc = Secure.encrypt("VRAsdre!", loginRepository.getLoggedInUser().getUserId());
//        System.out.println("ENC" + enc);
//        String dec = Secure.decrypt(enc, loginRepository.getLoggedInUser().getUserId());
//        System.out.println("DEC: "+dec);

        ListView simpleList = root.findViewById(R.id.listView1);
        savePassButton = root.findViewById(R.id.button3);

        List<PersePass> persePassObject = readFromFile();
        if (persePassObject != null) {
            persePassObject.forEach(pp -> {
                HashMap<String, String> map = new HashMap<>(0);
                map.put(Secure.decrypt(pp.getKey(), loginRepository.getLoggedInUser().getUserId()),
                        Secure.decrypt(pp.getPass(), loginRepository.getLoggedInUser().getUserId()));
                passList.add(map);
            });
        }
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, passList);
        key = root.findViewById(R.id.editText1);
        pass = root.findViewById(R.id.editText2);

        simpleList.setAdapter(adapter);
        Log.i("HomeFragment", "Adapter set");
        savePassButton.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.button3:
                    if (!key.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty()) {
                        HashMap m = new HashMap();
                        m.put(key.getText().toString().trim(), pass.getText().toString().trim());
                        passList.add(m);
                        Log.i("Added to PassList", key.getText().toString());
                        adapter.notifyDataSetChanged();
                        writeToFile();
                        pass.setText("");
                        key.setText("");
                        Snackbar.make(v, "Pass added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    break;
                default:
                    break;
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }

    // write text to file
    public void writeToFile() {
        File file = new File(getContext().getExternalFilesDir(null), CHILD_FOLDER);
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File oFile = new File(file, FILE_NAME);
            FileOutputStream output = new FileOutputStream(oFile, true);
            OutputStreamWriter writer = new OutputStreamWriter(output);
            String saveValue = Secure.encrypt(key.getText().toString(), loginRepository.getLoggedInUser().getUserId()) +
                    "|" + Secure.encrypt(pass.getText().toString(), loginRepository.getLoggedInUser().getUserId());
            writer.append(saveValue);
            writer.append("\n");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public List<PersePass> readFromFile() {
        File file = new File(getContext().getExternalFilesDir(null), CHILD_FOLDER);
        if (!file.exists()) {
            Log.i("FILE_NOT_FOUD", "file not found " + getContext().getExternalFilesDir(null) + CHILD_FOLDER + "/" + FILE_NAME);
            return null;
        }
        try {
            File oFile = new File(file, FILE_NAME);

            FileReader reader = new FileReader(oFile);
            BufferedReader in = new BufferedReader(reader);
            String text = "";
            List<PersePass> persePasses = new ArrayList<>(0);
            while ((text = in.readLine()) != null) {
                String values[] = text.split("[|]");
                PersePass pp = new PersePass(values[0], values[1]);
                persePasses.add(pp);
            }
            return persePasses;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

