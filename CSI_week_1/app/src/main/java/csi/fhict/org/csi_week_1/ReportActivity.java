package csi.fhict.org.csi_week_1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ReportActivity extends AppCompatActivity {

    private static final byte CALL_PERMISSION = 1;
    private static final byte CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button callPopo = (Button)findViewById(R.id.callPopo);
        callPopo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });

        Button btnMakePic = (Button)findViewById(R.id.btnMakePic);
        btnMakePic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                showCamera();
            }
        });


    }

    //Controleer of de benodigde permissions gegeven zijn en vraagt er om als dat niet het geval is
//    protected void checkCallPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
//                //Laat een verklaring aan de gebruiker zien
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
//            }
//        } else {
//            callDaPolice();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION) {
            //Recieved permission result for camera permission.

            // Check if the only required permission has been granted
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission had been granted start camera intent
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Geen toegang tot camera verleend", Toast.LENGTH_SHORT).show();
            }


        } else if (requestCode == CALL_PERMISSION) {
            //Recieved permission result for phone permission.

            // Check if the only required permission has been granted
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission had been granted start phone intent
                callDaPolice();
            } else {
                Toast.makeText(this, "Geen toegang tot telefoon verleend", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void callDaPolice(){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "113", null)));
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showCamera() {
        //Check if camera permission is granted
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //Camera permission is granted -> dispatch camera intent
            dispatchTakePictureIntent();
        } else {
            //Camera permission not granted
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Toegang tot de camera is nodig om een foto van het boefje te maken!", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void makeCall() {
        //Check if call permission is granted
        if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //Phone permission is granted -> dispatch phone intent
            callDaPolice();
        } else {
            //Phone permission not granted
            if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "Toegang tot de telefoon is nodig om 112 te bellen!", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
        }
    }

}
