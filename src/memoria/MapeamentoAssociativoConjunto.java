package memoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapeamentoAssociativoConjunto extends Cache {

	private int[] ponteiro;
	private int tamanhoConjunto;
	private int qtdeConjunto;
	private int[][] acessos;

	public MapeamentoAssociativoConjunto(int tamanho, int qtdeConjuntos, PoliticaInsercaoEnum politica) {
		super.cache = new Integer[tamanho];
		this.tamanhoConjunto = tamanho / qtdeConjuntos;
		this.qtdeConjunto = qtdeConjuntos;
		this.ponteiro = new int[qtdeConjuntos];
		for (int i = 0; i < qtdeConjuntos; i++) {
			this.ponteiro[i] = 0;
		}
		super.politica = politica;
		if (super.politica == PoliticaInsercaoEnum.LFU) {
			acessos = new int[qtdeConjuntos][this.tamanhoConjunto];
		}
	}

	public Integer[] getConjunto(int pagina) {
		Integer[] conjunto = new Integer[tamanhoConjunto];
		int indiceConjunto = pagina % this.qtdeConjunto;
		for (int i = 0; i < tamanhoConjunto; i++) {
			conjunto[i] = cache[indiceConjunto * tamanhoConjunto + i];
		}
		return conjunto;
	}

	public boolean isHit(int pagina) {
		boolean hit = false;
		int indiceConjunto = pagina % this.qtdeConjunto;
		Integer[] conjunto = getConjunto(pagina);
		for (int i = 0; i < tamanhoConjunto; i++) {
			if (conjunto[i] != null && conjunto[i].intValue() == pagina) {
				if (super.politica == PoliticaInsercaoEnum.LFU) {
					acessos[indiceConjunto][i]++;
				}
				hit = true;
				break;
			}
		}
		if (this.politica == PoliticaInsercaoEnum.LRU && hit) {
			List<Integer> temp = new LinkedList<Integer>(Arrays.asList(conjunto));
			temp.remove(temp.indexOf(pagina));
			List<Integer> temp2 = new ArrayList<>();
			temp2.add(pagina);
			temp2.addAll(temp);
			temp2.toArray(conjunto);
			this.inserirConjunto(indiceConjunto, conjunto);
		}
		return hit;
	}

	private void inserirConjunto(int indiceConjunto, Integer[] conjunto) {
		for (int i = 0; i < tamanhoConjunto; i++) {
			cache[indiceConjunto * tamanhoConjunto + i] = conjunto[i];
		}
	}

	public void inserir(int pagina) {
		if (super.politica == PoliticaInsercaoEnum.FIFO) {
			inserirFIFO(pagina);
		} else if (super.politica == PoliticaInsercaoEnum.LFU) {
			inserirLFU(pagina);
		} else if (super.politica == PoliticaInsercaoEnum.LRU) {
			inserirLRU(pagina);
		} else if (super.politica == PoliticaInsercaoEnum.RANDOM) {
			inserirRANDOM(pagina);
		}
	}

	private void inserirRANDOM(int pagina) {
		int indiceConjunto = pagina % this.qtdeConjunto;
		Integer[] conjunto = getConjunto(pagina);
		conjunto[new Random().nextInt(this.tamanhoConjunto)] = pagina;
		this.inserirConjunto(indiceConjunto, conjunto);
	}

	private void inserirLRU(int pagina) {
		int indiceConjunto = pagina % this.qtdeConjunto;
		Integer conjunto[] = this.getConjunto(pagina);
		List<Integer> temp = Arrays.asList(Arrays.copyOf(conjunto, conjunto.length - 1));
		List<Integer> temp2 = new ArrayList<>();
		temp2.add(pagina);
		temp2.addAll(temp);
		temp2.toArray(conjunto);
		this.inserirConjunto(indiceConjunto, conjunto);
	}

	private void inserirFIFO(int pagina) {
		int indiceConjunto = pagina % this.qtdeConjunto;
		Integer conjunto[] = this.getConjunto(pagina);
		if (ponteiro[indiceConjunto] >= tamanhoConjunto) {
			ponteiro[indiceConjunto] = 0;
		}
		conjunto[ponteiro[indiceConjunto]] = pagina;
		ponteiro[indiceConjunto]++;
		this.inserirConjunto(indiceConjunto, conjunto);
	}

	private void inserirLFU(int pagina) {
		int indiceConjunto = pagina % this.qtdeConjunto;
		int anteriorMenor = acessos[indiceConjunto][0];
		int ponteiro = 0;
		for (int i = 1; i < tamanhoConjunto; i++) {
			if (acessos[indiceConjunto][i] < anteriorMenor) {
				anteriorMenor = acessos[indiceConjunto][i];
				ponteiro = i;
			}
		}
		Integer conjunto[] = this.getConjunto(pagina);
		conjunto[ponteiro] = pagina;
		acessos[indiceConjunto][ponteiro] = 1;
		this.inserirConjunto(indiceConjunto, conjunto);
	}

}
