package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements Initializable {
	public Stage gameStage;
	@FXML private ImageView gameImg1;
	@FXML private ImageView gameImg2;
	@FXML private ImageView gameImg3;
	@FXML private Button gameBtnStart;
	@FXML private Button gameBtnExit;
	@FXML private RadioButton gameRdo1;
	@FXML private RadioButton gameRdo2;
	@FXML private RadioButton gameRdo3;
	@FXML private RadioButton gameRdo4;
	@FXML private RadioButton gameRdo5;
	@FXML private RadioButton gameRdo6;
	@FXML private RadioButton gameRdo7;
	@FXML private RadioButton gameRdo8;
	@FXML private RadioButton gameRdo9;
	@FXML private Label gameLabName;
	@FXML private Label gameLabMysc;
	@FXML private Label gameLabOthersc;
	@FXML private Label gameLabFirst;
	@FXML private Label gameLabSecond;
	@FXML private Label gameLabThird;
	@FXML private TextField gameTxtInsert;
	@FXML private TextField gameTxtGet;
	@FXML private TextArea gameTxtArea;
	@FXML private ToggleGroup group;
	Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private char me, other;
	int score=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startClient();
		gameLabName.setText(LoginController.nickname);
		handleRdoCheckAction();
		gameBtnExit.setOnAction(e-> gameStage.close());
	}
	
	
	public void startClient() {
	      Runnable runnable = new Runnable() {
	         @Override
	         public void run() {
	            socket = new Socket();
	            try {
	               socket.connect(new InetSocketAddress("192.168.0.175", 9000));
	               Platform.runLater(() -> {
	                  gameTxtArea.appendText("연결 완료 : " + LoginController.nickname + "\n");
	               });
	               
	            try {
   					gameLabMysc.setText(score+"");
					gameLabOthersc.setText(score+"");
   			    } catch (Exception e1) {}
	               
	               InputStream is = socket.getInputStream();
	       		   InputStreamReader isr = new InputStreamReader(is);
	       		   br = new BufferedReader(isr);

	       		   OutputStream os = socket.getOutputStream();
	       		   pw = new PrintWriter(os, true);
	       		

	       		try {
	    			while (true) {	
	    				selectToggleButton();
	 	       		    gameBtnStart.setOnMouseClicked(e ->pw.println("PLAY2") );
	 	       		        
	    				String response = br.readLine();
	    				String value=response.substring(0,5);
	    				switch(value) {
	    				case"START":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									me=response.charAt(6);
			    					other=(me=='1')?('2'):('1');
			    					gameTxtArea.setText("경기가 시작됩니다.\n");                                
			    					gameBtnStart.setDisable(true);	
								}
							});
	    					break;
	    				case"OTHER":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									gameLabOthersc.setText(response.substring(6));	
								}
							});
	    					break;
	    				case"PRINT":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									String string=response.substring(6);
			    					gameTxtArea.setText(string);	
								}
							});
	    					break;
	    				case"CLICK":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									editToggleButton(true);	
			    					gameTxtArea.setText("기다리세요..");
			    					gameBtnStart.setDisable(true);	
								}
							});	
	    					break; 
	    				case"CLIC2":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									editToggleButton(true);
			    					gameTxtArea.setText("준비끝! 시작버튼을 눌러주세요");
			    					gameBtnStart.setDisable(false);	
								}
							});			
	    					break;
	    				case"SGAME":
	    					Platform.runLater(new Runnable() {
								@Override
								public void run() {
									String random1=response.substring(5,10);
			    					String random2=response.substring(10,15);
			    					String random3=response.substring(15,20);
			    					handleGameStartAction(Integer.parseInt(random1),Integer.parseInt(random2),Integer.parseInt(random3));
								}
							});			
	    					break;	
	    				default:
	    					gameTxtArea.setText("오류가 발생하였습니다.");
	    					break;
	    				}
	    				try {
	    					Thread.sleep(200);
	    				} catch (Exception e) {
	    					
	    				}
	    				
	    			}
	    		} catch (Exception e) {
	    			
	    			
	    		}finally{
	    			if (!socket.isClosed()) {
	    				try {
	    					socket.close();
	    				} catch (IOException e1) {

	    				}
	    			}
	    		}
	       	 
	            } catch (Exception e) {
	               if (!socket.isClosed()) {
	                 // stopClient();
	               }
	               return;
	            }
	         }// end of run
	      };// end of Runnable

	      Thread thread = new Thread(runnable);
	      thread.start();
	   }


	private void handleGameStartAction(int a, int b, int c) {
		editToggleButton(true);
		gameTxtArea.clear();
		gameLabFirst.setText("");
		gameLabSecond.setText("");
		gameLabThird.setText("");
		
		gameImg1.setTranslateX(0);
		gameImg2.setTranslateX(0);
		gameImg3.setTranslateX(0);

		Timeline timeline = new Timeline();
		KeyValue keyvalue1 = new KeyValue(gameImg1.translateXProperty(), 650);
		KeyValue keyvalue2 = new KeyValue(gameImg2.translateXProperty(), 650);
		KeyValue keyvalue3 = new KeyValue(gameImg3.translateXProperty(), 650);

		KeyFrame keyframe1 = new KeyFrame(Duration.millis(a),e-> {
			if (a<b && a<c) {
				gameLabFirst.setText("네");
			}else if(a<b && a>c) {
				gameLabSecond.setText("네");
			}else if(a<c && a>b) {
				gameLabSecond.setText("네");
			}else if(a>c && a>b) {
				gameLabThird.setText("네");
			}
			if(gameRdo4.isSelected()) {
				if(a<b && a<c && b<c) {
					gameLabFirst.setText("네");
					gameLabSecond.setText("임");
					gameLabThird.setText("드");
					gameTxtArea.setText("적중! +3점");
					score+=3;
					gameLabMysc.setText(score+"");
				}else {
					gameTxtArea.setText("적중실패 -1점");	
					score-=1;
					gameLabMysc.setText(score+"");
				}
			}
			if(gameRdo5.isSelected()) {
				if(a<b && a<c && b>c){
					gameLabFirst.setText("네");
					gameLabSecond.setText("드");
					gameLabThird.setText("임");
					gameTxtArea.setText("적중! +3점");
					score+=3;
					gameLabMysc.setText(score+"");
				}else {
					gameTxtArea.setText("적중실패 -1점");	
					score-=1;
					gameLabMysc.setText(score+"");
				}
			}
			
			editToggleButton(false);
			handleRdoCheckAction();
			group.selectToggle(null);
			selectToggleButton();
			gameBtnStart.setDisable(true);
			pw.println("SCORE"+score);
			
			if((gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(-2+"")) || 
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(-1+"")) ||
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(0+"")) ||
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(1+"")) ||
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(2+"")) ||
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(3+"")) ||
					(gameLabMysc.getText().equals(5+"") && gameLabOthersc.getText().equals(4+""))) {
				pw.println("SCORE"+score);
				pw.println("RESUT");
			}else if((gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(-2+"")) || 
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(-1+"")) ||
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(0+"")) ||
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(1+"")) ||
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(2+"")) ||
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(3+"")) ||
					(gameLabMysc.getText().equals(-3+"") && gameLabOthersc.getText().equals(4+""))) {
				pw.println("SCORE"+score);
				pw.println("RESU2");
			}else {
				pw.println("RESTR");
			}
			if(gameLabMysc.getText().equals(gameLabOthersc.getText()) && (gameLabMysc.getText().equals(-3+"") || gameLabMysc.getText().equals(5+""))) {
				pw.println("NOGAM");
			}			
		}, keyvalue1);
		KeyFrame keyframe2 = new KeyFrame(Duration.millis(b),e-> {
			if (b<a && b<c) {
				gameLabFirst.setText("임");
			}else if(b<a && b>c) {
				gameLabSecond.setText("임");
			}else if(b<c && b>a) {
				gameLabSecond.setText("임");
			}else if(b>c && b>a) {
				gameLabThird.setText("임");
			}
		}, keyvalue2);
		KeyFrame keyframe3 = new KeyFrame(Duration.millis(c),e-> {
			if (c<b && c<a) {
				gameLabFirst.setText("드");
			}else if(c<b && c>a) {
				gameLabSecond.setText("드");
			}else if(c<a && c>b) {
				gameLabSecond.setText("드");
			}else if(c>a && c>b) {
				gameLabThird.setText("드");
			}
		}, keyvalue3);

		timeline.getKeyFrames().add(keyframe1);
		timeline.getKeyFrames().add(keyframe2);
		timeline.getKeyFrames().add(keyframe3);
		timeline.play();
	}
	
	private void handleRdoCheckAction() {
		
		if(gameRdo1.isSelected()) {
			if(gameLabFirst.getText()=="네") {
				gameTxtArea.appendText("적중! +1점");
				score+=1;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}else if(gameRdo2.isSelected()) {
			if(gameLabFirst.getText()=="임") {
				gameTxtArea.appendText("적중! +1점");
				score+=1;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");	
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}else if(gameRdo3.isSelected()) {
			if(gameLabFirst.getText()=="드") {
				gameTxtArea.appendText("적중! +1점");
				score+=1;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");
				score-=1;
				gameLabMysc.setText(score+"");
			}
		
		}else if(gameRdo6.isSelected()) {
			if((gameLabFirst.getText()=="드" && gameLabSecond.getText()=="네") || gameLabThird.getText()=="임") {
				gameTxtArea.appendText("적중! +3점");
				score+=3;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}else if(gameRdo7.isSelected()) {
			if(gameLabFirst.getText()=="드" && gameLabSecond.getText()=="임" && gameLabThird.getText()=="네") {
				gameTxtArea.appendText("적중! +3점");
				score+=3;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}else if(gameRdo8.isSelected()) {
			if((gameLabFirst.getText()=="임" && gameLabSecond.getText()=="네") || gameLabThird.getText()=="드") {
				gameTxtArea.appendText("적중! +3점");
				score+=3;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");	
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}else if(gameRdo9.isSelected()) {
			if(gameLabFirst.getText()=="임" && gameLabSecond.getText()=="드" && gameLabThird.getText()=="네") {
				gameTxtArea.appendText("적중! +3점");
				score+=3;
				gameLabMysc.setText(score+"");
			}else {
				gameTxtArea.appendText("적중실패 -1점");
				score-=1;
				gameLabMysc.setText(score+"");
			}
		}
	}
	
	private void editToggleButton(boolean bool) {
		gameRdo1.setDisable(bool);
		gameRdo2.setDisable(bool);
		gameRdo3.setDisable(bool);
		gameRdo4.setDisable(bool);
		gameRdo5.setDisable(bool);
		gameRdo6.setDisable(bool);
		gameRdo7.setDisable(bool);
		gameRdo8.setDisable(bool);
		gameRdo9.setDisable(bool);
	}
	
	private void selectToggleButton() {   
		
		gameRdo1.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo2.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo3.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo4.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo5.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo6.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo7.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo8.setOnMouseClicked(e-> {pw.println("PLAY");});
		gameRdo9.setOnMouseClicked(e-> {pw.println("PLAY");});
	
	}

	// 기타 알림창 "오류정보":값을 제대로 입력해주세요
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

}
