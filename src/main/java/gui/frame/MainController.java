package gui.frame;

import ftp.FTPSiteContext;
import gui.action.FtpAction;
import gui.action.LocalAction;
import gui.action.impl.FtpActionImpl;
import gui.action.impl.LocalActionImpl;
import gui.uicomponent.TransferComponent;
import gui.vo.FileInfo;
import gui.vo.TransferTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * create by zhong
 * gui.frame
 * Date 2019/5/12
 */
public class MainController {
    public TextField userTxtField;
    public PasswordField pwdTxtField;
    public TextField hostTxtField;
    public ListView remoteDirectory;
    public ListView localDirectory;
    public TextField portTxtField;
    public ScrollPane uploadPane;
    public ScrollPane downloadPane;
    public VBox uploadBox;
    public VBox downloadBox;

    private FtpAction action;
    private LocalAction localAction;

    private FTPSiteContext context;

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    public void init() {
        action = new FtpActionImpl();
        localAction = new LocalActionImpl();
        context = action.getContext();
        context.setLocalDirectory("D:\\local");
        refleshLocal(null);
        //TODO:init

        remoteDirectory.setOnMouseClicked(value -> {
            if (value.getClickCount() != 2) {
                return;
            }
            FileInfo fileInfo = (FileInfo) remoteDirectory.getSelectionModel().getSelectedItem();
            if (!fileInfo.getAuthority().startsWith("d")) {
                return;
            }
            action.changeWorkDir(context.getRemoteDirectory() + "/" + fileInfo.getName());
            refleshRemote(null);
        });
    }

    public void connect(MouseEvent mouseEvent) {
        String host = hostTxtField.getText();
        int port = Integer.parseInt(portTxtField.getText());
        String user = userTxtField.getText();
        boolean login = false;
        String pwd = "";
        if (user == null || user.equals("")) {
            login = action.anonymityLogin(host, port);
        } else {
            pwd = pwdTxtField.getText();
            login = action.login(host, port, user, pwd);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!login) {
            log.error(host + "" + port + "" + user + "" + pwd);
            alert.setContentText("登陆失败");
        } else {
            alert.setContentText("登陆成功");
        }
        alert.showAndWait();
        refleshRemote(null);
    }


    public void refleshLocal(MouseEvent actionEvent) {
        List<FileInfo> fileInfoList = localAction.list(context.getLocalDirectory());
        localDirectory.setItems(FXCollections.observableArrayList(fileInfoList));
    }

    public void refleshRemote(MouseEvent actionEvent) {
        List<FileInfo> fileInfoList = action.list();
        if (fileInfoList == null) return;
        remoteDirectory.setItems(FXCollections.observableArrayList(fileInfoList));
    }

    public FtpAction getAction() {
        return action;
    }

    public void setAction(FtpAction action) {
        this.action = action;
    }

    public LocalAction getLocalAction() {
        return localAction;
    }

    public void setLocalAction(LocalAction localAction) {
        this.localAction = localAction;
    }

    public FTPSiteContext getContext() {
        return context;
    }

    public void setContext(FTPSiteContext context) {
        this.context = context;
    }

    public void upload(MouseEvent mouseEvent) {
        if (!loginCheck()) return;
        ObservableList files = localDirectory.getSelectionModel().getSelectedItems();
        for (Object o : files
        ) {
            FileInfo fileInfo = (FileInfo) o;
            TransferTask task = action.upload(fileInfo.getName());
            if (task == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("上传失败：" + action.getContext().getLastResponse());
                alert.showAndWait();
            }
            TransferComponent component = new TransferComponent(task);
            component.setAction(action);
            component.setLocalAction(localAction);
            uploadBox.getChildren().add(component);
        }
    }

    public void download(MouseEvent mouseEvent) {
        if (!loginCheck()) return;
        ObservableList files = remoteDirectory.getSelectionModel().getSelectedItems();
        for (Object o : files
        ) {
            FileInfo fileInfo = (FileInfo) o;
            TransferTask task = action.download(context.getRemoteDirectory() + "/" + fileInfo.getName());
            TransferComponent component = new TransferComponent(task);
            component.setAction(action);
            component.setLocalAction(localAction);
            downloadBox.getChildren().add(component);
        }
    }

    public void chooseLocalDir(ActionEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        context.setLocalDirectory(file.getAbsolutePath());
        log.info("改变本地目录为：" + file.getAbsolutePath());
        refleshLocal(null);
    }

    private boolean loginCheck() {
        if (context == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("请先登录");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
