package algorithm;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

/**
 * Created by leoz on 2016/12/13.
 */
public class Utils {
    public static List<RegressionBean> getRegressionBeans(String path) {
        List<RegressionBean> regressionBeanList = Lists.newArrayList();

        File file=new File(path);
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            System.out.println("***********文件路径错误*********");
            e.printStackTrace();
            return regressionBeanList;
        }
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    break;
                }
                RegressionBean regressionBean = new RegressionBean();
                String[] properties = line.split(",");
                for (int i = 0; i < properties.length; i++) {
                    switch (i) {
                        case 0:
                            regressionBean.setX(Double.parseDouble(properties[0]));
                            break;
                        case 1:
                            regressionBean.setY(Double.parseDouble(properties[1]));
                            break;
                        case 2:
                            regressionBean.setValue(Integer.parseInt(properties[i]));
                            break;
                        default:
                            break;
                    }
                }
                regressionBeanList.add(regressionBean);
            }
        } catch (IOException e) {
            System.out.println("***********读取文件失败************");
            e.printStackTrace();
        }
        try {
            br.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("***********关闭读文件流失败************");
            e.printStackTrace();
        }
        return regressionBeanList;
    }
}
