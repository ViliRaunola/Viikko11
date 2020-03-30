package com.example.viikko11;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Text extends Fragment {

    private TextView textView;
    private EditText editText;
    private String teksti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text, container, false);

        textView = view.findViewById(R.id.text_textView);
        editText = view.findViewById(R.id.text_edittext);

        Bundle bundle = getArguments();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                teksti = editText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                teksti = editText.getText().toString();
            }
        });

        try {
            String message = bundle.getString("message");
            if(!message.equals("")){
                textView.setText(message);
            }
            String vari = bundle.getString("vari");
            if(!vari.equals("")){
                if(vari.equals("musta")) {
                    textView.setTextColor(Color.BLACK);
                }else if(vari.equals("keltainen")){
                    textView.setTextColor(Color.YELLOW);
                }else if(vari.equals("vihreä")){
                    textView.setTextColor(Color.GREEN);
                }else if(vari.equals("valkea")){
                    textView.setTextColor(Color.WHITE);
                }
            }

            int koko = bundle.getInt("koko");
            textView.setTextSize(koko);

            Boolean caps = bundle.getBoolean("caps");
            textView.setAllCaps(caps);

            boolean bold = bundle.getBoolean("bold");
            if(bold == true){
                textView.setTypeface(null, Typeface.BOLD);
            }

            boolean muokkaus = bundle.getBoolean("muokkaus");

            if(muokkaus == true){
                editText.setFocusable(true);
            }else{
                textView.setText(teksti);
                editText.setFocusable(false);
            }


        } catch (NullPointerException e) {
            editText.setFocusable(false);
            e.printStackTrace();
        }
        return view;
    }
}
