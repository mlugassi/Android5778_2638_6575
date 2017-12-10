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
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;

public class AddBranch extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private EditText nameEditText;
    private EditText cityEditText;
    private EditText addressEditText;
    private EditText parkingSpaceEditText;
    private Button button;
    private Branch branch;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        db_manager = new DB_List();
        findViews();
        setBranchValues();
    }

    private void findViews() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        parkingSpaceEditText = (EditText) findViewById(R.id.parkingSpaceEditText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    void setBranchValues() {
        position = getIntent().getIntExtra(CarRentConst.POSITION, -1);
        if (position >= 0) {
            branch = db_manager.getBranches().get(position);
            nameEditText.setText(branch.getBranchName());
            cityEditText.setText(branch.getCity());
            addressEditText.setText(branch.getAddress());
            parkingSpaceEditText.setText(((Integer) branch.getMaxParkingSpace()).toString());
            button.setText(getString(R.string.buttonUpdate));
        } else resetInput();
    }

    private void resetInput() {
        nameEditText.setText("");
        addressEditText.setText("");
        cityEditText.setText("");
        parkingSpaceEditText.setText("");
        branch = null;
    }

    private boolean checkValues() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            nameEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(cityEditText.getText().toString())) {
            cityEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(parkingSpaceEditText.getText().toString())) {
            parkingSpaceEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        return true;
    }

    private void updateBranch() {
        final ContentValues contentValues = new ContentValues();
        try {
            if (!checkValues())
                return;
            contentValues.put(CarRentConst.BranchConst.BRANCH_ID, branch.getBranchID());
            contentValues.put(CarRentConst.BranchConst.ACTUAL_PARKING_SPACE, branch.getActualParkingSpace());
            contentValues.put(CarRentConst.BranchConst.BRANCH_NAME, nameEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.ADDRESS, addressEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.CITY, cityEditText.getText().toString());
            int maxParkingSpace = Integer.parseInt(parkingSpaceEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.MAX_PARKING_SPACE, maxParkingSpace);

            new AsyncTask<Object, Object, Boolean>() {
                @Override
                protected void onPostExecute(Boolean idResult) {
                    super.onPostExecute(idResult);
                    if (idResult)
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateBranchMessage), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage), Toast.LENGTH_SHORT).show();

                }

                @Override
                protected Boolean doInBackground(Object... params) {
                    return db_manager.updateBranch(branch.getBranchID(), contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                    Toast.makeText(getBaseContext(), getString(R.string.textSuccessAddBranchMessage) + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Integer doInBackground(Object... params) {
                    return db_manager.addBranch(contentValues);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFiledAddMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            if (position == -1) addBranch();
            else updateBranch();
        }
    }
}
