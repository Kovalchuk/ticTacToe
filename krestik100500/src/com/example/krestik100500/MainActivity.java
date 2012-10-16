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
import android.graphics.Bitmap;
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
import android.view.Window;
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
	static final int sign_O = 0;
	static final int sign_X = 1;
	
	int[] mass = {
			cellNotFilled, cellNotFilled, cellNotFilled, 
			cellNotFilled, cellNotFilled, cellNotFilled, 
			cellNotFilled, cellNotFilled, cellNotFilled
 		 };
	int my_sign = sign_X;
	
	enum GameResult { WIN_USER, WIN_PHONE, WIN_DRAW, WIN_UNDEFINED }
	enum GameDificult { SIMPLE, MEDIUM, HARD }
	GameDificult gameDificult = GameDificult.SIMPLE;	
	
	Button [] arrayButton = new Button[9];
	int [][]arrayLines = {
							{0,1,2}, {3,4,5}, {6, 7, 8},
							{0,3,6}, {1,4,7}, {2, 5, 8},
							{0,4,8}, {2,4,6}
						 };
	

	
	List<Pair<Integer, Integer>> listHistoryStep = new ArrayList<Pair<Integer, Integer>>();  // Кто ходил, куда ходил.		
	ArrayList<Integer> listHistory_STATE = new ArrayList<Integer>();
	
	private CheckBox chBox_O;
	private CheckBox chBox_X;
	private CheckBox chBox_Simple;
	private CheckBox chBox_Medium; 
	private CheckBox chBox_Hard;	
	
	private TextView text1;	

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
        chBox_Simple = (CheckBox)findViewById(R.id.checkBox_Simple);
        chBox_Medium = (CheckBox)findViewById(R.id.checkBox_Medium);
        chBox_Hard = (CheckBox)findViewById(R.id.checkBox_Hard);
     
        text1 = (TextView)findViewById(R.id.textView1);

        chooseX(null);
        chooseSimple(null);
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
    
    static final String MASS = "main massive in this game";
    static final String MY_SIGN = "my sign - X or O";
    static final String LIST_HISTORY = "my list history step - who and where";
    static final String GAME_DIFFICULTY = "game difficulty";
    static final String CHECK_BOX_ENABLE = "check box enable";
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
    	
    	listHistory_STATE.clear();
        for (int i = 0; i < listHistoryStep.size(); i++) {        	
        	listHistory_STATE.add(listHistoryStep.get(i).first);
        	listHistory_STATE.add(listHistoryStep.get(i).second);
        }
    	        
        savedInstanceState.putInt(MY_SIGN, my_sign);
        savedInstanceState.putInt(GAME_DIFFICULTY, getGameDificultInt());
        savedInstanceState.putIntArray(MASS, mass);
        savedInstanceState.putIntegerArrayList(LIST_HISTORY, listHistory_STATE);
        savedInstanceState.putBoolean(CHECK_BOX_ENABLE, chBox_X.isEnabled());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
       
        // Restore state members from saved instance
        my_sign = savedInstanceState.getInt(MY_SIGN);
        setGameDificultInt(savedInstanceState.getInt(GAME_DIFFICULTY));
        mass = savedInstanceState.getIntArray(MASS);
        listHistory_STATE = savedInstanceState.getIntegerArrayList(LIST_HISTORY);
        boolean enabled = savedInstanceState.getBoolean(CHECK_BOX_ENABLE); 

        chBox_O.setEnabled(enabled); 
        chBox_X.setEnabled(enabled);
        chBox_Simple.setEnabled(enabled);
        chBox_Medium.setEnabled(enabled);
        chBox_Hard.setEnabled(enabled);
        
        listHistoryStep.clear();
        for (int i = 0; i < listHistory_STATE.size(); i = i + 2) {
        	int a = i;
        	int b = i + 1;
        	Pair<Integer, Integer> p = new Pair<Integer, Integer>(listHistory_STATE.get(a),listHistory_STATE.get(b));
        	listHistoryStep.add(p);
        }
        
        for (int i = 0; i < mass.length; i++) {
        	if (mass[i] == cellFilledPhone) {
        		arrayButton[i].setEnabled(false);
        		arrayButton[i].setText(getPhoneSignStr());
        	}
        	else if (mass[i] == cellFilledUser) {
        		arrayButton[i].setEnabled(false);
        		arrayButton[i].setText(getMySignStr());
        	} 
        }

    }


    private void setMySign(int sign) {
    	my_sign = sign;
    }
    private String getMySignStr() {
    	if (my_sign == sign_X)
    		return "X";
    	else
    		return "O";    	
    }
    private String getPhoneSignStr() {
    	if (my_sign == sign_X)
    		return "O";
    	else
    		return "X";    	
    }
    
    private int getGameDificultInt() {
    	if (gameDificult == GameDificult.MEDIUM)
    		return 1;
    	else if (gameDificult == GameDificult.HARD)
    		return 2;
    	else
    		return 0;
    }
    private void setGameDificultInt(int dif) {
    	switch (dif) {
		case 1:
			gameDificult = GameDificult.MEDIUM;
			break;
		case 2:
			gameDificult = GameDificult.HARD;
			break;
		default:
			gameDificult = GameDificult.SIMPLE;
			break;
		}    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void myClick(View v){    	
    	chBox_O.setEnabled(false);
    	chBox_X.setEnabled(false);
    	chBox_Simple.setEnabled(false);
    	chBox_Medium.setEnabled(false);
    	chBox_Hard.setEnabled(false);
    	
     
    	for (int a = 0; a < arrayButton.length; a++) {
    		if (arrayButton[a].getId() == v.getId()) {
    			arrayButton[a].setText(getMySignStr());
    			

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
    	
    	if (gameDificult == GameDificult.MEDIUM) {
    		
    		numCell = getNumCellByRules1();		// First Rule;
        	if (fixPhoneMove(numCell))
    			return;    	    	
        	numCell = getNumCellByRules2();		// Second Rule;
        	if (fixPhoneMove(numCell))
    			return;
    	}
    	else if (gameDificult == GameDificult.HARD) {
        	
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
    	}
    	
    	

    
    	numCell = getAnyAnyCellNumber();
    	fixPhoneMove(numCell);
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
    		return getAnyCellNumber(true);    		
    	}
    	return 10;
    }
    private int getNumCellByRulesZero4() {
    	
    	boolean XFirstMoveWasToCorner = stepWasToCorner(0, cellFilledUser);
    	
    	if (XFirstMoveWasToCorner && mass[4] == cellNotFilled)
    		return 4;    	
    	
    	if (XFirstMoveWasToCorner) {
    		int opposite = getOppositeSide(listHistoryStep.get(0).second);
    		
    		if (mass[opposite] == cellNotFilled)
    			return opposite;
    		else 
    			return getAnyCellNumber(false);    			
    	}    
    	return 10;
    }
    
    private int getNumCellByRulesZero5() {
    	
    	boolean XFirstMoveWasToCorner = stepWasToCorner(0, cellFilledUser);    	
    	if (!XFirstMoveWasToCorner && mass[4] == cellNotFilled)
    		return 4;
    	
    	boolean XSecondMoveWasToCorner = stepWasToCorner(2, cellFilledUser);
    	int opposite = getOppositeSide(listHistoryStep.get(2).second);
    	if (XSecondMoveWasToCorner && (mass[opposite] == cellNotFilled))
    		return opposite;    	
    	else {
    		if (mass[opposite] == cellFilledUser) {	
    			return getAnyCellNumber(true);
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
    
    
    

    
    private int getAnyAnyCellNumber() {
    	
    	List<Integer> list =  new ArrayList< Integer>();
    	for (int i = 0; i < mass.length; i++) {
    		if (mass[i] == cellNotFilled)
    			list.add(i);	    		
    	}
    	
    	if (list.size() > 0) {
	    	Random r = new Random();
	    	int num = r.nextInt(list.size());
	    	return list.get(num);
    	}
    	return 10;
    }
    
    private int getAnyCellNumber(boolean corner) {
    	int [] angels = new int[4];
    	if (corner) {
    		angels[0] = 0;
    		angels[1] = 2;
    		angels[2] = 6;
    		angels[3] = 8;
    	}
    	else {
    		angels[0] = 1;
    		angels[1] = 3;
    		angels[2] = 5;
    		angels[3] = 7;    		
    	}
    	
    	List<Integer> list =  new ArrayList< Integer>();
    	for (int i = 0; i < angels.length; i++) {
    		if (mass[angels[i]] == cellNotFilled)
    			list.add(angels[i]);	    		
    	}
    	
    	if (list.size() > 0) {
	    	Random r = new Random();
	    	int num = r.nextInt(list.size());
	    	return list.get(num);
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
    	chBox_Simple.setEnabled(true); 
    	chBox_Medium.setEnabled(true);
    	chBox_Hard.setEnabled(true); 
    	
		
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
    	chBox_O.setEnabled(true);
    	chBox_X.setEnabled(true);
    	chBox_O.setEnabled(true);
    	
    	listHistoryStep.clear();
    	
    	for (int i = 0; i < 9; i++) {
    		arrayButton[i].setEnabled(true);
    		arrayButton[i].setClickable(true); 
    		arrayButton[i].setText("");
    		arrayButton[i].setWidth(150);
    		arrayButton[i].setHeight(150);
    		mass[i] = 2;
    	}    	
    }
    
    public void chooseX(View v) {
    	chBox_X.setChecked(true);
    	chBox_O.setChecked(false);
    	setMySign(sign_X);
    }
    public void chooseO(View v) {
    	chBox_O.setChecked(true);
    	chBox_X.setChecked(false);
    	setMySign(sign_O);
    }
    public void chooseSimple(View v) {
    	chBox_Simple.setChecked(true);
    	chBox_Medium.setChecked(false);
    	chBox_Hard.setChecked(false);
    	gameDificult = GameDificult.SIMPLE;
    }
    public void chooseMedium(View v) {
    	chBox_Simple.setChecked(false);
    	chBox_Medium.setChecked(true);
    	chBox_Hard.setChecked(false);
    	gameDificult = GameDificult.MEDIUM;
    }
    public void chooseHard(View v) {
    	chBox_Simple.setChecked(false);
    	chBox_Medium.setChecked(false);
    	chBox_Hard.setChecked(true);
    	gameDificult = GameDificult.HARD;
    }
}
