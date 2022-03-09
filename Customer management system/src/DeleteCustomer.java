import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class DeleteCustomer {

    private TextField custID = new TextField();
    private Label idLabel = new Label("ID");
    private Label titleLabel = new Label("Delete Customer");

    private Button buttonDelete = new Button("Delete");
    private Button buttonCancel = new Button("Cancel");

    private VBox vBox = new VBox();
    private HBox hBox = new HBox();
    private GridPane gridPane = new GridPane();

    //Constructor
    public DeleteCustomer(Stage stage, Statement statement, ObservableList<Customer> data, ArrayList<Customer>customers){
        custID.setStyle("-fx-background:blue");
        custID.setPrefWidth(200);

        idLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:calibri;"+"-fx-font-size:14;");
        titleLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:calibri;"+"-fx-font-size:16;");

        buttonDelete.setPrefWidth(100);
        buttonCancel.setPrefWidth(100);

        hBox.setSpacing(10);
        hBox.getChildren().addAll(buttonDelete,buttonCancel);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(20);

        //titleLabel.setPadding(new Insets(0,0,0,30));
        idLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(titleLabel,0.0);
        AnchorPane.setRightAnchor(titleLabel,0.0);
       titleLabel.setAlignment(Pos.CENTER);

        gridPane.setPadding(new Insets(10,20,0,0));
        gridPane.add(idLabel,0,0);
        gridPane.add(custID,1,0);
        gridPane.add(hBox,1,1);

        GridPane.setHalignment(hBox, HPos.RIGHT);

        vBox.setSpacing(25);
        vBox.setPadding(new Insets(10,20,0,0));
        vBox.getChildren().addAll(titleLabel,gridPane);

        Scene scene =new Scene(vBox,300,150);
        Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(stage);

        mainStage.setScene(scene);
        mainStage.setTitle("Customer Management System - Delete Customer)");
        mainStage.show();


        buttonCancel.setOnAction(e->mainStage.close());
        buttonDelete.setOnAction(e->{

            try {
                deleteCustomer(mainStage,statement,data,customers);
            } catch (Exception exception) {
                popErrorMessage(mainStage, exception.getMessage());
            }

        });

        vBox.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.ENTER)
            {
                try{

                    deleteCustomer(stage,statement, data,customers);
                }catch(Exception exception)
                {
                    popErrorMessage(mainStage,exception.getMessage());
                }
            }
        });

    }

    public void deleteCustomer(Stage stage, Statement statement, ObservableList<Customer>data ,ArrayList<Customer>customers) throws Exception {
        boolean exists = false;

        String deleteQuery = "delete from customers where id='"+Integer.parseInt(custID.getText())+"'";

        int result = JOptionPane.showOptionDialog(null,"Are you sure you want to delete this record?","Delete Record",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result==JOptionPane.YES_NO_OPTION){
            for (int i = 0; i < data.size();i++){
                if (data.get(i).getCustomerID()==Integer.parseInt(custID.getText()))
                {
                    Connection connection = null;
                    PreparedStatement statement1 = null;


                    try {
                        connection = CustomerManager.getConnection();                    ;
                        statement1 = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?");
                        statement1.setInt(1,Integer.parseInt(custID.getText()));

                        statement1.executeUpdate();

                    } finally {
                        CustomerManager.close(null,statement1,connection);
                    }
                    data.remove(data.get(i));
                    customers.remove(customers.get(i));
                    exists = true;
                    break;

                }
            }
        }
        if (exists){
            custID.clear();
            dataFetcher(stage,"Customer successfully deleted");

        }else {
            popErrorMessage(stage,"Customer doesn't exist.");
        }
    }

    /**Error Message */
    public void popErrorMessage(Stage primaryStage,String text2)
    {
        /**Create a Stage  */
        final Stage dialog=new Stage ();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        /**Set text properties */
        Text text=new Text();
        text.setText(text2);
        text.setFill(Color.RED);
        text.setFont(Font.font("Times new roman",20));

        /**Set image properties */
        ImageView image =new ImageView(new Image("file:warning.png"));
        image.setFitHeight(20);
        image.setFitWidth(20);

        /**Set the button properties */
        Button dialogOk=new Button("OK");
        dialogOk.setPrefWidth(100);

        /**Set dialog HBox properties */
        HBox dialogHbox=new HBox ();
        dialogHbox.setSpacing(10);
        dialogHbox.getChildren().addAll(image,text);
        HBox.setMargin(image,new Insets(0,0,0,10));
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


    public void dataFetcher (Stage stage, String string)
    {
        /**Create a Stage  zip Files*/
        final Stage dialog=new Stage ();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        /**Set text properties */
        Text localText=new Text();
        localText.setText(string);
        localText.setFill(Color.BLUE);
        localText.setFont(Font.font("Times new roman",20));

        /**Set image properties */
        ImageView image =new ImageView(new Image("file:info.jpg"));

        /**Set dialog HBox properties */
        HBox dialogHbox=new HBox();
        dialogHbox.setSpacing(10);
        dialogHbox.getChildren().addAll(image,localText);
        HBox.setMargin(image,new Insets(0,0,0,10));
        /**Set VBox properties */
        VBox dialogVBox=new VBox();
        Button dialogOk=new Button("Ok");
        dialogVBox.setSpacing(10);
        dialogVBox.getChildren().addAll(dialogHbox,dialogOk);
        VBox.setMargin(dialogOk,new Insets(0,0,0,170));

        /**Set the button properties */
        dialogOk.setPrefWidth(100);

        /** exit the pop up window*/
        dialogOk.setOnAction(new EventHandler<ActionEvent>(){

            public void handle (ActionEvent event)
            {
                dialog.close();
            }

        });

        /**Create a scene  */
        Scene scene=new Scene (dialogVBox,450,100);

        dialog.setScene(scene);
        dialog.setTitle("Info Message");
        dialog.show();
    }

}
