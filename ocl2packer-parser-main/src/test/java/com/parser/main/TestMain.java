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
public class TestMain {

    public static String getHclScript(Provisioner provisioner, String cloudType) {
        String hcl = ParserManager.getHcl(provisioner, cloudType);
        System.out.println(hcl);
        return hcl;

    }

    public static void main(String[] args) {
        Provisioner provisioner = new Provisioner();
        provisioner.setBase_image("Ubuntu 20.04 server 64bit");
        provisioner.setImage_name("image-demo-yy");

        List InlineList = new ArrayList<>();
        InlineList.add("\"echo \\\"start install docker\\\"\", \"echo \\\"run install\\\"\", "
            + "\"apt-get update\", \"apt install -y docker.io\", \"docker network create app-tier"
            + " --driver bridge\", \"docker run -d --name zookeeper-server --network app-tier -e "
            + "ALLOW_ANONYMOUS_LOGIN=yes bitnami/zookeeper:latest\", \"docker run -d --name "
            + "kafka-server --network app-tier -e ALLOW_PLAINTEXT_LISTENER=yes -e "
            + "KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest\", \"echo "
            + "\\\"@reboot docker restart zookeeper-server ; docker container rm kafka-server ; "
            + "docker run -d --name kafka-server --network app-tier -p 9092:9092  -e "
            + "KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://\\\\`hostname\\\\`:9092 -e "
            + "KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -e "
            + "KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest\\\" | "
            + "crontab -\"");
        provisioner.setInline(InlineList);
        String cloudType = "huaweicloud";
        getHclScript(provisioner, cloudType);
    }

}
