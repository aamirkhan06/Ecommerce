package com.example.ecommerce;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList
{
    private TableView<Product> productTable;

    public VBox createTable(ObservableList<Product> data)
    {
        //columns
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));



        //data -dummy data

        productTable=new TableView<>();
        productTable.getColumns().addAll(id,name,price);
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);
        return vBox;
    }

    public VBox getAllProducts()
    {
        ObservableList<Product> data=Product.getAllProducts();
        return createTable(data);
    }

    public Product getSelectedProduct()
    {
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data)
    {
        return createTable(data);
    }
    // dummy data:-
//    public VBox getDummyTable()
//    {
//        ObservableList<Product> data= FXCollections.observableArrayList();
//        data.add(new Product(2,"Iphone 13 pro", 70000));
//        data.add(new Product(5,"MacBook 11", 90000));
//        return createTable(data);
//    }

}
