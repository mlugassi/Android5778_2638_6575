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

/**
 * Created by Michael on 21/11/2017.
 */

public class DB_List implements DB_manager {

    @Override
    public String checkAdmin(String userName, String password) {
        return "Success";
    }

    @Override
    public Boolean createAdmin(String userName, String password, int userID) {
        return true;
    }

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
        addPromotion(CarRentConst.promotionToContentValues(new Promotion(customer.getCustomerID())));
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


    public boolean addPromotion(ContentValues contentValues) {
        Promotion promotion = CarRentConst.contentValuesPromotion(contentValues);
        // if promotion for customer exists in promotionList don't do anything
        if (doesPromotionExist(promotion.getCustomerID()))
            return false;
        ListsDataSource.promotionsList.add(promotion);
        return true;
    }

    @Override
    public boolean updateCarModel(ContentValues contentValues) {
        CarModel carModel = CarRentConst.contentValuesToCarModel(contentValues);
        for (int i = 0; i < ListsDataSource.carModelsList.size(); i++)
            if (ListsDataSource.carModelsList.get(i).getModelCode() == carModel.getModelCode()) {
                ListsDataSource.carModelsList.set(i, carModel);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateCar(ContentValues contentValues) {
        Car car = CarRentConst.contentValuesCar(contentValues);
        for (int i = 0; i < ListsDataSource.carsList.size(); i++)
            if (ListsDataSource.carsList.get(i).getModelCode() == car.getCarID()) {
                ListsDataSource.carsList.set(i, car);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateCustomer(ContentValues contentValues) {
        Customer customer = CarRentConst.contentValuesCustomer(contentValues);
        for (int i = 0; i < ListsDataSource.customersList.size(); i++)
            if (ListsDataSource.customersList.get(i).getCustomerID() == customer.getCustomerID()) {
                ListsDataSource.customersList.set(i, customer);
                return true;
            }
        return false;
    }

    @Override
    public boolean updateBranch(ContentValues contentValues) {
        Branch branch = CarRentConst.contentValuesBranch(contentValues);
        // branch.setBranchID(branchID);
        for (int i = 0; i < ListsDataSource.branchesList.size(); i++)
            if (ListsDataSource.branchesList.get(i).getBranchID() == branch.getBranchID()) {
                ListsDataSource.branchesList.set(i, branch);
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
        removePromotion(customerID);
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
    public Branch getBranch(int branchID) throws Exception {
        for (Branch branch : ListsDataSource.branchesList)
            if (branch.getBranchID() == branchID)
                return branch;
        return null;
    }

    @Override
    public Customer getCustomer(int customerID) throws Exception {
        for (Customer customer : ListsDataSource.customersList)
            if (customer.getCustomerID() == customerID)
                return customer;
        return null;
    }

    @Override
    public CarModel getCarModel(int modelCode) throws Exception {
        for (CarModel carModel : ListsDataSource.carModelsList)
            if (carModel.getModelCode() == modelCode)
                return carModel;
        return null;
    }

    @Override
    public Car getCar(int carID) throws Exception {
        for (Car car : ListsDataSource.carsList)
            if (car.getCarID() == carID)
                return car;
        return null;
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

}
