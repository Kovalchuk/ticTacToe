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


import android.R.drawable;
import android.R.string;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import android.view.Display; 
import java.io.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {


	static final int cellFilledPhone = 0;
	static final int cellFilledUser = 1;
	static final int cellNotFilled = 2;
	
	enum GameResult { WIN_USER, WIN_PHONE, WIN_DRAW, WIN_UNDEFINED }
	
	
	
	
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
//	private TextView text2;
	int[] mass = {
					cellNotFilled, cellNotFilled, cellNotFilled, 
					cellNotFilled, cellNotFilled, cellNotFilled, 
					cellNotFilled, cellNotFilled, cellNotFilled
				 };
	
	List<Pair<Integer, Integer>> listHistoryStep = new ArrayList<Pair<Integer, Integer>>();  // Кто ходил, куда ходил.
	Pair<Integer, Integer> p;
	
	enum sign { X, O }
	sign mySign = sign.X;
	

	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
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
//        text2 = (TextView)findViewById(R.id.textView2);
        
    	refresh(null); 
    }
    
    @Override
    public void onBackPressed(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Выход");
        builder.setMessage("Вы действительно хотите выйти?");

        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	System.exit(0);
            }
        });

        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
        });

        AlertDialog alert = builder.create();
        alert.show();
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
    	
		ImageButton but = new ImageButton(this);
		Resources res = getResources();
		Drawable drawable = res.getDrawable(R.drawable.image_x);		
		but.setImageDrawable(drawable);
    
    	 
    	for (int a = 0; a < arrayButton.length; a++) {
    		if (arrayButton[a].getId() == v.getId()) {
    			arrayButton[a].setText(getMySignStr());
    			arrayButton[a].setBackgroundResource(R.drawable.image_x);
    			arrayButton[a].setEnabled(false);    
    			mass[a] = cellFilledUser;    			 
    			listHistoryStep.add(Pair.create(cellFilledUser, a));
    			break;
    		}
    	}

    	if (checkResult())
    		return;

    	clickPhone();    	
    	checkResult();    			
    }
    
    public void clickPhone() {
    	int numCell = 10;    	
    	
    	numCell = getNumCellByRules1();		// First Rule;
    	if (fixPhoneMove(numCell))
			return;    	    	
    	numCell = getNumCellByRules2();		// Second Rule;
    	if (fixPhoneMove(numCell))
			return;
		
		numCell = getNumCellByRulesZero3(); // First Rule by Zero;
    	if (fixPhoneMove(numCell))
			return;
    	
		numCell = getNumCellByRulesZero4(); // Fourth Rule by Zero;
    	if (fixPhoneMove(numCell))
			return;
    	
		numCell = getNumCellByRulesZero5(); // Fourth Rule by Zero;
    	if (fixPhoneMove(numCell))
			return;
    	
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
			listHistoryStep.add(Pair.create(cellFilledPhone, numCell));
			return true;
    	}
    	return false;
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
    
    private int getNumCellByRulesZero3() {    	
    	if (listHistoryStep.get(0).first == cellFilledUser && listHistoryStep.get(0).second == 4) {
    	
	    	int [] angels = {0, 2, 6, 8};
	    	
	//    	List<Integer> list = Arrays.asList(0, 2, 6, 8);    	
	//    	Random r = new Random();    	
	//    	while (!list.isEmpty()) {
	//    		int num = r.nextInt(list.size());
	//    		if (mass[list.get(num)] == cellNotFilled)
	//    			return list.get(num);    		
	//    		list.remove(num);
	//    	}    	
	    	
	    	for (int i = 0; i < angels.length; i++) {
	    		if (mass[angels[i]] == cellNotFilled)
	    			return angels[i];
	    	}
    	}
    	return 10;
    }
    private int getNumCellByRulesZero4() {
    	
    	boolean XFirstMoveWasToCorner = false;
    	if (listHistoryStep.get(0).first == cellFilledUser && 
    			(listHistoryStep.get(0).second == 0 || 
    			 listHistoryStep.get(0).second == 2 || 
    			 listHistoryStep.get(0).second == 6 || 
    			 listHistoryStep.get(0).second == 8) ) {
    		XFirstMoveWasToCorner = true;
    	}
    	
    	if (XFirstMoveWasToCorner && mass[4] == cellNotFilled)
    		return 4;
    	
    	
    	if (XFirstMoveWasToCorner) {
    		int opposite = -1;
    		switch (listHistoryStep.get(0).second) {
			case 0:
				opposite = 8;
				break;
			case 2:
				opposite = 6;
				break;
			case 6:
				opposite = 2;
				break;
			case 8:
				opposite = 0;
				break;
			default: 
				break;
			}
    		
    		if (mass[opposite] == cellNotFilled)
    			return opposite;
    		else {			// RANDOM RANDOM RANDOM
    			if (mass[1] == cellNotFilled)
    				return 1;
    			else if (mass[3] == cellNotFilled)
    				return 3;
    			else if (mass[5] == cellNotFilled)
    				return 5;
    			else if (mass[7] == cellNotFilled)
    				return 7;
    		}
    			
    	}
    
    	return 10;
    }
    
    private int getNumCellByRulesZero5() {
    	
    	boolean XFirstMoveWasToCorner = stepWasToCorner(0, cellFilledUser);    	
    	if (!XFirstMoveWasToCorner && mass[4] == cellNotFilled)
    		return 4;
    	
    	boolean XSecondMoveWasToCorner = stepWasToCorner(2, cellFilledUser);
    	int opposite = getOppositeSide(listHistoryStep.get(2).second);
    	if (XSecondMoveWasToCorner) {    		
    		if (mass[opposite] == cellNotFilled)
    			return opposite;
    	}
    	else {
    		if (mass[opposite] == cellFilledUser) {	// GO TO CORNER
    			if (mass[0] == cellNotFilled)	// RANDOM RANDOM RANDOM
    				return 0;
    			else if (mass[2] == cellNotFilled)
    				return 2;
    			else if (mass[6] == cellNotFilled)
    				return 6;
    			else if (mass[8] == cellNotFilled)
    				return 8;
    		}
    		else {

    			int numCell = 10;
    			switch (listHistoryStep.get(0).second) {
				case 1:
    				if (listHistoryStep.get(2).second == 3)
    					numCell = 0;
    				else if (listHistoryStep.get(2).second == 5)
    					numCell = 2;					
					break;
				case 3:
    				if (listHistoryStep.get(2).second == 1)
    					numCell = 0;
    				else if (listHistoryStep.get(2).second == 7)
    					numCell = 6;					
					break;
				case 5:
    				if (listHistoryStep.get(2).second == 1)
    					numCell = 2;
    				else if (listHistoryStep.get(2).second == 7)
    					numCell = 8;    				
					break;
				case 7:
    				if (listHistoryStep.get(2).second == 3)
    					numCell = 6;
    				else if (listHistoryStep.get(2).second == 5)
    					numCell = 8;
    				break;
				}
    			if ((numCell != 10) && mass[numCell] == cellNotFilled)
    				return numCell;
    		}
    	}
    	
    	
    	
    	
    	
    	return 10;
    }
    
    
    
    
    
    
    
    private boolean stepWasToCorner(int numberStep, int whoWent) {
    	if (listHistoryStep.get(numberStep).first == whoWent &&
    			(listHistoryStep.get(numberStep).second == 0 ||
    			listHistoryStep.get(numberStep).second == 2 ||
    			listHistoryStep.get(numberStep).second == 6 ||
    			listHistoryStep.get(numberStep).second == 8) ) {
    		return true;
    		}
    	return false;
    }
    private int getOppositeSide(int numberCell) {
    	switch (numberCell) {
		case 0:
			return 8;
		case 1:
			return 7;
		case 2:
			return 6;
		case 3:
			return 5;
		case 5:
			return 3;
		case 6:
			return 2;
		case 7:
			return 1;
		case 8:
			return 0;
		}
    	return 4;    	
    }
    
    
    
    
    
    
    
    
    
    
    public boolean checkResult(){   	
    	
    	GameResult result = GameResult.WIN_UNDEFINED;
    	
    	for (int i = 0; i < arrayLines.length; i++) {
    		int a = arrayLines[i][0];
    		int b = arrayLines[i][1];
    		int c = arrayLines[i][2];
    		if (mass[a] == mass[b] && mass[b] == mass[c] && mass[c] != cellNotFilled) {
    			if (mass[a] == cellFilledPhone)
    				result = GameResult.WIN_PHONE;
    			else
    				result = GameResult.WIN_USER;
    			break;
    		}
    	}
    	
    	if (result == GameResult.WIN_UNDEFINED && listHistoryStep.size() == 9)
    		result = GameResult.WIN_DRAW;
    	
    	if (result != GameResult.WIN_UNDEFINED) {
    		gameOver(result);
    		return true;
    	}

    	return false;
    }
    
    public void gameOver(GameResult result) {
    	
    	String str;
    	switch (result) {
		case WIN_USER:
			str = "ПОЗДАВЛЯЮ!!!";
			break;
		case WIN_PHONE:
			str = "Телефон умнее тебя :-)";
			break;
		case WIN_DRAW:
			str = "НИЧЬЯ!";
			break;
		default:
			str = "Какая-то ошибка";
			break;
		}    				 

		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);		
		toast.setGravity(Gravity.CENTER, 0, 0);		
		toast.show();		
	     
		

    	chBox_O.setEnabled(true); 
    	chBox_X.setEnabled(true);
		
    	for (int i = 0; i < 9; i++) 
    		arrayButton[i].setClickable(false);
    	
		new CountDownTimer(2000, 1000) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	 refresh(null);
		     }
		  }.start();
    	}


    
    public void refresh(View v) {
    	text1.setVisibility(View.INVISIBLE);
    	text1.setText("");
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
    	listHistoryStep.clear();
    	
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
