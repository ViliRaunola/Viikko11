package com.example.viikko11;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//Apua haettu täältä https://www.youtube.com/watch?v=OLbsiE42JvE
public class Settings extends Fragment {
    private Button nappi;
    private EditText editText;
    private EditText fonttikoko;
    private Spinner spinner_varit;
    private Switch switch_caps;
    private Switch switch_bold;
    private String vari;
    private boolean caps;
    private boolean bold;


    Settings_Send_Listener settings_send_listener;

    public interface Settings_Send_Listener{

        public void Settings_Send(String viesti, int koko_fontti, String vari, boolean allcaps, boolean bold);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        nappi = view.findViewById(R.id.send);
        editText = view.findViewById(R.id.settings_editText);
        fonttikoko = view.findViewById(R.id.settings_fonttiKoko);

        switch_bold = view.findViewById(R.id.settings_bold);
        switch_bold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bold = isChecked;
            }
        });


        switch_caps = view.findViewById(R.id.settings_allcaps);
        switch_caps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                caps = isChecked;
            }
        });



        spinner_varit = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.varit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_varit.setAdapter(adapter);
        spinner_varit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vari = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        nappi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String viesti = editText.getText().toString();
                String koko = fonttikoko.getText().toString();
                int koko_fontti = 40;
                try {
                    koko_fontti = Integer.parseInt(koko);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

                settings_send_listener.Settings_Send(viesti, koko_fontti, vari, caps, bold);
            }
        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try{
            settings_send_listener = (Settings_Send_Listener) activity;
        }
        catch(ClassCastException e){
            throw  new ClassCastException(activity.toString() + " täytyy ensin impletoida Settings_Send_Listener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setText("");
        fonttikoko.setText("");
    }
}
