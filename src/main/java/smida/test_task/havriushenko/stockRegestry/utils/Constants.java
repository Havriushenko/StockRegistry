package smida.test_task.havriushenko.stockRegestry.utils;

public interface Constants {

    interface StatusConstants {
        String ACTIVE_STATUS = "active";
        String DELETED_STATUS = "deleted";
    }

    interface ExceptionMessages {

        String SPACE = " ";

        String STOCK_WITH = "Stock with ";
        String USREOU_WAS_EXIST = " USREOU was exist!";

        String STOCK_WAS_NOT_FOUND_WITH = "Stock was not found with ";

        String WRITE_WRONG_STATUS = "Write wrong status: '";
        String STATUS_COULD_BE_ACTIVE_OR_DELETED = "' ! Status could be active or deleted!";
    }

    interface FieldNames {
        String PK_FIELD_NAME = "pk";
        String USREOU_FIELD_NAME = "USREOU";
        String COMMENT_FIELD_NAME = "comment";
        String TOTAL_NOMINAL_VALUE_FIELD_NAME = "totalNominalValue";
        String NOMINAL_VALUE_FIELD_NAME = "nominalValue";
        String QUANTITY_FIELD_NAME = "quantity";
    }
}
