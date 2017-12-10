package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_List;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;

public class DataLists extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {

    DB_manager db_manager;
    private ListView dataListView;
    private Spinner dataSpinner;
    private String data[] = {"Branches" , "Cars" , "Car Models"};
    private MyListAdapter branchesAdapter;
    private MyListAdapter carsAdapter;
    private MyListAdapter carModelsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_lists);
        db_manager = new DB_List();
        branchesAdapter = new MyListAdapter(DataLists.this, db_manager.getBranches()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(DataLists.this, R.layout.item_comp_view, null);

                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) db_manager.getBranches().get(position).getBranchID()).toString());
                nameTextView.setText(db_manager.getBranches().get(position).getBranchName());

                return convertView;
            }
        };
        carsAdapter = new MyListAdapter(DataLists.this, db_manager.getCars()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(DataLists.this, R.layout.item_comp_view, null);

                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) db_manager.getCars().get(position).getBranchID()).toString());
                nameTextView.setText("");

                return convertView;
            }
        };
        carModelsAdapter = new MyListAdapter(DataLists.this, db_manager.getCarModels()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(DataLists.this, R.layout.item_comp_view, null);

                TextView idTextView = (TextView) convertView.findViewById(R.id.itemIdEditText);
                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameEditText);

                idTextView.setText(((Integer) db_manager.getCarModels().get(position).getModelCode()).toString());
                nameTextView.setText(db_manager.getCarModels().get(position).getModelName());

                return convertView;
            }
        };
        findViews();
        resetInput();
    }

    @Override
    protected void onResume() {
        super.onResume();
        branchesAdapter.setData(db_manager.getBranches());
        branchesAdapter.notifyDataSetChanged();
        carModelsAdapter.setData(db_manager.getCarModels());
        carModelsAdapter.notifyDataSetChanged();
        carsAdapter.setData(db_manager.getCars());
        carsAdapter.notifyDataSetChanged();
    }

    private void findViews() {
        dataListView = (ListView) findViewById(R.id.dataListView);
        dataSpinner = (Spinner) findViewById(R.id.dataSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DataLists.this, android.R.layout.simple_spinner_item, data);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dataSpinner.setAdapter(spinnerAdapter);
        dataSpinner.setOnItemSelectedListener(this);
        dataListView.setOnItemLongClickListener(this);
    }

    private void resetInput() {
        dataSpinner.setSelection(-1);
        dataListView.setAdapter(null);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            dataListView.setAdapter((ListAdapter) branchesAdapter);
        else if (position == 1)
            dataListView.setAdapter((ListAdapter) carsAdapter);
        else if (position == 2)
            dataListView.setAdapter((ListAdapter) carModelsAdapter);
        else dataListView.setAdapter(null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        dataListView.setAdapter(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.textDialogTitle));

        builder.setMessage(getString(R.string.textDialogMessage));
        builder.setPositiveButton(getString(R.string.buttonUpdate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Class nxtActivity = null;
                switch (dataSpinner.getSelectedItemPosition()) {
                    case 0:
                        nxtActivity = AddBranch.class;
                        break;
                    case 1:
                        nxtActivity = AddCar.class;
                        break;
                    case 2:
                        nxtActivity = AddCarModel.class;
                        break;
                    default:
                        return;
                }
                Intent myIntent = new Intent(DataLists.this, nxtActivity);
                myIntent.putExtra(CarRentConst.POSITION, position);
                DataLists.this.startActivity(myIntent);
            }
        });
        builder.setNegativeButton(getString(R.string.buttonRemove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (dataSpinner.getSelectedItemPosition()) {
                    case 0:
                        db_manager.removeBranch(((Branch) dataListView.getItemAtPosition(position)).getBranchID());
                        break;
                    case 1:
                        db_manager.removeCar(((Car) dataListView.getItemAtPosition(position)).getCarID());
                        break;
                    case 2:
                        db_manager.removeCarModel(((CarModel) dataListView.getItemAtPosition(position)).getModelCode());
                        break;
                    default:
                        return;
                }
                branchesAdapter.setData(db_manager.getBranches());
                branchesAdapter.notifyDataSetChanged();
                carModelsAdapter.setData(db_manager.getCarModels());
                carModelsAdapter.notifyDataSetChanged();
                carsAdapter.setData(db_manager.getCars());
                carsAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
        return false;
    }
}
