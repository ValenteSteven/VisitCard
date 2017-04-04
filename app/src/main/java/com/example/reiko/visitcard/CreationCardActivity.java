package com.example.reiko.visitcard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreationCardActivity  extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText editTextName;
    EditText editTextSurName;
    EditText editTextAdress;
    EditText editTextMail;
    EditText editTextFunction;
    EditText editTextPhone;
    EditText editTextCompagny;
    EditText editTextWebSite;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_card);


        Intent intent = getIntent();

        imageView = (ImageView)findViewById(R.id.imageView);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextSurName = (EditText)findViewById(R.id.editTextsurname);
        editTextAdress = (EditText)findViewById(R.id.editTextAdress);
        editTextMail = (EditText)findViewById(R.id.editTextEmail);
        editTextFunction = (EditText)findViewById(R.id.editTextFunction);
        editTextPhone = (EditText)findViewById(R.id.editTextPhoneNumber);
        editTextCompagny = (EditText)findViewById(R.id.editTextCompanyName);
        editTextPhone = (EditText)findViewById(R.id.editTextPhoneNumber);
        editTextWebSite = (EditText)findViewById(R.id.editTextWebsite);

        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditTextValue = editTextName.getText().toString() + "\n" +
                        editTextSurName.getText().toString() +  "\n" +
                        editTextAdress.getText().toString() + "\n" +
                        editTextMail.getText().toString() + "\n" +
                        editTextFunction.getText().toString() + "\n" +
                        editTextPhone.getText().toString() + "\n" +
                        editTextCompagny.getText().toString() + "\n" +
                        editTextPhone.getText().toString() + "\n" +
                        editTextWebSite.getText().toString();

                try {
                    bitmap = TextToImageEncode(EditTextValue);

                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}

