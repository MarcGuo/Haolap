package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.ConditionBean;

import java.util.List;

public interface OLAPTaskXmlDriver {
    public String getTimestamp();

    public String getKey();

    public String getAggregation();

    public String getFromCubeIdentifier();

    public List<ConditionBean> getConditionBeans();
}
