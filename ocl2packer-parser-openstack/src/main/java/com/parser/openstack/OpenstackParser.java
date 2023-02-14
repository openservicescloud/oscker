package com.parser.openstack;

import com.parser.packer.Ocl2PackerParser;
import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;

/**
 * @Description:
 * @Param:
 * @Return:
 * @DateTime: 11:29 2023/2/1
 */
public class OpenstackParser implements Ocl2PackerParser {

    public final String type = "openstack";

    static {
        try {
            ParserManager.registerParser(new OpenstackParser());
        } catch (Exception e) {
            throw new RuntimeException("Can't register parser!");
        }
    }

    public String type() {
        return this.type;
    }

    @Override
    public String getHclImages(Provisioner provisioner, String cloudType) {
        if (isTypeCompatible(cloudType)) {
            StringBuilder hcl = new StringBuilder();
            hcl.append("openstack hcl is ok!");
            return hcl.toString();
        } else {
            return "cloudType is wrong!";
        }
    }


    public Boolean isTypeCompatible(String cloudType) {
        return cloudType.equals(type);
    }
}
