package cielo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bandera
{
	List<List<Integer>> trozosPorEscuela;
	int largoCarretel;
	
	public Bandera (File archivo) throws Exception
	{
		Scanner sc = new Scanner(archivo);
		
		largoCarretel = sc.nextInt();
		int cantEscuelas = sc.nextInt();
		trozosPorEscuela = new ArrayList<List<Integer>>(cantEscuelas);
		
		for (int i = 0; i < cantEscuelas; i++)
		{
			int largoRetazo = sc.nextInt();
			trozosPorEscuela.add(new ArrayList<Integer>()); 
			trozosPorEscuela.get(i).add(largoRetazo);
			largoCarretel -= largoRetazo;
		}
		
		sc.close();
		
		if (largoCarretel < 0)
			throw new Exception("No hay carretel ni siquiera para cortar los retazos iniciales");
	}
	
	public void recortarRetazos ()
	{
		boolean pudeCortar = true;
		
		while (pudeCortar)
		{
			// si en una vuelta no pudo cortar para ninguna de las escuelas esto va a 
			// quedar en falso y va a salir del bucle.
			pudeCortar = false;
			
			for (int i = 0; i < trozosPorEscuela.size(); i++)
			{
				List<Integer> lista = trozosPorEscuela.get(i);
				int longCortar = lista.get(lista.size() - 1) + sumarCifras(lista.get(lista.size() - 1));
				
				// pregunto si tengo carretel para cortar
				if (largoCarretel >= longCortar)
				{
					lista.add(longCortar);
					largoCarretel -= longCortar;
					pudeCortar = true;
				}
			}
		}
	}
	
	public int sumarCifras (int num)
	{
		int suma = 0;
		char[] cifras = String.valueOf(num).toCharArray();
		
		for (int i = 0; i < cifras.length; i++)
		{
			suma += Character.getNumericValue(cifras[i]);
		}
		
		return suma;
	}
	
	public int longitudBandera (List<Integer> bandera)
	{
		int largo = 0;
		
		for (int i = 0; i < bandera.size(); i++)
			largo += bandera.get(i);
		
		return largo;
	}
	
	public int buscarSubsecuencia (List<Integer> bandera1, List<Integer> bandera2)
	{
		for (int i = 0; i < bandera1.size(); i++)
		{
			int index = bandera2.indexOf(bandera1.get(i));
			
			if(index != -1)
			{
				int contador = 1;
				int j = 0;
				
				while(i + j < bandera1.size() && index + j < bandera2.size())
				{
					contador++;
					j++;
				}
				return contador;
			}
					
		}
		return 0;
	}
	
	public void mostrarResultados (File archivo) throws Exception
	{
		PrintWriter out = new PrintWriter(new FileWriter(archivo));
		
		int cantBanderas = trozosPorEscuela.size();
		// busco la bandera más larga
		int numBandera = 0;
		int maxLongitud = 0;
		
		for (int i = 0; i < cantBanderas; i++)
		{
			int largoBandera = longitudBandera(trozosPorEscuela.get(i));
			
			if (largoBandera > maxLongitud)
			{
				maxLongitud = largoBandera;
				numBandera = i;
			}
		}
		
		// comienzo a buscar subsecuencias
		int maxSubsecuencia = 0;
		int maxBand1 = 0;
		int maxBand2 = 0;
		
		if (cantBanderas < 2)
			throw new Exception("No hay banderas para comparar!");
		
		for (int i = 0; i < cantBanderas ; i++)
		{
			for (int j = i + 1; j < cantBanderas ; j++)
			{
				int subsecuencia = buscarSubsecuencia(trozosPorEscuela.get(i), trozosPorEscuela.get(j));
				
				if (subsecuencia > maxSubsecuencia)
				{
					maxSubsecuencia = subsecuencia;
					maxBand1 = i + 1;
					maxBand2 = j + 1;
				}
			}
		}
		
		out.println((numBandera + 1) + " " + maxLongitud);
		out.println(trozosPorEscuela.get(numBandera).size());
		out.println(largoCarretel);
		out.println(maxSubsecuencia + " " + maxBand1 + " " + maxBand2);
		
		out.close();
	}

}
