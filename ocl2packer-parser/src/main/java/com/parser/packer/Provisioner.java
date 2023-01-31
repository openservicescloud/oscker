package com.parser.packer;

import java.util.List;
import lombok.Data;

@Data
public class Provisioner {

    private String cloudType;
    private String base_image;
    private String version;
    private String image_name;
    private String type;
    private List<String> environments;
    private List<String> inline;

}
