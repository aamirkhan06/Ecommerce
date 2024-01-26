package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface
{
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Label welcomeLabel;
    VBox body;
    Customer  loggedInCustomer;
    ProductList productList=new ProductList();
    VBox productPage;
    Button placeOrderButton=new Button("Place Order");
    ObservableList<Product> itemsInCart= FXCollections.observableArrayList();

    public UserInterface()
    {
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    public BorderPane createContent()
    {
        BorderPane root=new BorderPane();
        root.setPrefSize(800,600);

        root.setTop(headerBar);
        //root.setCenter(loginPage);
        body=new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        productPage=productList.getAllProducts();

        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }

    private void createLoginPage()
    {
        Text userNameText=new Text("User Name");
        Text passwordText=new Text("Password");

        TextField userName= new TextField("angad@gmail.com"); //displays text
        userName.setPromptText("Type your user name here");

        PasswordField password=new PasswordField(); //password is hidden i.e. it shows circle instead of text while writing
        password.setPromptText("Type your password here");
        password.setText("abc123");

        Label messageLabel=new Label("Hi!");
        Button loginButton=new Button("Login");

        loginPage=new GridPane();
        //loginPage.setStyle("-fx-background-color: grey");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);

        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);

        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);

        loginPage.add(messageLabel,0,2);
        loginPage.add(loginButton,1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name= userName.getText();
                String pass=password.getText();
                Login login=new Login();
                loggedInCustomer=login.customerLogin(name,pass);
                if(loggedInCustomer!=null)
                {
                    messageLabel.setText("Welcome : "+loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome! "+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else
                {
                    messageLabel.setText("Login Failed! please provide correct username and password");
                }
            }
        });


    }

    private void createHeaderBar()
    {
        Button homeButton=new Button();
        Image image=new Image("file:C:\\Users\\DELL\\major Projects\\ECommerce\\src\\ecomlogo.png");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(45);
        homeButton.setGraphic(imageView);

        TextField searchBar=new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(200);

        Button searchButton=new Button("search");
        Button cartButton=new Button("cart");
        Button orderButton=new Button("Orders");

        signInButton =new Button("Sign In");
        welcomeLabel=new Label();
        headerBar=new HBox();
        //headerBar.setStyle("-fx-background-color: black");
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,cartButton,signInButton,orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();//remove everything from body
                body.getChildren().add(loginPage); //opens login page
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage=productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false); //all cases need to be handled
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of products
                if(itemsInCart==null)
                {
                    showDialog("Please add some products in the cart to place order!");
                    return;
                }
                if(loggedInCustomer==null)
                {
                    //please select a product first to place order
                    showDialog("Please login first to place order!");
                    return;
                }
                int count=Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if(count!=0)
                {
                    showDialog("Order for "+count+" products placed successfully!");
                }
                else
                {
                    showDialog("Order failed!");
                }
            }
        });
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null && !headerBar.getChildren().contains(signInButton))
                {
                    headerBar.getChildren().add(signInButton);
                }
            }
        });
    }
    private void createFooterBar()
    {
        Button buyNowButton=new Button("Buy Now");
        Button addToCartButton=new Button("Add to Cart");

        footerBar=new HBox();
        //headerBar.setStyle("-fx-background-color: black");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                if(loggedInCustomer==null)
                {
                    showDialog("Please log in first to place order!");
                    return;
                }
                if(product==null)
                {
                    //please select a product first to place order
                    showDialog("Please select a product first to place order!");
                    return;
                }
                boolean status=Order.placeOrder(loggedInCustomer,product);
                if(status==true)
                {
                    showDialog("Order placed successfully!");
                }
                else
                {
                    showDialog("Order failed!");
                }
            }
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                if(product==null)
                {
                    //please select a product first to place order
                    showDialog("Please select a product first to add it to cart!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected  product has been added to the cart successfully!");
            }
        });
    }
    private void showDialog(String message)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}











