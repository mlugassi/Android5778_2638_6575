package lugassi.wallach.android5778_2638_6575.model.backend;

import android.content.ContentValues;
import android.database.MatrixCursor;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.*;
import lugassi.wallach.android5778_2638_6575.model.entities.*;

import static android.util.Patterns.WEB_URL;

/**
 * Created by Michael on 21/11/2017.
 */

public class DB_SQL implements DB_manager {

    private String url = "http://mlugassi.vlab.jct.ac.il/JAVA-Project/";

    public DB_SQL() {

        try {
            String branchResult = GET(url + "Branch/GetSerialNumber.php");
            String carResult = GET(url + "Car/GetSerialNumber.php");
            String carModelResult = GET(url + "CarModel/GetSerialNumber.php");
            Branch.setBranchIDSerializer(Integer.parseInt(branchResult.substring(0, branchResult.length() - 1)));
            Car.setCarIDSerializer(Integer.parseInt(carResult.substring(0, carResult.length() - 1)));
            CarModel.setModelCodeSerializer(Integer.parseInt(carModelResult.substring(0, carModelResult.length() - 1)));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /// Admin

    public Boolean createAdmin(String userName, String password, int userID) {
        try {
            return new AsyncTask<Object, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Object... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();


                        data.put(UserConst.USER_NAME, ((String) params[0]).toLowerCase());
                        data.put(UserConst.PASSWORD, (String) params[1]);
                        data.put(UserConst.USER_ID, (Integer) params[2]);

                        String results = POST(url + "Login/CreateAdmin.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(userName, password, userID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkAdmin(String userName, String password) {
        try {
            return new AsyncTask<String, Object, String>() {
                @Override
                protected String doInBackground(String... params) {
                    String results;
                    try {
                        Map<String, Object> map = new LinkedHashMap<>();

                        map.put(UserConst.USER_NAME, params[0].toLowerCase());
                        map.put(UserConst.PASSWORD, params[1]);

                        results = POST(url + "Login/CheckAdmin.php", map);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return results;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                }
            }.execute(userName, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "error";
    }

///   Branches


    @Override
    public int addBranch(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Integer>() {
                @Override
                protected Integer doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(BranchConst.BRANCH_ID, values.getAsInteger(BranchConst.BRANCH_ID));
                        data.put(BranchConst.BRANCH_NAME, values.getAsString(BranchConst.BRANCH_NAME));
                        data.put(BranchConst.ADDRESS, values.getAsString(BranchConst.ADDRESS));
                        data.put(BranchConst.CITY, values.getAsString(BranchConst.CITY));
                        data.put(BranchConst.MAX_PARKING_SPACE, values.getAsInteger(BranchConst.MAX_PARKING_SPACE));
                        data.put(BranchConst.ACTUAL_PARKING_SPACE, 0);

                        String results = POST(url + "Branch/AddBranch.php", data);

                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return params[0].getAsInteger(BranchConst.BRANCH_ID);
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateBranch(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(BranchConst.BRANCH_ID, values.getAsInteger(BranchConst.BRANCH_ID));
                        data.put(BranchConst.BRANCH_NAME, values.getAsString(BranchConst.BRANCH_NAME));
                        data.put(BranchConst.ADDRESS, values.getAsString(BranchConst.ADDRESS));
                        data.put(BranchConst.CITY, values.getAsString(BranchConst.CITY));
                        data.put(BranchConst.MAX_PARKING_SPACE, values.getAsInteger(BranchConst.MAX_PARKING_SPACE));
                        data.put(BranchConst.ACTUAL_PARKING_SPACE, values.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

                        String results = POST(url + "Branch/UpdateBranch.php", data);

                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeBranch(int branchID) {
        try {
            return new AsyncTask<Integer, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Integer... params) {
                    try {
                        Map<String, Object> myParams = new LinkedHashMap<>();
                        int branchID = params[0];

                        myParams.put(BranchConst.BRANCH_ID, branchID);

                        String results = POST(url + "Branch/RemoveBranch.php", myParams);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(branchID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Branch> getBranches() {

        ArrayList<Branch> branches = new ArrayList<Branch>();
        try {
            JSONArray array = new JSONObject(GET(url + "Branch/GetBranches.php")).getJSONArray("branches");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Branch branch = new Branch(jsonObject.getInt(BranchConst.BRANCH_ID));
                branch.setAddress(jsonObject.getString(BranchConst.ADDRESS));
                branch.setMaxParkingSpace(jsonObject.getInt(BranchConst.MAX_PARKING_SPACE));
                branch.setCity(jsonObject.getString(BranchConst.CITY));
                branch.setBranchName(jsonObject.getString(BranchConst.BRANCH_NAME));
                branch.setActualParkingSpace(jsonObject.getInt(BranchConst.ACTUAL_PARKING_SPACE));

                branches.add(branch);
            }
        } catch (Exception e) {
        }
        return branches;
    }

    @Override
    public Branch getBranch(int branchID) {
        try {
            return new AsyncTask<Integer, Object, Branch>() {
                @Override
                protected Branch doInBackground(Integer... params) {
                    try {
                        int param = params[0];
                        Map<String, Object> data = new LinkedHashMap<>();

                        data.put(BranchConst.BRANCH_ID, param);

                        JSONArray array = new JSONObject(POST(url + "Branch/GetBranch.php", data)).getJSONArray("branch");
                        JSONObject jsonObject = array.getJSONObject(0);

                        Branch branch = new Branch(jsonObject.getInt(BranchConst.BRANCH_ID));
                        branch.setAddress(jsonObject.getString(BranchConst.ADDRESS));
                        branch.setMaxParkingSpace(jsonObject.getInt(BranchConst.MAX_PARKING_SPACE));
                        branch.setCity(jsonObject.getString(BranchConst.CITY));
                        branch.setBranchName(jsonObject.getString(BranchConst.BRANCH_NAME));
                        branch.setActualParkingSpace(jsonObject.getInt(BranchConst.ACTUAL_PARKING_SPACE));
                        return branch;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Branch result) {
                    super.onPostExecute(result);
                }
            }.execute(branchID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /// cars

    @Override
    public int addCar(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Integer>() {
                @Override
                protected Integer doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CarConst.CAR_ID, values.getAsInteger(CarConst.CAR_ID));
                        data.put(CarConst.MODEL_CODE, values.getAsInteger(CarConst.MODEL_CODE));
                        data.put(CarConst.BRANCH_ID, values.getAsInteger(CarConst.BRANCH_ID));
                        data.put(CarConst.RESERVATIONS, 0);
                        data.put(CarConst.MILEAGE, 0);

                        String results = POST(url + "Car/AddCar.php", data);

                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return params[0].getAsInteger(CarConst.CAR_ID);
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateCar(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CarConst.CAR_ID, values.getAsInteger(CarConst.CAR_ID));
                        data.put(CarConst.MODEL_CODE, values.getAsInteger(CarConst.MODEL_CODE));
                        data.put(CarConst.BRANCH_ID, values.getAsInteger(CarConst.BRANCH_ID));
                        data.put(CarConst.RESERVATIONS, values.getAsInteger(CarConst.RESERVATIONS));
                        data.put(CarConst.MILEAGE, values.getAsInteger(CarConst.MILEAGE));

                        String results = POST(url + "Car/UpdateCar.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCar(int carID) {
        try {
            return new AsyncTask<Integer, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Integer... params) {
                    try {
                        Map<String, Object> myParams = new LinkedHashMap<>();
                        int carID = params[0];

                        myParams.put(CarConst.CAR_ID, carID);

                        String results = POST(url + "Car/RemoveCar.php", myParams);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(carID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        try {
            JSONArray array = new JSONObject(GET(url + "Car/GetCars.php")).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Car car = new Car(jsonObject.getInt(CarConst.CAR_ID));
                car.setModelCode(jsonObject.getInt(CarConst.MODEL_CODE));
                car.setBranchID(jsonObject.getInt(CarConst.BRANCH_ID));
                car.setReservations(jsonObject.getInt(CarConst.RESERVATIONS));
                car.setMileage(jsonObject.getInt(CarConst.MILEAGE));

                cars.add(car);
            }
        } catch (Exception e) {
        }

        return cars;
    }

    @Override
    public Car getCar(int carID) {
        try {
            return new AsyncTask<Integer, Object, Car>() {
                @Override
                protected Car doInBackground(Integer... params) {
                    try {
                        int param = params[0];
                        Map<String, Object> data = new LinkedHashMap<>();

                        data.put(CarConst.CAR_ID, param);

                        JSONArray array = new JSONObject(POST(url + "Car/GetCar.php", data)).getJSONArray("car");
                        JSONObject jsonObject = array.getJSONObject(0);

                        Car car = new Car(jsonObject.getInt(CarConst.CAR_ID));
                        car.setModelCode(jsonObject.getInt(CarConst.MODEL_CODE));
                        car.setBranchID(jsonObject.getInt(CarConst.BRANCH_ID));
                        car.setReservations(jsonObject.getInt(CarConst.RESERVATIONS));
                        car.setMileage(jsonObject.getInt(CarConst.MILEAGE));

                        return car;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Car result) {
                    super.onPostExecute(result);
                }
            }.execute(carID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

// car models

    @Override
    public boolean updateCarModel(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CarModelConst.MODEL_CODE, values.getAsInteger(CarModelConst.MODEL_CODE));
                        data.put(CarModelConst.MODEL_NAME, values.getAsString(CarModelConst.MODEL_NAME));
                        data.put(CarModelConst.CAR_TYPE, values.getAsString(CarModelConst.CAR_TYPE));
                        data.put(CarModelConst.COMPANY, values.getAsString(CarModelConst.COMPANY));
                        data.put(CarModelConst.ENGINE_CAPACITY, values.getAsString(CarModelConst.ENGINE_CAPACITY));
                        data.put(CarModelConst.MAX_GAS_TANK, values.getAsInteger(CarModelConst.MAX_GAS_TANK));
                        data.put(CarModelConst.SEATS, values.getAsInteger(CarModelConst.SEATS));

                        String results = POST(url + "CarModel/UpdateCarModel.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCarModel(int modelCode) {
        try {
            return new AsyncTask<Integer, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Integer... params) {
                    try {
                        Map<String, Object> myParams = new LinkedHashMap<>();
                        int modelCode = params[0];

                        myParams.put(CarModelConst.MODEL_CODE, modelCode);

                        String results = POST(url + "CarModel/RemoveCarModel.php", myParams);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(modelCode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int addCarModel(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Integer>() {
                @Override
                protected Integer doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CarModelConst.MODEL_CODE, values.getAsInteger(CarModelConst.MODEL_CODE));
                        data.put(CarModelConst.MODEL_NAME, values.getAsString(CarModelConst.MODEL_NAME));
                        data.put(CarModelConst.CAR_TYPE, values.getAsString(CarModelConst.CAR_TYPE));
                        data.put(CarModelConst.COMPANY, values.getAsString(CarModelConst.COMPANY));
                        data.put(CarModelConst.ENGINE_CAPACITY, values.getAsString(CarModelConst.ENGINE_CAPACITY));
                        data.put(CarModelConst.MAX_GAS_TANK, values.getAsInteger(CarModelConst.MAX_GAS_TANK));
                        data.put(CarModelConst.SEATS, values.getAsInteger(CarModelConst.SEATS));

                        String results = POST(url + "CarModel/AddCarModel.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return params[0].getAsInteger(CarModelConst.MODEL_CODE);
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public ArrayList<CarModel> getCarModels() {
        ArrayList<CarModel> carModels = new ArrayList<CarModel>();
        try {
            JSONArray array = new JSONObject(GET(url + "CarModel/GetCarModels.php")).getJSONArray("carModels");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                CarModel carModel = new CarModel(jsonObject.getInt(CarModelConst.MODEL_CODE));
                carModel.setCompany(Company.valueOf(jsonObject.getString(CarModelConst.COMPANY)));
                carModel.setSeats(jsonObject.getInt(CarModelConst.SEATS));
                carModel.setCarType(CarType.valueOf(jsonObject.getString(CarModelConst.CAR_TYPE)));
                carModel.setEngineCapacity(EngineCapacity.valueOf(jsonObject.getString(CarModelConst.ENGINE_CAPACITY)));
                carModel.setMaxGasTank(jsonObject.getInt(CarModelConst.MAX_GAS_TANK));
                carModel.setModelName(jsonObject.getString(CarModelConst.MODEL_NAME));

                carModels.add(carModel);
            }
        } catch (Exception e) {
        }

        return carModels;
    }

    @Override
    public CarModel getCarModel(int modelCode) {
        try {
            return new AsyncTask<Integer, Object, CarModel>() {
                @Override
                protected CarModel doInBackground(Integer... params) {
                    try {
                        int param = params[0];
                        Map<String, Object> data = new LinkedHashMap<>();

                        data.put(CarModelConst.MODEL_CODE, param);

                        JSONArray array = new JSONObject(POST(url + "CarModel/GetCarModel.php", data)).getJSONArray("carModel");
                        JSONObject jsonObject = array.getJSONObject(0);

                        CarModel carModel = new CarModel(jsonObject.getInt(CarModelConst.MODEL_CODE));
                        carModel.setCompany(Company.valueOf(jsonObject.getString(CarModelConst.COMPANY)));
                        carModel.setSeats(jsonObject.getInt(CarModelConst.SEATS));
                        carModel.setCarType(CarType.valueOf(jsonObject.getString(CarModelConst.CAR_TYPE)));
                        carModel.setEngineCapacity(EngineCapacity.valueOf(jsonObject.getString(CarModelConst.ENGINE_CAPACITY)));
                        carModel.setMaxGasTank(jsonObject.getInt(CarModelConst.MAX_GAS_TANK));
                        carModel.setModelName(jsonObject.getString(CarModelConst.MODEL_NAME));

                        return carModel;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(CarModel result) {
                    super.onPostExecute(result);
                }
            }.execute(modelCode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /// customer

    @Override
    public int addCustomer(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Integer>() {
                @Override
                protected Integer doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CustomerConst.FIRST_NAME, values.getAsString(CustomerConst.FIRST_NAME));
                        data.put(CustomerConst.FAMILY_NAME, values.getAsString(CustomerConst.FAMILY_NAME));
                        data.put(CustomerConst.CUSTOMER_ID, values.getAsInteger(CustomerConst.CUSTOMER_ID));
                        data.put(CustomerConst.PHONE, values.getAsInteger(CustomerConst.PHONE));
                        data.put(CustomerConst.EMAIL, values.getAsString(CustomerConst.EMAIL));
                        data.put(CustomerConst.CREDIT_CARD, values.getAsLong(CustomerConst.CREDIT_CARD));
                        data.put(CustomerConst.GENDER, values.getAsString(CustomerConst.GENDER));
                        data.put(CustomerConst.NUM_ACCIDENTS, 0);
                        data.put(CustomerConst.BIRTH_DAY, values.getAsString(CustomerConst.BIRTH_DAY));

                        String results = POST(url + "Customer/AddCustomer.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return params[0].getAsInteger(CustomerConst.CUSTOMER_ID);
                }

                @Override
                protected void onPostExecute(Integer result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateCustomer(ContentValues contentValues) {
        try {
            return new AsyncTask<ContentValues, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(ContentValues... params) {
                    try {
                        Map<String, Object> data = new LinkedHashMap<>();

                        ContentValues values = params[0];

                        data.put(CustomerConst.FIRST_NAME, values.getAsString(CustomerConst.FIRST_NAME));
                        data.put(CustomerConst.FAMILY_NAME, values.getAsString(CustomerConst.FAMILY_NAME));
                        data.put(CustomerConst.CUSTOMER_ID, values.getAsInteger(CustomerConst.CUSTOMER_ID));
                        data.put(CustomerConst.PHONE, values.getAsInteger(CustomerConst.PHONE));
                        data.put(CustomerConst.EMAIL, values.getAsString(CustomerConst.EMAIL));
                        data.put(CustomerConst.CREDIT_CARD, values.getAsLong(CustomerConst.CREDIT_CARD));
                        data.put(CustomerConst.GENDER, values.getAsString(CustomerConst.GENDER));
                        data.put(CustomerConst.NUM_ACCIDENTS, values.getAsInteger(CustomerConst.NUM_ACCIDENTS));
                        data.put(CustomerConst.BIRTH_DAY, values.getAsString(CustomerConst.BIRTH_DAY));

                        String results = POST(url + "Customer/UpdateCustomer.php", data);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(contentValues).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCustomer(final int customerID) {
        try {
            return new AsyncTask<Integer, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Integer... params) {
                    try {
                        Map<String, Object> myParams = new LinkedHashMap<>();
                        int customerID = params[0];

                        myParams.put(CustomerConst.CUSTOMER_ID, customerID);

                        String results = POST(url + "Customer/RemoveCustomer.php", myParams);
                        if (results.equals("")) {
                            throw new Exception("An error occurred on the server's side");
                        }
                        if (results.substring(0, 5).equalsIgnoreCase("error")) {
                            throw new Exception(results.substring(5));
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                }
            }.execute(customerID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            JSONArray array = new JSONObject(GET(url + "Customer/GetCustomers.php")).getJSONArray("customers");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Customer customer = new Customer();
                customer.setCustomerID(jsonObject.getInt(CustomerConst.CUSTOMER_ID));
                customer.setFirstName(jsonObject.getString(CustomerConst.FIRST_NAME));
                customer.setFamilyName(jsonObject.getString(CustomerConst.FAMILY_NAME));
                customer.setPhone(jsonObject.getInt(CustomerConst.PHONE));
                customer.setCreditCard(jsonObject.getLong(CustomerConst.CREDIT_CARD));
                customer.setEmail(jsonObject.getString(CustomerConst.EMAIL));
                customer.setBirthDay(jsonObject.getString(CustomerConst.BIRTH_DAY));
                customer.setGender(Gender.valueOf(jsonObject.getString(CustomerConst.GENDER)));
                customer.setNumAccidents(jsonObject.getInt(CustomerConst.NUM_ACCIDENTS));

                customers.add(customer);
            }
        } catch (Exception e) {
        }

        return customers;
    }

    @Override
    public Customer getCustomer(int customerID) {
        try {
            return new AsyncTask<Integer, Object, Customer>() {
                @Override
                protected Customer doInBackground(Integer... params) {
                    try {
                        int param = params[0];
                        Map<String, Object> data = new LinkedHashMap<>();

                        data.put(CustomerConst.CUSTOMER_ID, param);

                        JSONArray array = new JSONObject(POST(url + "CarModel/GetCarModel.php", data)).getJSONArray("carModel");
                        JSONObject jsonObject = array.getJSONObject(0);

                        Customer customer = new Customer();
                        customer.setCustomerID(jsonObject.getInt(CustomerConst.CUSTOMER_ID));
                        customer.setFirstName(jsonObject.getString(CustomerConst.FIRST_NAME));
                        customer.setFamilyName(jsonObject.getString(CustomerConst.FAMILY_NAME));
                        customer.setPhone(jsonObject.getInt(CustomerConst.PHONE));
                        customer.setCreditCard(jsonObject.getLong(CustomerConst.CREDIT_CARD));
                        customer.setEmail(jsonObject.getString(CustomerConst.EMAIL));
                        customer.setBirthDay(jsonObject.getString(CustomerConst.BIRTH_DAY));
                        customer.setGender(Gender.valueOf(jsonObject.getString(CustomerConst.GENDER)));
                        customer.setNumAccidents(jsonObject.getInt(CustomerConst.NUM_ACCIDENTS));

                        return customer;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Customer result) {
                    super.onPostExecute(result);
                }
            }.execute(customerID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

/// post and get

    private static String GET(String url) throws ExecutionException, InterruptedException {

        return new AsyncTask<String, Object, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL obj = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // print result
                        return response.toString();
                    } else {
                        return "";
                    }
                } catch (Exception e) {
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }.execute(url).get();
    }

    private static String POST(String url, Map<String, Object> params) throws IOException {

        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else return "";
    }
}
