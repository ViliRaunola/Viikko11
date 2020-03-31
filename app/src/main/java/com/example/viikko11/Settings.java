package com.example.viikko11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.util.ULocale;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

//Apua haettu täältä https://www.youtube.com/watch?v=OLbsiE42JvE, https://www.youtube.com/watch?v=qTHmMr7JW6k
public class Settings extends Fragment {
    private Button nappi;
    private Button nappi_kieli;
    private EditText editText;
    private EditText fonttikoko;
    private Spinner spinner_varit;
    private Spinner spinner_kielet;
    private Switch switch_caps;
    private Switch switch_bold;
    private Switch switch_muokkaus;
    private int vari;
    private boolean caps;
    private boolean bold;
    private boolean muokkaus;
    private int kieli;


    Settings_Send_Listener settings_send_listener;

    public interface Settings_Send_Listener{

        public void Settings_Send(String viesti, int koko_fontti, int vari, boolean allcaps, boolean bold, boolean muokkaus);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        nappi = view.findViewById(R.id.send);
        nappi_kieli = view.findViewById(R.id.settings_vaihdakieli);
        editText = view.findViewById(R.id.settings_editText);
        fonttikoko = view.findViewById(R.id.settings_fonttiKoko);

        switch_muokkaus = view.findViewById(R.id.settings_lupa);
        switch_muokkaus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                muokkaus = isChecked;
            }
        });

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

        spinner_kielet = view.findViewById(R.id.spinner_kielet);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.kielet, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kielet.setAdapter(adapter2);
        spinner_kielet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kieli = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                kieli = 2;
            }
        });




        spinner_varit = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.varit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_varit.setAdapter(adapter);
        spinner_varit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vari = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vari = 0;
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

                settings_send_listener.Settings_Send(viesti, koko_fontti, vari, caps, bold, muokkaus);
            }
        });

        nappi_kieli.setOnClickListener(new View.OnClickListener() {
            String valinta;
            @Override
            public void onClick(View v) {
                if(kieli == 2){
                    valinta = "en";
                }else if(kieli == 0){
                    valinta = "fi";
                }else{
                    valinta = "en";
                }
                //https://stackoverflow.com/questions/12908289/how-to-change-language-of-app-when-user-selects-language
                System.out.println("#################"+valinta+"#####################");
                Locale myLocale = new Locale(valinta);
                Resources resources = getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration conf = resources.getConfiguration();
                conf.locale = myLocale;
                resources.updateConfiguration(conf, dm);
                sendActivity(valinta);
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
    }

    public void sendActivity(String kieli){
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("kieli", kieli);
        startActivity(intent);
    }

}
