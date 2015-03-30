package com.example.kenny.pluggedin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class StepOne extends Activity implements View.OnClickListener {

    private ViewFlipper vf;
    private Button Next;
    private Button Previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        Previous = (Button) findViewById(R.id.button);
        Next = (Button) findViewById(R.id.button2);
        Next.setOnClickListener(this);
        Previous.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == Next) {
            vf.showNext();
        }
        if (v == Previous) {
            vf.showPrevious();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_one, menu);
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

