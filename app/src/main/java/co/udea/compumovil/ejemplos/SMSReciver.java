package co.udea.compumovil.ejemplos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SMSReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show();
        Log.d("SMS RECIVED", "onRecive");
    }
}
