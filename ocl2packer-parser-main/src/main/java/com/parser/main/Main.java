package com.parser.main;

import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @ClassName: Main
 * @Author: yy
 * @Date: 2023/1/29 9:16
 * @Version: 1.0
 */
public class Main {

    /**
     * @Description: get hcl script
     * @Param: [provisioner, cloudType]
     * @Return: java.lang.String
     * @DateTime: 15:37 2023/2/8
     */
    public static String getHclScript(Provisioner provisioner, String cloudType) {

        String hcl = ParserManager.getHcl(provisioner, cloudType);
        return hcl;

    }

}
