package Controller;

import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML private TextField loginTxtName;
	@FXML private PasswordField loginTxtPassword;
	@FXML private Button loginBtnRegist;
	@FXML private Button loginBtnCancel;
	public static String nickname;
	public static String password;
	

	public Stage loginStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBtnRegist.setOnAction(e-> handleBtnRegistAction());
		loginBtnCancel.setOnAction(e-> loginStage.close());
	}

	private void handleBtnRegistAction() {

		if(MemberDAO.Check(loginTxtName.getText(),loginTxtPassword.getText())==-1) {
			GameController.callAlert("로그인 실패: 닉네임 또는 비밀번호 오류입니다.");
			loginTxtName.clear();
			loginTxtPassword.clear();
			return;
		};
		GameController.callAlert("로그인 성공: 환영합니다.");
		
		nickname = loginTxtName.getText();
		password= loginTxtPassword.getText();
		try {
			Stage linkStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/link.fxml"));
			Parent root = loader.load();

			Button linkBtnStart = (Button) root.lookup("#linkBtnStart");
			Button linkBtnRegister = (Button) root.lookup("#linkBtnRegister");

			Scene scene = new Scene(root);
			linkStage.setScene(scene);
			linkStage.setTitle("대기방");
			loginStage.close();
			linkStage.show();

			linkBtnStart.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						Stage gameStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/game.fxml"));
						Parent root = loader.load();
						GameController gameController = loader.getController();
						gameController.gameStage = gameStage;

						Scene scene = new Scene(root);
						gameStage.setScene(scene);
						gameStage.setTitle("달팽이 게임");
						loginStage.close();
						gameStage.show();

					} catch (Exception e) {
						GameController.callAlert("화면 전환 오류: 화면 전환에 문제가 있습니다.");
					}
				}
			});
			
			linkBtnRegister.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						Stage howgameStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/howgame.fxml"));
						Parent root = loader.load();
						HowController howController = loader.getController();
						howController.howgameStage = howgameStage;

						Scene scene = new Scene(root);
						howgameStage.setScene(scene);
						howgameStage.setTitle("달팽이 게임 방법");
						howgameStage.show();

					} catch (Exception e) {
						GameController.callAlert("화면 전환 오류: 화면 전환에 문제가 있습니다.");
					}
				}
			});

		} catch (Exception e) {
			GameController.callAlert("화면 전환 오류: 대기화면 전환에 문제가 있습니다.");
		}
	}
}
