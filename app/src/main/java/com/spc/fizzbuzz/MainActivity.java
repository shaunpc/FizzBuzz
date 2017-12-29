package com.spc.fizzbuzz;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FizzBuzz";
    TextView    textView;
    Button      buttonAuto;

    Random random = new Random();
    CountDownTimer fizzbuzzTimer;

    int     fizzbuzzCounter = 0;
    float   fontsize = 40;
    int     randomColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the other relevant UI elements
        textView = findViewById(R.id.textView);
        buttonAuto = findViewById(R.id.buttonAuto);

        Log.v(TAG, "setting initial textview text");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontsize);
        setFizzbuzzText(fizzbuzzCounter);

    }


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
        if (id == R.id.action_reset_counter) {
            //Call the counterReset function with a null view parameter
            counterReset(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void counterAuto (View v) {
             // As long as not already existing, define a 100sec countdown with a tick every second
            if (fizzbuzzTimer == null) {
                Log.v(TAG, "...inside counterAuto - timer is NULL; fbc is " + fizzbuzzCounter);
                fizzbuzzTimer = new CountDownTimer(100000, 1000) {
                    // define what happens each tick
                    public void onTick(long millisUntilFinished) {
                        // Do something each tick
                        Log.v(TAG, "...inside counterAuto onTick; fbc is " + fizzbuzzCounter);
                        fizzbuzzCounter = fizzbuzzCounter + 1;
                        setFizzbuzzText(fizzbuzzCounter);
                    }
                    // define what happens when finished
                    public void onFinish() {
                        // Do something when finished
                        Log.v(TAG, "...inside counterAuto onFinish; fbc is " + fizzbuzzCounter);
                        fizzbuzzCounter = 0;
                        setFizzbuzzText(fizzbuzzCounter);
                        fizzbuzzTimer = null;
                        buttonAuto.setText(getString(R.string.buttonAuto));
                    }
                    // start the thing going
                }.start();
                buttonAuto.setText(getString(R.string.buttonStop));
            } else {
                Log.v(TAG, "...inside counterAuto - timer is NOT NULL; fbc is " + fizzbuzzCounter);
                fizzbuzzTimer.cancel();
                fizzbuzzTimer = null;
                buttonAuto.setText(getString(R.string.buttonAuto));
            }

    }

    public int getRandomColour () {
            int red = Math.abs(random.nextInt() % 256);
            int green = Math.abs(random.nextInt() % 256);
            int blue = Math.abs(random.nextInt() % 256);
            return Color.rgb(red, green, blue);
    }

    public void counterAdd (View v) {
        Log.v(TAG, "...inside counterAdd; fbc is " + fizzbuzzCounter);
        // If the counter is running on AUTO, then cancel it
        if (fizzbuzzTimer != null) {
            Log.v(TAG, "...inside counterAdd - cancelling timer; fbc is " + fizzbuzzCounter);
            fizzbuzzTimer.cancel();
            fizzbuzzTimer = null;
            buttonAuto.setText(getString(R.string.buttonAuto));
        }
        fizzbuzzCounter = fizzbuzzCounter + 1;
        setFizzbuzzText(fizzbuzzCounter);

    }

    public void setFizzbuzzText (int fbc) {
        String fbt;
        randomColour = getRandomColour ();
        textView.setTextColor(randomColour);
        if (fbc == 0)  {
            textView.setText(getString(R.string.ready));
        } else {
            //
            // This is the real CODING TEST
            // a) For multiples of 3 print "Fizz" instead of the number
            // b) For multiples of 5 print "Buzz" instead of the number
            // c) For numbers which are multiples of both 3 and 5 print "
            fbt = Integer.toString(fbc);
            if (fbc % 3 == 0) fbt = "Fizz";
            if (fbc % 5 == 0) fbt = "Buzz";
            if (fbc % 15 == 0) fbt = "FizzBuzz";
            textView.setText("> "+fbt+" <");

            // TODO //
            // Stage 2 - New Requirements
            // A number is FIZZ if it is divisible by 3 or if it has a 3 in it
            // A number is BUZZ if it is divisible by 5 or if it has a 5 in it

        }

    }

    public void counterSubtract (View v) {
        Log.v(TAG, "...inside counterSubtract; fbc is " + fizzbuzzCounter);
        // If the counter is running on AUTO, then cancel it
        if (fizzbuzzTimer != null) {
            Log.v(TAG, "...inside counterSubtract - cancelling timer; fbc is " + fizzbuzzCounter);
            fizzbuzzTimer.cancel();
            fizzbuzzTimer = null;
            buttonAuto.setText(getString(R.string.buttonAuto));
        }

        // ensure the counter doesn't go negative
        if (fizzbuzzCounter > 0) {
            fizzbuzzCounter = fizzbuzzCounter - 1;
            setFizzbuzzText(fizzbuzzCounter);
        }


    }

    public void counterReset (View v) {
        Log.v(TAG, "...inside counterReset; fbc is " + fizzbuzzCounter);
        if (fizzbuzzTimer != null) {
            Log.v(TAG, "...inside counterReset - cancelling timer; fbc is " + fizzbuzzCounter);
            fizzbuzzTimer.cancel();
            fizzbuzzTimer = null;
            buttonAuto.setText(getString(R.string.buttonAuto));
        }
        fizzbuzzCounter = 0;
        setFizzbuzzText (fizzbuzzCounter);

    }

    // These two overrides should store the current value of the counter, such that if the
    // app reappears, it doesn't reset it to zero!
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        Log.v(TAG, "...inside onSaveInstanceState; fbc being stored is " + fizzbuzzCounter);
        savedInstanceState.putInt("fizzbuzzCounter", fizzbuzzCounter);
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        fizzbuzzCounter = savedInstanceState.getInt("fizzbuzzCounter");
        Log.v(TAG, "...inside onRestoreInstanceState; fbc retrieved is " + fizzbuzzCounter);
    }

     /**
     * The AdMob Advertising Fragment view
     */
    public static class AdFragment extends Fragment {

        private AdView mAdView;

        public AdFragment() {
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }

        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
            // values/strings.xml.
            mAdView = getView().findViewById(R.id.adView);

            // Add the test device whilst getting just test ads
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);
        }

        /** Called when leaving the activity */
        @Override
        public void onPause() {
            if (mAdView != null) {
                mAdView.pause();
            }
            super.onPause();
        }

        /** Called when returning to the activity */
        @Override
        public void onResume() {
            super.onResume();
            if (mAdView != null) {
                mAdView.resume();
            }
        }

        /** Called before the activity is destroyed */
        @Override
        public void onDestroy() {
            if (mAdView != null) {
                mAdView.destroy();
            }
            super.onDestroy();
        }


    }
}
