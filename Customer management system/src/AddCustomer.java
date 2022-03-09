import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddCustomer {

    private final TextField customerFirstName = new TextField();
    private final TextField customerLastName = new TextField();
    private final TextField customerMiddleName = new TextField();
    private final TextField customerEmail = new TextField();
    private final DatePicker customerDOB = new DatePicker();
    private final TextField customerAddress = new TextField();
    private final TextField customerPhoneNumber = new TextField();
    private final TextField customerGender = new TextField();
    private final TextField customerCountry = new TextField();

    private final ArrayList<Label> labels = new ArrayList<>(Arrays.asList(
            new Label("First Name"),
            new Label("Last Name"),
            new Label("Middle Name"),
            new Label("Email"),
            new Label("Date of Birth"),
            new Label("Address"),
            new Label("Phone Number"),
            new Label("Gender"),
            new Label("Country")

    ));

    private final Button add = new Button("Add Customer");
    private final Button saveAndAddAnother = new Button("Save and add another");
    private final Button clear = new Button("Clear Fields");
    private final Button cancel = new Button("Cancel");


    private final GridPane gridPane = new GridPane();
    private final VBox vbox = new VBox();
    private final HBox hbox = new HBox();

    private final Label title = new Label("Add Customer: ");


    public AddCustomer(Stage stage, Statement statement, ObservableList<Customer> data,
                       int[] startingPoint, ArrayList<Customer> customers) {

        title.setStyle("-fx-font-family:calibri;" + "-fx-font-size:17;");


        customerFirstName.setStyle("-fx-background:blue");
        customerFirstName.setPrefWidth(100);
        customerLastName.setStyle("-fx-background:blue");
        // customerLastName.setPrefWidth(100);
        customerMiddleName.setStyle("-fx-background:blue");
        //  customerMiddleName.setPrefWidth(100);
        customerEmail.setStyle("-fx-background:blue");
        //  customerEmail.setPrefWidth(100);
        customerDOB.setStyle("-fx-background:blue");
        //
        customerAddress.setStyle("-fx-background:blue");
        //
        customerPhoneNumber.setStyle("-fx-background:blue");
        //
        customerGender.setStyle("-fx-background:blue");
        //
        customerCountry.setStyle("-fx-background:blue");
        //

        for(int i = 0; i < labels.size(); i++) {
            labels.get(i).setStyle("-fx-text-fill:black;" + "-fx-font-family:calibri;" + "-fx-font-size:15;");
        }

        add.setPrefWidth(100);
        saveAndAddAnother.setPrefWidth(100);
        clear.setPrefWidth(100);
        cancel.setPrefWidth(100);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);


        gridPane.add(labels.get(0), 0, 0);
        gridPane.add(customerFirstName, 0, 1);
        gridPane.add(labels.get(1), 1, 0);
        gridPane.add(customerLastName, 1, 1);
        gridPane.add(labels.get(2), 2, 0);
        gridPane.add(customerMiddleName, 2, 1);


        gridPane.add(labels.get(3), 0, 2);
        gridPane.add(customerEmail, 1, 2);
        gridPane.add(labels.get(4), 0, 3);
        gridPane.add(customerDOB, 1, 3);
        gridPane.add(labels.get(5), 0, 4);
        gridPane.add(customerAddress, 1, 4);
        gridPane.add(labels.get(6), 0, 5);
        gridPane.add(customerPhoneNumber, 1, 5);
        gridPane.add(labels.get(7), 0, 6);
        gridPane.add(customerGender, 1, 6);
        gridPane.add(labels.get(8), 0, 7);
        gridPane.add(customerCountry, 1, 7);

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(add,saveAndAddAnother, clear, cancel);

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 15, 0, 0));
        vbox.getChildren().addAll(title, gridPane, hbox);

        Scene scene = new Scene(vbox, 500, 500);
        Stage stage1 = new Stage();

        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.initOwner(stage);

        stage1.setScene(scene);
        stage1.setTitle("Add Customer");
        stage1.show();

        clear.setOnAction(e -> {
            clearFields();
        });

        /**Register and handle the event fires by the exit button */
        cancel.setOnAction(e->{stage1.close();});

        /**Register and handle */

        add.setOnAction(e->{

            try {
                addNewCustomer(stage1,statement,data, startingPoint,customers);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });

        saveAndAddAnother.setOnAction((e->{
            try {
                addNewCustomer(stage1,statement,data, startingPoint,customers);
                clearFields();
            } catch (Exception exception) {


                popErrorMessage(stage1,exception.getMessage());
            }


        }));

        /**Enter Key */
        vbox.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER)
            {
                try{
                    addNewCustomer(stage1,statement,data, startingPoint,customers);
                }catch(Exception exception)
                {
                    popErrorMessage(stage1,exception.getMessage());
                }
            }
        });


    }

    public void clearFields() {
        customerFirstName.clear();
        customerLastName.clear();
        customerMiddleName.clear();
        customerEmail.clear();
        customerAddress.clear();
        customerPhoneNumber.clear();
        customerGender.clear();
        customerCountry.clear();
    }

    public void addNewCustomer(Stage stage, Statement statement, ObservableList<Customer> data, int[] startingPoint, ArrayList<Customer> customers) throws Exception {

        if (customerFirstName.getText().equals("")) {
            customerFirstName.requestFocus();
        } else if (customerLastName.getText().equals("")) {
            customerLastName.requestFocus();
        } else if (customerMiddleName.getText().equals("")) {
            customerMiddleName.requestFocus();
        } else if (customerEmail.getText().equals("")) {
            customerEmail.requestFocus();
        }

        else if(String.valueOf(customerDOB.getValue()).equals("")){
            customerDOB.requestFocus();
        }
//        (DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss z");
//        String strDate = dateFormat.format(customers.get(i).getDOB());)
        else if (customerAddress.getText().equals("")) {
            customerAddress.requestFocus();
        } else if (customerPhoneNumber.getText().equals("")) {
            customerPhoneNumber.requestFocus();
        } else if (customerGender.getText().equals("")) {
            customerGender.requestFocus();
        } else if (customerCountry.getText().equals("")) {
            customerCountry.requestFocus();
        } else {
            String query = "insert into customers " +
                    "(First_Name, Last_Name, Middle_Name, Email, Date_of_Birth, Address, Phone_Number, Gender, Country) values "
                    + "('" + customerFirstName.getText() + "','" + customerLastName.getText() + "','"
                    + customerMiddleName.getText() + "','" + customerEmail.getText() + "','"
                    + customerDOB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "','"
                    + customerAddress.getText() + "','" + customerPhoneNumber.getText() + "','"
                    + customerGender.getText() + "','"
                    + customerCountry.getText() + "')";


            Connection connection = null;
            PreparedStatement statement1 = null;

            try {
                connection = CustomerManager.getConnection();
                statement1 = connection.prepareStatement("INSERT INTO CUSTOMERS (First_Name,Last_Name, Middle_Name, Email, Date_of_Birth, Address, Phone_Number, Gender, Country) VALUES (?,?,?,?,?,?,?,?,?)");

//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
//                String strDate = dateFormat.format(customers.get(i).getDOB());

                int i = 1;
                statement1.setString(i++, customerFirstName.getText());
                statement1.setString(i++, customerLastName.getText());
                statement1.setString(i++, customerMiddleName.getText());
                statement1.setString(i++, customerEmail.getText());

                statement1.setDate(i++,Date.valueOf(customerDOB.getValue()));
                statement1.setString(i++, customerAddress.getText());
                statement1.setString(i++, customerPhoneNumber.getText());
                statement1.setString(i++, customerGender.getText());
                statement1.setString(i++, customerCountry.getText());

                statement1.executeUpdate();


            } finally {
                CustomerManager.close(null, statement1, connection);

            }
            clearFields();

            informationFetcher(stage,"Employee added successfully");

            query = "select * from customers where id>'" + startingPoint[0] + "'";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                data.add(new Customer(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDate(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)

                ));

                customers.add(new Customer(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDate(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                ));

                startingPoint[0] = Integer.parseInt(resultSet.getString(1));

            }
        }
    }

    public void informationFetcher(Stage stage,String string){

        Stage infoFetcher = new Stage();
        infoFetcher.initModality(Modality.APPLICATION_MODAL);
        infoFetcher.initOwner(stage);

        Text text = new Text();
        text.setText(string);
        text.setFill(Color.BLUE);
        text.setFont(Font.font("Times new roman",20));

        ImageView imageView = new ImageView(new Image("file:istockphoto-1223088904-612x612.jpg"));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(imageView,text);
        HBox.setMargin(imageView,new Insets(0,0,0,10));

        VBox vBox = new VBox();
        Button OKButton = new Button("OK");
        vBox.setSpacing(10);
        vBox.getChildren().addAll(hbox,OKButton);
        VBox.setMargin(OKButton,new Insets(0,0,0,200));

        OKButton.setPrefWidth(100);
        OKButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){infoFetcher.close();}
        });

        Scene scene = new Scene(vBox,450,100);

        infoFetcher.setScene(scene);
        infoFetcher.setTitle("Info Message");
        infoFetcher.show();

    }

    public void popErrorMessage(Stage stage,String text2)
    {
        /**Create a Stage  */
        final Stage dialog=new Stage ();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        /**Set text properties */
        Text text=new Text();
        text.setText(text2);
        text.setFill(Color.RED);
        text.setFont(Font.font("Times new roman",20));

        /**Set image properties */
        ImageView imageView =new ImageView(new Image("file:warning.png"));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);


        /**Set the button properties */
        Button dialogOk=new Button("OK");
        dialogOk.setPrefWidth(100);

        /**Set dialog HBox properties */
        HBox dialogHbox=new HBox ();
        dialogHbox.setSpacing(10);
        dialogHbox.getChildren().addAll(imageView,text);
        HBox.setMargin(imageView,new Insets(0,0,0,10));
        /**Set VBox properties */
        VBox dialogVBox =new VBox();
        dialogVBox.setSpacing(10);
        dialogVBox.getChildren().addAll(dialogHbox,dialogOk);
        VBox.setMargin(dialogOk,new Insets(0,0,0,170));



        /** exit the pop up window*/
        dialogOk.setOnAction(e->{dialog.close();});

        /**Create a scene  */
        Scene scene=new Scene (dialogVBox,450,200);

        dialog.setScene(scene);
        dialog.setTitle("Error Message");
        dialog.show();
    }


}
