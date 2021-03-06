package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.BranchConst;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;

public class AddBranch extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private EditText nameEditText;
    private EditText cityEditText;
    private EditText addressEditText;
    private EditText parkingSpaceEditText;
    private Button button;
    private Branch branch;
    private int branchID;
    private String errorMessage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_branch);
        db_manager = DBManagerFactory.getManager();
        findViews();
        setBranchValues();
    }

    // set values from res.values and get the elements for this activity and be able to get event from button press
    private void findViews() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        parkingSpaceEditText = (EditText) findViewById(R.id.parkingSpaceEditText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    // set branch details for option to be called to update specific branch
    void setBranchValues() {
        branchID = getIntent().getIntExtra(BranchConst.BRANCH_ID, -1);
        if (branchID >= 0) {

            new AsyncTask<Integer, Object, Branch>() {
                @Override
                protected void onPostExecute(Branch o) {
                    if (errorMessage != null) {
                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                        errorMessage = null;
                    }
                    if (o == null) return;
                    branch = o;
                    nameEditText.setText(branch.getBranchName());
                    cityEditText.setText(branch.getCity());
                    addressEditText.setText(branch.getAddress());
                    parkingSpaceEditText.setText(((Integer) branch.getMaxParkingSpace()).toString());
                    button.setText(getString(R.string.buttonUpdate));
                }

                @Override
                protected Branch doInBackground(Integer... params) {
                    try {
                        return db_manager.getBranch(branchID);
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                        return null;
                    }
                }
            }.execute(branchID);


        } else resetInput();
    }

    // clear fields from input text by user
    private void resetInput() {
        nameEditText.setText("");
        addressEditText.setText("");
        cityEditText.setText("");
        parkingSpaceEditText.setText("");
        branch = null;
    }

    // check that values inserted are not error (meanwhile just basic non empty)
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
        if (!tryParseInt(parkingSpaceEditText.getText().toString())) {
            parkingSpaceEditText.setError(getString(R.string.exceptionNumberFileds));
            return false;
        }
        if (Integer.parseInt(parkingSpaceEditText.getText().toString()) < 29) {
            parkingSpaceEditText.setError(getString(R.string.exceptionLessFileds) + " " + 30);
            return false;
        }
        return true;
    }

    private void updateBranch() {
        try {
            if (!checkValues())
                return;
            int maxParkingSpace = Integer.parseInt(parkingSpaceEditText.getText().toString());

            branch.setAddress(addressEditText.getText().toString());
            branch.setMaxParkingSpace(maxParkingSpace);
            branch.setCity(cityEditText.getText().toString());
            branch.setBranchName(nameEditText.getText().toString());


            /// update the branch
            new AsyncTask<Branch, Object, String>() {
                @Override
                protected void onPostExecute(String idResult) {
                    if (tryParseInt(idResult) && Integer.parseInt(idResult) > 0)
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessUpdateBranchMessage), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + idResult, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected String doInBackground(Branch... params) {
                    return db_manager.updateBranch(CarRentConst.branchToContentValues(params[0]));

                }
            }.execute(branch);

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getString(R.string.textFailedUpdateMessage) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addBranch() {
        try {
            if (!checkValues())
                return;
            int maxParkingSpace = Integer.parseInt(parkingSpaceEditText.getText().toString()); // needed for option that not number inserted

            Branch branch = new Branch();
            branch.setAddress(addressEditText.getText().toString());
            branch.setMaxParkingSpace(maxParkingSpace);
            branch.setCity(cityEditText.getText().toString());
            branch.setBranchName(nameEditText.getText().toString());

            /// adding branch
            new AsyncTask<Branch, Object, String>() {
                @Override
                protected void onPostExecute(String idResult) {
                    if (tryParseInt(idResult) && Integer.parseInt(idResult) > 0) {
                        resetInput();
                        Toast.makeText(getBaseContext(), getString(R.string.textSuccessAddBranchMessage) + idResult, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getBaseContext(), getString(R.string.textFiledAddMessage), Toast.LENGTH_SHORT).show();
                }

                @Override
                protected String doInBackground(Branch... params) {
                    return db_manager.addBranch(CarRentConst.branchToContentValues(params[0]));
                }
            }.execute(branch);
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

    // implemented because implements onClickListener interface
    @Override
    public void onClick(View v) {
        if (v == button) {
            if (branchID == -1) addBranch();
            else updateBranch();
        }
    }
}
