package AppBuilder;

import AppBuilder.Interfaces.Configure;
import AppConfiguration.ApplicationConfiguration;

public class AppBuilder {

    public static void buildAndStart() {
        Configure configure = new ApplicationConfiguration();
        configure.configure();
    }
}
