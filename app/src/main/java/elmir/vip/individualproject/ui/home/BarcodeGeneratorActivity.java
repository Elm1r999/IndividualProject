package elmir.vip.individualproject.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Objects;

import elmir.vip.individualproject.R;

public class BarcodeGeneratorActivity extends AppCompatActivity {

    EditText enterCode;
    Button generateCode;
    ImageView bar_Code, qr_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_generator);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        enterCode = findViewById(R.id.enterCode);
        generateCode = findViewById(R.id.generateQRCode);
        bar_Code = findViewById(R.id.barcode);
        qr_Code = findViewById(R.id.qr_code);

        generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix qrCode = multiFormatWriter.encode(enterCode.getText().toString(), BarcodeFormat.QR_CODE, 350, 300);
                    BitMatrix barcode = multiFormatWriter.encode(enterCode.getText().toString(), BarcodeFormat.CODE_128, 400, 200, null);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(qrCode);
                    Bitmap bitmap2 = barcodeEncoder.createBitmap(barcode);
                    qr_Code.setImageBitmap(bitmap);
                    bar_Code.setImageBitmap(bitmap2);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}