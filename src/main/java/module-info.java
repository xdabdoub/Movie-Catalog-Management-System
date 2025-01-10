module me.yhamarsheh.moviesms.moviesms {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.yhamarsheh.moviesms.moviesms to javafx.fxml;
    exports me.yhamarsheh.moviesms.moviesms.managers;
    exports me.yhamarsheh.moviesms.moviesms.ui;
    exports me.yhamarsheh.moviesms.moviesms.structure;
    exports me.yhamarsheh.moviesms.moviesms.utilities;
    exports me.yhamarsheh.moviesms.moviesms.ui.screens;
    exports me.yhamarsheh.moviesms.moviesms.ui.screens.sub;
    exports me.yhamarsheh.moviesms.moviesms.objects;
    exports me.yhamarsheh.moviesms.moviesms.storage;
    exports me.yhamarsheh.moviesms.moviesms;
}