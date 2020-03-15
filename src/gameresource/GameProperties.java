package gameresource;
/*
 * 用于配置文件的载入和保存
 */

import java.io.File;
import java.util.Properties;

public class GameProperties {
    private Properties gamePro;
    //获取配置文件路径，存于jar包同级目录下
    private String filePath = new File(
            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
    ).getParent() + "配置文件.propertise";
    private File proFile = new File(filePath);

    public GameProperties() {
        gamePro = new Properties();
        if (proFile.exists()){

        } else {

        }
    }


    private Properties getDefaultPro() {
        //Properties dPro
        return null;
    }


}
