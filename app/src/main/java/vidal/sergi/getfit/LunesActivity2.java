package vidal.sergi.getfit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LunesActivity2 extends AppCompatActivity {
    public final String TAG ="LunesActivity2";
    RecyclerView rv;
    List<String> diasSemanas;
    LunesAdapter2 adapter;
    Intent intent;
    public String dd;
    TextView tvindica;

    public LunesActivity2(String dd) {
        this.dd = dd;
    }

    public LunesActivity2() {
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lunes3);
        Bundle extras2 = getIntent().getExtras();
        String value = extras2.getString("c");
        final String value2 = extras2.getString("d");
        Log.d(TAG, "onCreate: VAAAKSKSAKASKAS "+value2);
        tvindica = findViewById(R.id.tvindica);
        tvindica.setText(value2);
        AdapterHome ah = new AdapterHome(value2);
        ah.setDias(value2);
        LunesAdapter ls= new LunesAdapter();
        rv = findViewById(R.id.recycler);
        rv. setLayoutManager(new LinearLayoutManager(this));
        diasSemanas = new ArrayList<String>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapter = new LunesAdapter2(diasSemanas,value,value2);
        adapter.setValor(value);

        adapter.setDia(value2);
        rv.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        /*if (extras != null) {

            String value3 = extras2.getString("diferencia");

        }*/
        database.getReference().child("Seguimiento/Semana1/Domingo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diasSemanas.removeAll(diasSemanas);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: VALOR SNAPSHOT "+snapshot.getKey());
                    diasSemanas.add(snapshot.getKey());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        intent = new Intent(LunesActivity2.this, HomeActivity.class);
                        intent.putExtra("dia",value2);
                        startActivity(intent);
                        break;
                    case R.id.action_rutinas:
                        intent = new Intent(LunesActivity2.this, RutinasActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_dietas:
                        intent = new Intent(LunesActivity2.this, DietasActivity.class);
                        startActivity(intent);;
                        break;
                }
                return true;
            }
        });

    }


}


