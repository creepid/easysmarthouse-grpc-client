/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.easysmarthouse.example;

import by.ginger.smarthome.provider.device.trigger.Trigger;
import com.github.creepid.grpc.client.GRPC;
import com.github.creepid.grpc.client.RequestHeader;
import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import by.ginger.smarthome.ui.webui.client.rpc.TriggerService;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class TriggersShowExample {

    private static final int ITERATIONS = 20;

    private static final GRpcSettings SETTINGS = new GRpcSettings();
    private static TriggerService service;

    static {
        SETTINGS.put(GRpcSetting.BASE_URL, "http://localhost:8080/webui/webui/");
        SETTINGS.put(GRpcSetting.POLICY_FILE_STRONG_NAME, "7EE387C033B24AAAA85E37BE94C12928");
        SETTINGS.put(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER,
                new RequestHeader("Accept-Language", "en-gb,en;q=0.5"));

        service = (TriggerService) GRPC.create(TriggerService.class, SETTINGS);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            List<Trigger> triggers = service.getTriggers();
            for (Trigger trigger : triggers) {
                System.out.println("------------------------------");
                System.out.println("Address: " + trigger.getName());
                System.out.println("Enabled: " + trigger.isEnabled());
            }
            Thread.sleep(1000l);
        }
    }

}
