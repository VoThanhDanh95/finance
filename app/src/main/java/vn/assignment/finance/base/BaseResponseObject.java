package vn.assignment.finance.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 3/15/17.
 */

public class BaseResponseObject<T> {
    @SerializedName("base")
    private String base;
    @SerializedName("date")
    private String date;

    @SerializedName("rates")
    private T data;

    public String getBase() {
        return base == null ? "" : base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
