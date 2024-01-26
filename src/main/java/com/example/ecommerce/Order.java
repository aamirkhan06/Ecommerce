package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order
{
    public static boolean placeOrder(Customer customer,Product product)
    {
        String groupOrderid="select max(group_order_id)+1 id from orders";
        DbConnection dbConnection=new DbConnection();
        try
        {
            ResultSet rs= dbConnection.getQueryTable(groupOrderid);
            if(rs.next())
            {
                String placeOrder="insert into orders(group_order_id,customer_id,product_id)values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDataBase(placeOrder)!=0;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static int placeMultipleOrder(Customer customer, ObservableList<Product>productList)
    {
        String groupOrderid="select max(group_order_id)+1 id from orders";
        DbConnection dbConnection=new DbConnection();
        try
        {
            ResultSet rs= dbConnection.getQueryTable(groupOrderid);
            int count=0;
            if(rs.next())
            {
                for(Product product: productList)
                {
                    String placeOrder="insert into orders(group_order_id,customer_id,product_id)values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count+=dbConnection.updateDataBase(placeOrder);
                }
                return count;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
