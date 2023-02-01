package com.parser.k8s;

import com.parser.packer.Ocl2PackerParser;
import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;

/**
 *@Description:
 *@Param:
 *@Return:
 *@DateTime: 11:29 2023/2/1
 */
public class K8SParser implements Ocl2PackerParser {

    public final String type = "k8s";

    static
    {
        try
        {
            ParserManager.registerParser(new K8SParser());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Can't register parser!");
        }
    }

    public String cloudType() {
        return this.type;
    }

    @Override
    public String getHclImages(Provisioner provisioner) {
        if(isTypeCompatible(provisioner.getCloudType())) {
            StringBuilder hcl = new StringBuilder();
            hcl.append("k8s hcl is ok!");
            return hcl.toString();
        }else{
            return "cloudType is wrong!";
        }
    }


    public Boolean isTypeCompatible(String cloudType) {
        return cloudType.equals(type);
    }
}
