package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.MyListAdapter;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;

public class DataLists extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {

    DB_manager db_manager;
    private ListView dataListView;
    private Spinner dataSpinner;
    private String[] data;
    private MyListAdapter branchesAdapter;
    private MyListAdapter carsAdapter;
    private MyListAdapter carModelsAdapter;
    private boolean onCreate = false;
    private String errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_data_lists);
        onCreate = true;
        errorMessage = null;
        setDataLists();
        db_manager = DBManagerFactory.getManager();


        /// create branches list
        new AsyncTask<Object, Object, ArrayList<Branch>>() {
            @Override
            protected void onPostExecute(final ArrayList<Branch> branches) {
                if (errorMessage != null) {
                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                    errorMessage = null;
                }
                branchesAdapter = new MyListAdapter(DataLists.this, branches) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(DataLists.this, R.layout.branch_list_view, null);

                        TextView nameAnIdTextView = (TextView) convertView.findViewById(R.id.nameAndIdEditText);
                        TextView addressTextView = (TextView) convertView.findViewById(R.id.addressEditText);

                        Branch branch = (Branch) branches.get(position);
                        nameAnIdTextView.setText(branch.getBranchName());
                        addressTextView.setText(branch.getAddress());

                        return convertView;
                    }
                };
                branchesAdapter.notifyDataSetChanged();
                dataListView.setAdapter(branchesAdapter);
            }

            @Override
            protected ArrayList<Branch> doInBackground(Object... params) {
                try {
                    return db_manager.getBranches();
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    return new ArrayList<Branch>();
                }
            }
        }.execute();


        /// craeate cars list
        new AsyncTask<Object, Object, ArrayList<Car>>() {
            @Override
            protected void onPostExecute(final ArrayList<Car> cars) {
                if (errorMessage != null) {
                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                    errorMessage = null;
                }
                carsAdapter = new MyListAdapter<Car>(DataLists.this, cars) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(DataLists.this, R.layout.car_list_view, null);

                        TextView carIdEditText = (TextView) convertView.findViewById(R.id.carIdEditText);
                        final TextView modelNameAndCompanyEditText = (TextView) convertView.findViewById(R.id.modelNameAndCompanyEditText);
                        final TextView branchNameEditText = (TextView) convertView.findViewById(R.id.branchNameEditText);


                        final Car car = (Car) cars.get(position);
                        carIdEditText.setText(((Integer) car.getCarID()).toString());
                        new AsyncTask<Integer, Object, String>() {
                            @Override
                            protected void onPostExecute(String branchName) {
                                if (errorMessage != null) {
                                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                    errorMessage = null;
                                }

                                if (branchName != null)
                                    branchNameEditText.setText(branchName);
                            }

                            @Override
                            protected String doInBackground(Integer... params) {
                                try {
                                    return db_manager.getBranch(params[0]).getBranchName();
                                } catch (Exception e) {
                                    errorMessage = e.getMessage();
                                    return null;
                                }
                            }
                        }.execute(car.getBranchID());

                        new AsyncTask<Integer, Object, CarModel>() {
                            @Override
                            protected void onPostExecute(final CarModel carModel) {
                                if (errorMessage != null) {
                                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                    errorMessage = null;
                                }
                                if (carModel == null) return;
                                modelNameAndCompanyEditText.setText(carModel.getModelName() + ", " + carModel.getCompany().name());
                            }

                            @Override
                            protected CarModel doInBackground(Integer... params) {
                                try {
                                    return db_manager.getCarModel(params[0]);
                                } catch (Exception e) {
                                    errorMessage = e.getMessage();
                                    return null;
                                }
                            }
                        }.execute(car.getModelCode());


                        return convertView;
                    }

                };
                carsAdapter.notifyDataSetChanged();

            }

            @Override
            protected ArrayList<Car> doInBackground(Object... params) {
                try {
                    return db_manager.getCars();
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    return new ArrayList<Car>();
                }
            }
        }.execute();


        /// create models list
        new AsyncTask<Object, Object, ArrayList<CarModel>>() {
            @Override
            protected void onPostExecute(final ArrayList<CarModel> carModels) {
                if (errorMessage != null) {
                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                    errorMessage = null;
                }
                carModelsAdapter = new MyListAdapter(DataLists.this, carModels) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(DataLists.this, R.layout.car_model_list_view, null);

                        TextView carModelTextView = (TextView) convertView.findViewById(R.id.modelCodeEditText);
                        TextView nameAndCompanyEditText = (TextView) convertView.findViewById(R.id.nameAndCompanyEditText);

                        final CarModel carModel = (CarModel) carModels.get(position);
                        carModelTextView.setText(((Integer) carModel.getModelCode()).toString());
                        nameAndCompanyEditText.setText(carModel.getModelName() + ", " + carModel.getCompany().name());

                        return convertView;
                    }
                };
                carModelsAdapter.notifyDataSetChanged();
            }

            @Override
            protected ArrayList<CarModel> doInBackground(Object... params) {
                try {
                    return db_manager.getCarModels();
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    return new ArrayList<CarModel>();
                }
            }
        }.execute();


        findViews();
        resetInput();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (!onCreate) {  /// when backing from update - update the lists

           /// update branches list
            new AsyncTask<Object, Object, ArrayList<Branch>>() {
                @Override
                protected void onPostExecute(final ArrayList<Branch> branches) {
                    if (errorMessage != null) {
                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                        errorMessage = null;
                    }
                    branchesAdapter.setData(branches);
                    branchesAdapter.notifyDataSetChanged();
                }

                @Override
                protected ArrayList<Branch> doInBackground(Object... params) {
                    try {
                        return db_manager.getBranches();
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                        return new ArrayList<Branch>();
                    }
                }
            }.execute();

            /// update cars list
            new AsyncTask<Object, Object, ArrayList<Car>>() {
                @Override
                protected void onPostExecute(final ArrayList<Car> cars) {
                    if (errorMessage != null) {
                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                        errorMessage = null;
                    }
                    carsAdapter.setData(cars);
                    carsAdapter.notifyDataSetChanged();
                }

                @Override
                protected ArrayList<Car> doInBackground(Object... params) {
                    try {
                        return db_manager.getCars();
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                        return new ArrayList<Car>();
                    }
                }
            }.execute();

            /// update model list
            new AsyncTask<Object, Object, ArrayList<CarModel>>() {
                @Override
                protected void onPostExecute(final ArrayList<CarModel> carModels) {
                    if (errorMessage != null) {
                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                        errorMessage = null;
                    }
                    carModelsAdapter.setData(carModels);
                    carModelsAdapter.notifyDataSetChanged();
                }

                @Override
                protected ArrayList<CarModel> doInBackground(Object... params) {
                    try {
                        return db_manager.getCarModels();
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                        return new ArrayList<CarModel>();
                    }
                }
            }.execute();
        }
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

    /// create the data list spinner list
    void setDataLists() {
        data = new String[3];
        data[0] = getString(R.string.dataBranches);
        data[1] = getString(R.string.dataCars);
        data[2] = getString(R.string.dataCarModels);
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            dataListView.setAdapter(branchesAdapter);
        else if (position == 1) {
            dataListView.setAdapter(carsAdapter);
        } else if (position == 2)
            dataListView.setAdapter(carModelsAdapter);
        else dataListView.setAdapter(null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        dataListView.setAdapter(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


        /// opennig remove or update dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.textDialogTitle));

        builder.setMessage(getString(R.string.textDialogMessage));
        builder.setPositiveButton(getString(R.string.buttonUpdate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Class nxtActivity = null;
                int id = -1;
                String ID = "";
                switch (dataSpinner.getSelectedItemPosition()) {
                    case 0:
                        nxtActivity = AddBranch.class;
                        id = ((Branch) dataListView.getItemAtPosition(position)).getBranchID();
                        ID = CarRentConst.BranchConst.BRANCH_ID;
                        break;
                    case 1:
                        nxtActivity = AddCar.class;
                        id = ((Car) dataListView.getItemAtPosition(position)).getCarID();
                        ID = CarRentConst.CarConst.CAR_ID;
                        break;
                    case 2:
                        nxtActivity = AddCarModel.class;
                        id = ((CarModel) dataListView.getItemAtPosition(position)).getModelCode();
                        ID = CarRentConst.CarModelConst.MODEL_CODE;
                        break;
                    default:
                        return;
                }

                /// open activity in update mode
                Intent myIntent = new Intent(DataLists.this, nxtActivity);
                myIntent.putExtra(ID, id);
                onCreate = false;
                DataLists.this.startActivity(myIntent);
            }
        });
        builder.setNegativeButton(getString(R.string.buttonRemove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    switch (dataSpinner.getSelectedItemPosition()) {
                        case 0:
                            // remove Branch
                            new AsyncTask<Integer, Object, ArrayList<Branch>>() {
                                @Override
                                protected void onPostExecute(ArrayList<Branch> branches) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    branchesAdapter.setData(branches);
                                    branchesAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<Branch> doInBackground(Integer... params) {
                                    try {
                                        String result = db_manager.removeBranch(params[0]);
                                        if (!tryParseInt(result) || Integer.parseInt(result) < 0)
                                            errorMessage = result;
                                        return db_manager.getBranches();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<Branch>();
                                    }
                                }
                            }.execute(((Branch) dataListView.getItemAtPosition(position)).getBranchID());
                            /// update cars if there is change
                            new AsyncTask<Integer, Object, ArrayList<Car>>() {
                                @Override
                                protected void onPostExecute(ArrayList<Car> cars) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    carsAdapter.setData(cars);
                                    carsAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<Car> doInBackground(Integer... params) {
                                    try {
                                        return db_manager.getCars();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<Car>();
                                    }
                                }
                            }.execute();

                            /// update models if there is change
                            new AsyncTask<Integer, Object, ArrayList<CarModel>>() {
                                @Override
                                protected void onPostExecute(ArrayList<CarModel> carModels) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    carModelsAdapter.setData(carModels);
                                    carModelsAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<CarModel> doInBackground(Integer... params) {
                                    try {
                                        return db_manager.getCarModels();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<CarModel>();
                                    }
                                }
                            }.execute();

                            break;
                        case 1:
                            // remove Car
                            new AsyncTask<Integer, Object, ArrayList<Car>>() {
                                @Override
                                protected void onPostExecute(ArrayList<Car> cars) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    carsAdapter.setData(cars);
                                    carsAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<Car> doInBackground(Integer... params) {
                                    try {
                                        String result = db_manager.removeCar(params[0]);
                                        if (!tryParseInt(result) || Integer.parseInt(result) < 0)
                                            errorMessage = result;
                                        return db_manager.getCars();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<Car>();
                                    }
                                }
                            }.execute(((Car) dataListView.getItemAtPosition(position)).getCarID());

                            break;
                        case 2:
                            // remove CarModel
                            new AsyncTask<Integer, Object, ArrayList<CarModel>>() {
                                @Override
                                protected void onPostExecute(ArrayList<CarModel> carModels) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    carModelsAdapter.setData(carModels);
                                    carModelsAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<CarModel> doInBackground(Integer... params) {
                                    try {
                                        String result = db_manager.removeCarModel(params[0]);
                                        if (!tryParseInt(result) || Integer.parseInt(result) < 0)
                                            errorMessage = result;
                                        return db_manager.getCarModels();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<CarModel>();
                                    }
                                }
                            }.execute(((CarModel) dataListView.getItemAtPosition(position)).getModelCode());
                           // update cars that changed
                            new AsyncTask<Integer, Object, ArrayList<Car>>() {
                                @Override
                                protected void onPostExecute(ArrayList<Car> cars) {
                                    if (errorMessage != null) {
                                        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                                        errorMessage = null;
                                    }
                                    carsAdapter.setData(cars);
                                    carsAdapter.notifyDataSetChanged();
                                }

                                @Override
                                protected ArrayList<Car> doInBackground(Integer... params) {
                                    try {
                                        return db_manager.getCars();
                                    } catch (Exception e) {
                                        errorMessage = e.getMessage();
                                        return new ArrayList<Car>();
                                    }
                                }
                            }.execute();

                            break;
                    }
                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }


            }
        });
        builder.show();
        return false;
    }
}
