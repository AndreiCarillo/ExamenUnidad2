package sample;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;
public class Cronometro extends Task<String> {
        private TextField segundos, minutos;
        public Cronometro(TextField minutos, TextField segundos) {
            super();
            this.segundos = segundos;
            this.minutos  = minutos;
        }

        @Override
        protected String call() throws Exception {
            int Min = 0;
            int Seg = 0;
            try {
                for (; ; ) {
                    if (Seg != 59)
                        Seg++;
                    else {
                        if (Min != 59) {
                            Seg = 0;
                            Min++;
                        }
                    }
                    segundos.setText(Integer.toString(Seg));
                    minutos.setText(Integer.toString(Min));
                    System.out.println( Min + ":" + Seg);

                    Thread.sleep(999);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            return "Tarea terminada";
        }

        @Override
        public void succeeded()
        {
            super.succeeded();
            updateMessage("La tarea ah finalizado.");
        }
}
