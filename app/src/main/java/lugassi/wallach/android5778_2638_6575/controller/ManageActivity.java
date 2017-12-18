package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import lugassi.wallach.android5778_2638_6575.R;

public class ManageActivity extends Activity {

    private Button addBranchActivityButton;
    private Button addCarActivityButton;
    private Button addCarModelActivityButton;
    private Button showBranchActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage);
        findViews();
    }

    private void findViews() {
        addBranchActivityButton = (Button) findViewById(R.id.add_branch_activity_button);
        addCarActivityButton = (Button) findViewById(R.id.add_car_activity_button);
        addCarModelActivityButton = (Button) findViewById(R.id.add_carModel_activity_button);
        showBranchActivityButton = (Button) findViewById(R.id.show_branch_activity_button);

        addBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this , AddBranch.class));
            }
        });

        addCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this , AddCar.class ));
            }
        });
        addCarModelActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this , AddCarModel.class));
            }
        });
        showBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageActivity.this , DataLists.class));
            }
        });
    }
}
