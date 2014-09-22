package com.cebidanes.base;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cebidanes.carregadores.CarregadorDados;

public class Main {

public static void main(String[] args) throws Exception {
		
		File localBase;
		File diretorioCasa = new File("C:/Users/jhonnatan.santos/git/UnicariocaAI/ImportadorDadosAi");
		File diretorioExterno = new File("C:/Users/jhonnatan.santos/git/ImportadorDadosAi");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("unicariocaPU");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			if(diretorioExterno.exists()){
				localBase = diretorioExterno;
			}else if(diretorioCasa.exists()){
				localBase = diretorioCasa;
			}else{
				throw new FileSystemNotFoundException("Local Base não encontrado");
			}
			
			em.getTransaction().begin();
			CarregadorDados dados  =  new CarregadorDados(em);
			File[] arquivosDados = carregaArquivos(localBase+"/arquivos/dados");
			
			for(File f: arquivosDados){
				dados.carregaArquivoDado(f.getPath());
			}
			em.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	public static File[] carregaArquivos(String path) {
		File diretorio = new File(path);  
		return diretorio.listFiles();  
	}
	
}
