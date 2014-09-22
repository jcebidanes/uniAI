package com.cebidanes.carregadores;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cebidanes.entidades.Dado;

public class CarregadorDados {

	Integer i = 0;
	EntityManager em;

	public CarregadorDados(EntityManager em) {
		this.em = em;
	}

	public void carregaArquivoDado(String path) throws NoResultException {

		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("[\\t\\||\n]");

			while (arquivo.hasNext()) {
				String ip = arquivo.next();
				String port = arquivo.next();
				String domain = arquivo.next();
				String type = arquivo.next();

				em.persist(new Dado(ip, port, domain, type));

				if (i++ % 20 == 0) {
					em.flush();
					em.clear();
				}
			}
			scanner.close();
			fr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
