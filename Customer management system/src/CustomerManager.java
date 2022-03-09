import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerManager extends Application {

private GUIPane guiPane = new GUIPane();
private Connection connection;
private Statement statement;
private int[] startingPoint = new int[]{0};
private ArrayList<Customer> customers = new ArrayList<>();



    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage stage) throws Exception {

        dbConnect();

        showAllCustomers();

        customers.addAll(guiPane.data);

        guiPane.showAllCustomers.setOnAction(event->{
            guiPane.data.clear();
            guiPane.data.addAll(customers);
        });


        /**Add Employee  */
        guiPane.addCustomer.setOnAction(
                event -> new AddCustomer(stage,statement, guiPane.data, startingPoint,customers)
        );


        guiPane.deleteCustomer.setOnAction(
                event -> new DeleteCustomer(stage,statement,guiPane.data,customers)
        );


        /**Update Employee */
         guiPane.updateCustomer.setOnAction(e->{new UpdateCustomer(stage, statement, guiPane.data, startingPoint,customers);});




        guiPane.setOnKeyPressed(e->{
            if (e.getCode()== KeyCode.ENTER){
                search(stage);
            }
        });


        guiPane.exit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event)
            {
                System.exit(0);
            }

        });

        /**Create a scene  */
        Scene scene =new Scene(guiPane,780,500);
        stage.setScene(scene);
        stage.setTitle("Customer Management");
        stage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }

    private void dbConnect() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CustomerManagementDB","root","");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static Connection getConnection() throws Exception{
         return DriverManager.
                 getConnection("jdbc:mysql://localhost:3306/CustomerManagementDB",
                         "root",
                         "");
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }}

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**Search method */
    public void search(Stage stage)
    {
        int index=-1;
        boolean exists = false;
        ArrayList<Integer> indexes=new ArrayList<>();

        if (guiPane.searchField.getText().equals(""))
        {
            guiPane.searchField.requestFocus();
        }
        else
        {

            if (guiPane.comboBoxSearch.getValue().equals("Id"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    if (customers.get(i).getCustomerID()==Integer.parseInt(guiPane.searchField.getText()))
                    {
                        exists = true;
                        index=i;
                    }
                }
                if (exists)
                {
                    guiPane.data.clear();
                    guiPane.data.add(customers.
                            get(index));
                    startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(stage,"Customer Does Not Exist !!!");
                }
            }
            else if (guiPane.comboBoxSearch.getValue().equals("First Name"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    if (customers.get(i).getCustomerFirstName().equals(guiPane.searchField.getText()))
                    {
                        exists = true;
                        indexes.add(i);
                    }
                }
                if (exists)
                {
                    guiPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        guiPane.data.add(customers.get(indexes.get(i)));
                    }

                    startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(stage,"Customer Does Not Exist!!!");
                }
            }
            else if (guiPane.comboBoxSearch.getValue().equals("Middle Name"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    if (customers.get(i).getCustomerMiddleName().equals(guiPane.searchField.getText()))
                    {
                        exists=true;
                        indexes.add(i);
                    }
                }
                if (exists)
                {
                    guiPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        guiPane.data.add(customers.get(indexes.get(i)));
                    }

                    startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(stage,"Customer Does Not Exist!!!");
                }
            }

            else if (guiPane.comboBoxSearch.getValue().equals("Last Name"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    if (customers.get(i).getCustomerLastName().equals(guiPane.searchField.getText()))
                    {
                        exists=true;
                        indexes.add(i);
                    }
                }
                if (exists)
                {
                    guiPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        guiPane.data.add(customers.get(indexes.get(i)));
                    }

                    startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(stage,"Customer Does Not Exist!!!");
                }
            }
            else if (guiPane.comboBoxSearch.getValue().equals("Email"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    if (customers.get(i).getCustomerEmail().equals(guiPane.searchField.getText()))
                    {
                        exists = true;
                        indexes.add(i);
                    }
                }
                if (exists)
                {
                    guiPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        guiPane.data.add(customers.get(indexes.get(i)));
                    }

                    startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(stage,"Customer Does Not Exist!!!");
                }
            }
            else if (guiPane.comboBoxSearch.getValue().equals("Date of Birth"))
            {

                for(int i=0; i<customers.size();i++)
                {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = dateFormat.format(customers.get(i).getDOB());

                    if (strDate.equals(guiPane.searchField.getText()))
                    {
                        exists = true;
                        indexes.add(i);
                    }
                }
                if (exists = true)
                {
                    guiPane.data.clear();
                    for(int i=0; i<indexes.size();i++)
                    {
                        guiPane.data.add(customers.get(indexes.get(i)));
                    }

                    startingPoint[0]=0;
                }
                else
                {

                    popErrorMessage(stage,"Customer Does Not Exist!!!");
                }
            }
        }
    }


    public void showAllCustomers(){
        String query = "select * from customers where id>'"+startingPoint[0]+"'";
        try {
            ResultSet resultSet= statement.executeQuery(query);

            while(resultSet.next()){
                guiPane.data.add(
                        new Customer(
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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



}

