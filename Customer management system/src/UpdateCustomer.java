import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class UpdateCustomer {

    int elementPosition=-1;


    private final TextField customerId = new TextField();
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
            new Label("ID"),
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

    private final Button updateCustomer = new Button("Update Customer");
    private final Button clear = new Button("Clear Fields");
    private final Button cancel = new Button("Cancel");


    private final GridPane gridPane = new GridPane();
    private final VBox vbox = new VBox();
    private final HBox hbox = new HBox();

    private final Label title = new Label("Update Customer: ");

    public UpdateCustomer(Stage primaryStage, Statement statement, ObservableList<Customer> data, int[] startingPoint, ArrayList<Customer> customers)
    {

        title.setStyle("-fx-font-family:calibri;"+"-fx-font-size:17;");


        /**Set the text fields properties  */
        customerFirstName.setStyle("-fx-background:yellow;");
        customerFirstName.setPrefWidth(200);
        customerLastName.setStyle("-fx-background:yellow;");
        customerMiddleName.setStyle("-fx-background:yellow;");
        customerEmail.setStyle("-fx-background:yellow;");
        customerDOB.setStyle("-fx-background:yellow;");
        customerAddress.setStyle("-fx-background:yellow;");
        customerPhoneNumber.setStyle("-fx-background:yellow;");
        customerGender.setStyle("-fx-background:yellow;");
        customerCountry.setStyle("-fx-background:yellow;");


        /**Set the label properties */
        for (int i=0; i<labels.size();i++)
        {
            labels.get(i).setStyle("-fx-text-fill:black;"+"-fx-font-family:calibri;"+"-fx-font-size:15;");
        }

        /**Set the button properties*/
        updateCustomer.setPrefWidth(100);
        clear.setPrefWidth(100);
        cancel.setPrefWidth(100);

        /**Set the grid pane properties */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(labels.get(0), 0, 0);
        gridPane.add(customerId, 1, 0);

        gridPane.add(labels.get(1), 0, 1);
        gridPane.add(customerFirstName, 0, 2);
        gridPane.add(labels.get(2), 1, 1);
        gridPane.add(customerLastName, 1, 2);
        gridPane.add(labels.get(3), 2, 1);
        gridPane.add(customerMiddleName, 2, 2);


        gridPane.add(labels.get(4), 0, 3);
        gridPane.add(customerEmail, 1, 3);
        gridPane.add(labels.get(5), 0, 4);
        gridPane.add(customerDOB, 1, 4);
        gridPane.add(labels.get(6), 0, 5);
        gridPane.add(customerAddress, 1, 5);
        gridPane.add(labels.get(7), 0, 6);
        gridPane.add(customerPhoneNumber, 1, 6);
        gridPane.add(labels.get(8), 0, 7);
        gridPane.add(customerGender, 1, 7);
        gridPane.add(labels.get(9), 0, 8);
        gridPane.add(customerCountry, 1, 8);



        /**Set the HBox properties */
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(updateCustomer,clear,cancel);


        /**Set the VBox properties */
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,15,0,0));
        vbox.getChildren().addAll(title,gridPane,hbox);

        /** Create a Scene */
        Scene scene=new Scene(vbox,800,500);
        final Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);

        mainStage.setScene(scene);
        mainStage.setTitle("Update Customer");
        mainStage.show();



        /**Register and handle the event fired by the clear button */
        clear.setOnAction(e->{clearFields();});


        /**Register and handle the event fires by the exit button */
        cancel.setOnAction(e->{mainStage.close();});

        /**Register and handle */

        updateCustomer.setOnAction(e->{
            //try{

            try {
                updateCustomer(mainStage,data,statement,customers);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//
            }catch(Exception exception)
            {

                popErrorMessage(mainStage,exception.getMessage());
            }

        });


        /** Employee information */
        customerId.setOnKeyPressed(e->{

            if(e.getCode()== KeyCode.ENTER)
            {
                try{
                    customerInfo(data,mainStage);
                }catch(Exception exception)
                {
                    popErrorMessage(mainStage,exception.getMessage());
                }
            }

        });



    }

    /**Clear text fields */
    public void clearFields() {
        customerId.clear();
        customerFirstName.clear();
        customerLastName.clear();
        customerMiddleName.clear();
        customerEmail.clear();
        customerAddress.clear();
        customerPhoneNumber.clear();
        customerGender.clear();
        customerCountry.clear();
    }

    /**Create Information Dialog method */
    public void informationFetcher(Stage stage,String string){

        Stage infoFetcher = new Stage();
        infoFetcher.initModality(Modality.APPLICATION_MODAL);
        infoFetcher.initOwner(stage);

        Text text = new Text();
        text.setText(string);
        text.setFill(Color.BLUE);
        text.setFont(Font.font("Times new roman",20));

        ImageView imageView = new ImageView(new Image("file:info.jpg"));
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
        Scene scene=new Scene (dialogVBox,450,100);

        dialog.setScene(scene);
        dialog.setTitle("Error Message");
        dialog.show();
    }

    /**Update method  */
    public void customerInfo(ObservableList<Customer> data, Stage stage) throws Exception
    {

        boolean exists = false;


        if(customerId.getText().equals(""))
        {
            customerId.requestFocus();
        }
        else
        {
            for(int i=0;i<data.size();i++)
            {
                if(data.get(i).getCustomerID()==Integer.parseInt(customerId.getText()))
                {
                    exists=true;
                    elementPosition=i;

                    break;

                }
            }
            if(exists)
            {
                customerFirstName.setText(data.get(elementPosition).getCustomerFirstName());
                customerLastName.setText(data.get(elementPosition).getCustomerLastName());
                customerMiddleName.setText(data.get(elementPosition).getCustomerMiddleName());
                customerEmail.setText(data.get(elementPosition).getCustomerEmail());
                customerDOB.setAccessibleText(String.valueOf(data.get(elementPosition).getDOB()));
                customerAddress.setText(data.get(elementPosition).getCustomerAddress());
                customerPhoneNumber.setText(data.get(elementPosition).getPhoneNumber());
                customerGender.setText(data.get(elementPosition).getGender());
                customerCountry.setText(data.get(elementPosition).getCountry());

            }
            else
            {
                popErrorMessage(stage,"Customer does not exist.\nPlease Enter Another Id Number!!!");
            }
        }
    }


    public void updateCustomer(Stage stage, ObservableList<Customer> data, Statement statement, ArrayList<Customer> customers) throws Exception
    {
        int custID=Integer.parseInt(customerId.getText());
        String custFirstName=customerFirstName.getText();
        String custLastName=customerLastName.getText();

        String custMiddleName=customerMiddleName.getText();
        String custEmail=customerEmail.getText();
        //DatePicker custDOB =customerDOB;
        String custAddress=customerAddress.getText();
        String custPhoneNumber=customerPhoneNumber.getText();
        String custGender=customerGender.getText();
        String custCountry=customerCountry.getText();


//customerDOB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))




        if(customerId.getText().equals(""))
        {
            customerId.requestFocus();
        }
        else
        {
            String query="update customers set First_Name='"+custFirstName
                    +"', Last_Name='"+custLastName
                    +"', Middle_Name='"+custMiddleName
                    +"', Email='"+custEmail
                    +"', Date_of_Birth='"
                    +customerDOB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    +"', Address='" +custAddress
                    +"', Phone_Number='"+custPhoneNumber
                    +"', Gender='"+custGender
                    +"', Country='"+custCountry
                    +"' where ID='" +custID+"'";

            statement.executeUpdate(query);
            Connection connection = null;
            PreparedStatement statement1 = null;

            try {
                connection =  CustomerManager.getConnection();
                statement1 =  connection.prepareStatement("UPDATE CUSTOMERS SET First_Name = ?, Last_Name = ?, Middle_Name = ?, Email = ?, Date_of_Birth = ?, Address = ?, Phone_Number = ?, Gender = ?, Country = ? WHERE ID = ?");

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

                statement1.setInt(i++, custID);

                statement1.executeUpdate();
            } finally {
                CustomerManager.close(null, statement1, connection);
            }




//            data.set(elementPosition, new Customer(custID,custFirstName,custLastName,custMiddleName,custEmail,Date.valueOf(customerDOB.getValue()),
//                    custAddress,custPhoneNumber,custGender,custCountry ));
//
//            customers.set(elementPosition, new Customer(custID,custFirstName,custLastName,custMiddleName,custEmail,Date.valueOf(customerDOB.getValue()),
//                    custAddress,custPhoneNumber,custGender,custCountry ));

            clearFields();

            informationFetcher(stage,"Customer was successfully updated.");
        }
    }



}
