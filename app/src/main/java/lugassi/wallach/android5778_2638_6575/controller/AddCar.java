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
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;

public class AddCar extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private ArrayList<Branch> branches;
    private ArrayList<CarModel> carModels;
    private Spinner branchesSpinner;
    private Spinner carModelsSpinner;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        db_manager = DBManagerFactory.getManager();
        branches = db_manager.getBranches();
        carModels = db_manager.getCarModels();

        findViews();

    }

    private void resetEditText() {
        branchesSpinner.setSelection(-1);
        carModelsSpinner.setSelection(-1);
    }

    private void addCar() {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(CarRentConst.CarConst.BRANCH_ID,((Branch)branchesSpinner.getSelectedItem()).getBranchID());
            contentValues.put(CarRentConst.CarConst.MODEL_CODE, ((CarModel)carModelsSpinner.getSelectedItem()).getModelCode());

            if(branchesSpinner.getSelectedItem() == null || carModelsSpinner.getSelectedItem() == null ) {
                throw new Exception(String.valueOf(R.string.exceptionEmptyFileds));
            }
            new AsyncTask<Object, Object, Integer>() {
                @Override
                protected void onPostExecute(Integer idResult) {
                    super.onPostExecute(idResult);
                    resetEditText();
                    Toast.makeText(getBaseContext(), "Create Car id: " + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Integer doInBackground(Object... params) {
                    return db_manager.addCar(contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Create failed!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void findViews() {
        branchesSpinner = (Spinner) findViewById(R.id.branchesSpinner);
        carModelsSpinner = (Spinner) findViewById(R.id.carModelsSpinner);
        createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(this);
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
        if (v == createButton) {
            addCar();
        }
    }


}
