package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.entities.CarType;
import lugassi.wallach.android5778_2638_6575.model.entities.Company;
import lugassi.wallach.android5778_2638_6575.model.entities.EngineCapacity;

public class AddCarModel extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private EditText nameEditText;
    private Spinner companySpinner;
    private Spinner engineCapacitySpinner;
    private EditText seatsEditText;
    private Spinner carTypeSpinner;
    //    private Spinner colorSpinner;
    private EditText maxGasEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_model);
        db_manager = DBManagerFactory.getManager();
        findViews();
        resetInput();
    }

    private void findViews() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        companySpinner = (Spinner) findViewById(R.id.companySpinner);
        engineCapacitySpinner = (Spinner) findViewById(R.id.engineCapacitySpinner);
        seatsEditText = (EditText) findViewById(R.id.seatsEditText);
        carTypeSpinner = (Spinner) findViewById(R.id.carTypeSpinner);
        //     colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        maxGasEditText = (EditText) findViewById(R.id.maxGasEditText);
        addButton = (Button) findViewById(R.id.addButton);

        companySpinner.setAdapter(new ArrayAdapter<Company>(this, android.R.layout.simple_spinner_item, Company.values()));
        engineCapacitySpinner.setAdapter(new ArrayAdapter<EngineCapacity>(this, android.R.layout.simple_spinner_item, EngineCapacity.values()));
        carTypeSpinner.setAdapter(new ArrayAdapter<CarType>(this, android.R.layout.simple_spinner_item, CarType.values()));

        addButton.setOnClickListener(this);
    }

    private void resetInput() {
        nameEditText.setText("");
        seatsEditText.setText("");
        maxGasEditText.setText("");
        companySpinner.setSelection(-1);
        engineCapacitySpinner.setSelection(-1);
 //       colorSpinner.setSelection(-1);
        carTypeSpinner.setSelection(-1);
    }

    private boolean checkValues() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            nameEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }

        if (TextUtils.isEmpty(seatsEditText.getText().toString())) {
            seatsEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(maxGasEditText.getText().toString())) {
            maxGasEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
//
//        if (Integer.parseInt(seatsEditText.getText().toString()) < 2) {
//            seatsEditText.setError(String.valueOf(R.string.exceptionInvalidValue));
//            return false;
//        }
//        if (Integer.parseInt(maxGasEditText.getText().toString()) < 30) {
//            maxGasEditText.setError(String.valueOf(R.string.exceptionInvalidValue));
//            return false;
//        }
        if (companySpinner.getSelectedItem() == null ||
                engineCapacitySpinner.getSelectedItem() == null ||
               // colorSpinner.getSelectedItem() == null ||
                carTypeSpinner.getSelectedItem() == null)
            return false;
        return true;
    }


    private void addCarModel() {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(CarRentConst.CarModelConst.MODEL_NAME, nameEditText.getText().toString());
            contentValues.put(CarRentConst.CarModelConst.COMPANY, ((Company) companySpinner.getSelectedItem()).name());
            contentValues.put(CarRentConst.CarModelConst.ENGINE_CAPACITY, ((EngineCapacity) engineCapacitySpinner.getSelectedItem()).name());
            contentValues.put(CarRentConst.CarModelConst.CAR_TYPE, ((CarType) carTypeSpinner.getSelectedItem()).name());
          //  contentValues.put(CarRentConst.CarModelConst.COLOR, ((Color) colorSpinner.getSelectedItem()).toString());
            int seats = Integer.parseInt(seatsEditText.getText().toString());
            int maxGasTank = Integer.parseInt(maxGasEditText.getText().toString());
            contentValues.put(CarRentConst.CarModelConst.SEATS, seats);
            contentValues.put(CarRentConst.CarModelConst.MAX_GAS_TANK, maxGasTank);

            if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                nameEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
                return;
            }

            new AsyncTask<Object, Object, Integer>() {
                @Override
                protected void onPostExecute(Integer idResult) {
                    super.onPostExecute(idResult);
                    resetInput();
                    Toast.makeText(getBaseContext(), "Add Car Model id: " + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Integer doInBackground(Object... params) {
                    return db_manager.addCarModel(contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Add failed!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addButton) {
            addCarModel();
        }
    }


}
