package com.example.t1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button[] buttons;
	Button valbutton;
	EditText valuebox;
  int a[][]={{0,1,1,0,0,0},
			{0,1,2,2,0,0},
			{0,1,1,2,3,0},
			{17,0,2,2,4,0},
			{17,5,5,5,6,0},
			{17,0,0,8,7,0},
			{17,9,8,8,10,10},
			{17,11,12,12,12,13},
			{17,15,12,14,0,16},
			{17,12,12,12,16,19}}; //The initial box configured here
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      buttons = new Button[60];
      valbutton=(Button)findViewById(R.id.b);
      valuebox=(EditText)findViewById(R.id.editText1);
      load();//loads  page with coloured boxes
  }  
  public int getColor(int num){   // returns the color of specific number
    	switch(num){
    	case 0: return Color.WHITE;
    	case 1: return 	Color.rgb(255,192,203);
    	case 2: return Color.rgb(0,128,0);
    	case 3: return Color.rgb(255,0,0);
    	case 4: return Color.rgb(192,192,192);
    	case 5: return Color.rgb(240,230,140);
    	case 6: return Color.rgb(0,255,255);
    	case 7: return Color.rgb(192,192,192);
    	case 8: return Color.rgb(0,0,205);
    	case 9: return Color.rgb(192,192,192);
    	case 10: return Color.rgb(152,251,152);
    	case 11: return Color.rgb(0,255,255);
    	case 12: return Color.rgb(189,183,107);
    	case 13: return Color.rgb(192,192,192);
    	case 14: return Color.rgb(192,192,192);
    	case 15: return Color.rgb(192,192,192);
    	case 16: return Color.rgb(0,255,255);
    	case 17: return Color.rgb(128,0,128);
    	case 19: return Color.rgb(192,192,192);
        default:return Color.WHITE;
    	}
    }
    public boolean present(int n)
    {
    	for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            {
                if(a[i][j] == n)
                    return true;
            }
        }
    	return false;
    }
    public void check(View v)//called when button is clicked
    {
    	int n=Integer.parseInt(valuebox.getText().toString());
    	if(present(n))
    	{
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            {
                if(a[i][j] == n)
                    a[i][j]=0;
            }
        }
        for(int i=0;i<a.length-1;i++)
        {
            String s="";
            for(int j=0;j<a[i].length;j++)
            {
                s+=a[i][j];  //every single row is stored as string
            }
            String[] comb=find(s); // This matches a pattern having consecutive substring of recurring number in every row instance
            for (String comb1 : comb) {
                if (!comb1.contains("0")) {
                    if(!(comb1.length() == 0))
                    {
                        if(findbelow(i,s.indexOf(comb1),comb1.length()))
                        { 
                            System.out.println("Insert here");
                            align(i,s.indexOf(comb1)-1,comb1.length());
                        }
                        else
                            System.out.println("not insert");
                    }
                }
            }
        }
        load();
    	}
    	else
    	{
    		Toast.makeText(getApplicationContext(), "Given no not present", Toast.LENGTH_SHORT).show();
    	}
    }
    public void load()
    {
    	int count=1;
        for(int i = 0; i < 10; i++){
        	for(int j=0;j<6;j++){
        	String buttonID = "button" + count;
        	int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        	buttons[count-1] = ((Button) findViewById(resID));
        	if(a[i][j]== 0)
        		buttons[count-1].setText("");
        	else
        		buttons[count-1].setText(a[i][j]+"");
        	buttons[count-1].setBackgroundColor(getColor(a[i][j]));
        	count++;
        }
        }
    }
    public void align(int endi,int startj,int n)
    {
        int endj=startj+n;
        int starti=0;
        System.out.println("starti endi startj endj"+starti+""+endi+""+startj+""+endj);
        for(int i=endi;i>=starti;i--)
        {
            for(int j=endj-1;j>=startj;j--)
            {
                System.out.print(a[i][j]);
                a[i+1][j]=a[i][j];
            }
        System.out.println("");
        }
        for(int j=startj;j<endj;j++)
             a[0][j]=0;
        System.out.println("After alignment");
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            {
                System.out.print(a[i][j]+" ");
            }
            System.out.println("");
        }
    }
    public  boolean findbelow(int i,int j,int n)
    {
        j-=1;
        System.out.println("i: j: n"+i+""+j+""+n);
        int t=j+n;
        for(;j<t;j++)
        {
            System.out.println("j is:"+j+"and j+n"+t);
            System.out.println("checking"+a[i+1][j]);
            if(a[i+1][j] != 0)
            {
                return false;
            }
        }
        return true;
    }
    private  String[] find(String s) {
        String[] str={"",""};
        if(s.contains("000000"))
            return str;
        else{
         Matcher m = Pattern.compile("(.+)\\1+").matcher(s);
         int count=0;
         while (m.find()) {
             str[count]=m.group();
             count++;
            System.out.println(m.group());
         }
         return str;
        }
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
