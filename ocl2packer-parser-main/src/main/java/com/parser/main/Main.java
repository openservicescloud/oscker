package com.parser.main;

import com.parser.packer.ParserManager;
import com.parser.packer.Provisioner;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @ClassName: Demo
 * @Author: yy
 * @Date: 2023/1/29 9:16
 * @Version: 1.0
 */
public class Main {

    public static void main(String[] args) {
        testHWParser();
    }

    public static void testHWParser() {
        Provisioner provisioner = new Provisioner();
        provisioner.setCloudType("huaweicloud");
        provisioner.setBase_image("Ubuntu 20.04 server 64bit");
        provisioner.setVersion("latest");
        provisioner.setImage_name("image-demo-yy");
        provisioner.setType("shell");

        List envList = new ArrayList<>();
        envList.add("WORK_HOME=/usr1/KAFKA/");
        provisioner.setEnvironments(envList);

        List InlineList = new ArrayList<>();
        InlineList.add("\"echo \\\"start install docker\\\"\"");
        InlineList.add("\"echo \\\"run install\\\"\"");
        InlineList.add("\"apt-get update\"");
        InlineList.add("\"apt install -y docker.io\"");
        InlineList.add("\"docker network create app-tier --driver bridge\"");
        InlineList.add("\"docker run -d --name zookeeper-server --network app-tier -e "
            + "ALLOW_ANONYMOUS_LOGIN=yes bitnami/zookeeper:latest\"");
        InlineList.add("\"docker run -d --name kafka-server --network app-tier -e "
            + "ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 "
            + "bitnami/kafka:latest\"");
        InlineList.add("\"echo \\\"@reboot docker restart zookeeper-server ; docker container rm "
            + "kafka-server ; docker run -d --name kafka-server --network app-tier -p 9092:9092  "
            + "-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://\\\\`hostname\\\\`:9092 -e "
            + "KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -e "
            + "KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181 bitnami/kafka:latest\\\" | "
            + "crontab -\"");
        provisioner.setInline(InlineList);

        String hclImages = ParserManager.getHcl(provisioner);
        System.out.println(hclImages);
    }
}
