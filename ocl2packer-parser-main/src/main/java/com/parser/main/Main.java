package com.parser.main;

import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;



/**
 * @Description:
 * @ClassName: Main
 * @Author: yy
 * @Date: 2023/1/29 9:16
 * @Version: 1.0
 */
public class Main {

    /**
     *@Description: get hcl script
     *@Param: [provisioner]
     *@Return: java.lang.String
     *@DateTime: 10:54 2023/2/2
     */
    public static String getHclScript(Provisioner provisioner){
        String hcl = ParserManager.getHcl(provisioner);
        return hcl;
    }

}
