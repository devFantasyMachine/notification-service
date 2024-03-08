package cm.fantasymachine.notification.application.websocket;

import jakarta.servlet.ServletContext;
import jakarta.websocket.server.ServerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;




@Configuration
@EnableWebSocket
@RequestMapping(path = "/*")
public class WebSocketConfig implements WebSocketConfigurer {


    @Autowired
    WebsocketController websocketController;


    @Autowired
    ServletContext servletContext;



    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {

        ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute("jakarta.websocket.server.ServerContainer");

        if (serverContainer == null)
            return null;

        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(4096000);
        container.setMaxBinaryMessageBufferSize(4096000);
        return container;
    }




/*    @Bean
    public DefaultHandshakeHandler handshakeHandler() {

        WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setInputBufferSize(4096000);
        policy.setIdleTimeout(4096000);

        return new DefaultHandshakeHandler(
                new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
    }*/




    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //
        registry.addHandler(websocketController, "/listen-notification")
                //.addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");

    }


}

















