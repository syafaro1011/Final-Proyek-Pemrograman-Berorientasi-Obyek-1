module com.mycompany.food_order_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    exports com.mycompany.food_order_app.controller;
    opens com.mycompany.food_order_app.controller to javafx.fxml;
    opens com.mycompany.food_order_app to javafx.fxml;
    exports com.mycompany.food_order_app;
    exports com.mycompany.food_order_app.model;
    
}
