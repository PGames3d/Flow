package com.pgames.flow;

import java.util.HashMap;
import java.util.Map;

public class DataTransportor {
  public static Map<String,Object> mDataMap = new HashMap<>();

    public Map<String, Object> getmUserMap() {
        return mDataMap;
    }

    public void setmUserMap(Map<String, Object> dataMap) {
        DataTransportor.mDataMap = dataMap;
    }
}
