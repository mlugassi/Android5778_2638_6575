package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_List;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.ListsDataSource;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;

public class AddBranch extends Activity {

    private DB_manager db_manager;
    private EditText branchNameEditText;
    private EditText cityEditText;
    private EditText addressEditText;
    private EditText parkingSpaceEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        db_manager =  new DB_List();
        findViews();
    }



    private void findViews() {
        branchNameEditText = (EditText)findViewById( R.id.branchNameEditText );
        cityEditText = (EditText)findViewById( R.id.cityEditText );
        addressEditText = (EditText)findViewById( R.id.addressEditText );
        parkingSpaceEditText = (EditText)findViewById( R.id.parkingSpaceEditText );
        addButton = (Button)findViewById( R.id.addButton );

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();
            }
        });
    }


    private void addBranch() {
        final ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(CarRentConst.BranchConst.BRANCH_NAME , branchNameEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.ADDRESS , addressEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.CITY, this.cityEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.ACTUAL_PARKING_SPACE, 0);
            int  maxParkingSpace = Integer.parseInt(parkingSpaceEditText.getText().toString());
            contentValues.put(CarRentConst.BranchConst.MAX_PARKING_SPACE, maxParkingSpace);
            db_manager.addBranch(contentValues);
            Toast.makeText(getBaseContext(), "insert Branch id: " + Branch.getBranchIDSerializer() , Toast.LENGTH_LONG).show();

//            new AsyncTask<Void, Void, Long>() {
//                @Override
//                protected void onPostExecute(Long idResult) {
//                    super.onPostExecute(idResult);
//                    if (idResult > 0)
//                        Toast.makeText(getBaseContext(), "insert id: " + idResult, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                protected Long doInBackground(Void... params) {
//                    return DBManagerFactory.getManager().addLecturer(contentValues);
//                }
//            }.execute();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "insert failed!\n" + e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }
}
