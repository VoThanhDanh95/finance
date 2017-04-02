package vn.assignment.finance.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ray on 3/15/17.
 */

public class TransferResponse {
    @PrimaryKey
    private String code;

    private String name;
    private double buy, transfer, sell;

    public TransferResponse(String code, String name, double buy, double transfer, double sell) {
        this.code = code;
        this.name = name;
        this.buy = buy;
        this.transfer = transfer;
        this.sell = sell;
    }

    public TransferResponse() {
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getTransfer() {
        return transfer;
    }

    public void setTransfer(double transfer) {
        this.transfer = transfer;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return code == null ? "" : code;
    }
}
