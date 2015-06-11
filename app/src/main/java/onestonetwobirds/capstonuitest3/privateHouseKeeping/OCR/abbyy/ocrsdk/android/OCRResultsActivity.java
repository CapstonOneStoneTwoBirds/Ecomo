package onestonetwobirds.capstonuitest3.privateHouseKeeping.OCR.abbyy.ocrsdk.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Insert.InsertActivity;

public class OCRResultsActivity extends Activity {

	String outputPath;
    String year, month, day;
    String store, price, way;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tv = new TextView(this);
		setContentView(tv);

		String imageUrl = "unknown";
		
		Bundle extras = getIntent().getExtras();
		if( extras != null) {
			imageUrl = extras.getString("IMAGE_PATH" );
			outputPath = extras.getString( "RESULT_PATH" );
		}
		
		// Starting recognition process
		new AsyncProcessTask(this).execute(imageUrl, outputPath);

	}

	public void updateResults(Boolean success) {
		if (!success)
			return;
		try {
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = openFileInput(outputPath);
			try {
				Reader reader = new InputStreamReader(fis, "UTF-8");
				BufferedReader bufReader = new BufferedReader(reader);
				String text = null;
				while ((text = bufReader.readLine()) != null) {
					//contents.append(text).append(System.getProperty("line.separator"));
                    contents.append(text);
				}
			} finally {
				fis.close();
			}

			displayMessage(contents.toString());
		} catch (Exception e) {
			displayMessage("Error: " + e.getMessage());
		}
	}
	
	public void displayMessage( String text )
	{

        System.out.println(text);
        System.out.println("where is here?");

        price = getPrice(text);
        way = getPaid(text);
        store = getStore(text);
        String temp = price + " //// " + way; //추출되는 값이 없기 때문에 에러가 뜸

        // 현재 상표추출 알고리즘은 없음
        ArrayList<String> date = getDate(text);
        if(date.size()!=0) {
            year = date.get(0);
            month = date.get(1);
            day = date.get(2);
        } else System.out.println("data out of bound");

        if( date.size() != 0 ) {
            System.out.println("Confirm year : " + date.get(0));
            System.out.println("Confirm month : " + date.get(1));
            System.out.println("Confirm day : " + date.get(2));
            System.out.println("Confirm "+temp);
            System.out.println("Confirm "+date.get(0)+" / "+date.get(1)+" / "+date.get(2));
        }

        tv.post(new MessagePoster(text));
        System.out.println("text length : " + text.length());
	}
    ////////////////////////////////////////////////////

    public String getPrice( String text ){
        String price = "";
        Character c;
        String numbers="";
        boolean check = false;
        for( int i = 0 ; i < text.length() ; i++){
            c = text.charAt(i);
            try{
                Integer.parseInt(c.toString());
                numbers = numbers+c.toString();
                check = true;
            }catch( NumberFormatException e){
                if( c.toString().equals(",") )
                    check = true;
                else{
                    if( check == true )
                        numbers = numbers+" ";
                    check = false;
                }
            }
        }
        System.out.println("numbers : " + numbers);

        ArrayList<Integer> num = new ArrayList<Integer>();
        String temp = "";

        for(int i=0;i<numbers.length() ; i++){
            Character cha = numbers.charAt(i);
            if(cha.toString().equals(" ") & temp != ""){
                try{
                    new Integer(temp);
                }catch( NumberFormatException e ){
                    temp = "";
                }
                if( temp != "" ){
                    num.add(new Integer(temp));
                    temp = "";
                }
            }
            else
                temp = temp + cha.toString();
        }

        System.out.println("num String size : " + num.size());

        for(int i = 0 ; i < num.size() ; i ++){
            if(num.get(i) <= 100)
                num.remove(i--);
        }

        System.out.println("num Array size : " + num.size());

        for(int i = 0 ; i < num.size() ; i++){
            System.out.print(num.get(i)+ " ");
        }

        System.out.println("here2");

        check = true;
        for(int i = 0 ; i < num.size() ; i++){
            for(int j = 0 ; j < num.size() ; j++ ){
                for(int k = 0 ;  k < num.size(); k++ ){
                    if( num.get(i) + num.get(j) == num.get(k) && num.get(i) > num.get(j) &&
                            ( num.get(i)/10+1 == num.get(j) ||  num.get(i)/10 == num.get(j))){
                        price = num.get(k).toString();
                        check = false;
                        break;
                    }
                }
                if( check == false)
                    break;
            }
            if ( check == false)
                break;
        }
        System.out.println("price 1 : " + price);

        if(price.equals("")){
            for(int i = 0 ; i < num.size() ; i++){
                for( int j = 0 ; j < num.size() ; j++ ){
                    for( int k = 0 ; k < num.size() ; k ++){
                        if(  num.get(j).equals((int)(num.get(i)/1.1)) ||  num.get(j).equals((int)(num.get(i)/1.1)+1)
                                || num.get(j).equals( ((int)(num.get(i)/1.1))/10 ) || num.get(j).equals( ((int)(num.get(i)/1.1))/10 +1 )  ){
                                    price = num.get(i).toString();
                        }
                    }
                }
            }
        }

        return price;
    }

    public String getPaid(String text){
        String result = "";
        int ca = text.indexOf("신용");
        if ( ca != -1 )
            result = "신용카드";
        else {
            ca = text.indexOf("체크");
            if( ca != -1 )
                result = "체크카드";
            else{
                ca = text.indexOf("현금");
                if( ca != -1){
                    result = "현금";
                }
            }
        }

        if(result.equals("")) result = "";
            //result = "ERROR";

        return result;
    }

    public String getStore(String text){
        String result = "";
        int ca = text.indexOf("엔젤");
        if ( ca != -1 )
            result = "엔젤리너스";
        else {
            ca = text.indexOf("투썸");
            if( ca != -1 )
                result = "투썸플레이스";
            else{
                ca = text.indexOf("마카");
                if( ca != -1){
                    result = "마카나";
                } else {
                    ca = text.indexOf("할리스");
                    if( ca != -1){
                        result = "할리스커피";
                    } else {
                        ca = text.indexOf("파리");
                        if( ca != -1){
                            result = "파리바게트";
                        } else {
                            ca = text.indexOf("올리");
                            if( ca != -1){
                                result = "CJ 올리브영";
                            } else {
                                ca = text.indexOf("베스");
                                if( ca != -1){
                                    result = "LG 베스트샵";
                                } /*else {
                                    ca = text.indexOf("현금");
                                    if( ca != -1){
                                        result = "현금";
                                    } else {
                                        ca = text.indexOf("현금");
                                        if( ca != -1){
                                            result = "현금";
                                        } else {
                                            ca = text.indexOf("현금");
                                            if( ca != -1){
                                                result = "현금";
                                            }
                                        }
                                    }
                                }*/
                            }
                        }
                    }
                }
            }
        }

        if(result.equals("")) result = "";

        return result;
    }


    public ArrayList<String> getDate(String text){
        int index = text.indexOf("2015");
        Character c1 = text.charAt(index+4);
        Character c2 = text.charAt(index+7);
        ArrayList<String> arr = new ArrayList<String>();

        if ((c1.toString().equals("/") || c1.toString().equals("-")) && (c2.toString().equals("/") || c2.toString().equals("-")) ){
            //  2015/04/02
            System.out.println("here2"); // 2015/02/05
            arr.add(text.substring(index, index+4));
            arr.add(text.substring(index+5, index+7));
            arr.add(text.substring(index+8, index+10));
        }else{
            System.out.println("here3");
            index = text.indexOf("2015", index + 2);
            c1 = text.charAt(index+4);
            c2 = text.charAt(index+7);
            arr = new ArrayList<String>();

            if ((c1.toString().equals("/") || c1.toString().equals("-")) && (c2.toString().equals("/") || c2.toString().equals("-")) ) {
                //  2015/04/02
                System.out.println("here4");
                arr.add(text.substring(index, index+4));
                arr.add(text.substring(index+5, index+7));
                arr.add(text.substring(index+8, index+10));
            }
        }
        return arr;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ocr_results, menu);
		return true;
	}

	class MessagePoster implements Runnable {
		public MessagePoster( String message )
		{
			_message = message;
		}

		public void run() {
            //tv.append( _message + "\n" );
			//setContentView( tv );

            Bundle bundle = new Bundle();
            bundle.putString("store", store);
            bundle.putString("year", year);
            bundle.putString("month", month);
            bundle.putString("date", day);
            bundle.putString("cost", price);
            bundle.putString("content", way);

            Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

		}


		private final String _message;
	}
}
