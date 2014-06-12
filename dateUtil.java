
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class DateUtil {

    // List of all date formats that we want to parse.
    // Add your own format here.
    private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>() {
        {
            add(new SimpleDateFormat("M/d/yyyy"));
            add(new SimpleDateFormat("dd MMM yyyy"));
            add(new SimpleDateFormat("dd/MMM/yyyy"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
            add(new SimpleDateFormat("dd MM yyyy"));
            add(new SimpleDateFormat("dd/MM/yyyy"));
            add(new SimpleDateFormat("dd.MM.yyyy"));
            add(new SimpleDateFormat("dd-MM-yyyy"));
            add(new SimpleDateFormat("dd MMMM yyyy"));
            add(new SimpleDateFormat("dd/MMMM/yyyy"));
            add(new SimpleDateFormat("dd.MMMM.yyyy"));
            add(new SimpleDateFormat("dd-MMMM-yyyy"));
            add(new SimpleDateFormat("dd MMMM,yyyy"));
            add(new SimpleDateFormat("dd MMMM, yyyy"));
            add(new SimpleDateFormat("MMM dd yyyy"));
            add(new SimpleDateFormat("MMM/dd/yyyy"));
            add(new SimpleDateFormat("MMM.dd.yyyy"));
            add(new SimpleDateFormat("MMM-dd-yyyy"));
            add(new SimpleDateFormat("MM dd yyyy"));
            add(new SimpleDateFormat("MM/dd/yyyy"));
            add(new SimpleDateFormat("MM.dd.yyyy"));
            add(new SimpleDateFormat("MM-dd-yyyy"));
            add(new SimpleDateFormat("MMMM dd yyyy"));
            add(new SimpleDateFormat("MMMM/dd/yyyy"));
            add(new SimpleDateFormat("MMMM.dd.yyyy"));
            add(new SimpleDateFormat("MMMM-dd-yyyy"));
            add(new SimpleDateFormat("MMMM dd,yyyy"));
            add(new SimpleDateFormat("yyyy MMMM dd"));
            add(new SimpleDateFormat("yyyy.MMMM.dd"));
            add(new SimpleDateFormat("yyyy/MMMM/dd"));
            add(new SimpleDateFormat("yyyy-MMMM-dd"));
            add(new SimpleDateFormat("yyyy MM dd"));
            add(new SimpleDateFormat("yyyy.MM.dd"));
            add(new SimpleDateFormat("yyyy/MM/dd"));
            add(new SimpleDateFormat("yyyy-MM-dd"));
            add(new SimpleDateFormat("yyyy-dd-MM"));
            add(new SimpleDateFormat("yyyy dd MM"));
            add(new SimpleDateFormat("yyyy.dd.MM"));
            add(new SimpleDateFormat("yyyy/dd/MM"));
            add(new SimpleDateFormat("yyyy MMM dd"));
            add(new SimpleDateFormat("yyyy.MMM.dd"));
            add(new SimpleDateFormat("yyyy/MMM/dd"));
            add(new SimpleDateFormat("yyyy-MMM-dd"));
        }
    };

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String s = scan.nextLine();
            System.out.println(s + " = " + DateUtil.convertToDate(s));
        }

    }

    public static String convertToDate(String input) {
        Date date = null;
        if (null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                break;
            }
        }
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            return f.format(date);
        } catch (Exception w) {
            return null;
        }
    }
}
