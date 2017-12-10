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
        setContentView(R.layout.activity_main);
        findViews();
//        Branch branch;
//        Car car;
//        CarModel carModel;
//        DB_manager db_manager = DBManagerFactory.getManager();
//
//        for(Integer  i = 0; i < 10 ; i++)
//        {
//            branch = new Branch();
//            carModel = new CarModel();
//            car = new Car();
//
//            branch.setActualParkingSpace(i);
//            branch.setCity(i.toString());
//            branch.setBranchName(i.toString());
//            branch.setMaxParkingSpace(i);
//            branch.setAddress(i.toString());
//
//            carModel.setCompany(Company.BMW);
//            carModel.setModelCode(i);
//            carModel.setCarType(CarType.Manual);
//            carModel.setMaxGasTank(i);
//            carModel.setEngineCapacity(EngineCapacity._1000);
//            carModel.setSeats(i);
//
//            car.setModelCode(i);
//            car.setBranchID(i);
//            car.setMileage(i);
//            car.setReservations(i);
//
//            db_manager.getBranches().add(branch);
//            db_manager.getCarModels().add(carModel);
//            db_manager.getCars().add(car);
//        }

    }

    private Button addBranchActivityButton;
    private Button addCarActivityButton;
    private Button addCarModelActivityButton;
    private Button showBranchActivityButton;

    private void findViews() {
        addBranchActivityButton = (Button) findViewById(R.id.add_branch_activity_button);
        addCarActivityButton = (Button) findViewById(R.id.add_car_activity_button);
        addCarModelActivityButton = (Button) findViewById(R.id.add_carModel_activity_button);
        showBranchActivityButton = (Button) findViewById(R.id.show_branch_activity_button);

        addBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , AddBranch.class));
            }
        });

        addCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , AddCar.class ));
            }
        });
        addCarModelActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , AddCarModel.class));
            }
        });
        showBranchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , DataLists.class));
            }
        });
    }
}

