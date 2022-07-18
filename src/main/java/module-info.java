module cellsociety_app {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.xml;
    requires java.desktop;

  exports cellsociety;
  exports cellsociety.model;
  exports cellsociety.view;
  exports cellsociety.configuration;
    exports cellsociety.view.shapes;
    exports cellsociety.view.grid;
    exports cellsociety.model.unit;
}
