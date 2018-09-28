package memoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapeamentoAssociativo extends Cache {

	private int ponteiro;
	private int[] acessos;

	public MapeamentoAssociativo(int tamanho, PoliticaInsercaoEnum politica) {
		super.cache = new Integer[tamanho];
		this.ponteiro = 0;
		super.politica = politica;
		if (super.politica == PoliticaInsercaoEnum.LFU) {
			acessos = new int[tamanho];
		}
	}

	public boolean isHit(int pagina) {
		boolean hit = false;
		for (int i = 0; i < cache.length; i++) {
			if (cache[i] != null && cache[i].intValue() == pagina) {
				if (super.politica == PoliticaInsercaoEnum.LFU) {
					acessos[i]++;
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
		int anteriorMenor = acessos[0];
		ponteiro = 0;
		for (int i = 0; i < acessos.length; i++) {
			if (acessos[i] < anteriorMenor) {
				anteriorMenor = acessos[i];
				ponteiro = i;
			}
		}
		cache[ponteiro] = pagina;
		acessos[ponteiro]++;
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

	public int[] getAcessos() {
		return acessos;
	}

	public void setAcessos(int[] acessos) {
		this.acessos = acessos;
	}

}
