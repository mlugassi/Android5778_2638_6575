package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent intent =  new Intent(MainActivity.this , Login.class);
        finish();
        MainActivity.this.startActivity(intent);
    }
}

