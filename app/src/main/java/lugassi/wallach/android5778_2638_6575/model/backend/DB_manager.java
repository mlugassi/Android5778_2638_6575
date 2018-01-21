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

    public String checkAdmin(String userName, String password) throws Exception;

    public String createAdmin(String userName, String password, int userID) throws Exception;

    public String addCarModel(ContentValues contentValues) throws Exception;

    public String addCar(ContentValues contentValues) throws Exception;

    public String addCustomer(ContentValues contentValues) throws Exception;

    public String addBranch(ContentValues contentValues) throws Exception;


    public String updateCarModel(ContentValues contentValues) throws Exception;

    public String updateCar(ContentValues contentValues) throws Exception;

    public String updateCustomer(ContentValues contentValues) throws Exception;

    public String updateBranch(ContentValues contentValues) throws Exception;


    public String removeCarModel(int modelCode) throws Exception;

    public String removeCar(int carID) throws Exception;

    public String removeCustomer(int customerID) throws Exception;

    public String removeBranch(int branchID) throws Exception;


    public ArrayList<Branch> getBranches() throws Exception;

    public ArrayList<Car> getCars() throws Exception;

    public ArrayList<CarModel> getCarModels() throws Exception;

    public ArrayList<Customer> getCustomers() throws Exception;

    public Branch getBranch(int branchID) throws Exception;

    public Customer getCustomer(int customerID) throws Exception;

    public CarModel getCarModel(int modelCode) throws Exception;

    public Car getCar(int carID) throws Exception;

    public String removePromotion(final int customerID) throws Exception;

    public String addPromotion(ContentValues contentValues) throws Exception;
}
