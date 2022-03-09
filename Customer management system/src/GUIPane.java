import com.sun.deploy.net.MessageHeader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIPane extends BorderPane {


    protected final ObservableList<Customer> data = FXCollections.observableArrayList();



//
//    protected ToolBar toolBar = new ToolBar(
//            new Button("Add Customer"),
//            new Button("Delete Customer"),
//            new Button("Update Customer"),
//            new Button("Help"),
//            new Button("Exit")
//    );



    protected ToolBar toolBar = new ToolBar();

    protected Button addCustomer = new Button("Add Customer");
    protected Button deleteCustomer = new Button("Delete Customer");
    protected Button updateCustomer = new Button("Update Customer");
    protected Button help = new Button("Help");
    protected Button exit = new Button("Exit");


//    private MenuBar menuBar = new MenuBar();
//    protected Menu mainMenu = new Menu("Main Menu");
//    protected Menu addCustomer = new Menu("Add Customer");
//    protected Menu deleteCustomer = new Menu("Delete Customer");
//    protected Menu updateCustomer = new Menu("Update Customer");
//    protected Menu help = new Menu("Help");
//    protected Menu exit = new Menu("Exit");


    protected Button showAllCustomers = new Button("Show customers");

    protected ComboBox<String> comboBoxSearch = new ComboBox<>();

    protected TextField searchField = new TextField();

    protected TableView <Customer>table =new TableView();
    protected TableColumn idColumn=new TableColumn("Id");
    protected TableColumn firstNameColumn =new TableColumn("First name");
    protected TableColumn lastNameColumn=new TableColumn("Last name");
    protected TableColumn middleNameColumn=new TableColumn("Middle name");
    protected TableColumn emailColumn=new TableColumn("Email");
    protected TableColumn dobColColumn=new TableColumn("Date of Birth");
    protected TableColumn addressColumn=new TableColumn("Address");
    protected TableColumn phoneNumColumn=new TableColumn("Phone Number");
    protected TableColumn genderColumn=new TableColumn("Gender");
    protected TableColumn countryColumn=new TableColumn("Country");

    private Label searchLabel = new Label("Search");

    private HBox searchBox = new HBox();
    private VBox vbox = new VBox();

    public GUIPane(){




        toolBar.getItems().addAll(addCustomer,deleteCustomer,updateCustomer,help,exit);
        toolBar.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;");
        addCustomer.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;");
        deleteCustomer.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;"); //+"-fx-background-image:url('delete.png');" +"-fx-background-size: 10 10;" + "-fx-background-repeat: no-repeat;\n"+"-fx-background-image-position:right;");
        updateCustomer.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;");
        help.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;");
        exit.setStyle("-fx-font-size:12;"+"-fx-font-family:calibri;");



//
//        menuBar.getMenus().addAll(addCustomer,deleteCustomer,updateCustomer,help,exit);
//
//        menuBar.setStyle("-fx-background-color:rgb(205,205,205);");
//

//
//        menuBar.getMenus().add(mainMenu);
//        mainMenu.getItems().addAll(addCustomer,deleteCustomer,updateCustomer,help,exit);

        searchLabel.setStyle("-fx-text-fill:black;"+"-fx-font-family:Arial;"+"-fx-font-size:13;");

        table.getColumns().addAll(idColumn,firstNameColumn,lastNameColumn,middleNameColumn,
                emailColumn,dobColColumn,addressColumn,phoneNumColumn,genderColumn,countryColumn);

        firstNameColumn.setMinWidth(100);
        lastNameColumn.setMinWidth(100);
        middleNameColumn.setMinWidth(100);
        emailColumn.setMinWidth(100);
        dobColColumn.setMinWidth(100);
        addressColumn.setMinWidth(100);
        phoneNumColumn.setMinWidth(100);
        genderColumn.setMinWidth(100);
        countryColumn.setMinWidth(100);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerMiddleName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        dobColColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        phoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        table.setItems(data);

        /**Set the search field properties */
        searchField.setStyle("-fx-background:yellow;");
        searchField.setPrefWidth(200);


        /**HBox properties */
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10,100,0,0));

        searchBox.getChildren().addAll(comboBoxSearch,searchField,showAllCustomers);


        /**Set combo box properties */
        comboBoxSearch.getItems().addAll("Id", "First Name","Last Name",
                "Middle Name","Email","Date Of Birth","Address"
                ,"Phone Number", "Gender","Country");

        comboBoxSearch.setPrefWidth(200);
        comboBoxSearch.setValue("Id");


        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(searchLabel,searchBox,table);



        /**Border pane*/
        setTop(toolBar);
        setCenter(vbox);


    }

}
