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

    public Boolean createAdmin(String userName, String password, int userID) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(UserConst.USER_NAME, userName);
            params.put(UserConst.PASSWORD, password);
            params.put(UserConst.USER_ID, userID);

            String results = POST(url + "Login/CreateNewAdmin.php", params);
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

    public String checkAdmin(String userName, String password) {
        try {
            return new AsyncTask<String, Object, String>() {
                @Override
                protected String doInBackground(String... params) {
                    String results;
                    try {
                        Map<String, Object> map = new LinkedHashMap<>();

                        map.put(UserConst.USER_NAME, params[0]);
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


    /// add functions
    @Override
    public int addCarModel(ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CarModelConst.MODEL_CODE, contentValues.getAsInteger(CarModelConst.MODEL_CODE));
            params.put(CarModelConst.MODEL_NAME, contentValues.getAsString(CarModelConst.MODEL_NAME));
            params.put(CarModelConst.CAR_TYPE, contentValues.getAsString(CarModelConst.CAR_TYPE));
            params.put(CarModelConst.COMPANY, contentValues.getAsString(CarModelConst.COMPANY));
            params.put(CarModelConst.ENGINE_CAPACITY, contentValues.getAsString(CarModelConst.ENGINE_CAPACITY));
            params.put(CarModelConst.MAX_GAS_TANK, contentValues.getAsInteger(CarModelConst.MAX_GAS_TANK));
            params.put(CarModelConst.SEATS, contentValues.getAsInteger(CarModelConst.SEATS));

            String results = POST(url + "CarModel/AddCarModel.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return contentValues.getAsInteger(CarModelConst.MODEL_CODE);
    }

    @Override
    public int addCar(ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CarConst.CAR_ID, contentValues.getAsInteger(CarConst.CAR_ID));
            params.put(CarConst.MODEL_CODE, contentValues.getAsInteger(CarConst.MODEL_CODE));
            params.put(CarConst.BRANCH_ID, contentValues.getAsInteger(CarConst.BRANCH_ID));
            params.put(CarConst.RESERVATIONS, 0);
            params.put(CarConst.MILEAGE, 0);

            String results = POST(url + "Car/AddCar.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return contentValues.getAsInteger(CarConst.CAR_ID);
    }

    @Override
    public int addCustomer(ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CustomerConst.FIRST_NAME, contentValues.getAsString(CustomerConst.FIRST_NAME));
            params.put(CustomerConst.FAMILY_NAME, contentValues.getAsString(CustomerConst.FAMILY_NAME));
            params.put(CustomerConst.CUSTOMER_ID, contentValues.getAsInteger(CustomerConst.CUSTOMER_ID));
            params.put(CustomerConst.PHONE, contentValues.getAsInteger(CustomerConst.PHONE));
            params.put(CustomerConst.EMAIL, contentValues.getAsString(CustomerConst.EMAIL));
            params.put(CustomerConst.CREDIT_CARD, contentValues.getAsLong(CustomerConst.CREDIT_CARD));
            params.put(CustomerConst.GENDER, contentValues.getAsString(CustomerConst.GENDER));
            params.put(CustomerConst.NUM_ACCIDENTS, 0);
            params.put(CustomerConst.BIRTH_DAY, contentValues.getAsString(CustomerConst.BIRTH_DAY));

            String results = POST(url + "Customer/AddCustomer.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return contentValues.getAsInteger(CustomerConst.CUSTOMER_ID);
    }

    @Override
    public int addBranch(ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(BranchConst.BRANCH_ID, contentValues.getAsInteger(BranchConst.BRANCH_ID));
            params.put(BranchConst.BRANCH_NAME, contentValues.getAsString(BranchConst.BRANCH_NAME));
            params.put(BranchConst.ADDRESS, contentValues.getAsString(BranchConst.ADDRESS));
            params.put(BranchConst.CITY, contentValues.getAsString(BranchConst.CITY));
            params.put(BranchConst.MAX_PARKING_SPACE, contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
            params.put(BranchConst.ACTUAL_PARKING_SPACE, 0);

            String results = POST(url + "Branch/AddBranch.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return contentValues.getAsInteger(BranchConst.BRANCH_ID);
    }

    @Override
    public int addReservation(ContentValues contentValues) {
        return 0;
    }

    @Override
    public int addPromotion(ContentValues contentValues) {
        return 0;
    }


    /// update functions

    @Override
    public boolean updateCar(int carID, ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CarConst.CAR_ID, carID);
            params.put(CarConst.MODEL_CODE, contentValues.getAsInteger(CarConst.MODEL_CODE));
            params.put(CarConst.BRANCH_ID, contentValues.getAsInteger(CarConst.BRANCH_ID));
            params.put(CarConst.RESERVATIONS, contentValues.getAsInteger(CarConst.RESERVATIONS));
            params.put(CarConst.MILEAGE, contentValues.getAsInteger(CarConst.MILEAGE));

            String results = POST(url + "Car/UpdateCar.php", params);
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
    public boolean updateCarModel(int modelCode, ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CarModelConst.MODEL_CODE, modelCode);
            params.put(CarModelConst.MODEL_NAME, contentValues.getAsString(CarModelConst.MODEL_NAME));
            params.put(CarModelConst.CAR_TYPE, contentValues.getAsString(CarModelConst.CAR_TYPE));
            params.put(CarModelConst.COMPANY, contentValues.getAsString(CarModelConst.COMPANY));
            params.put(CarModelConst.ENGINE_CAPACITY, contentValues.getAsString(CarModelConst.ENGINE_CAPACITY));
            params.put(CarModelConst.MAX_GAS_TANK, contentValues.getAsInteger(CarModelConst.MAX_GAS_TANK));
            params.put(CarModelConst.SEATS, contentValues.getAsInteger(CarModelConst.SEATS));

            String results = POST(url + "CarModel/UpdateCarModel.php", params);
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
    public boolean updateCustomer(int customerID, ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(CustomerConst.FIRST_NAME, contentValues.getAsString(CustomerConst.FIRST_NAME));
            params.put(CustomerConst.FAMILY_NAME, contentValues.getAsString(CustomerConst.FAMILY_NAME));
            params.put(CustomerConst.CUSTOMER_ID, customerID);
            params.put(CustomerConst.PHONE, contentValues.getAsInteger(CustomerConst.PHONE));
            params.put(CustomerConst.EMAIL, contentValues.getAsString(CustomerConst.EMAIL));
            params.put(CustomerConst.CREDIT_CARD, contentValues.getAsLong(CustomerConst.CREDIT_CARD));
            params.put(CustomerConst.GENDER, contentValues.getAsString(CustomerConst.GENDER));
            params.put(CustomerConst.NUM_ACCIDENTS, contentValues.getAsInteger(CustomerConst.NUM_ACCIDENTS));
            params.put(CustomerConst.BIRTH_DAY, contentValues.getAsString(CustomerConst.BIRTH_DAY));

            String results = POST(url + "Customer/UpdateCustomer.php", params);
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
    public boolean updateBranch(int branchID, ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(BranchConst.BRANCH_ID, branchID);
            params.put(BranchConst.BRANCH_NAME, contentValues.getAsString(BranchConst.BRANCH_NAME));
            params.put(BranchConst.ADDRESS, contentValues.getAsString(BranchConst.ADDRESS));
            params.put(BranchConst.CITY, contentValues.getAsString(BranchConst.CITY));
            params.put(BranchConst.MAX_PARKING_SPACE, contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
            params.put(BranchConst.ACTUAL_PARKING_SPACE, contentValues.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

            String results = POST(url + "Branch/UpdateBranch.php", params);
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
    public boolean updateReservation(int reservationID, ContentValues contentValues) {
        return false;
    }

    @Override
    public boolean updatePromotion(int customerID, ContentValues contentValues) {
        return false;
    }


    /// remove functions
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
    public boolean removeReservation(int reservationID) {
        return false;
    }

    @Override
    public boolean removePromotion(int customerID) {
        return false;
    }


    /// get functions
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
    public ArrayList<Promotion> getPromotions() {
        return null;
    }

    @Override
    public ArrayList<Reservation> getReservations() {
        return null;
    }


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
