package elmir.vip.individualproject.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Objects;

import elmir.vip.individualproject.R;

public class BarcodeGeneratorActivity extends AppCompatActivity {

    Button generateCode;
    ImageView bar_Code, qr_Code;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_generator);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        generateCode = findViewById(R.id.generateQRCode);
        bar_Code = findViewById(R.id.barcode);
        qr_Code = findViewById(R.id.qr_code);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        generateCode.setOnClickListener(v -> {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                //display unique QR/Barcode for every user based on their User ID from Firebase
                BitMatrix qrCode = multiFormatWriter.encode(userId, BarcodeFormat.QR_CODE, 350, 300);
                BitMatrix barcode = multiFormatWriter.encode(userId, BarcodeFormat.CODE_128, 400, 200, null);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(qrCode);
                Bitmap bitmap2 = barcodeEncoder.createBitmap(barcode);
                qr_Code.setImageBitmap(bitmap);
                bar_Code.setImageBitmap(bitmap2);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }
}