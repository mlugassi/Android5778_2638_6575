package lugassi.wallach.android5778_2638_6575.model.backend;

import android.content.ContentValues;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
import lugassi.wallach.android5778_2638_6575.model.entities.Customer;
import lugassi.wallach.android5778_2638_6575.model.entities.Promotion;
import lugassi.wallach.android5778_2638_6575.model.entities.Reservation;


/**
 * Created by Michael on 21/11/2017.
 */

public interface DB_manager {

    public String checkAdmin(String userName, String password);

    public Boolean createAdmin(String userName, String password, int userID);

    public int addCarModel(ContentValues contentValues);

    public int addCar(ContentValues contentValues);

    public int addCustomer(ContentValues contentValues);

    public int addBranch(ContentValues contentValues);


    public boolean updateCarModel(ContentValues contentValues);

    public boolean updateCar(ContentValues contentValues);

    public boolean updateCustomer(ContentValues contentValues);

    public boolean updateBranch(ContentValues contentValues);


    public boolean removeCarModel(int modelCode);

    public boolean removeCar(int carID);

    public boolean removeCustomer(int customerID);

    public boolean removeBranch(int branchID);


    public ArrayList<Branch> getBranches();

    public ArrayList<Car> getCars();

    public ArrayList<CarModel> getCarModels();

    public ArrayList<Customer> getCustomers();

    public Branch getBranch(int branchID);

    public Customer getCustomer(int customerID);

    public CarModel getCarModel(int modelCode);

    public Car getCar(int carID);
}
