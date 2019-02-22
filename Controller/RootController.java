package Controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;


import javafx.stage.Stage;


public class RootController implements Initializable{
	public Stage primaryStage;
	@FXML private Button mainBtnJoin;
	@FXML private Button mainBtnLogin;
	@FXML private Button mainBtnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//1.회원가입 버튼을 눌렀을때 발생하는 이벤트함수
		mainBtnJoin.setOnAction(e-> handleBtnJoinAction());
		//2.로그인 버튼을 눌렀을때 발생하는 이벤트함수
		mainBtnLogin.setOnAction(e-> handleBtnLoginAction());
		
		mainBtnExit.setOnAction(e-> primaryStage.close());	
	}

	//1.회원가입 버튼을 눌렀을때 발생하는 이벤트함수
	private void handleBtnJoinAction() {
		try {
			Stage joinStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/join.fxml"));
			Parent root=loader.load();
			JoinController joinController=loader.getController();
			joinController.joinStage=joinStage;
			
			Scene scene=new Scene(root);
			joinStage.setScene(scene);
			joinStage.setTitle("회원가입");
			//primaryStage.close();
			joinStage.show();
		} catch (Exception e) {
			callAlert("화면 전환 오류: 화면 전환에 문제가 있습니다.");
		}
	}
	
	//2.로그인 버튼을 눌렀을때 발생하는 이벤트함수
	private void handleBtnLoginAction() {
		try {
			Stage loginStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/login.fxml"));
			Parent root=loader.load();
			LoginController loginController=loader.getController();
			loginController.loginStage=loginStage;
			
			Scene scene=new Scene(root);
			loginStage.setScene(scene);
			loginStage.setTitle("로그인");
			primaryStage.close();
			loginStage.show();
		} catch (Exception e) {
			callAlert("화면 전환 오류: 화면 전환에 문제가 있습니다.");
		}
	}

	//기타 알림창 "오류정보":값을 제대로 입력해주세요
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
