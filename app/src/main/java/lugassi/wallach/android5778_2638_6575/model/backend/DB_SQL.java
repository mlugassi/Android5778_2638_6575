package lugassi.wallach.android5778_2638_6575.model.backend;

import android.content.ContentValues;
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

import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.BranchConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.CarConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.CarModelConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.CustomerConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.PromotionConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst.UserConst;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
import lugassi.wallach.android5778_2638_6575.model.entities.Customer;
import lugassi.wallach.android5778_2638_6575.model.entities.Enums.CarType;
import lugassi.wallach.android5778_2638_6575.model.entities.Enums.Company;
import lugassi.wallach.android5778_2638_6575.model.entities.Enums.EngineCapacity;
import lugassi.wallach.android5778_2638_6575.model.entities.Enums.Gender;


/**
 * Created by Michael on 21/11/2017.
 */

public class DB_SQL implements DB_manager {

    private String url = "http://mlugassi.vlab.jct.ac.il/JAVA-Project/";

    public DB_SQL() {

        try {
            String branchResult = new AsyncTask<Object, Object, String>() {
                @Override
                protected void onPostExecute(String reservationResult) {
                    super.onPostExecute(reservationResult);
                }

                @Override
                protected String doInBackground(Object... params) {

                    try {
                        return GET(url + "Branch/GetSerialNumber.php");
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "0";
                }
            }.execute().get();
            String carResult = new AsyncTask<Object, Object, String>() {
                @Override
                protected void onPostExecute(String reservationResult) {
                    super.onPostExecute(reservationResult);
                }

                @Override
                protected String doInBackground(Object... params) {

                    try {
                        return GET(url + "Car/GetSerialNumber.php");
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "0";
                }
            }.execute().get();
            String carModelResult = new AsyncTask<Object, Object, String>() {
                @Override
                protected void onPostExecute(String reservationResult) {
                    super.onPostExecute(reservationResult);
                }

                @Override
                protected String doInBackground(Object... params) {

                    try {
                        return GET(url + "CarModel/GetSerialNumber.php");
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "0";
                }
            }.execute().get();
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

    public Boolean createAdmin(String userName, String password, int userID) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(UserConst.USER_NAME, userName);
        params.put(UserConst.PASSWORD, password);
        params.put(UserConst.USER_ID, userID);

        String results = POST(url + "Login/CreateAdmin.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    public String checkAdmin(String userName, String password) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put(UserConst.USER_NAME, userName);
        map.put(UserConst.PASSWORD, password);

        String results = POST(url + "Login/CheckAdmin.php", map);
        if (!(results.startsWith("Worng") || results.startsWith("User") || results.startsWith("Success"))) {
            throw new Exception("An error occurred on the server's side");
        }
        return results;
    }

///   Branches

    @Override
    public int addBranch(ContentValues contentValues) throws Exception {

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(BranchConst.BRANCH_ID, contentValues.getAsInteger(BranchConst.BRANCH_ID));
        params.put(BranchConst.BRANCH_NAME, contentValues.getAsString(BranchConst.BRANCH_NAME));
        params.put(BranchConst.ADDRESS, contentValues.getAsString(BranchConst.ADDRESS));
        params.put(BranchConst.CITY, contentValues.getAsString(BranchConst.CITY));
        params.put(BranchConst.MAX_PARKING_SPACE, contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
        params.put(BranchConst.ACTUAL_PARKING_SPACE, contentValues.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

        String results = POST(url + "Branch/AddBranch.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return contentValues.getAsInteger(BranchConst.BRANCH_ID);
    }

    @Override
    public boolean updateBranch(ContentValues contentValues) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(BranchConst.BRANCH_ID, contentValues.getAsInteger(BranchConst.BRANCH_ID));
        params.put(BranchConst.BRANCH_NAME, contentValues.getAsString(BranchConst.BRANCH_NAME));
        params.put(BranchConst.ADDRESS, contentValues.getAsString(BranchConst.ADDRESS));
        params.put(BranchConst.CITY, contentValues.getAsString(BranchConst.CITY));
        params.put(BranchConst.MAX_PARKING_SPACE, contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
        params.put(BranchConst.ACTUAL_PARKING_SPACE, contentValues.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

        String results = POST(url + "Branch/UpdateBranch.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public boolean removeBranch(int branchID) throws Exception {

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(BranchConst.BRANCH_ID, branchID);

        String results = POST(url + "Branch/RemoveBranch.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public ArrayList<Branch> getBranches() throws Exception {
        ArrayList<Branch> branches = new ArrayList<Branch>();

        String results = GET(url + "Branch/GetBranches.php");
        if (!results.startsWith("0")) {
            JSONArray array = new JSONObject(results).getJSONArray("branches");
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
        }
        return branches;
    }

    @Override
    public Branch getBranch(int branchID) throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put(BranchConst.BRANCH_ID, branchID);

        String results = POST(url + "Branch/GetBranch.php", data);
        if (results.startsWith("0"))
            throw new Exception("Record dosn't exist");
        JSONArray array = new JSONObject(results).getJSONArray("branch");
        JSONObject jsonObject = array.getJSONObject(0);

        Branch branch = new Branch(jsonObject.getInt(BranchConst.BRANCH_ID));
        branch.setAddress(jsonObject.getString(BranchConst.ADDRESS));
        branch.setMaxParkingSpace(jsonObject.getInt(BranchConst.MAX_PARKING_SPACE));
        branch.setCity(jsonObject.getString(BranchConst.CITY));
        branch.setBranchName(jsonObject.getString(BranchConst.BRANCH_NAME));
        branch.setActualParkingSpace(jsonObject.getInt(BranchConst.ACTUAL_PARKING_SPACE));
        return branch;
    }


    /// cars

    @Override
    public int addCar(ContentValues contentValues) throws Exception {

        Map<String, Object> data = new LinkedHashMap<>();

        data.put(CarConst.CAR_ID, contentValues.getAsInteger(CarConst.CAR_ID));
        data.put(CarConst.MODEL_CODE, contentValues.getAsInteger(CarConst.MODEL_CODE));
        data.put(CarConst.BRANCH_ID, contentValues.getAsInteger(CarConst.BRANCH_ID));
        data.put(CarConst.RESERVATIONS, contentValues.getAsInteger(CarConst.RESERVATIONS));
        data.put(CarConst.MILEAGE, contentValues.getAsInteger(CarConst.MILEAGE));

        String results = POST(url + "Car/AddCar.php", data);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return contentValues.getAsInteger(CarConst.CAR_ID);
    }

    @Override
    public boolean updateCar(ContentValues contentValues) throws Exception {

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CarConst.CAR_ID, contentValues.getAsInteger(CarConst.CAR_ID));
        params.put(CarConst.MODEL_CODE, contentValues.getAsInteger(CarConst.MODEL_CODE));
        params.put(CarConst.BRANCH_ID, contentValues.getAsInteger(CarConst.BRANCH_ID));
        params.put(CarConst.RESERVATIONS, contentValues.getAsInteger(CarConst.RESERVATIONS));
        params.put(CarConst.MILEAGE, contentValues.getAsInteger(CarConst.MILEAGE));

        String results = POST(url + "Car/UpdateCar.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public boolean removeCar(int carID) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CarConst.CAR_ID, carID);

        String results = POST(url + "Car/RemoveCar.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public ArrayList<Car> getCars() throws Exception {
        ArrayList<Car> cars = new ArrayList<Car>();
        String results = GET(url + "Car/GetCars.php");
        if (!results.startsWith("0")) {
            JSONArray array = new JSONObject(results).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Car car = new Car(jsonObject.getInt(CarConst.CAR_ID));
                car.setModelCode(jsonObject.getInt(CarConst.MODEL_CODE));
                car.setBranchID(jsonObject.getInt(CarConst.BRANCH_ID));
                car.setReservations(jsonObject.getInt(CarConst.RESERVATIONS));
                car.setMileage(jsonObject.getInt(CarConst.MILEAGE));

                cars.add(car);
            }
        }
        return cars;
    }

    @Override
    public Car getCar(int carID) throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put(CarConst.CAR_ID, carID);
        String results = POST(url + "Car/GetCar.php", data);
        if (results.startsWith("0"))
            throw new Exception("Record dosn't exist");
        JSONArray array = new JSONObject(results).getJSONArray("car");
        JSONObject jsonObject = array.getJSONObject(0);

        Car car = new Car(jsonObject.getInt(CarConst.CAR_ID));
        car.setModelCode(jsonObject.getInt(CarConst.MODEL_CODE));
        car.setBranchID(jsonObject.getInt(CarConst.BRANCH_ID));
        car.setReservations(jsonObject.getInt(CarConst.RESERVATIONS));
        car.setMileage(jsonObject.getInt(CarConst.MILEAGE));
        return car;
    }

// car models

    @Override
    public boolean updateCarModel(ContentValues contentValues) throws Exception {

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CarModelConst.MODEL_CODE, contentValues.getAsInteger(CarModelConst.MODEL_CODE));
        params.put(CarModelConst.MODEL_NAME, contentValues.getAsString(CarModelConst.MODEL_NAME));
        params.put(CarModelConst.CAR_TYPE, contentValues.getAsString(CarModelConst.CAR_TYPE));
        params.put(CarModelConst.COMPANY, contentValues.getAsString(CarModelConst.COMPANY));
        params.put(CarModelConst.ENGINE_CAPACITY, contentValues.getAsString(CarModelConst.ENGINE_CAPACITY));
        params.put(CarModelConst.MAX_GAS_TANK, contentValues.getAsInteger(CarModelConst.MAX_GAS_TANK));
        params.put(CarModelConst.SEATS, contentValues.getAsInteger(CarModelConst.SEATS));

        String results = POST(url + "CarModel/UpdateCarModel.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public boolean removeCarModel(int modelCode) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CarModelConst.MODEL_CODE, modelCode);

        String results = POST(url + "CarModel/RemoveCarModel.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public int addCarModel(ContentValues contentValues) throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put(CarModelConst.MODEL_CODE, contentValues.getAsInteger(CarModelConst.MODEL_CODE));
        data.put(CarModelConst.MODEL_NAME, contentValues.getAsString(CarModelConst.MODEL_NAME));
        data.put(CarModelConst.CAR_TYPE, contentValues.getAsString(CarModelConst.CAR_TYPE));
        data.put(CarModelConst.COMPANY, contentValues.getAsString(CarModelConst.COMPANY));
        data.put(CarModelConst.ENGINE_CAPACITY, contentValues.getAsString(CarModelConst.ENGINE_CAPACITY));
        data.put(CarModelConst.MAX_GAS_TANK, contentValues.getAsInteger(CarModelConst.MAX_GAS_TANK));
        data.put(CarModelConst.SEATS, contentValues.getAsInteger(CarModelConst.SEATS));

        String results = POST(url + "CarModel/AddCarModel.php", data);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return contentValues.getAsInteger(CarModelConst.MODEL_CODE);
    }

    @Override
    public ArrayList<CarModel> getCarModels() throws Exception {
        ArrayList<CarModel> carModels = new ArrayList<CarModel>();
        String results = GET(url + "CarModel/GetCarModels.php");
        if (!results.startsWith("0")) {
            JSONArray array = new JSONObject(results).getJSONArray("carModels");
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
        }
        return carModels;
    }

    @Override
    public CarModel getCarModel(int modelCode) throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put(CarModelConst.MODEL_CODE, modelCode);
        String results = POST(url + "CarModel/GetCarModel.php", data);
        if (results.startsWith("0"))
            throw new Exception("Record dosn't exist");
        JSONArray array = new JSONObject(results).getJSONArray("carModel");
        JSONObject jsonObject = array.getJSONObject(0);

        CarModel carModel = new CarModel(jsonObject.getInt(CarModelConst.MODEL_CODE));
        carModel.setCompany(Company.valueOf(jsonObject.getString(CarModelConst.COMPANY)));
        carModel.setSeats(jsonObject.getInt(CarModelConst.SEATS));
        carModel.setCarType(CarType.valueOf(jsonObject.getString(CarModelConst.CAR_TYPE)));
        carModel.setEngineCapacity(EngineCapacity.valueOf(jsonObject.getString(CarModelConst.ENGINE_CAPACITY)));
        carModel.setMaxGasTank(jsonObject.getInt(CarModelConst.MAX_GAS_TANK));
        carModel.setModelName(jsonObject.getString(CarModelConst.MODEL_NAME));
        return carModel;
    }
    /// customer

    @Override
    public int addCustomer(ContentValues contentValues) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CustomerConst.FIRST_NAME, contentValues.getAsString(CustomerConst.FIRST_NAME));
        params.put(CustomerConst.FAMILY_NAME, contentValues.getAsString(CustomerConst.FAMILY_NAME));
        params.put(CustomerConst.CUSTOMER_ID, contentValues.getAsInteger(CustomerConst.CUSTOMER_ID));
        params.put(CustomerConst.PHONE, contentValues.getAsInteger(CustomerConst.PHONE));
        params.put(CustomerConst.EMAIL, contentValues.getAsString(CustomerConst.EMAIL));
        params.put(CustomerConst.CREDIT_CARD, contentValues.getAsLong(CustomerConst.CREDIT_CARD));
        params.put(CustomerConst.GENDER, contentValues.getAsString(CustomerConst.GENDER));
        params.put(CustomerConst.NUM_ACCIDENTS, contentValues.getAsInteger(CustomerConst.NUM_ACCIDENTS));
        params.put(CustomerConst.BIRTH_DAY, contentValues.getAsString(CustomerConst.BIRTH_DAY));

        String results = POST(url + "Customer/AddCustomer.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return contentValues.getAsInteger(CustomerConst.CUSTOMER_ID);
    }

    @Override
    public boolean updateCustomer(ContentValues contentValues) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CustomerConst.FIRST_NAME, contentValues.getAsString(CustomerConst.FIRST_NAME));
        params.put(CustomerConst.FAMILY_NAME, contentValues.getAsString(CustomerConst.FAMILY_NAME));
        params.put(CustomerConst.CUSTOMER_ID, contentValues.getAsInteger(CustomerConst.CUSTOMER_ID));
        params.put(CustomerConst.PHONE, contentValues.getAsInteger(CustomerConst.PHONE));
        params.put(CustomerConst.EMAIL, contentValues.getAsString(CustomerConst.EMAIL));
        params.put(CustomerConst.CREDIT_CARD, contentValues.getAsLong(CustomerConst.CREDIT_CARD));
        params.put(CustomerConst.GENDER, contentValues.getAsString(CustomerConst.GENDER));
        params.put(CustomerConst.NUM_ACCIDENTS, contentValues.getAsInteger(CustomerConst.NUM_ACCIDENTS));
        params.put(CustomerConst.BIRTH_DAY, contentValues.getAsString(CustomerConst.BIRTH_DAY));

        String results = POST(url + "Customer/UpdateCustomer.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public boolean removeCustomer(final int customerID) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CustomerConst.CUSTOMER_ID, customerID);
        String results = POST(url + "Customer/RemoveCustomer.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public ArrayList<Customer> getCustomers() throws Exception {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String results = GET(url + "Customer/GetCustomers.php");
        if (!results.startsWith("0")) {
            JSONArray array = new JSONObject().getJSONArray("customers");
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
        }
        return customers;
    }

    @Override
    public Customer getCustomer(int customerID) throws Exception {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put(CustomerConst.CUSTOMER_ID, customerID);
        String results = POST(url + "Customer/GetCustomer.php", data);
        if (results.startsWith("0"))
            throw new Exception("Record dosn't exist");
        JSONArray array = new JSONObject(results).getJSONArray("customer");
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
    }

    /// promotions

    @Override
    public boolean addPromotion(ContentValues contentValues) throws Exception {

        Map<String, Object> data = new LinkedHashMap<>();

        data.put(PromotionConst.CUSTOMER_ID, contentValues.getAsInteger(PromotionConst.CUSTOMER_ID));
        data.put(PromotionConst.TOTAL_RENT_DAYS, contentValues.getAsInteger(PromotionConst.TOTAL_RENT_DAYS));
        data.put(PromotionConst.IS_USED, contentValues.getAsBoolean(PromotionConst.IS_USED));

        String results = POST(url + "Promotion/AddPromotion.php", data);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

    @Override
    public boolean removePromotion(final int customerID) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(PromotionConst.CUSTOMER_ID, customerID);

        String results = POST(url + "Promotion/RemovePromotion.php", params);
        if (!results.startsWith("New")) {
            throw new Exception("An error occurred on the server's side");
        }
        return true;
    }

/// post and get

    private static String GET(String url) throws ExecutionException, InterruptedException {
        try {
            URL obj = new URL(url);
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
