package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private Button addBranchActivityButton;
    private Button addCarActivityButton;
    private Button addCarModelActivityButton;
    private Button addCustomerActivityButton;
    private Button addPromotionActivityButton;
    private Button addReservationActivityButton;
    private Button showBranchActivityButton;
    private Button showCarActivityButton;
    private Button showCarModelActivityButton;
    private Button showCustomerActivityButton;
    private Button showPromotionActivityButton;
    private Button showReservationActivityButton;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-12-03 21:49:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        addBranchActivityButton = (Button) findViewById(R.id.add_branch_activity_button);
        addCarActivityButton = (Button) findViewById(R.id.add_car_activity_button);
        addCarModelActivityButton = (Button) findViewById(R.id.add_carModel_activity_button);
        addCustomerActivityButton = (Button) findViewById(R.id.add_customer_activity_button);
        addPromotionActivityButton = (Button) findViewById(R.id.add_promotion_activity_button);
        addReservationActivityButton = (Button) findViewById(R.id.add_reservation_activity_button);
        showBranchActivityButton = (Button) findViewById(R.id.show_branch_activity_button);
        showCarActivityButton = (Button) findViewById(R.id.show_car_activity_button);
        showCarModelActivityButton = (Button) findViewById(R.id.show_carModel_activity_button);
        showCustomerActivityButton = (Button) findViewById(R.id.show_customer_activity_button);
        showPromotionActivityButton = (Button) findViewById(R.id.show_promotion_activity_button);
        showReservationActivityButton = (Button) findViewById(R.id.show_reservation_activity_button);

        addBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , AddBranch.class));
            }
        });

//        addCarActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
//        addCarModelActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
     //   addCustomerActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
    //    addPromotionActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
     //   addReservationActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
        showBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Branches.class));
            }
        });
        showCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , Cars.class));
            }
        });
    //    showCarModelActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
    //    showCustomerActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
     //   showPromotionActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
      //  showReservationActivityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this , ));
//            }
//        });
    }
}

