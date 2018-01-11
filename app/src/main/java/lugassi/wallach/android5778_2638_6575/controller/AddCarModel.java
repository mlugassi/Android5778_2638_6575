package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.ContentValues;
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
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
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
    private EditText maxGasEditText;
    private Button button;
    private CarModel carModel;
    private int modelCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_model);
        db_manager = DBManagerFactory.getManager();
        findViews();
        setCarModelValues();
    }

    private void findViews() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        companySpinner = (Spinner) findViewById(R.id.companySpinner);
        engineCapacitySpinner = (Spinner) findViewById(R.id.engineCapacitySpinner);
        seatsEditText = (EditText) findViewById(R.id.seatsEditText);
        carTypeSpinner = (Spinner) findViewById(R.id.carTypeSpinner);
        //     colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        maxGasEditText = (EditText) findViewById(R.id.maxGasEditText);
        button = (Button) findViewById(R.id.button);

        companySpinner.setAdapter(new ArrayAdapter<Company>(this, android.R.layout.simple_spinner_item, Company.values()));
        engineCapacitySpinner.setAdapter(new ArrayAdapter<EngineCapacity>(this, android.R.layout.simple_spinner_item, EngineCapacity.values()));
        carTypeSpinner.setAdapter(new ArrayAdapter<CarType>(this, android.R.layout.simple_spinner_item, CarType.values()));

        button.setOnClickListener(this);
    }

    void setCarModelValues() {
        modelCode = getIntent().getIntExtra(CarRentConst.CarModelConst.MODEL_CODE, -1);
        if (modelCode >= 0) {
            carModel = db_manager.getCarModel(modelCode);
            nameEditText.setText(carModel.getModelName());
            companySpinner.setSelection(carModel.getCompany().ordinal());
            engineCapacitySpinner.setSelection(carModel.getEngineCapacity().ordinal());
            seatsEditText.setText(((Integer) carModel.getSeats()).toString());
            carTypeSpinner.setSelection(carModel.getCarType().ordinal());
            maxGasEditText.setText(((Integer) carModel.getMaxGasTank()).toString());

            button.setText(getString(R.string.buttonUpdate));
        } else resetInput();
    }

    private void resetInput() {
        nameEditText.setText("");
        seatsEditText.setText("");
        maxGasEditText.setText("");
        companySpinner.setSelection(-1);
        engineCapacitySpinner.setSelection(-1);
        //       colorSpinner.setSelection(-1);
        carTypeSpinner.setSelection(-1);
        carModel = null;
    }

    private boolean checkValues() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            nameEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }

        if (TextUtils.isEmpty(seatsEditText.getText().toString())) {
            seatsEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(maxGasEditText.getText().toString())) {
            maxGasEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
//
//        if (Integer.parseInt(seatsEditText.getText().toString()) < 2) {
//            seatsEditText.setError(getString(R.string.exceptionInvalidValue));
//            return false;
//        }
//        if (Integer.parseInt(maxGasEditText.getText().toString()) < 30) {
//            maxGasEditText.setError(getString(R.string.exceptionInvalidValue));
//            return false;
//        }
        if (companySpinner.getSelectedItem() == null ||
                engineCapacitySpinner.getSelectedItem() == null ||
                // colorSpinner.getSelectedItem() == null ||
                carTypeSpinner.getSelectedItem() == null)
            return false;
        return true;
    }

    private void updateCarModel() {
        try {
            if (!checkValues()) return;

            int seats = Integer.parseInt(seatsEditText.getText().toString());
            int maxGasTank = Integer.parseInt(maxGasEditText.getText().toString());

            carModel.setModelName(nameEditText.getText().toString());
            carModel.setCompany((Company) companySpinner.getSelectedItem());
            carModel.setCarType((CarType) carTypeSpinner.getSelectedItem());
            carModel.setEngineCapacity((EngineCapacity) engineCapacitySpinner.getSelectedItem());
            carModel.setMaxGasTank(maxGasTank);
            carModel.setSeats(seats);
            if (db_manager.updateCarModel(CarRentConst.carModelToContentValues(carModel)))
                Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarModelMessage), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();
//            new AsyncTask<Object, Object, Boolean>() {
//                @Override
//                protected void onPostExecute(Boolean idResult) {
//                    super.onPostExecute(idResult);
//                    if (idResult)
//                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateCarModelMessage), Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                protected Boolean doInBackground(Object... params) {
//                    return db_manager.updateCarModel(CarRentConst.carModelToContentValues(carModel));
//                }
//            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCarModel() {
        try {
            if (!checkValues()) return;

            int seats = Integer.parseInt(seatsEditText.getText().toString());
            int maxGasTank = Integer.parseInt(maxGasEditText.getText().toString());

            final CarModel carModel = new CarModel();
            carModel.setModelName(nameEditText.getText().toString());
            carModel.setCompany((Company) companySpinner.getSelectedItem());
            carModel.setCarType((CarType) carTypeSpinner.getSelectedItem());
            carModel.setEngineCapacity((EngineCapacity) engineCapacitySpinner.getSelectedItem());
            carModel.setMaxGasTank(maxGasTank);
            carModel.setSeats(seats);
            Toast.makeText(getBaseContext(), getString(R.string.textSuccessAddCarModelMessage) + db_manager.addCarModel(CarRentConst.carModelToContentValues(carModel)), Toast.LENGTH_SHORT).show();
            resetInput();

//            new AsyncTask<Object, Object, Integer>() {
//                @Override
//                protected void onPostExecute(Integer idResult) {
//                    super.onPostExecute(idResult);
//                    resetInput();
//                    Toast.makeText(getBaseContext(), getString(R.string.textSuccessAddCarModelMessage) + idResult, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                protected Integer doInBackground(Object... params) {
//                    return db_manager.addCarModel(CarRentConst.carModelToContentValues(carModel));
//                }
//            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFiledAddMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            if (modelCode == -1)
                addCarModel();
            else updateCarModel();
        }
    }


}
