package lugassi.wallach.android5778_2638_6575.model.backend;

import android.content.ContentValues;

import java.util.ArrayList;

import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;
import lugassi.wallach.android5778_2638_6575.model.datasource.ListsDataSource;
import lugassi.wallach.android5778_2638_6575.model.entities.Branch;
import lugassi.wallach.android5778_2638_6575.model.entities.Car;
import lugassi.wallach.android5778_2638_6575.model.entities.CarModel;
import lugassi.wallach.android5778_2638_6575.model.entities.Customer;
import lugassi.wallach.android5778_2638_6575.model.entities.Promotion;
import lugassi.wallach.android5778_2638_6575.model.entities.Reservation;

/**
 * Created by Michael on 21/11/2017.
 */

public class DB_List implements DB_manager {

    @Override
    public int addCarModel(ContentValues contentValues) {
        CarModel carModel = CarRentConst.contentValuesToCarModel(contentValues);
        // if car model exists in carModelsList don't do anything
        if (doesCarModelExist(carModel.getModelCode()))
            return -1;
        ListsDataSource.carModelsList.add(carModel);
        return carModel.getModelCode();
    }

    @Override
    public int addCar(ContentValues contentValues) {
        Car car = CarRentConst.contentValuesCar(contentValues);
        // if car  exists in carsList don't do anything
        if (doesCarExist(car.getCarID()))
            return -1;
        ListsDataSource.carsList.add(car);
        return car.getCarID();
    }

    @Override
    public int addCustomer(ContentValues contentValues) {
        Customer customer = CarRentConst.contentValuesCustomer(contentValues);
        // if customer exists in customersList don't do anything
        if (doesCustomerExist(customer.getCustomerID()))
            return -1;
        ListsDataSource.customersList.add(customer);
        return customer.getCustomerID();
    }

    @Override
    public int addBranch(ContentValues contentValues) {
        Branch branch = CarRentConst.contentValuesBranch(contentValues);
        // if branch exists in branchList don't do anything
        if (doesBranchExist(branch.getBranchID()))
            return -1;
        ListsDataSource.branchesList.add(branch);
        return branch.getBranchID();
    }

    @Override
    public int addReservation(ContentValues contentValues) {
        Reservation reservation = CarRentConst.contentValuesReservation(contentValues);
        // if reservation exists in reservationList don't do anything
        if (doesReservationExist(reservation.getReservationID()))
            return -1;
        ListsDataSource.reservationsList.add(reservation);
        return reservation.getReservationID();
    }

    @Override
    public int addPromotion(ContentValues contentValues) {
        Promotion promotion = CarRentConst.contentValuesPromotion(contentValues);
        // if promotion for customer exists in promotionList don't do anything
        if (doesPromotionExist(promotion.getCustomerID()))
            return -1;
        ListsDataSource.promotionsList.add(promotion);
        return promotion.getCustomerID();
    }

    @Override
    public boolean updateCarModel(int modelCode, ContentValues contentValues) {
        CarModel carModel = CarRentConst.contentValuesToCarModel(contentValues);
        carModel.setModelCode(modelCode);
        for (int i = 0; i < ListsDataSource.carModelsList.size(); i++)
            if (ListsDataSource.carModelsList.get(i).getModelCode() == modelCode) {
                ListsDataSource.carModelsList.set(i,carModel);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateCar(int carID, ContentValues contentValues) {
        Car car = CarRentConst.contentValuesCar(contentValues);
        car.setCarID(carID);
        for (int i = 0; i < ListsDataSource.carsList.size(); i++)
            if (ListsDataSource.carsList.get(i).getModelCode() == carID) {
                ListsDataSource.carsList.set(i,car);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateCustomer(int customerID, ContentValues contentValues) {
        Customer customer = CarRentConst.contentValuesCustomer(contentValues);
        customer.setCustomerID(customerID);
        for (int i = 0; i < ListsDataSource.customersList.size(); i++)
            if (ListsDataSource.customersList.get(i).getCustomerID() == customerID) {
                ListsDataSource.customersList.set(i,customer);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateBranch(int branchID, ContentValues contentValues) {
        Branch branch = CarRentConst.contentValuesBranch(contentValues);
        branch.setBranchID(branchID);
        for (int i = 0; i < ListsDataSource.branchesList.size(); i++)
            if (ListsDataSource.branchesList.get(i).getBranchID() == branchID) {
                ListsDataSource.branchesList.set(i,branch);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateReservation(int reservationID, ContentValues contentValues) {
        Reservation reservation = CarRentConst.contentValuesReservation(contentValues);
        reservation.setReservationID(reservationID);
        for (int i = 0; i < ListsDataSource.reservationsList.size(); i++)
            if (ListsDataSource.reservationsList.get(i).getReservationID() == reservationID) {
                ListsDataSource.reservationsList.set(i,reservation);
                return true;
            }
        return false;
    }

    @Override
    public boolean updatePromotion(int customerID, ContentValues contentValues) {
        Promotion promotion = CarRentConst.contentValuesPromotion(contentValues);
        promotion.setCustomerID(customerID);
        for (int i = 0; i < ListsDataSource.promotionsList.size(); i++)
            if (ListsDataSource.promotionsList.get(i).getCustomerID() == customerID) {
                ListsDataSource.promotionsList.set(i,promotion);
                return true;
            }
        return false;
    }

    @Override
    public boolean removeCarModel(int modelCode) {
        CarModel carModel = null;
        for (CarModel item : ListsDataSource.carModelsList)
            if (item.getModelCode() == modelCode) {
                carModel = item;
                break;
            }
        return ListsDataSource.carModelsList.remove(carModel);
    }

    @Override
    public boolean removeCar(int carID) {
        Car car = null;
        for (Car item : ListsDataSource.carsList)
            if (item.getCarID() == carID) {
                car = item;
                break;
            }
        return ListsDataSource.carsList.remove(car);
    }

    @Override
    public boolean removeCustomer(int customerID) {
        Customer customer = null;
        for (Customer item : ListsDataSource.customersList)
            if (item.getCustomerID() == customerID) {
                customer = item;
                break;
            }
        return ListsDataSource.customersList.remove(customer);
    }

    @Override
    public boolean removeBranch(int branchID) {
        Branch branch = null;
        for (Branch item : ListsDataSource.branchesList)
            if (item.getBranchID() == branchID) {
                branch = item;
                break;
            }
        return ListsDataSource.branchesList.remove(branch);
    }

    @Override
    public boolean removeReservation(int reservationID) {
        Reservation reservation = null;
        for (Reservation item : ListsDataSource.reservationsList)
            if (item.getReservationID() == reservationID) {
                reservation = item;
                break;
            }
        return ListsDataSource.reservationsList.remove(reservation);
    }

    @Override
    public boolean removePromotion(int customerID) {
        Promotion promotion = null;
        for (Promotion item : ListsDataSource.promotionsList)
            if (item.getCustomerID() == customerID) {
                promotion = item;
                break;
            }
        return ListsDataSource.promotionsList.remove(promotion);
    }

    @Override
    public ArrayList<Branch> getBranches() {
        ArrayList<Branch> branches = new ArrayList<Branch>();
        for (Branch branch : ListsDataSource.branchesList)
            branches.add(branch);
        return branches;
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        for (Car car : ListsDataSource.carsList)
            cars.add(car);
        return cars;
    }

    @Override
    public ArrayList<CarModel> getCarModels() {
        ArrayList<CarModel> carModels = new ArrayList<CarModel>();
        for (CarModel carModel : ListsDataSource.carModelsList)
            carModels.add(carModel);
        return carModels;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (Customer customer : ListsDataSource.customersList)
            customers.add(customer);
        return customers;
    }

    @Override
    public ArrayList<Promotion> getPromotions() {
        ArrayList<Promotion> promotions = new ArrayList<Promotion>();
        for (Promotion promotion : ListsDataSource.promotionsList)
            promotions.add(promotion);
        return promotions;
    }

    @Override
    public ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        for (Reservation reservation : ListsDataSource.reservationsList)
            reservations.add(reservation);
        return reservations;
    }

    private boolean doesCustomerExist(int customerID) {
        for (Customer item : ListsDataSource.customersList) {
            if (item.getCustomerID() == customerID) {
                //Customer exists in customrsList
                return true;
            }
        }
        // Customer doesn't exist in customrsList
        return false;
    }

    private boolean doesCarModelExist(int modelCode) {
        for (CarModel item : ListsDataSource.carModelsList) {
            if (item.getModelCode() == modelCode) {
                //CarModel exists in carModelsList
                return true;
            }
        }
        // CarModel doesn't exist in carModelsList
        return false;
    }

    private boolean doesCarExist(int carID) {
        for (Car item : ListsDataSource.carsList) {
            if (item.getCarID() == carID) {
                //Car exists in carsList
                return true;
            }
        }
        // Car doesn't exist in carsList
        return false;
    }

    private boolean doesBranchExist(int branchID) {
        for (Branch item : ListsDataSource.branchesList) {
            if (item.getBranchID() == branchID)
                //Branch exists in branchList
                return true;
        }
        // Branch doesn't exist in branchList
        return false;
    }

    private boolean doesPromotionExist(int customerID) {
        for (Promotion item : ListsDataSource.promotionsList) {
            if (item.getCustomerID() == customerID)
                //Promotion for customer exists in PromotionList
                return true;
        }
        // Promotion for customer doesn't exists in PromotionList
        return false;
    }

    private boolean doesReservationExist(int reservationID) {
        for (Reservation item : ListsDataSource.reservationsList) {
            if (item.getReservationID() == reservationID)
                //Reservation exists in reservationList
                return true;
        }
        // Reservation doesn't exist in reservationList
        return false;
    }
}
