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

    private String url = "http://mlugassi.vlab.jct.ac.il/";

    @Override
    public int addCarModel(ContentValues contentValues) {
        return 0;
    }

    @Override
    public int addCar(ContentValues contentValues) {
        return 0;
    }

    @Override
    public int addCustomer(ContentValues contentValues) {
        return 0;
    }

    @Override
    public int addBranch(ContentValues branch) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(BranchConst.BRANCH_ID, branch.getAsInteger(BranchConst.BRANCH_ID));
            params.put(BranchConst.BRANCH_NAME, branch.getAsString(BranchConst.BRANCH_NAME));
            params.put(BranchConst.ADDRESS, branch.getAsString(BranchConst.ADDRESS));
            params.put(BranchConst.CITY, branch.getAsString(BranchConst.CITY));
            params.put(BranchConst.MAX_PARKING_SPACE, branch.getAsInteger(BranchConst.MAX_PARKING_SPACE));
            params.put(BranchConst.ACTUAL_PARKING_SPACE, 0);

            String results = POST(url + "AddBranch.php", params);
            if (results.equals("")) {
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return branch.getAsInteger(BranchConst.BRANCH_ID);
    }

    @Override
    public int addReservation(ContentValues contentValues) {
        return 0;
    }

    @Override
    public int addPromotion(ContentValues contentValues) {
        return 0;
    }

    @Override
    public boolean updateCarModel(int modelCode, ContentValues contentValues) {
        return false;
    }

    @Override
    public boolean updateCar(int carID, ContentValues contentValues) {
        return false;
    }

    @Override
    public boolean updateCustomer(int customerID, ContentValues contentValues) {
        return false;
    }

    @Override
    public boolean updateBranch(int branchID, ContentValues contentValues) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();

            params.put(BranchConst.BRANCH_ID, contentValues.getAsInteger(BranchConst.BRANCH_ID));
            params.put(BranchConst.BRANCH_NAME, contentValues.getAsString(BranchConst.BRANCH_NAME));
            params.put(BranchConst.ADDRESS, contentValues.getAsString(BranchConst.ADDRESS));
            params.put(BranchConst.CITY, contentValues.getAsString(BranchConst.CITY));
            params.put(BranchConst.MAX_PARKING_SPACE, contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
            params.put(BranchConst.ACTUAL_PARKING_SPACE, contentValues.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

            String results = POST(url + "UpdateBranch.php", params);
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

    @Override
    public boolean removeCarModel(int modelCode) {
        return false;
    }

    @Override
    public boolean removeCar(int carID) {
        return false;
    }

    @Override
    public boolean removeCustomer(int customerID) {
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

                        String results = POST(url + "DeleteBranch.php", myParams);
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

    @Override
    public ArrayList<Branch> getBranches() {

        ArrayList<Branch> branches = new ArrayList<Branch>();
        try {
            JSONArray array = new JSONObject(GET(url + "GetBranches.php")).getJSONArray("branches");
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
        return null;
    }

    @Override
    public ArrayList<CarModel> getCarModels() {
        return null;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        return null;
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
