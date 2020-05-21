package com.dyy.tsp.evgb.gateway.exe;

import com.dyy.tsp.evgb.gateway.exe.gui.MainPanel;
import com.dyy.tsp.evgb.gateway.exe.handler.BusinessHandler;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import javax.swing.*;

@SpringBootApplication
public class ExeApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            new MainPanel(new BusinessHandler());
        });
        new SpringApplicationBuilder(ExeApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
