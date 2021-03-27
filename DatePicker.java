package BackEnd;

import javax.swing.*;
import java.util.Calendar;

public class DatePicker{
    private String date;
    private static Calendar calendar=Calendar.getInstance();
    private static String defaultDate;
    private static String previousMonth;

    public DatePicker() {

        String[] day=new String[31];
        String[] month={"January","February","March","April","May","June","July","August","September","October",
                        "November","December"};
        int currYr=calendar.get(Calendar.YEAR);
        String[] year=new String[5];
        int index=0;
        for (int i=4;i>=0;i--){
            year[index]=String.valueOf(currYr);
            index++;
            currYr--;
        }
        for (int i=0;i<31;i++){
            day[i]=getDay(i);
        }
        JComboBox CByr=new JComboBox(year);
        JComboBox CBmonth=new JComboBox(month);
        JComboBox CBday=new JComboBox(day);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Year: "));
        myPanel.add(CByr);
        myPanel.add(new JLabel("Month: "));
        myPanel.add(CBmonth);
        myPanel.add(new JLabel("Day: "));
        myPanel.add(CBday);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Date", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String sYear=year[CByr.getSelectedIndex()];

            int iMonth=CBmonth.getSelectedIndex()+1;
            String sMonth=getMonth(iMonth);

            String sDay=day[CBday.getSelectedIndex()];

            date=sYear+"-"+sMonth+"-"+sDay;

            if (Validation.isDateValid(date)){
                System.out.println(date);
            }
            else{
                date="invalid";
            }
        }
        else{
            date=defaultDate;
        }
    }

    public String getDate() {
        return date;
    }

    public static String getDefaultDate(){
        int day=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH)+1;
        int year=calendar.get(Calendar.YEAR);
        String sMonth=getMonth(month);
        String sDay=getDay(day);
        defaultDate= String.format("%d-%s-%s",year,sMonth,sDay);

        if (day>28){
            day=28;
        }
        if (month==1){
            month=12;
            year--;
        }
        else {
            month--;
        }
        previousMonth=String.format("%d-%s-%s",year,getMonth(month),getDay(day));
        return defaultDate;
    }

    public static String getPreviousMonth(){
        return previousMonth;
    }
    private static String getMonth(int iMonth){
        String sMonth="";
        if ((iMonth>=0)&&(iMonth<10)){
            sMonth="0"+iMonth;
        }
        else {
            sMonth=String.valueOf(iMonth);
        }
        return sMonth;
    }

    private static String getDay(int iDay){
        String sDay="";
        if ((iDay>=0)&&(iDay<9)){
            sDay="0"+(iDay+1);
        }
        else {
            sDay=String.valueOf(iDay);
        }
        return sDay;
    }
}