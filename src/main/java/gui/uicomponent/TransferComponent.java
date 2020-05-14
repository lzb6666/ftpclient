package gui.uicomponent;

import gui.action.FtpAction;
import gui.action.LocalAction;
import gui.vo.TransferTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * create by zhong
 * gui.uicomponent
 * Date 2019/5/25
 * logTips:private static final Logger log=LoggerFactory.getLogger(${Class}.class)
 */
public class TransferComponent extends HBox {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button ctlBtn;
    @FXML
    private Label fileNameLable;
    private TransferTask transferTask;

    private Task task;

    private FtpAction action;
    private LocalAction localAction;

    public TransferComponent(TransferTask transferTask) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "fxml/transferComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        HBox box = fxmlLoader.getRoot();
        /*progressBar= (ProgressBar) box.getChildren().get(0);
        ctlBtn= (Button) box.getChildren().get(1);*/
        this.transferTask = transferTask;
        progressBar.setProgress(((double) transferTask.getCurSize()) / transferTask.getTotalSize());
        task = new MyTask();
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        fileNameLable.setText(transferTask.getAbPath());
    }

    @FXML
    private void ctl(MouseEvent actionEvent) {
        if (ctlBtn.getText().equals("暂停")) {
            action.pause();
            task.cancel();
            ctlBtn.setText("继续");
        } else if (ctlBtn.getText().equals("继续")) {
            action.continueTask(transferTask);
            progressBar.progressProperty().unbind();
            task = new MyTask();
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
            ctlBtn.setText("暂停");
        }
    }

    class MyTask extends Task {

        MyTask() {
            updateProgress(transferTask.getCurSize(), transferTask.getTotalSize());
        }

        @Override
        protected Object call() throws Exception {
            while (transferTask.getCurSize() != transferTask.getTotalSize()) {
                System.out.println(transferTask.getCurSize() + " " + transferTask.getTotalSize());
                updateProgress(transferTask.getCurSize(), transferTask.getTotalSize());
                Thread.sleep(1000);
            }
            updateProgress(transferTask.getCurSize(), transferTask.getTotalSize());
            Platform.runLater(() -> {
                ctlBtn.setText("已完成");
            });
            return true;
        }
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
}
