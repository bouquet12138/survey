package top.systemsec.survey.bean;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class StreetAndPolice {

    @SerializedName("streets")
    private String[] mStreetList;//街道列表

    @SerializedName("stations")
    private String[] mPoliceList;//警局列表

    public StreetAndPolice(String[] streetList, String[] policeList) {
        mStreetList = streetList;
        mPoliceList = policeList;
    }

    public String[] getStreetList() {
        return mStreetList;
    }

    public void setStreetList(String[] streetList) {
        mStreetList = streetList;
    }

    public String[] getPoliceList() {
        return mPoliceList;
    }

    public void setPoliceList(String[] policeList) {
        mPoliceList = policeList;
    }

    @Override
    public String toString() {
        return "StreetAndPolice{" +
                "mStreetList=" + Arrays.toString(mStreetList) +
                ", mPoliceList=" + Arrays.toString(mPoliceList) +
                '}';
    }
}
