package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            BorderPane root = new BorderPane();
            //
            GridPane pnlPrincipal = new GridPane();
            pnlPrincipal.setHgap(10);
            pnlPrincipal.setVgap(10);
            pnlPrincipal.setPadding(new Insets(0, 11, 0, 11));

            //Valor 1
            Label Label1 = new Label ("Primer Valor:");
            pnlPrincipal.add(Label1, 1, 3);
            TextField Val1 = new TextField();
            Val1.setText(Integer.toString((int)(Math.random()*50)));
            Val1.setEditable(false);
            pnlPrincipal.add(Val1, 1, 4);

            //Valor 2
            Label Label2 = new Label("Segundo Valor:");
            pnlPrincipal.add(Label2, 9, 3);
            TextField Val2 = new TextField();
            Val2.setText(Integer.toString((int)(Math.random()*50)));
            Val2.setEditable(false);
            pnlPrincipal.add(Val2, 9, 4);

            //resultado
            Label LabelResul = new Label("Resultado");
            pnlPrincipal.add(LabelResul, 0, 6);
            TextField resultado = new TextField();
            pnlPrincipal.add(resultado, 1,6);

            //Simbolo de multiplicar
            Label simbolo = new Label("X");
            pnlPrincipal.add(simbolo, 5, 4);

            //Botón aceptar
            Button comprobar = new Button("Probar");
            pnlPrincipal.add(comprobar, 9, 10);

            VBox tiempo = new VBox();
            tiempo.setPadding(new Insets(5, 0, 5, 0));

            Label Labelmin = new Label("Minutos");
            TextField minutos = new TextField();
            minutos.setEditable(false);

            Label Labelseg = new Label("Segundos");
            TextField segundos = new TextField();
            segundos.setEditable(false);

            Label aviso = new Label("Mal");
            aviso.setVisible(false);
            tiempo.getChildren().addAll(Labelmin, minutos, Labelseg, segundos, aviso);

            root.setRight(tiempo);
            Cronometro hilo1 = new Cronometro(minutos, segundos);
            Thread hilo2 = new Thread(hilo1);
            hilo2.setDaemon(true);
            hilo2.start();
            hilo1.isDone();
            class evento implements EventHandler<MouseEvent> {
                Thread hilo1;

                public evento(Thread hilo1) {
                    this.hilo1 = hilo1;
                }

                @Override
                public void handle(MouseEvent actionEvent) {
                    try {
                        if (resultado.getText() == null || resultado.getText() == "") {
                            aviso.setVisible(true);
                        }
                        if (Integer.parseInt(resultado.getText()) == (Integer.parseInt(Val1.getText()) * Integer.parseInt(Val2.getText()))) {
                            aviso.setVisible(false);
                            hilo1.stop();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Información del tiempo");
                            alert.setContentText("Le tomo:" + "\n" + "Minutos: " + minutos.getText() + "\n" + "Segundos: " + segundos.getText());
                            alert.showAndWait();

                        } else {
                            aviso.setVisible(true);
                        }
                    } catch (Exception e) {
                        System.out.println("e = " + e);
                    }
                }
            }
            Label pregunta = new Label("¿Cuánto da la multiplicación?");
            root.setTop(pregunta);
            root.setCenter(pnlPrincipal);
            comprobar.setOnMouseClicked(new evento(hilo2));
            primaryStage.setTitle("Examen Concurrencia");
            primaryStage.setScene(new Scene(root, 450, 300));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}