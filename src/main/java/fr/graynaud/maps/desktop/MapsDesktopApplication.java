package fr.graynaud.maps.desktop;

import fr.graynaud.maps.javaleaflet.JLMapView;
import fr.graynaud.maps.javaleaflet.JLProperties;
import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;
import fr.graynaud.maps.javaleaflet.listener.event.ClickEvent;
import fr.graynaud.maps.javaleaflet.listener.event.Event;
import fr.graynaud.maps.javaleaflet.model.JLBounds;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMapOption;
import fr.graynaud.maps.javaleaflet.model.JLOptions;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class MapsDesktopApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapsDesktopApplication.class);

    @Override
    public void start(Stage stage) {
        JLMapOption option = new JLMapOption(new JLLatLng(48.925239263078566, 2.183056484769497), new JLProperties.MapType(""),
                                             Set.of(new JLMapOption.Parameter("zoomControl", "true")));
        option.setMinZoom(7);
        option.setMaxBounds(new JLBounds(new JLLatLng(41.2, -10), new JLLatLng(51.45, 15)));

        JLMapView map = new JLMapView(option);

        AnchorPane root = new AnchorPane(map);
        root.setBackground(Background.EMPTY);
        root.setMinHeight(JLProperties.INIT_MIN_HEIGHT_STAGE);
        root.setMinWidth(JLProperties.INIT_MIN_WIDTH_STAGE);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setMinHeight(JLProperties.INIT_MIN_HEIGHT_STAGE);
        stage.setMinWidth(JLProperties.INIT_MIN_WIDTH_STAGE);
        stage.setTitle("Graynaud Maps");
        stage.setScene(scene);
        stage.setMaximized(true);

        map.setMapListener(new OnJLMapViewListener() {
            @Override
            public void mapLoadedSuccessfully(JLMapView mapView) {
                LOGGER.info("map loaded!");
            }

            @Override
            public void mapFailed() {
                LOGGER.error("map failed!");
            }

            @Override
            public void onAction(Event event) {
                if (event instanceof ClickEvent(JLLatLng center)) {
                    LOGGER.info("click event: {}", center);
                    JLOptions jlOptions = new JLOptions();
                    jlOptions.setCloseButton(true);
                    jlOptions.setAutoClose(false);
                    map.getUiLayer().addPopup(center, "New Click Event!", jlOptions);
                }
            }
        });

        stage.show();
    }
}
