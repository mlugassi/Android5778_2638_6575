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

    public Boolean createAdmin(String userName, String password, int userID) throws Exception;

    public int addCarModel(ContentValues contentValues) throws Exception;

    public int addCar(ContentValues contentValues) throws Exception;

    public int addCustomer(ContentValues contentValues) throws Exception;

    public int addBranch(ContentValues contentValues) throws Exception;


    public boolean updateCarModel(ContentValues contentValues) throws Exception;

    public boolean updateCar(ContentValues contentValues) throws Exception;

    public boolean updateCustomer(ContentValues contentValues) throws Exception;

    public boolean updateBranch(ContentValues contentValues) throws Exception;


    public boolean removeCarModel(int modelCode) throws Exception;

    public boolean removeCar(int carID) throws Exception;

    public boolean removeCustomer(int customerID) throws Exception;

    public boolean removeBranch(int branchID) throws Exception;


    public ArrayList<Branch> getBranches() throws Exception;

    public ArrayList<Car> getCars() throws Exception;

    public ArrayList<CarModel> getCarModels() throws Exception;

    public ArrayList<Customer> getCustomers() throws Exception;

    public Branch getBranch(int branchID) throws Exception;

    public Customer getCustomer(int customerID) throws Exception;

    public CarModel getCarModel(int modelCode) throws Exception;

    public Car getCar(int carID) throws Exception;

    public boolean removePromotion(final int customerID) throws Exception;

    public boolean addPromotion(ContentValues contentValues) throws Exception;
}
