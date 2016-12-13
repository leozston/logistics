package algorithm;

import com.google.common.collect.Lists;
import com.google.common.collect.MapConstraint;

import java.util.List;

/**
 * Created by leoz on 2016/12/13.
 */
public class LogisticAlgorithm {

    private static String trainPath = "F:\\data\\test1\\train.txt";
    private static String testPath = "F:\\data\\test1\\test.txt";
    private static double FACTOR = 0.001d;

    private static double epsilon = 0.0001d;
    /**
     *批量梯度下降
     * */
    public double[] batchAlgorithm() {
        //读数据
        List<RegressionBean> regressionBeanList = Utils.getRegressionBeans(trainPath);
        double[] new_value = new double[3];
        //赋初值为<1,1,1>
        new_value[0] = 1;
        new_value[1] = 1;
        new_value[2] = 1;

        double[] old_value = new double[3];

        int times = 0;
        while(times < 10000) {  //1w次自动退出
            for (int j = 0; j < new_value.length; j++) {
                old_value[j] = new_value[j] - FACTOR * batchLogisticGradient(j, new_value, regressionBeanList);
            }
            if (isLinerSame(new_value, old_value, regressionBeanList)) {
                new_value = old_value;
                break;
            }
            new_value = old_value;
        }

        return new_value;
    }

    private double batchLinerGradient(int j, double new_value[], List<RegressionBean> regressionBeanList) {
        double result = 0d;
        for (int i =0; i < regressionBeanList.size(); i++) {
            if (j == 0) {
                result += (new_value[0] * regressionBeanList.get(i).getX() + new_value[1] * regressionBeanList.get(i).getY() + new_value[2] * 1 - regressionBeanList.get(i).getValue()) * regressionBeanList.get(i).getX();
            } else {
                result += (new_value[0] * regressionBeanList.get(i).getX() + new_value[1] * regressionBeanList.get(i).getY() + new_value[2] * 1 - regressionBeanList.get(i).getValue()) * regressionBeanList.get(i).getY();
            }
        }
        return result;
    }

    private double batchLogisticGradient(int j, double new_value[], List<RegressionBean> regressionBeanList) {
        double result = 0d;
        for (int i =0; i < regressionBeanList.size(); i++) {
            if (j == 0) {
                result += (regressionBeanList.get(i).getValue() - funcLogistic(new_value, regressionBeanList.get(i))) * regressionBeanList.get(i).getX();
            } else {
                result += (regressionBeanList.get(i).getValue() - funcLogistic(new_value, regressionBeanList.get(i))) * regressionBeanList.get(i).getY();
            }
        }
        return result;
    }

    private double funLiner(double param[], RegressionBean regressionBean) {
        double value = 0d;
        return param[0] * regressionBean.getX() + param[1] * regressionBean.getY() + param[2] * 1;
    }

    private double funcLogistic(double param[], RegressionBean regressionBean) {
        double value = 0d;
        double z = param[0] * regressionBean.getX() + param[1] * regressionBean.getY() + param[2] * 1;
        value = 1 / (1 + Math.pow(Math.E, 0 - z));
        return value;
    }

    private boolean isLinerSame(double[] new_value, double[] old_value, List<RegressionBean> regressionBeanList) {
        double newValue = 0d;
        double oldValue = 0d;

        newValue = funcLinerValue(new_value, regressionBeanList);
        oldValue = funcLinerValue(old_value, regressionBeanList);
        if (Math.abs(newValue - oldValue) < epsilon) {
            return true;
        }
        return false;
    }

    private boolean isLogisticSame(double[] new_value, double[] old_value, List<RegressionBean> regressionBeanList) {
        double newValue = 0d;
        double oldValue = 0d;

        newValue = funcLogisticValue(new_value, regressionBeanList);
        oldValue = funcLogisticValue(old_value, regressionBeanList);
        if (Math.abs(newValue - oldValue) < epsilon) {
            return true;
        }
        return false;
    }

    private double funcLinerValue(double[] value, List<RegressionBean> regressionBeanList) {
        double destFunc = 0;
        for (int i = 0; i < regressionBeanList.size(); i++) {
            destFunc +=Math.pow((funLiner(value, regressionBeanList.get(i)) -regressionBeanList.get(i).getValue()), 2);
        }

        return destFunc;
    }

    private double funcLogisticValue(double[] value, List<RegressionBean> regressionBeanList) {
        double destFunc = 0;
        for (int i = 0; i < regressionBeanList.size(); i++) {
            destFunc += regressionBeanList.get(i).getValue() * (Math.log(funcLogistic(value, regressionBeanList.get(i)))) + (1 - regressionBeanList.get(i).getValue()) * (Math.log(1 - funcLogistic(value, regressionBeanList.get(i))));
        }

        return destFunc;
    }

    public void testLogistic(double[] value) {
        List<RegressionBean> testrRegressionBeanList = Utils.getRegressionBeans(testPath);
        for (int i = 0; i < testrRegressionBeanList.size(); i++) {
            double tmp = this.funcLogistic(value, testrRegressionBeanList.get(i));
            int a = 0;
        }
    }

    /**
     * 随机梯度下降
     * */
    public void stochasticAlgorithm() {

    }

    public static void main(String[] args) {
        LogisticAlgorithm logisticAlgorithm = new LogisticAlgorithm();
        double[] value = logisticAlgorithm.batchAlgorithm();
        logisticAlgorithm.testLogistic(value);
    }
}
