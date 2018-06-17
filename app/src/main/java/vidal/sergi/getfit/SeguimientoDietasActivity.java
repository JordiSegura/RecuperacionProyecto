package vidal.sergi.getfit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import vidal.sergi.getfit.Objetos.FirebaseReferences;
import vidal.sergi.getfit.Objetos.Seguimiento;

public class SeguimientoDietasActivity extends AppCompatActivity {
    public final String TAG ="SeguimientoRutinas";
    Intent intent;

    ImageView ivLogo;
    int difDias;

    public int getDifDias() {
        return difDias;
    }

    public void setDifDias(int difDias) {
        this.difDias = difDias;
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference(FirebaseReferences.CURRENT_USER);
    DatabaseReference usersRef2 = database.getReference(FirebaseReferences.USERS);

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("A no ser que veas 'bien' al lado de la fecha se considera ese dia como no completado");
        builder.setTitle("Info Seguimiento");
        builder.setNegativeButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_segumiento_dietas_activity);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
        ivLogo = findViewById(R.id.ivLogo1);
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SeguimientoDietasActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });




        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation4);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        intent = new Intent(SeguimientoDietasActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_rutinas:
                        intent = new Intent(SeguimientoDietasActivity.this, RutinasActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_dietas:
                        intent = new Intent(SeguimientoDietasActivity.this, DietasActivity.class);
                        startActivity(intent);;
                        break;
                }
                return true;
            }
        });

    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");

        mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");  mImageUrls.add("https://image.flaticon.com/icons/png/128/42/42524.png");
        mNames.add("Lunes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672701_calendar_512x512.png");
        mNames.add("Martes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672906_calendar_512x512.png");
        mNames.add("Miercoles");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/16/672860_calendar_512x512.png");
        mNames.add("Jueves");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672747_calendar_512x512.png");
        mNames.add("Viernes");

        mImageUrls.add("https://www.shareicon.net/data/256x256/2015/11/15/672757_calendar_512x512.png");
        mNames.add("Sábado");

        mImageUrls.add("https://www.shareicon.net/data/128x128/2015/11/15/672742_calendar_512x512.png");
        mNames.add("Domingo");

        initRecyclerView();
    }
    private void initRecyclerView(){
        String contador2 = "semana1";
        String val = contador2;

        Bundle extras2 = getIntent().getExtras();
        String value = extras2.getString("diferencia2");

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        SeguimientoDietasAdapter adapter = new SeguimientoDietasAdapter(mNames,mImageUrls,this,contador2,value);
        adapter.setIntento(val);
        adapter.setDiferenciaDias(value);
        Log.d(TAG, "onDataChange: wwwwwwwwwwwwwwwww "+ value);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




}
