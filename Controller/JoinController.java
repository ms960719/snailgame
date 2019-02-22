package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import Model.Member;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinController implements Initializable {
	@FXML private TextField joinTxtName;
	@FXML private PasswordField joinTxtPassword;
	@FXML private PasswordField joinTxtRepassword;
	@FXML private Button joinBtnOverlab;
	@FXML private Button joinBtnJoin;
	@FXML private Button joinBtnCancel;
	public Stage joinStage;
	public static String name;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		joinBtnJoin.setOnAction(e -> handleBtnJoinAction());
		joinBtnOverlab.setOnAction(e-> handleBtnOverLabAction());
		joinBtnCancel.setOnAction(e-> joinStage.close());
	}

	
	private void handleBtnJoinAction() {
		if (!joinTxtPassword.getText().equals(joinTxtRepassword.getText())) {
			GameController.callAlert("비밀번호 확인 요망:입력하신 비밀번호가 서로 다릅니다.");
			joinTxtPassword.clear();
			joinTxtRepassword.clear();
		}else {
			Member member = new Member(joinTxtName.getText(), joinTxtPassword.getText(), 0);
			MemberDAO.insertMemberData(member);
			
			GameController.callAlert("환영합니다:회원가입 성공");
			joinStage.close();
			
		}
	}
	
	private void handleBtnOverLabAction() {
		name=joinTxtName.getText();
		if(MemberDAO.getMemberData(name)!=0) {
			GameController.callAlert("실패:닉네임 중복입니다.");
		}else {
			GameController.callAlert("성공:사용 가능한 닉네임입니다.");
		}
		
	}


}
