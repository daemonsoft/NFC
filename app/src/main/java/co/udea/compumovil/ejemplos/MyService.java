package co.udea.compumovil.ejemplos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);

        String data = intent.getStringExtra("KEY");
        Log.d(  "MyService", "Se inició el servicio" + data);
        return flags;
    }

    public void onDestroy(View view)
    {
    }
}
