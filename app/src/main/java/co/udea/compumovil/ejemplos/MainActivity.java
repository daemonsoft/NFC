package co.udea.compumovil.ejemplos;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private final String ACTION_SEND = "co.udea.compumovil.ejemplos.SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        broadcastReceiver = new MyReceiver();
        intentFilter = new IntentFilter();
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        intentFilter.addAction(ACTION_SEND);
        // registerReceiver(broadcastReceiver, intentFilter);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), MyService.class);
                //intent.putExtra("KEY", "Hola Service");
                //startService(intent);

                Intent intent = new Intent();
                intent.setAction(ACTION_SEND);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
