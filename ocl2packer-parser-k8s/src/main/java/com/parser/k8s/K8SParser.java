package com.parser.k8s;

import com.parser.packer.Ocl2PackerParser;
import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;

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
            hcl.append("k8s hcl创建完成!");
            return hcl.toString();
        }else{
            return "2222";
        }
    }


    public Boolean isTypeCompatible(String cloudType) {
        return cloudType.equals(type);
    }
}
