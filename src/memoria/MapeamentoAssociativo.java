package memoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapeamentoAssociativo extends Cache {

	private int ponteiro;
	private NoFrequencia frequencias;

	public MapeamentoAssociativo(int tamanho, PoliticaInsercaoEnum politica) {
		super.cache = new Integer[tamanho];
		this.ponteiro = 0;
		super.politica = politica;
		if (super.politica == PoliticaInsercaoEnum.LFU) {
			frequencias = new NoFrequencia(-1, -1);
		}
	}

	public boolean isHit(int pagina) {
		boolean hit = false;
		for (int i = 0; i < cache.length; i++) {
			if (cache[i] != null && cache[i].intValue() == pagina) {
				if (super.politica == PoliticaInsercaoEnum.LFU) {
					frequencias.adicionar(pagina);
				}
				hit = true;
				break;
			}
		}
		if (this.politica == PoliticaInsercaoEnum.LRU && hit) {
			List<Integer> temp = new LinkedList<Integer>(Arrays.asList(super.cache));
			temp.remove(temp.indexOf(pagina));
			List<Integer> temp2 = new ArrayList<>();
			temp2.add(pagina);
			temp2.addAll(temp);
			temp2.toArray(super.cache);
		}
		return hit;
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
		cache[new Random().nextInt(super.cache.length)] = pagina;
	}

	private void inserirLFU(int pagina) {
		int chaveSubstituir = frequencias.getChaveMenorFrequencia(super.cache.length);
		for (int i = 0; i < super.cache.length; i++) {
			if (chaveSubstituir == -1 && super.cache[i] == null) {
				super.cache[i] = pagina;
				break;
			} else {
				if (chaveSubstituir == super.cache[i]) {
					super.cache[i] = pagina;
					break;
				}
			}
		}
		frequencias.adicionar(pagina);
		frequencias.atualizarChaves(super.cache);
	}

	private void inserirLRU(int pagina) {
		List<Integer> temp = Arrays.asList(Arrays.copyOf(super.cache, super.cache.length - 1));
		List<Integer> temp2 = new ArrayList<>();
		temp2.add(pagina);
		temp2.addAll(temp);
		temp2.toArray(super.cache);
	}

	private void inserirFIFO(int pagina) {
		if (ponteiro >= super.cache.length) {
			ponteiro = 0;
		}
		cache[ponteiro] = pagina;
		ponteiro++;
	}

	public int getPonteiro() {
		return ponteiro;
	}

	public void setPonteiro(int ponteiro) {
		this.ponteiro = ponteiro;
	}

}
