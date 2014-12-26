package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.NodeMeaningKeyErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

public class TimeNodeMeaningMapper implements NodeMeaningMapper {
    private int baseYear = 1850;
    private int minYear = 0;
    private int maxYear = 1 - 1;
    private int minMonth = 0;
    private int maxMonth = 12 - 1;
    private int minDay = 0;
    private int maxDay = 31 - 1;

    public TimeNodeMeaningMapper() {

    }

    public TimeNodeMeaningMapper(int minYear, int maxYear, int minMonth,
                                 int maxMonth, int minDay, int maxDay) {
        super();
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        this.minDay = minDay;
        this.maxDay = maxDay;
    }

    @Override
    public String getMeaning(String key) throws NumberFormatException,
            NodeMeaningKeyErrorException {
        System.err
                .println(key + "|||" + minYear + "-" + maxYear + "/" + minMonth
                        + "-" + maxMonth + "/" + minDay + "-" + maxDay + "/");
        String parts[] = key.split(SystemConf.getNodeMeaningKeyDelimiter());
        if (parts.length == 1) {
            return map(Integer.valueOf(parts[0]), minMonth, minDay);
        } else if (parts.length == 2) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    minDay);
        } else if (parts.length == 3) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    Integer.valueOf(parts[2]));
        } else {
            throw new NodeMeaningKeyErrorException();
        }
    }

    private String map(int year, int month, int day)
            throws NodeMeaningKeyErrorException {
        System.err.println(year + "/" + month + "/" + day);
        year -= baseYear;
        if (year < minYear || year > maxYear) {
            throw new NodeMeaningKeyErrorException();
        }
        if (month < minMonth || month > maxMonth) {
            throw new NodeMeaningKeyErrorException();
        }
        if (day < minDay || day > maxDay) {
            throw new NodeMeaningKeyErrorException();
        }
        year -= minYear;
        month -= minMonth;
        day -= minDay;
        int monthLength = maxMonth - minMonth + 1;
        int dayLength = maxDay - minDay + 1;
        int value = year * monthLength * dayLength + month * dayLength + day;
        return String.valueOf(value);
    }
}
