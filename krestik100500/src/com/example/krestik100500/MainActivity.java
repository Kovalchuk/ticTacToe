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
import android.graphics.Path.FillType;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {


	static final int cellFilledPhone = 0;
	static final int cellFilledUser = 1;
	static final int cellNotFilled = 2;
	
	
	Button [] arrayButton = new Button[9];	
	
	int [] line1 = {0, 1, 2};
	int [] line2 = {3, 4, 5};
	
	
	// 012
	// 345
	// 678

	int [][]arrayLines = {  {0,1,2}, {3,4,5}, {6, 7, 8},
							{0,3,6}, {1,4,7}, {2, 5, 8},
							{0,4,8}, {2,4,6}
						};
	
	
	
	private CheckBox chBox_O;
	private CheckBox chBox_X;
	private LinearLayout container;
	
	private TextView text1; 
	int[] mass = {
					cellNotFilled, cellNotFilled, cellNotFilled, 
					cellNotFilled, cellNotFilled, cellNotFilled, 
					cellNotFilled, cellNotFilled, cellNotFilled
				 };
	
	enum ResultGame { UNDEFINED, XWIN, OWIN, DRAW }	
	ResultGame resultGame = ResultGame.UNDEFINED;
	
	enum sign { X, O }
	sign mySign = sign.X;
	

	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        arrayButton[0] = (Button)findViewById(R.id.btnButton1);        
        arrayButton[1] = (Button)findViewById(R.id.btnButton2);
        arrayButton[2] = (Button)findViewById(R.id.btnButton3);
        arrayButton[3] = (Button)findViewById(R.id.btnButton4);
        arrayButton[4] = (Button)findViewById(R.id.btnButton5);
        arrayButton[5] = (Button)findViewById(R.id.btnButton6);
        arrayButton[6] = (Button)findViewById(R.id.btnButton7);
        arrayButton[7] = (Button)findViewById(R.id.btnButton8);
        arrayButton[8] = (Button)findViewById(R.id.btnButton9);   
        
        chBox_O = (CheckBox)findViewById(R.id.CheckBox_O);
        chBox_X = (CheckBox)findViewById(R.id.checkBox_X);
     
        text1 = (TextView)findViewById(R.id.textView1);    
        
    	refresh(null);

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
    private int getNumCellByRules1() {
    	return getNumCellByRules(cellFilledPhone);
    }
    private int getNumCellByRules2() {
    	return getNumCellByRules(cellFilledUser);
    }
    
    private int getNumCellByRules(int cellFilled) {
    	
    	for (int i = 0; i < arrayLines.length; i++) {
    		
    		int numCell = checkRules1(arrayLines[i][0], arrayLines[i][1], arrayLines[i][2], cellFilled);
    		if (numCell < 10)
    			return numCell;
    	}    	
    	
    	return 10;
    }
    
    private int checkRules1(int a, int b, int c, int cellFilled) {
    	if (mass[a] == cellNotFilled && mass[b] == mass[c] && mass[c] == cellFilled)
    		return a;
    	else if (mass[b] == cellNotFilled && mass[a] == mass[c] && mass[c] == cellFilled)
    		return b;
    	else if (mass[c] == cellNotFilled && mass[a] == mass[b] && mass[b] == cellFilled)
    		return c;
    	return 10;
    }

    private void setMySign(sign sig) {
    	mySign = sig;    	
    }
    private String getMySignStr() {
    	if (mySign == sign.X)
    		return "X";
    	else
    		return "O";    	
    }
    private String getPhoneSignStr() {
    	if (mySign == sign.X)
    		return "O";
    	else
    		return "X";    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }   
    
    public void myClick(View v){    	
    	chBox_O.setEnabled(false);
    	chBox_X.setEnabled(false);
    
    	
    	for (int a = 0; a < arrayButton.length; a++) {
    		if (arrayButton[a].getId() == v.getId()) {
    			arrayButton[a].setText(getMySignStr());
    			arrayButton[a].setEnabled(false);    
    			mass[a] = cellFilledUser;
    			break;
    		}
    	}

    	if (checkResult())
    		return;

    	clickPhone();    	
    	checkResult();    			
    }
    
    
    private int getNumCellByRulesZero3() {    	
    	int [] angels = {0, 2, 6, 8};
//    	
//    	List<Integer> list = Arrays.asList(0, 2, 6, 8);
//    	
//    	Random r = new Random();
//    	
//    	while (!list.isEmpty()) {
//    		int num = r.nextInt(list.size());
//    		if (mass[list.get(num)] == cellNotFilled)    		
//    	}
    	
    	
    	
    	
    	
    	for (int i = 0; i < angels.length; i++) {
    		if (mass[angels[i]] == cellNotFilled)
    			return angels[i];
    	}
    	return 10;
    }
    
    public void clickPhone() {
    	int numCell = 10;
    	
    	
    	numCell = getNumCellByRules1();		// First Rule;
    	if (fixPhoneMove(numCell))
			return;    	    	
    	numCell = getNumCellByRules2();		// Second Rule;
    	if (fixPhoneMove(numCell))
			return;
    	
    	if (0 == 0) {    		
    		if (mass[4] == cellFilledUser) {    			
    			numCell = getNumCellByRulesZero3();
    	    	if (fixPhoneMove(numCell))
    				return;
    		}
    		
    	}
    	
    	
    	for (int i = 0; i < 9; i++) {
    		if (mass[i] == cellNotFilled) {
    			fixPhoneMove(i);
    			break;
    		}
    	}    
    }
    
    private boolean fixPhoneMove (int numCell) {
    	if (numCell < 10) {
			mass[numCell] = cellFilledPhone;
			arrayButton[numCell].setText(getPhoneSignStr());
			arrayButton[numCell].setEnabled(false);
			return true;
    	}
    	return false;
    }
    
    public boolean checkResult(){   	
    	
    	int result = 2;    	
    	
    	if (mass[4] != cellNotFilled) {
			if (
					(mass[3] == mass[4] && mass[4] == mass[5]) || 
					(mass[1] == mass[4] && mass[4] == mass[7]) ||
					(mass[0] == mass[4] && mass[4] == mass[8]) ||
					(mass[2] == mass[4] && mass[4] == mass[6])
			   )
				result = mass[4];
    	}
    	
    	if (mass[0] != cellNotFilled) {
    		if (
    				(mass[0] == mass[1] && mass[1] == mass[2]) ||
    				(mass[0] == mass[3] && mass[3] == mass[6])
    		   )
    			result = mass[0];
    	}
    	
    	if (mass[8] != cellNotFilled) {
    		if (
    				(mass[2] == mass[5] && mass[5] == mass[8]) ||
    				(mass[6] == mass[7] && mass[7] == mass[8])
    		   )
    			result = mass[8];
    	}
    	
    	if (result != 2) {    		 
    		gameOver(result);
    		return true;
    	}
    	else {
			boolean cellsAreFilled = true;
			for (int i = 0; i < mass.length; i++)
				if (mass[i] == cellNotFilled) {
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
    	
    	text1.setText(str);
		text1.setVisibility(View.VISIBLE);
		
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
		
    	for (int i = 0; i < 9; i++) 
    		arrayButton[i].setClickable(false);
    }
    
    public void refresh(View v) {
    	text1.setVisibility(View.INVISIBLE);
    	text1.setText("");
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
    	
    	for (int i = 0; i < 9; i++) {
    		arrayButton[i].setEnabled(true);
    		arrayButton[i].setClickable(true);
    		arrayButton[i].setText("");
    		mass[i] = 2;
    	}    	
    }
    
    public void chooseX(View v) {
    	chBox_X.setChecked(true);
    	chBox_O.setChecked(false);
    	setMySign(sign.X);
    }
    public void chooseO(View v) {
    	chBox_O.setChecked(true);
    	chBox_X.setChecked(false);
    	setMySign(sign.O);
    }
}
