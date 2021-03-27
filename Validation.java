package BackEnd;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

/**
 * Created by erosennin on 3/3/17.
 */
public abstract class Validation {


    public static boolean isDateValid(String date){
        String errorMsg="Invalid date";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        Date d = null;
        try {
            d = df.parse(date);
            return true;
        } catch (ParseException e1) {
            PopUp.DisplayMessage(new JFrame(),errorMsg,"Date Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean isDiscountValid(double discount){
            if (discount>=0){
                return true;
            }
            else {
                PopUp.DisplayMessage(new JFrame(),"Discount must be greater than 0","Discount Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
    }

    public static boolean isWeightValid(double weight){
        if (weight>=0){
            return true;
        }
        else {
            PopUp.DisplayMessage(new JFrame(),"Weight must be greater than 0","Weight Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean isFormValid(int Ci,String inv,int Pi,String quan,String weight,String deli){
        if (Ci<0){
            PopUp.DisplayMessage(new JFrame(),"Choose a customer",
                    "Customer Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (inv.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Invoice Number",
                    "Invoice Number Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (Pi<0){
            PopUp.DisplayMessage(new JFrame(),"Choose a product",
                    "Product Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (weight.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Weight","Weight Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (quan.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter quantity",
                    "Quantity Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (deli.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Name of Delivery Person",
                    "DeliveredBy Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isFormViewValid(String inv,String quan,String weight,String deli){
        if (inv.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Invoice Number",
                    "Invoice Number Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (weight.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Weight","Weight Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (quan.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter quantity",
                    "Quantity Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (deli.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Name of Delivery Person",
                    "DeliveredBy Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isFormValid(String name,String bp,String sp,String stock){
        if (name.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Product description",
                    "Description Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((bp.isEmpty())&&(Integer.parseInt(bp)<0)){
            PopUp.DisplayMessage(new JFrame(),"Please Enter a Buying Price greater than 0",
                    "Invalid Buying Price",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((sp.isEmpty())&&(Integer.parseInt(sp)<0)){
            PopUp.DisplayMessage(new JFrame(),"Enter a Selling Price greater than 0",
                    "Invalid Selling Price",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((stock.isEmpty())&&(Integer.parseInt(stock)<0)){
            PopUp.DisplayMessage(new JFrame(),"Enter a non-negative Stock value","Invalid Stock",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isFormValidC(String name,String tel,String email){
        if (name.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter Customer Nmae",
                    "Name Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((tel.isEmpty())&&((Integer.parseInt(tel)<0)&&(Integer.parseInt(tel)>7))){
            PopUp.DisplayMessage(new JFrame(),"Please Enter a valid telephone number",
                    "Invalid telephone number",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (email.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Enter a valid email",
                    "Invalid Email address",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    public static String isSqlValid(String sql){
        String sTexte="ORDER";
        Pattern pattern=Pattern.compile(sTexte);
        Matcher matcher=pattern.matcher(sql);
        String str=sql;
        if (matcher.find()){
            int end=matcher.start();
            str=sql.substring(0,end);
        }
        return str;
    }

    public static boolean isFormValid(String uname,String pass){
        //validate manage user
        if (uname.isEmpty() && pass.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Please input username and password","Username and Password fields are empty",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (uname.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Please input username ","Username empty",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(pass.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Please input password ","Password empty",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean isFormValidp(String id,String inv,String amount){
        if ((id.isEmpty())&&(Integer.parseInt(id)<0)){
            PopUp.DisplayMessage(new JFrame(),"Enter ID",
                    "ID Missing",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((inv.isEmpty())&&(Integer.parseInt(inv)<0)){
            PopUp.DisplayMessage(new JFrame(),"Please Enter a valid invoice number",
                    "Invalid invoice number",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((amount.isEmpty())&&(Integer.parseInt(amount)<0)){
            PopUp.DisplayMessage(new JFrame(),"Enter a valid amount",
                    "Invalid Amount",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isSupFormValid(String name, String tel, String email, String balDue) {

        if(name.isEmpty() || tel.isEmpty() || email.isEmpty() || balDue.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"Fill in the texboxes","Empty Textboxes",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(name.length()>25){
            PopUp.DisplayMessage(new JFrame(),"Name should be less than 25 characters","Name is too long",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(tel.length()>8 || tel.length()<7){
            PopUp.DisplayMessage(new JFrame(),"Telephone should be of length 7 or 8","Error on telephone",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(Float.parseFloat(balDue) < 0.0){
            PopUp.DisplayMessage(new JFrame(),"Balance Due must be greater or equal 0","Negative Value",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean isPurchaseFormValid(String invoice, String date, String qty, String weight) {

        if(invoice.isEmpty() || date.isEmpty() || qty.isEmpty() || weight.isEmpty()){
            PopUp.DisplayMessage(new JFrame(),"No value(s) can be missing","Missing values",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        else if (Integer.parseInt(qty) <0 || Double.parseDouble(weight)<0.0){
            PopUp.DisplayMessage(new JFrame(),"Quantity or Weight cannot be negative","Negative values",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }
}
