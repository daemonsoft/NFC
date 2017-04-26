package co.udea.compumovil.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    NfcAdapter nfcAdapter;
    PendingIntent nfcPendingIntent;
    private TextView mTextView;
    private TextView mTextViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mTextView = (TextView) findViewById(R.id.textView_explanation);
        mTextViewId = (TextView) findViewById(R.id.idNFC);

        if (nfcAdapter == null)
            Toast.makeText(this, "No se dispone de un NFC", Toast.LENGTH_SHORT).show();
        else {

            if (!nfcAdapter.isEnabled()) {
                mTextView.setText(R.string.nfc_iddle);
            } else {
                mTextView.setText(R.string.nfc_enable);
                handleIntent(getIntent());
            }
        }
    }

    public void handleIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] msgs = null;
            mTextView.setText("Hello NFC TAG!!!");
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null)
            {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            mTextView.setText("Hello TECH!");
        }
        printTagId(intent);
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    protected void printTagId(Intent intent) {
        if(intent.hasExtra(NfcAdapter.EXTRA_ID)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            mTextViewId.setText("" + toHexString(byteArrayExtra));
        }
    }

    public String toHexString(byte[] buffer) {
        StringBuilder sb = new StringBuilder();
        for(byte b: buffer)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString().toUpperCase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundMode();
    }

    public void enableForegroundMode() {
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] writeTagFilters = new IntentFilter[] {tagDetected};
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, writeTagFilters, null);
    }
    public void disableForegroundMode() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    /*******************************************************************/
    /*******************************************************************/
    /*******************************************************************/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
