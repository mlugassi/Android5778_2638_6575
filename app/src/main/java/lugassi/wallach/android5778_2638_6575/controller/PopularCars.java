package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.MyListAdapter;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;

public class PopularCars extends Activity {

    private TextView totalResTextView;
    private ListView carsListView;
    private String errorMassage = null;
    private DB_manager db_manager;
    MyListAdapter<Car> carsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_cars);
        db_manager = DBManagerFactory.getManager();
        findViews();
    }

    private void findViews() {
        totalResTextView = (TextView) findViewById(R.id.totalResTextView);
        carsListView = (ListView) findViewById(R.id.carsListView);

        /// get popular cars
        new AsyncTask<Object, Object, ArrayList<Car>>() {
            @Override
            protected void onPostExecute(final ArrayList<Car> cars) {
                if (errorMassage != null) {
                    Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                    errorMassage = null;
                }
                if (cars.size() > 0)
                    totalResTextView.setText(((Integer) cars.get(0).getReservations()).toString());
                carsAdapter = new MyListAdapter<Car>(PopularCars.this, cars) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        if (convertView == null)
                            convertView = View.inflate(PopularCars.this, R.layout.car_list_view, null);

                        TextView carIdEditText = (TextView) convertView.findViewById(R.id.carIdEditText);
                        final TextView modelNameAndCompanyEditText = (TextView) convertView.findViewById(R.id.modelNameAndCompanyEditText);
                        final TextView branchNameEditText = (TextView) convertView.findViewById(R.id.branchNameEditText);


                        final Car car = (Car) cars.get(position);
                        carIdEditText.setText(((Integer) car.getCarID()).toString());

                        /// get branch details
                        new AsyncTask<Integer, Object, String>() {
                            @Override
                            protected void onPostExecute(String branchName) {
                                if (errorMassage != null) {
                                    Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                                    errorMassage = null;
                                }

                                if (branchName != null)
                                    branchNameEditText.setText(branchName);
                            }

                            @Override
                            protected String doInBackground(Integer... params) {
                                try {
                                    return db_manager.getBranch(params[0]).getBranchName();
                                } catch (Exception e) {
                                    errorMassage = e.getMessage();
                                    return null;
                                }
                            }
                        }.execute(car.getBranchID());


                        /// get car details
                        new AsyncTask<Integer, Object, CarModel>() {
                            @Override
                            protected void onPostExecute(final CarModel carModel) {
                                if (errorMassage != null) {
                                    Toast.makeText(getBaseContext(), errorMassage, Toast.LENGTH_LONG).show();
                                    errorMassage = null;
                                }
                                if (carModel == null) return;
                                modelNameAndCompanyEditText.setText(carModel.getModelName() + ", " + carModel.getCompany().name());
                            }

                            @Override
                            protected CarModel doInBackground(Integer... params) {
                                try {
                                    return db_manager.getCarModel(params[0]);
                                } catch (Exception e) {
                                    errorMassage = e.getMessage();
                                    return null;
                                }
                            }
                        }.execute(car.getModelCode());


                        return convertView;
                    }

                };
                carsListView.setAdapter(carsAdapter);
            }

            @Override
            protected ArrayList<Car> doInBackground(Object... params) {
                try {
                    return db_manager.getPopularCars();
                } catch (Exception e) {
                    errorMassage = e.getMessage();
                    return new ArrayList<Car>();
                }
            }
        }.execute();

    }
}
