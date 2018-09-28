package memoria;

import java.util.Arrays;

public abstract class Cache {

	public Integer[] cache;

	public PoliticaInsercaoEnum politica;

	public Integer[] getCache() {
		return cache;
	}

	public String toString() {
		return Arrays.toString(this.cache);
	}

	public abstract boolean isHit(int pagina);

	public abstract void inserir(int pagina);

}
