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

public class DB_SQL  implements DB_manager {
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
    public int addBranch(ContentValues contentValues) {
        return 0;
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
        return false;
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
        return null;
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
}
