package lugassi.wallach.android5778_2638_6575.model.backend;

import android.content.ContentValues;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
import lugassi.wallach.android5778_2638_6575.model.entities.Customer;


/**
 * Created by Michael on 21/11/2017.
 */

public interface DB_manager {

    public String checkAdmin(String userName, String password);

    public String createAdmin(String userName, String password, int userID);

    public String addCarModel(ContentValues contentValues);

    public String addCar(ContentValues contentValues);

    public String addCustomer(ContentValues contentValues);

    public String addBranch(ContentValues contentValues);


    public String updateCarModel(ContentValues contentValues);

    public String updateCar(ContentValues contentValues);

    public String updateCustomer(ContentValues contentValues);

    public String updateBranch(ContentValues contentValues);


    public String removeCarModel(int modelCode);

    public String removeCar(int carID);

    public String removeCustomer(int customerID);

    public String removeBranch(int branchID);


    public ArrayList<Branch> getBranches() throws Exception;

    public ArrayList<Car> getCars() throws Exception;

    public ArrayList<CarModel> getCarModels() throws Exception;

    public ArrayList<Customer> getCustomers() throws Exception;

    public Branch getBranch(int branchID) throws Exception;

    public Customer getCustomer(int customerID) throws Exception;

    public CarModel getCarModel(int modelCode) throws Exception;

    public Car getCar(int carID) throws Exception;

    public String removePromotion(final int customerID);

    public String addPromotion(ContentValues contentValues);
}
