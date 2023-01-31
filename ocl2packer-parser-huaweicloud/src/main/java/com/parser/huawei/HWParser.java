package com.parser.huawei;


import com.parser.packer.Ocl2PackerParser;
import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;

public class HWParser implements Ocl2PackerParser {

    public final String type = "huaweicloud";

    static
    {
        try
        {
            ParserManager.registerParser(new HWParser());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Can't register parser!");
        }
    }

    public String cloudType() {
        return this.type;
    }

    public String getHclImages(Provisioner provisioner) {
        if(isTypeCompatible(provisioner)) {
            StringBuilder hcl = new StringBuilder();
            hcl.append(String.format(""
                    + "packer {\n"
                    + "  required_plugins {\n"
                    + "    huaweicloud-ecs = {\n"
                    + "      version = \">= 0.4.0\"\n"
                    + "      source = \"github.com/huaweicloud/huaweicloud\"\n"
                    + "    }\n"
                    + "  }\n"
                    + "}"
                    + "%nvariable \"region_name\" {"
                    + "%n  type = string"
                    + "%n  default = env(\"HW_REGION_NAME\")"
                    + "%n}"
                    + "%nvariable \"secret_key\" {"
                    + "%n  type = string"
                    + "%n  default = env(\"HW_SECRET_KEY\")"
                    + "%n}"
                    + "%nvariable \"access_key\" {"
                    + "%n  type = string"
                    + "%n  default = env(\"HW_ACCESS_KEY\")"
                    + "%n}"
                    + "%nsource \"huaweicloud-ecs\" huaweicloud {"
                    + "%n  region             = var.region_name"
                    + "%n  image_name         = \"%s\""
                    + "%n  access_key         = var.access_key"
                    + "%n  secret_key         = var.secret_key"
                    + "%n  eip_bandwidth_size = \"5\""
                    + "%n  eip_type           = \"5_sbgp\""
                    + "%n  flavor             = \"s6.large.2\""
                    + "%n  instance_name      = \"%s\""
                    + "%n  vpc_id             = vpc_id"
                    + "%n  subnets            = subnets"
                    + "%n  security_groups    = security_groups"
                    + "%n  source_image_filter {"
                    + "%n    filters {"
                    + "%n      name = \"%s\""
                    + "%n      visibility = \"public\""
                    + "%n    }"
                    + "%n    most_recent = true"
                    + "%n  }"
                    + "%n  ssh_ip_version     = \"4\""
                    + "%n  ssh_username       = \"root\""
                    + "%n}%n",
                provisioner.getImage_name(),provisioner.getImage_name(),provisioner.getBase_image()));

            hcl.append(String.format("%nbuild {"
                    + "%n  sources = [\"source.huaweicloud-ecs.%s\"]"
                    + "%n  provisioner \"shell\" {"
                    + "%n    pause_before = \"30s\""
                    + "%n    inline       = %s"
                    + "%n  }"
                    + "%n}%n",
                provisioner.getImage_name(),provisioner.getInline()));
            return hcl.toString();
        }else{
            System.out.println("hcl创建失败");
            return "2";
        }
    }

    public Boolean isTypeCompatible(Provisioner provisioner) {
        return provisioner.getCloudType().equals(type);
    }
}