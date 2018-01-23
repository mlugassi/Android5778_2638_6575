package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;

public class ManageActivity extends Activity {

    private Button addBranchActivityButton;
    private Button addCarActivityButton;
    private Button addCarModelActivityButton;
    private Button popularCarsButton;
    private Button dataListsButton;
    private Button myProfileButton;
    private Button logoutButton;
    private int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage);
        customerID = getIntent().getIntExtra(CarRentConst.CustomerConst.CUSTOMER_ID, -1);
        findViews();
    }


    private void findViews() {
        addBranchActivityButton = (Button) findViewById(R.id.add_branch_activity_button);
        addCarActivityButton = (Button) findViewById(R.id.add_car_activity_button);
        addCarModelActivityButton = (Button) findViewById(R.id.add_carModel_activity_button);
        dataListsButton = (Button) findViewById(R.id.dataListsButton);
        popularCarsButton = (Button) findViewById(R.id.popularCarsButton);
        myProfileButton = (Button) findViewById(R.id.myProfileButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        addBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, AddBranch.class));
            }
        });

        addCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, AddCar.class));
            }
        });
        addCarModelActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, AddCarModel.class));
            }
        });
        dataListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, DataLists.class));
            }
        });
        popularCarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this, PopularCars.class));
            }
        });
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, AddCustomer.class);
                intent.putExtra(CarRentConst.CustomerConst.CUSTOMER_ID, customerID);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear SharedPreferences details to prevent auto Login with current customer details
                Login.setDefaults(CarRentConst.UserConst.USER_NAME, "", ManageActivity.this);
                Login.setDefaults(CarRentConst.UserConst.PASSWORD, "", ManageActivity.this);

                Intent intent = new Intent(ManageActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
