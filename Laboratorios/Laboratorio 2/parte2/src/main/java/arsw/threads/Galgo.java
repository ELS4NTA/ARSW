package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class Galgo extends Thread {
    private int paso;
    private Carril carril;
    RegistroLlegada regl;
    private boolean stop;

    public Galgo(Carril carril, String name, RegistroLlegada reg) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
        this.stop = true;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {
            synchronized (regl) {
                while (!stop) {
                    regl.wait();
                }
            }
            Thread.sleep(100);
            carril.setPasoOn(paso++);
            carril.displayPasos(paso);
            if (paso == carril.size()) {
                synchronized (regl) {
                    carril.finish();
                    int ubicacion = regl.getUltimaPosicionAlcanzada();
                    regl.setUltimaPosicionAlcanzada(ubicacion + 1);
                    System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
                    if (ubicacion == 1) {
                        regl.setGanador(this.getName());
                    }
                }
            }
        }
    }

    @Override
    public void run() {

        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void continueExec(boolean execute) {
        stop = execute;
    }

}
