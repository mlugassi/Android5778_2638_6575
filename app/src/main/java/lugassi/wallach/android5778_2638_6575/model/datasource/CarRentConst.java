package lugassi.wallach.android5778_2638_6575.model.datasource;

import android.content.ContentValues;

import lugassi.wallach.android5778_2638_6575.model.entities.*;
import lugassi.wallach.android5778_2638_6575.model.entities.Enums.*;

/**
 * Created by Michael on 13/11/2017.
 */

public class CarRentConst {

    /*
    * Final strings for entities details
    * */
    public static class UserConst {
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";
        public static final String USER_ID = "userID";
    }

    public static class CarConst {
        public static final String RESERVATIONS = "reservations";
        public static final String BRANCH_ID = "branchID";
        public static final String MODEL_CODE = "modelCode";
        public static final String MILEAGE = "mileage";
        public static final String CAR_ID = "carID";
    }

    public static class CarModelConst {
        public static final String MODEL_CODE = "modelCode";
        public static final String MODEL_NAME = "modelName";
        public static final String COMPANY = "company";
        public static final String ENGINE_CAPACITY = "engineCapacity";
        public static final String SEATS = "seats";
        public static final String CAR_TYPE = "carType";
        public static final String COLOR = "color";
        public static final String MAX_GAS_TANK = "maxGasTank";
    }

    public static class BranchConst {
        public static final String BRANCH_ID = "branchID";
        public static final String BRANCH_NAME = "branchName";
        public static final String ADDRESS = "address";
        public static final String MAX_PARKING_SPACE = "maxParkingSpace";
        public static final String ACTUAL_PARKING_SPACE = "actualParkingSpace";
        public static final String CITY = "city";
    }

    public static class CustomerConst {
        public static final String FIRST_NAME = "firstName";
        public static final String FAMILY_NAME = "familyName";
        public static final String CUSTOMER_ID = "customerID";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String CREDIT_CARD = "creditCard";
        public static final String GENDER = "gender";
        public static final String NUM_ACCIDENTS = "numAccidents";
        public static final String BIRTH_DAY = "birthDay";
    }

    public static class ReservationConst {
        public static final String RESERVATION_ID = "resevationID";
        public static final String CUSTOMER_ID = "customerID";
        public static final String CAR_ID = "carID";
        public static final String IS_OPEN = "isOpen";
        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
        public static final String RETURN_DATE = "returnDate";
        public static final String BEGIN_MILEAGE = "beginMileage";
        public static final String FINISH_MILEAGE = "finishMileage";
        public static final String IS_GAS_FULL = "isGasFull";
        public static final String GAS_FILLED = "gasFilled";
        public static final String RESERVATION_COST = "reservationCost";
    }

    public static class PromotionConst {
        public static final String CUSTOMER_ID = "customerID";
        public static final String TOTAL_RENT_DAYS = "totalRentDays";
        public static final String IS_USED = "isUsed";
    }

    /*
    * Assistance methods to cast Content values to entities and entities to Content Values
    * */

    /**
     *
     * @param contentValues
     * @exception if car model doesn't exist
     * @return car model
     */
    public static CarModel contentValuesToCarModel(ContentValues contentValues) {
        CarModel carModel = null;
        try {
            int modelCode = contentValues.getAsInteger(CarModelConst.MODEL_CODE);
            carModel = new CarModel(modelCode);
        } catch (Exception ex) {
            carModel = new CarModel();
        }
        carModel.setCarType(CarType.valueOf(contentValues.getAsString(CarModelConst.CAR_TYPE)));
        carModel.setCompany(Company.valueOf(contentValues.getAsString(CarModelConst.COMPANY)));
        carModel.setEngineCapacity(EngineCapacity.valueOf(contentValues.getAsString(CarModelConst.ENGINE_CAPACITY)));
        carModel.setMaxGasTank(contentValues.getAsInteger(CarModelConst.MAX_GAS_TANK));
        carModel.setModelName(contentValues.getAsString(CarModelConst.MODEL_NAME));
        carModel.setSeats(contentValues.getAsInteger(CarModelConst.SEATS));
        //  carModel.setColor();
        return carModel;
    }

    /**
     *
     * @param contentValues
     * @exception if car doesn't exist
     * @return car
     */
    public static Car contentValuesCar(ContentValues contentValues) {
        Car car = null;
        try {
            int carID = contentValues.getAsInteger(CarConst.CAR_ID);
            car = new Car(carID);
        } catch (Exception ex) {
            car = new Car();
        }
        car.setBranchID(contentValues.getAsInteger(CarConst.BRANCH_ID));
        car.setModelCode(contentValues.getAsInteger(CarConst.MODEL_CODE));
        car.setMileage(contentValues.getAsLong(CarConst.MILEAGE));
        car.setReservations(contentValues.getAsInteger(CarConst.RESERVATIONS));
        return car;
    }

    /**
     *
     * @param contentValues
     * @exception if customer doesn't exist
     * @return customer
     */
    public static Customer contentValuesCustomer(ContentValues contentValues) {
        Customer customer = new Customer();
        customer.setCreditCard(contentValues.getAsLong(CustomerConst.CREDIT_CARD));
        customer.setCustomerID(contentValues.getAsInteger(CustomerConst.CUSTOMER_ID));
        customer.setEmail(contentValues.getAsString(CustomerConst.EMAIL));
        customer.setFamilyName(contentValues.getAsString(CustomerConst.FAMILY_NAME));
        customer.setFirstName(contentValues.getAsString(CustomerConst.FIRST_NAME));
        customer.setGender(Gender.valueOf(contentValues.getAsString(CustomerConst.GENDER)));
        customer.setNumAccidents(contentValues.getAsInteger(CustomerConst.NUM_ACCIDENTS));
        customer.setPhone(contentValues.getAsInteger(CustomerConst.PHONE));
        // customer.setBirthDay(Calendar);
        return customer;
    }

    /**
     *
     * @param contentValues
     * @exception if branch doesn't exist
     * @return branch
     */
    public static Branch contentValuesBranch(ContentValues contentValues) {

        Branch branch = new Branch(contentValues.getAsInteger(BranchConst.BRANCH_ID));
        branch.setAddress(contentValues.getAsString(BranchConst.ADDRESS));
        branch.setMaxParkingSpace(contentValues.getAsInteger(BranchConst.MAX_PARKING_SPACE));
        branch.setCity(contentValues.getAsString(BranchConst.CITY));
        branch.setBranchName(contentValues.getAsString(BranchConst.BRANCH_NAME));
        branch.setActualParkingSpace(contentValues.getAsInteger(BranchConst.ACTUAL_PARKING_SPACE));

        return branch;
    }

    /**
     *
     * @param contentValues
     * @exception if promotion doesn't exist
     * @return promotion
     */
    public static Promotion contentValuesPromotion(ContentValues contentValues) {
        Promotion promotion = new Promotion();
        promotion.setCustomerID(contentValues.getAsInteger(PromotionConst.CUSTOMER_ID));
        promotion.setTotalRentDays(contentValues.getAsInteger(PromotionConst.TOTAL_RENT_DAYS));
        promotion.setUsed(contentValues.getAsBoolean(PromotionConst.IS_USED));
        return promotion;
    }

    /**
     *
     * @param branch
     * @return Content Values
     */
    public static ContentValues branchToContentValues(Branch branch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BranchConst.BRANCH_ID, branch.getBranchID());
        contentValues.put(BranchConst.BRANCH_NAME, branch.getBranchName());
        contentValues.put(BranchConst.ADDRESS, branch.getAddress());
        contentValues.put(BranchConst.CITY, branch.getCity());
        contentValues.put(BranchConst.MAX_PARKING_SPACE, branch.getMaxParkingSpace());
        contentValues.put(BranchConst.ACTUAL_PARKING_SPACE, branch.getActualParkingSpace());

        return contentValues;
    }

    /**
     *
     * @param car
     * @return Content Values
     */
    public static ContentValues carToContentValues(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConst.CAR_ID, car.getCarID());
        contentValues.put(CarConst.BRANCH_ID, car.getBranchID());
        contentValues.put(CarConst.MODEL_CODE, car.getModelCode());
        contentValues.put(CarConst.MILEAGE, car.getMileage());
        contentValues.put(CarConst.RESERVATIONS, car.getReservations());

        return contentValues;
    }

    /**
     *
     * @param carModel
     * @return Content Values
     */
    public static ContentValues carModelToContentValues(CarModel carModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarModelConst.MODEL_CODE, carModel.getModelCode());
        contentValues.put(CarModelConst.MODEL_NAME, carModel.getModelName());
        contentValues.put(CarModelConst.COMPANY, carModel.getCompany().name());
        contentValues.put(CarModelConst.ENGINE_CAPACITY, carModel.getEngineCapacity().name());
        contentValues.put(CarModelConst.SEATS, carModel.getSeats());
        contentValues.put(CarModelConst.CAR_TYPE, carModel.getCarType().name());
        contentValues.put(CarModelConst.MAX_GAS_TANK, carModel.getMaxGasTank());

        return contentValues;
    }

    /**
     *
     * @param customer
     * @return Content Values
     */
    public static ContentValues customerToContentValues(Customer customer) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CustomerConst.CREDIT_CARD, customer.getCreditCard());
        contentValues.put(CustomerConst.CUSTOMER_ID, customer.getCustomerID());
        contentValues.put(CustomerConst.EMAIL, customer.getEmail());
        contentValues.put(CustomerConst.FAMILY_NAME, customer.getFamilyName());
        contentValues.put(CustomerConst.FIRST_NAME, customer.getFirstName());
        contentValues.put(CustomerConst.GENDER, customer.getGender().name());
        contentValues.put(CustomerConst.NUM_ACCIDENTS, customer.getNumAccidents());
        contentValues.put(CustomerConst.PHONE, customer.getPhone());
        contentValues.put(CustomerConst.BIRTH_DAY, customer.getBirthDayString());

        return contentValues;
    }

    /**
     *
     * @param promotion
     * @return Content Values
     */
    public static ContentValues promotionToContentValues(Promotion promotion) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PromotionConst.CUSTOMER_ID, promotion.getCustomerID());
        contentValues.put(PromotionConst.TOTAL_RENT_DAYS, promotion.getTotalRentDays());
        contentValues.put(PromotionConst.IS_USED, promotion.isUsed());

        return contentValues;
    }
}
