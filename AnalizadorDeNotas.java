import java.util.Random;
//Maria choquehuanca, Camila Valencia 

public class AnalizadorDeNotas {
    double[][] notas;  // matriz para guardar las notas de cada estudiante
    String[] evaluaciones; // array para nombres de evaluaciones
    int[] rut; // array para RUT de alumno
    int cantEstudiantes, cantEvaluaciones; // numero entero que guarda la cantidad de estudiante y el otro la cantidad de los alumnos
    double[] promediosEstudiantes; //array de tamaño cantidad de estudiantes
    double[] promedioEvaluaciones; //array de tamaño cantidad de evaluaciones

    // CONSTRUCTOR 1 
    public AnalizadorDeNotas(int estudiantes, int evaluaciones) {
        cantEstudiantes = estudiantes;   // se guarda la cantidad de estudiantes
        cantEvaluaciones = evaluaciones; // se guarda la cantidad de evaluaciones

        notas = new double[estudiantes][evaluaciones]; // se crea la matriz de notas
        this.evaluaciones = new String[evaluaciones];  // se crea el arreglo para nombres de evaluaciones
        rut = new int[estudiantes]; // se crea el arreglo para los RUT

        // nombres simples
        for (int j = 0; j < cantEvaluaciones; j++) {
            this.evaluaciones[j] = "Evaluacion " + (j + 1); // se asigna un nombre automatico a cada evaluacion
        }

        // datos aleatorios
        Random rnd = new Random(); // se crea un objeto Random para generar notas
        for (int i = 0; i < cantEstudiantes; i++) {
            rut[i] = 10000000 + i; // se asigna un RUT simple a cada estudiante
            for (int j = 0; j < cantEvaluaciones; j++) {
                notas[i][j] = 1 + rnd.nextDouble() * 6; // entre 1.0 y 7.0
            }
        }

        calcularPromediosInternos(); // llenar los atributos optimizados
    }

    //  CONSTRUCTOR 2
    public AnalizadorDeNotas(int estudiantes, int evaluaciones, String[] nombresEvaluaciones) {
        cantEstudiantes = estudiantes;   // se guarda la cantidad de estudiantes
        cantEvaluaciones = evaluaciones; // se guarda la cantidad de evaluaciones

        notas = new double[estudiantes][evaluaciones]; // se crea la matriz de notas
        this.evaluaciones = new String[evaluaciones];  // se crea el arreglo para nombres de evaluaciones
        rut = new int[estudiantes]; // se crea el arreglo para los RUT

        // copia nombres entregados
        for(int j = 0; j < cantEvaluaciones; j++) {
            this.evaluaciones[j] = nombresEvaluaciones[j]; // se copian los nombres entregados en el arreglo
        }

        // datos aleatorios
        Random rnd = new Random(); // se crea un objeto Random
        for (int i = 0; i < cantEstudiantes; i++) {
            rut[i] = 10000000 + i; // se asigna un RUT simple
            for (int j = 0; j < cantEvaluaciones; j++) {
                notas[i][j] = 1 + rnd.nextDouble() * 6; // entre 1.0 y 7.0
            }
        }

        calcularPromediosInternos(); // llenar los atributos optimizados
    }

    public String[] getEvaluaciones(){
        return evaluaciones; // retorna el arreglo con los nombres de evaluaciones
    }

    //  METODOS BASICOS

    public double calcularPromedioEstudiante(int numEstudiante) {
        double suma = 0; // se incializa la << suma > > en 0
        for (int j = 0; j < cantEvaluaciones; j++) {
            // recorre arreglo y va sumando las notas del estudiante
            suma += notas[numEstudiante][j];
        }
        return suma / cantEvaluaciones; // operacion para obtener promedio y retornar su valor
    }

    public double calcularPromedioEvaluacion(int index) {
        double suma = 0; // inicializa << suma > > en 0
        for (int i = 0; i < cantEstudiantes; i++) { // recorre arreglo sumando las notas de una evaluacion
            suma += notas[i][index];
        }
        return suma / cantEstudiantes;
    } // operacion para obtener promedio segun la suma , luego retorna el valor

    public double calcularVarianzaEvaluacion(int index) { // calcula la media de la evaluacion
        double media = calcularPromedioEvaluacion(index); // obtiene el promedio de esa evaluación
        double suma = 0; // inicializa << suma > > en 0
        for (int i = 0; i < cantEstudiantes; i++) { // recorre y suma diferencias al cuadrado de cada nota y la media
            suma += Math.pow(notas[i][index] - media, 2);
        }
        return suma / cantEstudiantes;
    } // retorna la varianza operando por la cantidad de estudiantes

    public double[] calcularPromediosEstudiantes() {
        double[] res = new double[cantEstudiantes]; // arreglo
        for (int i = 0; i < cantEstudiantes; i++) { // arreglo res , guarda el promedio de cada estudiante segun posicion
            res[i] = calcularPromedioEstudiante(i);
        }
        return res; // retorna el arreglo completo con el promedio de cada estudiante
    }

    public double[] calcularVarianzaEstudiantes() {
        double[] res = new double[cantEstudiantes]; // arreglo res para guardarlas varianzas
        for (int i = 0; i < cantEstudiantes; i++) { // calcula la media de cada estudiante
            double media = calcularPromedioEstudiante(i);
            double suma = 0; // << suma > > se inicializa en 0
            for (int j = 0; j < cantEvaluaciones; j++) { // segundo for , calcula la diferencia al cuadrado entre cada nota con la media
                suma += Math.pow(notas[i][j] - media, 2);
            }
            // se dividide por la cantidad de evaluaciones , guarda el valor en el arreglo
            res[i] = suma / cantEvaluaciones;
            // se retorna el arreglo completo de la varianza de cada alumno
        }
        return res;
    }

    //  METODOS NUEVOS

    // Promedio de un subconjunto de evaluaciones por cada estudiante
    public double[] calcularPromedioEvaluaciones(String[] nombresEvaluaciones) {
        double[] res = new double[cantEstudiantes]; // arreglo para guardar resultados
        for (int i = 0; i < cantEstudiantes; i++) {
            double suma = 0; // acumulador de notas inicializado en 0
            int contador = 0; // cuenta cuántas evaluaciones del subconjunto se encontraron
            for (int j = 0; j < cantEvaluaciones; j++) {
                for (String nombresEvaluacione : nombresEvaluaciones) {
                    if (evaluaciones[j].equals(nombresEvaluacione)) { // compara si el nombre coincide
                        suma += notas[i][j]; // si coincide suma esa nota
                        contador++; // aumenta el contador
                    }
                }
            }
            if (contador > 0) {
                res[i] = suma / contador; // calcula promedio del subconjunto
            } else {
                res[i] = 0; // si no hay evaluaciones, se asigna 0
            }
        }
        return res; // retorna el arreglo de promedios por estudiante
    }

    // Retorna el RUT del estudiante con la nota maxima en esa evaluacion
    public String encontrarMaximo(int index) {
        double max = notas[0][index]; // inicializa el maximo con la primera nota
        int pos = 0; // posición del estudiante con la nota maxima
        for (int i = 1; i < cantEstudiantes; i++) {
            if (notas[i][index] > max) { // si se encuentra una nota mayor
                max = notas[i][index]; // actualiza el maximo
                pos = i; // guarda la posicion
            }
        }
        return String.valueOf(rut[pos]); // retorna el RUT como String
    }

    // OPTIMIZACION

    private void calcularPromediosInternos() {
        promediosEstudiantes = new double[cantEstudiantes]; // arreglo para almacenar promedios de estudiantes
        for (int i = 0; i < cantEstudiantes; i++) {
            promediosEstudiantes[i] = calcularPromedioEstudiante(i); //  recorre llenando el arreglo con el promedio de cada alumno
        }

        promedioEvaluaciones = new double[cantEvaluaciones]; // arreglo para almacenar promedios de evaluaciones
        for (int j = 0; j < cantEvaluaciones; j++) {
            promedioEvaluaciones[j] = calcularPromedioEvaluacion(j); // recorre llenando el arreglo con el promedio de cada evaluacion
        }
    }

    public double[] calcularPromediosEstudiantesOptimizado() {
        return promediosEstudiantes; // retorna los promedios precalculados de estudiantes
    }

    public double[] calcularPromedioEvaluacionesOptimizado() {
        return promedioEvaluaciones; // retorna los promedios precalculados de evaluaciones
    }

}




