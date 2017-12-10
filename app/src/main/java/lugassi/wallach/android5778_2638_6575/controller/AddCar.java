package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        position = getIntent().getIntExtra(CarRentConst.POSITION, -1);
        if (position >= 0) {
            car = db_manager.getCars().get(position);
            //  branchesSpinner.setSelection(db_manager.getBranches().indexOf()car.getBranchID());
            // carModelsSpinner.setSelection(car.getModelCode());
            button.setText(getString(R.string.buttonUpdate));
        }
    }

    private void updateCar() {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(CarRentConst.CarConst.CAR_ID, car.getCarID());
            contentValues.put(CarRentConst.CarConst.MILEAGE, car.getMileage());
            contentValues.put(CarRentConst.CarConst.RESERVATIONS, car.getReservations());
            contentValues.put(CarRentConst.CarConst.BRANCH_ID, ((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            contentValues.put(CarRentConst.CarConst.MODEL_CODE, ((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());

            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null) {
                throw new Exception(getString(R.string.exceptionEmptyFileds));
            }
            new AsyncTask<Object, Object, Boolean>() {
                @Override
                protected void onPostExecute(Boolean idResult) {
                    super.onPostExecute(idResult);
                    if (idResult)
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarMessage), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();

                }

                @Override
                protected Boolean doInBackground(Object... params) {
                    return db_manager.updateCar(car.getCarID(), contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCar() {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(CarRentConst.CarConst.BRANCH_ID, ((Branch) branchesSpinner.getSelectedItem()).getBranchID());
            contentValues.put(CarRentConst.CarConst.MODEL_CODE, ((CarModel) carModelsSpinner.getSelectedItem()).getModelCode());

            if (branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null) {
                throw new Exception(getString(R.string.exceptionEmptyFileds));
            }
            new AsyncTask<Object, Object, Integer>() {
                @Override
                protected void onPostExecute(Integer idResult) {
                    super.onPostExecute(idResult);
                    resetEditText();
                    Toast.makeText(getBaseContext(), getString(R.string.textSuccessCreateCarMessage) + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Integer doInBackground(Object... params) {
                    return db_manager.addCar(contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFiledCreateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        branchesSpinner = (Spinner) findViewById(R.id.branchesSpinner);
        carModelsSpinner = (Spinner) findViewById(R.id.carModelsSpinner);
        button = (Button) findViewById(R.id.createButton);

        button.setOnClickListener(this);
        carModelsSpinner.setAdapter(new ArrayAdapter<CarModel>(this, R.layout.item_list_view, carModels) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.item_list_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) carModels.get(position).getModelCode()).toString());
                nameTextView.setText(carModels.get(position).getModelName());


                return convertView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.item_comp_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) carModels.get(position).getModelCode()).toString());
                String s = carModels.get(position).getModelName();
                nameTextView.setText(s);


                return convertView;
            }
        });
        branchesSpinner.setAdapter(new ArrayAdapter<Branch>(this, R.layout.item_comp_view, branches) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.item_list_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) branches.get(position).getBranchID()).toString());
                String s = branches.get(position).getBranchName();
                nameTextView.setText(s);


                return convertView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(AddCar.this, R.layout.item_comp_view, null);


                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
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
            if (position == -1) addCar();
            else updateCar();
        }
    }


}
