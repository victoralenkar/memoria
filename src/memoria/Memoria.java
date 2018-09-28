package memoria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Memoria {

	/**
	 * 
	 * java -jar memoria.jar {arquivo entrada} -t [(D)ireto,T,(C)onjunto] -s {tamanho cache} -c {qtde conjuntos} -p [RANDOM,FIFO,LRU,LFU]
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String arquivo = args[0];
		String tipoMapeamento = args[2];
		String tamanhoCache = args[4];
		String qtdeConjuntos = null;
		String politica = null;
		if (args.length > 5) {
			qtdeConjuntos = args[5].equals("-c") ? args[6] : null;
			politica = args[5].equals("-p") ? args[6] : null;
			if (args.length > 7) {
				politica = args[7].equals("-p") ? args[8] : null;
			}
		}
		FileReader fileReader = new FileReader(arquivo);
		BufferedReader br = new BufferedReader(fileReader);
		String linha = "";
		MapeamentoDireto md = null;
		MapeamentoAssociativo ma = null;
		MapeamentoAssociativoConjunto mac = null;
		String tipo = "";
		if (tipoMapeamento.equals("D")) {
			tipo = "Mapeamento Direto";
			md = new MapeamentoDireto(Integer.valueOf(tamanhoCache));
		} else if (tipoMapeamento.equals("T")) {
			tipo = "Mapeamento Associativo com " + PoliticaInsercaoEnum.valueOf(politica);
			ma = new MapeamentoAssociativo(Integer.valueOf(tamanhoCache), PoliticaInsercaoEnum.valueOf(politica));
		} else if (tipoMapeamento.equals("C")) {
			tipo = "Mapeamento Associativo por Conjunto com " + PoliticaInsercaoEnum.valueOf(politica) + " e "
					+ qtdeConjuntos + " conjuntos";
			mac = new MapeamentoAssociativoConjunto(Integer.valueOf(tamanhoCache), Integer.valueOf(qtdeConjuntos),
					PoliticaInsercaoEnum.valueOf(politica));
		}
		double hits = 0;
		double miss = 0;
		while ((linha = br.readLine()) != null) {
			int entrada = Integer.valueOf(linha);
			if (md != null) {
				if (md.isHit(entrada)) {
					hits++;
				} else {
					md.inserir(entrada);
					miss++;
				}
				System.out.println(md.toString().replace("null", ""));
			} else if (ma != null) {
				if (ma.isHit(entrada)) {
					hits++;
				} else {
					ma.inserir(entrada);
					miss++;
				}				
				System.out.println(ma.toString().replace("null", ""));
			} else if (mac != null) {
				if (mac.isHit(entrada)) {
					hits++;
				} else {
					mac.inserir(entrada);
					miss++;
				}				
				System.out.println(mac.toString().replace("null", ""));
			}

		}
		double hitsPercent = hits / (hits + miss);
		double missPercent = miss / (hits + miss);
		hitsPercent = Math.floor(hitsPercent * 10000) / 100;
		missPercent = Math.floor(missPercent * 10000) / 100;
		System.out.println();
		System.out.println(tipo + " Cache hits: " + hitsPercent + "%");
		System.out.println(tipo + " Cache miss: " + missPercent + "%");
		br.close();
		fileReader.close();

	}

}
