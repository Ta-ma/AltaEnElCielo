package cielo;

import java.io.File;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		File dir = new File(".//IN//");
		
		for (File archivo : dir.listFiles())
		{
			Bandera bandera = new Bandera(archivo);
			bandera.recortarRetazos();
			bandera.mostrarResultados(new File(".//OUT//" + archivo.getName().replaceAll("in", "out")));
		}
	}
}
