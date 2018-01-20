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
import java.util.concurrent.ExecutionException;

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
        try {
            branches = new AsyncTask<Object, Object, ArrayList<Branch>>() {
                @Override
                protected ArrayList<Branch> doInBackground(Object... params) {
                    try {
                        return db_manager.getBranches();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }.execute().get();

            carModels = new AsyncTask<Object, Object, ArrayList<CarModel>>() {
                @Override
                protected ArrayList<CarModel> doInBackground(Object... params) {
                    try {
                        return db_manager.getCarModels();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
            new AsyncTask<Integer, Object, Car>() {
                @Override
                protected void onPostExecute(Car o) {
                    if (o == null) return;
                    car = o;
                    // branchesSpinner.setSelection(db_manager.getBranches().indexOf()car.getBranchID());
                    // carModelsSpinner.setSelection(car.getModelCode());
                    button.setText(getString(R.string.buttonUpdate));
                }

                @Override
                protected Car doInBackground(Integer... params) {
                    try {
                        return db_manager.getCar(carID);
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }.execute(carID);
        }
    }

    private void updateCar() {
        try {
            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null)
                throw new Exception(getString(R.string.exceptionEmptyFileds));

            car.setBranchID(((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            car.setModelCode(((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());

            new AsyncTask<Car, Object, Boolean>() {
                @Override
                protected void onPostExecute(Boolean idResult) {
                    if (idResult)
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarMessage), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();

                }

                @Override
                protected Boolean doInBackground(Car... params) {
                    try {
                        return db_manager.updateCar(CarRentConst.carToContentValues(params[0]));
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }.execute(car);

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCar() {
        try {
            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null)
                throw new Exception(getString(R.string.exceptionEmptyFileds));

            Car car = new Car();
            car.setBranchID(((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            car.setModelCode(((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());

            new AsyncTask<Car, Object, Integer>() {
                @Override
                protected void onPostExecute(Integer idResult) {
                    if (idResult > 0) {
                        resetEditText();
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessCreateCarMessage) + idResult, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getBaseContext(), getString(R.string.textFiledCreateMessage), Toast.LENGTH_SHORT).show();

                }

                @Override
                protected Integer doInBackground(Car... params) {
                    try {
                        return db_manager.addCar(CarRentConst.carToContentValues(params[0]));
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return -1;
                    }
                }
            }.execute(car);

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
