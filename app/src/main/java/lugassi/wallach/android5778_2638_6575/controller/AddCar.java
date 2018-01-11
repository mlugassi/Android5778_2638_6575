package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;

public class AddCar extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private ArrayList<Branch> branches;
    private ArrayList<CarModel> carModels;
    private Spinner branchesSpinner;
    private Spinner carModelsSpinner;
    private Button button;
    private Car car;
    private int carID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_car);

        db_manager = DBManagerFactory.getManager();
        branches = db_manager.getBranches();
        carModels = db_manager.getCarModels();
        findViews();
        setCarValues();
    }

    private void resetEditText() {
        branchesSpinner.setSelection(-1);
        carModelsSpinner.setSelection(-1);
    }

    void setCarValues() {
        carID = getIntent().getIntExtra(CarRentConst.CarConst.CAR_ID, -1);
        if (carID >= 0) {
            car = db_manager.getCar(carID);
            // branchesSpinner.setSelection(db_manager.getBranches().indexOf()car.getBranchID());
            // carModelsSpinner.setSelection(car.getModelCode());
            button.setText(getString(R.string.buttonUpdate));
        }
    }

    private void updateCar() {
        try {
            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null)
                throw new Exception(getString(R.string.exceptionEmptyFileds));

            car.setBranchID(((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            car.setModelCode(((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());
            if (db_manager.updateCar(CarRentConst.carToContentValues(car)))
                Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarMessage), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();
//            new AsyncTask<Object, Object, Boolean>() {
//                @Override
//                protected void onPostExecute(Boolean idResult) {
//                    super.onPostExecute(idResult);
//                    if (idResult)
//                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarMessage), Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                protected Boolean doInBackground(Object... params) {
//                    return db_manager.updateCar(CarRentConst.carToContentValues(car));
//                }
//            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCar() {
        try {
            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null)
                throw new Exception(getString(R.string.exceptionEmptyFileds));

            final Car car = new Car();
            car.setBranchID(((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            car.setModelCode(((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());
            Toast.makeText(getBaseContext(), getString(R.string.textSuccessCreateCarMessage) + db_manager.addCar(CarRentConst.carToContentValues(car)), Toast.LENGTH_SHORT).show();
            resetEditText();

//            new AsyncTask<Object, Object, Integer>() {
//                @Override
//                protected void onPostExecute(Integer idResult) {
//                    super.onPostExecute(idResult);
//                    resetEditText();
//                    Toast.makeText(getBaseContext(), getString(R.string.textSuccessCreateCarMessage) + idResult, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                protected Integer doInBackground(Object... params) {
//                    return db_manager.addCar(CarRentConst.carToContentValues(car));
//                }
//            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFiledCreateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        branchesSpinner = (Spinner) findViewById(R.id.branchesSpinner);
        carModelsSpinner = (Spinner) findViewById(R.id.carModelsSpinner);
        button = (Button) findViewById(R.id.createButton);

        button.setOnClickListener(this);
        carModelsSpinner.setAdapter(new ArrayAdapter<CarModel>(this, R.layout.spinner_view, carModels) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) carModels.get(position).getModelCode()).toString());
                nameTextView.setText(carModels.get(position).getModelName());


                return convertView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) carModels.get(position).getModelCode()).toString());
                String s = carModels.get(position).getModelName();
                nameTextView.setText(s);


                return convertView;
            }
        });
        branchesSpinner.setAdapter(new ArrayAdapter<Branch>(this, R.layout.spinner_view, branches) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) branches.get(position).getBranchID()).toString());
                String s = branches.get(position).getBranchName();
                nameTextView.setText(s);


                return convertView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) branches.get(position).getBranchID()).toString());
                String s = branches.get(position).getBranchName();
                nameTextView.setText(s);


                return convertView;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            if (carID == -1) addCar();
            else updateCar();
        }
    }


}
