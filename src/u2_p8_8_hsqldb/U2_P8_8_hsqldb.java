/*
 * Basándote en el ejercicio 6 rectifica lo que haga falta para hacer lo mismo con las base de
 * HSQLDB.
 */

//ANOTACIONES ÚTILES
/*
    1. Situarse en el directorio de hsqldb: cd c:/hsqldb-2.4.1/hsqldb

    2. Iniciar la herramienta de texto: java -jar lib/sqltool.jar

    3. Acceder a la BBDD/Crear BBDD: \j SA jdbc:hsqldb:C:/hsqldb-2.4.1/hsqldb/hsqldb/ejemplo1

    4. Hacer consulta: SELECT * FROM PROFESORES; (si se realizan inserciones, borrados o actualizaciones hay que realizar un "COMMIT;" justo después para conservar los cambios)

    5. Apagar BBDD: SHUTDOWN;

    6. Quitar herramienta texto: \q
*/
package u2_p8_8_hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author mdfda
 */
public class U2_P8_8_hsqldb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombre = args[0], apellidos = args[1] + " " + args[2], email = args[3], dept_no = args[4];
        Float salario = Float.parseFloat(args[5]);
        Date fecha_antes = new Date();
        java.sql.Date fecha_alta = new java.sql.Date(fecha_antes.getTime());
        
        //Expresión regular para verificar que la dirección de email est abien formada
        Pattern patron = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        Boolean emailValido = patron.matcher(email).matches();
        
        if(salario <= 0.0 || !emailValido){
            if(salario <= 0.0){
                System.out.println("El salario no puede ser menor de 0: " + salario);
            }
            
            if(!emailValido){
                System.out.println("El email esta mal formado: " + email);
            }
            
            System.out.println("El registro no ha podido introducirse");
        }else{
            
            try{
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                
                Connection con = DriverManager.getConnection("jdbc:hsqldb:C://hsqldb-2.4.1//hsqldb//hsqldb//ejemplo1", "SA", "");
                
                Statement s = con.createStatement();
                
                s.executeUpdate("INSERT INTO PROFESORES VALUES((SELECT MAX(NRM) FROM PROFESORES) + 1, '" + nombre + "' , '" + apellidos + "', '" + email + "', '" + fecha_alta + "', " + dept_no + ", " + salario + ")");
                
                s.close();
                con.close();
                
                System.out.println("El registro ha sido introducido correctamente");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(U2_P8_8_hsqldb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                System.out.println("Código de error: " + ex.getErrorCode());
                System.out.println("Mensaje de error: " + ex.getMessage());
                System.out.println("Estado SQL: " + ex.getSQLState());
            }
            
        }
    }
    
}
