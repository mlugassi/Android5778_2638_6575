package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.ListsDataSource;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
import lugassi.wallach.android5778_2638_6575.model.entities.CarType;
import lugassi.wallach.android5778_2638_6575.model.entities.Company;
import lugassi.wallach.android5778_2638_6575.model.entities.EngineCapacity;

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

