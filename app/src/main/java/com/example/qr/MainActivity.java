package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String text;
    ImageView qrImageView;
    Button optionsButton, saveAsPngButton, saveAsSvgButton;
    LinearLayout extraOptions;
    EditText sizeEditText;
    Spinner errorCorrectionLevelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // replace with your layout file name

        qrImageView = findViewById(R.id.qr);
        optionsButton = findViewById(R.id.extraOptionsButton);
        extraOptions = findViewById(R.id.extraOptionsLayout);
        saveAsPngButton = findViewById(R.id.saveAsPngButton);
        saveAsSvgButton = findViewById(R.id.saveAsSvgButton);
        sizeEditText = findViewById(R.id.sizeEditText);
        errorCorrectionLevelSpinner = findViewById(R.id.errorCorrectionSpinner);

        text = "1234124124124/123124124/123123123";

        Bitmap bitmap = QRCodeGenerator.generateBitmapQRCode(text, ErrorCorrectionLevel.L, 1200);
        qrImageView.setImageBitmap(bitmap);
//        QRCodeGenerator.saveQRCodeToExternalStorage(uri);

        optionsButton.setOnClickListener(v -> toggleExtraOptions());
        saveAsPngButton.setOnClickListener(v -> {
            if (sizeEditText.getText().toString().isEmpty()) {
                sizeEditText.setError("Please enter size");
                sizeEditText.requestFocus();
                return;
            }
            ErrorCorrectionLevel L;
            switch (errorCorrectionLevelSpinner.getSelectedItemPosition()) {
                case 0:
                    L = ErrorCorrectionLevel.L;
                    break;
                case 1:
                    L = ErrorCorrectionLevel.M;
                    break;
                case 2:
                    L = ErrorCorrectionLevel.Q;
                    break;
                case 3:
                    L = ErrorCorrectionLevel.H;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + errorCorrectionLevelSpinner.getSelectedItemPosition());
            }
            try {
                QRCodeGenerator.savePNGQR(text, L, Integer.parseInt(sizeEditText.getText().toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        saveAsSvgButton.setOnClickListener(v -> {
            ErrorCorrectionLevel L;
            switch (errorCorrectionLevelSpinner.getSelectedItemPosition()) {
                case 0:
                    L = ErrorCorrectionLevel.L;
                    break;
                case 1:
                    L = ErrorCorrectionLevel.M;
                    break;
                case 2:
                    L = ErrorCorrectionLevel.Q;
                    break;
                case 3:
                    L = ErrorCorrectionLevel.H;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + errorCorrectionLevelSpinner.getSelectedItemPosition());
            }
            try {
                QRCodeGenerator.savePNGQR(text, L, Integer.parseInt(sizeEditText.getText().toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QRCodeGenerator.saveVectorQR(text, L);

        });

    }

    private void toggleExtraOptions() {
        if (extraOptions.getVisibility() == View.VISIBLE) {
            extraOptions.setVisibility(View.GONE);
        } else {
            extraOptions.setVisibility(View.VISIBLE);
        }
    }
}