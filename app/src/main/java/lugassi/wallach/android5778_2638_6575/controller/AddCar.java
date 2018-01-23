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
    private String errorMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_car);

        errorMassage = null;
        db_manager = DBManagerFactory.getManager();
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
                protected void onPostExecute(Car result) {
                    if (errorMassage != null) {
                        Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                        errorMassage = null;
                    }
                    if (result == null || branches == null || carModels == null) return;
                    car = result;
                    branchesSpinner.setSelection(getIndexByBranchID(result.getBranchID()));
                    carModelsSpinner.setVisibility(View.GONE);
                    carModelsSpinner.setSelection(getIndexByModelCode(result.getModelCode()));
                    button.setText(getString(R.string.buttonUpdate));
                }

                @Override
                protected Car doInBackground(Integer... params) {
                    try {
                        return db_manager.getCar(carID);
                    } catch (Exception e) {
                        errorMassage = e.getMessage();
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

            new AsyncTask<Car, Object, String>() {
                @Override
                protected void onPostExecute(String idResult) {
                    if (tryParseInt(idResult) && Integer.parseInt(idResult) > 0)
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarMessage) + "\n" + idResult, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + idResult, Toast.LENGTH_SHORT).show();

                }

                @Override
                protected String doInBackground(Car... params) {
                    return db_manager.updateCar(CarRentConst.carToContentValues(params[0]));
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

            new AsyncTask<Car, Object, String>() {
                @Override
                protected void onPostExecute(String idResult) {
                    if (tryParseInt(idResult) && Integer.parseInt(idResult) > 0) {
                        resetEditText();
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessCreateCarMessage) + "\n" + idResult, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getBaseContext(), getString(R.string.textFiledCreateMessage) + "\n" + idResult, Toast.LENGTH_SHORT).show();

                }

                @Override
                protected String doInBackground(Car... params) {
                    return db_manager.addCar(CarRentConst.carToContentValues(params[0]));

                }
            }.execute(car);

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void findViews() {
        branchesSpinner = (Spinner) findViewById(R.id.branchesSpinner);
        carModelsSpinner = (Spinner) findViewById(R.id.carModelsSpinner);
        button = (Button) findViewById(R.id.createButton);
        new AsyncTask<Object, Object, ArrayList<Branch>>() {
            @Override
            protected void onPostExecute(ArrayList<Branch> result) {
                if (errorMassage != null) {
                    Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                    errorMassage = null;
                }
                branches = result;
                branchesSpinner.setAdapter(new ArrayAdapter<Branch>(AddCar.this, R.layout.spinner_view, branches) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null)
                            convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                        TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                        Branch branch = (Branch) branchesSpinner.getItemAtPosition(position);
                        idTextView.setText(((Integer) branch.getBranchID()).toString());
                        nameTextView.setText(branch.getBranchName());

                        return convertView;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                        TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                        Branch branch = (Branch) branchesSpinner.getItemAtPosition(position);
                        idTextView.setText(((Integer) branch.getBranchID()).toString());
                        nameTextView.setText(branch.getBranchName());

                        return convertView;
                    }
                });

            }

            @Override
            protected ArrayList<Branch> doInBackground(Object... params) {
                try {
                    return db_manager.getBranches();
                } catch (Exception e) {
                    errorMassage = e.getMessage();
                    return new ArrayList<Branch>();
                }
            }
        }.execute();

        new AsyncTask<Object, Object, ArrayList<CarModel>>() {
            @Override
            protected void onPostExecute(ArrayList<CarModel> result) {
                if (errorMassage != null) {
                    Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                    errorMassage = null;
                }
                carModels = result;
                carModelsSpinner.setAdapter(new ArrayAdapter<CarModel>(AddCar.this, R.layout.spinner_view, carModels) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null)
                            convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                        TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                        CarModel carModel = (CarModel) carModelsSpinner.getItemAtPosition(position);
                        idTextView.setText(carModel.getCompany().name());
                        nameTextView.setText(carModel.getModelName());


                        return convertView;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(AddCar.this, R.layout.spinner_view, null);


                        TextView idTextView = (TextView) convertView.findViewById(R.id.idEditText);
                        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                        CarModel carModel = (CarModel) carModelsSpinner.getItemAtPosition(position);
                        idTextView.setText(carModel.getCompany().name());
                        nameTextView.setText(carModel.getModelName());

                        return convertView;
                    }
                });
            }

            @Override
            protected ArrayList<CarModel> doInBackground(Object... params) {
                try {
                    return db_manager.getCarModels();
                } catch (Exception e) {
                    errorMassage = e.getMessage();
                    return new ArrayList<CarModel>();
                }
            }
        }.execute();

        button.setOnClickListener(this);
    }

    private int getIndexByBranchID(int branchID) {
        if (branches == null) return -1;
        for (Branch branch : branches)
            if (branchID == branch.getBranchID())
                return branches.indexOf(branch);
        return -1;
    }

    private int getIndexByModelCode(int modelCode) {
        if (carModels == null) return -1;
        for (CarModel carModel : carModels)
            if (modelCode == carModel.getModelCode())
                return carModels.indexOf(carModel);
        return -1;
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            if (carID == -1) addCar();
            else updateCar();
        }
    }


}
