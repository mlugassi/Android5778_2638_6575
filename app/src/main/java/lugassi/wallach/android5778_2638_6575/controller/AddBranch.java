package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_List;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;

public class AddBranch extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private EditText nameEditText;
    private EditText cityEditText;
    private EditText addressEditText;
    private EditText parkingSpaceEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        db_manager = new DB_List();
        findViews();
    }

    private void findViews() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        parkingSpaceEditText = (EditText) findViewById(R.id.parkingSpaceEditText);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    private void resetInput() {
        nameEditText.setText("");
        addressEditText.setText("");
        cityEditText.setText("");
        parkingSpaceEditText.setText("");
    }

    private boolean checkValues() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            nameEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(cityEditText.getText().toString())) {
            cityEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(parkingSpaceEditText.getText().toString())) {
            parkingSpaceEditText.setError(String.valueOf(R.string.exceptionEmptyFileds));
            return false;
        }
        return true;
    }

    private void addBranch() {
        final ContentValues contentValues = new ContentValues();
        try {
            if (!checkValues())
                return;
            contentValues.put(CarRentConst.BranchConst.BRANCH_NAME, nameEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.ADDRESS, addressEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.CITY, cityEditText.getText().toString());
            int maxParkingSpace = Integer.parseInt(parkingSpaceEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.MAX_PARKING_SPACE, maxParkingSpace);

            new AsyncTask<Object, Object, Integer>() {
                @Override
                protected void onPostExecute(Integer idResult) {
                    super.onPostExecute(idResult);
                    resetInput();
                    Toast.makeText(getBaseContext(), "insert Branch id: " + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Integer doInBackground(Object... params) {
                    return db_manager.addBranch(contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "insert failed!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addButton) {
            addBranch();
        }
    }
}
