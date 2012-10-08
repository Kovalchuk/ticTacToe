//package com.example.krestik100500;
//
//import android.os.Bundle;
//import android.app.Activity;
//import android.view.Menu;
//
//public class MainActivity extends Activity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
//}


package com.example.krestik100500;


import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import android.view.Display; 
import java.io.*; 

public class MainActivity extends Activity {
	private EditText edtext;
	private Button but0;
	private Button but1;
	private Button but2;
	private Button but3;
	private Button but4;
	private Button but5;
	private Button but6;
	private Button but7; 
	private Button but8;	
	private CheckBox chBox_O;
	private CheckBox chBox_X;
	private LinearLayout container;
	
	private TextView text1; 
	int[] mass ={2, 2, 2, 2, 2, 2, 2, 2, 2}; 
	
	
	enum ResultGame { UNDEFINED, XWIN, OWIN, DRAW }
	
	ResultGame resultGame = ResultGame.UNDEFINED;  

	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        but0 = (Button)findViewById(R.id.btnButton1);
        but1 = (Button)findViewById(R.id.btnButton2);
        but2 = (Button)findViewById(R.id.btnButton3);
        but3 = (Button)findViewById(R.id.btnButton4);
        but4 = (Button)findViewById(R.id.btnButton5);
        but5 = (Button)findViewById(R.id.btnButton6);
        but6 = (Button)findViewById(R.id.btnButton7);
        but7 = (Button)findViewById(R.id.btnButton8);
        but8 = (Button)findViewById(R.id.btnButton9);   
        
        chBox_O = (CheckBox)findViewById(R.id.CheckBox_O);
        chBox_X = (CheckBox)findViewById(R.id.checkBox_X);
     
        text1 = (TextView)findViewById(R.id.textView1);    
        
    	refresh(null);    	
  
//        
//        finish();  
        

        

        

//        
//        
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//        but1.setLayoutParams(params);
//
//        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.RIGHT_OF, but1.getId());
//        but2.setLayoutParams(params);
        
        
//        but2 = (Button)findViewById(R.id.button2);

        
        
        
        
//        linearLayout = (LinearLayout)findViewById(R.id.layout1);
        
        
        
//    	Display display=getWindowManager().getDefaultDisplay();
//        int width=display.getWidth();
//        but1.setWidth(width/3);
//        but2.setWidth(width/3);    
        
//         
        
//        but1.setLayoutParams(new LayoutParams(20, 30));
//        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(100,50, 200, 200);
//        but1.setLayoutParams(new LayoutParams(20,20));
//        linearLayout.addView(but1, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public Button getButtonByNumber(int num) {
    	Button b = new Button(this);
    	switch (num) 
    	{
    	    case 0:
    	    	b = but0;
    	        break;
    	    case 1:
    	    	b = but1;
    	        break;
    	    case 2:
    	    	b = but2;
    	        break;
    	    case 3:
    	    	b = but3;
    	        break;
    	    case 4:
    	    	b = but4;
    	        break;
    	    case 5:
    	    	b = but5;
    	        break;
    	    case 6:
    	    	b = but6;
    	        break;
    	    case 7:
    	    	b = but7;
    	        break;
    	    case 8:
    	    	b = but8;
    	        break;
    	} 	
    	
    	return b;
    }
    
    public void myClick(View v){    	
    	chBox_O.setEnabled(false);
    	chBox_X.setEnabled(false);

    	Button butt = (Button) v;
    	butt.setText("X");
    	butt.setEnabled(false);    	
    	
    	switch (v.getId()) 
    	{
    	    case R.id.btnButton1:
    	    	System.out.println("button 0");
    	    	mass[0] = 1;
    	        break;
    	    case R.id.btnButton2:
    	    	System.out.println("button 1");
    	    	mass[1] = 1;
    	        break;
    	    case R.id.btnButton3:
    	    	System.out.println("button 2");
    	    	mass[2] = 1;
    	        break;
    	    case R.id.btnButton4:
    	    	System.out.println("button 3");
    	    	mass[3] = 1;
    	        break;
    	    case R.id.btnButton5:
    	    	System.out.println("button 4");
    	    	mass[4] = 1;
    	        break;
    	    case R.id.btnButton6:
    	    	System.out.println("button 5");
    	    	mass[5] = 1;
    	        break;
    	    case R.id.btnButton7:
    	    	System.out.println("button 6");
    	    	mass[6] = 1;
    	        break;
    	    case R.id.btnButton8:
    	    	System.out.println("button 7");
    	    	mass[7] = 1;
    	        break;
    	    case R.id.btnButton9:
    	    	System.out.println("button 8");
    	    	mass[8] = 1;
    	        break;
        }    	

    	if (checkResult())
    		return;

    	clickPhone();    	
    	checkResult();    			
    }
    
    public void clickPhone(){
    	
    	for (int i = 0; i < 9; i++) {
    		if (mass[i] == 2) {
    			mass[i] = 0;
    			System.out.println(100500);
    			System.out.println(i);
    			Button button = getButtonByNumber(i);   		
    			button.setText("O");
    			button.setEnabled(false);
    			break;
    		}
    	}    
    }
    public boolean checkResult(){   	
    	
    	int result = 2;    	
    	
    	if (mass[4] != 2) {
			if (
					(mass[3] == mass[4] && mass[4] == mass[5]) || 
					(mass[1] == mass[4] && mass[4] == mass[7]) ||
					(mass[0] == mass[4] && mass[4] == mass[8]) ||
					(mass[2] == mass[4] && mass[4] == mass[6])
			   )
				result = mass[4];
    	}
    	
    	if (mass[0] != 2) {
    		if (
    				(mass[0] == mass[1] && mass[1] == mass[2]) ||
    				(mass[0] == mass[3] && mass[3] == mass[6])
    		   )
    			result = mass[0];
    	}
    	
    	if (mass[8] != 2) {
    		if (
    				(mass[2] == mass[5] && mass[5] == mass[8]) ||
    				(mass[6] == mass[7] && mass[7] == mass[8])
    		   )
    			result = mass[8];
    	}
    	
//    	boolean draw = true;
//    	for (int i = 0; i < mass.length; i++)
//    		if  (mass[i] == 2)
//    			draw = false;
    	

    	

    	
    	if (result != 2) {    		 
    		gameOver(result);
    		return true;
    	}
    	else {
			boolean cellsAreFilled = true;
			for (int i = 0; i < mass.length; i++)
				if (mass[i] == 2) {
					cellsAreFilled = false;
					break;
				}
			
			if (cellsAreFilled)
				gameOver(3);
			
		}
    	return false;
    }
    
    public void gameOver(int result) {
    	
    	String str;
    	
    	if (result == 0)    	
    		str = "Телефон умнее тебя :-)";
    	else if (result == 1)    	
    		str = "ПОЗДАВЛЯЮ!!!";
    	else if(result == 3)
    		str = "НИЧЬЯ!";
    	else
    		str = "Какая-то ошибка";
    	

//        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
//        dlgAlert.setMessage("Игра окончена.");
//        dlgAlert.setTitle(str);              
//        dlgAlert.setPositiveButton("OK", null);
//        dlgAlert.setCancelable(true);
//        dlgAlert.create().show();
    	
    	text1.setText(str);
		text1.setVisibility(View.VISIBLE);
		
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
		
    	for (int i = 0; i < 9; i++) {
    		Button button = getButtonByNumber(i);   		
    		button.setClickable(false);    		
    	} 		
    }
    
    public void refresh(View v) {
    	text1.setVisibility(View.INVISIBLE);
    	text1.setText("");
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
    	
    	for (int i = 0; i < 9; i++) {
    		Button button = getButtonByNumber(i); 
    		button.setEnabled(true);    		
    		button.setClickable(true);
    		button.setText("");
    		mass[i] = 2;
    	}    	
    }
    
    public void chooseX(View v) {
    	chBox_X.setChecked(true);
    	chBox_O.setChecked(false);
    }
    public void chooseO(View v) {
    	chBox_O.setChecked(true);
    	chBox_X.setChecked(false);
    }
}
