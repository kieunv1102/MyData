package com.entertaiment.photo.christmassticker.screen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.adapters.TextFontsAdapter;
import com.entertaiment.photo.christmassticker.models.MyDataModel;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TextActivity extends Activity {
    GridView grvFontText;
    TextFontsAdapter textFontsAdapter;
    EditText edtAddText;
    TextView txtDisplayText;
    Button btnApply, btnColor;
    int mcolor = 0xffffff00;
    int font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        edtAddText = (EditText) findViewById(R.id.edt_dialog_text__enter_text);
        txtDisplayText = (TextView) findViewById(R.id.txv_dialog_text_input);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnColor = (Button) findViewById(R.id.btnColor);
        grvFontText = (GridView) findViewById(R.id.grvFontText);
        textFontsAdapter = new TextFontsAdapter(TextActivity.this, MyDataModel.getFonts());
        grvFontText.setAdapter(textFontsAdapter);
        grvFontText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                font = position;
                if (position == 0) {
                    txtDisplayText.setTypeface(null, Typeface.NORMAL);
                } else {
                    Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), MyDataModel.getFonts().get(position));
                    txtDisplayText.setTypeface(typeface);
                }
            }
        });
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(false);
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                returnIntent.putExtra("add_text",txtDisplayText.getText());
                returnIntent.putExtra("color_text",mcolor);
                returnIntent.putExtra("font_text",MyDataModel.getFonts().get(font));
                setResult(10,returnIntent);
                finish();
            }
        });
        edtAddText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtDisplayText.setText(s.toString());
//                txvDialogTextInput.clearComposingText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(TextActivity.this, mcolor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                TextActivity.this.mcolor = color;
                txtDisplayText.setTextColor(mcolor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
